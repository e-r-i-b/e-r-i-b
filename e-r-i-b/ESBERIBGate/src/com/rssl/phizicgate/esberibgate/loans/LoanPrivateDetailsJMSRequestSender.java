package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizicgate.esberibgate.documents.senders.GetPrivateDetailRequestProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanDetailsRs;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSRequestSender;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPrivateDetailsJMSRequestSender extends AbstractJMSRequestSender<Loan, GetPrivateLoanDetailsRs>
{

    /**
     * конструктор
     * @param factory фабрика гейта
     */
    public LoanPrivateDetailsJMSRequestSender(GateFactory factory)
    {
        super(factory);
    }

    @Override
    protected OnlineMessageProcessorBase<GetPrivateLoanDetailsRs> getRequestProcessor(Loan document, String rbTbBrch) throws GateException
    {
        return new GetPrivateDetailRequestProcessor(document, rbTbBrch);
    }

}
