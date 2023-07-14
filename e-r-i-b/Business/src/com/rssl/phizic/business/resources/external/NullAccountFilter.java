package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

/**
 * @author Evgrafov
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 *
 * Не фильтрует ничего - выбирает все счета
 */
@PublicDefaultCreatable
public class NullAccountFilter implements AccountFilter
{
    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//

    private static final AccountFilter instance = new NullAccountFilter();

    public static AccountFilter getInstance()
    {
        return instance;
    }

    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

    public NullAccountFilter()
    {
    }

    public boolean accept(AccountLink accountLink)
    {
        return true;
    }
}
