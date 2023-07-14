package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Money;

/**
 * @author Maleyev
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoansSumCodeParser implements UserFieldParser<String>
{
	public String parse(Object value) throws GateException
	{
		if (value instanceof Money)
			return ((Money)value).getCurrency().getNumber().equals("643") ? "0" : ((Money)value).getCurrency().getNumber();
		return "";
	}
}
