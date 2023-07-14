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
	 * �������� ���������� �� �����
	 * CardInf	����� ��������
	 *       CardNumber	CardNumber  ����� �����	                    [1]
	 *       EndDate	Date        ���� ��������� �������� �����	[1]
	 * @param message ���������.
	 * @param cardNumber ����� �����
	 * @param endDate ���� ��������� �������� �����
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
	 * �������� ���������� �� ����� ��� �������� �����-�����.
	 * ���� ��������� �������� ����� �� ���������� �� ������������.
	 * CardInf	����� ��������
	 *       CardNumber	CardNumber  ����� �����	                    [1]
	 * @param message ���������.
	 * @param cardNumber ����� �����
	 */
	public static void appendCardInfForCardsTransfer(GateMessage message, String cardNumber)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, "DebitCard");
		XmlHelper.appendSimpleElement(debitCard, "CardNumber", cardNumber);
	}

	/**
	 * �������� ����� ������� � �������
	 *  SumCurr
	 *      Summa - �����
	 *      CurrIso - ��� ������
	 * @param message ���������
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
	 * �������� ������ ������
	 *  PayPeriod	������ ������
	 *      PayMonth	Long	 �����, � ������� MM	[1]
	 *      PayYear	    Long    ���, � ������� yyyy	    [1]
	 * @param message ���������.
	 * @param fieldName ��� ����
	 * @param period ������ � ������� MM/�����
	 */
	public static void appendPeriod(GateMessage message, String fieldName, String period) throws GateLogicException
	{
		if (!period.matches("(0[0-9]|1[0-2])/[0-9]{4}"))
		{
			throw new GateLogicException("����������, ������� ������ � ������� ��/����.");
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
	 * �������� ��� ��������
	 * RouteCode	��� ������� (��������)
	 *      DigCode	    Long    ��� ������� (��������) ����������� ����������. [1]
	 * @param message ���������.
	 * @param tagName ��� ����
	 * @param code ���
	 */
	public static void appendRouteCode(GateMessage message, String tagName, Long code)
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element debitCard = XmlHelper.appendSimpleElement(element, tagName);
		XmlHelper.appendSimpleElement(debitCard, "DigCode", code.toString());
	}

	/**
	 * �������� �� ��������� ��� ��������
	 * RouteCode	��� ������� (��������)
	 *      DigCode	    Long    ��� ������� (��������) ����������� ����������. [1]
	 * @param root ��������
	 * @param tagName �������� ���� � �����
	 * @return ��� ��������
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
	 * �������� ������ ������
	 *  PayPeriod	������ ������
	 *      PayMonth	Long	 �����, � ������� MM	[1]
	 *      PayYear	    Long    ���, � ������� yyyy	    [1]
	 * @param root ��������
	 * @param tagName ��� ����
	 * @return ������ ������. � �������� ����� ����� 1 �����.
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
	 * �������� ����� � ���������
	 * Money	Decimal  �����. �� ����� ����� ����� 2 ������ ����� �����
	 * @param message
	 * @param amount �����
	 * @param parameterName ��� ���������
	 */
	public static void appendSumma(GateMessage message, Money amount, String parameterName)
	{
		message.addParameter(parameterName, amount.getDecimal().toString());
	}

	/**
	 * �������� ���� "������� ����"
	 * @return ���� "������� ����"
	 */
	static Field createIdentifierField()
	{
		return createIdentifierField("������� ����");
	}

	/**
	 * �������� ���� RecIdentifier � ��������� ���������� �����
	 * @return ���� RecIdentifier
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
	 * �������� ���� "����� ��������"
	 * @return ���� "����� ��������"
	 */
	static Field createFlatNumberField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.FLAT_NUMBER_FIELD_NAME);
		field.setName("����� ��������");
		field.setRequired(false);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * �������� ���� "������� ������"
	 * @return ���� "������� ������"
	 */
	static Field createTarifVersionField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.TARIFF_VAR_FIELD_NAME);
		field.setName("������� ������");
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
	 * �������� ���� "���� �����"
	 * @param tarifVersion ������� ������
	 * @return ���� "���� �����"
	 */
	static Field createTarifZone(String tarifVersion)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.TARIFF_ZONE_FIELD_NAME);
		field.setName("���� �����");
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
	 * �������� ���� "������ ������"
	 * @return ���� "������ ������"
	 */
	static Field createPeriodField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.PERIOD_FIELD_NAME);
		field.setName("������ ������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(false);

		List<FieldValidationRule> validationRules = new ArrayList<FieldValidationRule>();
		FieldValidationRuleImpl   validationRule  = new FieldValidationRuleImpl();

		validationRule.setErrorMessage("����������, ������� ������ � ������� ��/����.");
		validationRule.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regexp", "(0[0-9]|1[0-2])/[0-9]{4}");
		validationRule.setParameters(map);

		validationRules.add(validationRule);
		field.setFieldValidationRules(validationRules);

		return field;
	}

	/**
	 * �������� ���� "�������������"
	 * @return ���� "�������������"
	 */
	static Field createDebtField()
	{
		CommonField field = new CommonField();
		field.setExternalId(Constants.DEBT_FIELD_NAME);
		field.setName("�������������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setKey(true);
		field.setRequiredForBill(true);

		return field;
	}

	/**
	 * �������� ���� "�������� ������"
	 * @param debtRows ������ ��������� ������
	 * @return ���� "�������� ������"
	 */
	static Field createDebtRowField(List<DebtRow> debtRows)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.DEBT_ROW_FIELD_NAME);
		field.setName("�������� ������");
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
	 * �������� ���� "���������� ��������� ��������"
	 * @return ���� "���������� ��������� ��������"
	 */
	public static Field createIndicationFieldField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.LAST_INDICATION_FIELD_NAME);
		field.setName("���������� ��������� ��������");
		field.setRequired(true);
		field.setEditable(false);
		field.setVisible(true);
		field.setDefaultValue("001");
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 * �������� ���� "������� ��������� ��������"
	 * @return ���� "������� ��������� ��������"
	 */
	public static Field createCurrentIndicationField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.CURRENT_INDICATION_FIELD_NAME);
		field.setName("������� ��������� ��������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);

		return field;
	}

	/**
	 * �������� ���������������� ���������� ����
	 * @param externalId ������� �������������
	 * @param name ��������� ���
	 * @param initialValue ��������� ��������
	 * @param groupName �������� ������
	 * @return �������������� ����
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
	 * �������� ��������� �������� ���� �� ������ ����������� ����� �� �����.
	 * @param payment - ������
	 * @param name - ��� ����
	 * @return - ��������� ��������
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
