package com.rssl.phizgate.common.messaging.retail.jni.pool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * непосредственно пул коннектов.
 * описание параметров смотреть в GenericKeyedObjectPool
 */
public class RetailJniPool extends GenericKeyedObjectPool
{
    private static final int DEFAULT_MAX_IDLE  = 8;

    private static final int DEFAULT_MAX_ACTIVE  = 4;

    private static final long DEFAULT_MAX_WAIT = 150;

	public RetailJniPool(RetailJniFactory factory)
	{
		super(factory);
		setMaxActive(DEFAULT_MAX_ACTIVE);
		setMaxWait(DEFAULT_MAX_WAIT);
		setMaxIdle(DEFAULT_MAX_IDLE);
	}
}
