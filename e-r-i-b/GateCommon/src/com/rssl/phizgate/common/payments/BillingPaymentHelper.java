package com.rssl.phizgate.common.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 * ������ ��� ��������� ����������� ��������
 */
public class BillingPaymentHelper
{
	public static final String DEFAULT_AMOUNT_FIELD_ID                      = "amount";
	public static final String ID_FROM_PAYMENT_SYSTEM_FIELD_NAME            = "PaymentSystemId";    //���� �� ������������ �������������� ������� �� ������� �������
	public static final String REQUEST_BILLING_ATTRIBUTES_FIELD_NAME        = "q3eb2fZ69";          //����, �� �������� ���������� ���������� �� ������ ���. ���������� �������
 	/**
	 * ��� ���� ������� ����� ��� ������������ �������������� �������� ������� �����.
	 */
	public static final String MAIN_SUM_FIELD_NAME_FIELD = "main-sum-field-name-field-inner";

	private static final String RBC_CODE_SUFFIX = "-rbc";
	/**
	 * �������� ����������� ���� �� ������ ����������� ����� �� �������������.
	 * @param extendedFields ����������� ����
	 * @param fieldId ��� ����
	 * @return �������� ���� ��� null, ���� ����������.
	 */
	public static Field getFieldById(List<Field> extendedFields, String fieldId)
	{
		if (extendedFields == null)
		{
			return null;
		}
		for (Field field : extendedFields)
		{
			if (fieldId.equals(field.getExternalId()))
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * @param payment ������
	 * @return �������� �� ������ � ����������� ������� ���
	 */
	public static boolean isRBCBilling(AbstractPaymentSystemPayment payment)
	{
		if (payment == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		//��� �������� ����� ������ ������
		String billingCode = payment.getBillingCode();
		return StringHelper.isNotEmpty(billingCode) && billingCode.endsWith(RBC_CODE_SUFFIX);
	}

	/**
	 * �������� ����������� ���� �� ������� �� �����.
	 * @param payment ������
	 * @param fieldId ��� ����
	 * @return �������� ���� ��� null, ���� ����������.
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public static Field getFieldById(AbstractPaymentSystemPayment payment, String fieldId) throws GateException
	{
		if (payment == null)
		{
			return null;
		}
		try
		{
			return getFieldById(payment.getExtendedFields(), fieldId);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� ���� �� � ��������� ������������� .
	 * �������� ��������� �� �������� �������������� ����.
	 * @param payment ������
	 * @param field ����. �� ����� ���� null.
	 * @return ����/���
	 */
	public static boolean hasField(AbstractPaymentSystemPayment payment, Field field) throws GateException
	{
		if (field == null)
		{
			throw new IllegalArgumentException("�������� field �� ����� ���� null");
		}
		return getFieldById(payment, field.getExternalId()) != null;
	}

	/**
	 * �������� ���� � �������� ������� ����� �� ����������� ����� �������
	 * @param payment ������
	 * @return ����� � ��������� ������� ����� ��� null, ���� ����������
	 * @throws GateException
	 */
	public static Field getMainSumField(AbstractPaymentSystemPayment payment) throws GateException
	{
		if (payment == null)
		{
			return null;
		}
		try
		{
			return getMainSumField(payment.getExtendedFields());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ���� � �������� ������� ����� �� ������ ����������� �����
	 * @param extendedFields ����������� ����
	 * @return ����� � ��������� ������� ����� ��� null, ���� ����������
	 */
	public static Field getMainSumField(List<Field> extendedFields)
	{
		if (extendedFields == null)
		{
			return null;
		}
		for (Field field : extendedFields)
		{
			if (field.isMainSum())
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * ������������ �������������� ������� �� ������� �������
	 * (������������ ��� ����, ����� ���������� �������������� �������� ����,
	 * ����� ������ ������ send ������� ����� ������� �� �������� �������� ������� �������)
	 * @param document ��������
	 * @return �������������
	 */
	public static String generateIdFromPaymentSystem(GateDocument document) throws GateException
	{
		return new StringBuffer()
				.append(ID_FROM_PAYMENT_SYSTEM_FIELD_NAME).append(document.getInternalOwnerId())
				.append("-").append(document.getDocumentNumber()).toString();
	}


	/**
	 * �������� ��� ���� ��� ����� ������� �����
	 * ����� ����� ��� "�����" � ������� ����������� "amount".
	 * @return ���� ��� ����� ������� �����
	 */
	public static CommonField createAmountField()
	{
		return createAmountField("�����", true, null);
	}

	/**
	 * @param mainSumFieldName ��� ���� � ������� ������.
	 * @return ������� ����, ����������� �� ���� � ������� ������.
	 */
	public static CommonField createMainSumFieldNameField(String mainSumFieldName)
	{
		//�� �� ����� ������� ���� ������� �����, ������� ����� ������� ����, � ������� �������� ��� ���� � ������� ������.
		CommonField field = new CommonField();
		field.setExternalId(MAIN_SUM_FIELD_NAME_FIELD);
		field.setType(FieldDataType.string);
		field.setDefaultValue(mainSumFieldName);
		field.setValue(mainSumFieldName);
		field.setVisible(false);
		return field;
	}

	public static CommonField createAmountField(String name, boolean isEditable, String defaultValue)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setExternalId(DEFAULT_AMOUNT_FIELD_ID);
		field.setName(name);
		field.setRequired(true);
		field.setEditable(isEditable);
		field.setVisible(true);
		field.setMainSum(true);
		field.setDefaultValue(defaultValue);
		return field;
	}

	/**
	 * @return ����, �� �������� ���������� ���������� �� ������ ���. ���������� �������.
	 */
	public static Field createRequestBillingAttributesField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(REQUEST_BILLING_ATTRIBUTES_FIELD_NAME);
		field.setName("����, �� �������� ���������� ���������� �� ������ ���. ���������� �������");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setValue("step_1");
		field.setDefaultValue("step_1");

		return field;
	}

	/**
	 * ����������/������������� ����� ��� ����������� �������
	 * @param payment ������
	 */
	public static void prepareFields(AbstractPaymentSystemPayment payment) throws GateException
	{
		/*
			��������� ���������� �� ����������� ������� ���� ����� ��������� visible=false, editable=true.
			�� ��� ����������� �� �� ����� ���������� �������, ����� ���� ���� ������������, �.�. visible=true.
		*/
		try
		{
			for (Field field : payment.getExtendedFields())
			{
				if (field instanceof CommonField)
				{
					CommonField cf = (CommonField) field;
					if (cf.isEditable() && !cf.isVisible())
					{
						cf.setVisible(true);
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
	 * ����������� ������ ��� ����� � ����(field.externalId - ����, field - ��������)
	 * @param extendedFields ������ ��� �����
	 * @return ���(field.externalId - ����, field - ��������)
	 * @throws GateException
	 */
	public static Map<String, Field> convertExtendedFieldsToMap(List<Field> extendedFields) throws GateException
	{
		if(CollectionUtils.isEmpty(extendedFields))
			return Collections.emptyMap();

		Map<String, Field> result = new HashMap<String, Field>();

		for(Field field : extendedFields)
			result.put(field.getExternalId(), field);

		return result;
	}

	/**
	 * ��������� ������� �������������� ����� �������
	 * @param payment ������
	 * @return ������ �������������� �����
	 * @throws GateException
	 */
	public static List<Field> getExtendedFields(AbstractPaymentSystemPayment payment) throws GateException
	{
		try
		{
			return payment.getExtendedFields();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ������� ��������
	 * @param regexp ���������� ��������� ��� ��������
	 * @param errorMessage ��������� �� ������
	 * @return ������� ��������
	 */
	public static FieldValidationRule createRegexpValidator(String regexp, String errorMessage)
	{
		FieldValidationRuleImpl validator = new FieldValidationRuleImpl();
		validator.setErrorMessage(errorMessage);
		validator.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);
		validator.setParameters(Collections.<String, Object>singletonMap("regexp", regexp));
		return validator;
	}

	/**
	 * �������� ���� � �����������
	 * @param externalId ������� �������������
	 * @param name ���(����� ��������� ����� [url][/url] ��� ����������� ������ �� ����������)
	 * @param agreementName ��� ����������
	 * @return �������������� ����
	 */
	public static CommonField createAgreementField(String externalId, String name, String agreementName)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.choice);
		field.setExternalId(externalId);
		field.setName(name);
		field.setExtendedDescId(agreementName);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setHideInConfirmation(true);
		field.setRequiredForBill(false);
		field.setSaveInTemplate(false);
		return field;
	}
}
