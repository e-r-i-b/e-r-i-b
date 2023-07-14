package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � �������
 */
public interface Loan extends Serializable
{
   /**
    * ������� ID �������
    * Domain: ExternalID
    *
    * @return ID
    */
   String getId();
   /**
    * ������� ���������
    *
    *
    * @return ���������
    */
   LoanState getState();
   /**
    * �������� (����������, ��������������� etc)
    *
    * Domain: Text
    *
    * @return �����
    */
   String getDescription();
   /**
    * ����� ��������
    *
    * Domain: Text
    *
    * @return �����
    */
   String getAgreementNumber();
	/**
    * ����� �������� �����
    *
    * Domain: Text
    *
    * @return �����
    */
   String getAccountNumber();
   /**
    * ���� �������� ��������
    *
    * Domain: Date
    *
    * @return ����
    */
   Calendar getTermStart();
   /**
    * ���� ��������� ��������
    *
    * Domain: Date
    *
    * @return ����
    */
   Calendar getTermEnd();
   /**
    * ���� �������
    *
    *
    * @return ����
    */
   DateSpan getTermDuration();
   /**
    * ����� ������� �� ��������
    *
    *
    * @return �����
    */
   Money getLoanAmount();
   /**
    * % ������ (% �������)
    *
    * Domain: Percent
    *
    * @return
    */
   BigDecimal getRate();
   /**
    * ����� ������� ������� �������������
    *
    *
    * @return �����
    */
   Money getBalanceAmount();
   /**
    * ���� ���������� �������
    *
    * Domain: DateTime
    *
    * @return ����
    */
   Calendar getLastPaymentDate();
   /**
    * ����� ���������� �������
    *
    *
    * @return �����
    */
   Money getLastPaymentAmount();
   /**
    * ���� ���������� �������
    *
    * Domain: DateTime
    *
    * @return ����
    */
   Calendar getNextPaymentDate();
    /**
    * ������������� ����� ���������� �������
    *
    *
    * @return �����
    */
	Money getNextPaymentAmount();

   /**
    * ����� ������������ �������������
    *
    *
    * @return �����
    */
   Money getPastDueAmount();

	/**
    * ���� ������� � ��������� ��������
	*
    * @return ���� �������
    */
    PersonLoanRole getPersonRole();

	/**
    * ����������� ������
	*
    * @return true - ����������� ������, false - ������������������ ������
    */
    boolean getIsAnnuity();

	/**
    * ������� �������
	*
    * @return �������
    */
    Client getBorrower();

	/**
    * �������������, � ������� ������ ������
    *
    * @return �������������, � ������� ������ ������ ��� null, ���� ������ �������� ��� ����������
    */
    Office getOffice();

	/**
	 * ����� ������ �� 1� ���������
	 *
	 * @return �����
	 */
	public Money getFirstDelayPenalty();

	/**
	 * ����� ������ �� 2� ���������
	 *
	 * @return �����
	 */
	public Money getSecondDelayPenalty();

	/**
	 * ����� ������ �� 3� ���������
	 *
	 * @return �����
	 */
	public Money getThirdDelayPenalty();

	/**
	 * ������ ����������� ��������
	 *
	 * @return ������ ��������
	 */
	public BigDecimal getCommissionRate();

	/**
	 * ���� ����������� ��������
	 *
	 * @return ���� ��������
	 */
	public CommissionBase getCommissionBase();

	/**
	 * �������� �����������
	 *
	 * @return ��������
	 */
	public Iterator<GuaranteeContract> getGuaranteeContractIterator();

	/**
	 * ��� ��������, ����������� ������� � �������(�������, ���������, ������������, ����������)
	 *
	 * @return ��� ��������
	 */
	public Map<PersonLoanRole, Client> getLoanPersons();

    /**
     * ������������� ����������� ��������
     * <ProdType> �� GFL
     * @return ������������� ����������� ��������
     */
    public String getProdType();

    /**
     * ���������� ������ �� �������
     * <CreditingRate> GetPrivateLoanDetailsRs
     * @return ���������� ������
     */
    public BigDecimal getCreditingRate();

    /**
     * ��� �������
     * <LoanType> GetPrivateLoanDetailsRs
     * @return ��� �������
     */
    public String getLoanType();

    /**
     * �������� ��������
     * <FullRepaymentAmount> GetPrivateLoanDetailsRs
     * @return �������� ��������
     */
    public Money getFullRepaymentAmount();

    /**
     * ���� ���������� �������
     * <PaymentDate> GetPrivateLoanListRs
     * @return ���� ���������� �������
     */
    public Calendar getClosestPaymentDate();

    /**
     * ����� ���������� �������
     * <RecPayment> GetPrivateLoanListRs
     * @return ����� ���������� �������
     */
    public Money getRecPayment();

    /**
     * �������� ����
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = ������
     * @return ����� ��������� �����
     */
    public Money getMainDebt();

    /**
     * ������� �� ���������
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_PRC | BAKS_EXT_PEN
     * @return ������� �� ���������
     */
    public Money getInterestPayments();

    /**
     * ������������ ����������� �� ��������� �����
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_CRED
     * @return ������������ ����������� �� ��������� �����
     */
    public Money getOverdueMainDebts();

    /**
     * ������������ ����������� �� ���������
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_OVE
     * @return ������������ ����������� �� ���������
     */
    public Money getOverdueInterestDebts();

    /**
     * �������� ��� ���������
     * <NextAmt> GetPrivateLoanDetailsRs
     * @return �������� ��� ���������
     */
    public List<String> getAccount();

    /**
     * ����� ��������� �����
     * <AcctBalOnDate><CurAmt> GetPrivateLoanListRs <BalType> = ������ | BAKS_EXT_CRED
     * @return ����� ��������� �����
     */
    public Money getMainDebtAmount();

    /**
     * ����� ������ �� ���������
     * <AcctBalOnDate><CurAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_OVE | BAKS_EXT_PRC | BAKS_EXT_PEN
     * @return ����� ������ �� ���������
     */
    public Money getInterestPaymentsAmount();

    /**
     * ��������� ������������ �������
     * <AgencyAddress> GetPrivateLoanDetailsRs
     * @return ��������� ������������ �������
     */
    public String getAgencyAddress();

    /**
     * �������
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return �������
     */
    public List<String> getNewBorrower();

    /**
     * ����������
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return ����������
     */
    public List<String> getNewCoBorrower();

    /**
     * ����������
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return ����������
     */
    public List<String> getGuarantor();

    /**
     * ������ �������
     * <LoanStatus> GetPrivateLoanDetailsRs
     * @return ������ �������
     */
    public Long getLoanStatus();

    /**
     * ������� ��������� ����� �� �������
     * <PrincipalBalance> GetPrivateLoanDetailsRs
     * @return ������� ��������� ����� �� �������
     */
    public BigDecimal getPrincipalBalance();

    /**
     * ������� ���������
     * <Overdue> GetPrivateLoanDetailsRs
     * @return ������� ���������
     */
    public boolean isOverdue();

	/**
	 * <BAKS> GetPrivateLoanDetailsRs
	 * @return ������� ����
	 */
	public boolean isBAKS();

	/**
	 * <RepaymentChannel> GetPrivateLoanDetailsRs
	 * @return ������������������ ��������� ���������
	 */
	public List<EarlyRepayment> getEarlyRepayments();

	public boolean isAutoGranted();

	public String getApplicationNumber();

}