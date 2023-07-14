package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import com.rssl.phizgate.common.documents.payments.fields.FieldValidationParameter;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 09.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class FieldValidationRuleImpl implements FieldValidationRule
{
	private FieldValidationRuleType fieldValidationRuleType;
	private String errorMessage;
	private Map<String, Object> parameters = new HashMap<String, Object>();


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
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}
	
	public void setFieldValidationRuleType(String fieldValidationRuleType)
	{
		if (fieldValidationRuleType == null || fieldValidationRuleType.trim().length() == 0)
		{
			return;
		}
		this.fieldValidationRuleType = FieldValidationRuleType.fromValue(fieldValidationRuleType);
	}

	public void setFieldValidators(Map<String, Object> mapValidatorParameter)
	{
		if (MapUtils.isEmpty(mapValidatorParameter))
		{
			return;
		}

		for (Map.Entry<String, Object> entry : mapValidatorParameter.entrySet())
		{
			if (entry.getValue() instanceof FieldValidationParameter)
			{
				parameters.put(entry.getKey(), ((FieldValidationParameter) entry.getValue()).getValue());
			}
		}
	}
}
