package com.rssl.phizic.credit;

import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.InitiateConsumerProductOfferRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferResultTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRs;

/**
 * @author Rtischeva
 * @ created 18.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PhizProxyCreditEjbMessage extends XmlMessage
{
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq statusLoanApplicationRqRelease13;
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq statusLoanApplicationRqRelease16;
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq statusLoanApplicationRqRelease19;
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ERIBSendETSMApplRq eribSendETSMApplRq19;

	private EnquiryResponseERIB enquiryResponseERIB;

	private ERIBSendETSMApplRq eribSendETSMApplRq;
	private OfferResultTicket offerResultTicket;

	private SearchApplicationRs searchApplicationRs;

	private InitiateConsumerProductOfferRq initiateConsumerProductOfferRq;

	public PhizProxyCreditEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq statusLoanApplicationRq, String message)
	{
		super(message, statusLoanApplicationRq.getClass(), statusLoanApplicationRq.getRqUID(), statusLoanApplicationRq.getRqTm());
		this.statusLoanApplicationRqRelease13 = statusLoanApplicationRq;
	}

	public PhizProxyCreditEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq statusLoanApplicationRq, String message)
	{
		super(message, statusLoanApplicationRq.getClass(), statusLoanApplicationRq.getRqUID(), statusLoanApplicationRq.getRqTm());
		this.statusLoanApplicationRqRelease16 = statusLoanApplicationRq;
	}

	public PhizProxyCreditEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq statusLoanApplicationRq, String message)
	{
		super(message, statusLoanApplicationRq.getClass(), statusLoanApplicationRq.getRqUID(), statusLoanApplicationRq.getRqTm());
		this.statusLoanApplicationRqRelease19 = statusLoanApplicationRq;
	}

	public PhizProxyCreditEjbMessage(EnquiryResponseERIB enquiryResponseERIB, String message)
	{
		super(message, enquiryResponseERIB.getClass(), enquiryResponseERIB.getRqUID(), enquiryResponseERIB.getRqTm());
		this.enquiryResponseERIB = enquiryResponseERIB;
	}

	public PhizProxyCreditEjbMessage(ERIBSendETSMApplRq eribSendETSMApplRq, String message)
	{
		super(message, eribSendETSMApplRq.getClass(), eribSendETSMApplRq.getRqUID(), eribSendETSMApplRq.getRqTm());
		this.eribSendETSMApplRq = eribSendETSMApplRq;
	}

	public PhizProxyCreditEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ERIBSendETSMApplRq eribSendETSMApplRq19, String message)
	{
		super(message, eribSendETSMApplRq19.getClass(), eribSendETSMApplRq19.getRqUID(), eribSendETSMApplRq19.getRqTm());
		this.eribSendETSMApplRq19 = eribSendETSMApplRq19;
	}

	public PhizProxyCreditEjbMessage(SearchApplicationRs searchApplicationRs, String message)
	{
		super(message, searchApplicationRs.getClass(), searchApplicationRs.getRqUID(), searchApplicationRs.getRqTm());
		this.searchApplicationRs = searchApplicationRs;
	}

	public PhizProxyCreditEjbMessage(OfferResultTicket offerResultTicket, String message)
	{
		super(message, offerResultTicket.getClass(), offerResultTicket.getRqUID(), offerResultTicket.getRqTm());
		this.offerResultTicket = offerResultTicket;
	}

	public PhizProxyCreditEjbMessage(InitiateConsumerProductOfferRq initiateConsumerProductOfferRq, String message)
	{
		super(message, initiateConsumerProductOfferRq.getClass(), initiateConsumerProductOfferRq.getRqUID(), initiateConsumerProductOfferRq.getRqTm());
		this.initiateConsumerProductOfferRq = initiateConsumerProductOfferRq;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq getStatusLoanApplicationRqRelease13()
	{
		return statusLoanApplicationRqRelease13;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq getStatusLoanApplicationRqRelease16()
	{
		return statusLoanApplicationRqRelease16;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq getStatusLoanApplicationRqRelease19()
	{
		return statusLoanApplicationRqRelease19;
	}

	public EnquiryResponseERIB getEnquiryResponseERIB()
	{
		return enquiryResponseERIB;
	}

	public ERIBSendETSMApplRq getEribSendETSMApplRq()
	{
		return eribSendETSMApplRq;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ERIBSendETSMApplRq getEribSendETSMApplRq19()
	{
		return eribSendETSMApplRq19;
	}

	public SearchApplicationRs getSearchApplicationRs()
	{
		return searchApplicationRs;
	}

	public InitiateConsumerProductOfferRq getInitiateConsumerProductOfferRq()
	{
		return initiateConsumerProductOfferRq;
	}

	public OfferResultTicket getOfferResultTicket()
	{
		return offerResultTicket;
	}

	public void setOfferResultTicket(OfferResultTicket offerResultTicket)
	{
		this.offerResultTicket = offerResultTicket;
	}
}
