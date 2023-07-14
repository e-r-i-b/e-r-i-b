package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.util.List;

/**
 * @author Gainanov
 * @ created 15.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class FieldImpl implements Field
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
	private boolean key;
	private int num;
	private boolean saveInTemplate;
	private boolean requiredForConformation;
	private boolean requiredForBill;
	private boolean hideInConfirmation;
	private Object value;
	private String error;
	private String groupName;
	private String extendedDescId;
	private String mask;
	private BusinessFieldSubType businessSubType;

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public FieldImpl()
	{
		visible = true;
		editable = true;
	}

	public FieldImpl(String externalId, String name, FieldDataType type, Object value)
	{
		this.name = name;
		this.externalId = externalId;
		this.type = type;
		this.value = value;
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

	public boolean isHideInConfirmation()
	{
		return hideInConfirmation;
	}

	public Object getValue()
	{
		return value;
	}

	public String getError()
	{
		return error;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public void setMainSum(boolean mainSum)
	{
		this.mainSum = mainSum;
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

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public void setType(FieldDataType type)
	{
		this.type = type;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void setKey(boolean key)
	{
		this.key = key;
	}

	public int compareTo(Object o)
	{
		if(!(o instanceof Field))
			throw new RuntimeException("ќжидалс€ тип Field!");
		return getExternalId().compareTo(((Field)o).getExternalId());
	}

	public void setRequiredForBill(boolean requiredForBill)
	{
		this.requiredForBill = requiredForBill;
	}

	public void setRequiredForConformation(boolean requiredForConformation)
	{
		this.requiredForConformation = requiredForConformation;
	}

	public void setSaveInTemplate(boolean saveInTemplate)
	{
		this.saveInTemplate = saveInTemplate;
	}

    public void setHideInConfirmation(boolean hideInConfirmation)
	{
		this.hideInConfirmation = hideInConfirmation;
	}

	public void setValue(Object value)
	{
		this.value = value;
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
