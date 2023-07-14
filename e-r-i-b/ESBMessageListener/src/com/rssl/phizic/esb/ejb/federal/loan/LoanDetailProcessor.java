package com.rssl.phizic.esb.ejb.federal.loan;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanDetailsRs;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanDetailProcessor extends ProcessorBase<GetPrivateLoanDetailsRs>
{
    @Override protected Object getStatus(GetPrivateLoanDetailsRs message)
    {
        return message.getStatus();
    }

    @Override protected String getType(GetPrivateLoanDetailsRs message)
    {
        return message.getClass().getSimpleName();
    }

    @Override protected String getId(GetPrivateLoanDetailsRs message)
    {
        return message.getRqUID();
    }

}
