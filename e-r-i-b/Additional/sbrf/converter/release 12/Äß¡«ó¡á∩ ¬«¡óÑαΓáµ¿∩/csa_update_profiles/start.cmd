for /L %%i in (1,1,32) do (
	ping -n 2 127.0.0.1 >null 	
	start job.bat
)
exit
