package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.DebtImpl;
import com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.DebtRow;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.documents.RequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DebtsHelper
{
	/**
	 * Распарсить ответ о задолженности для МГТС или FNS
	 *
	 * Ответ о задолженности:
	 * Элемент	            Тип	        Комментарий	                        Кратность
	 * <SummCanEdit>	    Bool        Клиент может изменить сумму платежа	    [1]
	 * <DebtsRoute>	        RouteCode   Код сервиса (маршрута)	                [1]
	 * <DebtsCurrCode>	    IsoCode     Валюта задолженности	                [1]
	 * <DebtsSumma>	        DebSumType  Сумма задолженности	                    [1]
	 *
	 * @param response документ с ответом о задолженности
	 * @return Список задолженностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Debt parseMGTSFNSDebt(Document response) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Element root = response.getDocumentElement();
		DebtImpl debt = new DebtImpl();
		String debtCode = RequestHelper.getRouteCode(root, "DebtsRoute").toString();
		debt.setCode(debtCode);
		debt.setDescription("Задолженность");
		debt.setFixed(!Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit")));

		DebtRowImpl debtRow = new DebtRowImpl();
		debtRow.setCode(debtCode);
		Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(root, "DebtsCurrCode"));
		Money noMoney = new Money(BigDecimal.ZERO, currency);

		BigDecimal debtValue = getMoney(root, "DebtsSumma");
		debtRow.setDebt(new Money(debtValue, currency));
		debtRow.setCommission(noMoney);
		debtRow.setFine(noMoney);
		debt.addRow(debtRow);

		return debt;
	}

	/**
	 * Распарсить ответ о задолженности для МГТС или FNS
	 *
	 * Ответ о задолженности:
	 * Элемент	            Тип	        Комментарий	                        Кратность
	 * <SummCanEdit>	    Bool        Клиент может изменить сумму платежа	    [1]
	 * <DebtsRoute>	        RouteCode   Код сервиса (маршрута)	                [1]
	 * <DebtsCurrCode>	    IsoCode     Валюта задолженности	                [1]
	 * <DebtsSumma>	        DebSumType  Сумма задолженности	                    [1]
	 *
	 * @param response документ с ответом о задолженности
	 * @return Список задолженностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	//TODO метод временно оставлен (чтобы компилировалось), в последствии убрать
	//TODO пока не ясно в запросе или задаче этот фун-л будет убран
	static List<Debt> parseMGTSFNSDebtList(Document response) throws GateException, GateLogicException
	{
		List<Debt> result = new ArrayList<Debt>();
		result.add(parseMGTSFNSDebt(response));
		return result;
	}

	private static BigDecimal getMoney(Element root, String tagName) throws GateException
	{
		BigDecimal debtValue = null;
		try
		{
			debtValue = NumericUtil.parseBigDecimal(XmlHelper.getSimpleElementValue(root, tagName));
		}
		catch (ParseException e)
		{
			throw new GateException("Некорректный формат суммы задолженности", e);
		}
		return debtValue;
	}

	/**
	 * Сформировать основную чать запросa задолженности

	 * Запрос задолженности:
	 * Элемент	         Тип	        Комментарий	                  Кратность
	 * <Route>	        RouteCode      Код сервиса (маршрута)          [1]
	 * <DebitCard>	    CardInf        Информация по карте списания	   [1]
	 * <RecIdentifier>	Requisite      Номер налогового извещения	   [1]
	 *
	 * @param recipient получатель
	 * @param keyFields ключевые поля
	 * @throws GateException
	 */
	//TODO метод временно оставлен (чтобы компилировалось), в последствии убрать
	//TODO пока не ясно в запросе или задаче этот фун-л будет убран
	static void fillDebtRequest(GateMessage message, Recipient recipient, List<Field> keyFields) throws GateException
	{
		//Код сервиса (маршрута)
		RequestHelper.appendRouteCode(message, "Route", Long.valueOf((String) recipient.getSynchKey()));
		//Информация по карте списания
		appendCardInf(message, keyFields);
		//Номер налогового извещения
		Field identifier = BillingPaymentHelper.getFieldById(keyFields, Constants.REC_IDENTIFIER_FIELD_NAME);
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, identifier.getValue());
	}

	//TODO метод временно оставлен (чтобы компилировалось), в последствии убрать
	//TODO пока не ясно в запросе или задаче этот фун-л будет убран
	private static void appendCardInf(GateMessage message, List<Field> keyFields) throws GateException
	{
		Field cardNumberField = BillingPaymentHelper.getFieldById(keyFields, "$$CLIENT_CARD_NUMBER");
		Field cardExpireDateField = BillingPaymentHelper.getFieldById(keyFields, "$$CLIENT_CARD_EXPIRE_DATE");
		Object value = cardExpireDateField.getValue();
		RequestHelper.appendCardInf(message, (String) cardNumberField.getValue(), DateHelper.toCalendar((Date) value));
	}

	/**
	 * Сформировать основную чать запросa задолженности

	 * Запрос задолженности:
	 * Элемент	         Тип	        Комментарий	                  Кратность
	 * <Route>	        RouteCode      Код сервиса (маршрута)          [1]
	 * <DebitCard>	    CardInf        Информация по карте списания	   [1]
	 * <RecIdentifier>	Requisite      Номер налогового извещения	   [1]
	 *
	 * @param message сообщение
	 * @param payment платеж
	 * @throws GateException
	 */
	public static void fillDebtRequest(GateMessage message, CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			//Код сервиса (маршрута)
			RequestHelper.appendRouteCode(message, "Route", Long.valueOf(payment.getReceiverPointCode()));
			//Информация по карте списания
			RequestHelper.appendCardInf(message, payment.getChargeOffCard(), payment.getChargeOffCardExpireDate());
			//Номер налогового извещения
			Field identifier = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.REC_IDENTIFIER_FIELD_NAME);
			message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, identifier.getValue());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Распарсить ответ о задолженности для ЖКХ
	 *
	 *  Ответ о задолженности:
	 * Элемент	                    Тип	            Комментарий	                                                             Кратность
	 * <SummCanEdit>	            Bool        Клиент может изменить сумму платежа.                                        [1]
	 *                                  (всегда равен «false», менять сумму нельзя).
	 * <PayDebtsList>	 Список задолженностей. Пустой список означает, что у клиента нет текущей задолженности.	        [0..n]
	 *      <DebtsRoute>	        RouteCode   Код сервиса (маршрута)	                                                    [1]
	 *  	<DebtsNumber>	        Long        Номер задолженности	                                                        [1]
	 *      <DebtsPeriod>	        PayPeriod   Период задолженности	                                                    [1]
	 *  	<DebtsCurrCode>	        IsoCode     Валюта задолженности	                                                    [1]
	 *      <DebtsSumma>	        DebSumType  Сумма задолженности, должна быть положительной, переплата не допускается.	[1]
	 *  	<DebtsComission>	    Money       Сумма комиссии, должна быть положительной	                                [1]
	 *  	<DebtsName>	            C-50        Описание задолженности	                                                    [1]
	 *  	<DebtsCases>    Варианты оплаты	                                                                                [0…2]
	 *  		<CaseNumber>	    Long        Номер тега спецификации E-pay для поля суммы варианта оплаты.	            [1]
	 *  		<CaseDesc>	        C-50        Описание варианта оплаты	                                                [1]
	 *          <CaseSumma>	        DebSumType  Сумма задолженности варианта оплаты, должна быть положительной,
	 *                                          переплата не допускается.	                                                [1]
	 *          <CaseComission>    Money       Сумма комиссии варианта оплаты, должна быть положительной	                        [1]
	 *
	 * Примечание: Если <DebtsCases> не содержит дочерних элементов,
	 * сумма задолженности и комиссия берется из полей <DebtsSumma> и <DebtsComission>.
	 * Если <DebtsCases> содержит 1 или 2 варианта оплаты,
	 * сумма задолженности и комиссия берется из полей <CaseSumma> и <CaseComission>.
	 *
	 * @param response документ с ответом о задолженности
	 * @return Список задолженностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static List<Debt> parseGKHDebts(Document response) throws GateException, GateLogicException
	{
		Element root = response.getDocumentElement();
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		final boolean summCanEdit = !Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit"));

		final List<Debt> result = new ArrayList<Debt>();
		try
		{
			XmlHelper.foreach(root, "PayDebtsList", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(element, "DebtsCurrCode"));
					DebtImpl debt = new DebtImpl();
					String debtCode = RequestHelper.getRouteCode(element, "DebtsRoute").toString();
					debt.setCode(debtCode);
					debt.setDescription(XmlHelper.getSimpleElementValue(element, "DebtsName"));
					debt.setFixed(summCanEdit);
					debt.setPeriod(RequestHelper.getDebtsPeriod(element, "DebtsPeriod"));
					String debtsNumber = XmlHelper.getSimpleElementValue(element, "DebtsNumber");
					debt.setCode(debtCode + "^" + debtsNumber);
					List<DebtRow> rows = parseGKHDebtRows(element, currency, debtsNumber);

					if (rows.isEmpty())
					{
						DebtRowImpl debtRow = new DebtRowImpl();
						debtRow.setCode(debtsNumber);
						BigDecimal debtValue = getMoney(element, "DebtsSumma");
						debtRow.setDebt(new Money(debtValue, currency));
						BigDecimal commission = getMoney(element, "DebtsComission");
						debtRow.setCommission(new Money(commission, currency));
						rows.add(debtRow);
					}
					debt.setRows(rows);
					result.add(debt);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return result;
	}

	/**
	 *  <DebtsCases>    Варианты оплаты	                                                                                [0…2]
	 *  	<CaseNumber>	    Long        Номер тега спецификации E-pay для поля суммы варианта оплаты.	            [1]
	 *  	<CaseDesc>	        C-50        Описание варианта оплаты	                                                [1]
	 *      <CaseSumma>	        DebSumType  Сумма задолженности варианта оплаты, должна быть положительной,
	 *                                      переплата не допускается.	                                                [1]
	 *      <CaseComission>     Money       Сумма комиссии варианта оплаты, должна быть положительной                   [1]
	 */
	private static List<DebtRow> parseGKHDebtRows(Element element, final Currency currency, final String debtsNumber) throws GateException
	{
		try
		{
			final List<DebtRow> rows = new ArrayList<DebtRow>();
			XmlHelper.foreach(element, "DebtsCases", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					DebtRowImpl debtRow = new DebtRowImpl();
					debtRow.setCode(debtsNumber + "-" + XmlHelper.getSimpleElementValue(element, "CaseNumber"));
					debtRow.setDescription(XmlHelper.getSimpleElementValue(element, "CaseDesc"));
					BigDecimal debtValue = getMoney(element, "CaseSumma");
					debtRow.setDebt(new Money(debtValue, currency));
					BigDecimal commission = getMoney(element, "CaseComission");
					debtRow.setCommission(new Money(commission, currency));
					rows.add(debtRow);
				}
			});
			return rows;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Распарсить ответ от Rostelecom
	 *
	 * Ответ задолженности:
	 * Элемент	             Тип	        Комментарий	                        Кратность
	 *	<SummCanEdit>        Bool        Клиент может изменить сумму платежа. (всегда равен «false», менять сумму нельзя).	     [1]
	 *	<PayDebtsList>		             Список задолженностей. Пустой список означает, что у клиента нет текущей задолженности. [0..n]
	 *	    <DebtsRoute>	 RouteCode   Код сервиса (маршрута)	                [1]
	 *	    <DebtsNumber>	 Long        Номер задолженности	                [1]
	 *      <DebtsAccNumber> NC-20       Номер счета задолженности              [1]
	 *	    <DebtsPeriod>	 PayPeriod   Период задолженности	                [1]
	 *	    <DebtsCurrCode>	 IsoCode     Валюта задолженности	                [1]
	 *	    <DebtsSumma>	 DebSumType  Сумма задолженности, должна быть положительной, переплата не допускается.	            [1]
	 *	    <DebtsName>	     C-50        Описание задолженности	                [1]
	 *
	 * @param response  - ответ
	 * @return - список задолжностей
	 */
	public static List<Debt> parseRostelecomDebts(Document response) throws GateException
	{
		Element root = response.getDocumentElement();
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		final boolean summCanEdit = !Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "SummCanEdit"));

		final List<Debt> result = new ArrayList<Debt>();
		try
		{
			XmlHelper.foreach(root, "PayDebtsList", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					DebtImpl debt = new DebtImpl();
					DebtRowImpl debtRow = new DebtRowImpl();
					List<DebtRow> debtRows = new ArrayList<DebtRow>();

					debt.setCode(RequestHelper.getRouteCode(element, "DebtsRoute").toString());

					// используем для идентификации задолженности склеенные через разделитель значения DebtsAccNumber
					// и DebtsNumber при отправке платежа выдираем значение DebtsAccNumber из кода зщадолженности.
					String debtsNumber = XmlHelper.getSimpleElementValue(element, "DebtsNumber");
					String debtsAccNumber = XmlHelper.getSimpleElementValue(element, "DebtsAccNumber");
					StringBuilder code = new StringBuilder();
					code.append(debtsNumber).append(Constants.DELIMITER).append(debtsAccNumber);
					debtRow.setCode(code.toString());

					debt.setPeriod(RequestHelper.getDebtsPeriod(element, "DebtsPeriod"));
					debt.setDescription(XmlHelper.getSimpleElementValue(element, "DebtsName"));
					debt.setFixed(summCanEdit);
					Currency currency = currencyService.findByAlphabeticCode(XmlHelper.getSimpleElementValue(element, "DebtsCurrCode"));
					debtRow.setDebt(new Money(getMoney(element, "DebtsSumma"), currency));
					debtRows.add(debtRow);
					debt.setRows(debtRows);
					result.add(debt);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return result;
	}
}
