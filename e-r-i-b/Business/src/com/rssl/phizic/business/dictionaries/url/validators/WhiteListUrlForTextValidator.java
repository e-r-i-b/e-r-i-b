package com.rssl.phizic.business.dictionaries.url.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор проверяет на доступность (разрешены ли они в системе) ссылки,
 * указанные в переданном тексте (ищется BBCode урла)
 *
 * @author akrenev
 * @ created 05.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class WhiteListUrlForTextValidator extends WhiteListUrlValidator
{
	private static final Pattern URL_PATTERN = Pattern.compile("\\[url=['\"]?(.*?[^'\"])['\"]?\\]");

	public boolean validate(String text) throws TemporalDocumentException
	{
		if (isValueEmpty(text))
			return true;

		Matcher matcher = URL_PATTERN.matcher(text);
		while (matcher.find())
		{
			String url = matcher.group(1);
			if (!super.validate(url))
				return false;
		}
		return true;
	}
}
