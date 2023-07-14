package com.rssl.phizic.esb.ejb.mock.federal.loan;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

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

public class ESBMockLoanPaymentProcessor extends FederalESBMockProcessorBase<LoanPaymentRq, LoanPaymentRs>
{
	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockLoanPaymentProcessor(Module module)
	{
		super(module);
	}

    @Override
    protected boolean needSendResult(ESBMessage<LoanPaymentRq> xmlRequest, LoanPaymentRs message)
    {
        return true;
    }

    @Override
    protected boolean needSendOnline(ESBMessage<LoanPaymentRq> xmlRequest, LoanPaymentRs message)
    {
        return true;
    }

    @Override
    protected void process(ESBMessage<LoanPaymentRq> xmlRequest)
    {
        LoanPaymentRs response = new LoanPaymentRs();

        String service = "LoanInfoRequest";

        LoanPaymentRq request = xmlRequest.getObject();

        response.setStatus(getStatusInstance(0, "Ok", "Ok"));
        response.setRqUID(request.getRqUID());
        response.setRqTm(request.getRqUID());
        response.setOperUID(request.getOperUID());
        response.setSystemId(request.getLoanAcctId().getSystemId());
        response.setAcctId(request.getLoanAcctId().getAcctId());

        response.setDoneAmount(new BigDecimal(300000));
        response.setRemainAmount(new BigDecimal(11300000));
        response.setFineAmount(new BigDecimal(93000));
        response.setMaxSize(new Long(3));


        Random rand = new Random();

        for (int i = 0; i < 40; i++)  {
            LoanPaymentRec paymentRec = new LoanPaymentRec();
            paymentRec.setLoanPaymentNumber(i+1);
            paymentRec.setLoanPaymentStatus("1");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, i);
            paymentRec.setLoanPaymentDate(DateHelper.formatDateToString(cal));
            paymentRec.setRemainDebt(new BigDecimal(rand.nextInt(100)));

            AcctBal acctBal = new AcctBal("BAKS_EXT_CRED", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 1L, "12322144124");
            paymentRec.getAcctBalOnDates().add(acctBal);
            acctBal = new AcctBal("BAKS_EXT_PRC", "Задолженность по процентам", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 2L, "12322144124");
            paymentRec.getAcctBalOnDates().add(acctBal);
            acctBal = new AcctBal("BAKS_EXT_PEN", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 3L, "12322144124");
            paymentRec.getAcctBalOnDates().add(acctBal);

            response.getLoanPaymentRecs().add(paymentRec);
        }

        StatusType statusType = new StatusType();
        statusType.setStatusCode(0L);
        response.setStatus(statusType);

        send(xmlRequest, response, service);
    }

}
