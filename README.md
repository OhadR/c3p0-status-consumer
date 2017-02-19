c3p0-status-consumer
====================

This project is a web-app that should be deployed on a Servlet container. When Spring finishes to load all components, 
there is a `TimerTask` (`MonitorConnectionPoolsTask`), that invokes REST calls to a configurable target and monitors
it c3p0-connection-pool status.

It stores the data (converted from JSON) in a Map, that maps from the file name - which is the data-source name - to 
a pair of <CSV file object, file creation date>.
In addition, it stores the data in a CSV file (each data-source has its own CSV file).



Configuration
=============
 
properties
----------
**com.ohadr.c3p0-status-consumer.target-url** - 

**com.ohadr.c3p0-status-consumer.test-mode** - the username in the SMTP server. E.g. if you use Google account: myaccount@gmail.com

**com.ohadr.c3p0-status-consumer.num-days-for-file** - the password in the SMTP server.

**com.ohadr.c3p0-status-consumer.sample-rate-seconds** - the string that appears in the "from" field in the emails that are sent to users. E.g 'App Admin'.
