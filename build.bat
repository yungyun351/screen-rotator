@echo off
chcp 65001

set JDK_PATH=C:\Program Files\Java\jdk-21\bin

set PATH=%JDK_PATH%;%PATH%
set CURRENT_DIR=%~dp0

echo 【Remove old dist】
rmdir /s /q "%CURRENT_DIR%dist"

echo 【Build JAR】
call mvnw.cmd clean package

echo 【Build EXE】
jpackage --type app-image ^
 --name ScreenRotator ^
 --input target ^
 --main-jar screen-rotator-1.0.jar ^
 --main-class com.marttapps.screenrotator.Main ^
 --icon public/icon.ico ^
 --dest dist ^
 --vendor "Martin T. Studio"

echo 【Finish】

pause