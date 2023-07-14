package com.rssl.phizgate.common.payments;

import com.google.gson.stream.JsonWriter;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author krenev
 * @ created 11.06.15
 * @ $Author$
 * @ $Revision$
 *
 * —ериалайзер доп. полей в gson
 */
public class ExtendedFieldsGSONSerializer
{
	private List<? extends Field> fields;

	/**
	 * ctor
	 * @param fields список полей
	 */
	public ExtendedFieldsGSONSerializer(List<? extends Field> fields)
	{
		this.fields = fields;
	}

	/**
	 * @return сериализованный в строку список полей
	 */
	public String serialize() throws IOException
	{
		StringWriter out = new StringWriter();
		JsonWriter writer = new JsonWriter(out);
		try
		{
			writer.setSerializeNulls(false);
			serializeFields(writer);
		}
		finally
		{
			writer.close();
		}

		return out.toString();
	}

	private void serializeFields(JsonWriter writer) throws IOException
	{
		writer.beginArray();
		for (Field field : fields)
		{
			serializeField(writer, field);
		}
		writer.endArray();
	}

	private void serializeField(JsonWriter writer, Field field) throws IOException
	{
		writer.beginObject();
		writer.name(ATTRIBUTE_NAME_BS_FIELD).value(StringHelper.getNullIfEmpty(field.getExternalId()));
		writer.name(ATTRIBUTE_DESCRIPTION_FIELD).value(StringHelper.getNullIfEmpty(field.getDescription()));
		writer.name(ATTRIBUTE_EXTENDED_DESCRIPTION_ID_FIELD).value(StringHelper.getNullIfEmpty(field.getExtendedDescId()));
		writer.name(ATTRIBUTE_NAME_VISIBLE_FIELD).value(StringHelper.getNullIfEmpty(field.getName()));
		writer.name(COMMENT_FIELD).value(StringHelper.getNullIfEmpty(field.getHint()));
		writer.name(POPUP_HINT).value(StringHelper.getNullIfEmpty(field.getPopupHint()));

		writer.name(TYPE_FIELD).value(serializeEnum(field.getType()));

		if (FieldDataType.number == field.getType())
		{
			NumberField numberField = (NumberField) field;
			writer.name(ATTRIBUTE_NUMBER_PRECISION_FIELD).value(numberField.getNumberPrecision());
		}

		if (FieldDataType.calendar == field.getType())
		{
			CalendarField calendarField = (CalendarField) field;
			writer.name(ATTRIBUTE_DATE_PERIOD_FIELD).value(serializeEnum(calendarField.getPeriod()));
		}

		if (FieldDataType.set == field.getType() || FieldDataType.list == field.getType() || FieldDataType.graphicset == field.getType())
		{
			ListField listField = (ListField) field;
			serializeMenu(writer, listField.getValues());
			if(FieldDataType.graphicset == field.getType())
			{
				GraphicSetField graphicSetField = (GraphicSetField) field;
				writer.name(ATTRIBUTE_GRAPHIC_TEMPLATE_NAME_FIELD).value(StringHelper.getNullIfEmpty(graphicSetField.getGraphicTemplateName()));
			}
		}

		writer.name(ATTRIBUTE_BINDATA_FIELD).value(serializeBindata(field));

		writer.name(ATTRIBUTE_GROUP_NAME_FIELD).value(StringHelper.getNullIfEmpty(field.getGroupName()));
		writer.name(ATTRIBUTE_MASK_FIELD).value(StringHelper.getNullIfEmpty(field.getMask()));
		writer.name(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD).value(serializeEnum(field.getBusinessSubType()));

		serializeRequisiteTypes(writer, field.getRequisiteTypes());

		writer.name(ATTRIBUTE_MAX_LENGTH_FIELD).value(field.getMaxLength());
		writer.name(ATTRIBUTE_MIN_LENGTH_FIELD).value(field.getMinLength());

		serializeFieldValidationRules(writer, field.getFieldValidationRules());

		writer.name(ATTRIBUTE_DEFAULT_VALUE_FIELD).value(StringHelper.getNullIfEmpty(field.getDefaultValue()));
		writer.name(ATTRIBUTE_ERROR_FIELD).value(StringHelper.getNullIfEmpty(field.getError()));
		writer.endObject();
	}

