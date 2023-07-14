package com.rssl.auth.csa.back.servises;

/**
 * @author krenev
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */

public enum ConnectorState
{
	ACTIVE,   //активированый, аутентификация возможна только для этого статуса
	BLOCKED,  //временно заблокированный
	CLOSED,   //закрытый/неиспользуемый.
}
