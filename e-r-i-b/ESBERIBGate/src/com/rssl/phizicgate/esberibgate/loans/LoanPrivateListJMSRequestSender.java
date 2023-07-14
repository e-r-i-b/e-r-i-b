package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizicgate.esberibgate.documents.senders.GetPrivateListRequestProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanListRs;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSRequestSender;

import java.util.List;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPrivateListJMSRequestSender extends AbstractJMSRequestSender<List<Loan>, GetPrivateLoanListRs>
{
    protected LoanPrivateListJMSRequestSender(GateFactory factory)
    {
        super(factory);
    }

    @Override
    protected OnlineMessageProcessorBase<GetPrivateLoanListRs> getRequestProcessor(List<Loan> document, String rbTbBrch) throws GateException
    {
        return new GetPrivateListRequestProcessor(document, rbTbBrch);
    }

}
