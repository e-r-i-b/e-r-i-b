package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.payment.ServicePaymentTask;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.List;

/**
 * Смс-команда "оплата услуг"
 * @author Rtischeva
 * @created 19.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ServicePaymentCommand extends CommandBase
{
	/**
	 * реквизиты для оплаты поставщику
	 */
	protected List<String> requisites;

	/**
	 * смс-алиас поставщика услуг
	 */
	private transient ServiceProviderSmsAlias serviceProviderSmsAlias;

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		ServicePaymentTask task = paymentManager.createServicePaymentTask();
		task.setServiceProviderSmsAlias(serviceProviderSmsAlias);
		task.setRequisites(requisites);
		task.execute();
	}

	@Override
	protected boolean needResetIncompletePayment()
	{
		Calendar previousSmsTime = ErmbSmsContext.getPreviousSmsTime();
		if (previousSmsTime == null)
			return true;

		//оформление платежа завершается автоматически, если клиент в течение заданного периода задержки не отправит в банк запрос (команду) продолжающую оформление платежа.
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		int expireInterval = smsConfig.getIncompletePaymentsExpireInterval();
		Calendar lifetime = DateHelper.addSeconds(ErmbSmsContext.getCurrentSmsTime(), -expireInterval);
		return previousSmsTime.before(lifetime);
	}

	public List<String> getRequisites()
	{
		return requisites;
	}

	public void setRequisites(List<String> requisites)
	{
		this.requisites = requisites;
	}

	public ServiceProviderSmsAlias getServiceProviderSmsAlias()
	{
		return serviceProviderSmsAlias;
	}

	public void setServiceProviderSmsAlias(ServiceProviderSmsAlias serviceProviderSmsAlias)
	{
		this.serviceProviderSmsAlias = serviceProviderSmsAlias;
	}

	public String getCommandName()
	{
		return "SERVICE_PAYMENT";
	}
}
