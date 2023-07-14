package com.rssl.phizic.esb.ejb.mock.federal.loan;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanListRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanListRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.PrivateLoanInfoType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

/**
 * @author sergunin
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при запросе перевода с карты на вклад
 */

public class ESBMockGetPrivateLoanListProcessor extends FederalESBMockProcessorBase<GetPrivateLoanListRq, GetPrivateLoanListRs>
{
	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockGetPrivateLoanListProcessor(Module module)
	{
		super(module);
	}

    @Override
    protected boolean needSendResult(ESBMessage<GetPrivateLoanListRq> xmlRequest, GetPrivateLoanListRs message)
    {
        return true;
    }

    @Override
    protected boolean needSendOnline(ESBMessage<GetPrivateLoanListRq> xmlRequest, GetPrivateLoanListRs message)
    {
        return true;
    }

    @Override
	protected void process(ESBMessage<GetPrivateLoanListRq> xmlRequest)
	{
        GetPrivateLoanListRs response = new GetPrivateLoanListRs();

        String service = "LoanInfoRequest";

        GetPrivateLoanListRq request = xmlRequest.getObject();

        response.setStatus(getStatusInstance(0, "Ok", "Ok"));
        response.setRqTm(request.getRqTm());
        response.setSystemId(request.getLoanAcctIds().get(0).getSystemId());
        response.setRqUID(request.getRqUID());
        response.setOperUID(request.getOperUID());

        GetPrivateLoanListRs.LoanRec loanRec = new GetPrivateLoanListRs.LoanRec();
        PrivateLoanInfoType privateLoanInfoType = new PrivateLoanInfoType();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, -1);
        privateLoanInfoType.setPaymentDate(DateHelper.formatDateToString(date));
        privateLoanInfoType.setProdId(request.getLoanAcctIds().get(0).getProdId());
        privateLoanInfoType.setRecPayment(new BigDecimal(new Random().nextInt(1000000)));
        loanRec.setLoanInfo(privateLoanInfoType);
        response.getLoanRecs().add(loanRec);

        send(xmlRequest, response, service);
	}

}
