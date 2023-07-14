package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.gate.statistics.exception.GateExceptionStatisticHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор онлайн jms запросов в шину
 */

public abstract class OnlineMessageProcessorBase<Rs> extends MessageProcessorBase<OnlineMessageProcessor<Rs>> implements OnlineMessageProcessor<Rs>
{
	public static final Response EMPTY_RESPONSE = new Response<Object>(null, null, null, "-105", GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE);

	private final String serviceName;

	/**
	 * конструктор
	 * @param segment сегмент шины
	 * @param serviceName имя сервиса
	 */
	protected OnlineMessageProcessorBase(ESBSegment segment, String serviceName)
	{
		super(segment);
		this.serviceName = serviceName;
	}

	protected final void addHeaders(TextMessage message) throws JMSException
	{
		super.addHeaders(message);
		setReplyTo(message, getSegment().getOnlineOutQueueName());
	}

	protected abstract String getResponseId(Rs response);
	protected abstract String getResponseErrorCode(Rs response);
	protected abstract String getResponseErrorMessage(Rs response);

	private Response<Rs> processEmptyResponse()
	{
		//noinspection unchecked
		return EMPTY_RESPONSE;
	}

	public String getServiceName() throws GateException, GateLogicException
	{
		return serviceName;
	}

	public final String getResponseMessageSelector(Message message) throws GateException
	{
		try
		{
			String selector = "JMSCorrelationID = '" + message.getJMSMessageID() + "'";
			String groupId = getGroupId();
			if (StringHelper.isNotEmpty(groupId))
				selector += " and JMSXGroupID = '" + getGroupId() + "'";
			return selector;
		}
		catch (JMSException e)
		{
			throw new GateException("Ошибка получения идентификатора сообщения.", e);
		}
	}

	public final Response<Rs> buildResponse(Message message) throws GateException, GateLogicException
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
			Rs response = getSegment().getMessageParser().<Rs>parseMessage(source);
			return new Response<Rs>(getResponseId(response), body, response, getResponseErrorCode(response), getResponseErrorMessage(response));
		}
		catch (JMSException e)
		{
			throw new GateException("Ошибка разбора ответа.", e);
		}
	}

	protected abstract void processResponse(Request<OnlineMessageProcessor<Rs>> request, Response<Rs> response) throws GateException, GateLogicException;

	public final void processResponse(Response<Rs> response) throws GateLogicException, GateException
	{
		if (EMPTY_RESPONSE == response)
			processTimeout(getRequest(), response);

		processResponse(getRequest(), response);
	}

	protected final void processTimeout(Request request, Response<Rs> response) throws GateLogicException, GateException
	{
		String customErrorMessage = getCustomErrorMessage(request, response);
		throw new GateTimeOutException(StringHelper.isNotEmpty(customErrorMessage)? customErrorMessage: response.getErrorMessage());
	}

	protected final void processError(Request request, Response<Rs> response) throws GateLogicException, GateException
	{
		String customErrorMessage = getCustomErrorMessage(request, response);
		throw new GateLogicException(StringHelper.isNotEmpty(customErrorMessage)? customErrorMessage: response.getErrorMessage());
	}

	private String getCustomErrorMessage(Request request, Response<Rs> response)
	{
		String errorMessage = response.getErrorMessage();
		String errorCode = response.getErrorCode();
		ExternalExceptionInfo exceptionInfo = GateExceptionStatisticHelper.getBaseExceptionInfo(request.getSystem(), request.getMessageType(), errorCode, errorMessage);
		exceptionInfo.setDetail(response.getResponseBody());
		exceptionInfo.setGate(com.rssl.phizic.logging.messaging.System.esb);
		return GateExceptionStatisticHelper.getCustomErrorMessage(exceptionInfo);
	}

}
