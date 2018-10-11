# TaskManagementSystem
<h3>There are 2 authorization levels - <br />"ADMIN" and "USER".</h3>

<ul>
	<li>Users with role "ADMIN" are allowed to create users, projects,<br /> tasks, assign tasks to users and projects, reassign tasks, and<br /> modify users, projects and tasks.</li>
	<li>Users with role "USER" are allowed to view their user details and<br /> modify them, view tasks assigned to him/her and reassign tasks to <br />other "USER"-s.<br /><br />
	</li>
</ul>

<p>You can change DB credential settings from application.properties file<br /> in the source code to run the application in localhost.</p>

<p>You can find Postman requests collections in the project directory<br /> (TMSRequests.postman_collection.json)</p>

<h5>*There are 1 user with role "ADMIN", 2 users with role "USER",<br /> 3 projects and 9 tasks created by default.</h5>

<h4>Login Requisites</h4>
<ul>
	<li>Log in as "ADMIN".
	    <ul>
	    <li>username - admin123</li>
	    <li>password - 123adminPassword987</li>
	    </ul>
    </li>
	<li>Log in as "USER".
	    <ul>
	        <li>username - userName1</li>
	        <li>password - user1pass</li>
	    </ul>
	    <br />
	    <ul>
            <li>username - userName2</li>
            <li>password - user2pass</li>
        </ul>
	</li>
</ul>

