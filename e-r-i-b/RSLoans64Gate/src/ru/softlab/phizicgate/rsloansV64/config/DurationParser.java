package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class DurationParser extends CommaSeparatedStringParser
{
	private static final String DURATION_PREFIX = "M:";
	private static final String INTERVAL_SYMBOL = "-";

	public List<String> parse(Object value) throws GateException
	{
		if (value == null)
			return new ArrayList<String>();

		String durationString = ((String) value).trim();

		if (durationString.length() == 0)
			return new ArrayList<String>();

		String[] durations = parseString(durationString);

		List<String> result = new ArrayList<String>();
		for (String duration : durations)
		{
			String[] subdurations = parseInterval(duration);
			for (String subduration : subdurations)
			{
				result.add(DURATION_PREFIX + subduration);
			}
		}
		return result;
	}

	private String[] parseInterval(String duration)
	{
		duration = duration.trim();
		String[] strings = duration.split(INTERVAL_SYMBOL);
		if (strings.length == 1)
		{
			if (duration.endsWith(INTERVAL_SYMBOL))
			{
				return new String[]{duration};
			}
			return new String[]{strings[0].trim()};
		}
		if (strings.length != 2)
		{
			throw new IllegalArgumentException("некорректный формат промежутка: " + duration);
		}
		Integer minValue = Integer.valueOf(strings[0].trim());
		Integer maxValue = Integer.valueOf(strings[1].trim());
		if (minValue > maxValue)
		{
			throw new IllegalArgumentException("некорректный формат промежутка: " + duration);
		}
		String[] rezult = new String[maxValue - minValue + 1];
		for (int i = minValue; i <= maxValue; i++)
		{
			rezult[i - minValue] = Integer.toString(i);
		}
		return rezult;
	}
}

