package com.rssl.phizic.web.jms;

import javax.naming.NamingException;
import javax.jms.JMSException;

/**
 * @author krenev
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class MultiThreadJMSReceiver
{
	private JMSReceiverTreadBase[] receivers;
	private int threadsCount;

	public MultiThreadJMSReceiver(int threadsCount)
	{
		this.threadsCount = threadsCount;
		receivers = new JMSReceiverTreadBase[threadsCount];
	}

	public abstract JMSReceiverTreadBase createReceiver();

	public void start() throws NamingException, JMSException
	{
		for (int i = 0; i < threadsCount; i++)
		{
			receivers[i] = createReceiver();
			receivers[i].start();
		}
	}

	public void stop()
	{
		for (JMSReceiverTreadBase receiver : receivers)
		{
			if (receiver!= null)
			{
				receiver.stop();
			}
		}
	}
}
