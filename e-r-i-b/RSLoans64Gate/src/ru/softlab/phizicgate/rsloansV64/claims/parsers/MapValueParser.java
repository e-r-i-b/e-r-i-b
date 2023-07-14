package ru.softlab.phizicgate.rsloansV64.claims.parsers;

import java.util.Map;

/**
 * @author Krenev
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class MapValueParser implements ValueParser
{
	private Map<String, String> map;

	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public MapValueParser(Map<String, String> map)
	{
		this.map = map;
	}

	public String parse(String value)
	{
		if (value == null){
			return null;
		}
		return map.get(value);
	}
}
