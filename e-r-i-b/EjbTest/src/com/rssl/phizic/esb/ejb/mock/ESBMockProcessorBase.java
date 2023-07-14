package com.rssl.phizic.esb.ejb.mock;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.documents.mapping.ServiceMapping;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.MessageCreator;
import com.rssl.phizic.utils.jms.TextMessageCreator;
import com.rssl.phizic.utils.jms.TextMessageWithUserHeadersCreator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 26.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый класс заглушки, имитирующей работу шины
 */

public abstract class ESBMockProcessorBase<Rq, Rs> extends MessageProcessorBase<ESBMessage<Rq>>
{
	private static final String GROUP_ID_PROPERTY_KEY = "JMSXGroupID";

	private static final Random RANDOM = new Random();
	private final AtomicLong index = new AtomicLong(0);

	protected ESBMockProcessorBase(Module module)
	{
		super(module);
	}

	protected final long getRandomLong()
	{
		return RANDOM.nextLong();
	}

	protected final boolean getRandomBoolean()
	{
		return RANDOM.nextBoolean();
	}

	protected final String getRandomString(int length)
	{
		return RandomHelper.rand(length, RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS);
	}

	protected final long getRequestIndex()
	{
		return index.get();
	}

	protected abstract boolean needSendResult(ESBMessage<Rq> xmlRequest, Rs message);

	protected abstract boolean needSendOnline(ESBMessage<Rq> xmlRequest, Rs message);

	protected abstract void process(ESBMessage<Rq> xmlRequest);

	@Override
	protected final void doProcessMessage(ESBMessage<Rq> xmlRequest)
	{
		index.incrementAndGet();
		process(xmlRequest);
	}

	protected final MessageCreator<TextMessage> getMessageCreator(ESBMessage<Rq> xmlRequest)
	{
		String groupId = xmlRequest.getProperty(GROUP_ID_PROPERTY_KEY);
		if (StringHelper.isEmpty(groupId))
			return TextMessageCreator.getInstance();

		//noinspection unchecked
		return TextMessageWithUserHeadersCreator.getInstance(new Pair<String, String>(GROUP_ID_PROPERTY_KEY, groupId));
	}

	protected final void send(ESBMessage<Rq> xmlRequest, Rs message, ESBSegment segment, String service)
	{
		if (!needSendResult(xmlRequest, message))
			return;

		MessageCreator<TextMessage> messageCreator = getMessageCreator(xmlRequest);

		if (needSendOnline(xmlRequest, message))
		{
			sendToSyncQueue(message, segment, messageCreator, xmlRequest.getMessageId());
			return;
		}

		try
		{
			Thread.sleep(ServiceMapping.getConfig().getMQIntegrationTimeout(service) + 5 * DateHelper.MILLISECONDS_IN_SECOND);
		}
		catch (InterruptedException e)
		{
			throw new InternalErrorException("Не смогли уснуть.", e);
		}
		sendToAsyncQueue(message, segment, messageCreator, xmlRequest.getMessageId());
	}

	protected final void sendToSyncQueue(Rs message, ESBSegment segment, MessageCreator<TextMessage> creator, String jmsCorrelationId)
	{
		try
		{
			String messageText = getMessage(message, segment);
			MockJMSTransportProvider.getInstance(segment).sendToSync(messageText, creator, jmsCorrelationId);
		}
		catch (Exception e)
		{
			log.error("[ESB-MQ MOCK] Ошибка отправки ответного сообщения.", e);
		}
	}

	protected final void sendToAsyncQueue(Rs message, ESBSegment segment, MessageCreator<TextMessage> creator, String jmsCorrelationId)
	{
		try
		{
			String messageText = getMessage(message, segment);
			MockJMSTransportProvider.getInstance(segment).sendToAsync(messageText, creator, jmsCorrelationId);
		}
		catch (Exception e)
		{
			log.error("[ESB-MQ MOCK] Ошибка отправки ответного сообщения.", e);
		}
	}

	private String getMessage(Rs message, ESBSegment segment)
	{
		try
		{
			return segment.getMessageBuilder().buildMessage(message);
		}
		catch (GateException e)
		{
			throw new InternalErrorException("Ошибка преобразования объекта к строке.", e);
		}
	}

	@Override
	public final boolean writeToLog()
	{
		return false;
	}
}
