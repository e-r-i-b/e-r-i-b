package com.rssl.phizicgate.esberibgate.documents.senders.EarlyLoanRepaymentClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OfflineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RepaymentRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RequestPrivateEarlyRepaymentRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SPNameType;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * конструктор запросов на досрочное погашение кредитов
 * @author basharin
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EarlyLoanRepaymentClaimProcessor extends OfflineMessageProcessorBase
{
	private static final int ACCOUNT_LENGTH = 20;
	private static final String SYSTEM_ID = "urn:sbrfsystems:99-erib";
	private static final String REQUEST_TYPE = RequestPrivateEarlyRepaymentRq.class.getSimpleName();
	private static final String RUB = "RUB";
	private static final String RUR = "RUR";

	private final PaymentsRequestHelper paymentsRequestHelper;

	private final EarlyLoanRepaymentClaim document;
	private RequestPrivateEarlyRepaymentRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 */
	public EarlyLoanRepaymentClaimProcessor(GateFactory factory, EarlyLoanRepaymentClaim document) throws GateException
	{
		super(ESBSegment.federal);
		paymentsRequestHelper = new PaymentsRequestHelper(factory);
		this.document = document;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		ExternalSystemHelper.check(EarlyLoanRepaymentClaimSender.CODE_EKP);
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

	protected RequestPrivateEarlyRepaymentRq buildRequestObject(EarlyLoanRepaymentClaim document) throws GateException, GateLogicException
	{
		RequestPrivateEarlyRepaymentRq earlyRepaymentRq = new RequestPrivateEarlyRepaymentRq();
		earlyRepaymentRq.setRqUID(document.getExternalId());
		earlyRepaymentRq.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(new GregorianCalendar()));
		earlyRepaymentRq.setOperUID(document.getOperUID());
		earlyRepaymentRq.setSPName(SPNameType.BP_ERIB);
		earlyRepaymentRq.setBankInfo(RequestHelper.makeBankInfo(paymentsRequestHelper.getRbTbBrch(document.getInternalOwnerId())));
		earlyRepaymentRq.setRepaymentRequest(makeRepaymentRequest(document));
		return earlyRepaymentRq;
	}

	protected RepaymentRequestType makeRepaymentRequest(EarlyLoanRepaymentClaim document) {
		RepaymentRequestType repaymentRequest = new RepaymentRequestType();
		repaymentRequest.setProdId(document.getLoanIdentifier());
		repaymentRequest.setPayAccount(document.getChargeOffAccount());
		repaymentRequest.setRepaymentDate(XMLDatatypeHelper.formatDateWithoutTimeZone(document.getRepaymentDate()));
		repaymentRequest.setAmount(document.getRepaymentAmount().getDecimal());
		if(document.getRepaymentAmount().getCurrency().getCode().equals(RUB))
		{
			repaymentRequest.setCurrency(RUR);
		}
		else
		{
			repaymentRequest.setCurrency(document.getRepaymentAmount().getCurrency().getCode());
		}
		repaymentRequest.setFullRepayment(document.isPartial() ? 0L : 1L);
		repaymentRequest.setDateOfSign(XMLDatatypeHelper.formatDateWithoutTimeZone(Calendar.getInstance()));
		repaymentRequest.setType(document.getChargeOffAccount().length() < ACCOUNT_LENGTH ? "Card" : "Account");
		return repaymentRequest;
	}
}