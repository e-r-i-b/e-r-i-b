package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;

import java.math.BigDecimal;
import java.util.Map;

/**
 * ��������� ��������� ������ �� �������� ����� � ������ ��� �������� �����������
 * � ��������, ��������� � ����������.
 * @author niculichev
 * @ created 26.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckValuesThresholdAutoPayValidator extends MultiFieldsValidatorBase
{
	private static final ServiceProviderService  providerService = new ServiceProviderService();
	private static final String MESSAGE_SUM = "����� ����������� �� ����� ���� ������ %s ������, �� ��� �� ������ ��������� %s ������. ����������, ������� ������ �����.";
	private static final String MESSAGE_VAL = "����������� ������ ����� �� ����� ���� ������ %s ������, �� �� �� ������ ��������� %s ������. ����������, ������� ������ �����.";
	private static final String TOTAL_SUM_SELL_AMOUNT = "������������ ����� �� ����� ���� ������ ����� ����������� (������ �����������). ����������, ������� ������ ��������.";
	private static final String TOTAL_SUM_MAX_TOTAL_SUM = "������������ ����� �������� �� ������ �� ����� ���� ������ %s";
	private static final String RICIPIENT = "recipient";
	private static final String SELL_AMOUNT = "sellAmount";
	private static final String FLOOR_LIMIT = "floorLimit";
	private static final String TOTAL_AMOUNT_LIMIT = "totalAmountLimit";
	private static ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long recipient = (Long) retrieveFieldValue(RICIPIENT, values);
		BigDecimal sellAmount = (BigDecimal)retrieveFieldValue(SELL_AMOUNT, values);
		BigDecimal floorLimit = (BigDecimal)retrieveFieldValue(FLOOR_LIMIT, values);
		BigDecimal totalAmountLimit = (BigDecimal)retrieveFieldValue(TOTAL_AMOUNT_LIMIT, values);
		try
		{
			ServiceProviderBase providerBase = providerService.findById(recipient);
			if(!(providerBase instanceof BillingServiceProvider))
			    throw new TemporalDocumentException("���������� � ��������������� "+ recipient + " �� �������� ����������� �����");
			
			BillingServiceProvider billingProvider = (BillingServiceProvider) providerBase;
			if (!AutoPaymentHelper.checkAutoPaymentSupport(billingProvider))
			{
				throw new TemporalDocumentException("���������� � ��������������� "+ recipient + " �� ����������� �����������");
			}
			ThresholdAutoPayScheme autoPayScheme = billingProvider.getThresholdAutoPayScheme();

			if (autoPayScheme == null)
			{
				throw new TemporalDocumentException("���������� � ��������������� "+ recipient + " �� ����������� ����������� ���������� ����");
			}

			if(autoPayScheme.getMaxSumThreshold().compareTo(sellAmount) < 0 || autoPayScheme.getMinSumThreshold().compareTo(sellAmount) >0)
			{
				setMessage(String.format(MESSAGE_SUM, autoPayScheme.getMinSumThreshold(), autoPayScheme.getMaxSumThreshold()));
				return false;
			}

			if(autoPayScheme.isInterval() && (autoPayScheme.getMaxValueThreshold().compareTo(floorLimit) < 0 || autoPayScheme.getMinValueThreshold().compareTo(floorLimit) >0))
			{
				setMessage(String.format(MESSAGE_VAL, autoPayScheme.getMinValueThreshold(), autoPayScheme.getMaxValueThreshold()));
				return false;
			}

			if(autoPayScheme.isAccessTotalMaxSum() && totalAmountLimit != null)
			{
				BigDecimal limitValue = autoPayScheme.getTotalMaxSum();

				if(totalAmountLimit.compareTo(sellAmount) < 0)
				{
					setMessage(TOTAL_SUM_SELL_AMOUNT);
					return false;
				}

				if(limitValue != null && limitValue.compareTo(totalAmountLimit) < 0)
				{
					setMessage(String.format(TOTAL_SUM_MAX_TOTAL_SUM, limitValue));
					return false;
				}
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("������ ��������� ���������� � ��������������� " + recipient, e);
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
