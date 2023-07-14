package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.gate.loans.LoanInfo;
import com.rssl.phizic.gate.loans.CommissionBase;
import com.rssl.phizic.gate.loans.GuaranteeContract;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanInfoImpl implements LoanInfo
{
	private Money firstDelayPenalty;
	private Money secondDelayPenalty;
	private Money thirdDelayPenalty;
	private BigDecimal commissionRate;
	private CommissionBase commissionBase;
	private Iterator<GuaranteeContract> guaranteeContractIterator;
	private Map<PersonLoanRole, Client> loanPersons;


	/**
	 * ����� ������ �� 1� ���������
	 *
	 * @return �����
	 */
	public Money getFirstDelayPenalty()
	{
		return firstDelayPenalty;
	}

	/**
	 * ����� ������ �� 2� ���������
	 *
	 * @return �����
	 */
	public Money getSecondDelayPenalty()
	{
		return secondDelayPenalty;
	}

	/**
	 * ����� ������ �� 3� ���������
	 *
	 * @return �����
	 */
	public Money getThirdDelayPenalty()
	{
		return thirdDelayPenalty;
	}

	/**
	 * ������ ����������� ��������
	 *
	 * @return ������ ��������
	 */
	public BigDecimal getCommissionRate()
	{
		return commissionRate;
	}

	/**
	 * ���� ����������� ��������
	 *
	 * @return ���� ��������
	 */
	public CommissionBase getCommissionBase()
	{
		return commissionBase;
	}

	/**
	 * �������� �����������
	 *
	 * @return ��������
	 */
	public Iterator<GuaranteeContract> getGuaranteeContractIterator()
	{
		return guaranteeContractIterator;
	}



	public void setFirstDelayPenalty(Money firstDelayPenalty)
	{
		this.firstDelayPenalty = firstDelayPenalty;
	}

	public void setSecondDelayPenalty(Money secondDelayPenalty)
	{
		this.secondDelayPenalty = secondDelayPenalty;
	}

	public void setThirdDelayPenalty(Money thirdDelayPenalty)
	{
		this.thirdDelayPenalty = thirdDelayPenalty;
	}

	public void setCommissionRate(BigDecimal commissionRate)
	{
		this.commissionRate = commissionRate;
	}

	public void setCommissionBase(CommissionBase commissionBase)
	{
		this.commissionBase = commissionBase;
	}

	public void setGuaranteeContractIterator(Iterator<GuaranteeContract> guaranteeContractIterator)
	{
		this.guaranteeContractIterator = guaranteeContractIterator;
	}

	public Map<PersonLoanRole, Client> getLoanPersons()
	{
		return loanPersons;
	}

	public void setLoanPersons(Map<PersonLoanRole, Client> loanPersons)
	{
		this.loanPersons = loanPersons;
	}
}
