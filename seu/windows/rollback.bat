@ECHO OFF

REM ************************
REM * Rollback score-it
REM * The folder "scoreit-1.0-SNAPSHOT" has unfortunately the name of the version, so if the version is changed, so must this batch
REM ************************


"%gae_home%\appcfg.cmd" rollback %~dp0\..\..\scoreit\target\scoreit-1.0-SNAPSHOT
