package com.rssl.phizicgate.mdm.business.products;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по продуктам клиентов
 */

public class ClientProductsSynchronizeInfo
{
	private List<Account> accountsSaveList = new ArrayList<Account>();
	private List<Account> accountsDeleteList = new ArrayList<Account>();
	private List<Card> cardsSaveList = new ArrayList<Card>();
	private List<Card> cardsDeleteList = new ArrayList<Card>();
	private List<DepoAccount> depoAccountsSaveList = new ArrayList<DepoAccount>();
	private List<DepoAccount> depoAccountsDeleteList = new ArrayList<DepoAccount>();
	private List<Loan> loansSaveList = new ArrayList<Loan>();
	private List<Loan> loansDeleteList = new ArrayList<Loan>();
	private List<Service> servicesSaveList = new ArrayList<Service>();
	private List<Service> servicesDeleteList = new ArrayList<Service>();

	/**
	 * задать информацию по вкладам/счетам
	 * @param accounts информация по вкладам/счетам
	 */
	void setAccounts(List<Account> accounts)
	{
		accountsDeleteList.addAll(accounts);
	}

	/**
	 * задать информацию по картам
	 * @param cards информация по картам
	 */
	void setCards(List<Card> cards)
	{
		cardsDeleteList.addAll(cards);
	}

	/**
	 * задать информацию по счетам депо
	 * @param depoAccounts информация по счетам депо
	 */
	void setDepoAccounts(List<DepoAccount> depoAccounts)
	{
		depoAccountsDeleteList.addAll(depoAccounts);
	}

	/**
	 * задать информацию по кредитам
	 * @param loans информация по кредитам
	 */
	void setLoans(List<Loan> loans)
	{
		loansDeleteList.addAll(loans);
	}

	/**
	 * задать информацию по сервисам
	 * @param services информация по сервисам
	 */
	void setServices(List<Service> services)
	{
		servicesDeleteList.addAll(services);
	}

	/**
	 * @return информация по вкладам/счетам для сохранения
	 */
	public List<Account> getAccountsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accountsSaveList;
	}

	/**
	 * @return информация по вкладам/счетам для удаления
	 */
	public List<Account> getAccountsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accountsDeleteList;
	}

	/**
	 * @return информация по картам для сохранения
	 */
	public List<Card> getCardsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cardsSaveList;
	}

	/**
	 * @return информация по картам для удаления
	 */
	public List<Card> getCardsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cardsDeleteList;
	}

	/**
	 * @return информация по счетам депо для сохранения
	 */
	public List<DepoAccount> getDepoAccountsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccountsSaveList;
	}

	/**
	 * @return информация по счетам депо для удаления
	 */
	public List<DepoAccount> getDepoAccountsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccountsDeleteList;
	}

	/**
	 * @return информация по кредитам для сохранения
	 */
	public List<Loan> getLoansSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loansSaveList;
	}

	/**
	 * @return информация по кредитам для удаления
	 */
	public List<Loan> getLoansDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loansDeleteList;
	}

	/**
	 * @return информация по сервисам для сохранения
	 */
	public List<Service> getServicesSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return servicesSaveList;
	}

	/**
	 * @return информация по сервисам для удаления
	 */
	public List<Service> getServicesDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return servicesDeleteList;
	}
}
