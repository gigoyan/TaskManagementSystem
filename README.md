# TaskManagementSystem
There are 2 authorization levels - “ADMIN” and “USER”. 
Users with role “ADMIN” are allowed to create users, projects, tasks, 
assign tasks to users and projects, reassign tasks, and modify users, projects and tasks. 
Users with role “USER” are allowed to view their user details and modify them, 
view tasks assigned to him/her and reassign tasks to other “USER”-s.

You can login as “ADMIN” with (admin123) username and (123adminPassword987) password, 
Login as "USER" with (userName1) username and (user1pass) password.
You can change DB credential settings from application.properties file in source code to run application in localhost.

You can find Postman requests collections in project directory
(TMSRequests.postman_collection.json)
