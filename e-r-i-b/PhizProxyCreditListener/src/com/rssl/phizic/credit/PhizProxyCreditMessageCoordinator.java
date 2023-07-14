package com.rssl.phizic.credit;

import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.InitiateConsumerProductOfferRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferResultTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRs;

/**
 * @author Rtischeva
 * @ created 15.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PhizProxyCreditMessageCoordinator extends MessageCoordinator
{
	private final PhizProxyCreditListenerModule phizProxyCreditListenerModule = moduleManager.getModule(PhizProxyCreditListenerModule.class);

	public PhizProxyCreditMessageCoordinator()
	{
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq.class, phizProxyCreditListenerModule.getClaimStatusProxyProcessor());
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq.class, phizProxyCreditListenerModule.getClaimStatusProxyProcessor());

		registerProcessor(EnquiryResponseERIB.class, phizProxyCreditListenerModule.getClaimStatusProxyProcessor());

		registerProcessor(ERIBSendETSMApplRq.class, phizProxyCreditListenerModule.getCrmLoanClaimResponseProcessor());
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ERIBSendETSMApplRq.class, phizProxyCreditListenerModule.getCrmLoanClaimResponseProcessor());
		registerProcessor(SearchApplicationRs.class, phizProxyCreditListenerModule.getCrmLoanClaimResponseProcessor());
		registerProcessor(InitiateConsumerProductOfferRq.class, phizProxyCreditListenerModule.getCrmLoanClaimResponseProcessor());
		registerProcessor(OfferResultTicket.class, phizProxyCreditListenerModule.getCrmLoanClaimResponseProcessor());
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq.class, phizProxyCreditListenerModule.getClaimStatusProxyProcessor());
	}
}
