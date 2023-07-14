package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;

import java.util.Map;

/**
 * Валидатор всегда возвращает false.
 * @author niculichev
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class FailureValidator extends MultiFieldsValidatorBase
{
	/**
	 */
	public FailureValidator()
	{
		super();
	}

	/**
	 * @param message сообщение об ошибке
	 */
	public FailureValidator(String message)
	{
		super(message);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		return false;
	}
}
