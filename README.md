# BSMP 2016 - Data Analytics and Presentation
## Glass Charts Project
Glass Charts is a Google Glass application that aims to help technicians and faculty staff to improve their work by 
providing a way for them to access environmental measurements while doing doing other tasks.
<br><br>
Data is shown in graphs which data comes from a remote database. In order to retrieve data from the database, the application does a HTTP
call to the server that will handle it according to what was requested.
<br>

Repository contents:
<ul>
<li>Script to populate database (db)</li>
<li>Server code (NodeServer)</li>
<li>Application code (GlassDataPresentation)</li>
</ul>

###Server and Database
Currently the server and the database are both deployed at Heroku.<br>
There are four ways to access data:<br>
<ul>
<li>Reach https://iit-data-presentation-server.herokuapp.com/ to get all the data stored in the database.</li>
<li>Reach https://iit-data-presentation-server.herokuapp.com/getHourData to get hourly data from a day.</li>
<li>Reach https://iit-data-presentation-server.herokuapp.com/getMonthData to get weekly data from a month.</li>
<li>Reach https://iit-data-presentation-server.herokuapp.com/getYearData to get monthly data from a year.</li>
</ul>

###Running the application
There are a few steps that are needed to be done before running the application on Google Glass.
<ul>
<li> The first step is to set up the environment. It is recommended to follow the tutorial provided by Google of how to set up the environment
(https://developers.google.com/glass/develop/gdk/quick-start). But basically all you need to do is to have Android Studio installed, to have
the SDK platform for Android 4.4.2 (API 19), and also to have installed Glass Development Kit Preview. All this for you to be able to compile the project.
</li>
<li>On Google Glass, you need to enable the Debug mode. Settings > Device Info > Turn on debug. </li>
<li>Connect Glass to your computer with the USB cable.</li>
<li>After doing all the set up above, you will need to open the project through Android Studio and run it selecting Google Glass as the target.</li>
</ul>
<b>REMEMBER TO HAVE GLASS PAIRED WITH A PHONE THAT HAS INTERNET CONNECTION OR TO CONNECT IT TO WI-FI IN ORDER TO MAKE THE APPLICATION RUN!</b>

