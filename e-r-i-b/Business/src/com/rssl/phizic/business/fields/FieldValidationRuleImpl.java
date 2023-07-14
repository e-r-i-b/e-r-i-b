package com.rssl.phizic.business.fields;

import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import com.rssl.phizgate.common.documents.payments.fields.FieldValidationParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class FieldValidationRuleImpl implements FieldValidationRule
{
	private Long id;
	private FieldValidationRuleType fieldValidationRuleType;
	private String errorMessage;
	private Map<String, Object> fieldValidators;
	private Long fieldId;

	public FieldValidationRuleImpl()
	{}

	public FieldValidationRuleImpl(FieldValidationRuleImpl validationRule)
	{
		this.fieldValidationRuleType = validationRule.getFieldValidationRuleType();
		this.errorMessage = validationRule.getErrorMessage();
		fieldValidators = new HashMap<String, Object>();
		Map<String, Object> map = validationRule.getFieldValidators();
		for (String key: map.keySet())
		{
			Object value = map.get(key);
			if (value instanceof StringFieldValidationParameter)
			{
				fieldValidators.put(key, new StringFieldValidationParameter((StringFieldValidationParameter) value));
			}
		}

	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getFieldId()
	{
		return fieldId;
	}

	public void setFieldId(Long fieldId)
	{
		this.fieldId = fieldId;
	}

	public FieldValidationRuleType getFieldValidationRuleType()
	{
		return fieldValidationRuleType;
	}

	public void setFieldValidationRuleType(FieldValidationRuleType fieldValidationRuleType)
	{
		this.fieldValidationRuleType = fieldValidationRuleType;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Map<String, Object> getParameters()
	{
		Map<String, Object> validators = getFieldValidators();
        if (validators == null)
            return new HashMap<String, Object>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : getFieldValidators().entrySet())
		{
			FieldValidationParameter fieldValidationParameter = (FieldValidationParameter) entry.getValue();
			if (fieldValidationParameter instanceof StringFieldValidationParameter)
				parameters.put(String.valueOf(getFieldValidationRuleType()), ((StringFieldValidationParameter) fieldValidationParameter).getValue());
		}

		return parameters;
	}

	public Map<String, Object> getFieldValidators()
	{
		return fieldValidators;
	}

	public void setFieldValidators(Map<String, Object> fieldValidators)
	{
		this.fieldValidators = fieldValidators;
	}

}
