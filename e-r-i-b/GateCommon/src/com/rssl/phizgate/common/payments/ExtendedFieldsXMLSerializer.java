package com.rssl.phizgate.common.payments;

import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author osminin
 * @ created 18.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сериалайзер доп. полей
 */
public class ExtendedFieldsXMLSerializer
{
	private List<? extends Field> fields;

	/**
	 * ctor
	 * @param fields список полей
	 */
	public ExtendedFieldsXMLSerializer(List<? extends Field> fields)
	{
		this.fields = fields;
	}

	/**
	 * @return сериализованный в строку список полей
	 */
	public String serialize()
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();

		builder.openEntityTag(ATTRIBUTES_FIELD);

		for (Field field : fields)
		{
			builder.openEntityTag(ATTRIBUTE_FIELD);
			builder.createEntityTag(ATTRIBUTE_NAME_BS_FIELD, field.getExternalId());
			builder.createEntityTag(ATTRIBUTE_DESCRIPTION_FIELD,  StringHelper.getEmptyIfNull(field.getDescription()));
			builder.createEntityTag(ATTRIBUTE_EXTENDED_DESCRIPTION_ID_FIELD,  StringHelper.getEmptyIfNull(field.getExtendedDescId()));
			builder.createEntityTag(ATTRIBUTE_NAME_VISIBLE_FIELD, StringHelper.getEmptyIfNull(field.getName()));
			builder.createEntityTag(COMMENT_FIELD, StringHelper.getEmptyIfNull(field.getHint()));
			builder.createEntityTag(POPUP_HINT, StringHelper.getEmptyIfNull(field.getPopupHint()));
			builder.createEntityTag(TYPE_FIELD, field.getType().name());

			if (FieldDataType.number == field.getType())
			{
				NumberField numberField = (NumberField) field;
				builder.createEntityTag(ATTRIBUTE_NUMBER_PRECISION_FIELD, StringHelper.getEmptyIfNull(numberField.getNumberPrecision()));
			}

			if (FieldDataType.calendar == field.getType())
			{
				CalendarField calendarField = (CalendarField) field;
				CalendarFieldPeriod period  = calendarField.getPeriod();
				builder.createEntityTag(ATTRIBUTE_DATE_PERIOD_FIELD, period != null ? period.toString() : "");
			}

			if (FieldDataType.set == field.getType() || FieldDataType.list == field.getType() || FieldDataType.graphicset == field.getType())
			{
				ListField listField = (ListField) field;

				if (CollectionUtils.isNotEmpty(listField.getValues()))
				{
					builder.openEntityTag(ATTRIBUTE_MENU_FIELD);

					for (ListValue listValue : listField.getValues())
					{
						builder.openEntityTag(ATTRIBUTE_MENU_ITEM_FIELD);
						builder.createEntityTag(ID_FIELD, !StringHelper.isEmpty(listValue.getId())? listValue.getId().trim() : listValue.getId());
						builder.createEntityTag(VALUE_FIELD, !StringHelper.isEmpty(listValue.getValue())? listValue.getValue().trim() : listValue.getValue());
						builder.closeEntityTag(ATTRIBUTE_MENU_ITEM_FIELD);
					}

					builder.closeEntityTag(ATTRIBUTE_MENU_FIELD);
				}

				if(FieldDataType.graphicset == field.getType())
				{
					builder.createEntityTag(ATTRIBUTE_GRAPHIC_TEMPLATE_NAME_FIELD,
							StringHelper.getEmptyIfNull(((GraphicSetField) field).getGraphicTemplateName()));
				}
			}

			builder.createEntityTag(ATTRIBUTE_IS_REQUIRED_FIELD, StringHelper.getEmptyIfNull(field.isRequired()));
			builder.createEntityTag(ATTRIBUTE_IS_EDITABLE_FIELD, StringHelper.getEmptyIfNull(field.isEditable()));
			builder.createEntityTag(ATTRIBUTE_IS_VISIBLE_FIELD,  StringHelper.getEmptyIfNull(field.isVisible()));
			builder.createEntityTag(ATTRIBUTE_IS_MAIN_SUM_FIELD, StringHelper.getEmptyIfNull(field.isMainSum()));
			builder.createEntityTag(ATTRIBUTE_IS_SUM_FIELD,      String.valueOf(field.getType() == FieldDataType.money));
			builder.createEntityTag(ATTRIBUTE_IS_FOR_BILL_FIELD, StringHelper.getEmptyIfNull(field.isRequiredForBill()));
			builder.createEntityTag(ATTRIBUTE_IS_KEY_FIELD,      StringHelper.getEmptyIfNull(field.isKey()));
			builder.createEntityTag(ATTRIBUTE_GROUP_NAME_FIELD,  StringHelper.getEmptyIfNull(field.getGroupName()));
			builder.createEntityTag(ATTRIBUTE_REQUIRED_FOR_CONFIRMATION_FIELD, StringHelper.getEmptyIfNull(field.isRequiredForConformation()));
			builder.createEntityTag(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD, StringHelper.getEmptyIfNull(field.isSaveInTemplate()));
			builder.createEntityTag(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD, StringHelper.getEmptyIfNull(field.isHideInConfirmation()));
			builder.createEntityTag(ATTRIBUTE_MASK_FIELD, StringHelper.getEmptyIfNull(field.getMask()));
			builder.createEntityTag(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD, StringHelper.getEmptyIfNull(field.getBusinessSubType()));

			if (CollectionUtils.isNotEmpty(field.getRequisiteTypes()))
			{
				builder.openEntityTag(ATTRIBUTE_REQUISITE_TYPES);
				for (RequisiteType requisiteType : field.getRequisiteTypes())
				{
					builder.createEntityTag(ATTRIBUTE_REQUISITE_TYPE, requisiteType.getDescription());
				}
				builder.closeEntityTag(ATTRIBUTE_REQUISITE_TYPES);
			}

			builder.createEntityTag(ATTRIBUTE_MAX_LENGTH_FIELD,  StringHelper.getEmptyIfNull(field.getMaxLength()));
			builder.createEntityTag(ATTRIBUTE_MIN_LENGTH_FIELD,  StringHelper.getEmptyIfNull(field.getMinLength()));

			if (CollectionUtils.isNotEmpty(field.getFieldValidationRules()))
			{
				builder.openEntityTag(VALIDATORS_FIELD);

				for (FieldValidationRule validationRule : field.getFieldValidationRules())
				{
					builder.openEntityTag(VALIDATOR_FIELD);
					builder.createEntityTag(VALIDATOR_TYPE_FIELD, validationRule.getFieldValidationRuleType().name());
					builder.createEntityTag(VALIDATOR_MESSAGE_FIELD, StringHelper.getEmptyIfNull(validationRule.getErrorMessage()));
					Map<String, Object> parameters = validationRule.getParameters();
					if (MapUtils.isNotEmpty(parameters))
					{
						for(String key : parameters.keySet())
						{
							builder.createEntityTag(VALIDATOR_PARAMETER_FIELD, (String) parameters.get(key));
						}
					}

					builder.closeEntityTag(VALIDATOR_FIELD);
				}

				builder.closeEntityTag(VALIDATORS_FIELD);
			}

			buildValueTag(field, builder);

			builder.createEntityTag(ATTRIBUTE_DEFAULT_VALUE_FIELD, StringHelper.getEmptyIfNull(field.getDefaultValue()));
			builder.createEntityTag(ATTRIBUTE_ERROR_FIELD, StringHelper.getEmptyIfNull(field.getError()));
			builder.closeEntityTag(ATTRIBUTE_FIELD);
		}

		builder.closeEntityTag(ATTRIBUTES_FIELD);

		return builder.toString();
	}

	protected void buildValueTag(Field field, XmlEntityBuilder builder)
	{
		//в мете значений полей не храним
	}
}
