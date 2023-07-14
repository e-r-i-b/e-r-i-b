package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Danilov
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanImpl implements Loan
{
	public void setId(String id)
	{
		this.id = id;
	}

	public void setState(LoanState state)
	{
		this.state = state;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public void setTermStart(Calendar termStart)
	{
		this.termStart = termStart;
	}

	public void setTermDuration(DateSpan termDuration)
	{
		this.termDuration = termDuration;
	}

	public void setTermEnd(Calendar termEnd)
	{
		this.termEnd = termEnd;
	}

	public void setLoanAmount(Money loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public void setBalanceAmount(Money balanceAmount)
	{
		this.balanceAmount = balanceAmount;
	}

	public void setNextPaymentDate(Calendar nextPaymentDate)
	{
		this.nextPaymentDate = nextPaymentDate;
	}

	public void setNextPaymentAmount(Money nextPaymentAmount)
	{
		this.nextPaymentAmount = nextPaymentAmount;
	}

	public void setLastPaymentDate(Calendar lastPaymentDate)
	{
		this.lastPaymentDate = lastPaymentDate;
	}

	public void setLastPaymentAmount(Money lastPaymentAmount)
	{
		this.lastPaymentAmount = lastPaymentAmount;
	}

	public void setPastDueAmount(Money pastDueAmount)
	{
		this.pastDueAmount = pastDueAmount;
	}

	void setTermDurationMonths(int termDurationMonths)
	{
		this.termDurationMonths = termDurationMonths;
	}

	private String id;
	private LoanState state;
	private String description;
	private String agreementNumber;
	private Calendar termStart;
	private DateSpan termDuration;
	private Calendar termEnd;
	private Money loanAmount;
	private BigDecimal rate;
	private Money balanceAmount;
	private Calendar nextPaymentDate;
	private Money nextPaymentAmount;
	private Calendar lastPaymentDate;
	private Money lastPaymentAmount;
	private Money pastDueAmount;
	private int termDurationMonths;
	private String accountNumber;
	private PersonLoanRole personRole;
	private boolean isAnnuity;

	/**
	 * Внешний ID кредита
	 * Domain: ExternalID
	 *
	 * @return ID
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Текущее состояние
	 *
	 *
	 * @return состояние
	 */
	public LoanState getState()
	{
		return state;
	}

	/**
	 * Описание (Автокредит, Потребительский etc)
	 *
	 * Domain: Text
	 *
	 * @return текст
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Номер договора
	 *
	 * Domain: Text
	 *
	 * @return текст
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * Дата открытия договора
	 *
	 * Domain: Date
	 *
	 * @return дата
	 */
	public Calendar getTermStart()
	{
		return termStart;
	}

	/**
	 * Дата окончания договора
	 *
	 * Domain: Date
	 *
	 * @return дата
	 */
	public Calendar getTermEnd()
	{
		return termEnd;
	}

	/**
	 * Срок кредита
	 *
	 *
	 * @return срок
	 */
	public DateSpan getTermDuration()
	{
		return termDuration;
	}

	/**
	 * Сумма кредита
	 *
	 *
	 * @return сумма
	 */
	public Money getLoanAmount()
	{
		return loanAmount;
	}

	/**
	 * % ставка (% годовых)
	 *
	 * Domain: Percent
	 *
	 * @return
	 */
	public BigDecimal getRate()
	{
		return rate;
	}

	/**
	 * Сумма остатка
	 *
	 *
	 * @return сумма
	 */
	public Money getBalanceAmount()
	{
		return balanceAmount;
	}

	/**
	 * Дата последнего платежа
	 *
	 * Domain: DateTime
	 *
	 * @return дата
	 */
	public Calendar getLastPaymentDate()
	{
		return lastPaymentDate;
	}

	/**
	 * Сумма последнего платежа
	 *
	 *
	 * @return сумма
	 */
	public Money getLastPaymentAmount()
	{
		return lastPaymentAmount;
	}

	/**
    * Дата следующего платежа
    *
    * Domain: DateTime
    *
    * @return дата
    */
	public Calendar getNextPaymentDate()
	{
		return nextPaymentDate;
	}

	/**
    * Сумма следующего платежа
    *
    *
    * @return сумма
    */
	public Money getNextPaymentAmount()
	{
		return nextPaymentAmount;
	}

	/**
	 * Сумма просроченной задолженности
	 *
	 *
	 * @return сумма
	 */
	public Money getPastDueAmount()
	{
		return pastDueAmount;
	}

	int getTermDurationMonths()
	{
		return termDurationMonths;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public PersonLoanRole getPersonRole()
	{
		return personRole;
	}

	public void setPersonRole(PersonLoanRole personRole)
	{
		this.personRole = personRole;
	}

	public boolean getIsAnnuity()
	{
		return isAnnuity;
	}

	public Client getBorrower()
	{
		return null;
	}

	public Office getOffice()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void setIsAnnuity(boolean isAnnuity)
	{
		this.isAnnuity = isAnnuity;
	}
}
