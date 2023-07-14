package com.rssl.phizicgate.esberibgate.documents.senders.GetPrivateOperationScanClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.GetPrivateOperationScanClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OfflineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateOperationScanRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.VSPOperationType;

/**
 * @author komarov
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса заявки на получение копии документа на электронную почту
 */

class GetPrivateOperationScanClaimProcessor extends OfflineMessageProcessorBase
{
	private static final String SYSTEM_ID = "urn:sbrfsystems:99-erib";
	private static final String DATE_FORMAT = "yyyy.MM.dd";
	private static final String REQUEST_TYPE = GetPrivateOperationScanRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;
	private final GetPrivateOperationScanClaim document;
	private GetPrivateOperationScanRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 */
	GetPrivateOperationScanClaimProcessor(GateFactory factory, GetPrivateOperationScanClaim document) throws GateException
	{
		super(ESBSegment.federal);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.document = document;
	}

	protected Object initialize() throws GateException, GateLogicException
	{
		this.request = buildRequestObject(document);
		return request;
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return SYSTEM_ID;
	}

	public void processAfterSend(Request request)
	{
		document.setExternalId(request.getJmsMessageId());
	}

	private GetPrivateOperationScanRq buildRequestObject(GetPrivateOperationScanClaim claim) throws GateException, GateLogicException
	{
		GetPrivateOperationScanRq result = new GetPrivateOperationScanRq();
		result.setRqUID(RequestHelper.generateUUID());
		result.setRqTm(RequestHelper.generateRqTm());
		result.setSPName(SYSTEM_ID);

		result.setBankInfo(makeBankInfo(requestHelper.getRbTbBrch(claim)));
		result.setContactInfo(makeContactInfo(claim));
		result.setVSPOperationInfo(makeVSPOperationInfo(claim));

		return result;
	}

 	protected GetPrivateOperationScanRq.BankInfo makeBankInfo(String rbTbBrch)
	{
		GetPrivateOperationScanRq.BankInfo info = new GetPrivateOperationScanRq.BankInfo();
		info.setRbTbBrchId(rbTbBrch);
		return info;
	}

	private GetPrivateOperationScanRq.ContactInfo makeContactInfo(GetPrivateOperationScanClaim claim)
	{
		GetPrivateOperationScanRq.ContactInfo info = new GetPrivateOperationScanRq.ContactInfo();
		info.setEmailAddr(claim.getEMail());
		return info;
	}

	private VSPOperationType makeVSPOperationInfo(GetPrivateOperationScanClaim claim)
	{
		VSPOperationType info = new VSPOperationType();
		info.setAmount(claim.getAmount());
		info.setClientName(claim.getFIO());
		info.setIsDebit(claim.isDebit());
		info.setAuthorizationCode(claim.getAuthorisationCode() == null ? 0 : claim.getAuthorisationCode());
		info.setPayDate(DateHelper.formatDateToStringOnPattern(claim.getSendOperationDate(), DATE_FORMAT));
		return info;
	}
}
