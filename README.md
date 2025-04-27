#Social Media Management System - Detailed Project Explanation
==============================================================

DISCLAIMER : 
1. Before running the program , please configure the external jar files present in          the lib folder through build path.

2. Before using the Market Analyst , please create accounts of either Admin or Content Creator and post some content or follow some users.

Overview
--------
This Java project simulates a complete social media management platform where users can interact with two platforms: Instagram and X (formerly Twitter). 
Users are classified into three roles: Admin, Content Creator, and Market Analyst, each with different abilities. The system uses CSV files to store and manage data persistently.

Main Features
-------------
1. User Authentication:
    - New users can register by setting a username, password, and selecting a role.
    - Existing users can log in using their credentials.

2. Role-Based Functionalities:
    - Admin:
        * Post content on Instagram and X.
        * Delete posts.
        * Comment on posts.
        * Follow or unfollow other users.
    - Content Creator (inherits Admin capabilities):
        * Manage brand affiliations.
        * Manage sponsored products.
    - Market Analyst:
        * Analyze Instagram and X metrics.
        * Generate and plot follower growth trends.

3. Data Management:
    - User posts, followers, following, and comments are stored in `profiles/profiles.csv`.
    - User authentication info is stored in `profiles/user_auth.csv`.
    - Brand and sponsored product data is stored in `profiles/creator_data.csv`.

4. Visualization:
    - Follower growth trends are plotted using JFreeChart.
    - Charts are saved as JPEG files inside the `charts/` directory.

Important Classes and Responsibilities
---------------------------------------
- `SocialMediaManager`:
    * Main class with `main` method to handle user authentication, role assignment, and menu-driven interaction.

- `User` (abstract class):
    * Base class for all users (Admin, ContentCreator, MarketAnalyst).
    * Manages user profile data loading/saving.

- `Admin` (extends User, implements SocialMediaActions):
    * Handles posting, commenting, deleting posts, following, and unfollowing users.

- `ContentCreator` (extends Admin):
    * Adds functionality for managing brand affiliations and sponsored products.

- `MarketAnalyst` (extends User):
    * Focuses on analytics and trend visualization.
    * Uses InstagramAnalysis and XAnalysis classes.

- `Profile`:
    * Represents user profile on Instagram/X.
    * Manages posts, followers, following.

- `Profile.Post` (inner class):
    * Represents a single social media post with content, likes, comments, and hashtags.

- `SocialMediaActions` (interface):
    * Defines standard actions like post, delete, comment, follow, unfollow.

- `MarketAnalysis`, `InstagramAnalysis`, `XAnalysis`:
    * Classes for simulating follower trends and extracting follower metrics from CSV data.

Files and Directories
----------------------
- `profiles/user_auth.csv` : Stores username, password, and role.
- `profiles/profiles.csv`  : Stores posts, follower and following count for each user.
- `profiles/creator_data.csv` : Stores brand affiliations and sponsored products for content creators.
- `charts/` : Stores generated trend graphs in JPEG format.

Program Flow
------------
1. User logs in or registers.
2. Based on their role, they are given access to specific functionalities.
3. Actions like posting, following, commenting, etc., are executed.
4. Profile data is updated and saved in CSV files after each action.
5. Market Analysts can also plot and visualize trend data using generated charts.

External Libraries
-------------------
- JFreeChart for generating and displaying trend graphs.

Conclusion
----------
This project demonstrates object-oriented programming (OOP) concepts like inheritance, interfaces, abstraction, file handling, exception handling, and external library integration in Java. It provides a miniature simulation of a social media management environment with analytics and persistent data storage.
