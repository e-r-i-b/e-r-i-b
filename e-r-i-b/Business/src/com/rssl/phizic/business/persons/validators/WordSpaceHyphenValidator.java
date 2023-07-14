package com.rssl.phizic.business.persons.validators;

import com.rssl.common.forms.validators.NotMatchRegexpFieldValidator;

/**
 * Проверка на то, что в строке нет пробела рядом с дефисом
 * @author egorova
 * @ created 10.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class WordSpaceHyphenValidator extends NotMatchRegexpFieldValidator
{
	private static final String regexp = ".*(\\s-|-\\s).*";
	
	public WordSpaceHyphenValidator()
	{
		super(regexp);		
	}
}
