package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;

/**
 * Используется для фильтрации списка ОМС
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public interface IMAccountFilter extends Restriction
{
	/**
     * Подходит ли ОМС под критерии
     * @param imAccountLink ссылка на ОМС
     * @return true - подходит, false - не подходит
     */
    boolean accept(IMAccountLink imAccountLink);
}
