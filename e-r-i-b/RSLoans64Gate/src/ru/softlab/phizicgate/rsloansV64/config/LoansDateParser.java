package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.DateSpan;

import java.util.Calendar;

/**
 * @author Maleyev
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoansDateParser implements UserFieldParser<String>
{
	public String parse(Object value) throws GateException
	{
		if (value instanceof Calendar)
			return String.format("%1$tY-%1$tm-%1$te",((Calendar)value).getTime());
		return "";
	}
}
