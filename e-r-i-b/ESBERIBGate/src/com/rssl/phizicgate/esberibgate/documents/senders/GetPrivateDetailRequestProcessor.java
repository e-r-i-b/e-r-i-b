package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LoanCompositeId;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSRequestSender;

import java.util.Calendar;

/**
 * @author sergunin
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса детальной информации по кредиту
 */

public class GetPrivateDetailRequestProcessor extends OnlineMessageProcessorBase<GetPrivateLoanDetailsRs>
{
	private static final String REQUEST_TYPE = "GetPrivateLoanDetailsRq";

	private final Loan requestDataSource;
	private GetPrivateLoanDetailsRq request;
    private String rbTbBrch;

	public GetPrivateDetailRequestProcessor(Loan requestDataSource, String rbTbBrch)
	{
		super(getSenderSegment(), REQUEST_TYPE);
		this.requestDataSource = requestDataSource;
        this.rbTbBrch = rbTbBrch;
	}

	private static ESBSegment getSenderSegment()
	{
		return AbstractJMSRequestSender.SEGMENT;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getLoanAcctId().getSystemId();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

    @Override protected String getMonitoringDocumentType()
    {
        return Request.SKIP_MONITORING;
    }

    @Override protected Object initialize() throws GateException, GateLogicException
    {
        request = buildRequestObject(requestDataSource);
        return request;
    }

    private GetPrivateLoanDetailsRq buildRequestObject(Loan loan) throws GateException, GateLogicException
	{
        // заглушка с ответом ESBMockGetPrivateLoanDetailProcessor
        GetPrivateLoanDetailsRq detailsRq = new GetPrivateLoanDetailsRq();

        detailsRq.setRqUID(new RandomGUID().getStringValue());
        detailsRq.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
        detailsRq.setSPName(SPNameType.BP_ERIB);
        detailsRq.setOperUID(RequestHelperBase.generateOUUID());

        LoanCompositeId compositeId = EntityIdHelper.getLoanCompositeId(loan);
        BankInfoType bankInfoType = new BankInfoType();
        bankInfoType.setRbTbBrchId(rbTbBrch);
        detailsRq.setBankInfo(bankInfoType);

        LoanAcctType loanAcctType = new LoanAcctType();
        loanAcctType.setProdId(loan.getProdType());
        loanAcctType.setSystemId(compositeId.getSystemIdActiveSystem());

        detailsRq.setLoanAcctId(loanAcctType);

        return detailsRq;
	}

    @Override
    protected String getResponseId(GetPrivateLoanDetailsRs response)
    {
        return response.getRqUID();
    }

    @Override
    protected String getResponseErrorCode(GetPrivateLoanDetailsRs response)
    {
        return String.valueOf(response.getStatus().getStatusCode());
    }

    @Override
    protected String getResponseErrorMessage(GetPrivateLoanDetailsRs response)
    {
        return response.getStatus().getStatusDesc();
    }

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<GetPrivateLoanDetailsRs>> request, Response<GetPrivateLoanDetailsRs> response){}
}
