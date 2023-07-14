package com.rssl.phizgate.common.messaging.retail.jni.pool;

import com.rssl.api.retail.Retail;

/**
 * Обертка над инстансом jni ретейл
 * @author gladishev
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public interface RetailWrapper
{
	/**
	 * @return инстанс ретейл
	 */
	Retail getRetail();
}
