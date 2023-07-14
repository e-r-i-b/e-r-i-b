package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizicgate.esberibgate.documents.senders.LoanPaymentRequestProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.LoanPaymentRs;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSRequestSender;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPaymentJMSRequestSender extends AbstractJMSRequestSender<Loan, LoanPaymentRs>
{
    protected LoanPaymentJMSRequestSender(GateFactory factory)
    {
        super(factory);
    }

    @Override
    protected OnlineMessageProcessor<LoanPaymentRs> getRequestProcessor(Loan document, String rbTbBrch) throws GateException
    {
        return new LoanPaymentRequestProcessor(document, rbTbBrch);
    }
}
