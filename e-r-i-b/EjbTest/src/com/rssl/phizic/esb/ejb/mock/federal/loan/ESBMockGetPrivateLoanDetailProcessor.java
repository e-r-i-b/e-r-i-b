package com.rssl.phizic.esb.ejb.mock.federal.loan;

import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author sergunin
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины
 */

public class ESBMockGetPrivateLoanDetailProcessor extends FederalESBMockProcessorBase<GetPrivateLoanDetailsRq, GetPrivateLoanDetailsRs>
{
	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockGetPrivateLoanDetailProcessor(Module module)
	{
		super(module);
	}

    @Override
    protected boolean needSendResult(ESBMessage<GetPrivateLoanDetailsRq> xmlRequest, GetPrivateLoanDetailsRs message)
    {
        return true;
    }

    @Override
    protected boolean needSendOnline(ESBMessage<GetPrivateLoanDetailsRq> xmlRequest, GetPrivateLoanDetailsRs message)
    {
        return true;
    }

    @Override
    protected void process(ESBMessage<GetPrivateLoanDetailsRq> xmlRequest)
    {
        GetPrivateLoanDetailsRs response = new GetPrivateLoanDetailsRs();

        String service = "LoanInfoRequest";

        GetPrivateLoanDetailsRq request = xmlRequest.getObject();


        response.setStatus(getStatusInstance(0, "Ok", "Ok"));
        response.setRqTm(request.getRqTm());
        response.setSystemId(request.getLoanAcctId().getSystemId());
        response.setRqUID(request.getRqUID());
        response.setOperUID(request.getOperUID());

        PrivateLoanDetailsType privateLoanDetailsType = new PrivateLoanDetailsType();
        privateLoanDetailsType.setProdId(request.getLoanAcctId().getProdId());
        privateLoanDetailsType.setLoanType("Тип кредита");
        privateLoanDetailsType.setPeriod(12);
        privateLoanDetailsType.setCreditingRate(new BigDecimal(5.5));
        privateLoanDetailsType.setLoanStatus(1);
        privateLoanDetailsType.setPrincipalBalance(new BigDecimal(10000));
        privateLoanDetailsType.setFullRepaymentAmount(new BigDecimal(100000));
        privateLoanDetailsType.setOverdue(1L); //может не быть, вот его и нет

	    List<EarlyRepaymentType> earlyRepaymentTypes = privateLoanDetailsType.getEarlyRepayments();
	    EarlyRepaymentType earlyRepaymentType = new EarlyRepaymentType();

	    earlyRepaymentType.setAmount(BigDecimal.TEN);
	    earlyRepaymentType.setDate(DateHelper.toXMLDateFormat(Calendar.getInstance().getTime()));
	    earlyRepaymentType.setStatus("Принято");
	    earlyRepaymentType.setAccount("42301810603291554671");
	    earlyRepaymentType.setRepaymentChannel("1");
	    earlyRepaymentType.setTerminationRequestId("123");

	    earlyRepaymentTypes.add(earlyRepaymentType);

        PrivateLoanDetailsType.Accounts accounts = new PrivateLoanDetailsType.Accounts();
        PrivateLoanDetailsType.Accounts.Account account = new PrivateLoanDetailsType.Accounts.Account();
        PrivateLoanDetailsType.Accounts.Account.Element element = new PrivateLoanDetailsType.Accounts.Account.Element();
        element.setId("40817810179688690948");       //номер счёта (счёт должен быть в валюте кредита)
        element.setPriority(new BigInteger("1"));
        account.getElements().add(element);
        accounts.setAccount(account);

        PrivateLoanDetailsType.Accounts.Card card = new PrivateLoanDetailsType.Accounts.Card();
        PrivateLoanDetailsType.Accounts.Card.Element elm = new PrivateLoanDetailsType.Accounts.Card.Element();
        elm.setId("9244610397227395");       //номер карты (счёт должен быть в валюте кредита)
        elm.setPriority(new BigInteger("2"));
        card.getElements().add(elm);
        accounts.setCard(card);
        privateLoanDetailsType.setAccounts(accounts);

        privateLoanDetailsType.setBAKS(new BigInteger("34"));
        privateLoanDetailsType.setAutoGrantion(new BigInteger("2"));
        privateLoanDetailsType.setApplicationNumberCA("234234");

        CustRec custRec = new CustRec();
        custRec.setCustId("1");
        CustInfoType custInfoType = new CustInfoType();
        PersonInfoType personInfoType = new PersonInfoType();
        personInfoType.setPersonRole(2L);
        PersonName personName = new PersonName();
        personName.setFirstName("firstName");
        personName.setLastName("lastName");
        personName.setMiddleName("middleName");
        personInfoType.setPersonName(personName);
        custInfoType.setPersonInfo(personInfoType);
        custRec.setCustInfo(custInfoType);
        privateLoanDetailsType.getCustRecs().add(custRec);

        privateLoanDetailsType.setAgencyAddress("Адрес ВСП");

        Random rand = new Random();

        AcctBal acctBal = new AcctBal("BAKS_EXT_CRED", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 1L, "12322144124");
        privateLoanDetailsType.getAcctBalOnDates().add(acctBal);
        acctBal = new AcctBal("BAKS_EXT_PRC", "Задолженность по процентам", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 2L, "12322144124");
        privateLoanDetailsType.getAcctBalOnDates().add(acctBal);
        acctBal = new AcctBal("BAKS_EXT_PEN", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 3L, "12322144124");
        privateLoanDetailsType.getAcctBalOnDates().add(acctBal);
        acctBal = new AcctBal("КРЕДИТ", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 3L, "12322144124");
        privateLoanDetailsType.getAcctBalOnDates().add(acctBal);
        acctBal = new AcctBal("BAKS_EXT_OVE", "Просроченная задолженность по основному долгу", new BigDecimal(rand.nextInt(100)), new BigDecimal(rand.nextInt(100)), "RUB", 3L, "12322144124");
        privateLoanDetailsType.getAcctBalOnDates().add(acctBal);

        response.setLoanRec(privateLoanDetailsType);

        send(xmlRequest, response, service);
    }

}
