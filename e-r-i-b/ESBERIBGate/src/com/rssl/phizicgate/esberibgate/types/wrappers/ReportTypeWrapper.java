package com.rssl.phizicgate.esberibgate.types.wrappers;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ReportTypeType;

/**
 * @author akrenev
 * @ created 21.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Враппер значения подписки на отчеты по карте
 */

public class ReportTypeWrapper
{
	/**
	 * получить прдставление внешней системы из нашего представления
	 * @param use наше представление
	 * @return прдставление внешней системы
	 */
	public static ReportTypeType toGate(boolean use)
	{
		return use? ReportTypeType.E: ReportTypeType.N;
	}
}
