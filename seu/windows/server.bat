@ECHO OFF

REM ************************
REM * Start the local development server
REM * The folder "scoreit-1.0-SNAPSHOT" has unfortunately the name of the version, so if the version is changed, so must this batch
REM ************************

ECHO.
ECHO The server is started in another window. To close it use CTRL+C in that window, because just closing the window will NOT stop the server!
ECHO.

REM ******************************************
REM * Start the server
REM ******************************************

start %~dp0\..\appengine-java-sdk\bin\dev_appserver.cmd %~dp0\..\..\scoreit\target\scoreit-1.0-SNAPSHOT

