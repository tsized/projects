#!/bin/bash
javac *.java 
java -Djava.security.policy=policy/server.policy ServerDriver 25483
