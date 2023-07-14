package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Mescheryakova
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class StateReport
{
	/*
	 Принят к исполнению
	 */
	public final static char SEND = 's';

	/*
	Исполнен
	 */
	public final static char EXECUTED = 'o';

	/*
	Ошибка
	 */
	public final static char ERROR = 'e';

	/*
	Соответствие название состояния отчета по коду состояния (используется в jsp)
	 */
	public final static Map<Object, String> NAME_STATE_BY_CODE = new HashMap<Object, String>(){{
		put(SEND,       "Обрабатывается");
		put(EXECUTED,   "Исполнен");
		put(ERROR,      "Ошибка");

	}};
}
