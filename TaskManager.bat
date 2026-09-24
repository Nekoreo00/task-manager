@echo off

del /Q *.class 2>nul
del /Q TaskManager.jar 2>nul

javac Task.java IconUtil.java TaskManager.java

if errorlevel 1 (
    echo コンパイルに失敗しました。
    pause
    exit /b
)

jar --create --file TaskManager.jar --main-class TaskManager Task.class IconUtil.class TaskManager.class images

echo JARの更新が完了しました。

start javaw -jar TaskManager.jar

pause