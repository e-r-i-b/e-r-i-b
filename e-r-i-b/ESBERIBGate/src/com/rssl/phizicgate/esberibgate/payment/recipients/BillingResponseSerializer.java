package com.rssl.phizicgate.esberibgate.payment.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizicgate.esberibgate.messaging.BaseResponseSerializer;
import com.rssl.phizicgate.esberibgate.ws.generated.AttributeLength_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.RecipientRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Requisite_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Validator_Type;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class BillingResponseSerializer extends BaseResponseSerializer
{
	public List<Field> fillFields(RecipientRec_Type recipientRec) throws GateException, GateLogicException
	{
		List<Field> list = new ArrayList<Field>();
		Requisite_Type[] requisites = recipientRec.getRequisites();

		if (!ArrayUtils.isEmpty(requisites))
		{
			boolean isMainSumDetected = false;
			for (Requisite_Type requisite : requisites)
			{
				Field field = fillField(requisite);
				if (field.isMainSum() && isMainSumDetected)
				   throw new GateException("Для получателя " + recipientRec.getName() + " в ответе пришло более одного поля главной суммы");

				isMainSumDetected = isMainSumDetected || field.isMainSum();
				list.add(field);
			}
		}
		return list;
	}

	protected Field fillField(Requisite_Type requisite) throws GateException, GateLogicException
	{
		FieldDataType type = FieldDataType.fromValue(requisite.getType());
		CommonField result = new CommonField();
		result.setName(requisite.getNameVisible());
		result.setExternalId(requisite.getNameBS());
		result.setHint(requisite.getDescription());
		result.setDescription(requisite.getComment());
		result.setType(type);
		if (type == FieldDataType.number)
		{
			result.setNumberPrecision(convertToInteger(requisite.getNumberPrecision()));
		}
		result.setRequired(getNullBooleanValue(requisite.getIsRequired(), false));
		result.setMainSum(getNullBooleanValue(requisite.getIsSum(), false));
		result.setKey(getNullBooleanValue(requisite.getIsKey(), false));
		result.setEditable(getNullBooleanValue(requisite.getIsEditable(), false));
		result.setVisible(getNullBooleanValue(requisite.getIsVisible(), false));
		result.setRequiredForBill(getNullBooleanValue(requisite.getIsForBill(),false));
		result.setRequiredForConformation(getNullBooleanValue(requisite.getIncludeInSMS(), false));
		result.setSaveInTemplate(getNullBooleanValue(requisite.getSaveInTemplate(),false));
		result.setHideInConfirmation(getNullBooleanValue(requisite.getHideInConfirmation(), false));

		String[] requisiteTypeArray = requisite.getRequisiteTypes();
		if (ArrayUtils.isNotEmpty(requisiteTypeArray))
		{
			List<RequisiteType> requisiteTypeList = new ArrayList<RequisiteType>(requisiteTypeArray.length);
			for (String requisiteType : requisiteTypeArray)
			{
				requisiteTypeList.add(RequisiteType.fromValue(requisiteType));
			}
			result.setRequisiteTypes(requisiteTypeList);
		}

		AttributeLength_Type length = requisite.getAttributeLength();
		if (length != null)
		{
			result.setMaxLength(converToLong(length.getMaxLength()));
			result.setMinLength(converToLong(length.getMinLength()));
		}
		result.setFieldValidationRules(processValidators(requisite.getValidators()));

		if (type == FieldDataType.list || type == FieldDataType.set)
		{
			result.setValues(convertToListValues(requisite.getMenu()));
		}
		result.setValue(processEnteredData(requisite));
		result.setDefaultValue(requisite.getDefaultValue());
		result.setError(requisite.getError());
		return result;
	}

	private List<ListValue> convertToListValues(String[] menu)
	{
		if (menu == null)
		{
			return null;
		}
		List<ListValue> result = new ArrayList<ListValue>();
		for (String item : menu)
		{
			result.add(new ListValue(item,item));
		}
		return result;
	}

	private Object processEnteredData(Requisite_Type requisite) throws GateException
	{
		FieldDataType type = FieldDataType.fromValue(requisite.getType());
		String[] data = requisite.getEnteredData();
		if (data == null)
		{
			return null;
		}
		if (data.length == 1)
		{
			return data[0];
		}
		if (type == FieldDataType.set)
		{
			//значения для типа set передаются в виде листа данных
			StringBuffer values = new StringBuffer();
			values.append(data[0]);

			for (int i = 1; i < data.length; i++)
			{
				values.append("@").append(data[i]);
			}

			return values.toString();
		}
		if (data.length > 1)
		{
			throw new GateException("Для поля с типом " + type + " пришло " + data.length + " значений");
		}
		return null;
	}

	private List<FieldValidationRule> processValidators(Validator_Type[] validators)
	{
		if (validators == null)
		{
			return null;
		}
		List<FieldValidationRule> result = new ArrayList<FieldValidationRule>();
		for (Validator_Type validator : validators)
		{
			result.add(processValidator(validator));
		}
		return result;
	}

	private FieldValidationRule processValidator(Validator_Type validator)
	{
		FieldValidationRuleImpl result = new FieldValidationRuleImpl();
		result.setErrorMessage(validator.getMessage());
		result.setFieldValidationRuleType(validator.getType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(validator.getType(), validator.getParameter());
		result.setParameters(map);
		return result;
	}

	//BUG026291, на случай если поле реквизита null в Boolean.
	//если значение реквизита null то используем значение по умолчанию из спецификации
	private boolean getNullBooleanValue(Boolean value, boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
}
