package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author sergunin
 * @ created 12.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class FundCountRequestForm extends ActionFormBase
{
	private long count;

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }
}
