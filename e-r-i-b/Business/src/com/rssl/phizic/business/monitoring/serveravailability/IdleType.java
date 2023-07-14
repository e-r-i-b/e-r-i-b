package com.rssl.phizic.business.monitoring.serveravailability;

/**
 * Типы неработоспособности
 * @author gladishev
 * @ created 11.10.2011
 * @ $Author$
 * @ $Revision$
 */
public enum IdleType
{
	//полная неработоспособность
	fullIdle,

	//частичная неработоспособность
	partIdle,

	//не работал джоб
	jobIdle;
}
