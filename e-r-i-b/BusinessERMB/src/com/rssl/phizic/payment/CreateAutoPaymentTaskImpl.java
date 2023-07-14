package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация платежной задачи "Подключение автоплатежа"
 * @author Rtischeva
 * @ created 13.06.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentTaskImpl extends PaymentTaskBase implements CreateAutoPaymentTask
{
	private PhoneNumber phoneNumber;

	private BigDecimal amount;

	private BigDecimal threshold;

	private BigDecimal limit;

	private transient BillingServiceProvider provider;

	private transient BankrollProductLink cardLink;

	protected String getFormName()
	{
		return FormConstants.CREATE_AUTOPAYMENT_FORM;
	}

	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.CREATE_AUTOPAYMENT;
	}

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, cardLink.getCode());
		map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
		map.put(PaymentFieldKeys.PROVIDER_KEY, String.valueOf(provider.getId()));
		map.put(PaymentFieldKeys.RECEIVER_SERVICE_CODE, provider.getCodeService());
		map.put("requisite", PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber));

		if (limit != null)
		{
			map.put(PaymentFieldKeys.AUTO_PAYMENT_TYPE, ExecutionEventType.BY_INVOICE.name());
			map.put(PaymentFieldKeys.AUTO_PAYMENT_FLOOR_LIMIT, limit.toPlainString());
		}
		else
		{
			map.put(PaymentFieldKeys.AUTO_PAYMENT_TYPE, ExecutionEventType.REDUSE_OF_BALANCE.name());
			map.put(PaymentFieldKeys.AUTO_PAYMENT_FLOOR_LIMIT , threshold.toPlainString());

			try
			{
				map.put(BillingPaymentHelper.getMainSumFieldDescription(provider).getExternalId(), amount.toPlainString());
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}
		}
		return new MapValuesSource(map);
	}

	@Override
	protected EditDocumentOperation createEditOperation()
	{
		try
		{
			EditDocumentOperation operation = createOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
			operation.initialize(documentSource, requestFieldValuesSource);
			return operation;
		}
		catch (TemporalBusinessException e)
		{
			String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
			throw new UserErrorException(new TextMessage(exceptionMessage), e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	@Override
	protected void saveNewPayment(Map<String, Object> formData)
	{
		try
		{
			editOperation.makeLongOffer(formData);
			documentId = editOperation.getDocument().getId();
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	@Override
	protected boolean needConfirm()
	{
		return false;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
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

	public void setCardLink(BankrollProductLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public void setProvider(BillingServiceProvider provider)
	{
		this.provider = provider;
	}
}
