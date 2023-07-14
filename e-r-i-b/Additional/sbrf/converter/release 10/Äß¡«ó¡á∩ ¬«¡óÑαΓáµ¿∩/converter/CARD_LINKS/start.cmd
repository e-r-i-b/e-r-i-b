FOR /L %%i IN (1,1,30) DO ( 
ping -n 2 127.0.0.1 > nul
start convert.bat
)

