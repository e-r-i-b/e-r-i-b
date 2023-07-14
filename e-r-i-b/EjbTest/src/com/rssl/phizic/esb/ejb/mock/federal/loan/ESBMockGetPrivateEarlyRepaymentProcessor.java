package com.rssl.phizic.esb.ejb.mock.federal.loan;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * заглушка, имитирующая работу шины. Посылает ответ на заявку о досрочном погашении кредита
 * @author basharin
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 */

public class ESBMockGetPrivateEarlyRepaymentProcessor extends FederalESBMockProcessorBase<RequestPrivateEarlyRepaymentRq, RequestPrivateEarlyRepaymentRs>
{
	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockGetPrivateEarlyRepaymentProcessor(Module module)
	{
		super(module);
	}

    @Override
    protected boolean needSendResult(ESBMessage<RequestPrivateEarlyRepaymentRq> xmlRequest, RequestPrivateEarlyRepaymentRs message)
    {
        return true;
    }

    @Override
    protected boolean needSendOnline(ESBMessage<RequestPrivateEarlyRepaymentRq> xmlRequest, RequestPrivateEarlyRepaymentRs message)
    {
        return false;
    }

    @Override
    protected void process(ESBMessage<RequestPrivateEarlyRepaymentRq> xmlRequest)
    {
	    RequestPrivateEarlyRepaymentRs response = new RequestPrivateEarlyRepaymentRs();

        String service = "LoanInfoRequest";

	    RequestPrivateEarlyRepaymentRq request = xmlRequest.getObject();
        response.setStatus(getStatusInstance(0, "", "", ""));
        response.setRqTm(request.getRqTm());
        response.setSystemId("BP_ES");
        response.setRqUID(request.getRqUID());
        response.setOperUID(request.getOperUID());

	    PrivateEarlyTerminationResultType terminationResultType = new PrivateEarlyTerminationResultType();
	    terminationResultType.setProdId("123");
	    response.setPrivateEarlyTerminationResult(terminationResultType);

        send(xmlRequest, response, service);
    }

}
