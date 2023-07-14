package com.rssl.phizic.business.persons.validators;

import com.rssl.common.forms.validators.NotMatchRegexpFieldValidator;

/**
 * Проверка на наличие двойных пробелов
 * @author egorova
 * @ created 24.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class DoubleSpacesValidator extends NotMatchRegexpFieldValidator
{
	private static final String regexp = ".*\\s{2,}.*";

	public DoubleSpacesValidator()
	{
		super(regexp);		
	}
}