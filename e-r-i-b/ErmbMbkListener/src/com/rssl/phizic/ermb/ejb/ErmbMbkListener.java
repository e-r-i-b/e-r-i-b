package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.*;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.MbkResultSender;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.Serializable;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Слушатель сообщений из МБК (через прокси)
 * @author Puzikov
 * @ created 07.07.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbMbkListener implements MessageDrivenBean, MessageListener
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final MbkMessageProcessor newRegistrationMbkMessageProcessor = new NewRegistrationMbkMessageProcessor();

	private final MbkMessageProcessor removeRegistrationMbkMessageProcessor = new RemoveRegistrationMbkMessageProcessor();

	private final MbkMessageProcessor newTemplateMbkMessageProcessor = new NewTemplateMbkMessageProcessor();

	private final MbkMessageProcessor removeTemplateMbkMessageProcessor = new RemoveTemplateMbkMessageProcessor();

	private final MbkResultSender sender = new MbkResultSender();

	private final ProxyMbkMessageLogger logger = new ProxyMbkMessageLogger();

	///////////////////////////////////////////////////////////////////////////

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext)
	{
	}

	public void ejbRemove()
	{
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.ErmbMbkListener);
		Long messageId = null;
		try
		{
			MBKRegistration mbkMessage = getMessage(message);
			messageId = mbkMessage.getId();

			logger.write(mbkMessage);

			MbkMessageProcessor processor = getProcessor(mbkMessage);

			ProcessResult result = processor.process(mbkMessage);

			sender.send(result);
		}
		catch (Exception e)
		{
			log.error("Не удалось обработать JMS сообщение. id=" + messageId, e);
			if (messageId != null)
				sender.send(new ProcessResult(messageId, MBKRegistrationResultCode.ERROR_NOT_REG, "Техническая ошибка обработки связки"));
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private MBKRegistration getMessage(Message message) throws JMSException
	{
		if (!(message instanceof ObjectMessage))
			throw new IllegalArgumentException("Ожидается объектное JMS-сообщение");

		Serializable object = ((ObjectMessage) message).getObject();
		if (!(object instanceof MBKRegistration))
			throw new IllegalArgumentException("Неверный тип сообщения");

		return (MBKRegistration) object;
	}

	private MbkMessageProcessor getProcessor(MBKRegistration mbkMessage)
	{
		switch (mbkMessage.getRegTranType())
		{
			case MOBI:
				switch (mbkMessage.getRegAction())
				{
					case C:
						return newRegistrationMbkMessageProcessor;

					case D:
						return removeRegistrationMbkMessageProcessor;
				}
				break;

			case MOPS:
				switch (mbkMessage.getRegAction())
				{
					case C:
						return newTemplateMbkMessageProcessor;

					case D:
						return removeTemplateMbkMessageProcessor;
				}
				break;
		}

		throw new UnsupportedOperationException("Неожиданный тип связки (RegAction + RegTranType) " + mbkMessage);
	}
}
