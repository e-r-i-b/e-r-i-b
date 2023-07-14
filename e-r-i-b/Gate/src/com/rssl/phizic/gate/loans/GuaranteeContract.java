package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Договор обеспечения.
 */
public interface GuaranteeContract
{
	/**
	 * Номер договора поручительства
	 *
	 * @return номер
	 */
	public String getNumber();

	/**
	 * Тип договора
	 *
	 * @return тип
	 */
	public ContractType getType();

	/**
	 * Статус договора
	 *
	 * @return статус
	 */
	public ContractState getState();

	/**
	 * Дата начала договора
	 *
	 * @return дата
	 */
	public Calendar getOpenDate();

	/**
	 * Дата конца договора
	 *
	 * @return дата
	 */
	public Calendar getCloseDate();

	/**
	 * Сумма договора
	 *
	 * @return сумма
	 */
	public Money getSumm();

	/**
	 * Поручитель
	 *
	 * @return клиент
	 */
	public Client getGuarantee();
}
