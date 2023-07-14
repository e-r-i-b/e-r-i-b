package com.rssl.phizic.esb.ejb.federal;

import com.rssl.phizic.esb.ejb.ESBMessageListenerBase;
import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.esb.ejb.federal.crediting.GetCampaignerInfoProcessor;
import com.rssl.phizic.esb.ejb.federal.crediting.RegisterRespondToMarketingProposeProcessor;
import com.rssl.phizic.esb.ejb.federal.documents.*;
import com.rssl.phizic.esb.ejb.federal.loan.LoanDetailProcessor;
import com.rssl.phizic.esb.ejb.federal.loan.LoanListProcessor;
import com.rssl.phizic.esb.ejb.federal.loan.LoanPaymentProcessor;
import com.rssl.phizic.esb.ejb.federal.sbnkd.*;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.util.HashMap;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Листенер федерального сегмента шины
 */

public class Listener extends ESBMessageListenerBase
{
	private static final HashMap<Class,MessageProcessor> processors = new HashMap<Class, MessageProcessor>();

	static
	{
		processors.put(IssueCardRs.class,                           new IssueCardProcessor());
		processors.put(CreateCardContractRs.class,                  new CreateCardContractProcessor());
		processors.put(GetPrivateClientRs.class,                    new GetPrivateClientProcessor());
		processors.put(ConcludeEDBORs.class,                        new ConcludeEDBOProcessor());
		processors.put(CustAddRs.class,                             new CustAddProcessor());
		processors.put(RegisterRespondToMarketingProposeRs.class,   new RegisterRespondToMarketingProposeProcessor());
		processors.put(GetCampaignerInfoRs.class,                   new GetCampaignerInfoProcessor());
		processors.put(CardBlockRs.class,                           new CardBlockProcessor());
		processors.put(SetAccountStateRs.class,                     new SetAccountStateProcessor());
		processors.put(GetPrivateOperationScanRs.class,             new GetPrivateOperationScanProcessor());
		processors.put(UpdateCardReportSubscriptionRs.class,        new UpdateCardReportSubscriptionProcessor());
		processors.put(XferAddRs.class,                             new XferAddProcessor());
		processors.put(SvcAcctDelRs.class,                          new SvcAcctDelProcessor());
		processors.put(SvcAddRs.class,                              new SvcAddProcessor());
		processors.put(GetPrivateLoanDetailsRs.class,               new LoanDetailProcessor());
		processors.put(GetPrivateLoanListRs.class,                  new LoanListProcessor());
		processors.put(LoanPaymentRs.class,                         new LoanPaymentProcessor());
		processors.put(AcceptBillBasketExecuteRs.class,             new AcceptBillBasketExecuteProcessor());
		processors.put(RequestPrivateEarlyRepaymentRs.class,        new EarlyLoanRepaymentProcessor());
	}

	@Override
	protected ESBSegment getSegment()
	{
		return ESBSegment.federal;
	}

	@Override
	protected MessageProcessor getProcessor(Object response)
	{
		return processors.get(response.getClass());
	}
}
