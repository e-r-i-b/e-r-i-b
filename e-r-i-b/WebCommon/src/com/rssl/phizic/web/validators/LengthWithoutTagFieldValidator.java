package com.rssl.phizic.web.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.phizic.web.news.Tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидирует длину текста без учёта тегов.
 * @author komarov
 * @ created 13.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class LengthWithoutTagFieldValidator extends LengthFieldValidator
{

	private String replaceRegExp(String str, String regexp, String target)
	{
		String tmp = str;
		Pattern patt = Pattern.compile(regexp);
		Matcher match = patt.matcher(tmp);
        while (match.find())
        {
		  tmp = tmp.replaceAll(regexp, target);
	      match = patt.matcher(tmp);
        }
	    return tmp;
	}

	private String clearTags(String value)
	{
		for(Tags tag : Tags.values())
		{
			value = replaceRegExp(value, tag.getRegExp(), tag.getSubstitutionWhithoutTag());
		}
		return value;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(isValueEmpty(value))
            return true;
		
		String text = clearTags(value);

        if(minlength != null && text.length() < minlength.intValue())
          return false;

        if (maxlength != null && text.length() > maxlength.intValue())
          return false;

        return true;
	}
}
