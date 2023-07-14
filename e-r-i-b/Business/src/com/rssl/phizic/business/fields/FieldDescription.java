package com.rssl.phizic.business.fields;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hudyakov
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class FieldDescription extends MultiBlockDictionaryRecordBase implements Field, ListField, CalendarField, NumberField, GraphicSetField
{
	public static final String VALUES_DELIMITER = "|";
	public static final String VALUES_SPLITER = "\\|";
	private Long id;
	private String externalId;
	private String name;
	private String description;
	private String hint;
	private String popupHint;
	private FieldDataType type;
	private Long maxLength;
	private Long minLength;
	private boolean required;
	private boolean editable;
	private boolean visible;
	private boolean mainSum;
	private boolean key;
	private String defaultValue;
	private List<RequisiteType> requisiteTypes;
	private List<String> listValues;
	private List<ListValue> values;
	private List<FieldValidationRule> fieldValidationRules;
	private Long holderId;
	private boolean saveInTemplate;
	private boolean requiredForConformation;
	private boolean requiredForBill;
	private Integer numberPrecision;
	private boolean hideInConfirmation;
	private Object value;
	private String error;
	private String groupName;
	private String graphicTemplateName;
	private String extendedDescId;
	private String mask;
	private BusinessFieldSubType businessSubType;

	public Long getHolderId()
	{
		return holderId;
	}

	public void setHolderId(Long holderId)
	{
		this.holderId = holderId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getHint()
	{
		return hint;
	}

	public void setHint(String hint)
	{
		this.hint = hint;
	}

	public FieldDataType getType()
	{
		return type;
	}

	public void setType(FieldDataType type)
	{
		this.type = type;
	}

	public Long getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(Long maxLength)
	{
		this.maxLength = maxLength;
	}

	public Long getMinLength()
	{
		return minLength;
	}

	public void setMinLength(Long minLength)
	{
		this.minLength = minLength;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public boolean isMainSum()
	{
		return mainSum;
	}

	public void setMainSum(boolean mainSum)
	{
		this.mainSum = mainSum;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public boolean isKey()
	{
		return key;
	}

	public boolean isSaveInTemplate()
	{
		return saveInTemplate;
	}

	public boolean isRequiredForConformation()
	{
		return requiredForConformation;
	}

	public boolean isRequiredForBill()
	{
		return requiredForBill;
	}

	public void setKey(boolean key)
	{
		this.key = key;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public CalendarFieldPeriod getPeriod()
	{
		return CalendarFieldPeriod.broken;
	}

	public List<ListValue> getValues()
	{
		if (values == null || values.isEmpty())
		{
			values = new ArrayList<ListValue>(listValues.size());
			for (String value: listValues)
				values.add(new ListValue(value, value));
		}
		return values;
	}

	public List<FieldValidationRule> getFieldValidationRules()
	{
		return fieldValidationRules;
	}

	public void setFieldValidationRules(List<FieldValidationRule> fieldValidationRules)
	{
		this.fieldValidationRules = fieldValidationRules;
	}

	public int compareTo(Object o)
	{
		if(!(o instanceof Field))
			throw new RuntimeException("Ожидался тип Field!");

		return getExternalId().compareTo(((Field)o).getExternalId());
	}

	/**
	 * Получить список занчений в строку
	 * @param delimeter разделитель значений
	 * @return список значений разделенный delimeter
	 */
	public String getValuesAsString(String delimeter)
	{
		return (listValues == null || listValues.isEmpty()) ? null : StringUtils.join(listValues.toArray(), delimeter);
	}

	/**
	 * Сохранить список значений
	 * @param values строка значений
	 * @param spliter regex, по которому происходит разбивка строки.
	 */
	public void setValuesAsString(String values, String spliter)
	{
		if (StringHelper.isEmpty(values))
		{
			setListValues(null);
			return;
		}
		setListValues(ListUtil.fromArray(values.trim().split(spliter)));
	}

	public void addValidatorRule(FieldValidationRule validator)
	{
		fieldValidationRules.add(validator);
	}

	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		FieldDescription that = (FieldDescription) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	public List<String> getListValues()
	{
		return listValues;
	}

	public void setListValues(List<String> listValues)
	{
		this.listValues = listValues;
	}

	public Integer getNumberPrecision()
	{
		return numberPrecision;
	}

	public void setSaveInTemplate(boolean saveInTemplate)
	{
		this.saveInTemplate = saveInTemplate;
	}

	public void setRequiredForConformation(boolean requiredForConformation)
	{
		this.requiredForConformation = requiredForConformation;
	}

	public void setRequiredForBill(boolean requiredForBill)
	{
		this.requiredForBill = requiredForBill;
	}

	public void setNumberPrecision(Integer numberPrecision)
	{
		this.numberPrecision = numberPrecision;
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
		return error;
	}

	public void setHideInConfirmation(boolean hideInConfirmation)
	{
		this.hideInConfirmation = hideInConfirmation;
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
