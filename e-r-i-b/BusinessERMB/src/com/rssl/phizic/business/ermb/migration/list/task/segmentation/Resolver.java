package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;

/**
 * @author Gulov
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Определитель сегмента.
 */
interface Resolver
{
	/**
	 * Определяет и устанвливает сегмент.
	 * @param client - клиент
	 * @return - признак, что сегментация закончена
	 */
	boolean evaluate(Client client) throws BusinessException;
}
