package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckValidatorForm extends ActionFormBase
{
	private Long id;
	private String fieldName;
	private String fieldCode;
	private String validatorExpression;
	private String checkingValue;
	private String checkingResult;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldCode()
	{
		return fieldCode;
	}

	public void setFieldCode(String fieldCode)
	{
		this.fieldCode = fieldCode;
	}

	public String getValidatorExpression()
	{
		return validatorExpression;
	}

	public void setValidatorExpression(String validatorExpression)
	{
		this.validatorExpression = validatorExpression;
	}

	public String getCheckingValue()
	{
		return checkingValue;
	}

	public void setCheckingValue(String checkingValue)
	{
		this.checkingValue = checkingValue;
	}

	public String getCheckingResult()
	{
		return checkingResult;
	}

	public void setCheckingResult(String checkingResult)
	{
		this.checkingResult = checkingResult;
	}
}
