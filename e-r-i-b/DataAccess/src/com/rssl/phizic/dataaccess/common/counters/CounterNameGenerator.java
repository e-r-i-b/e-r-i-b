package com.rssl.phizic.dataaccess.common.counters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Barinov
 * @ created 18.10.2011
 * @ $Author$
 * @ $Revision$
 */

public enum CounterNameGenerator
{
	SIMPLE {
		public String generate(String name)
		{
			return "SC_" + name;
		}
	},

	EVERY_DAY {
		public String generate(String name)
		{
			return "SC_" + name + "_" + new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
		}
	};

	/**
	 * @param name
	 * @return сгенерированное имя счетчика (с префиксом и постфиксом)
	 */
	public abstract String generate(String name);
}
