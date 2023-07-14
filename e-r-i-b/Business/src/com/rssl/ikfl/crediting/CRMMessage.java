package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ConsumerProductOfferResultRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRs;

/**
 * @author Erkin
 * @ created 02.01.2015
 * @ $Author$
 * @ $Revision$
 */
@Immutable
@SuppressWarnings("PublicField")
public class CRMMessage extends XmlMessage
{
	public final GetCampaignerInfoRq offerRequest;

	public final GetCampaignerInfoRs offerResponse;

	public final RegisterRespondToMarketingProposeRq feedbackRequest;

	public final RegisterRespondToMarketingProposeRs feedbackResponse;

	public final CRMNewApplRq phoneCallbackRequest;

	public final ERIBUpdApplStatusRq updateApplStatusRequest;

	public final SearchApplicationRq searchApplicationRequest;

	public final OfferTicket ticket;

	public final ConsumerProductOfferResultRq consumerProductOfferResultRq;

	public CRMMessage(SearchApplicationRq searchApplicationRq, String message)
	{
		super(message, searchApplicationRq.getClass(), searchApplicationRq.getRqUID(), searchApplicationRq.getRqTm());
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = searchApplicationRq;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	public CRMMessage(OfferTicket ticket, String message)
	{
		super(message, ticket.getClass(), ticket.getRqUID(), ticket.getRqTm());
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = ticket;
		this.consumerProductOfferResultRq = null;
	}
	/**
	 * ctor
	 * @param offerRequest
	 * @param message
	 */
	public CRMMessage(GetCampaignerInfoRq offerRequest, String message)
	{
		super(message, offerRequest.getClass(), offerRequest.getRqUID(), XMLDatatypeHelper.parseDateTime(offerRequest.getRqTm()));
		this.offerRequest = offerRequest;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	/**
	 * ctor
	 * @param offerResponse
	 * @param message
	 */
	public CRMMessage(GetCampaignerInfoRs offerResponse, String message)
	{
		super(message, offerResponse.getClass(), offerResponse.getRqUID(), XMLDatatypeHelper.parseDateTime(offerResponse.getRqTm()));
		this.offerRequest = null;
		this.offerResponse = offerResponse;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	/**
	 * ctor
	 * @param feedbackRequest
	 * @param message
	 */
	public CRMMessage(RegisterRespondToMarketingProposeRq feedbackRequest, String message)
	{
		super(message, feedbackRequest.getClass(), feedbackRequest.getRqUID(), XMLDatatypeHelper.parseDateTime(feedbackRequest.getRqTm()));
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = feedbackRequest;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	/**
	 * ctor
	 * @param feedbackResponse
	 * @param message
	 */
	public CRMMessage(RegisterRespondToMarketingProposeRs feedbackResponse, String message)
	{
		super(message, feedbackResponse.getClass(), feedbackResponse.getRqUID(), XMLDatatypeHelper.parseDateTime(feedbackResponse.getRqTm()));
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = feedbackResponse;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	public CRMMessage(CRMNewApplRq phoneCallbackRequest, String message)
	{
		super(message, phoneCallbackRequest.getClass(), phoneCallbackRequest.getRqUID(), phoneCallbackRequest.getRqTm());
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = phoneCallbackRequest;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	public CRMMessage(ERIBUpdApplStatusRq updateApplStatusRequest, String message)
	{
		super(message, updateApplStatusRequest.getClass(), updateApplStatusRequest.getRqUID(), updateApplStatusRequest.getRqTm());
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = updateApplStatusRequest;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = null;
	}

	public CRMMessage(ConsumerProductOfferResultRq consumerProductOfferResultRq, String message)
	{
		super(message, consumerProductOfferResultRq.getClass(), consumerProductOfferResultRq.getRqUID(), consumerProductOfferResultRq.getRqTm());
		this.offerRequest = null;
		this.offerResponse = null;
		this.feedbackRequest = null;
		this.feedbackResponse = null;
		this.phoneCallbackRequest = null;
		this.updateApplStatusRequest = null;
		this.searchApplicationRequest = null;
		this.ticket = null;
		this.consumerProductOfferResultRq = consumerProductOfferResultRq;
	}
}
