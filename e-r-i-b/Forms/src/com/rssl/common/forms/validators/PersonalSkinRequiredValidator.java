package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 24.05.2011
 * Time: 17:25:47
 */
public class PersonalSkinRequiredValidator extends MultiFieldsValidatorBase
{
	public static final String SKIN_TYPE_VALUE_PARAMETER = "skinTypeParameter";
	public static final String SKIN_TYPE = "skinType";
	public static final String SKIN      = "skin";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long skin = (Long) values.get(SKIN);
		            
		if(values.get(SKIN_TYPE).equals( getParameter(SKIN_TYPE_VALUE_PARAMETER) ) && skin == null)
		{
			return false;
		}
		return true;
	}
}
