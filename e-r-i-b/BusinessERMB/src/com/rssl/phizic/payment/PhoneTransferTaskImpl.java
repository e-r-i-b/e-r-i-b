package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.task.TaskNotReadyException;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class PhoneTransferTaskImpl extends PaymentTaskBase implements PhoneTransferTask
{
	private String fromResourceAlias; //алиас карты списания
	private String fromResourceCode; //код карты списания
	private PhoneNumber phoneNumber; //номер телефона получателя
	private BigDecimal amount; //сумма перевода

	private transient CardLink chargeOffLink;//линк карты списания

	protected String getFormName()
	{
		return FormConstants.RUR_PAYMENT_FORM;
	}

	public void setFromResourceAlias(String fromResourceAlias)
	{
		this.fromResourceAlias = fromResourceAlias;
	}

	public String getFromResourceAlias()
	{
		return fromResourceAlias;
	}

	public void setFromResourceCode(String fromResourceCode)
	{
		this.fromResourceCode = fromResourceCode;
	}

	public String getFromResourceCode()
	{
		return fromResourceCode;
	}

	public void setPhone(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setChargeOffLink(CardLink chargeOffLink)
	{
		this.chargeOffLink = chargeOffLink;
	}

	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();
		if (fromResourceCode == null)
			throw new TaskNotReadyException("Не задан ресурс списания");
		if (phoneNumber == null)
			throw new TaskNotReadyException("Не задан телефон получателя");
		if (amount == null)
			throw new TaskNotReadyException("Не указана сумма перевода");
	}

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		// 0. Параметры платежа
		map.put(PaymentFieldKeys.RECEIVER_SUB_TYPE, RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE);
		map.put(PaymentFieldKeys.IS_OUR_BANK, Boolean.TRUE.toString());
		map.put("externalCard", Boolean.TRUE.toString());

		// 1. Параметры списания
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, fromResourceCode);
		map.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
		map.put(PaymentFieldKeys.FROM_ACCOUNT_SELECT, chargeOffLink.getNumber());

		// 2. Параметры зачисления
		map.put(PaymentFieldKeys.EXTERNAL_PHONE_NUMBER, PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber));

		// 3. Параметры суммы
		//сумма, вводимая клиентом, - это сумма списания
		map.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);
		map.put(PaymentFieldKeys.SELL_AMOUNT, amount.toPlainString());

		return new MapValuesSource(map);
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.PHONE_TRANSFER;
	}

	@Override
	protected void checkFormValidators(BusinessDocument document)
	{
		Money money = new Money(amount, chargeOffLink.getCurrency());
		if (!checkChargeOfProductAmount(chargeOffLink, money))
			throw new UserErrorException(messageBuilder.buildNotEnoughMoneyError(document));
	}
}
