package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * @author Erkin
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сканнер символов СМС-команды
 */
@Statefull
@NonThreadSafe
class Scanner
{
	private final String text;

	private final CharacterIterator iterator;

	///////////////////////////////////////////////////////////////////////////

	Scanner(String text)
	{
		this.text = text;
		this.iterator = new StringCharacterIterator(this.text);
	}

	String getText()
	{
		return text;
	}

	void gotoNextChar()
	{
		iterator.next();
	}

	char getCurrentChar()
	{
		return iterator.current();
	}

	int getPosition()
	{
		return iterator.getIndex();
	}

	void setPosition(int position)
	{
		iterator.setIndex(position);
	}
}
