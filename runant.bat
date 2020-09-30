@echo off

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181
rem set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_80
rem #set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_75
set ANT_HOME=D:\apache-ant
%ANT_HOME%\bin\ant -buildfile build.xml %1 %2 %3 %4 %5