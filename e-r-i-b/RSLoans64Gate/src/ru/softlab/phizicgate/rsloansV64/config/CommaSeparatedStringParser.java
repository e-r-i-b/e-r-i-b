package ru.softlab.phizicgate.rsloansV64.config;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public abstract class CommaSeparatedStringParser implements UserFieldParser<List<String>>
{
	private static final String DURATION_STRING_DELIMETR = ",";

	protected String[] parseString(String durationString)
	{
		String trimDuration = durationString.trim();
		return trimDuration.split(DURATION_STRING_DELIMETR);
	}
}
