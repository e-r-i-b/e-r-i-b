package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.ClassHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roshka
 * @ created 16.11.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class MessageGenerator
{
	private static final Object MESSAGE_GENERATOR_LOCKER = new Object();
	private static final Map<String, MessageGenerator> instances = new HashMap<String, MessageGenerator>();
	private static final String MESSAGE_GENERATOR_PARAMETER_NAME = "com.rssl.phizic.gate.MessageGenerator";

	/**
	 * ���������� ����������� ����������.
	 * ��������� �������� � gate.properties � ��������� com.rssl.phizic.gate.MessageGenerator.<system>
	 * @param system ����������, ��� ������� ��������� ���������.
	 * @return ���������� ����������.
	 */
	public static final MessageGenerator getInstance(String system) throws GateException
	{
		MessageGenerator instance = instances.get(system);
		if (instance == null)
		{
			synchronized (MESSAGE_GENERATOR_LOCKER)
			{
				instance = instances.get(system);
				if (instance == null)
				{
					String generatorClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(MESSAGE_GENERATOR_PARAMETER_NAME + "." + system);
					try
					{
						Class<MessageGenerator> generatorClass = ClassHelper.loadClass(generatorClassName);
						instance = generatorClass.newInstance();
					}
					catch (Exception e)
					{
						throw new GateException(e);
					}
					instances.put(system, instance);
				}
			}
		}
		return instance;
	}

	/**
	 * �������� ���������
	 * @param document
	 * @return
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public abstract MessageData buildMessage(Document document, MessageInfo info, MessageHead messageHead) throws GateException;

	/**
	 * ��������� ����� ��� ������ �������� ����� ������(����������� ����)
	 * @param serializer ������������(�������� �����)
	 * @param text ���������� ����
	 * @throws SAXException
	 */
	protected void writeText(InnerSerializer  serializer, String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		serializer.characters(arr, 0, arr.length);
	}
}
