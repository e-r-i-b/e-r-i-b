package com.rssl.phizic.esb.ejb.federal.loan;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanListRs;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanListProcessor extends ProcessorBase<GetPrivateLoanListRs>
{
    @Override
    protected Object getStatus(GetPrivateLoanListRs message)
    {
        return message.getStatus();
    }

    @Override
    protected String getType(GetPrivateLoanListRs message)
    {
        return message.getClass().getSimpleName();
    }

    @Override
    protected String getId(GetPrivateLoanListRs message)
    {
        return message.getRqUID();
    }

}
