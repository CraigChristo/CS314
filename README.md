CS314 - Software Development Methods 
===============

Repository for Programming Assignment 2 (and 3)

### Now uses Maven to run a Grizzly servlet/container with [Jersey](https://jersey.java.net/)

Getting Maven
-------------
Use a package manager:  

  ###Linux  
    `yum install -y maven`

  ###Mac (Homebrew)  
    `brew install maven`

[Or get the binaries](http://maven.apache.org/download.cgi)

Building the project
--------------------

**Run** 

  1.  `mvn clean compile`
  2.  `mvn exec:java`

**Test**

  1.  `mvn clean test`

Using CURL to test the project
------------------------------

`curl localhost:8080/music/[endpoint] -X[GET|PUT|DELETE] -H "Content-Type: application/json" -H "user: username" -data '{"json": "here"}'`

Creating an eclipse project
---------------------------
1.  `cd` into `./music-api`
2.  run: `mvn eclipse:eclipse`
3.  Import the project from the music-api folder


Project structure
-----------------
*  diagrams  
*  music-api  
  *  pom.xml -- main maven config  
  *  **src**  
    *  main  
      *  java  
        *  package edu.SouthernComfort  
    *  test  
      *  java  
        *  test classes
*  web -- http://root:port/php/  
  *  index.php  
  *  includes  
  *  fragments  
  *  css  
  *  js  