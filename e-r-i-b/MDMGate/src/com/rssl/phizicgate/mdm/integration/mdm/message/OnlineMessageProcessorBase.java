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
 * ������� ��������� ������ jms �������� � ����
 */

public abstract class OnlineMessageProcessorBase<Rs> extends MessageProcessorBase<OnlineMessageProcessor<Rs>> implements OnlineMessageProcessor<Rs>
{
	public static final Response EMPTY_RESPONSE = new Response<Object>(null, null, null);

	protected final void addHeaders(TextMessage message) throws JMSException
	{
		setReplyTo(message, MDMSegmentInfo.OUT_ONLINE_QUEUE_NAME);
	}

	/**
	 * �������� ������������� ������ (��� �����������)
	 * @param response �����
	 * @return �������������
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
			throw new GateException("������ ��������� �������������� ���������.", e);
		}
	}

	public final Response<Rs> buildResponse(Message message) throws GateException
	{
		if (message == null)
			return processEmptyResponse();

		if (!(message instanceof TextMessage))
			throw new GateException("����������� ��� ���������.");

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
			throw new GateException("������ ������� ������.", e);
		}
	}

	/**
	 * ���������� �����
	 * @param request ������
	 * @param response �����
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
		throw new GateException("������� �������� ������ �� ������� ������� ��� [MessageId=" + request.getMessageId() + ", MessageType=" + request.getMessageType() + "]");
	}

	protected final void processError(Request request, Response<Rs> response, Status status) throws GateException
	{
		throw new GateException("������ � ������ �� ������� ������� ��� " +
				"[" +
					"MessageType="        + request.getMessageType() + ", " +
					"RequestMessageId="   + request.getMessageId()   + ", " +
					"ResponseMessageId="  + response.getMessageId()  + ", " +
					"ResponseStatusCode=" + status.getStatusCode()   + ", " +
					"ResponseStatusDesc=" + status.getStatusDesc()   +
				"]");
	}
}
