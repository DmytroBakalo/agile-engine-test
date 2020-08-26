This is implementation of AgileEngine technical task. Only way to build and run:
1. mvn clean install from project root, or IDE.
2. get generated war from /target folder and put into any of http server(ex. Tomcat)
3. run server; in browser or Postman specify url http://localhost:port/agile-engine-test-version/
4. working endpoints are /images, /images/id, /images?page=

* What should be fixed;
- proper logging instead of throwing exceptions;
- payload saving in local storage upon application start;
- proper tests are needed;
- payload dto mapping to DB entities for proper saving;
- sonar issues fixes;