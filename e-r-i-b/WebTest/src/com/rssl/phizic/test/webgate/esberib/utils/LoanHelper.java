package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author gladishev
 * @ created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanHelper extends BaseResponseHelper
{
	private static final String SYSTEM_ID = "Loan_System";
	private static final String LOAN_CURRENCY = "RUB";
	private static final String LOAN_TYPE = "Кредит на покупку банка";
	private static ExternalResourceService resourceService = new ExternalResourceService();

	public LoanAcctRec_Type[] getLoanAcctRecs(Login login)
	{
		List<LoanLink> loans;
		try
		{
			loans = (login==null) ? null : resourceService.getLinks(login, LoanLink.class);
		}
		catch (BusinessException e)
		{
			loans = new ArrayList<LoanLink>(); 
		}
		catch (BusinessLogicException e)
		{
			loans = new ArrayList<LoanLink>();
		}

		boolean isNew = !(loans != null && !loans.isEmpty());
		int count = isNew ? 2 : loans.size();
		LoanAcctRec_Type[] result = new LoanAcctRec_Type[count];

		for (int i=0; i<count; i++)
		{
			String accountNumber = isNew ? RandomHelper.rand(24, RandomHelper.DIGITS) : loans.get(i).getNumber();
			result[i] = createLoanAcctRec(accountNumber);
		}
		return result;
	}


	private LoanAcctRec_Type createLoanAcctRec(String accountNumber)
	{
		LoanAcctRec_Type result = new LoanAcctRec_Type();

		//LoanAcctId
		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId(SYSTEM_ID);
		loanAcctId.setAcctId(accountNumber);
		loanAcctId.setAgreemtNum(RandomHelper.rand(14, RandomHelper.DIGITS));
		loanAcctId.setProdType("111111");
		loanAcctId.setLoanType(LOAN_TYPE);
		loanAcctId.setAcctCur(LOAN_CURRENCY);
		loanAcctId.setOrigAmt(new BigDecimal(30000000));
		result.setLoanAcctId(loanAcctId);
        loanAcctId.setProdType("12345123451234512345");
		//BankInfo
		result.setBankInfo(getMockBankInfo());

		//BankAcctInfo
		BankAcctInfo_Type bankAcctInfo = new BankAcctInfo_Type();
		Random rand = new Random();
		bankAcctInfo.setAnn(rand.nextBoolean());

		GregorianCalendar startDt = new GregorianCalendar();
		startDt.add(Calendar.DATE, -1 * rand.nextInt(100));
		bankAcctInfo.setStartDt(getStringDateTime(startDt));
		GregorianCalendar expDate = new GregorianCalendar();
		expDate.add(Calendar.DATE, rand.nextInt(100));
		bankAcctInfo.setExpDt(getStringDateTime(expDate));
		result.setBankAcctInfo(bankAcctInfo);

		//CustRec
		CustRec_Type  custRec  = new CustRec_Type();
		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonRole(1L);
		personInfo.setPersonName(new PersonName_Type("Иванов", "Сидор", "Петрович", null));
		personInfo.setIdentityCard(new IdentityCard_Type("21","12","1234","УФМС",
				"02",getStringDateTime(new GregorianCalendar()),getStringDateTime(new GregorianCalendar())));
		custInfo.setPersonInfo(personInfo);
		custRec.setCustInfo(custInfo);
		result.setCustRec(custRec);

		return result;
	}

	public IFXRs_Type createLoanInfoRs(IFXRq_Type parameters)
	{
		LoanInfoRq_Type request = parameters.getLoanInfoRq();
		String accountNumber = request.getLoanAcctId().getAcctId();

		IFXRs_Type ifxRs = new IFXRs_Type();

		LoanInfoRs_Type response = new LoanInfoRs_Type();
		response.setRqUID(request.getRqUID());
		response.setRqTm(getRqTm());
		response.setOperUID(request.getOperUID());

		LoanRec_Type loanRec = new LoanRec_Type();

		LoanInfo_Type loanInfo = new LoanInfo_Type();
		LoanAcctId_Type accId = new LoanAcctId_Type();
		accId.setSystemId(SYSTEM_ID);
		accId.setAcctId(accountNumber);
		loanInfo.setLoanAcctId(accId);
		loanInfo.setAgreemtNum("12345123451234512345");
		loanInfo.setProdType("111111");
		loanInfo.setLoanType(LOAN_TYPE);
		loanInfo.setAcctCur(LOAN_CURRENCY);
		loanInfo.setCurAmt(new BigDecimal("3000000"));
        loanInfo.setProdType("12345123451234512345");
//		loanInfo.setPeriod(new Long(1460));     в пилотной версии не передаются
//		loanInfo.setCreditingRate(new BigDecimal(13));
		/*GregorianCalendar regNextPayDate = new GregorianCalendar();
		regNextPayDate.add(Calendar.MONTH, 1);
		regNextPayDate.add(Calendar.DAY_OF_MONTH, 5);*/
		/*loanInfo.setRegNextPayDate(getStringDateTime(regNextPayDate));*/
		/*GregorianCalendar nextPayDate = new GregorianCalendar();
		regNextPayDate.add(Calendar.MONTH, 1);*/
		/*loanInfo.setNextPayDate(getStringDateTime(nextPayDate));
		loanInfo.setNextPaySum(new BigDecimal(300));*/
//		loanInfo.setPaymentStatus("2");
//		loanInfo.setLoanStatus("1");
//		loanInfo.setPrevPayDate(getDate());
//		loanInfo.setPrevPaySum(new BigDecimal(100));
//		loanInfo.setPayCard("123123123123");

		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustId("12324343");

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDateTime(new GregorianCalendar()));
		personInfo.setBirthPlace("г.Москва");
		personInfo.setTaxId("123456789056789");
		personInfo.setCitizenship("Россия");
		personInfo.setAdditionalInfo("ХХХ");
		personInfo.setPersonRole(new Long(1));
		personInfo.setPersonName(new PersonName_Type("Иванов", "Сидор", "Петрович", null));
		personInfo.setIdentityCard(new IdentityCard_Type("21","12","1234","УФМС",
				"02",getStringDateTime(new GregorianCalendar()),getStringDateTime(new GregorianCalendar())));
		custInfo.setPersonInfo(personInfo);

		custRec.setCustInfo(custInfo);
		loanInfo.setCustRec(new CustRec_Type[]{custRec});
		loanRec.setLoanInfo(loanInfo);

		loanRec.setBankInfo(getMockBankInfo());

		BankAcctInfo_Type bankAcctInfo = new BankAcctInfo_Type();
		Random rand = new Random();
		bankAcctInfo.setIsAnn(rand.nextBoolean());

		GregorianCalendar startDt = new GregorianCalendar();
		startDt.add(Calendar.DATE, -1 * rand.nextInt(100));
		bankAcctInfo.setStartDt(getStringDateTime(startDt));
		GregorianCalendar expDt = new GregorianCalendar();
		expDt.add(Calendar.DATE, rand.nextInt(30));
		bankAcctInfo.setExpDt(getStringDateTime(expDt));
		loanRec.setBankAccInfo(bankAcctInfo);

		Status_Type status = new Status_Type();
		status.setStatusCode(0);

		loanRec.setStatus(status);

		response.setLoanRec(loanRec);

		ifxRs.setLoanInfoRs(response);
		return ifxRs;
	}

	public IFXRs_Type createLoanInqRs(IFXRq_Type parameters)
	{
		LoanInqRq_Type request = parameters.getLoanInqRq();
		IFXRs_Type ifxRs = new IFXRs_Type();

		LoanInqRs_Type response = new LoanInqRs_Type();
		response.setRqUID(request.getRqUID());
		response.setRqTm(getRqTm());
		response.setOperUID(request.getOperUID());
		response.setStatus(new Status_Type(0 , null, null, null));

		LoanRec_Type rec = new LoanRec_Type();
		LoanInfo_Type loanInfo = new LoanInfo_Type();

		if(request.getLoanAcctId().getCurAmt() != null)
		{
			AcctBal_Type[] balancesFull = new AcctBal_Type[2];
			Calendar date = new GregorianCalendar();
			date.add(Calendar.DATE, 10);
			balancesFull[0] = new AcctBal_Type("103", "сумма платежа для закрытия кредита", new BigDecimal(238831.72), null, LOAN_CURRENCY, null, null, null);
			date.add(Calendar.DATE, 10);
			balancesFull[1] = new AcctBal_Type("104", "сумма платежа", new BigDecimal(4137.55), null, LOAN_CURRENCY, null, null, null);
			loanInfo.setAcctBalFull(balancesFull);

			AcctBal_Type[] balancesOnDate = new AcctBal_Type[1];
			balancesOnDate[0] = new AcctBal_Type("5", "Срочные проценты", new BigDecimal(4137.55), null, LOAN_CURRENCY, new Long(2), "123456789024312", null);
			loanInfo.setAcctBalOnDate(balancesOnDate);
		}
		else
		{
			AcctBal_Type[] balancesFull = new AcctBal_Type[3];
			Calendar date = new GregorianCalendar();
			balancesFull[0] = new AcctBal_Type("101", "сумма платежа для исполнения обязательств по сроку дата_1", new BigDecimal(4137.55), null, LOAN_CURRENCY, null, null, getStringDateTime(date));
			date.add(Calendar.DATE, 10);
			balancesFull[1] = new AcctBal_Type("103", "сумма платежа для закрытия кредита", new BigDecimal(238831.72), null, LOAN_CURRENCY, null, null, null);
			date.add(Calendar.DATE, 10);
			balancesFull[2] = new AcctBal_Type("104", "сумма платежа", new BigDecimal(4137.55), null, LOAN_CURRENCY, null, null, null);
			loanInfo.setAcctBalFull(balancesFull);

			AcctBal_Type[] balancesOnDate = new AcctBal_Type[1];			
			balancesOnDate[0] = new AcctBal_Type("5", "Срочные проценты", new BigDecimal(4137.55), null, LOAN_CURRENCY, new Long(1), "123456789024312", null);
			loanInfo.setAcctBalOnDate(balancesOnDate);
		}

		loanInfo.setIdSpacing("1d5y");
		rec.setLoanInfo(loanInfo);
		response.setLoanRec(rec);

		ifxRs.setLoanInqRs(response);
		return ifxRs;
	}

	public IFXRs_Type createLoanPaymentRs(IFXRq_Type parameters)
	{
		LoanPaymentRq_Type request = parameters.getLoanPaymentRq();
		String accountNumber = request.getLoanAcctId().getAcctId();

		IFXRs_Type ifxRs = new IFXRs_Type();

		LoanPaymentRs_Type response = new LoanPaymentRs_Type();
		response.setRqUID(request.getRqUID());
		response.setRqTm(getRqTm());
		response.setOperUID(request.getOperUID());
		response.setStatus(getStatus());
		response.setSystemId(SYSTEM_ID);
		response.setAcctId(accountNumber);
		response.setDoneAmount(new BigDecimal(300000));
		response.setRemainAmount(new BigDecimal(11300000));
		response.setFineAmount(new BigDecimal(93000));
		response.setMaxSize(new Long(3));

		LoanPaymentRec_Type rec1 = new LoanPaymentRec_Type();
		rec1.setLoanPaymentNumber(1);
		rec1.setLoanPaymentStatus("1");
		Calendar date1 = new GregorianCalendar();
		date1.add(Calendar.DATE, -10);
		rec1.setLoanPaymentDate(getStringDateTime(date1));
		AcctBal_Type[] balancesOnDate = new AcctBal_Type[4];
		balancesOnDate[0] = new AcctBal_Type("4", "Сумма задолженности по основному долгу", new BigDecimal(120), null, LOAN_CURRENCY, new Long(1), "12322144124", null);
		balancesOnDate[1] = new AcctBal_Type("5", "Срочные проценты", new BigDecimal(50), null, LOAN_CURRENCY, new Long(2), "12322144124", null);
		balancesOnDate[2] = new AcctBal_Type("13", "Неустойка за просрочку процентов", new BigDecimal(77), null, LOAN_CURRENCY, new Long(3), "12322144124", null);
		balancesOnDate[3] = new AcctBal_Type("44", "Неустойка за просрочку платы за операции по ссудному счету", new BigDecimal(17), null, LOAN_CURRENCY, new Long(4), null, null);
		rec1.setAcctBalOnDate(balancesOnDate);

		LoanPaymentRec_Type rec2 = new LoanPaymentRec_Type();
		rec2.setLoanPaymentNumber(2);
		rec2.setLoanPaymentStatus("2");
		Calendar date2 = new GregorianCalendar();
		date2.add(Calendar.DATE, 3);
		rec2.setLoanPaymentDate(getStringDateTime(date2));
		balancesOnDate = new AcctBal_Type[3];
		balancesOnDate[0] = new AcctBal_Type("4", "Сумма задолженности по основному долгу", new BigDecimal(110), null, LOAN_CURRENCY, new Long(1), null, null);
		balancesOnDate[1] = new AcctBal_Type("5", "Срочные проценты", new BigDecimal(10), null, LOAN_CURRENCY, new Long(2), "12322144124", null);
		balancesOnDate[2] = new AcctBal_Type("13", "Неустойка за просрочку процентов", new BigDecimal(34), null, LOAN_CURRENCY, new Long(3), null, null);
		rec2.setAcctBalOnDate(balancesOnDate);

		LoanPaymentRec_Type rec3 = new LoanPaymentRec_Type();
		rec3.setLoanPaymentNumber(3);
		rec3.setLoanPaymentStatus("3");
		Calendar date3 = new GregorianCalendar();
		date3.add(Calendar.DATE, 25);
		rec3.setLoanPaymentDate(getStringDateTime(date3));
		balancesOnDate = new AcctBal_Type[3];
		balancesOnDate[0] = new AcctBal_Type("4", "Сумма задолженности по основному долгу", new BigDecimal(330), null, LOAN_CURRENCY, new Long(1), null, null);
		balancesOnDate[1] = new AcctBal_Type("5", "Срочные проценты", new BigDecimal(34), null, LOAN_CURRENCY, new Long(2), null, null);
		balancesOnDate[2] = new AcctBal_Type("6", "Просроченный основной долг", new BigDecimal(99), null, LOAN_CURRENCY, new Long(3), null, null);
		rec3.setAcctBalOnDate(balancesOnDate);

		response.setLoanPaymentRec(new LoanPaymentRec_Type[]{rec1, rec2, rec3});

		ifxRs.setLoanPaymentRs(response);
		return ifxRs;
	}
}
