package com.rssl.phizic.business.persons.validators;

import com.rssl.common.forms.validators.NotMatchRegexpFieldValidator;

/**
 * Проверка на наличие двойных дефисов
 * @author egorova
 * @ created 24.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class DoubleHyphenValidator extends NotMatchRegexpFieldValidator
{
	private static final String regexp = ".*-{2,}.*";

	public DoubleHyphenValidator()
	{
		super(regexp);
	}
}
