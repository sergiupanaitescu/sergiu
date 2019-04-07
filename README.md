Basic URL Shortener API

This is just for personal development purposes and it was not tested thoroughly
The app exposes an api that can be used to obtain shortened urls and call those urls to get redirected to original ones.
Ex:
1)POST request at http://localhost:8180/shorten
with payload:
{
	"url":"http://www.abcd.com"
}
will answer with:
{
    "url": "DA"
}
2)GET request at http://localhost:8180/DA will answer with a redirect to http://www.abcd.com

To run this app you need MySql Database Server Installed.
This is a SpringBoot standalone app. Before building the jar you need to configure the MySql DB connection
in application.properties. You need a databse with the name you provided in the properties file to be created
prior of running the app.
