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
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.documents.debts.DebtsHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 * Оплата ЖКХ
 * Элемент	            Тип	            Комментарий	                        Кратность
 * <Route>	            RouteCode       Код сервиса (маршрута)	                [1]
 * <DebitCard>	        CardInf         Информация по карте списания	        [1]
 * <RecIdentifier>	    Requisite       Реквизит, идентифицирующий плательщика	[1]
 * <DebtsPeriod>	    PayPeriod       Период задолженности	                [1]
 * <DebtsCurrCode>	    IsoCode         Валюта задолженности	                [1]
 * <DebtsSumma>	        Money           Сумма задолженности	                    [1]
 * <DebtsComission>	    Money           Комиссия	                            [1]
 * <DebtsCaseNumber>    Long            Поле обязательно, если в списке	        [0-1]
 *                                      задолженностей вернулся хотя бы
 *                                      один вариант оплаты.
 * <MBOperCode>         MBOperCode      Код мобильного банка                    [0-1]
 */
public class GKHPaymentSender extends PaymentSystemPaymentSenderBase
{
	public GKHPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_GKH_REQUEST, Constants.PAYMENT_GKH_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		return Constants.PAYMENT_DEBTS_GKH_REQUEST;
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

			//если поле не найдено, добавляем его, на следуещем шаге делаем его не редактируемым
			CommonField periodField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME);
			if (periodField == null)
			{
				newExtendedFields.add(RequestHelper.createPeriodField());
			}
			else
			{
				periodField.setEditable(false);
			}

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			if (payment.isTemplate())
			{
				//поля задолженность и вариант оплаты для шаблонов не должны показваться.
				Iterator<Field> iter = extendedFields.iterator();
				while (iter.hasNext()) {
					Field field = iter.next();
					if (field.getExternalId().equals(Constants.DEBT_FIELD_NAME) ||
							field.getExternalId().equals(Constants.DEBT_ROW_FIELD_NAME)) {
						iter.remove();
					}
				}

				//для шаблона платежа не подставляем в поле суммы значение задолженности
				//если поле не найдено, добавляем его
				CommonField amountField  = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();
					newExtendedFields.add(amountField);
				}
				amountField.setEditable(true);
				amountField.setError(Constants.EMPTY_VALUE);

				//показываем форму клиенту
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}
			}
			else
			{
				//если необходимые поля доступны, получаем задолжность и не шаблон
				List<Debt> debts = DebtsHelper.parseGKHDebts(getDebtResponse(payment));
				if (debts.isEmpty())
					throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность.");

				CommonField debtField    = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
				CommonField debtRowField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_ROW_FIELD_NAME);
				//если поле не найдено, добавляем его, на следуещем шаге делаем его не редактируемым
				if (debtField == null)
				{
					debtField = (CommonField) RequestHelper.createDebtField();
					debtField.setType(FieldDataType.list);

					List<ListValue> values = new ArrayList<ListValue>();
					for (Debt debt : debts)
					{
						values.add(new ListValue(debt.getDescription(), debt.getCode()));
					}
					debtField.setValues(values);

					newExtendedFields.add(debtField);
				}
				else
				{
					debtField.setEditable(false);
				}

				//показываем форму клиенту
				if (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//если поле не найдено, добавляем его, на следуещем шаге делаем его не редактируемым
				if (debtRowField == null)
				{
					newExtendedFields.add(RequestHelper.createDebtRowField(findDebtRows(debts, (String) debtField.getValue())));
				}
				else
				{
					debtRowField.setEditable(false);
				}

				//показываем форму клиенту
				if (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				//для шаблона платежа не подставляем в поле суммы значение задолженности
				//если поле не найдено, добавляем его
				CommonField amountField  = (CommonField) BillingPaymentHelper.getMainSumField(extendedFields);
				if (amountField == null)
				{
					amountField = BillingPaymentHelper.createAmountField();

					DebtRow debtRow = findDebtRow(debts, (String)debtField.getValue(), (String)debtRowField.getValue());
					//запретить оплату при отрицательной задолжености при невозможности редактирования.
					if(debtRow.getDebt().getDecimal().compareTo(BigDecimal.valueOf(0L)) < 1)
						throw new GateLogicException("Вы не можете совершить платеж в пользу данного поставщика, т.к. у Вас отсутствует задолженность (переплата составляет" + debtRow.getDebt().getDecimal().toString().replace('-',' ') + "р.).");
					amountField.setDefaultValue(debtRow.getDebt().getDecimal().toString());

					//комиссию для шаблона платежа не устанавливаем
					payment.setCommission(debtRow.getCommission());
					amountField.setEditable(false);

					newExtendedFields.add(amountField);
				}
				amountField.setError(Constants.EMPTY_VALUE);

				//показываем форму клиенту
				if  (!newExtendedFields.isEmpty())
				{
					extendedFields.addAll(newExtendedFields);
					return;
				}

				BigDecimal paymentAmount = new BigDecimal((String) amountField.getValue());
				BigDecimal debtAmount = findDebtRow(debts, (String) debtField.getValue(), (String) debtRowField.getValue()).getDebt().getDecimal();

				//сумма платежа должна быть равна сумме задолжности, переплата, недоплата запрещены
				if (debtAmount.compareTo(paymentAmount) != 0)
				{
					payment.setIdFromPaymentSystem(Constants.EMPTY_VALUE);
					amountField.setError("Пожалуйста, укажите сумму платежа, равную сумме текущей задолженности.");
					return;
				}
			}

			//конец. устанавливаем идентификатор поля во внешней системе
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//валидировать ничего не надо
	}

	protected void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException, GateLogicException
	{
		CommonField period = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME);
		//к общей части необходимо добавить период мес/год
		RequestHelper.appendPeriod(message, Constants.PERIOD_FIELD_NAME, (String) period.getValue());
	}

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		try
		{
			Money amount = payment.getDestinationAmount();
			List<Field> extendedFields = payment.getExtendedFields();
			//Код сервиса (маршрута)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//Информация по карте списания
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());
			//Реквизит, идентифицирующий плательщика
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(payment));
			//Период задолженности
			Field period = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.PERIOD_FIELD_NAME);
			RequestHelper.appendPeriod(message, "DebtsPeriod", (String) period.getValue());
			//Валюта операции
			message.addParameter("DebtsCurrCode", amount.getCurrency().getCode());
			//Сумма задолженности
			RequestHelper.appendSumma(message, amount, "DebtsSumma");
			//Комиссия
			RequestHelper.appendSumma(message, payment.getCommission(), "DebtsComission");
			//DebtsCaseNumber
			String debtsCaseNumber = (String) BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_ROW_FIELD_NAME).getValue();
			message.addParameter("DebtsCaseNumber", debtsCaseNumber.substring(debtsCaseNumber.indexOf("-")+1));
            //Код мобильного банка
		    fillMBOperCodeField(message, payment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private DebtRow findDebtRow(List<Debt> debts, String debtValue, String debtRowValue) throws GateLogicException
	{
		for (DebtRow debtRow : findDebtRows(debts, debtValue))
		{
			if (debtRow.getCode().equals(debtRowValue))
				return debtRow;
		}
		throw new GateLogicException("Вы неправильно указали реквизиты платежа. Пожалуйста, проверьте реквизиты и повторите операцию.");
	}

	private List<DebtRow> findDebtRows(List<Debt> debts, String debtValue) throws GateLogicException
	{
		for (Debt debt : debts)
		{
			if (debt.getCode().equals(debtValue))
				return debt.getRows();
		}
		throw new GateLogicException("Вы неправильно указали реквизиты платежа. Пожалуйста, проверьте реквизиты и повторите операцию.");
	}
}
