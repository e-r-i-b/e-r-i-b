package com.rssl.common.forms.validators;

import org.apache.commons.lang.BooleanUtils;

/**
 * @author Erkin
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class BooleanConstantFieldValidator extends ConstantFieldValidatorBase<Boolean>
{
	/**
	 * ctor
	 * @param constant
	 */
	public BooleanConstantFieldValidator(boolean constant)
	{
		super(constant);
	}

	/**
	 * ctor
	 * @param constant
	 * @param message
	 */
	public BooleanConstantFieldValidator(boolean constant, String message)
	{
		super(constant, message);
	}

	protected Boolean valueOf(String string)
	{
		return BooleanUtils.toBoolean(string);
	}
}
