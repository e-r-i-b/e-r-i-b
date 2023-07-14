package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 * Оплата МГТС  – отличается от типовой оплаты  типом сообщения и наличием необязательного поля FlatNumber
 * Элемент	        Тип	            Комментарий	                    Кратность
 * <Route>	        RouteCode       Код сервиса (маршрута)	        [1]
 * <DebitCard>	    CardInf         Информация по карте списания	[1]
 * <RecIdentifier>	Requisite       Номер налогового извещения	    [1]
 * <CurrCode>	    IsoCode         Валюта операции	                [1]
 * <Summa>	        Money           Cумма платежа (задолженности)	[1]
 * <FlatNumber>	    FlatNumb        Номер квартиры	[0..1]
 */
public class MGTSPaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public MGTSPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_MGTS_REQUEST, Constants.PAYMENT_MGTS_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		return Constants.PAYMENT_DEBTS_MGTS_REQUEST;
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard())
				|| payment.getChargeOffCardExpireDate() == null)
			throw new GateException("Поставщик поддерживает только карточные переводы");

		try
		{
			List<Field> extendedFields    = payment.getExtendedFields();
			List<Field> newExtendedFields = new ArrayList<Field>();

			//если дополнительное поле не найдено, то добавляем его, на следуещем шаге делаем его не редактируемым
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if (identifierField == null)
			{
				newExtendedFields.add(RequestHelper.createIdentifierField());
			}
			else
			{
				identifierField.setEditable(false);
			}

			//если дополнительное поле номер квартиры не найдено, то добавляем его, на следующем шаге делаем его нередактируемым
			CommonField flatNumberField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.FLAT_NUMBER_FIELD_NAME);
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.FLAT_NUMBER_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createFlatNumberField());
			}
			else
			{
				flatNumberField.setEditable(false);
			}

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			Debt debt = DebtsHelper.parseMGTSFNSDebt(getDebtResponse(payment));
			Money debtValue = debt.getRows().get(0).getDebt();

			if (debtValue.getAsCents() == 0 && !payment.isTemplate())
				throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность.");
			if(debtValue.getAsCents() <0 && !payment.isTemplate() && debt.isFixed())
				throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность (переплата составляет" + debtValue.getDecimal().toString().replace('-',' ') + "р.).");

			CommonField debtField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
			//добавляем поле задолженность
			//для шаблона платежа поле задолженность не формируем
			if (debtField == null && !payment.isTemplate())
			{
				debtField = (CommonField) RequestHelper.createDebtField();
				debtField.setType(FieldDataType.money);
				debtField.setDefaultValue(debtValue.getDecimal().toString());
				debtField.setEditable(false);

				newExtendedFields.add(debtField);
			}

			//добавляем поле сумма
			CommonField amountField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.AMOUNT_FIELD_NAME);
			if (amountField == null)
			{
				amountField = BillingPaymentHelper.createAmountField();
				amountField.setDefaultValue(debtValue.getDecimal().toString());
				//в isFixed устанавливается значение обратное значению тега SummCanEdit, поэтому сетим с отрицанием 
				amountField.setEditable(payment.isTemplate() || !debt.isFixed());

				newExtendedFields.add(amountField);
			}

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			if (payment.isTemplate())
			{
				//в шаблоне платежа поле задолженность не отображаем
				extendedFields.remove(debtField);
			}
			else
			{
				BigDecimal paymentAmount = new BigDecimal((String) amountField.getValue());
				//если сумма платежа больше суммы задолжности и превышение суммы задолженности запрещено, ругаемся
				if (debt.isFixed() && debtValue.getDecimal().compareTo(paymentAmount) < 0)
				{
					amountField.setError("Пожалуйста, укажите сумму платежа, не превышающую сумму текущей задолженности.");
					payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
					return;
				}
			}

			//конец. устанавливаем идентификатор поля во внешней системе
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
			amountField.setError(Constants.EMPTY_VALUE);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		super.fillExecutionMessage(message, payment);
		//к общей части необходимо добавить номер квартиры(при необходимости)
		try
		{
			Field flatNumber = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.FLAT_NUMBER_FIELD_NAME);
			if (flatNumber != null)
			{
				String value = (String) flatNumber.getValue();
				if (!StringHelper.isEmpty(value))
					message.addParameter(Constants.FLAT_NUMBER_FIELD_NAME, value);
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException
	{
		//к общей части необходимо добавить необязательное поле номер квартиры
		Field flatNumber = BillingPaymentHelper.getFieldById(extendedFields, Constants.FLAT_NUMBER_FIELD_NAME);
		if (flatNumber != null && flatNumber.getValue()!=null)
		{
			message.addParameter(Constants.FLAT_NUMBER_FIELD_NAME, flatNumber.getValue());
		}
	}
}
