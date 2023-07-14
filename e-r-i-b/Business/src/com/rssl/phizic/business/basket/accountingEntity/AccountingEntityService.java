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
	 * Найти объект учета по идентификатору
	 * @param id идентификатор объекта учета
	 * @return объект учета
	 * @throws BusinessException
	 */
	public AccountingEntity findById(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор объекта учета не может быть null");
		}
		return simpleService.findById(AccountingEntity.class, id);
	}

	/**
	 * Добаить или обновить объект учета
	 * @param entity объект учета
	 * @throws BusinessException
	 */
	public void addOrUpdate(AccountingEntity entity) throws BusinessException
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("Объект учета не может быть null");
		}
		simpleService.addOrUpdate(entity);
	}

	/**
	 * Найти объекты учета с наименованием, содержащим переданное значение, по логину и типу
	 * @param loginId ижентификатор логина клиента
	 * @param type тип объекта учета
	 * @param name наименование
	 * @return список объектов
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
	 * Получить все объекты учета клиента
	 * @param loginId логин
	 * @return список объектов учета
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
	 * Получить наименование объекта учета по идентификатору
	 * @param id идентификатор
	 * @return наименование объекта учета
	 * @throws BusinessException
	 */
	public String getNameById(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор не может быть null");
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
