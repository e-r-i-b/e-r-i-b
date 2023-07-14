package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class OfficesParser extends CommaSeparatedStringParser
{
	public List<String> parse(Object value) throws GateException
	{
		if(value == null)
			return new ArrayList();

		String officeString = ((String) value).trim();

		if(officeString.length()==0)
			return new ArrayList();

		String[] offices = parseString(officeString);

		List<String> result = new ArrayList<String>();
		for (String office : offices)
			result.add(office);

		return result;
	}
}

