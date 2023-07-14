package com.rssl.phizicgate.asbc.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.asbc.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCRequestHelper
{
	private GateFactory factory;

	public ASBCRequestHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * ������������ � ������� ������ findCredits.
	 * @param providerId ������������� ����������
	 * @param keyField �������� �����
	 * @return ����� �� �� �� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public final Document makeFindCreditsRequest(String providerId, Field keyField) throws GateException, GateLogicException
	{
		if (keyField == null)
		{
			throw new GateException("�� �������� ���������������� ����");
		}
		Object account = keyField.getValue();
		if (account == null)
		{
			throw new GateLogicException("�� ������ �������� ��������� ����");
		}
		WebBankServiceFacade serviceFacade = factory.service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest("findCredits");
		message.addParameter("providerId", providerId);
		message.addParameter("account", account);
		return serviceFacade.sendOnlineMessage(message, null);
	}

	/**
	 * �������� �� ������� �������� ����
	 * @param payment ������
	 * @return �������� ���� ��� null
	 * @throws GateException
	 */
	public static Field findKeyFields(AccountPaymentSystemPayment payment) throws GateException
	{
		try
		{
			for (Field field : payment.getExtendedFields())
			{
				if (field.isKey())
				{
					return field;
				}
			}
			return null;
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� ���� ��������� ���������� �� ���������
	 * ���� � ���� �������� ���� NameBS ��������� � ��������� ���� �� �������� xml, �� ���������
	 * ���� ��������� �� ������ ����
	 * @param payment ������
	 * @param result ����� ����
	 */
	public void updateFieldsValue(AccountPaymentSystemPayment payment, Document result) throws GateException
	{
		try
		{
			Element element = result.getDocumentElement();
			for (Field field : payment.getExtendedFields())
			{
				String asbcValue = XmlHelper.getSimpleElementValue(element, field.getExternalId());
				if (!StringHelper.isEmpty(asbcValue))
				{
					if (field.getValue() == null)
					{
						((CommonField) field).setDefaultValue(asbcValue);
					}
					else
					{
						field.setValue(asbcValue);
					}
				}
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ���������� ���� "�������������"
	 * @param nodeList -������ ����� result_element � ����� � �������������
	 * @return ��������� ���� "�������������"
	 */
	Field createDebtListField(NodeList nodeList) throws GateException
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(Constants.DEBT_FIELD_NAME);
		field.setName("�������������");
		field.setRequired(true);
		field.setRequiredForBill(true);
		field.setEditable(true);
		field.setVisible(true);
		List<ListValue> values = new ArrayList<ListValue>();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element node = (Element) nodeList.item(i);
			String debtCode = XmlHelper.getSimpleElementValue(node, "creditId");

			StringBuffer debtInfo = new StringBuffer();
			debtInfo.append(XmlHelper.getSimpleElementValue(node, "infoPu"));
			debtInfo.append(" ");
			debtInfo.append(getDebt(node));
			debtInfo.append(" ");
			debtInfo.append(factory.service(CurrencyService.class).getNationalCurrency().getCode());
			values.add(new ListValue(debtInfo.toString(), debtCode));
		}
		field.setValues(values);
		return field;
	}

	/**
	 * ������� ������� ���� � ���� � �������������
	 * @param debt �������������
	 * @return ����
	 */
	Field createSingleDebtField(BigDecimal debt)
	{
		// ���� ��� ����������� ������������
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setName("�������������");
		field.setExternalId(Constants.DISPLAY_DEBT_FIELD_NAME);
		field.setEditable(false);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setDefaultValue(debt.toString());
		return field;
	}

	/**
	 * ������� ������� ���� � ����� ������������
	 * @param debtCode ��� �������������
	 * @return ����
	 */
	Field createHiddenDeptField(String debtCode)
	{
		// ������� ���� � ����� �������������
		CommonField fieldhidden = new CommonField();
		fieldhidden.setType(FieldDataType.string);
		fieldhidden.setExternalId(Constants.DEBT_FIELD_NAME);
		fieldhidden.setName("��� �������������");
		fieldhidden.setEditable(false);
		fieldhidden.setRequired(true);
		fieldhidden.setVisible(false);
		fieldhidden.setDefaultValue(debtCode);
		return fieldhidden;
	}

	/**
	 * �������� ���� "������������� ����������"
	 * @param value ��������
	 * @return ���� "������������� ����������"
	 */
	CommonField createTransactionIdField(String value)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.TRANSACTIONAL_ID_FIELD_NAME);
		field.setName("������������� ����������");
		field.setEditable(false);
		field.setRequired(false);
		field.setVisible(false);
		field.setDefaultValue(value);
		return field;
	}

	Field createKeyField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.KEY_FIELD_NAME);
		field.setName("������� ����");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setKey(true);
		return field;
	}

	BigDecimal getDebt(Element element) throws GateException
	{
		try
		{
			String debt = XmlHelper.getSimpleElementValue(element, "balance");
			return NumericUtil.parseBigDecimal(debt).divide(new BigDecimal(100));
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}

	Field createAmountField(BigDecimal debt)
	{
		CommonField field = BillingPaymentHelper.createAmountField();

		if (debt!=null && debt.compareTo(BigDecimal.ZERO) > 0)
		{
			field.setDefaultValue(debt.toString());
		}
		return field;
	}
}