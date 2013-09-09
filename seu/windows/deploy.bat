@ECHO OFF

REM ************************
REM * Deploy score-it
REM * You should only need to give your credentials once, the app engine will save them (in Windows in the registry)
REM * The folder "scoreit-1.0-SNAPSHOT" has unfortunately the name of the version, so if the version is changed, so must this batch
REM ************************


REM ******************************************
REM * Determine, which app-ID to use
REM ******************************************

SET used_app_id=%app_id%

IF "%1"=="alt" (
    IF NOT DEFINED app_id2 (
        ECHO The property "app_id2" ^(in the configuration file "scoreit.config"^) needs to be set, if you want to use the alternative app ID!
        GOTO _ERROR
    )
    
    ECHO Will use alternative app ID!
    SET used_app_id=%app_id2%
)

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
REM * Replace the placeholder-value for the app ID in "appengine-web.xml" with the real value from the config file.
REM ******************************************

ECHO.
ECHO Now the value of the app ID in the file "appengine-web.xml" will be replaced with this value from the config file: %used_app_id%.
ECHO.

cscript %~dp0\replace.vbs "%~dp0\..\..\scoreit\target\scoreit-1.0-SNAPSHOT\WEB-INF\appengine-web.xml" DO_NOT_CHANGE_THIS_VALUE %used_app_id%

REM ******************************************
REM * deploy
REM ******************************************

ECHO.
ECHO Deploying now. If this takes too long and you cancel it ^(with CTRL+C)^, then you need to run "rollback" before the next deployment.
ECHO.

"%gae_home%\appcfg.cmd" update %~dp0\..\..\scoreit\target\scoreit-1.0-SNAPSHOT

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
