@ECHO OFF

REM ******************************************
REM * Change to the directory, which contains this batch-file
REM ******************************************

cd %~dp0

REM ******************************************
REM * DO NOT CHANGE THIS BATCH FILE!
REM * This batch file prepares the command-window, which allows you to build and deploy score-it.
REM ******************************************

SET error_message=NONE

REM ******************************************
REM * Check if config file exists
REM ******************************************

IF NOT EXIST scoreit.config  (
    ECHO.
    ECHO The configuration file "scoreit.config" is missing!
    ECHO It needs to be in the same folder as this batch file.
    ECHO This file definces some properties. Each property must be defined in an extra line! There must not be empty lines! 
    ECHO A property is defined like this: PROPERTY_NAME=PROPERTY_VALUE
    ECHO No spaces! This is wrong: "maven_home = C:\mvn\bin". This is right: "maven_home=C:\mvn\bin"
    ECHO The following properties must be defined:
    ECHO * maven_home: The path to the folder containing the "mvn.bat".
    ECHO * java_home: The path to the java sdk ^(the folder which contains the folder "bin" which contains the file "javac.exe"^).
    ECHO * git_home: The path to the GIT folder which contains "git.exe". 
    ECHO * app_id: The id of the app in the Google App Engine ^(Dynamic, because every devloper may want to deploy on his own URL for testing.^).
    ECHO * app_id2: OPTIONAL! An alternative "app_id". If defined, you can use "deploy alt" for deploying with this app ID ^(That way, you can have a PRODUCTION and a TEST Environment. for example.^).
    ECHO Example of the content of "scoreit.config":
    ECHO **********************
    ECHO maven_home=C:\Program Files ^(x86^)maven\apache-maven-3.1.0\bin
    ECHO java_home=C:\Program Files\Java\jdk1.7.0_17
    ECHO git_home=C:\Program Files ^(x86^)\Git\bin
    ECHO app_id=scoreit
    ECHO app_id2=scoreittest
    ECHO **********************
    ECHO Attention: Needs Java ^>= 1.7 and Maven ^>= 3.1 !
    SET error_message=Please create the configuration file as stated above!
    GOTO _ERROR
)

REM ******************************************
REM * Read config file.
REM ******************************************

FOR /F "tokens=1,2 delims==" %%G IN (scoreit.config) DO  (
    IF "%%G"=="maven_home" SET maven_home=%%H
    IF "%%G"=="java_home" SET java_home=%%H
    IF "%%G"=="app_id" SET app_id=%%H
    IF "%%G"=="app_id2" SET app_id2=%%H
    IF "%%G"=="git_home" SET git_home=%%H
)

REM ******************************************
REM * Check if git exists
REM ******************************************

IF NOT EXIST "%git_home%\git.exe"  (
    SET error_message=The property "git_home" ^(in the configuration file "scoreit.config"^) does not lead to the folder containing the file "git.exe"!
    GOTO _ERROR
)

REM ******************************************
REM * Check if java exists
REM ******************************************

IF NOT EXIST "%java_home%\bin\javac.exe"  (
    SET error_message=The property "java_home" ^(in the configuration file "scoreit.config"^) does not lead to the folder containing the file "javac.exe"!
    GOTO _ERROR
)

REM ******************************************
REM * Check if git exists
REM ******************************************

IF NOT EXIST "%maven_home%\mvn.bat"  (
    SET error_message=The property "maven_home" ^(in the configuration file "scoreit.config"^) does not lead to the folder containing the file "mvn.bat"!
    GOTO _ERROR
)

REM ******************************************
REM * Check if google app engine SDK for java exists
REM ******************************************

IF NOT EXIST "%~dp0\appengine-java-sdk\bin\appcfg.cmd"  (
    SET error_message=Please download the google app engine SDK for Java ^(https://developers.google.com/appengine/downloads^). It needs to be in the same directory as this batch file in a folder "appengine-java-sdk" ^(This folder then contains the folder "bin" which contains the file "appcfg.cmd".^).
    GOTO _ERROR
)

REM ******************************************
REM * Check if app ID is set
REM ******************************************

IF NOT DEFINED app_id  (
    SET error_message=The property "app_id" ^(in the configuration file "scoreit.config"^) needs to be set!
    GOTO _ERROR
)

REM ******************************************
REM * Update the environment variables
REM ******************************************

SET PATH=%PATH%;%maven_home%
SET PATH=%PATH%;%git_home%
SET PATH=%PATH%;%~dp0
SET PATH=%PATH%;%~dp0windows
REM For google app engine sdk: appcfg.cmd just calls "java" and therefore java.exe must be in PATH. It needs to be from a JDK, too (no JRE)!
SET PATH=%java_home%\bin;%PATH%;
SET JAVA_HOME=%java_home%

REM ******************************************
REM * Change to the project directory
REM ******************************************

cd ..\scoreit

REM ******************************************
REM * Open the command line.
REM ******************************************

ECHO.
ECHO Please note: 
ECHO * Build and deploy with "deploy"
ECHO * Start the development server with "server"
ECHO * Do read in the folder "documentation" !: 
ECHO     * "eclipse_tips.txt" 
ECHO     * "maven_tips.txt" 
ECHO     * "git.odt" 
ECHO     * "coding_style.txt"
ECHO.
ECHO The score-it SEU is READY!
ECHO.
ECHO.
ECHO.

cmd

GOTO _END


REM ******************************************
REM * Shows an error-message and ends the script
REM ******************************************

:_ERROR
ECHO.
ECHO ERROR!
ECHO %error_message%
ECHO.
PAUSE
GOTO _END

REM ******************************************
REM * End script
REM ******************************************

:_END

