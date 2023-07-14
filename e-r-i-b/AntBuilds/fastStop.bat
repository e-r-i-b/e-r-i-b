@echo off
echo Fast Java Stoper

taskkill /IM java.exe /F

if errorlever==1 echo Error in stoping