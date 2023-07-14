package com.rssl.phizic;

import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.ermb.SendSmsRequest;
import com.rssl.phizic.messaging.ermb.SendSmsWithImsiRequest;
import com.rssl.phizic.synchronization.types.ResetIMSIRq;
import com.rssl.phizic.synchronization.types.ServiceStatusRes;
import com.rssl.phizic.synchronization.types.UpdateProfilesRq;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ConsumerProductOfferResultRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.text.ParseException;

/**
 * @author Rtischeva
 * @ created 18.12.14
 * @ $Author$
 * @ $Revision$
 */
public class TestEjbMessage extends XmlMessage
{
	private SendSmsRequest sendSmsRequest;

	private SendSmsWithImsiRequest sendSmsWithImsiRequest;

	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ChargeLoanApplicationRq chargeLoanApplicationRqRelease19;
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq chargeLoanApplicationRqRelease16;
	private com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq chargeLoanApplicationRqRelease13;

	private EnquiryRequestERIB enquiryRequestERIB;

	private UpdateProfilesRq updateProfilesRq;

	private ServiceStatusRes serviceStatusRes;

	private ResetIMSIRq resetIMSIRq;
	private GetPrivateClientRq getPrivateClientRq;
	private ConcludeEDBORq concludeEDBORq;
	private CreateCardContractRq createCardContractRq;
	private IssueCardRq issueCardRq;
	private CustAddRq custAddRq;
	private CRMNewApplRq crmNewApplRq;
	private ERIBUpdApplStatusRq eribUpdApplStatusRq;
	private SearchApplicationRq searchApplicationRq;
	private OfferTicket ticket;
	private ConsumerProductOfferResultRq ñonsumerProductOfferResultRq;

	public TestEjbMessage(ConsumerProductOfferResultRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTm());
		this.ñonsumerProductOfferResultRq = request;
	}

	public TestEjbMessage(OfferTicket request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTm());
		this.ticket = request;
	}

	public TestEjbMessage(SearchApplicationRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTm());
		this.searchApplicationRq = request;
	}


	public TestEjbMessage(SendSmsRequest request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTime());
		this.sendSmsRequest = request;
	}

	public TestEjbMessage(SendSmsWithImsiRequest request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTime());
		this.sendSmsWithImsiRequest = request;
	}

	public TestEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ChargeLoanApplicationRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID(), request.getRqTm());
		this.chargeLoanApplicationRqRelease19 = request;
	}

	public TestEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID(), request.getRqTm());
		this.chargeLoanApplicationRqRelease16 = request;
	}

	public TestEjbMessage(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID(), request.getRqTm());
		this.chargeLoanApplicationRqRelease13 = request;
	}

	public TestEjbMessage(EnquiryRequestERIB request, String message)
	{
		super(message, request.getClass(), request.getRqUID(), request.getRqTm());
		this.enquiryRequestERIB = request;
	}

	public TestEjbMessage(UpdateProfilesRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTime());
		this.updateProfilesRq = request;
	}

	public TestEjbMessage(ServiceStatusRes response, String message)
	{
		super(message, response.getClass(), response.getRqUID().toString(), response.getRqTime());
		this.serviceStatusRes = response;
	}

	public TestEjbMessage(ResetIMSIRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTime());
		this.resetIMSIRq = request;
	}

	public TestEjbMessage(GetPrivateClientRq request, String message) throws ParseException
	{
		super(message, request.getClass(), request.getRqUID().toString(), DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(request.getRqTm())));
		this.getPrivateClientRq = request;
	}

	public TestEjbMessage(ConcludeEDBORq request, String message) throws ParseException
	{
		super(message, request.getClass(), request.getRqUID().toString(), DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(request.getRqTm())));
		this.concludeEDBORq = request;
	}

	public TestEjbMessage(CreateCardContractRq request, String message) throws ParseException
	{
		super(message, request.getClass(), request.getRqUID().toString(), DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(request.getRqTm())));
		this.createCardContractRq = request;
	}

	public TestEjbMessage(IssueCardRq request, String message) throws ParseException
	{
		super(message, request.getClass(), request.getRqUID().toString(), DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(request.getRqTm())));
		this.issueCardRq = request;
	}

	public TestEjbMessage(CustAddRq request, String message)  throws ParseException
	{
		super(message, request.getClass(), request.getRqUID().toString(), DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(request.getRqTm())));
		this.custAddRq = request;
	}

	public TestEjbMessage(CRMNewApplRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTm());
		this.crmNewApplRq = request;
	}

	public TestEjbMessage(ERIBUpdApplStatusRq request, String message)
	{
		super(message, request.getClass(), request.getRqUID().toString(), request.getRqTm());
		this.eribUpdApplStatusRq = request;
	}

	public SendSmsRequest getSendSmsRequest()
	{
		return sendSmsRequest;
	}

	public SendSmsWithImsiRequest getSendSmsWithImsiRequest()
	{
		return sendSmsWithImsiRequest;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq getChargeLoanApplicationRqRelease16()
	{
		return chargeLoanApplicationRqRelease16;
	}

	public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq getChargeLoanApplicationRqRelease13()
	{
		return chargeLoanApplicationRqRelease13;
	}

	public EnquiryRequestERIB getEnquiryRequestERIB()
	{
		return enquiryRequestERIB;
	}

	public UpdateProfilesRq getUpdateProfilesRq()
	{
		return updateProfilesRq;
	}

	public ServiceStatusRes getServiceStatusRes()
	{
		return serviceStatusRes;
	}

	public ResetIMSIRq getResetIMSIRq()
	{
		return resetIMSIRq;
	}

	public ConcludeEDBORq getConcludeEDBORq()
	{
		return concludeEDBORq;
	}

	public CreateCardContractRq getCreateCardContractRq()
	{
		return createCardContractRq;
	}

	public GetPrivateClientRq getGetPrivateClientRq()
	{
		return getPrivateClientRq;
	}

	public IssueCardRq getIssueCardRq()
	{
		return issueCardRq;
	}

	public CustAddRq getCustAddRq()
	{
		return custAddRq;
	}

	public CRMNewApplRq getCrmNewApplRq()
	{
		return crmNewApplRq;
	}

	public ERIBUpdApplStatusRq getEribUpdApplStatusRq()
	{
		return eribUpdApplStatusRq;
	}

	public SearchApplicationRq getSearchApplicationRq()
	{
		return searchApplicationRq;
	}

	public OfferTicket getTicket()
	{
		return ticket;
	}
}