	/**
	 * ƒл€ полей метаинформации из списка ниже измен€етс€ формат ранени€ данных:
	 * IsRequired, IsSum, IsKey, IsEditable, IsVisible, IsForBill, IncludeInSMS, SaveInTemplate, HideInConfirmation, .
	 * ѕор€док полей фиксируетс€ списком выше.
	 * «начени€ каждого параметра из значений true/false преобразуютс€ в 1/0, далее конкатенируютс€ в одну строку и преобразуютс€ в шестнадцатеричный формат. Ќапример, 101101101 преобразуютс€ в 16D.ѕолучившеес€ значение сохран€етс€ с тегом "bindata".
	 * @param field поле
	 * @return хекс-представление булевых полей
	 */
	private String serializeBindata(Field field)
	{
		return toHex(field.isRequired(), field.isMainSum(), field.isKey(), field.isEditable(), field.isVisible(), field.isRequiredForBill(), field.isRequiredForConformation(), field.isSaveInTemplate(), field.isHideInConfirmation());
	}

	private String toHex(boolean... bools)
	{
		long result = 0;
		int pos = bools.length;
		for (boolean bool : bools)
		{
			pos--;
			if (bool)
			{
				result |= result | (1 << pos);
			}
		}
		return Long.toHexString(result);
	}

	private void serializeFieldValidationRules(JsonWriter writer, List<FieldValidationRule> fieldValidationRules) throws IOException
	{
		if (CollectionUtils.isEmpty(fieldValidationRules))
		{
			return;
		}

		writer.name(VALIDATORS_FIELD);
		writer.beginArray();
		for (FieldValidationRule validationRule : fieldValidationRules)
		{
			writer.beginObject();
			writer.name(VALIDATOR_TYPE_FIELD).value(serializeEnum(validationRule.getFieldValidationRuleType()));
			writer.name(VALIDATOR_MESSAGE_FIELD).value(StringHelper.getNullIfEmpty(validationRule.getErrorMessage()));
			Map<String, Object> parameters = validationRule.getParameters();
			if (MapUtils.isNotEmpty(parameters))
			{
				writer.name(VALIDATOR_PARAMETER_FIELD);
				writer.beginArray();
				for (String key : parameters.keySet())
				{
					writer.value(StringHelper.getNullIfEmpty((String) parameters.get(key)));
				}
				writer.endArray();
			}
			writer.endObject();
		}
		writer.endArray();
	}

	private void serializeRequisiteTypes(JsonWriter writer, List<RequisiteType> requisiteTypes) throws IOException
	{
		if (CollectionUtils.isEmpty(requisiteTypes))
		{
			return;
		}
		writer.name(ATTRIBUTE_REQUISITE_TYPES);
		writer.beginArray();
		for (RequisiteType requisiteType : requisiteTypes)
		{
			writer.value(StringHelper.getNullIfEmpty(requisiteType.getDescription()));
		}
		writer.endArray();
	}

	private void serializeMenu(JsonWriter writer, List<ListValue> values) throws IOException
	{
		if (CollectionUtils.isEmpty(values))
		{
			return;
		}
		writer.name(ATTRIBUTE_MENU_FIELD);
		writer.beginArray();
		for (ListValue listValue : values)
		{
			writer.beginObject();
			writer.name(ID_FIELD).value(trim(listValue.getId()));
			writer.name(VALUE_FIELD).value(trim(listValue.getValue()));
			writer.endObject();
		}
		writer.endArray();
	}

	private String trim(String value)
	{
		if (value == null)
		{
			return null;
		}
		return StringHelper.getNullIfEmpty(value.trim());
	}

	private String serializeEnum(Enum e)
	{
		if (e == null)
		{
			return null;
		}
		return e.name();
	}

	private Number serializeBool(Boolean value)
	{
		if (value == null)
		{
			return null;
		}
		return value ? 1 : 0;
	}
}
