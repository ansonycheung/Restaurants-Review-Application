# Restaurants-Review-Application

<h2>Setup</h2>

<ul>
  <li>Install Java JDK (SE JDK): http://www.oracle.com/technetwork/java/javase/downloads/index.html
</li>
  <li>Instead of Eclipse installer, I installed “Eclipse IDE for Enterprise Java Developers”: https://www.eclipse.org/downloads/eclipse-packages/
(then copied to Applications)
</li>
  <li>Download Apache Tomcat 9.X (I have 9.0): http://tomcat.apache.org</li>
  <li>Instructions to create new project, configure Eclipse+Tomcat, and run Tomcat (skip the "Web Tools Platform" step): https://www.mulesoft.com/tcat/tomcat-eclipse</li>
</ul>  


<h2>Create a New Project</h2>

<ul>
  <li>Instructions to create new project, configure Eclipse+Tomcat, and run Tomcat (skip the "Web Tools Platform" step): https://www.mulesoft.com/tcat/tomcat-eclipse</li>
  <li>New "Dynamic Web Project"</li>
  <li>"New Runtime…" > Apache Tomcat v9.0</li>
  <li>Specify installation directory (e.g. /Users/anson/Documents/apache-tomcat-9.0.21)</li>
  <li>Specify "Create a new local server" checkbox (default)</li>
</ul>

<h2>Connector/J</h2>

<ul>
  <li>Download Connector/J and add jar to your buildpath: http://dev.mysql.com/downloads/connector/j/</li>
  <li>Right click project > Build Path > Configure Build Path…</li>
  <li>Libraries > Classpath > Add External JARS…</li>
  <li>Browse to path (e.g. /Users/anson/Documents/mysql-connector-java-8.0.16/mysql-connector-java-8.0.16-bin.jar)</li>
</ul>

<h2>JSTL IMPL and SPEC jars</h2>

<ul>
  <li>Download JSTL IMPL and SPEC jars: (taglibs-standard-impl-1.2.5.jar, taglibs-standard-spec-1.2.5.jar), http://tomcat.apache.org/download-taglibs.cgi</li>
  <li>Copy the JSTL jars to the directory "BlogApplication/WebContent/WEB-INF/lib".</li>
  <li>Also copy the Connector/J jar to this path.</li>
  <li>Specify installation directory (e.g. /Users/anson/Documents/apache-tomcat-9.0.21)</li>
  <li>Refresh</li>
</ul>

<h2>Running - JDBC (data access demo)</h2>

<ul>
  <li>Make sure ConnectionManager.java is created.</li>
  <li>Make sure MySQL is running.</li>
  <li>Create empty tables, e.g. https://github.com/ansonycheung/Restaurants-Review-Application/blob/master/ReviewApplication.sql</li>
  <li>Right click Inserter.java: Run As > Java Application.</li>
  <li>View "Console" output.</li>
  <li>Verify data in Workbench.</li>
</ul>

<h2>Running - JSP (web application):</h2>

<ul>
  <li>Insert data (from JDBC or Workbench).</li>
  <li>Right click project: Run As > Run on Server. Start the Tomcat server.</li>
  <li>In a browser, go to: http://localhost:8080/ReviewApplication/findusers</li>
  <li>Type a blog user first name (e.g. ‘anson’) as explore!</li>
  <li>View "Console" output.</li>
  <li>To stop: right click server: Stop.</li>
</ul>

