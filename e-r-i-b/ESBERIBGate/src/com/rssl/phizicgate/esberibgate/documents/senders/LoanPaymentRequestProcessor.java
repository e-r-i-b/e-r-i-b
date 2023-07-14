package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.loans.LoanPaymentJMSRequestSender;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LoanCompositeId;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.util.Calendar;

/**
 * @author sergunin
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса графика платежей по кредиту
 */

public class LoanPaymentRequestProcessor extends OnlineMessageProcessorBase<LoanPaymentRs>
{
	private static final String REQUEST_TYPE = "LoanPaymentRq";

	private final Loan requestDataSource;
    private LoanPaymentRq request;
    private String rbTbBrch;

    public LoanPaymentRequestProcessor(Loan requestDataSource, String rbTbBrch)
    {
        super(getSenderSegment(), REQUEST_TYPE);
	    this.requestDataSource = requestDataSource;
        this.rbTbBrch = rbTbBrch;
    }

    private static ESBSegment getSenderSegment()
    {
        return LoanPaymentJMSRequestSender.SEGMENT;
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

    private LoanPaymentRq buildRequestObject(Loan loan) throws GateException, GateLogicException
    {
        // заглушка с ответом ESBMockLoanPaymentProcessor
        LoanPaymentRq rqst = new LoanPaymentRq();

        rqst.setRqUID(new RandomGUID().getStringValue());
        rqst.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
        rqst.setSPName(SPNameType.BP_ERIB);
        rqst.setOperUID(RequestHelperBase.generateOUUID());

        LoanCompositeId compositeId = EntityIdHelper.getLoanCompositeId(loan);
        BankInfoType bankInfoType = new BankInfoType();
        bankInfoType.setRbTbBrchId(rbTbBrch);
        rqst.setBankInfo(bankInfoType);

        LoanAcctIdType loanAcctType = new LoanAcctIdType();
        loanAcctType.setProdId(loan.getProdType());
        loanAcctType.setSystemId(compositeId.getSystemIdActiveSystem());
        loanAcctType.setAcctId(compositeId.getEntityId());

        BankInfoType bankInfoType1 = new BankInfoType();
        bankInfoType1.setRbBrchId(compositeId.getRbBrchId());
        loanAcctType.setBankInfo(bankInfoType1);

        rqst.setLoanAcctId(loanAcctType);

        return rqst;
    }

    @Override
    protected String getResponseId(LoanPaymentRs response)
    {
        return response.getRqUID();
    }

    @Override
    protected String getResponseErrorCode(LoanPaymentRs response)
    {
        return String.valueOf(response.getStatus().getStatusCode());
    }

    @Override
    protected String getResponseErrorMessage(LoanPaymentRs response)
    {
        return response.getStatus().getStatusDesc();
    }

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<LoanPaymentRs>> request, Response<LoanPaymentRs> response){}
}
