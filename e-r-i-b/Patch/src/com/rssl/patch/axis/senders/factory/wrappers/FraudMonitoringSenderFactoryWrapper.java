package com.rssl.patch.axis.senders.factory.wrappers;

import com.rssl.patch.axis.senders.Connection;
import com.rssl.phizic.utils.StringHelper;
import org.apache.axis.components.net.DefaultSocketFactory;
import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.SocketFactory;

/**
 * @author khudyakov
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringSenderFactoryWrapper implements SenderFactoryWrapper
{
	public SocketFactory getFactory(Connection connection)
	{
		String protocol = connection.getProtocol();
		if (StringHelper.isEmpty(protocol))
		{
			throw new IllegalArgumentException("Не задан тип протокола.");
		}

		if ("http".equals(protocol))
		{
			return new DefaultSocketFactory(connection.getOptions());
		}
		if ("https".equals(protocol))
		{
			return new JSSESocketFactory(connection.getOptions());
		}

		throw new IllegalArgumentException("Некорректный тип протокола, protocol = " + protocol);
	}
}