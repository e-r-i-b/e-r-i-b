package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��������� ������� jms �������� � ����
 */

public abstract class OfflineMessageProcessorBase extends MessageProcessorBase<OfflineMessageProcessor> implements OfflineMessageProcessor
{
	/**
	 * �����������
	 */
	protected OfflineMessageProcessorBase(ESBSegment segment)
	{
		super(segment);
	}

	protected final void addHeaders(TextMessage message) throws JMSException
	{
		super.addHeaders(message);
		setReplyTo(message, getSegment().getOfflineOutQueueName());
	}
}
