package com.rssl.common.forms.validators;

import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Валидатор предназначен для валидации регулярных выражений.
 *
 * @author usachev
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */
public class RegExpPatternValidator extends FieldValidatorBase
{
	public boolean validate(String value)
	{
		if (isValueEmpty(value))
		{
			return true;
		}

		try
		{
			Pattern.compile(value);
			return true;
		}
		catch (PatternSyntaxException ignore)
		{
			//Падение при парсинге выражения не должно приводить к падению системы
			return false;
		}
	}
}
