package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.gate.loans.GuaranteeContract;
import com.rssl.phizic.gate.loans.ContractType;
import com.rssl.phizic.gate.loans.ContractState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.common.types.Money;
import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 26.08.2008
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

	public GuaranteeContractImpl(String number, ContractType type, ContractState state, Calendar openDate,
	                             Calendar closeDate, Money summ, Client guarantee)
	{
		this.guarantee = guarantee;
		this.summ = summ;
		this.closeDate = closeDate;
		this.openDate = openDate;
		this.state = state;
		this.type = type;
		this.number = number;
	}

	/**
	 * Номер договора поручительства
	 *
	 * @return номер
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * Тип договора
	 *
	 * @return тип
	 */
	public ContractType getType()
	{
		return type;
	}

	/**
	 * Статус договора
	 *
	 * @return статус
	 */
	public ContractState getState()
	{
		return state;
	}

	/**
	 * Дата начала договора
	 *
	 * @return дата
	 */
	public Calendar getOpenDate()
	{
		return openDate;
	}

	/**
	 * Дата конца договора
	 *
	 * @return дата
	 */
	public Calendar getCloseDate()
	{
		return closeDate;
	}

	/**
	 * Сумма договора
	 *
	 * @return сумма
	 */
	public Money getSumm()
	{
		return summ;
	}

	/**
	 * Поручитель
	 *
	 * @return клиент
	 */
	public Client getGuarantee()
	{
		return guarantee;
	}
}
