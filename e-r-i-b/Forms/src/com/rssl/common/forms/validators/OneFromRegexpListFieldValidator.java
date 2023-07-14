package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Проверка строки на предмет соответствия одному из списка регулярных выражений. 
 *
 * @ author: Gololobov
 * @ created: 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class OneFromRegexpListFieldValidator extends FieldValidatorBase
{
	private List<Pattern> patterns = new ArrayList<Pattern>();

	public OneFromRegexpListFieldValidator(String[] regexps)
	{
		for (String regexp : regexps)
		{
			this.patterns.add(Pattern.compile(regexp));
		}

	}

	public OneFromRegexpListFieldValidator(String[] regexps, String message)
	{
		this(regexps);
		this.setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (CollectionUtils.isEmpty(patterns))
		{
			return true;
		}

		return validatePatterns(value);
	}

	private boolean validatePatterns(String value)
	{
		for (Pattern pattern : this.patterns)
		{
			if (pattern.matcher(value).matches())
				return true;
		}
		return false;
	}
}
