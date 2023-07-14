package com.rssl.phizicgate.mdm.common;

import java.util.List;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ��������� �� ���
 */

public class ClientProductsInfo
{
	private List<AccountInfo> accounts;
	private List<CardInfo> cards;
	private List<DepoAccountInfo> depoAccounts;
	private List<LoanInfo> loans;
	private List<ServiceInfo> services;

	/**
	 * @return ���������� �� �������/������
	 */
	public List<AccountInfo> getAccounts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return accounts;
	}

	/**
	 * ������ ���������� �� �������/������
	 * @param accounts ���������� �� �������/������
	 */
	public void setAccounts(List<AccountInfo> accounts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.accounts = accounts;
	}

	/**
	 * @return ���������� �� ������
	 */
	public List<CardInfo> getCards()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cards;
	}

	/**
	 * ������ ���������� �� ������
	 * @param cards ���������� �� ������
	 */
	public void setCards(List<CardInfo> cards)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cards = cards;
	}

	/**
	 * @return ���������� �� ������ ����
	 */
	public List<DepoAccountInfo> getDepoAccounts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return depoAccounts;
	}

	/**
	 * ������ ���������� �� ������ ����
	 * @param depoAccounts ���������� �� ������ ����
	 */
	public void setDepoAccounts(List<DepoAccountInfo> depoAccounts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.depoAccounts = depoAccounts;
	}

	/**
	 * @return ���������� �� ��������
	 */
	public List<LoanInfo> getLoans()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return loans;
	}

	/**
	 * ������ ���������� �� ��������
	 * @param loans ���������� �� ��������
	 */
	public void setLoans(List<LoanInfo> loans)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.loans = loans;
	}

	/**
	 * @return ���������� �� ��������
	 */
	public List<ServiceInfo> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * ������ ���������� �� ��������
	 * @param services ���������� �� ��������
	 */
	public void setServices(List<ServiceInfo> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}
}
