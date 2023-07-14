package com.rssl.phizicgate.mdm.business.products;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.mdm.business.MDMDatabaseServiceBase;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ����������
 */

public class ProductService extends MDMDatabaseServiceBase
{
	/**
	 * �������� �����/������
	 * @param profileId ������������� ���������
	 * @return �����/������
	 */
	public List<Account> getAccounts(Long profileId) throws GateException
	{
		return getProducts(profileId, Account.class);
	}

	/**
	 * ��������� ��������� �� ������/�������
	 * @param saveList   ����������� ��������
	 * @param deleteList ��������� ��������
	 * @throws GateException
	 */
	public void saveAccounts(List<Account> saveList, List<Account> deleteList) throws GateException
	{
		save(saveList, deleteList, Account.class);
	}

	/**
	 * �������� �����
	 * @param profileId ������������� ���������
	 * @return �����
	 */
	public List<Card> getCards(Long profileId) throws GateException
	{
		return getProducts(profileId, Card.class);
	}

	/**
	 * ��������� ��������� �� ������
	 * @param saveList   ����������� ��������
	 * @param deleteList ��������� ��������
	 * @throws GateException
	 */
	public void saveCards(List<Card> saveList, List<Card> deleteList) throws GateException
	{
		save(saveList, deleteList, Card.class);
	}

	/**
	 * �������� ����� ����
	 * @param profileId ������������� ���������
	 * @return ����� ����
	 */
	public List<DepoAccount> getDepoAccounts(Long profileId) throws GateException
	{
		return getProducts(profileId, DepoAccount.class);
	}

	/**
	 * ��������� ��������� �� ������ ����
	 * @param saveList   ����������� ��������
	 * @param deleteList ��������� ��������
	 * @throws GateException
	 */
	public void saveDepoAccounts(List<DepoAccount> saveList, List<DepoAccount> deleteList) throws GateException
	{
		save(saveList, deleteList, DepoAccount.class);
	}

	/**
	 * �������� �������
	 * @param profileId ������������� ���������
	 * @return �������
	 */
	public List<Loan> getLoans(Long profileId) throws GateException
	{
		return getProducts(profileId, Loan.class);
	}

	/**
	 * ��������� ��������� �� ��������
	 * @param saveList   ����������� ��������
	 * @param deleteList ��������� ��������
	 * @throws GateException
	 */
	public void saveLoans(List<Loan> saveList, List<Loan> deleteList) throws GateException
	{
		save(saveList, deleteList, Loan.class);
	}

	/**
	 * �������� �������
	 * @param profileId ������������� ���������
	 * @return �������
	 */
	public List<Service> getServices(Long profileId) throws GateException
	{
		return getProducts(profileId, Service.class);
	}

	/**
	 * ��������� ��������� �� ��������
	 * @param saveList   ����������� ��������
	 * @param deleteList ��������� ��������
	 * @throws GateException
	 */
	public void saveServices(List<Service> saveList, List<Service> deleteList) throws GateException
	{
		save(saveList, deleteList, Service.class);
	}

	private <T> List<T> getProducts(Long profileId, Class<T> productClass) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(productClass);
			criteria.add(Expression.eq("profileId", profileId));
			return databaseService.find(criteria, LockMode.READ, getInstance());
		}
		catch (Exception e)
		{
			throw new GateException("������ ��������� ��������� " + productClass.getSimpleName() + " ��� ������� " + profileId, e);
		}
	}

	private <T> void save(final List<T> saveList, final List<T> deleteList, Class<T> productClass) throws GateException
	{
		try
		{
			executeAtomic(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					databaseService.addList(saveList, getInstance());
					databaseService.deleteAll(deleteList, getInstance());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException("������ ���������� ��������� " + productClass.getSimpleName(), e);
		}
	}

	/**
	 * �������� ���������� �� ���������
	 * @param profileId ������������� �������
	 * @return ���������� �� ���������
	 * @throws GateException
	 */
	public ClientProductsSynchronizeInfo getClientProductsInfo(Long profileId) throws GateException
	{
		ClientProductsSynchronizeInfo clientProductsSynchronizeInfo = new ClientProductsSynchronizeInfo();
		clientProductsSynchronizeInfo.setAccounts(getAccounts(profileId));
		clientProductsSynchronizeInfo.setCards(getCards(profileId));
		clientProductsSynchronizeInfo.setDepoAccounts(getDepoAccounts(profileId));
		clientProductsSynchronizeInfo.setLoans(getLoans(profileId));
		clientProductsSynchronizeInfo.setServices(getServices(profileId));
		return clientProductsSynchronizeInfo;
	}

	/**
	 * ��������� ���������� �� ���������
	 * @param clientProductsSynchronizeInfo ���������� �� ���������
	 * @throws GateException
	 */
	public void saveClientProductsInfo(ClientProductsSynchronizeInfo clientProductsSynchronizeInfo) throws GateException
	{
		saveAccounts(clientProductsSynchronizeInfo.getAccountsSaveList(), clientProductsSynchronizeInfo.getAccountsDeleteList());
		saveCards(clientProductsSynchronizeInfo.getCardsSaveList(), clientProductsSynchronizeInfo.getCardsDeleteList());
		saveDepoAccounts(clientProductsSynchronizeInfo.getDepoAccountsSaveList(), clientProductsSynchronizeInfo.getDepoAccountsDeleteList());
		saveLoans(clientProductsSynchronizeInfo.getLoansSaveList(), clientProductsSynchronizeInfo.getLoansDeleteList());
		saveServices(clientProductsSynchronizeInfo.getServicesSaveList(), clientProductsSynchronizeInfo.getServicesDeleteList());
	}
}
