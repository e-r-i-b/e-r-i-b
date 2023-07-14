package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.basket.AccountingEntityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.List;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountingEntityService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * ����� ������ ����� �� ��������������
	 * @param id ������������� ������� �����
	 * @return ������ �����
	 * @throws BusinessException
	 */
	public AccountingEntity findById(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("������������� ������� ����� �� ����� ���� null");
		}
		return simpleService.findById(AccountingEntity.class, id);
	}

	/**
	 * ������� ��� �������� ������ �����
	 * @param entity ������ �����
	 * @throws BusinessException
	 */
	public void addOrUpdate(AccountingEntity entity) throws BusinessException
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("������ ����� �� ����� ���� null");
		}
		simpleService.addOrUpdate(entity);
	}

	/**
	 * ����� ������� ����� � �������������, ���������� ���������� ��������, �� ������ � ����
	 * @param loginId ������������� ������ �������
	 * @param type ��� ������� �����
	 * @param name ������������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<AccountingEntity> findLikeNameByLoginAndType(final long loginId, final AccountingEntityType type, final String name) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AccountingEntity>>()
			{
				public List<AccountingEntity> run(Session session) throws Exception
				{
					return (List<AccountingEntity>) session.getNamedQuery("com.rssl.phizic.business.basket.accountingEntity.AccountingEntity.findLikeNameByLoginAndType")
							.setParameter("login_id", loginId)
							.setParameter("type", type)
							.setParameter("name", name)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��� ������� ����� �������
	 * @param loginId �����
	 * @return ������ �������� �����
	 * @throws BusinessException
	 */
	public List<AccountingEntity> findByLogin(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AccountingEntity>>()
			{
				public List<AccountingEntity> run(Session session) throws Exception
				{
					return (List<AccountingEntity>) session.getNamedQuery("com.rssl.phizic.business.basket.accountingEntity.AccountingEntity.findByLogin")
							.setParameter("login_id", loginId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������������ ������� ����� �� ��������������
	 * @param id �������������
	 * @return ������������ ������� �����
	 * @throws BusinessException
	 */
	public String getNameById(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("������������� �� ����� ���� null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					return (String) session.getNamedQuery("com.rssl.phizic.business.basket.accountingEntity.AccountingEntity.getNameById")
							.setParameter("id", id)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
