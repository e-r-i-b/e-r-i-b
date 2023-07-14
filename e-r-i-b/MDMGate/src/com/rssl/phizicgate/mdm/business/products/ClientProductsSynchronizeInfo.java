package com.rssl.phizicgate.mdm.business.products;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ��������� ��������
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
	 * ������ ���������� �� �������/������
	 * @param accounts ���������� �� �������/������
	 */
	void setAccounts(List<Account> accounts)
	{
		accountsDeleteList.addAll(accounts);
	}

	/**
	 * ������ ���������� �� ������
	 * @param cards ���������� �� ������
	 */
	void setCards(List<Card> cards)
	{
		cardsDeleteList.addAll(cards);
	}

	/**
	 * ������ ���������� �� ������ ����
	 * @param depoAccounts ���������� �� ������ ����
	 */
	void setDepoAccounts(List<DepoAccount> depoAccounts)
	{
		depoAccountsDeleteList.addAll(depoAccounts);
	}

	/**
	 * ������ ���������� �� ��������
	 * @param loans ���������� �� ��������
	 */
	void setLoans(List<Loan> loans)
	{
		loansDeleteList.addAll(loans);
	}

	/**
	 * ������ ���������� �� ��������
	 * @param services ���������� �� ��������
	 */
	void setServices(List<Service> services)
	{
		servicesDeleteList.addAll(services);
	}

	/**
	 * @return ���������� �� �������/������ ��� ����������
	 */
	public List<Account> getAccountsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accountsSaveList;
	}

	/**
	 * @return ���������� �� �������/������ ��� ��������
	 */
	public List<Account> getAccountsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accountsDeleteList;
	}

	/**
	 * @return ���������� �� ������ ��� ����������
	 */
	public List<Card> getCardsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cardsSaveList;
	}

	/**
	 * @return ���������� �� ������ ��� ��������
	 */
	public List<Card> getCardsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cardsDeleteList;
	}

	/**
	 * @return ���������� �� ������ ���� ��� ����������
	 */
	public List<DepoAccount> getDepoAccountsSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccountsSaveList;
	}

	/**
	 * @return ���������� �� ������ ���� ��� ��������
	 */
	public List<DepoAccount> getDepoAccountsDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccountsDeleteList;
	}

	/**
	 * @return ���������� �� �������� ��� ����������
	 */
	public List<Loan> getLoansSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loansSaveList;
	}

	/**
	 * @return ���������� �� �������� ��� ��������
	 */
	public List<Loan> getLoansDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loansDeleteList;
	}

	/**
	 * @return ���������� �� �������� ��� ����������
	 */
	public List<Service> getServicesSaveList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return servicesSaveList;
	}

	/**
	 * @return ���������� �� �������� ��� ��������
	 */
	public List<Service> getServicesDeleteList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return servicesDeleteList;
	}
}
