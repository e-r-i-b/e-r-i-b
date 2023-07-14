package com.rssl.phizic.csa.ejb;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.synchronization.types.CheckImsiResponse;
import com.rssl.phizic.messaging.ermb.TransportMessageSerializer;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author osminin
 * @ created 09.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ЦСА. Слушатель очереди смс-сообщений с проверкой IMSI
 */
public class CSASmsWithIMSIEjbListener extends CSASmsEjbListener
{
	private final TransportMessageSerializer messageSerializer = new TransportMessageSerializer();

	@Override
	protected String getPhoneNumber(String message) throws Exception
	{
		CheckImsiResponse response = messageSerializer.unmarshallCheckImsiResponse(message);
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(response.getPhone());
	}

	@Override
	protected MQInfo getChannelMQInfo(NodeInfo nodeInfo)
	{
		return nodeInfo.getErmbQueueMQ();
	}

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isCheckImsiMessageLogAvailable();
	}

	@Override
	protected String getMessageType()
	{
		return "sos-CheckImsi";
	}
}
