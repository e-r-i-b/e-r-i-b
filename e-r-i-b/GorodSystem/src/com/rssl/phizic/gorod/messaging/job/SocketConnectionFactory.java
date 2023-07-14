package com.rssl.phizic.gorod.messaging.job;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gorod.messaging.GorodConfigImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Нигде не используется.
 * Был написан исключительно для решения проблем с подключением
 * к ИСППН "Город" в СРБ СБРФ.
 * оставлен на всякий пожарный случай, вдруг пригодится
 * @author Gainanov
 * @ created 06.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class SocketConnectionFactory extends Thread implements Job
{
	private static List<Socket> list = new ArrayList<Socket>();
	private static int CONNECTIONS_COUNT = 50;
	private static int MIN_CONNECTIONS_COUNT = 30;

	public static synchronized Socket getSocket() throws IOException
	{
		int i = 0;
		for (Socket socket : list)
		{
			if (socket.isConnected())
			{
				list.remove(socket);
				if (list.size() > MIN_CONNECTIONS_COUNT)
				{
					SocketConnectionFactory scf = new SocketConnectionFactory();
					scf.start();
				}
				return socket;
			}
			else
				list.remove(socket);
		}
		return createNewSocket();
	}

	private static SocketAddress getAddress()
	{
		GorodConfigImpl config = ConfigFactory.getConfig(GorodConfigImpl.class);
		return new InetSocketAddress(config.getHost(), config.getPort());
	}

	private synchronized void fillList() throws IOException
	{
		while (list.size() < CONNECTIONS_COUNT)
			list.add(createNewSocket());
	}

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			fillList();
		}
		catch (IOException e)
		{
			throw new JobExecutionException(e);
		}
	}

	private static Socket createNewSocket() throws IOException
	{
		Socket socket = new Socket();
		socket.connect(getAddress());
		return socket;
	}

	public void run()
	{
		try
		{
			fillList();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
