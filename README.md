# UsersCRUD
User administration web-demon using JSP on SB Admin 2 template for views, Tomcat Servlet Container for controllers and MySQL database entities 
for models.

## What for?
This web application enables you to create and manage users stored in MySQL database through a user-friendly graphic interface.

## How does the model look like?
The model layer and data access layer is done by classes from [UserDao](https://github.com/MichalKamfonik/UserDAO) project.


## What are the abilities of this application?
UserCRUD app gives you several endpoints:
* .../user/list - *list all created users*
* .../user/add - *create new user*
* .../user/show?id= - *show details of user with given ID*
* .../user/edit?id= - *edit user with given ID*
* .../user/delete?id= - *delete user with given ID*

## What technologies were used?
* JDBC
* MySQL
* JBCrypt
* Http Servlets and Filters
* log4j2
* SB Admin2 theme
* JSP
* Expression Language
* JSTL

## How does it work?
1. Users list:
  ![list_users][list_users]
1. Add user
  ![add_user][add_user]
1. Show user details:
  ![show_user][show_user]
1. Edit user
  ![edit_user][edit_user]
1. Delete user
  ![delete_user][delete_user]

[list_users]: images/list.png "Users list"
[add_user]: images/add.png "Add user"
[show_user]: images/show.png "Show user details"
[edit_user]: images/edit.png "Edit user"
[delete_user]: images/delete.png "Delete user"