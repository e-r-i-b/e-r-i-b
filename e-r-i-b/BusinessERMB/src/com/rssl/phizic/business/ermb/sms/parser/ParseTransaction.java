package com.rssl.phizic.business.ermb.sms.parser;

/**
 * @author Erkin
* @ created 25.12.2012
* @ $Author$
* @ $Revision$
*/
class ParseTransaction
{
	private final Lexeme lexeme;

	private final String text;

	private final int begin;

	private int end;

	ParseTransaction(Lexeme lexeme, String text, int begin)
	{
		this.lexeme = lexeme;
		this.text = text;
		this.begin = begin;
		this.end = -1;
	}

	Lexeme getLexeme()
	{
		return lexeme;
	}

	String getLexemeValue()
	{
		return (end >= 0) ? text.substring(begin, end) : null;
	}

	String getText()
	{
		return text;
	}

	int getBegin()
	{
		return begin;
	}

	int getEnd()
	{
		return end;
	}

	void setEnd(int end)
	{
		this.end = end;
	}
}
