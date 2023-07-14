package com.rssl.phizic.errors;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 17.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class XMLErrorMessagesMatcher extends ErrorMessagesMatcher
{
	private List<ErrorMessage> messages;

	public XMLErrorMessagesMatcher() throws Exception
	{
		initialize();
	}

	private void initialize() throws Exception
	{
		Document config = XmlHelper.loadDocumentFromResource(ErrorMessagesLoader.ERROR_MESSAGES_FILE_NAME);
		ErrorMessagesLoader errorMessagesLoader = new ErrorMessagesLoader(config);
		errorMessagesLoader.load();
		messages = errorMessagesLoader.getMessages();
	}

	public List<ErrorMessage> matchMessage(String message, ErrorSystem system) throws ConfigurationCheckedException
	{
		List<ErrorMessage> result = new ArrayList<ErrorMessage>();

		for (ErrorMessage error : messages)
		{
			if (error.getSystem() != system)
			{
				continue;
			}
			String regExp = error.getRegExp();
			Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

			if (pattern.matcher(message).matches())
				result.add(error);
		}
		return result;
	}
}
