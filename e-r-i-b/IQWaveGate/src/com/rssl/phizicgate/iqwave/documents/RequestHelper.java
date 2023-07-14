package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 12.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RequestHelper
{
	/**
	 * Добавить информацию по карте
	 * CardInf	Карта списания
	 *       CardNumber	CardNumber  Номер карты	                    [1]
	 *       EndDate	Date        Дата окончания действия карты	[1]
	 * @param message сообщение.
	 * @param cardNumber номер карты
	 * @param endDate дата окончания действия карты
	 */
	public static void appendCardInf(GateMessage message, String cardNumber, Calendar endDate)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, "DebitCard");
		XmlHelper.appendSimpleElement(debitCard, "CardNumber", cardNumber);
		XmlHelper.appendSimpleElement(debitCard, "EndDate", XMLDatatypeHelper.formatDateWithoutTimeZone(endDate));
	}

	/**
	 * Добавить информацию по карте для перевода карта-карта.
	 * Дата окончания действия карты не передается по спецификации.
	 * CardInf	Карта списания
	 *       CardNumber	CardNumber  Номер карты	                    [1]
	 * @param message сообщение.
	 * @param cardNumber номер карты
	 */
	public static void appendCardInfForCardsTransfer(GateMessage message, String cardNumber)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, "DebitCard");
		XmlHelper.appendSimpleElement(debitCard, "CardNumber", cardNumber);
	}

	/**
	 * Добавить сумму платежа с валютой
	 *  SumCurr
	 *      Summa - сумма
	 *      CurrIso - код валюты
	 * @param message сообщение
	 * @param money
	 */
	public static void appendAmount(GateMessage message, String fieldName, Money money)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element sumCurr = XmlHelper.appendSimpleElement(element, fieldName);
		XmlHelper.appendSimpleElement(sumCurr, "Summa", money.getDecimal().toString());
		XmlHelper.appendSimpleElement(sumCurr, "CurrIso", money.getCurrency().getCode());
	}

	/**
	 * Добавить период оплаты
	 *  PayPeriod	Период оплаты
	 *      PayMonth	Long	 Месяц, в формате MM	[1]
	 *      PayYear	    Long    Год, в формате yyyy	    [1]
	 * @param message сообщение.
	 * @param fieldName имя тега
	 * @param period период в формате MM/ГГГГГ
	 */
	public static void appendPeriod(GateMessage message, String fieldName, String period) throws GateLogicException
	{
		if (!period.matches("(0[0-9]|1[0-2])/[0-9]{4}"))
		{
			throw new GateLogicException("Пожалуйста, укажите период в формате ММ/ГГГГ.");
		}
		int slashIndex = period.indexOf('/');
		String month = period.substring(0, slashIndex);
		String year = period.substring(slashIndex + 1);
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, fieldName);
		XmlHelper.appendSimpleElement(debitCard, "PayMonth", month);
		XmlHelper.appendSimpleElement(debitCard, "PayYear", year);
	}

	/**
	 * Добавить код марщрута
	 * RouteCode	Код сервиса (маршрута)
	 *      DigCode	    Long    Код сервиса (маршрута) организации получателя. [1]
	 * @param message сообщение.
	 * @param tagName имя тега
	 * @param code код
	 */
	public static void appendRouteCode(GateMessage message, String tagName, Long code)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, tagName);
		XmlHelper.appendSimpleElement(debitCard, "DigCode", code.toString());
	}

	/**
	 * Получить из сообщения код маршрута
	 * RouteCode	Код сервиса (маршрута)
	 *      DigCode	    Long    Код сервиса (маршрута) организации получателя. [1]
	 * @param root документ
	 * @param tagName название тега с кодом
	 * @return код маршрута
	 */
	public static Long getRouteCode(Element root, String tagName) throws GateException
	{
		try
		{
			String value = XmlHelper.getElementValueByPath(root, tagName + "/DigCode");
			return Long.valueOf(value);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * получить период оплаты
	 *  PayPeriod	Период оплаты
	 *      PayMonth	Long	 Месяц, в формате MM	[1]
	 *      PayYear	    Long    Год, в формате yyyy	    [1]
	 * @param root документ
	 * @param tagName имя тега
	 * @return Период оплаты. В качестве чиста стоит 1 число.
	 */
	public static Calendar getDebtsPeriod(Element root, String tagName) throws GateException
	{
		try
		{
			String month = XmlHelper.getElementValueByPath(root, tagName + "/PayMonth");
			String year = XmlHelper.getElementValueByPath(root, tagName + "/PayYear");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(year));
			calendar.set(Calendar.MONTH, Integer.valueOf(month));
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			return calendar;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * добавить сумму в сообщение
	 * Money	Decimal  Сумма. Не может иметь более 2 знаков после точки
	 * @param message
	 * @param amount сумма
	 * @param parameterName имя параметра
	 */
	public static void appendSumma(GateMessage message, Money amount, String parameterName)
	{
		message.addParameter(parameterName, amount.getDecimal().toString());
	}

	/**
	 * Создание поля "Лицевой счет"
	 * @return поле "Лицевой счет"
	 */
	static Field createIdentifierField()
	{
		return createIdentifierField("Лицевой счет");
	}

	/**
	 * Создание поля RecIdentifier с указанием выводимого имени
	 * @return поле RecIdentifier
	 */
	static CommonField createIdentifierField(String name)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.REC_IDENTIFIER_FIELD_NAME);
		field.setName(name);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		return field;
	}

	/**
	 * Создание поля "Номер квартиры"
	 * @return поле "Номер квартиры"
	 */
	static Field createFlatNumberField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.FLAT_NUMBER_FIELD_NAME);
		field.setName("Номер квартиры");
		field.setRequired(false);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * Создание поля "Вариант тарифа"
	 * @return поле "Вариант тарифа"
	 */
	static Field createTarifVersionField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.TARIFF_VAR_FIELD_NAME);
		field.setName("Вариант тарифа");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);

		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue(Constants.TARIFF_VAR_ONETARIFF,  Constants.TARIFF_VAR_ONETARIFF));
		values.add(new ListValue(Constants.TARIFF_VAR_TWOTARIFF,  Constants.TARIFF_VAR_TWOTARIFF));
		values.add(new ListValue(Constants.TARIFF_VAR_MANYTARIFF, Constants.TARIFF_VAR_MANYTARIFF));
		field.setValues(values);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * Создание поля "Зона суток"
	 * @param tarifVersion вариант тарифа
	 * @return поле "Зона суток"
	 */
	static Field createTarifZone(String tarifVersion)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.TARIFF_ZONE_FIELD_NAME);
		field.setName("Зона суток");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);

		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue(Constants.TARIFF_ZONA_DAY, Constants.TARIFF_ZONA_DAY));

		if (!Constants.TARIFF_VAR_ONETARIFF.equals(tarifVersion))
		{
			values.add(new ListValue(Constants.TARIFF_ZONA_NIGHT, Constants.TARIFF_ZONA_NIGHT));

			if (Constants.TARIFF_VAR_MANYTARIFF.equals(tarifVersion))
			{
				values.add(new ListValue(Constants.TARIFF_ZONA_PEAK, Constants.TARIFF_ZONA_PEAK));
				values.add(new ListValue(Constants.TARIFF_ZONA_HALFPEAK, Constants.TARIFF_ZONA_HALFPEAK));
			}
		}
		field.setValues(values);

		return field;
	}

	/**
	 * Создание поля "Период оплаты"
	 * @return поле "Период оплаты"
	 */
	static Field createPeriodField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.PERIOD_FIELD_NAME);
		field.setName("Период оплаты");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(false);

		List<FieldValidationRule> validationRules = new ArrayList<FieldValidationRule>();
		FieldValidationRuleImpl   validationRule  = new FieldValidationRuleImpl();

		validationRule.setErrorMessage("Пожалуйста, укажите период в формате ММ/ГГГГ.");
		validationRule.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regexp", "(0[0-9]|1[0-2])/[0-9]{4}");
		validationRule.setParameters(map);

		validationRules.add(validationRule);
		field.setFieldValidationRules(validationRules);

		return field;
	}

	/**
	 * Создание поля "Задолженность"
	 * @return поле "Задолженность"
	 */
	static Field createDebtField()
	{
		CommonField field = new CommonField();
		field.setExternalId(Constants.DEBT_FIELD_NAME);
		field.setName("Задолженность");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setKey(true);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * Создание поля "Варианты оплаты"
	 * @param debtRows список вариантов оплаты
	 * @return поле "Варианты оплаты"
	 */
	static Field createDebtRowField(List<DebtRow> debtRows)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.DEBT_ROW_FIELD_NAME);
		field.setName("Варианты оплаты");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);

		List<ListValue> values = new ArrayList<ListValue>();

		for (DebtRow debtRow : debtRows)
		{
			values.add(new ListValue(debtRow.getDescription(), debtRow.getCode()));
		}

		field.setValues(values);

		return field;
	}

	/**
	 * Создание поля "Предидущее показание счетчика"
	 * @return поле "Предидущее показание счетчика"
	 */
	public static Field createIndicationFieldField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.LAST_INDICATION_FIELD_NAME);
		field.setName("Предыдущее показание счетчика");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setDefaultValue("001");
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 * Создание поля "Текущее показание счетчика"
	 * @return поле "Текущее показание счетчика"
	 */
	public static Field createCurrentIndicationField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.CURRENT_INDICATION_FIELD_NAME);
		field.setName("Текущее показание счетчика");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 * Создание нередактируемого текстового поля
	 * @param externalId внешний идентификатор
	 * @param name выводимое имя
	 * @param initialValue начальное значение
	 * @param groupName название группы
	 * @return сформированное поле
	 */
	public static CommonField createDisableTextField(String externalId, String name, String initialValue, String groupName)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(externalId);
		field.setName(name);
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setRequiredForBill(false);
		field.setSaveInTemplate(true);
		field.setDefaultValue(initialValue);
		field.setGroupName(groupName);

		return field;
	}

	/**
	 * Получить строковое значение поля из списка расширенных полей по имени.
	 * @param payment - платеж
	 * @param name - имя поля
	 * @return - строковое значение
	 * @throws GateException
	 */
	public static String getStringFromExtendedFields(AbstractPaymentSystemPayment payment, String name) throws GateException
	{
		try
		{
			Field field = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), name);

			if (field == null)
				return null;

			return (String) field.getValue();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
