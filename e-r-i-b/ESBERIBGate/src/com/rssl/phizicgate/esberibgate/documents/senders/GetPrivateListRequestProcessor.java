package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.RandomGUID;
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
import java.util.List;

/**
 * @author sergunin
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса краткой информации о кредитах
 */

public class GetPrivateListRequestProcessor extends OnlineMessageProcessorBase<GetPrivateLoanListRs>
{
	private static final String REQUEST_TYPE = "GetPrivateLoanListRq";


    private final List<Loan> requestDataSource;
    private GetPrivateLoanListRq request;
    private String rbTbBrch;

    public GetPrivateListRequestProcessor(List<Loan> requestDataSource, String rbTbBrch)
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
        return request.getLoanAcctIds().get(0).getSystemId();
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

    private GetPrivateLoanListRq buildRequestObject(List<Loan> loans) throws GateException, GateLogicException
    {
        // заглушка с ответом ESBMockGetPrivateLoanListProcessor
        GetPrivateLoanListRq listRq = new GetPrivateLoanListRq();

        listRq.setRqUID(new RandomGUID().getStringValue());
        listRq.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
        listRq.setSPName(SPNameType.BP_ERIB);
        listRq.setOperUID(RequestHelperBase.generateOUUID());

        BankInfoType bankInfoType = new BankInfoType();
        bankInfoType.setRbTbBrchId(rbTbBrch);
        listRq.setBankInfo(bankInfoType);

	    for (Loan loan : loans)
	    {
		    LoanCompositeId compositeId = EntityIdHelper.getLoanCompositeId(loan);
		    LoanAcctType loanAcctType = new LoanAcctType();
		    loanAcctType.setProdId(loan.getProdType());
		    loanAcctType.setSystemId(compositeId.getSystemIdActiveSystem());

		    listRq.getLoanAcctIds().add(loanAcctType);
	    }

        listRq.setOnDate(XMLDatatypeHelper.formatDateWithoutTimeZone(Calendar.getInstance()));
        return listRq;
    }

    @Override
    protected String getResponseId(GetPrivateLoanListRs response)
    {
        return response.getRqUID();
    }

    @Override
    protected String getResponseErrorCode(GetPrivateLoanListRs response)
    {
        return String.valueOf(response.getStatus().getStatusCode());
    }

    @Override
    protected String getResponseErrorMessage(GetPrivateLoanListRs response)
    {
        return response.getStatus().getStatusDesc();
    }

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<GetPrivateLoanListRs>> request, Response<GetPrivateLoanListRs> response){}
}
