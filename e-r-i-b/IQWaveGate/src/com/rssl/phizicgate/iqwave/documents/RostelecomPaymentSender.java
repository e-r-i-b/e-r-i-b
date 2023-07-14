package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * @author khudyakov
 * @ created 12.08.2010
 * @ $Author$
 * @ $Revision$
 *
 *  Оплата Ростелеком
 *
 *  Запрос на исполнение:
 *		Элемент	         Тип     	        Комментарий         	        Кратность
 *	<Route>	         RouteCode           Код сервиса (маршрута)	                [1]
 *	<DebitCard>	     CardInf             Информация по карте списания	        [1]
 *	<RecIdentifier>	 Requisite           Реквизит, идентифицирующий плательщика	[1]
 *  <DebtsAccNumber> NC-20	             Номер счета задолженности	            [1]
 *	<CurrCode>	     IsoCode             Валюта операции	                    [1]
 *	<Summa>	         Money               Cумма платежа (задолженности)	        [1]
 *
 */
public class RostelecomPaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public RostelecomPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_ROSTELECOM_REQUEST, Constants.PAYMENT_ROSTELECOM_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		return Constants.PAYMENT_DEBTS_ROSTELECOM_REQUEST;
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		super.fillExecutionMessage(message, payment);

		try
		{
			CommonField debtField = (CommonField) BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.DEBT_FIELD_NAME);
			if (debtField == null)
				throw new GateException("В платеже должна быть ровно одна задолженность");

			String code = (String) debtField.getValue();
			if (code.indexOf(Constants.DELIMITER) < 0)
				throw new GateException("Идентификация задолженности должна содержать склеенные через разделитель '" + Constants.DELIMITER  + "' значения DebtsAccNumber и DebtsNumber");

			message.addParameter("DebtsAccNumber", code.split(Constants.DELIMITER)[1]);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
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

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//если необходимые поля доступны, получаем задолжность
			List<Debt> debts = DebtsHelper.parseRostelecomDebts(getDebtResponse(payment));
			if (debts.isEmpty() && !payment.isTemplate())
				throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность.");

			if (payment.isTemplate())
			{
				//для шаблона платежа не подставляем в поле суммы значение задолженности
				//если поле не найдено, добавляем его
				CommonField amountField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();
					newExtendedFields.add(amountField);
				}
				amountField.setEditable(true);
				amountField.setError(Constants.EMPTY_VALUE);
			}
			else
			{
				//если поле не найдено, добавляем его, на следуещем шаге делаем его не редактируемым
				CommonField debtField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
				if (debtField == null)
				{
					debtField = (CommonField) RequestHelper.createDebtField();
					debtField.setType(FieldDataType.list);

					List<ListValue> values = new ArrayList<ListValue>();
					for (Debt debt : debts)
					{
						DebtRow debtRow = debt.getRows().get(0);

						StringBuffer buffer = new StringBuffer();
						buffer.append(debtRow.getDebt().getDecimal()).append(" ").append(debtRow.getDebt().getCurrency().getCode());

						values.add(new ListValue(buffer.toString() , debtRow.getCode()));
					}
					debtField.setValues(values);

					newExtendedFields.add(debtField);
				}
				else
				{
					debtField.setEditable(false);
				}

				//показываем форму клиенту
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//если поле не найдено, добавляем его
				CommonField amountField = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();
					String amountValue = getDebt(debts, (String) debtField.getValue()).getDecimal().toString();
					if (new BigDecimal(amountValue).compareTo(BigDecimal.valueOf(0L)) < 1)
					{
						throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность (переплата составляет" + amountValue.replace('-',' ') + " р.)");
					}
					amountField.setDefaultValue(amountValue);
					amountField.setEditable(false);

					newExtendedFields.add(amountField);
				}

				//показываем форму клиенту
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//делаем проверку суммы платежа на соответствие задолженности,
				//т.к. переплата/недоплаты данному поставщику запрещены
				BigDecimal debtMoney = getDebt(debts, (String) debtField.getValue()).getDecimal();
				BigDecimal paymentAmount = new BigDecimal((String) amountField.getValue());

				//переплата/недоплата запрещены
				if (debtMoney.compareTo(paymentAmount) != 0)
				{
					payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
					amountField.setError("Пожалуйста, укажите сумму платежа, равную сумме текущей задолженности.");
					return;
				}
				amountField.setError(Constants.EMPTY_VALUE);
			}

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//конец. устанавливаем идентификатор поля во внешней системе
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private Money getDebt(List<Debt> debts, String debtValue) throws GateLogicException
	{
		for (Debt debt : debts)
		{
			DebtRow debtRow = debt.getRows().get(0);
			if (debtValue.equals(debtRow.getCode()))
				return debtRow.getDebt();
		}
		throw new GateLogicException("Вы неправильно указали реквизиты платежа. Пожалуйста, проверьте реквизиты и повторите операцию.");
	}
}
