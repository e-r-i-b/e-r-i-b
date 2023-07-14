package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Сендер для оплаты ITunes
 * @author niculichev
 * @ created 11.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ITunesPaymentSender extends SimplePaymentSender
{
	private static final String AGREEMENT_FIELD_NAME = "Я согласен с [url]условиями[/url] покупки пин-кода ITunes";
	private static final String AGREEMENT_DESC_ID = "itunes-agreement";
	private static final String AGREEMENT_MESSAGE = "Для того чтобы совершить оплату, ознакомьтесь с условиями предоставления услуг iTunes, щелкнув по ссылке «условия». Если Вы согласны с условиями, установите флажок в поле «Я согласен с условиями покупки пин-кода iTunes».";

	private static final String PHONE_NUMBER_FIELD_NAME = "Номер телефона для отправки пин-кода активации карты ITunes";
	private static final String PHONE_NUMBER_DESC = "Введите 10-значный Номер телефона для получения пин-кода карты iTunes.";
	private static final String PHONE_NUMBER_REGEXP_VALUE = "^[0-9]{10}$";
	private static final String PHONE_NUMBER_REGEXP_MESSAGE = "Номер телефона: неверный формат.";

	private static final String DENOMINATION_CARD_FIELD_NAME = "Номинал карты";
	private static final String DENOMINATION_CARD_DESC = "Пожалуйста, укажите сумму, которая должна быть размещена на карте iTunes, в размере от 500 руб. до 10000 руб.";
	private static final String DENOMINATION_CARD_REGEXP_VALUE = "^([5-9]{1}|[1-9][0-9])[0-9]{2}|10000$";
	private static final String DENOMINATION_CARD_REGEXP_MESSAGE = DENOMINATION_CARD_DESC;

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public ITunesPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected List<Field> getNewExtendedFields(List<Field> extendedFields) throws GateException
	{
		List<Field> newExtendedFields = super.getNewExtendedFields(extendedFields);

		Field agreementField = BillingPaymentHelper.getFieldById(extendedFields, Constants.AGREEMENT_FIELD_NAME);
		if (agreementField == null)
			newExtendedFields.add(createAgreementField());

		return newExtendedFields;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);

		Field agreementField = BillingPaymentHelper.getFieldById(extendedFields, Constants.AGREEMENT_FIELD_NAME);
		if(agreementField == null)
			throw new GateException("Не найдено поле с соглашением " + Constants.AGREEMENT_FIELD_NAME);

		if(!Boolean.TRUE.toString().equals(agreementField.getValue()))
			throw new GateLogicException(AGREEMENT_MESSAGE);
	}

	protected Field createIdentifierField()
	{
		CommonField field = RequestHelper.createIdentifierField(PHONE_NUMBER_FIELD_NAME);
		field.setDescription(PHONE_NUMBER_DESC);
		field.setBusinessSubType(BusinessFieldSubType.phone);
		field.setKey(true);
		field.setFieldValidationRules(
				Collections.singletonList(BillingPaymentHelper.createRegexpValidator(PHONE_NUMBER_REGEXP_VALUE, PHONE_NUMBER_REGEXP_MESSAGE)));

		return field;
	}

	protected Field createAmountField()
	{
		CommonField field = BillingPaymentHelper.createAmountField(DENOMINATION_CARD_FIELD_NAME, true, null);
		field.setDescription(DENOMINATION_CARD_DESC);
		field.setFieldValidationRules(
				Collections.singletonList(BillingPaymentHelper.createRegexpValidator(DENOMINATION_CARD_REGEXP_VALUE, DENOMINATION_CARD_REGEXP_MESSAGE)));

		return field;
	}

	protected Field createAgreementField()
	{
		CommonField field = BillingPaymentHelper.createAgreementField(
				Constants.AGREEMENT_FIELD_NAME, AGREEMENT_FIELD_NAME, AGREEMENT_DESC_ID);
		field.setRequired(false);

		return field;
	}
}
