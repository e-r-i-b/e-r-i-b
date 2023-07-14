package com.rssl.phizicgate.mobilebank;

import java.text.ParseException;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
interface Parser<ReturnType>
{
	/**
	 * Распарсить строку
	 * @param string строка (can be null or empty)
	 * @return
	 * @throws ParseException
	 */
	ReturnType parse(String string) throws ParseException;
}
