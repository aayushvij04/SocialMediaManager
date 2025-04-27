package socialmedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends User implements SocialMediaActions{
	
	public Admin(String username) {
        super(username);
    }

    @Override
    public void displayOptions() {
        System.out.println("\n========================================");
        System.out.println("           Admin Dashboard              ");
        System.out.println("========================================\n");
        
        System.out.println("Choose an action:");
        System.out.println("----------------------------------------");
        System.out.println("1. Post Content");
        System.out.println("2. Delete Post");
        System.out.println("3. Comment on Post");
        System.out.println("4. Follow User");
        System.out.println("5. Unfollow User");
        System.out.println("6. Exit");
        System.out.println("----------------------------------------\n");
    }

    @Override
    public void post(String content) {
        instagram.addPost(content);
        x.addPost(content);
        saveProfileDataToCSV();
    }

    @Override
    public void deletePost(int index) {
        instagram.removePost(index);
        x.removePost(index);
        saveProfileDataToCSV();
    }

    @Override
    public void comment(int postIndex, String comment) {
        instagram.addComment(postIndex, comment);
        x.addComment(postIndex, comment);
        saveProfileDataToCSV();
    }

    @Override
    public void follow(String user, String platform) {
        if (platform.equalsIgnoreCase("Instagram")) {
            instagram.follow(user);
        } else if (platform.equalsIgnoreCase("X")) {
            x.follow(user);
        }
        saveProfileDataToCSV();
    }

    @Override
    public void unfollow(String user, String platform) {
        if (platform.equalsIgnoreCase("Instagram")) {
            instagram.unfollow(user);
        } else if (platform.equalsIgnoreCase("X")) {
            x.unfollow(user);
        }
        saveProfileDataToCSV();
    }

    
    public void handleFollowAction(String followUser) {
        System.out.println("Which platform do you want to follow on? (Instagram/X/Both):");
        Scanner sc = new Scanner(System.in);
        String platform = sc.nextLine();
        
        if (platform.equalsIgnoreCase("Both")) {
            follow(followUser, "Instagram");
            follow(followUser, "X");
            System.out.println("Now following " + followUser + " on both platforms!");
            displayFollowCounts(followUser, "Instagram");
            displayFollowCounts(followUser, "X");
        } else if (platform.equalsIgnoreCase("Instagram") || platform.equalsIgnoreCase("X")) {
            follow(followUser, platform);
            System.out.println("Now following " + followUser + " on " + platform + "!");
            displayFollowCounts(followUser, platform);
        } else {
            System.out.println("Invalid platform choice. Please choose Instagram, X, or Both.");
        }
    }

    public void handleUnfollowAction(String unfollowUser) {
        System.out.println("\n----------------------------------------");
        System.out.println("            Unfollow Action             ");
        System.out.println("----------------------------------------\n");
        
        System.out.println("Which platform do you want to unfollow from?");
        System.out.println("----------------------------------------");
        System.out.println("1. Instagram");
        System.out.println("2. X");
        System.out.println("3. Both");
        System.out.println("----------------------------------------\n");
        
        Scanner sc = new Scanner(System.in);
        String platform = sc.nextLine();
        
        System.out.println("\n----------------------------------------");
        if (platform.equalsIgnoreCase("Both") || platform.equals("3")) {
            unfollow(unfollowUser, "Instagram");
            unfollow(unfollowUser, "X");
            System.out.println("          Unfollow Status              ");
            System.out.println("----------------------------------------");
            System.out.println("Successfully unfollowed " + unfollowUser + " from both platforms!");
            System.out.println("----------------------------------------\n");
            displayFollowCounts(unfollowUser, "Instagram");
            displayFollowCounts(unfollowUser, "X");
        } else if (platform.equalsIgnoreCase("Instagram") || platform.equals("1") || 
                   platform.equalsIgnoreCase("X") || platform.equals("2")) {
            String actualPlatform = platform.equals("1") ? "Instagram" : 
                                  platform.equals("2") ? "X" : platform;
            unfollow(unfollowUser, actualPlatform);
            System.out.println("          Unfollow Status              ");
            System.out.println("----------------------------------------");
            System.out.println("Successfully unfollowed " + unfollowUser + " from " + actualPlatform + "!");
            System.out.println("----------------------------------------\n");
            displayFollowCounts(unfollowUser, actualPlatform);
        } else {
            System.out.println("          Invalid Selection            ");
            System.out.println("----------------------------------------");
            System.out.println("Please choose Instagram, X, or Both");
            System.out.println("----------------------------------------\n");
        }
    }

    private void displayFollowCounts(String otherUser, String platform) {
        try {
            File csvFile = new File("profiles/profiles.csv");
            if (!csvFile.exists()) return;

            
            Profile currentUserProfile = platform.equalsIgnoreCase("Instagram") ? instagram : x;
            int currentUserFollowing = currentUserProfile.following;
            int currentUserFollowers = currentUserProfile.followers;

            
            int otherUserFollowers = -1;
            int otherUserFollowing = -1;

            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 4 && data[0].equals(otherUser) && data[1].equals(platform)) {
                    otherUserFollowers = Integer.parseInt(data[2]);
                    otherUserFollowing = Integer.parseInt(data[3]);
                    break;
                }
            }
            reader.close();

            
            System.out.println("\n----------------------------------------");
            System.out.println("          " + platform + " Follow Counts          ");
            System.out.println("----------------------------------------\n");
            
            System.out.println("Your account (" + username + ")");
            System.out.println("----------------------------------------");
            System.out.println("Following: " + currentUserFollowing);
            System.out.println("Followers: " + currentUserFollowers);
            System.out.println("----------------------------------------\n");
            
            if (otherUserFollowers != -1) {
                System.out.println("Other account (" + otherUser + ")");
                System.out.println("----------------------------------------");
                System.out.println("Following: " + otherUserFollowing);
                System.out.println("Followers: " + otherUserFollowers);
                System.out.println("----------------------------------------\n");
            }
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error displaying follow counts: " + e.getMessage());
        }
    }

    public void handlePostAction(String content, boolean postToInstagram, boolean postToX) {
        boolean instagramSuccess = false;
        boolean xSuccess = false;
        
        if (postToInstagram) {
            if (instagram.posts.size() >= 5) {
                System.out.println("Instagram has reached the maximum limit of 5 posts. Please delete a post first.");
            } else {
                instagram.addPost(content);
                instagramSuccess = true;
            }
        }
        
        if (postToX) {
            if (x.posts.size() >= 5) {
                System.out.println("X has reached the maximum limit of 5 posts. Please delete a post first.");
            } else {
                x.addPost(content);
                xSuccess = true;
            }
        }
        
        if (instagramSuccess || xSuccess) {
            saveProfileDataToCSV();
            if (instagramSuccess && xSuccess) {
                System.out.println("Content posted successfully on both Instagram and X!");
            } else if (instagramSuccess) {
                System.out.println("Content posted successfully on Instagram!");
            } else {
                System.out.println("Content posted successfully on X!");
            }
        }
    }

    public void handleDeleteAction(int deleteIndex) {
        deletePost(deleteIndex);
        System.out.println("Post deleted successfully!");
    }

    public void handleCommentAction(int postIndex, String comment) {
        comment(postIndex, comment);
        System.out.println("Comment added successfully!");
    }

}
