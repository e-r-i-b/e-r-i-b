package com.rssl.phizic.monitoring.fraud.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.monitoring.fraud.messages.jaxb.RequestBuilder;
import com.rssl.phizic.monitoring.fraud.messages.jaxb.generated.Request;
import com.rssl.phizic.monitoring.fraud.operations.BlockClientOperation;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.monitoring.fraud.Constants.NOT_FOUND_REQUEST_ERROR_MESSAGE;

/**
 * Обработчик полученных сообщений
 *
 * @author khudyakov
 * @ created 22.06.15
 * @ $Author$
 * @ $Revision$
 */
public class MessageProcessor
{
	private TextMessage message;

	public MessageProcessor(Message message)
	{
		this.message = (TextMessage) message;
	}

	/**
	 * Обработать сообщение
	 */
	public void process() throws BusinessException, BusinessLogicException
	{
		try
		{
			Request request = RequestBuilder.getInstance().append(message.getText()).build();
			if (request == null)
			{
				throw new BusinessException(NOT_FOUND_REQUEST_ERROR_MESSAGE);
			}

			BlockClientOperation operation = new BlockClientOperation(request);
			operation.block();
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
		catch (JMSException e)
		{
			throw new BusinessException(e);
		}
	}
}
