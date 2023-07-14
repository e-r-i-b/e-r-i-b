package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import org.apache.commons.lang.StringUtils;

/**
 * @author akrenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 *
 * регексп валидатор для сумм (перед проверкой удаляет пробелы из строки)
 */
public class RegexpMoneyFieldValidator extends RegexpFieldValidator
{

	public RegexpMoneyFieldValidator()
	{}

	public RegexpMoneyFieldValidator(String regexp)
	{
		super(regexp);
	}

	public RegexpMoneyFieldValidator(String regexp, String message)
	{
		super(regexp, message);
	}

	public RegexpMoneyFieldValidator(String regexp, String message, String mode)
	{
		super(regexp, message, mode);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
	    return super.validate(StringUtils.deleteWhitespace(value));
	}
}
