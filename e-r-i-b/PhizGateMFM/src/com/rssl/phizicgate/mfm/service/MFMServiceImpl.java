package com.rssl.phizicgate.mfm.service;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.mfm.MFMService;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.mfm.PhizGateMFMConfig;
import com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.*;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author Nady
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class MFMServiceImpl extends AbstractService implements MFMService
{

	protected MFMServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void sendSMSWithoutIMSI(String phoneNumber, String smsText, Long priority) throws GateException
	{
		try
		{
			PhizGateMFMConfig config = ConfigFactory.getConfig(PhizGateMFMConfig.class);
			//Получаем сервис
			OutMessageConsumerServiceLocator outOfMessageConsumerLocator = new OutMessageConsumerServiceLocator();
			outOfMessageConsumerLocator.setOutMessageConsumerEndpointAddress(config.getUrl());
			OutMessageConsumer outMessageConsumer = outOfMessageConsumerLocator.getOutMessageConsumer();

			//заполняем запрос
			ConsumeOutMessageRequest request = new ConsumeOutMessageRequest();
			//Данные для авторизации
			Auth auth = new Auth(config.getLogin(), config.getPassword());

			//Сообщение
			OutMessage message = new OutMessage();
			message.setOutMessageId(new RandomGUID().toUUID().toString());
			message.setAddress(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber));
			message.setContent(smsText);
			message.setContentType("text");
			message.setPriority(config.getPriorityByCode(priority));

			OutMessage[] messages = {message};

			//Аргумент для вызова метода веб-сервиса
			ConsumeOutMessageArg arg = new ConsumeOutMessageArg(auth, messages);

			request.setConsumeOutMessageArg(arg);

			//выполняем запрос
			ConsumeOutMessageResult[] result = outMessageConsumer.consumeOutMessage(request);
			for (ConsumeOutMessageResult res : result)
			{
				if (res.getOutMessageId().equals(message.getOutMessageId()))
				{
					if (!res.getCode().equals("ok"))
						throw new GateException(res.getCode());
				}
			}
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
		catch (OutMessageConsumerFault outMessageConsumerFault)
		{
			throw new GateException(outMessageConsumerFault.getCode());
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}
}
