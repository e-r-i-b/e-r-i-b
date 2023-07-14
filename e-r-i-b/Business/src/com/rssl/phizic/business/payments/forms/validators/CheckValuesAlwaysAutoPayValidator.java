package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;

import java.util.Map;
import java.math.BigDecimal;

/**
 * Проверяет входит ли значение суммы регулярного автоплатежа в дипазон значений,
 * введенный в админке
 * @author niculichev
 * @ created 26.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckValuesAlwaysAutoPayValidator extends MultiFieldsValidatorBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final String MESSAGE = "Сумма автоплатежа не может быть меньше %s рублей, но она не должна превышать %s рублей. Пожалуйста, укажите другую сумму.";
	private static final String RICIPIENT = "recipient";
	private static final String SELL_AMOUNT = "sellAmount";
	private static ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long recipient = (Long) retrieveFieldValue(RICIPIENT, values);
		BigDecimal sellAmount = (BigDecimal) retrieveFieldValue(SELL_AMOUNT, values);

		try
		{
			ServiceProviderBase providerBase = providerService.findById(recipient);
			if(!(providerBase instanceof BillingServiceProvider))
			    throw new TemporalDocumentException("Получатель с идентификатором "+ recipient + "н е является поставщиком услуг");

			BillingServiceProvider billingProvider = (BillingServiceProvider) providerBase;
			if (!AutoPaymentHelper.checkAutoPaymentSupport(billingProvider))
			{
				throw new TemporalDocumentException("Получатель с идентификатором "+ recipient + " не поддеживает автоплатежи");
			}
			AlwaysAutoPayScheme autoPayScheme = billingProvider.getAlwaysAutoPayScheme();
			if (autoPayScheme == null)
			{
				throw new TemporalDocumentException("Получатель с идентификатором "+ recipient + " не поддеживает автоплатежи регулярного типа");
			}

			BigDecimal alwaysMaxSum = autoPayScheme.getMaxSumAlways();
			BigDecimal alwaysMinSum = autoPayScheme.getMinSumAlways();
			if ((alwaysMaxSum != null && alwaysMaxSum.compareTo(sellAmount) < 0) || (alwaysMinSum != null && alwaysMinSum.compareTo(sellAmount) > 0))
			{
				setMessage(String.format(MESSAGE, autoPayScheme.getMinSumAlways(), autoPayScheme.getMaxSumAlways()));
				return false;
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("Ошибка получения поставщика с идентификатором " + recipient, e);
		}

		return true;
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}
}
