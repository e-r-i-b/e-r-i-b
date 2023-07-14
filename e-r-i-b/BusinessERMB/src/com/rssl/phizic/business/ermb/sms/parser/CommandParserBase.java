package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */
abstract class CommandParserBase extends ParserBase implements CommandParser
{
	private final String parserName = getClass().getSimpleName();

	private Dictionary keywords;

	private ParseErrorCollector errorCollector;

	///////////////////////////////////////////////////////////////////////////

	public String getParserName()
	{
		return parserName;
	}

	protected Dictionary getKeywords()
	{
		return keywords;
	}

	public void setKeywords(Dictionary keywords)
	{
		this.keywords = keywords;
	}

	protected void addError(String error)
	{
		errorCollector.addError(error, getScanner().getPosition());
	}

	protected void addError(TextMessage error)
	{
		errorCollector.addError(error.getText(), getScanner().getPosition());
	}

	public void setErrorCollector(ParseErrorCollector errorCollector)
	{
		this.errorCollector = errorCollector;
	}
}
