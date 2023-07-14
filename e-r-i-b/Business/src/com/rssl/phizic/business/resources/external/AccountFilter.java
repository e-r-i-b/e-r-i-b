package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;

/**
 * Используется для фильтрации списка счетов по
 * разнообразным признакам
 * @author Evgrafov
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 */

public interface AccountFilter extends Restriction
{
    /**
     * Подходит ли счет под критерии
     * @param accountLink
     * @return true - подходит, false - не подходит
     */
    boolean accept(AccountLink accountLink);
}
