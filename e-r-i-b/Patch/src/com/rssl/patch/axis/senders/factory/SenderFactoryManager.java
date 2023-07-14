package com.rssl.patch.axis.senders.factory;

import com.rssl.patch.axis.senders.Connection;
import com.rssl.patch.axis.senders.factory.wrappers.FraudMonitoringSenderFactoryWrapper;
import com.rssl.patch.axis.senders.factory.wrappers.SenderFactoryWrapper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.axis.MessageContext;
import org.apache.axis.components.net.SocketFactory;
import org.apache.axis.components.net.SocketFactoryFactory;
import org.apache.axis.handlers.soap.SOAPService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */
public class SenderFactoryManager
{
	private static final Map<String, SenderFactoryWrapper> WRAPPERS = new HashMap<String, SenderFactoryWrapper>();
	static
	{
		WRAPPERS.put("rsa:analyze:Analyze",                             new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:notify:Notify",                               new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:query:Query",                                 new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:authenticate:Authenticate",                   new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:challenge:Challenge",                         new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:createuser:CreateUser",                       new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:queryauthstatus:QueryAuthStatus",             new FraudMonitoringSenderFactoryWrapper());
		WRAPPERS.put("rsa:udpateuser:UpdateUser",                       new FraudMonitoringSenderFactoryWrapper());
	}

	private static final SenderFactoryManager INSTANCE = new SenderFactoryManager();

	public static SenderFactoryManager getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Получить фабрику
	 * @param msgContext msgContext
	 * @param connection connection
	 * @return фабрика
	 */
	public SocketFactory getFactory(MessageContext msgContext, Connection connection)
	{
		String serviceName = getServiceName(msgContext);
		if (StringHelper.isNotEmpty(serviceName))
		{
			SenderFactoryWrapper wrapper = WRAPPERS.get(serviceName);
			if (wrapper != null)
			{
				return wrapper.getFactory(connection);
			}
		}

		return SocketFactoryFactory.getFactory(connection.getProtocol(), connection.getOptions());
	}

	private String getServiceName(MessageContext msgContext)
	{
		if (msgContext == null)
		{
			return null;
		}

		String serviceName = msgContext.getTargetService();
		return StringHelper.isEmpty(serviceName) ? null : serviceName;
	}
}
