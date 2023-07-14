package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация платежной задачи "Оплата телефона"
 * @author Rtischeva
 * @created 03.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RechargePhoneTaskImpl extends PaymentTaskBase implements RechargePhoneTask
{
	private BigDecimal amount; //сумма списания

	private String amountExternalId; //код поля главной суммы во внешней системе

	private String fromResourceCode; //код карты списания

	private String phoneNumber; //номер телефона, кому оплачиваем

	private boolean notNeedConfirm; //нужно ли подтверждать

	private Long providerKey; //код провайдера (айдишник)

	private String rechargePhoneExternalId; //название дополнительного поля провайдера для номера телефона

	private String operatorName; //название оператора

	@Override
	protected String getFormName()
	{
		return FormConstants.SERVICE_PAYMENT_FORM;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, fromResourceCode);
		map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
		map.put(amountExternalId, amount.toPlainString());
		map.put("fromResourceCurrency", "RUB");
		map.put(PaymentFieldKeys.PROVIDER_KEY, String.valueOf(providerKey));
		map.put(rechargePhoneExternalId, phoneNumber);

		return new MapValuesSource(map);
	}

	@Override
	protected EditServicePaymentOperation createEditOperation()
	{
		try
		{
			String serviceKey = documentSource.getMetadata().getName();
			EditServicePaymentOperation operation = createOperation(EditServicePaymentOperation.class, serviceKey);
			operation.initialize(documentSource, providerKey);
			setAdditionalDocumentInfo();
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
	protected boolean needSendMBOperCode(BusinessDocument document)
	{
		if (!ConfigFactory.getConfig(ErmbConfig.class).needSendPaymentSmsNotification())
			return false;
		return true;
	}

	public void setAmountExternalId(String amountExternalId)
	{
		this.amountExternalId = amountExternalId;
	}

	public void setFromResourceCode(String fromResourceCode)
	{
		this.fromResourceCode = fromResourceCode;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setProviderKey(Long providerKey)
	{
		this.providerKey = providerKey;
	}

	@Override
	protected boolean needConfirm()
	{
		return !notNeedConfirm;
	}

	public void setNotNeedConfirm(boolean notNeedConfirm)
	{
		this.notNeedConfirm = notNeedConfirm;
	}

	public void setRechargePhoneExternalId(String fieldName)
	{
		this.rechargePhoneExternalId = fieldName;
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.RECHARGE_PHONE;
	}

	private void setAdditionalDocumentInfo()
	{
		BusinessDocument document = documentSource.getDocument();
		document.setRechargePhoneNumber(phoneNumber);
		document.setMobileOperatorName(operatorName);
	}

	@Override
	protected boolean needCheckCardBeforeConfirm(BusinessDocument document)
	{
		return true;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
}
