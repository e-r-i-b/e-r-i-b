package com.rssl.phizic.business.resources.external;

/**
 * Не фильтрует список ОМС
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class NullIMAccountFilter implements IMAccountFilter
{
    public boolean accept(IMAccountLink imAccountLink)
    {
        return true;
    }
}
