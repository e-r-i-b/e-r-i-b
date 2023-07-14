package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.ClientImpl;
import com.rssl.phizicgate.esberibgate.types.DateDebtItemImpl;
import com.rssl.phizicgate.esberibgate.types.loans.*;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

import static com.rssl.phizicgate.esberibgate.types.loans.DateDebtItemTypesHelper.DateDebtItemType;

/**
 * @author gladishev
 * @ created 28.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanResponseSerializer extends BaseResponseSerializer
{

    private static final String BAKS_EXT_PRC = "BAKS_EXT_PRC";
    private static final String BAKS_EXT_PEN = "BAKS_EXT_PEN";
    private static final String BAKS_EXT_CRED = "BAKS_EXT_CRED";
    private static final String BAKS_EXT_OVE = "BAKS_EXT_OVE";
    private static final String CREDIT = "������";

	/**
	 * ���������� ������ ��������
	 * @param ifxRs - ���������� �����
	 * @param clientId - Id ������� �������� ����������� �����
	 * @return ������ ��������
	 */
	public List<Loan> fillLoans(IFXRs_Type ifxRs, Long clientId)
	{
		if (ifxRs == null)
		{
			return Collections.emptyList();
		}

		BankAcctInqRs_Type bankAcctInqRs = ifxRs.getBankAcctInqRs();
		Status_Type        statusType    = bankAcctInqRs.getStatus();

		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			return Collections.emptyList();
		}

		LoanAcctRec_Type[] loanRecs = ifxRs.getBankAcctInqRs().getLoanAcctRec();
		if ((loanRecs==null) || (loanRecs.length==0))
		{
			return Collections.emptyList();
		}

		Map<String, Loan> loans = new HashMap<String, Loan>();
		for (LoanAcctRec_Type loanRec: loanRecs)
		{
			try
			{
				Loan loan       = fillLoan(loanRec, clientId);
				Loan doubleLoan = loans.get(loan.getAccountNumber());

				if (doubleLoan != null)
				{
					if (doubleLoan.getPersonRole() == PersonLoanRole.borrower || loan.getPersonRole() != PersonLoanRole.borrower)
						continue;
				}

				loans.put(loan.getAccountNumber(), loan);
			}
			catch(Exception e)
			{
				if (loanRec != null && loanRec.getLoanAcctId() != null)
				{
					log.error("������ ��� ���������� ������� �" + loanRec.getLoanAcctId().getAcctId(), e);
				}
				else
				{
					log.error("������ ��� ���������� �������", e);
				}
			}
		}

		return new ArrayList<Loan>(loans.values());
	}

	/**
	 * ���������� ���������� � �������
	 * @param loanRec - ������ � �������
	 * @param clientId - id �������
	 * @return ������
	 */
	private Loan fillLoan(LoanAcctRec_Type loanRec, Long clientId) throws GateException, GateLogicException
	{
		LoanImpl loan = new LoanImpl();

		LoanAcctId_Type   accId        = loanRec.getLoanAcctId();
		BankAcctInfo_Type bankAcctInfo = loanRec.getBankAcctInfo();
		Currency          currency     = getCurrencyByString(accId.getAcctCur());

		loan.setId(EntityIdHelper.createLoanCompositeId(loanRec, clientId));
		loan.setAnnuity(bankAcctInfo.getAnn());
		loan.setAgreementNumber(accId.getAgreemtNum());
		loan.setAccountNumber(accId.getAcctId());
		loan.setDescription(accId.getLoanType());
		loan.setLoanAmount(createMoney(accId.getOrigAmt(), accId.getAcctCur()));
		loan.setTermStart(parseCalendar(bankAcctInfo.getStartDt()));
		loan.setTermEnd(parseCalendar(bankAcctInfo.getExpDt()));
		loan.setOffice(getOffice(loanRec.getBankInfo()));
		loan.setRate(loanRec.getCreditigRate());
        loan.setProdType(loanRec.getLoanAcctId().getProdType());

		LoanStatus_Type    loanStatus    = loanRec.getLoanStatus();
		PaymentStatus_Type paymentStatus = loanRec.getPaymentStatus();
		loan.setState(getLoanState(loanStatus == null ? null : loanStatus.getValue(), paymentStatus));


		BigDecimal prevPaymentSum = loanRec.getPrevPaySum();
		if (prevPaymentSum != null)
		{
			loan.setLastPaymentAmount(safeCreateMoney(prevPaymentSum, currency));
		}

		Date prevPaymentDate = loanRec.getPrevPayDate();
		if (prevPaymentDate != null)
		{
			loan.setLastPaymentDate(DateHelper.toCalendar(prevPaymentDate));
		}

		Long period = loanRec.getPeriod();
		if (period != null)
		{
			loan.setTermDuration(new DateSpan(period));
		}

		Date nextPayDate = loanRec.getNextPayDate();
		if (nextPayDate != null)
		{
			Calendar nextPaymentDate = DateHelper.toCalendar(nextPayDate);
			if (nextPaymentDate.after(new GregorianCalendar()))
			{
				loan.setNextPaymentDate(nextPaymentDate);
			}
			else
			{
				Date regNextPayDate = loanRec.getRegNextPayDate();
				if (regNextPayDate != null)
				{
					loan.setNextPaymentDate(DateHelper.toCalendar(regNextPayDate));
				}
			}
		}

		BigDecimal nextPaymentSum = loanRec.getNextPaySum();
		if (nextPaymentSum != null)
		{
			loan.setNextPaymentAmount(safeCreateMoney(nextPaymentSum, currency));
		}

		CustRec_Type clientRec = loanRec.getCustRec(); //� �������� ������ �������� ������ ���������� � ��������, ��� ��������� ���� ��� ���� ���������� - ���������
		if (clientRec != null)
		{
			ClientImpl borrower = fillClient(clientRec.getCustInfo());
			borrower.setId(clientRec.getCustId());
			loan.setBorrower(borrower);
		}

		long personRole = loanRec.getCustRec().getCustInfo().getPersonInfo().getPersonRole();
		if (personRole==1)
		{
			loan.setPersonRole(PersonLoanRole.borrower);
		}
		else if (personRole==2)
		{
			loan.setPersonRole(PersonLoanRole.guarantor);
		}

		return loan;
	}

	/**
	 * ���������� ������ ���������� � ������� �� ������
	 * @param ifxRq ������
	 * @param loginId - id �������
	 * @param loanInfoRs - ������ � �������
	 * @return loan
	 */
	public Loan getLoan(IFXRq_Type ifxRq, Long loginId, LoanInfoRs_Type loanInfoRs) throws GateException, GateLogicException
	{
		LoanRec_Type loanRec = loanInfoRs.getLoanRec();
		if (loanRec == null)
			return null;
		Status_Type statusType = loanRec.getStatus();
		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, LoanRec_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, LoanRec_Type.class, ifxRq);
		}

		LoanInfo_Type loanInfo = loanRec.getLoanInfo();
		if (loanInfo == null)
			throw new GateException("�� ������ ���������� � �������.");
		BankAcctInfo_Type bankAcctInfo = loanRec.getBankAccInfo();
		BankInfo_Type bankInfo = loanRec.getBankInfo();

		LoanImpl loan = new LoanImpl();

		loan.setId(EntityIdHelper.createLoanCompositeId(loanRec, loginId));

		loan.setAccountNumber(loanInfo.getLoanAcctId().getAcctId());
		loan.setAgreementNumber(loanInfo.getAgreemtNum());
		loan.setAnnuity(bankAcctInfo.getIsAnn());
		loan.setDescription(loanInfo.getLoanType());
		loan.setOffice(getOffice(bankInfo));

		Currency currency = getCurrencyByString(loanInfo.getAcctCur());
		loan.setLoanAmount(safeCreateMoney(loanInfo.getCurAmt(), currency));

		BigDecimal prevPaymentSum = loanInfo.getPrevPaySum();
		if (prevPaymentSum != null)
			loan.setLastPaymentAmount(safeCreateMoney(prevPaymentSum,currency));

		BigDecimal nextPaymentSum = loanInfo.getNextPaySum();
		if (nextPaymentSum != null)
			loan.setNextPaymentAmount(safeCreateMoney(nextPaymentSum,currency));										

		String prevPaymentDate = loanInfo.getPrevPayDate();
		if (!StringHelper.isEmpty(prevPaymentDate))
			loan.setLastPaymentDate(parseCalendar(prevPaymentDate));

		String nextPaymentDate = loanInfo.getNextPayDate();
		if (StringHelper.isEmpty(nextPaymentDate) || parseCalendar(nextPaymentDate).after(new GregorianCalendar()))
			nextPaymentDate = loanInfo.getRegNextPayDate();
		if (!StringHelper.isEmpty(nextPaymentDate))
			loan.setNextPaymentDate(parseCalendar(nextPaymentDate));

		CustRec_Type clientRec = loanInfo.getCustRec(0); //� �������� ������ �������� ������ ���������� � ��������, ��� ��������� ���� ��� ���� ���������� - ���������
		ClientImpl borrower = fillClient(clientRec.getCustInfo());
		borrower.setId(clientRec.getCustId());
		loan.setBorrower(borrower);

		loan.setRate(loanInfo.getCreditingRate());

		loan.setState(getLoanState(loanInfo.getLoanStatus(), loanInfo.getPaymentStatus()));

 		Long period = loanInfo.getPeriod();
		if (period != null)
			loan.setTermDuration(new DateSpan(period));

		loan.setTermStart(parseCalendar(bankAcctInfo.getStartDt()));
		loan.setTermEnd(parseCalendar(bankAcctInfo.getExpDt()));

		return loan;
	}

	/**
	 * ���������� ������������� �� �������
	 * @param ifxRq ������
	 * @param loanInqRs - �����
	 * @param currentDate - ����
	 * @return ScheduleItem
	 */
	public ScheduleItem getSchedule(IFXRq_Type ifxRq, LoanInqRs_Type loanInqRs, Calendar currentDate, Money amount) throws GateException, GateLogicException
	{
		Status_Type statusType = loanInqRs.getStatus();
		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, LoanInqRs_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, LoanInqRs_Type.class, ifxRq);
		}

		LoanInfo_Type loanInfo = loanInqRs.getLoanRec().getLoanInfo();

		LoanScheduleItemImpl scheduleItem = new LoanScheduleItemImpl();

		//���� � ������� ���������� �����, �� ��� ������, ��� �������� �������� �������
		//�������������, ����� ����� ������� ������ ���� ����� �����, ��� ������� ����������� ��������
		if(amount != null)
			scheduleItem.setTotalPaymentAmount(amount);

		if (loanInfo.getAcctBalFull() != null)
		{
			AcctBallFullCollector collector = new AcctBallFullCollector(loanInfo.getAcctBalFull());
			Currency loanCurrency = getCurrencyByString(collector.getCurrencyStr());
			updateScheduleItemFromFullDebt(scheduleItem, collector,currentDate);

			if (loanInfo.getAcctBalOnDate() != null)
			{
				Map[] maps = getAcctBalOnDateMap(loanCurrency, loanInfo.getAcctBalOnDate());
				if(maps != null)
				{
					//������������� � scheduleItem ����� ������ �� ��������� ����� � ����� ������ �� ���������
					setPrincipalInterestsAmounts(scheduleItem, (Map<DateDebtItemType, DateDebtItem>) maps[1], loanCurrency);
					scheduleItem.setPenaltyDateDebtItemMap((Map<PenaltyDateDebtItemType, DateDebtItem>) maps[0]);
				}
			}
			else
			{
				setPrincipalInterestsAmounts(scheduleItem, null, loanCurrency);
				scheduleItem.setPenaltyDateDebtItemMap(new HashMap<PenaltyDateDebtItemType, DateDebtItem>());
			}
		}

		scheduleItem.setIdSpacing(loanInfo.getIdSpacing());

		return scheduleItem;
	}

	/**
	 * ���������� ������� ������������� �� ������� ��������� ��������� ��������
	 *
	1.	���� ������ 108 ������, �� ���������� ����� ������� ����� �� ���.
		a.	���� � ������ �������� ������ 103(����� ��� �������� �������) � ������� ������, �� � ������������� ����� ����� 0, �.�. ���� � ���� ����� ����, �� ��� ������ ������ ������.
		b.	���� � ������ �������� ������ 106 ������, �� � ������������� ����� ����� 0, �.�. ���� ����� 106 , �� ��� ������ ������ ������.
		c.	���� ������������ � ��� ������ 105 ������, �� ���� ����� �� ���(����������� ������).
		d.	�����(������������������ ������)
			i.	���� ������ ���� �� 101 ��� 102 ������, �� ���� ����� �� ���
	        ii. ���� ������ ��� ������, �� ����� � 101-� ������ ����� 0, �� ����� ���� �� 102-� ������.
			iii.���� ������ ���, �� ����� ������ ����� ������� ����. ���� ��� ���� ������ �������, �� ����� ����� �������.
	2.	���� �� ������ 108 ������.
		a.	���� � ������ �������� ������ 103(����� ��� �������� �������) � ������� ������, �� � ����� ����� 0, �.�. ���� � ���� ����� ����, �� ��� ������ ������ ������.
		b.	���� � ������ �������� ������ 106 ������, �� � ������������� ����� ����� 0, �.�. ���� ����� 106 , �� ��� ������ ������ ������.
		c.	���� ������ 105, �� ����� � ���� ����� �� ���(����������� ������).
		d.	���� ������ 104 ������, �� ����� ����� �� ���(�������� ��� ����� ����� ��������� � �������� ������ �� e � f, �� ��� ����� �� ������� ��������� ������������� ����� ����� 104 ������)
			i.	���� ������ ���� �� 101 ��� 102 ������ �� ���� ����� �� ���
	        ii. ���� ������ ��� ������, �� ����� � 101-� ������ ����� 0, �� ����� ���� �� 102-� ������.
			iii.���� ������ ���, �� ����� ������ ����� ������� ����. ���� ��� ���� ������ �������, �� ����� ����� �������.
		e.	���� ������ ���� �� 101 ��� 102 ������, �� �� ������ 104, �� ���� � ����� ����� �� ���
		f.	���� ������ ��� � 101 � 102, �� �� ������ 104, �� ����� ������ ����� ������� ����. ���� ��� ���� ������ �������, �� ����� ����� �������. ����� ����� �� ��� �� ������, ��� � ����.
	 *
	 * @param scheduleItem
	 *@param collector - ������ �������������� �� �������
	 * @param currentDate - ������� ����   @return
	 */
	private LoanScheduleItemImpl updateScheduleItemFromFullDebt(LoanScheduleItemImpl scheduleItem, AcctBallFullCollector collector, Calendar currentDate)
	{
		if(collector.getRecommendedAmount() != null)
		{
			scheduleItem.setTotalPaymentAmount(createMoney(collector.getRecommendedAmount().getCurAmt(),collector.getRecommendedAmount().getAcctCur()));
			if(collector.getAmountForClose() != null)
				scheduleItem.setEarlyPaymentAmount(createMoney(collector.getAmountForClose().getCurAmt(), collector.getAmountForClose().getAcctCur()));
			//���� ����� ��� �������� ������� ����� 0, �� � ����� �������� ������� ����� 0. ������� ��� ������ ������.
			if(scheduleItem.getEarlyPaymentAmount() != null && scheduleItem.getEarlyPaymentAmount().getDecimal().compareTo(BigDecimal.ZERO) == 0)
			{
				scheduleItem.setTotalPaymentAmount(scheduleItem.getEarlyPaymentAmount());
				return scheduleItem;
			}
			if(collector.getAnnuitetAmount() != null)
				scheduleItem.setDate(parseCalendar(collector.getAnnuitetAmount().getEffDt()));
			if(collector.getFirstDateAmount() != null)
				scheduleItem.setDate(parseCalendar(collector.getFirstDateAmount().getEffDt()));
			if(collector.getSecondDateAmount() != null)
			{
				Calendar paymentDate = parseCalendar(collector.getSecondDateAmount().getEffDt());
				if(scheduleItem.getDate() == null ||
						/*���� ������� ���� ������� ����� ������� ����, � ����� ���� � ������ ������*/
						scheduleItem.getDate().after(currentDate) && scheduleItem.getDate().after(paymentDate)
						/*���� ������� ���� ������� ������ ������� ����, � ������ ���� � ������ ������*/
						|| scheduleItem.getDate().before(currentDate) && scheduleItem.getDate().before(paymentDate)
						/*���� ������ 101-� ������ � 0 ������*/
						|| collector.getFirstDateAmount() != null && collector.getFirstDateAmount().getCurAmt().compareTo(BigDecimal.ZERO) == 0)
				scheduleItem.setDate(paymentDate);
			}
		}
		else
		{
			if(collector.getAmountForClose() != null)
				scheduleItem.setEarlyPaymentAmount(createMoney(collector.getAmountForClose().getCurAmt(), collector.getAmountForClose().getAcctCur()));
			//���� ����� ��� �������� ������� ����� 0, �� � ����� �������� ������� ����� 0. ������� ��� ������ ������.
			if(scheduleItem.getEarlyPaymentAmount() != null && scheduleItem.getEarlyPaymentAmount().getDecimal().compareTo(BigDecimal.ZERO) == 0)
			{
				scheduleItem.setTotalPaymentAmount(scheduleItem.getEarlyPaymentAmount());
				return scheduleItem;
			}
			if(collector.getAnnuitetAmount() != null)
			{
				scheduleItem.setDate(parseCalendar(collector.getAnnuitetAmount().getEffDt()));
				scheduleItem.setTotalPaymentAmount(createMoney(collector.getAnnuitetAmount().getCurAmt(), collector.getAnnuitetAmount().getAcctCur()));
				return scheduleItem;
			}
			if(collector.getFirstDateAmount() != null)
			{
				scheduleItem.setDate(parseCalendar(collector.getFirstDateAmount().getEffDt()));
				scheduleItem.setTotalPaymentAmount(createMoney(collector.getFirstDateAmount().getCurAmt(), collector.getFirstDateAmount().getAcctCur()));
			}
			if(collector.getSecondDateAmount() != null)
			{
				Calendar paymentDate = parseCalendar(collector.getSecondDateAmount().getEffDt());
				if(scheduleItem.getDate() == null ||
						/*���� ������� ���� ������� ����� ������� ����, � ����� ���� � ������ ������*/
						scheduleItem.getDate().after(currentDate) && scheduleItem.getDate().after(paymentDate)
						/*���� ������� ���� ������� ������ ������� ����, � ������ ���� � ������ ������*/
						|| scheduleItem.getDate().before(currentDate) && scheduleItem.getDate().before(paymentDate)
						/*���� ������ 101-� ������ � 0 ������*/
						|| collector.getFirstDateAmount() != null && collector.getFirstDateAmount().getCurAmt().compareTo(BigDecimal.ZERO) == 0)
				{
					scheduleItem.setDate(paymentDate);
					scheduleItem.setTotalPaymentAmount(createMoney(collector.getSecondDateAmount().getCurAmt(), collector.getSecondDateAmount().getAcctCur()));
				}
			}
			if(collector.getOfferedAmount() != null && collector.getOfferedAmount().getCurAmt().compareTo(BigDecimal.ZERO) != 0)
				scheduleItem.setTotalPaymentAmount(createMoney(collector.getOfferedAmount().getCurAmt(), collector.getOfferedAmount().getAcctCur()));
		}

		if(scheduleItem.getTotalPaymentAmount() == null)
			scheduleItem.setTotalPaymentAmount(createMoney(BigDecimal.ZERO,collector.getCurrencyStr()));

		return scheduleItem;
	}

	/**
	 * ���������� ������ �������������� �� ���� �� ������
	 * @param acctBalOnDate - ������ � ��������������
	 * @return ������ ��������������
	 */
	private Map[] getAcctBalOnDateMap(Currency loanCurrency, AcctBal_Type... acctBalOnDate) throws GateException, GateLogicException
	{
		Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap = new HashMap<PenaltyDateDebtItemType, DateDebtItem>();
		Map<DateDebtItemType, DateDebtItem> dateDebtItemMap = new HashMap<DateDebtItemType, DateDebtItem>();

		if (acctBalOnDate == null && loanCurrency == null)//���� ��� ��������� null, ������ �� ���� �� ����� ������ �������������.
			return null;

		if (acctBalOnDate != null)
		{
			for (AcctBal_Type bal : acctBalOnDate)
			{
				DateDebtItemImpl debt = new DateDebtItemImpl();
				debt.setAmount(createMoney(bal.getCurAmt(), bal.getAcctCur()));
				debt.setName(bal.getBalName());
				debt.setPriority(bal.getPriority());
				debt.setAccountCount(bal.getAcctCount());
				if (bal.getEffDt() != null)
					debt.setAnnuityDate(parseCalendar(bal.getEffDt()));

				Enum debtType = DateDebtItemTypesHelper.getItemType(bal.getBalType());
				if (debtType instanceof PenaltyDateDebtItemType)
				{
					debt.setCode((PenaltyDateDebtItemType) debtType);
					penaltyDateDebtItemMap.put((PenaltyDateDebtItemType)debtType, debt);
				}
				else
				{
					dateDebtItemMap.put((DateDebtItemType)debtType, debt);
				}
			}
		}

		return new Map[]{penaltyDateDebtItemMap, dateDebtItemMap};
	}

	/**
	 * ���������� ������ �������������� �� ���� �� ������
	 * @param acctBalOnDate - ������ � ��������������
	 * @return ������ ��������������
	 */
	private Map[] getAcctBalOnDateMap(Currency loanCurrency, AcctBal... acctBalOnDate) throws GateException, GateLogicException
	{
		Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap = new HashMap<PenaltyDateDebtItemType, DateDebtItem>();
		Map<DateDebtItemType, DateDebtItem> dateDebtItemMap = new HashMap<DateDebtItemType, DateDebtItem>();

		if (acctBalOnDate == null && loanCurrency == null)//���� ��� ��������� null, ������ �� ���� �� ����� ������ �������������.
			return null;

		if (acctBalOnDate != null)
		{
			for (AcctBal bal : acctBalOnDate)
			{
				DateDebtItemImpl debt = new DateDebtItemImpl();
				debt.setAmount(createMoney(bal.getCurAmt(), bal.getAcctCur()));
				debt.setName(bal.getBalName());
				debt.setPriority(bal.getPriority());
				debt.setAccountCount(bal.getAcctCount());
				if (bal.getEffDt() != null)
					debt.setAnnuityDate(parseCalendar(bal.getEffDt()));

				Enum debtType = DateDebtItemTypesHelper.getItemType(bal.getBalType());
				if (debtType instanceof PenaltyDateDebtItemType)
				{
					debt.setCode((PenaltyDateDebtItemType) debtType);
					penaltyDateDebtItemMap.put((PenaltyDateDebtItemType)debtType, debt);
				}
				else
				{
					dateDebtItemMap.put((DateDebtItemType)debtType, debt);
				}
			}
		}

		return new Map[]{penaltyDateDebtItemMap, dateDebtItemMap};
	}

	/**
	 * �������� �� ������ �������������� ����� ������ �� ��������� ����� �
	 * ����� ������ �� ��������� � ��������� �� � scheduleItem, ������ �� ������
	 * @param scheduleItem - ������ �������
	 * @param acctBalOnDateMap - ������ ��������������
	 */
	private void setPrincipalInterestsAmounts(LoanScheduleItemImpl scheduleItem, Map<DateDebtItemType, DateDebtItem> acctBalOnDateMap, Currency loanCurrency)
	{
		if (acctBalOnDateMap == null)
		{
			scheduleItem.setPrincipalAmount(new Money(BigDecimal.ZERO, loanCurrency));
			scheduleItem.setInterestsAmount(new Money(BigDecimal.ZERO, loanCurrency));
			scheduleItem.setOverpayment(new Money(BigDecimal.ZERO, loanCurrency));
			return;
		}

		//PrincipalAmount
		Money principalAmount = getPrincipalAmount(acctBalOnDateMap.get(DateDebtItemType.firstDateDebtAmount),
												   acctBalOnDateMap.get(DateDebtItemType.secondDateDebtAmount));

		scheduleItem.setPrincipalAmount(principalAmount != null ? principalAmount : new Money(BigDecimal.ZERO, loanCurrency));

		//InterestsAmount
		DateDebtItem interestsAmountItem = acctBalOnDateMap.get(DateDebtItemType.percentsAmount);
		scheduleItem.setInterestsAmount(interestsAmountItem != null ? interestsAmountItem.getAmount() : new Money(BigDecimal.ZERO, loanCurrency));

		if (acctBalOnDateMap.get(DateDebtItemType.overpayment) != null)
			scheduleItem.setOverpayment(acctBalOnDateMap.get(DateDebtItemType.overpayment).getAmount());
	}

	/**
	 * ���������� �������� ���� �� ������� �� ������� ���� ��������� ��������� ��������
	 *
	 *	1. ���� ������ ���� �� ������ 4 ��� -4, �� ������������� ����� �� ��������� ������.
	 *	2. ���� ������ ��� ������, ��:
	 *		a.	���� ���� �� ����� ���� ������ �� ����������, �� ����� ������������� �� -4 ������;
	 *		b.	���� ��� ���� ����������, ��:
	 *			i.	���� ����� ���� ���� ����� �������, �� ����� ������ ����� ������� ����;
	 *			ii. ���� ��� ���� ������ �������, �� ����� ����� �������.
	 * @param firstDateDebtAmount  - ����� ������������� �� ��������� ����� �� ����� ����_1.
	 * @param secondDateDebtAmount - ����� ������������� �� ��������� ����� �� ����� ����_2.
	 * @return principalAmount - �������� ����
	 */
	private Money getPrincipalAmount(DateDebtItem firstDateDebtAmount, DateDebtItem secondDateDebtAmount)
	{
		Money principalAmount = null;
		Calendar firstDateDebtAmountDate = null;
		Calendar secondDateDebtAmountDate = null;
		//����������������� ������������� �������� �� �. 1-2a.
		//����, �������������� ���� ��� �. 2b.
		if (firstDateDebtAmount != null)
		{
			principalAmount = firstDateDebtAmount.getAmount();
			firstDateDebtAmountDate = firstDateDebtAmount.getAnnuityDate();
		}
		if (secondDateDebtAmount != null)
		{
			principalAmount = secondDateDebtAmount.getAmount();
			secondDateDebtAmountDate = secondDateDebtAmount.getAnnuityDate();
		}
		// �. 2�
		if ((firstDateDebtAmountDate == null) || (secondDateDebtAmountDate == null))
		{
			return principalAmount;
		}
		// �. 2b
		Calendar currentDate = Calendar.getInstance();
		// �������� ���� ������ 4 � ������� ���� � �������������
		long delta1 = firstDateDebtAmountDate.getTimeInMillis() - currentDate.getTimeInMillis();
		// �������� ���� ������ -4 � ������� ���� � �������������
		long delta2 = secondDateDebtAmountDate.getTimeInMillis() - currentDate.getTimeInMillis();
		if (delta1 == 0 || 
			Long.signum(delta1) == Long.signum(delta2) && Math.abs(delta1) < Math.abs(delta2) ||
			Long.signum(delta1) != Long.signum(delta2) && delta2 < 0)
		{
			principalAmount = firstDateDebtAmount.getAmount();
		}
		return principalAmount;
	}
	
	/**
	 * ���������� ������ �������� ��� ������� �� ������
	 * @param ifxRq ������
	 * @param loanPaymentRs - �����
	 * @param loan - ������
	 * @return ScheduleAbstract
	 */
	public ScheduleAbstract getScheduleAbstract(IFXRq_Type ifxRq, LoanPaymentRs_Type loanPaymentRs, Loan loan) throws GateException, GateLogicException
	{
		Status_Type statusType = loanPaymentRs.getStatus();
		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, LoanPaymentRs_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, LoanPaymentRs_Type.class, ifxRq);
		}

		ScheduleAbstractImpl scheduleAbstract = new ScheduleAbstractImpl();
		Currency loanCurrency = loan.getLoanAmount().getCurrency();

		if(loanPaymentRs.getRemainAmount() == null && loanPaymentRs.getFineAmount() == null)
		{
			scheduleAbstract.setIsAvailable(false);
			return scheduleAbstract;
		}

		scheduleAbstract.setIsAvailable(true);

		if (loanPaymentRs.getRemainAmount() != null)
			scheduleAbstract.setRemainAmount(new Money(loanPaymentRs.getRemainAmount(), loanCurrency));
		if (loanPaymentRs.getFineAmount() != null)
			scheduleAbstract.setPenaltyAmount(new Money(loanPaymentRs.getFineAmount(), loanCurrency));
		if (loanPaymentRs.getDoneAmount() != null)
			scheduleAbstract.setDoneAmount(new Money(loanPaymentRs.getDoneAmount(), loanCurrency));
			
		scheduleAbstract.setPaymentCount(loanPaymentRs.getMaxSize());
		List<ScheduleItem> schedules = getScheduleItems(loanCurrency, loanPaymentRs.getLoanPaymentRec());
		scheduleAbstract.setSchedules(schedules);

		return scheduleAbstract;
	}

	/**
	 * ���������� ������ �������� ��� ������� �� ������
	 * @param loanPaymentRs - �����
	 * @param loan - ������
	 * @return ScheduleAbstract
	 */
	public ScheduleAbstract getScheduleAbstract(LoanPaymentRs loanPaymentRs, Loan loan) throws GateException, GateLogicException
	{
        StatusType statusType = loanPaymentRs.getStatus();
		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
            if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
                ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, LoanPaymentRs_Type.class, loanPaymentRs.getSystemId());
            else
                ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, LoanPaymentRs_Type.class, loanPaymentRs.getSystemId());
		}

		ScheduleAbstractImpl scheduleAbstract = new ScheduleAbstractImpl();
		Currency loanCurrency = loan.getLoanAmount().getCurrency();

		if(loanPaymentRs.getRemainAmount() == null && loanPaymentRs.getFineAmount() == null)
		{
			scheduleAbstract.setIsAvailable(false);
			return scheduleAbstract;
		}

		scheduleAbstract.setIsAvailable(true);

		if (loanPaymentRs.getRemainAmount() != null)
			scheduleAbstract.setRemainAmount(new Money(loanPaymentRs.getRemainAmount(), loanCurrency));
		if (loanPaymentRs.getFineAmount() != null)
			scheduleAbstract.setPenaltyAmount(new Money(loanPaymentRs.getFineAmount(), loanCurrency));
		if (loanPaymentRs.getDoneAmount() != null)
			scheduleAbstract.setDoneAmount(new Money(loanPaymentRs.getDoneAmount(), loanCurrency));

		scheduleAbstract.setPaymentCount(loanPaymentRs.getMaxSize());
		List<LoanYearPayment> schedules = getScheduleYearItems(loanCurrency, loanPaymentRs.getLoanPaymentRecs());
		scheduleAbstract.setYearPayments(schedules);

		return scheduleAbstract;
	}


	/**
	 * ���������� ������ ��������
	 * @param loanPaymentRec - ������ � ������� ��������
	 * @return ������ ��������
	 */
	private List<ScheduleItem> getScheduleItems(Currency loanCurrency, LoanPaymentRec_Type... loanPaymentRec) throws GateException, GateLogicException
	{
		List<ScheduleItem> result = new ArrayList<ScheduleItem>();
		if(loanPaymentRec==null)
			return result;
		for (LoanPaymentRec_Type rec : loanPaymentRec)
		{
			LoanScheduleItemImpl scheduleItem = new LoanScheduleItemImpl();
			scheduleItem.setPaymentNumber(rec.getLoanPaymentNumber());
			scheduleItem.setDate(parseCalendar(rec.getLoanPaymentDate()));
			scheduleItem.setPaymentState(LoanPaymentStateWrapper.getLoanPaymentState(rec.getLoanPaymentStatus()));

			Map[] maps = getAcctBalOnDateMap(null,rec.getAcctBalOnDate());
			if (maps!= null)
			{
				Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDebs = (Map<PenaltyDateDebtItemType, DateDebtItem>) maps[0];
				//������������� � scheduleItem ����� ������ �� ��������� ����� � ����� ������ �� ���������
				setPrincipalInterestsAmounts(scheduleItem, (Map<DateDebtItemType, DateDebtItem>) maps[1], loanCurrency);
				//������� ������ ����� �������
				setTotalPaymentAmount(scheduleItem, loanCurrency, penaltyDebs);

				scheduleItem.setPenaltyDateDebtItemMap(penaltyDebs);
			}
			else
			{
				setPrincipalInterestsAmounts(scheduleItem, null, loanCurrency);
				scheduleItem.setPenaltyDateDebtItemMap(new HashMap<PenaltyDateDebtItemType, DateDebtItem>());
			}

			result.add(scheduleItem);
		}

		return result;
	}

	/**
	 * ���������� ������ ��������
	 * @param loanPaymentRec - ������ � ������� ��������
	 * @return ������ ��������
	 */
	private List<LoanYearPayment> getScheduleYearItems(Currency loanCurrency, List<LoanPaymentRec> loanPaymentRec) throws GateException, GateLogicException
	{
        if(loanPaymentRec==null)
            return null;

        Map<Integer, LoanYearPayment> map = new HashMap<Integer, LoanYearPayment>();

        int yearNumber;

        Collections.sort(loanPaymentRec, new Comparator<LoanPaymentRec>()
        {
            public int compare(LoanPaymentRec o1, LoanPaymentRec o2)
            {
                return o1.getLoanPaymentDate().compareTo(o2.getLoanPaymentDate());
            }
        });

        for (LoanPaymentRec rec: loanPaymentRec) {
            yearNumber = parseCalendar(rec.getLoanPaymentDate()).get(Calendar.YEAR);
            LoanYearPayment yearPayment = map.get(Integer.valueOf(yearNumber));
            if (yearPayment == null) {
                yearPayment = new LoanYearPayment(yearNumber);
                map.put(yearNumber, yearPayment);
            }

            LoanScheduleItemImpl scheduleItem = new LoanScheduleItemImpl();
            scheduleItem.setPaymentNumber(rec.getLoanPaymentNumber());
            scheduleItem.setDate(parseCalendar(rec.getLoanPaymentDate()));
            scheduleItem.setPaymentState(LoanPaymentStateWrapper.getLoanPaymentState(rec.getLoanPaymentStatus()));
            scheduleItem.setRemainDebt(new Money(rec.getRemainDebt(), loanCurrency));

            scheduleItem.setPrincipalAmount(new Money(BigDecimal.ZERO, loanCurrency));
            scheduleItem.setInterestsAmount(new Money(BigDecimal.ZERO, loanCurrency));
            scheduleItem.setTotalPaymentAmount(new Money(BigDecimal.ZERO, loanCurrency));

            if (rec.getAcctBalOnDates() != null) {
                for (AcctBal acctBal : rec.getAcctBalOnDates()){
                    if (BAKS_EXT_CRED.equals(acctBal.getBalType())) {
                        scheduleItem.setPrincipalAmount(scheduleItem.getPrincipalAmount().add(new Money(acctBal.getNextAmt(), loanCurrency)));
                    }
                    if (BAKS_EXT_PRC.equals(acctBal.getBalType()) || BAKS_EXT_OVE.equals(acctBal.getBalType()) || BAKS_EXT_PEN.equals(acctBal.getBalType())) {
                        scheduleItem.setInterestsAmount(scheduleItem.getInterestsAmount().add(new Money(acctBal.getCurAmt(), loanCurrency)));
                    }
                }
            }

            scheduleItem.setTotalPaymentAmount(scheduleItem.getPrincipalAmount().add(scheduleItem.getInterestsAmount()));

            yearPayment.getMonths().add(new LoanMonthPayment(yearNumber, parseCalendar(rec.getLoanPaymentDate()).get(Calendar.MONTH), scheduleItem));

            map.put(yearNumber, yearPayment);
        }

        List<LoanYearPayment> year = new ArrayList<LoanYearPayment>();

        for(Map.Entry<Integer, LoanYearPayment> y : map.entrySet()) {
            year.add(y.getValue());
        }

		Collections.sort(year, new Comparator<LoanYearPayment>()
        {
            public int compare(LoanYearPayment o1, LoanYearPayment o2)
            {
                return Integer.valueOf(o1.getYear()).compareTo(Integer.valueOf(o2.getYear()));
            }
        });

        return  year;
	}

	/**
	 * ������������� ����� ����� ������� ��� scheduleItem
	 * @param scheduleItem - ������ �������
	 * @param acctBalOnDateMap - ������ ��������������
	 */
	private void setTotalPaymentAmount(LoanScheduleItemImpl scheduleItem, Currency loanCurrency, Map<PenaltyDateDebtItemType, DateDebtItem> acctBalOnDateMap) throws GateException
	{
		Money result = new Money(BigDecimal.ZERO, loanCurrency);

		if (scheduleItem.getInterestsAmount()!= null)
			result = result.add(scheduleItem.getInterestsAmount());

		if (scheduleItem.getPrincipalAmount() != null)
			result = result.add(scheduleItem.getPrincipalAmount());

		if (MapUtils.isNotEmpty(acctBalOnDateMap))
		{
			Iterator<DateDebtItem> itr = acctBalOnDateMap.values().iterator();
			for (;itr.hasNext();)
			{
				result = result.add(itr.next().getAmount());
			}
		}
		scheduleItem.setTotalPaymentAmount(result);
	}
	/**
	 * ���������� ������ �������
	 * @param loanStatus   1 - ������, 2 - ������
	 * @param paymentStatus 2- ��������� 1 � ���
	 * @return LoanState 
	 */
	private LoanState getLoanState(String loanStatus, PaymentStatus_Type paymentStatus)
	{
		if (paymentStatus != null && "2".equals(paymentStatus.getValue()))
			return LoanState.overdue;
		else if (loanStatus == null)
			return LoanState.undefined;
		else if ("1".equals(loanStatus))
			return LoanState.open;
		else
			return LoanState.closed;
	}

    public void getLoanFromPrivateLoanDetailsRs(GetPrivateLoanDetailsRs response, LoanImpl loan)
    {
        PrivateLoanDetailsType rec = response.getLoanRec();

        if (rec == null)
            return;

        Currency currency = loan.getLoanAmount().getCurrency();
        loan.setCreditingRate(rec.getCreditingRate());
        loan.setLoanType(rec.getLoanType());
        loan.setFullRepaymentAmount(new Money(rec.getFullRepaymentAmount(), currency));
	    loan.setBAKS(!rec.getBAKS().equals(BigInteger.ZERO));
	    loan.setAutoGranted(rec.getAutoGrantion().equals(new BigInteger("2")));
	    loan.setApplicationNumber(rec.getApplicationNumberCA());

	    List<EarlyRepayment> earlyRepayments = new ArrayList<EarlyRepayment>();
	    if (rec.getEarlyRepayments() != null)
	    {
		    for (EarlyRepaymentType earlyRepaymentType : rec.getEarlyRepayments())
		    {
			    EarlyRepaymentImpl earlyRepayment = new EarlyRepaymentImpl();

			    earlyRepayment.setAmount(earlyRepaymentType.getAmount());
			    try
			    {
				    earlyRepayment.setDate(DateHelper.parseXmlDateFormat(earlyRepaymentType.getDate()));
			    }
			    catch (ParseException e)
			    {
				    log.error("������ ��� ���������� ���� ���� ��� ���������� ��������� �������", e);
			    }
			    earlyRepayment.setStatus(earlyRepaymentType.getStatus());
	            earlyRepayment.setAccount(earlyRepaymentType.getAccount());
	            earlyRepayment.setRepaymentChannel(earlyRepaymentType.getRepaymentChannel());
	            earlyRepayment.setTerminationRequestId(Long.valueOf(earlyRepaymentType.getTerminationRequestId()));

			    earlyRepayments.add(earlyRepayment);
		    }
	    }
	    loan.setEarlyRepayments(earlyRepayments);

        Money mainDebt = new Money(new BigDecimal(0), currency);
        Money interestPayments = new Money(new BigDecimal(0), currency);
        Money overdueMainDebts = new Money(new BigDecimal(0), currency);
        Money overdueInterestDebts = new Money(new BigDecimal(0), currency);
        Money mainDebtAmount = new Money(new BigDecimal(0), currency);
        Money interestPaymentsAmount = new Money(new BigDecimal(0), currency);

        for (AcctBal acctBal : rec.getAcctBalOnDates()) {
            if(acctBal.getBalType().equals(BAKS_EXT_PRC)) {
                interestPayments = interestPayments.add(createMoney(acctBal.getNextAmt(), acctBal.getAcctCur()));
                interestPaymentsAmount = interestPaymentsAmount.add(createMoney(acctBal.getCurAmt(), acctBal.getAcctCur()));
            }
            else if(acctBal.getBalType().equals(BAKS_EXT_PEN)){
                interestPayments = interestPayments.add(createMoney(acctBal.getNextAmt(), acctBal.getAcctCur()));
                interestPaymentsAmount = interestPaymentsAmount.add(createMoney(acctBal.getCurAmt(), acctBal.getAcctCur()));
            }
            else if(acctBal.getBalType().equals(BAKS_EXT_CRED)){
                overdueMainDebts = overdueMainDebts.add(createMoney(acctBal.getNextAmt(), acctBal.getAcctCur()));
                mainDebtAmount = mainDebtAmount.add(createMoney(acctBal.getCurAmt(), acctBal.getAcctCur()));
            }
            else if(acctBal.getBalType().equals(BAKS_EXT_OVE)){
                interestPaymentsAmount = interestPaymentsAmount.add(createMoney(acctBal.getCurAmt(), acctBal.getAcctCur()));
                overdueInterestDebts = overdueInterestDebts.add(createMoney(acctBal.getNextAmt(), acctBal.getAcctCur()));
            }
            else if(acctBal.getBalType().equals(CREDIT)) {
                mainDebt = mainDebt.add(createMoney(acctBal.getNextAmt(), acctBal.getAcctCur()));
                mainDebtAmount = mainDebtAmount.add(createMoney(acctBal.getCurAmt(), acctBal.getAcctCur()));
            }
        }

        loan.setMainDebt(mainDebt);
        loan.setInterestPayments(interestPayments);
        loan.setOverdueMainDebts(overdueMainDebts);
        loan.setOverdueInterestDebts(overdueInterestDebts);
        loan.setMainDebtAmount(mainDebtAmount);
        loan.setInterestPaymentsAmount(interestPaymentsAmount);

        loan.setAgencyAddress(rec.getAgencyAddress());

        List<String> borrower = new LinkedList<String>();
        List<String> coBorrower = new LinkedList<String>();
        List<String> guarantor = new LinkedList<String>();

        for(CustRec custRec : rec.getCustRecs()) {
            if (custRec.getCustInfo().getPersonInfo().getPersonName() == null)
                continue;
            StringBuilder name = new StringBuilder();
            name.append(custRec.getCustInfo().getPersonInfo().getPersonName().getFirstName());
            name.append(" ");
            name.append(custRec.getCustInfo().getPersonInfo().getPersonName().getMiddleName());
            name.append(" ");
            name.append(custRec.getCustInfo().getPersonInfo().getPersonName().getLastName().substring(0, 1));
            name.append(".");
            if (custRec.getCustInfo().getPersonInfo().getPersonRole().equals(2L)) {
//                borrower.add(name.toString());
                coBorrower.add(name.toString());
            }
            if (custRec.getCustInfo().getPersonInfo().getPersonRole().equals(3L)) {
                guarantor.add(name.toString());
            }
        }

        loan.setNewBorrower(borrower);
        loan.setNewCoBorrower(coBorrower);
        loan.setGuarantor(guarantor);

        loan.setOverdue(rec.getOverdue() != null && rec.getOverdue().equals(1L));

        loan.setPrincipalBalance(rec.getPrincipalBalance());

        List<String> accounts = new LinkedList<String>();
        if (rec.getAccounts() != null && rec.getAccounts().getAccount() != null) {
            for (PrivateLoanDetailsType.Accounts.Account.Element elem : rec.getAccounts().getAccount().getElements()){
                accounts.add("account:" + elem.getId());
            }
        }
        if (rec.getAccounts() != null && rec.getAccounts().getCard() != null) {
            for (PrivateLoanDetailsType.Accounts.Card.Element elem : rec.getAccounts().getCard().getElements()){
                accounts.add("card:" + elem.getId());
            }
        }

        loan.setAccount(accounts);

        loan.setLoanStatus(rec.getLoanStatus());
	}
}
