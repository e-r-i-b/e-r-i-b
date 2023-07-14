package com.rssl.phizicgate.mdm.integration.mdm.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.integration.mdm.MDMSegmentInfo;
import com.rssl.phizicgate.mdm.integration.mdm.generated.Status;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор онлайн jms запросов в шину
 */

public abstract class OnlineMessageProcessorBase<Rs> extends MessageProcessorBase<OnlineMessageProcessor<Rs>> implements OnlineMessageProcessor<Rs>
{
	public static final Response EMPTY_RESPONSE = new Response<Object>(null, null, null);

	protected final void addHeaders(TextMessage message) throws JMSException
	{
		setReplyTo(message, MDMSegmentInfo.OUT_ONLINE_QUEUE_NAME);
	}

	/**
	 * получить идентификатор ответа (для логирования)
	 * @param response ответ
	 * @return идентификатор
	 */
	protected abstract String getResponseId(Rs response);

	private Response<Rs> processEmptyResponse()
	{
		//noinspection unchecked
		return EMPTY_RESPONSE;
	}

	public final String getResponseMessageSelector(Message message) throws GateException
	{
		try
		{
			return "JMSCorrelationID = '" + message.getJMSMessageID() + "'";
		}
		catch (JMSException e)
		{
			throw new GateException("Ошибка получения идентификатора сообщения.", e);
		}
	}

	public final Response<Rs> buildResponse(Message message) throws GateException
	{
		if (message == null)
			return processEmptyResponse();

		if (!(message instanceof TextMessage))
			throw new GateException("Неожиданный тип сообщения.");

		try
		{
			TextMessage source = (TextMessage) message;
			String body = source.getText();
			//noinspection RedundantTypeArguments
			Rs response = JAXBMessageHelper.getInstance().<Rs>parseMessage(source);
			return new Response<Rs>(getResponseId(response), body, response);
		}
		catch (JMSException e)
		{
			throw new GateException("Ошибка разбора ответа.", e);
		}
	}

	/**
	 * обработать ответ
	 * @param request запрос
	 * @param response ответ
	 * @throws GateException
	 */
	protected abstract void processResponse(Request<OnlineMessageProcessor<Rs>> request, Response<Rs> response) throws GateException;

	public final void processResponse(Response<Rs> response) throws GateLogicException, GateException
	{
		if (EMPTY_RESPONSE == response)
			processTimeout(getRequest());

		processResponse(getRequest(), response);
	}

	protected final void processTimeout(Request request) throws GateException
	{
		throw new GateException("Таймаут ожидания ответа от внешней системы МДМ [MessageId=" + request.getMessageId() + ", MessageType=" + request.getMessageType() + "]");
	}

	protected final void processError(Request request, Response<Rs> response, Status status) throws GateException
	{
		throw new GateException("Ошибка в ответе от внешней системы МДМ " +
				"[" +
					"MessageType="        + request.getMessageType() + ", " +
					"RequestMessageId="   + request.getMessageId()   + ", " +
					"ResponseMessageId="  + response.getMessageId()  + ", " +
					"ResponseStatusCode=" + status.getStatusCode()   + ", " +
					"ResponseStatusDesc=" + status.getStatusDesc()   +
				"]");
	}
}
