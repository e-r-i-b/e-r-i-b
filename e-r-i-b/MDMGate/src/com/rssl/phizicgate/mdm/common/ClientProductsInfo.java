package com.rssl.phizicgate.mdm.common;

import java.util.List;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по продуктам из мдм
 */

public class ClientProductsInfo
{
	private List<AccountInfo> accounts;
	private List<CardInfo> cards;
	private List<DepoAccountInfo> depoAccounts;
	private List<LoanInfo> loans;
	private List<ServiceInfo> services;

	/**
	 * @return информация по вкладам/счетам
	 */
	public List<AccountInfo> getAccounts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accounts;
	}

	/**
	 * задать информацию по вкладам/счетам
	 * @param accounts информация по вкладам/счетам
	 */
	public void setAccounts(List<AccountInfo> accounts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.accounts = accounts;
	}

	/**
	 * @return информация по картам
	 */
	public List<CardInfo> getCards()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cards;
	}

	/**
	 * задать информацию по картам
	 * @param cards информация по картам
	 */
	public void setCards(List<CardInfo> cards)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cards = cards;
	}

	/**
	 * @return информация по счетам депо
	 */
	public List<DepoAccountInfo> getDepoAccounts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccounts;
	}

	/**
	 * задать информацию по счетам депо
	 * @param depoAccounts информация по счетам депо
	 */
	public void setDepoAccounts(List<DepoAccountInfo> depoAccounts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.depoAccounts = depoAccounts;
	}

	/**
	 * @return информация по кредитам
	 */
	public List<LoanInfo> getLoans()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loans;
	}

	/**
	 * задать информацию по кредитам
	 * @param loans информация по кредитам
	 */
	public void setLoans(List<LoanInfo> loans)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.loans = loans;
	}

	/**
	 * @return информация по сервисам
	 */
	public List<ServiceInfo> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * задать информацию по сервисам
	 * @param services информация по сервисам
	 */
	public void setServices(List<ServiceInfo> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}
}
