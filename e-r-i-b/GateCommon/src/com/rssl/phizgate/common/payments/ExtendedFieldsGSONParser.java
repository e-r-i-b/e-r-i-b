package com.rssl.phizgate.common.payments;

import com.google.gson.stream.JsonReader;
import com.rssl.phizgate.common.documents.payments.fields.StringFieldValidationParameter;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.payments.systems.recipients.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author krenev
 * @ created 11.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedFieldsGSONParser
{
	private String fields;

	public ExtendedFieldsGSONParser(String fields)
	{
		this.fields = fields;
	}

	public List<Field> parse() throws IOException
	{
		JsonReader reader = new JsonReader(new StringReader(fields));
		try
		{
			return parseFields(reader);
		}
		finally
		{
			reader.close();
		}
	}

	private List<Field> parseFields(JsonReader reader) throws IOException
	{
		List<Field> messages = new ArrayList<Field>();

		reader.beginArray();
		while (reader.hasNext())
		{
			messages.add(parseField(reader));
		}
		reader.endArray();
		return messages;
	}

	private Field parseField(JsonReader reader) throws IOException
	{
		reader.beginObject();
		CommonField field = new CommonField();
		while (reader.hasNext())
		{
			String name = reader.nextName();
			if (ATTRIBUTE_NAME_BS_FIELD.equals(name))
			{
				field.setExternalId(reader.nextString());
			}
			else if (ATTRIBUTE_DESCRIPTION_FIELD.equals(name))
			{
				field.setDescription(reader.nextString());
			}
			else if (ATTRIBUTE_EXTENDED_DESCRIPTION_ID_FIELD.equals(name))
			{
				field.setExtendedDescId(reader.nextString());
			}
			else if (ATTRIBUTE_NAME_VISIBLE_FIELD.equals(name))
			{
				field.setName(reader.nextString());
			}
			else if (COMMENT_FIELD.equals(name))
			{
				field.setHint(reader.nextString());
			}
			else if (POPUP_HINT.equals(name))
			{
				field.setPopupHint(reader.nextString());
			}
			else if (TYPE_FIELD.equals(name))
			{
				field.setType(FieldDataType.fromValue(reader.nextString()));
			}
			else if (ATTRIBUTE_NUMBER_PRECISION_FIELD.equals(name))
			{
				field.setNumberPrecision(reader.nextInt());
			}
			else if (ATTRIBUTE_DATE_PERIOD_FIELD.equals(name))
			{
				field.setPeriod(CalendarFieldPeriod.valueOf(reader.nextString()));
			}
			else if (ATTRIBUTE_MENU_FIELD.equals(name))
			{
				field.setValues(parseMenu(reader));
			}
			else if (ATTRIBUTE_GRAPHIC_TEMPLATE_NAME_FIELD.equals(name))
			{
				field.setGraphicTemplateName(reader.nextString());
			}
			else if (ATTRIBUTE_BINDATA_FIELD.equals(name))
			{
				setBindata(field, reader.nextString());
			}
			else if (ATTRIBUTE_IS_REQUIRED_FIELD.equals(name))
			{
				field.setRequired(readBool(reader));
			}
			else if (ATTRIBUTE_IS_EDITABLE_FIELD.equals(name))
			{
				field.setEditable(readBool(reader));
			}
			else if (ATTRIBUTE_IS_VISIBLE_FIELD.equals(name))
			{
				field.setVisible(readBool(reader));
			}
			else if (ATTRIBUTE_IS_MAIN_SUM_FIELD.equals(name))
			{
				field.setMainSum(readBool(reader));
			}
			else if (ATTRIBUTE_IS_FOR_BILL_FIELD.equals(name))
			{
				field.setRequiredForBill(readBool(reader));
			}
			else if (ATTRIBUTE_IS_KEY_FIELD.equals(name))
			{
				field.setKey(readBool(reader));
			}
			else if (ATTRIBUTE_REQUIRED_FOR_CONFIRMATION_FIELD.equals(name))
			{
				field.setRequiredForConformation(readBool(reader));
			}
			else if (ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD.equals(name))
			{
				field.setSaveInTemplate(readBool(reader));
			}
			else if (ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD.equals(name))
			{
				field.setHideInConfirmation(readBool(reader));
			}
			else if (ATTRIBUTE_GROUP_NAME_FIELD.equals(name))
			{
				field.setGroupName(reader.nextString());
			}
			else if (ATTRIBUTE_MASK_FIELD.equals(name))
			{
				field.setMask(reader.nextString());
			}
			else if (ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD.equals(name))
			{
				field.setBusinessSubType(BusinessFieldSubType.valueOf(reader.nextString()));
			}
			else if (ATTRIBUTE_MAX_LENGTH_FIELD.equals(name))
			{
				field.setMaxLength(reader.nextLong());
			}
			else if (ATTRIBUTE_MIN_LENGTH_FIELD.equals(name))
			{
				field.setMinLength(reader.nextLong());
			}
			else if (ATTRIBUTE_DEFAULT_VALUE_FIELD.equals(name))
			{
				field.setDefaultValue(reader.nextString());
			}
			else if (ATTRIBUTE_ERROR_FIELD.equals(name))
			{
				field.setError(reader.nextString());
			}
			else if (ATTRIBUTE_REQUISITE_TYPES.equals(name))
			{
				field.setRequisiteTypes(readRequisiteTypes(reader));
			}
			else if (VALIDATORS_FIELD.equals(name))
			{
				field.setFieldValidationRules(readFieldValidationRules(reader));
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return field;
	}

	/**
	 * Для полей метаинформации из списка ниже изменяется формат ранения данных:
	 * IsRequired, IsSum, IsKey, IsEditable, IsVisible, IsForBill, IncludeInSMS, SaveInTemplate, HideInConfirmation, .
	 * Порядок полей фиксируется списком выше.
	 * Значения каждого параметра из значений true/false преобразуются в 1/0, далее конкатенируются в одну строку и преобразуются в шестнадцатеричный формат. Например, 101101101 преобразуются в 16D.Получившееся значение сохраняется с тегом "bindata".
	 * @param field поле
	 * @param bindata хекс-представление булевых полей
	 */
	private void setBindata(CommonField field, String bindata)
	{
		long data = Long.valueOf(bindata, 16);
		int i = 0;
		field.setHideInConfirmation(getBitBool(data, i++));
		field.setSaveInTemplate(getBitBool(data, i++));
		field.setRequiredForConformation(getBitBool(data, i++));
		field.setRequiredForBill(getBitBool(data, i++));
		field.setVisible(getBitBool(data, i++));
		field.setEditable(getBitBool(data, i++));
		field.setKey(getBitBool(data, i++));
		field.setMainSum(getBitBool(data, i++));
		field.setRequired(getBitBool(data, i++));
	}

	private boolean getBitBool(long data, int i)
	{
		return ((data >> i) & 1) == 1;
	}
	private List<FieldValidationRule> readFieldValidationRules(JsonReader reader) throws IOException
	{
		List<FieldValidationRule> fieldValidationRules = new ArrayList<FieldValidationRule>();

		reader.beginArray();
		while (reader.hasNext())
		{
			fieldValidationRules.add(readFieldValidationRule(reader));
		}
		reader.endArray();
		return fieldValidationRules;
	}

	private FieldValidationRule readFieldValidationRule(JsonReader reader) throws IOException
	{
		FieldValidationRuleImpl validationRule = new FieldValidationRuleImpl();

		reader.beginObject();
		while (reader.hasNext())
		{
			String name = reader.nextName();
			if (VALIDATOR_TYPE_FIELD.equals(name))
			{
				validationRule.setFieldValidationRuleType(reader.nextString());
			}
			else if (VALIDATOR_MESSAGE_FIELD.equals(name))
			{
				validationRule.setErrorMessage(reader.nextString());
			}
			else if (VALIDATOR_PARAMETER_FIELD.equals(name))
			{
				validationRule.setFieldValidators(readParameters(reader, validationRule.getFieldValidationRuleType()));
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return validationRule;
	}

	private Map<String, Object> readParameters(JsonReader reader, FieldValidationRuleType type) throws IOException
	{
		Map<String, Object> mapValidatorParameter = new HashMap<String, Object>();
		reader.beginArray();
		while (reader.hasNext())
		{
			mapValidatorParameter.put(type.name(), new StringFieldValidationParameter(reader.nextString()));
		}
		reader.endArray();
		return mapValidatorParameter;
	}

	private List<RequisiteType> readRequisiteTypes(JsonReader reader) throws IOException
	{
		List<RequisiteType> requisiteTypes = new ArrayList<RequisiteType>();
		reader.beginArray();
		while (reader.hasNext())
		{
			requisiteTypes.add(RequisiteType.fromValue(reader.nextString()));
		}
		reader.endArray();
		return requisiteTypes;
	}

	private List<ListValue> parseMenu(JsonReader reader) throws IOException
	{
		List<ListValue> values = new ArrayList<ListValue>();

		reader.beginArray();
		while (reader.hasNext())
		{
			values.add(readListValue(reader));
		}
		reader.endArray();
		return values;
	}

	private ListValue readListValue(JsonReader reader) throws IOException
	{
		ListValue listValue = new ListValue();
		reader.beginObject();
		while (reader.hasNext())
		{
			String name = reader.nextName();
			if (ID_FIELD.equals(name))
			{
				listValue.setId(reader.nextString());
			}
			else if (VALUE_FIELD.equals(name))
			{
				listValue.setValue(reader.nextString());
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return listValue;
	}

	private boolean readBool(JsonReader reader) throws IOException
	{
		return reader.nextInt() == 1;
	}
}
