package com.rssl.phizic.ermb.transport;

import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.business.ermb.sms.messaging.imsi.CheckIMSIState;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ermb.*;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.CheckImsiResponse;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.jms.JmsService;

import java.util.Calendar;
import java.util.Random;
import javax.xml.bind.JAXBException;

import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.ERMB_QUEUE;
import static com.rssl.phizic.messaging.ermb.ErmbJndiConstants.ERMB_QCF;

/**
 * @author EgorovaA
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заглушка для приема транспортных сообщений
 */
public class TransportSmsProcessor extends MessageProcessorBase<TestEjbMessage>
{
	private final Random random = new Random();

	private final JmsService jmsService = new JmsService();

	private final TransportMessageSerializer requestSerializer = new TransportMessageSerializer();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль
	 */
	public TransportSmsProcessor(Module module)
	{
		super(module);
	}

	@Override protected void doProcessMessage(TestEjbMessage xmlRequest)
	{
		try
		{
			// A. Запрос без проверки IMSI
			if (xmlRequest.getSendSmsRequest() != null)
			{
				SendSmsRequest sendSmsRequest = xmlRequest.getSendSmsRequest();
				log.info("Получено смс-сообщение: " + sendSmsRequest.getText());
				return;
			}

			// B. Запрос с проверкой IMSI
			if (xmlRequest.getSendSmsWithImsiRequest() != null)
			{
				SendSmsWithImsiRequest sendSmsWithImsiRequest = xmlRequest.getSendSmsWithImsiRequest();
				log.info("Получено смс-сообщение с проверкой IMSI: " + sendSmsWithImsiRequest.getText());

				// 2. Отсылаем сообщение с результатами проверки IMSI (с высокой вероятностью)
				if (getRandomBoolean())
				{
					//отправляем результат проверки IMSI
					String messageText = buildMessageXml(sendSmsWithImsiRequest);
					jmsService.sendMessageToQueue(messageText, ERMB_QUEUE, ERMB_QCF, null, null);
				}
				else
					log.error("Не удалось отправить результат проверки IMSI");

				return;
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private String buildMessageXml(SendSmsWithImsiRequest request)
	{
		try
		{
			CheckImsiResponse response = new CheckImsiResponse();
			response.setRqVersion(CheckImsiResponse.VERSION);
			response.setRqUID(new RandomGUID().toUUID());
			response.setRqTime(Calendar.getInstance());
			response.setPhone(request.getPhone());
			response.setResultCode(generateResultCode());
			response.setCorrelationID(request.getRqUID());

			return requestSerializer.marshallCheckIMSIResponse(response);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException("Сбой на упаковке в XML", e);
		}
	}

	private boolean getRandomBoolean()
	{
		int i = random.nextInt(10);
		if (2 >= i && i > 0)
			return false;
		return true;
	}

	/**
	 * Сгенерировать статус ответа проверки
	 * @return
	 */
	private String generateResultCode()
	{
		int i = random.nextInt(10);
		if (2 >= i && i > 0)
			return CheckIMSIState.ERROR.getType();
		return CheckIMSIState.OK.getType();
	}

	@Override
	public boolean writeToLog()
	{
		// в заглушке логировать не надо
		return false;
	}
}
