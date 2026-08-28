@echo off

javac Task.java TaskManager.java

if errorlevel 1 (
    echo コンパイルに失敗しました。
    pause
    exit /b
)

jar --create --file TaskManager.jar --main-class TaskManager Task.class TaskManager.class

echo JARの更新が完了しました。

start javaw -jar TaskManager.jar

pause