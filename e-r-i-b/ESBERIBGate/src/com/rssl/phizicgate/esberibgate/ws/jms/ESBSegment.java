package com.rssl.phizicgate.esberibgate.ws.jms;

import com.rssl.phizicgate.esberibgate.ws.jms.common.JAXBSegmentMessageHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.MessageBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.common.MessageParser;

/**
 * @author akrenev
 * @ created 26.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����
 */

public enum ESBSegment
{
	//����������� ������� ����
	federal("jms/esb/esbQCF", "jms/esb/esbQueue", "jms/esb/esbSyncOutQueue", "jms/esb/esbOutQueue",
			com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ObjectFactory.class,
			"com/rssl/phizicgate/esberibgate/ws/ERIBAdapter.xsd"),

	//������� "������" ����
	light("jms/esb/light/queueFactory", "jms/esb/light/inQueue", "jms/esb/light/onlineOutQueue", "jms/esb/light/offlineOutQueue",
			com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.ObjectFactory.class,
			"com/rssl/phizicgate/esberibgate/ws/jms/segment/light/adapter.xsd"),
	;

	private final String queueFactoryName;
	private final String inQueueName;
	private final String onlineOutQueueName;
	private final String offlineOutQueueName;
	private final MessageBuilder messageBuilder;
	private final MessageParser messageParser;

	private <T extends MessageBuilder & MessageParser> ESBSegment(String queueFactoryName, String inQueueName, String onlineOutQueueName, String offlineOutQueueName, Class factoryClass, String xsdPath)
	{
		this(queueFactoryName, inQueueName, onlineOutQueueName, offlineOutQueueName, JAXBSegmentMessageHelper.getInstance(factoryClass.getPackage().getName(), xsdPath));
	}

	private <T extends MessageBuilder & MessageParser> ESBSegment(String queueFactoryName, String inQueueName, String onlineOutQueueName, String offlineOutQueueName, T messageHelper)
	{
		this(queueFactoryName, inQueueName, onlineOutQueueName, offlineOutQueueName, messageHelper, messageHelper);
	}

	private ESBSegment(String queueFactoryName, String inQueueName, String onlineOutQueueName, String offlineOutQueueName, MessageBuilder messageBuilder, MessageParser messageParser)
	{
		this.queueFactoryName = queueFactoryName;
		this.inQueueName = inQueueName;
		this.onlineOutQueueName = onlineOutQueueName;
		this.offlineOutQueueName = offlineOutQueueName;
		this.messageBuilder = messageBuilder;
		this.messageParser = messageParser;
	}

	/**
	 * @return ��� �������
	 */
	public String getQueueFactoryName()
	{
		return queueFactoryName;
	}

	/**
	 * @return ��� �������� �������
	 */
	public String getInQueueName()
	{
		return inQueueName;
	}

	/**
	 * @return ��� ��������� �������� �������
	 */
	public String getOnlineOutQueueName()
	{
		return onlineOutQueueName;
	}

	/**
	 * @return ��� ��������� ������� �������
	 */
	public String getOfflineOutQueueName()
	{
		return offlineOutQueueName;
	}

	/**
	 * @return ������ ���������, ��������������� �������������� ������
	 */
	public MessageBuilder getMessageBuilder()
	{
		return messageBuilder;
	}

	/**
	 * @return ������ ���������, ��������������� �������������� ������
	 */
	public MessageParser getMessageParser()
	{
		return messageParser;
	}
}
