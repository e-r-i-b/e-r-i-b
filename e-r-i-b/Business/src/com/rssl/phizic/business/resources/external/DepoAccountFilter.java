package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import org.apache.commons.collections.Predicate;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Используется для фильтрации списка счетов депо
 */
public interface DepoAccountFilter extends Restriction, Predicate
{
	/**
	 * Подходит ли счет депо под критерии
	 * @param depoAccount - счет депо
	 * @return true -подходит, false - не подходит
	 */
	boolean accept(DepoAccount depoAccount);
}
