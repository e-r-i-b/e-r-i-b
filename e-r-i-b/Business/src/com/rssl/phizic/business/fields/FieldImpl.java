package com.rssl.phizic.business.fields;

import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.util.List;

/**
 * Вспомогательный класс доп. полей
 * (некоторые внешние системы не предоставляют в дополнительном поле название поля и подсказку к нему
 * в этом классе устанавливаем в конструкторе недостающие параметры)
 *
 * @author hudyakov
 * @ created 05.03.2010
 * @ $Author:
 * @ $Revision:
 */

public class FieldImpl implements Field, ListField, CalendarField, NumberField, GraphicSetField
{
	private String name;
	private FieldDataType type;
	private List<RequisiteType> requisiteTypes;
	private String defaultValue;
	private boolean required;
	private boolean visible;
	private boolean editable;
	private boolean mainSum;
	private String externalId;
	private String description;
	private String hint;
	private String popupHint;
	private Long minLength;
	private Long maxLength;
	private List<FieldValidationRule> fieldValidationRules;
	private List<ListValue> values;
	private CalendarFieldPeriod period;
	private boolean key;
	private boolean saveInTemplate;
	private boolean requiredForBill;
	private boolean requiredForConformation;
	private boolean hideInConfirmation;
	private Integer numberPrecision;
	private String error;
	private Object value;
	private String groupName;
	private String graphicTemplateName;
	private String extendedDescId;
	private String mask;
	private BusinessFieldSubType businessSubType;

	public FieldImpl()
	{

	}

	public FieldImpl(Field field, String name, String hint, String description)
	{
		this.name = (name == null) ? field.getName() : name;
		this.hint = (hint == null) ? field.getHint() : hint;
		this.description = (description == null) ? field.getDescription() : description;
		this.type = field.getType();
		this.defaultValue = field.getDefaultValue();
		this.required = field.isRequired();
		this.visible  = field.isVisible();
		this.editable = field.isEditable();
		this.mainSum  = field.isMainSum();
		this.key = field.isKey();
		this.externalId = field.getExternalId();
		this.minLength = field.getMinLength();
		this.maxLength = field.getMaxLength();
		this.fieldValidationRules = field.getFieldValidationRules();

		if (field instanceof ListField)
			this.values = ((ListField) field).getValues();

		if (field instanceof CalendarField)
			this.period = ((CalendarField) field).getPeriod();
		this.saveInTemplate = field.isSaveInTemplate();
		this.requiredForBill = field.isRequiredForBill();
		this.requiredForConformation = field.isRequiredForConformation();
		this.hideInConfirmation = field.isHideInConfirmation();
		if (field instanceof NumberField)
			this.numberPrecision = ((NumberField) field).getNumberPrecision();
		this.value = field.getValue();
		this.error = field.getError();
	}

	public String getName()
	{
		return name;
	}

	public FieldDataType getType()
	{
		return type;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public boolean isRequired()
	{
		return required;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public boolean isMainSum()
	{
		return mainSum;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public String getDescription()
	{
		return description;
	}

	public String getHint()
	{
		return hint;
	}

	public Long getMinLength()
	{
		return minLength;
	}

	public Long getMaxLength()
	{
		return maxLength;
	}

	public List<FieldValidationRule> getFieldValidationRules()
	{
		return fieldValidationRules;
	}

	public List<ListValue> getValues()
	{
		return values;
	}

	public CalendarFieldPeriod getPeriod()
	{
		return period;
	}

	public boolean isKey()
	{
		return key;
	}

	public void setName(String fieldName)
	{
		this.name = fieldName;
	}

	public void setType(FieldDataType type)
	{
		this.type = type;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public void setMainSum(boolean mainSum)
	{
		this.mainSum = mainSum;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setHint(String hint)
	{
		this.hint = hint;
	}

	public void setMinLength(Long minLength)
	{
		this.minLength = minLength;
	}

	public void setMaxLength(Long maxLength)
	{
		this.maxLength = maxLength;
	}

	public void setFieldValidationRules(List<FieldValidationRule> fieldValidationRules)
	{
		this.fieldValidationRules = fieldValidationRules;
	}

	public void setValues(List<ListValue> values)
	{
		this.values = values;
	}

	public void setPeriod(CalendarFieldPeriod period)
	{
		this.period = period;
	}
	public void setPeriod(String period)
	{
		this.period = CalendarFieldPeriod.valueOf(period);
	}

	public void setKey(boolean key)
	{
		this.key = key;
	}

	public boolean isSaveInTemplate()
	{
		return saveInTemplate;
	}

	public void setSaveInTemplate(boolean saveInTemplate)
	{
		this.saveInTemplate = saveInTemplate;
	}

	public boolean isRequiredForBill()
	{
		return requiredForBill;
	}

	public boolean isHideInConfirmation()
	{
		return hideInConfirmation;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public String getError()
	{
		return  error;
	}

	public void setRequiredForBill(boolean requiredForBill)
	{
		this.requiredForBill = requiredForBill;
	}

	public boolean isRequiredForConformation()
	{
		return requiredForConformation;
	}

	public void setRequiredForConformation(boolean requiredForConformation)
	{
		this.requiredForConformation = requiredForConformation;
	}

	public void setHideInConfirmation(boolean hideInConfirmation)
	{
		this.hideInConfirmation = hideInConfirmation;
	}

	public Integer getNumberPrecision()
	{
		return numberPrecision;
	}

	public void setNumberPrecision(Integer numberPrecision)
	{
		this.numberPrecision = numberPrecision;
	}

	public int compareTo(Object o)
	{
		if(!(o instanceof Field))
			throw new RuntimeException("Ожидался тип Field!");
		return getExternalId().compareTo(((Field)o).getExternalId());
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getPopupHint()
	{
		return popupHint;
	}

	public void setPopupHint(String popupHint)
	{
		this.popupHint = popupHint;
	}

	public List<RequisiteType> getRequisiteTypes()
	{
		return requisiteTypes;
	}

	public void setRequisiteTypes(List<RequisiteType> requisiteTypes)
	{
		this.requisiteTypes = requisiteTypes;
	}

	public String getGraphicTemplateName()
	{
		return graphicTemplateName;
	}

	public void setGraphicTemplateName(String graphicTemplateName)
	{
		this.graphicTemplateName = graphicTemplateName;
	}

	public String getExtendedDescId()
	{
		return extendedDescId;
	}

	public void setExtendedDescId(String extendedDescId)
	{
		this.extendedDescId = extendedDescId;
	}

	public String getMask()
	{
		return mask;
	}

	public void setMask(String mask)
	{
		this.mask = mask;
	}

	public BusinessFieldSubType getBusinessSubType()
	{
		return businessSubType;
	}

	public void setBusinessSubType(BusinessFieldSubType businessSubType)
	{
		this.businessSubType = businessSubType;
	}
}
