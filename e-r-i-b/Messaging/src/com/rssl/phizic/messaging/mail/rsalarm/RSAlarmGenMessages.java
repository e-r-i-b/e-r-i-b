package com.rssl.phizic.messaging.mail.rsalarm;
/*************************************************************************
 FILE         	:   RSAlarmGenMessages.java
 COPYRIGHT    	:   R-Style Softlab, 2006
 DESCRIPTION  	:   Генерация потока сообщений для тестирования SMPP
 PROGRAMMED BY	:   Иванов Александр
 CREATION DATE	:   22.03.2006
*************************************************************************/
import java.io.PrintStream;
import java.nio.charset.Charset;

class	RSAlarmGenMessages
{
	public	static	void	main(String[] args)	throws Throwable
	{
		Long 	to 	= 27999900001L;

		String	path 	= "SMPP";
		String	text	= "This is a test message...";
		String	cp    	= "win1251";
		String	host 	= "localhost";

		int	count	= 100;
		int	port 	= 12121;

		for(int i = 0; i < args.length; i++) if(args[i].substring(0, 3).compareToIgnoreCase("to=") == 0)
		{
			to = Long.valueOf(args[i].substring(3));
		}
		else if(args[i].substring(0, 3).compareToIgnoreCase("cp=") == 0)
		{
			cp = args[i].substring(3);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("host=") == 0)
		{
			host = args[i].substring(5);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("port=") == 0)
		{
			port = Integer.valueOf(args[i].substring(5)).intValue();
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("text=") == 0)
		{
			text = args[i].substring(5);
		}
		else if(args[i].substring(0, 5).compareToIgnoreCase("path=") == 0)
		{
			path = args[i].substring(5);
		}
		else if(args[i].substring(0, 6).compareToIgnoreCase("count=") == 0)
		{
			count = Integer.valueOf(args[i].substring(6));
		}

		PrintStream	out = Charset.isSupported(cp) ? new PrintStream(System.out, true, cp) : System.out;

  	  	try
 		{
			RSAlarmConnect conn = new RSAlarmConnect(host, port);

		 	for(int i = 0; i++ < count;)
			{
				out.println("\n" + i + " RC = " + conn.Submit("JAVA-SAMPLE", to.toString() + ",,charsize=8", path, text, null).getProperty(RSAlarmConnect.RSALARM_PROP_RESULT, RSAlarmConnect.RSALARM_TYPE_INTEGER).toString() + "\n");
				++to;
			}
			
			conn.shutdown();
		}
		catch(Throwable einfo)
		{
			out.println("\nGeneral fault: " + einfo.getMessage() + "\n");
		}
	}
}
