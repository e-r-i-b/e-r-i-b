package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.task.TaskNotReadyException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация платёжной задачи "Перевод по номеру карты"
 */
class CardTransferTaskImpl extends PaymentTaskBase implements CardTransferTask
{
	private String senderProductCode;

	private String receiverCardNumber;

	/**
	 * Сумма списания
	 */
	private BigDecimal amount;

	/**
	 * Буквенный ISO-код валюты суммы списания
	 */
	private String amountCurrencyCode;

	/**
	 * линк списания
	 */
	private transient BankrollProductLink chargeOffLink;

	///////////////////////////////////////////////////////////////////////////

	protected String getFormName()
	{
		return FormConstants.RUR_PAYMENT_FORM;
	}

	public void setSenderProduct(BankrollProductLink senderProduct)
	{
		this.chargeOffLink = senderProduct;
		this.senderProductCode = senderProduct.getCode();
	}

	public void setReceiverCardNumber(String receiverCardNumber)
	{
		this.receiverCardNumber = receiverCardNumber;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount.getDecimal();
		this.amountCurrencyCode = amount.getCurrency().getCode();
	}

	protected void checkParameters() throws TaskNotReadyException
	{
		super.checkParameters();

		if (StringHelper.isEmpty(senderProductCode))
			throw new TaskNotReadyException("Не задан источник списания");

		if (StringHelper.isEmpty(receiverCardNumber))
			throw new TaskNotReadyException("Не задан приёмник зачисления");

		if (amount == null)
			throw new TaskNotReadyException("Не указана сумма перевода");
	}

	///////////////////////////////////////////////////////////////////////////

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		String receiverSubType = getReceiverSubType(receiverCardNumber);
		boolean isOurCard = receiverSubType.equals(RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE);

		// 1. Параметры платежа
		// Оплата физику (а не юрику)
		map.put(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME, Constants.PHIZ_RECEIVER_TYPE_ATTRIBUTE_VALUE);
		map.put(PaymentFieldKeys.RECEIVER_SUB_TYPE, receiverSubType);
		map.put(PaymentFieldKeys.IS_OUR_BANK, Boolean.toString(isOurCard));
		map.put(PaymentFieldKeys.IS_CARD_TRANSFER, "true");

		// 2. Параметры списания
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, senderProductCode);

		// 3. Параметры зачисления
		map.put(PaymentFieldKeys.EXTERNAL_CARD_NUMBER, receiverCardNumber);

		// 4. Параметры суммы
		// сумма, вводимая клиентом, - это сумма списания
		map.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);
		map.put(PaymentFieldKeys.SELL_AMOUNT, amount.toPlainString());
		map.put(PaymentFieldKeys.BUY_AMOUNT, amount.toPlainString());
		map.put(PaymentFieldKeys.BUY_AMOUNT_CURRENCY, amountCurrencyCode);

		return new MapValuesSource(map);
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.CARD_TRANSFER;
	}

	private String getReceiverSubType(String cardNumber)
	{
		if (isOurCard(cardNumber))
			return RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE;

		if (cardNumber.startsWith("4"))
			return RurPayment.VISA_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;

		if (cardNumber.startsWith("5"))
			return RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;

		if (cardNumber.startsWith("6"))
			return RurPayment.MASTERCARD_EXTERNAL_CARD_TRANSFER_TYPE_VALUE;

		throw new UserErrorException(messageBuilder.buildCardTransferBadReceiverError());
	}

	private boolean isOurCard(String cardNumber)
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = ((ActivePerson)getPerson()).asClient();

			Pair<String, Office> stringOfficePair = new Pair<String, Office>(cardNumber, null);
			// noinspection unchecked
			GroupResult<Pair<String,Office>,Card> gresult = bankrollService.getCardByNumber(client, stringOfficePair);
			return GroupResultHelper.getOneResult(gresult) != null;
		}
		catch (LogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (SystemException e)
		{
			throw new InternalErrorException(e);
		}
	}

	@Override
	protected void checkFormValidators(BusinessDocument document)
	{
		Money money = new Money(amount, chargeOffLink.getCurrency());
		if (!checkChargeOfProductAmount(chargeOffLink, money))
			throw new UserErrorException(messageBuilder.buildNotEnoughMoneyError(document));
	}
}
