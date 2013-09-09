@ECHO OFF

REM ************************
REM * Start the local development server
REM * The folder "scoreit-1.0-SNAPSHOT" has unfortunately the name of the version, so if the version is changed, so must this batch
REM ************************

REM ******************************************
REM * Maven clean
REM ******************************************

CALL mvn clean
IF NOT "%ERRORLEVEL%" == "0" GOTO _ERROR

REM ******************************************
REM * Maven build
REM ******************************************

CALL mvn verify
IF NOT "%ERRORLEVEL%" == "0" GOTO _ERROR

REM ******************************************
REM * Start the server
REM ******************************************

ECHO.
ECHO The server is started in another window. To close it use CTRL+C in that window, because just closing the window will NOT stop the server!
ECHO.

start "Google App Engine Development Server" "%gae_home%\dev_appserver.cmd" %~dp0..\..\scoreit\target\scoreit-1.0-SNAPSHOT 

GOTO _END



REM ******************************************
REM * Shows an error-message and ends the script
REM ******************************************

:_ERROR
ECHO.
ECHO ERROR!
ECHO.
PAUSE
GOTO _END

REM ******************************************
REM * End script
REM ******************************************

:_END

