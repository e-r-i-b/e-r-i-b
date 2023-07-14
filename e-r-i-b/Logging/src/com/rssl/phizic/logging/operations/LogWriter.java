package com.rssl.phizic.logging.operations;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 15.03.2006
 * @ $Author: krenev $
 * @ $Revision: 12163 $
 */

public interface LogWriter extends Serializable
{
	public static final String AUTHENTICATION = "Аутентификация";
	public static final String AUTHENTICATION_FINISH = "Аутентификация пройдена";
	public static final String LOGOFF = "Выход";

	void writeActiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception;

	void writePassiveOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception;

	void writeSecurityOperation(LogDataReader reader, Calendar start, Calendar end) throws Exception;
}
