package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.payment.CreateAutoPaymentTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * СМС-Команда подключения автоплатежа
 * @author Rtischeva
 * @ created 13.06.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentCommand extends CommandBase
{
	//номер телефона
	private PhoneNumber phoneNumber;

	//сумма пополнения для порогового АП
	private BigDecimal amount;

	//порог для порогового АП
	private BigDecimal threshold;

	//лимит для АП по выставленному счету
	private BigDecimal limit;

	//алиас карты списания
	private String cardAlias;

	private final ServiceProviderService serviceProviderService = new ServiceProviderService();

	@Override
	protected void doExecute()
	{
		BillingServiceProvider serviceProvider = findProvider();
		if (serviceProvider == null)
			throw new UserErrorException(messageBuilder.buildNotFoundCreateAutoPayProvider(phoneNumber));

		PersonBankrollManager bankrollManager = getPersonBankrollManager();
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		CreateAutoPaymentTask task = paymentManager.createAutoPaymentTask();

		task.setPhoneNumber(phoneNumber);
		task.setProvider(serviceProvider);

		//ищем приоритетную карту или карту по присланному алиасу
		BankrollProductLink link;
		if (cardAlias == null)
		{
			link = getPriorityCardLink();
		}
		else
		{
			//cardAlias не null еще не гарантирует, что клиент прислал именно алиас. Поэтому пробуем найти линк и продолжаем проверять дальше
			link = bankrollManager.findProductBySmsAlias(cardAlias, CardLink.class);
		}

		if (limit != null)
		{
			if (threshold != null)
			{
				//спорная ситуация, смотрим, смогли ли найти карту по присланному алиасу. Если нашли - АП по выставленному счету с лимитом. Если не нашли - пороговый АП.
				if (cardAlias != null)
				{
					if (link != null)
					{
						if (!link.getShowInSms())
							throw new UserErrorException(messageBuilder.buildAutoPayIncorrectCardMessage(phoneNumber));

						//АП по выставленному счету с лимитом
						checkLimitAutoPayParameters(serviceProvider);
						task.setLimit(limit);
					}
					else
					{
						link = getPriorityCardLink();

						//пороговый АП
						checkThresholdAutoPayParameters(serviceProvider);
						task.setAmount(amount);
						task.setThreshold(threshold);
					}
				}
			}
			else
			{
				//АП по выставленному счету с лимитом
				checkLimitAutoPayParameters(serviceProvider);
				task.setLimit(limit);
			}
		}
		else
		{
			//пороговый АП
			checkThresholdAutoPayParameters(serviceProvider);
			task.setAmount(amount);
			task.setThreshold(threshold);
		}

		if (link == null || !link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildAutoPayIncorrectCardMessage(phoneNumber));
		task.setCardLink(link);

		task.execute();
	}

	private BankrollProductLink getPriorityCardLink()
	{
		BankrollProductLink link = null;
		try
		{
			link = ErmbHelper.getPriorityCardLink();
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildAutoPayIncorrectCardMessage(phoneNumber));
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(messageBuilder.buildAutoPayIncorrectCardMessage(phoneNumber));
		}
		if (!link.getShowInSms())
			throw new UserErrorException(messageBuilder.buildAutoPayIncorrectCardMessage(phoneNumber));
		return link;
	}

	private BillingServiceProvider findProvider()
	{
		try
		{
			return serviceProviderService.getMobileOperatorByPhoneNumber(phoneNumber);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void checkLimitAutoPayParameters(BillingServiceProvider provider)
	{
		InvoiceAutoPayScheme invoiceAutoPayScheme = provider.getInvoiceAutoPayScheme();
		if (invoiceAutoPayScheme == null)
			throw new UserErrorException(messageBuilder.buildAutoPayCreateNotProvideInvoiceAutoPaySchemeMessage(phoneNumber));
	}

	private void checkThresholdAutoPayParameters(BillingServiceProvider provider)
	{
		ThresholdAutoPayScheme thresholdAutoPayScheme = provider.getThresholdAutoPayScheme();
		if (thresholdAutoPayScheme == null)
			throw new UserErrorException(messageBuilder.buildAutoPayCreateNotProvideThresholdAutoPaySchemeMessage(phoneNumber));

		checkThresholdAutoPayAmount(thresholdAutoPayScheme);
		checkThresholdAutoPayThreshold(thresholdAutoPayScheme);
	}

	private void checkThresholdAutoPayAmount(ThresholdAutoPayScheme thresholdAutoPayScheme)
	{
		BigDecimal minSum = thresholdAutoPayScheme.getMinSumThreshold();
		BigDecimal maxSum = thresholdAutoPayScheme.getMaxSumThreshold();
		if (amount.compareTo(minSum) < 0 || amount.compareTo(maxSum) > 0)
			throw new UserErrorException(messageBuilder.buildAutoPayIncorrectAmountMessage(minSum, maxSum));
	}

	private void checkThresholdAutoPayThreshold(ThresholdAutoPayScheme thresholdAutoPayScheme)
	{
		BigDecimal minThreshold = thresholdAutoPayScheme.getMinValueThreshold();
		BigDecimal maxThreshold = thresholdAutoPayScheme.getMaxValueThreshold();
		if (minThreshold != null && maxThreshold != null)
		{
			if(threshold.compareTo(minThreshold) < 0 || threshold.compareTo(maxThreshold) > 0)
				throw new UserErrorException(messageBuilder.buildAutoPayIncorrectThresholdMessage(phoneNumber, minThreshold, maxThreshold));
		}
		else
		{
			try
			{
				List<BigDecimal> discreteThresholdValues = thresholdAutoPayScheme.getAccessValuesAsList();
				if (!CollectionUtils.isEmpty(discreteThresholdValues))
				{
					for (BigDecimal discreteValue : discreteThresholdValues)
					{
						if (discreteValue.compareTo(threshold) == 0)
							return;
					}
					throw new UserErrorException(messageBuilder.buildAutoPayIncorrectThresholdMessage(phoneNumber, discreteThresholdValues));
				}
			}
			catch (ParseException e)
			{
				throw new InternalErrorException(e);
			}
		}
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void setThreshold(BigDecimal threshold)
	{
		this.threshold = threshold;
	}

	public void setLimit(BigDecimal limit)
	{
		this.limit = limit;
	}

	public void setCardAlias(String cardAlias)
	{
		this.cardAlias = cardAlias;
	}

	public String getCommandName()
	{
		return "CREATE_AUTO_PAYMENT";
	}
}
