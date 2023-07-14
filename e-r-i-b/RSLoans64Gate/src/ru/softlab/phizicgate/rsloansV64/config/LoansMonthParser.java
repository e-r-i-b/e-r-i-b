package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.DateSpan;

/**
 * @author Maleyev
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoansMonthParser implements UserFieldParser<String>
{
	public String parse(Object value) throws GateException
	{
		if (value instanceof DateSpan)
			return new Integer(((DateSpan)value).getMonths()).toString();
		return "";
	}
}
