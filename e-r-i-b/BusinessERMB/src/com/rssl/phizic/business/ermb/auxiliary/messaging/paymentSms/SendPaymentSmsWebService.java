package com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.utils.RandomGUID;

import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.rpc.ServiceException;

/**
 * Класс - обертка для вызова веб-сервиса, отправляющего оповещение об успешном исполнении платежа в СОС
 * @author Rtischeva
 * @created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public class SendPaymentSmsWebService
{
	public SendPaymentSmsRs_Type sendPaymentSms(String mbOperCode, String text) throws BusinessException
	{
		SendPaymentSmsRq_Type request = new SendPaymentSmsRq_Type(new RandomGUID().getStringValue(), Calendar.getInstance(), mbOperCode, text);

		SendPaymentSmsServiceBindingStub sendSmsService = getStub();
		return invokeSendSms(sendSmsService, request);
	}

	private SendPaymentSmsRs_Type invokeSendSms(SendPaymentSmsServiceBindingStub stub, SendPaymentSmsRq_Type request) throws BusinessException
	{
		try
		{
			return stub.sendPaymentSms(request);
		}
		catch (RemoteException e)
		{
			throw new BusinessException("Ошибка вызова веб-сервиса отправки оповещений об исполнении платежа", e);
		}
	}

	private SendPaymentSmsServiceBindingStub getStub() throws BusinessException
	{
		try
		{
			SendPaymentSmsService_ServiceLocator locator = new SendPaymentSmsService_ServiceLocator();
			locator.setSendPaymentSmsServicePortEndpointAddress(ConfigFactory.getConfig(ErmbConfig.class).getPaymentSmsWServiceUrl());
			return (SendPaymentSmsServiceBindingStub) locator.getSendPaymentSmsServicePort();
		}
		catch (ServiceException e)
		{
			throw new BusinessException(e);
		}
	}
}
