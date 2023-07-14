package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.gate.loans.GuaranteeContract;
import com.rssl.phizic.gate.loans.ContractType;
import com.rssl.phizic.gate.loans.ContractState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class GuaranteeContractImpl implements GuaranteeContract
{
	private String number;
	private ContractType type;
	private ContractState state;
	private Calendar openDate;
	private Calendar closeDate;
	private Money summ;
	private Client guarantee;

	/**
	 * ����� �������� ��������������
	 *
	 * @return �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ��� ��������
	 *
	 * @return ���
	 */
	public ContractType getType()
	{
		return type;
	}

	/**
	 * ������ ��������
	 *
	 * @return ������
	 */
	public ContractState getState()
	{
		return state;
	}

	/**
	 * ���� ������ ��������
	 *
	 * @return ����
	 */
	public Calendar getOpenDate()
	{
		return openDate;
	}

	/**
	 * ���� ����� ��������
	 *
	 * @return ����
	 */
	public Calendar getCloseDate()
	{
		return closeDate;
	}

	/**
	 * ����� ��������
	 *
	 * @return �����
	 */
	public Money getSumm()
	{
		return summ;
	}

	/**
	 * ����������
	 *
	 * @return ������
	 */
	public Client getGuarantee()
	{
		return guarantee;
	}

	
	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setType(ContractType type)
	{
		this.type = type;
	}

	public void setState(ContractState state)
	{
		this.state = state;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public void setSumm(Money summ)
	{
		this.summ = summ;
	}

	public void setGuarantee(Client guarantee)
	{
		this.guarantee = guarantee;
	}
}
