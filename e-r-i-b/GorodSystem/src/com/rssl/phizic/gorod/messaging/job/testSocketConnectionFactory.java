package com.rssl.phizic.gorod.messaging.job;

import junit.framework.TestCase;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Gainanov
 * @ created 06.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class testSocketConnectionFactory  extends TestCase
{
	public void testGetter()
	{
		try
		{
			SocketConnectionFactory scf = new SocketConnectionFactory();
			scf.execute(null);
			Socket socket = scf.getSocket();
			if(!socket.isConnected())
				System.out.println("!! socket is not connected!!");
			scf.execute(null);
		}
		catch (JobExecutionException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
