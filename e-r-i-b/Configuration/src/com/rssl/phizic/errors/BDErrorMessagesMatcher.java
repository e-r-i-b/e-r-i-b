package com.rssl.phizic.errors;

import com.rssl.phizic.ConfigurationCheckedException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 17.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class BDErrorMessagesMatcher extends ErrorMessagesMatcher
{
	private static final ErrorMessagesService service = new ErrorMessagesService();

	public List<ErrorMessage> matchMessage(String message, ErrorSystem system) throws ConfigurationCheckedException
	{
		List<ErrorMessage> result = new ArrayList<ErrorMessage>();

		List<ErrorMessage> errors = service.findBySystem(system);
		for (ErrorMessage error : errors)
		{
			String regExp = error.getRegExp();
			Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

			if (pattern.matcher(message).matches())
				result.add(error);
		}
		return result;
	}
}
