package ru.softlab.phizicgate.rsloansV64.claims.parsers;

import ru.softlab.phizicgate.rsloansV64.claims.parsers.ValueParser;

/**
 * @author Omeliyanchuk
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ничего не делает.
 */
public class DummyFieldParser implements ValueParser
{
	public String parse(String value)
	{
		return value;
	}
}
