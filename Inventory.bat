start "" E:\apache-tomcat-8.5.20\bin\startup.bat
waitfor SomethingThatIsNeverHappening /t 20
start chrome.exe http://localhost:8080/Inventory-1.0/tally-item-mng.html
