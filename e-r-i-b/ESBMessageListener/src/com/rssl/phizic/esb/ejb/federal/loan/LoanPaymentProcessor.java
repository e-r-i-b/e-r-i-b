package com.rssl.phizic.esb.ejb.federal.loan;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.LoanPaymentRs;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPaymentProcessor extends ProcessorBase<LoanPaymentRs>
{
    @Override
    protected Object getStatus(LoanPaymentRs message)
    {
        return message.getStatus();
    }

    @Override
    protected String getType(LoanPaymentRs message)
    {
        return message.getClass().getSimpleName();
    }

    @Override
    protected String getId(LoanPaymentRs message)
    {
        return message.getRqUID();
    }

}
