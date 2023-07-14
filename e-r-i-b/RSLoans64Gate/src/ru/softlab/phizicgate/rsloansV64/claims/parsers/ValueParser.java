package ru.softlab.phizicgate.rsloansV64.claims.parsers;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * преобразуют значения IKFL в знания Loans.
 */
public interface ValueParser
{
	/**
	 * преобразование значения в формат Loans
	 * @param value значение
	 * @return преобразованое значение
	 */
	String parse(String value);
}
