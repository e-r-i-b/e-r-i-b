package com.rssl.phizic.ejb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.loader.ModuleLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� ��������� JMS-��������� � �������������� EJB MessageDrivenBean
 */
public abstract class EjbListenerBase implements MessageDrivenBean, MessageListener
{
	private static final int MESSAGE_MAX_BYTE_LENGTH = 1000 * 1024;

	// �� ������ ejb-��������� ikfl-����������� �� ��������, ������� ���������� apache
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());

	private final static Map<String, Module> startedModules = new HashMap<String, Module>();

	///////////////////////////////////////////////////////////////////////////

	protected abstract XmlMessageParser getParser();

	protected abstract MessageCoordinator getMessageCoordinator();

	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void setMessageDrivenContext(MessageDrivenContext ctx)
	{
	}

	@SuppressWarnings({"JavaDoc"})
	public void ejbCreate() throws EJBException
	{
	}

	public final void onMessage(Message message)
	{
		//���� �� �����, ����� ������ ��������� ������, ������������� ����������� ����������
		ApplicationInfo.setCurrentApplication(Application.Other);

		// �������� ������
		XmlMessageParser parser = getParser();

		//������ ���������
		String messageText = getMessageText(message);

		XmlMessage xmlRequest = null;
		try
		{
			xmlRequest = parser.parseMessage(new com.rssl.phizic.common.types.TextMessage(messageText));
			if (xmlRequest == null)
				return;
		}
		catch (JAXBException e)
		{
			log.error("���� �� ��������� JMS-��������� - " + e.getMessage(), e);
			return;
		}

		//�������� ����������� ������
		MessageCoordinator messageCoordinator = getMessageCoordinator();

		//�������� ���������� ���������
		MessageProcessor messageProcessor = messageCoordinator.getProcessor(xmlRequest);

		 //�������� ������ ��� ����������� xml-�������
		Module module = messageProcessor.getModule();

		//��������� ������ ��� ����������� �������
		startModule(module);

		//������������ ���������� (��������� �� ������) ����������
		ApplicationInfo.setCurrentApplication(module.getApplication());

		try
		{
			//������������ ���������
			messageProcessor.processMessage(xmlRequest);
		}
		catch (Throwable e)
		{
			log.error("[" + module.getName() + "] ���� �� ��������� JMS-��������� - " + e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private String getMessageText(Message message)
	{
		if (message instanceof TextMessage)
		{
			TextMessage textMessage = (TextMessage) message;

			try
			{
				return textMessage.getText();
			}
			catch (JMSException e)
			{
				throw new RuntimeException(e);
			}
		}

		if (message instanceof BytesMessage)
		{
			BytesMessage bytesMessage = (BytesMessage) message;

			try
			{
				long messageLength = bytesMessage.getBodyLength();
				if (messageLength > MESSAGE_MAX_BYTE_LENGTH)
					throw new IllegalArgumentException("������� ������� JMS-���������: ����� " + messageLength + " ����");

				byte[] messageBytes = new byte[(int)messageLength];
				bytesMessage.readBytes(messageBytes, messageBytes.length);
				return new String(messageBytes, "UTF-8");
			}
			catch (JMSException e)
			{
				throw new RuntimeException(e);
			}
			catch (UnsupportedEncodingException e)
			{
				throw new RuntimeException(e);
			}
		}

		throw new IllegalArgumentException("����������� ��� JMS-��������� " + message.getClass());
	}

	public final void ejbRemove()
	{
		Iterator<String> iterator = startedModules.keySet().iterator();

		while (iterator.hasNext())
		{
			Module module = startedModules.get(iterator.next());
			ModuleLoader moduleLoader = module.getModuleLoader();
			if (moduleLoader != null)
				moduleLoader.stop();
			iterator.remove();
		}
	}

	private void startModule(Module module)
	{
		if (!startedModules.containsKey(module.getName()))
		{
			ModuleLoader moduleLoader = module.getModuleLoader();
			moduleLoader.start();
			startedModules.put(module.getName(), module);
		}
	}

}
