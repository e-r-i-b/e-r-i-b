package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibilityInfo;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author EgorovaA
 * @ created 14.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityVisibilityService
{
	/**
	 * Обновить видимость вкладных продуктов
	 * @param visibility - сущность для обновления
	 * @throws BusinessException
	 */
	public void updateVisibility(final DepositEntityVisibility visibility) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(visibility);
					session.flush();
					session.clear();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось обновить видимость вкладных продуктов в тербанках", e);
		}
	}

	/**
	 * Получить список видов вкладов, доступных для открытия в тербанке
	 * @param tb - код ТБ
	 * @return список с номерами видов вкладов
	 * @throws BusinessException
	 */
	public List<Long> getOnlineAvailableTypes(final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility.getAllowedTypes");
					query.setParameter("tb", tb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список всех возможных видов вкладов, загруженных из справочника ЦАС НСИ
	 * @return список с номерами видов вкладов
	 * @throws BusinessException
	 */
	public List<Long> getAllTypes() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility.getAllTypes");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список всех подвидов вкладного продукта, доступных для открытия в тербанке
	 * @param type - код вида вклада
	 * @param groupCode - код группы вкладного продукта
	 * @param tb - код ТБ
	 * @return список кодов подвидов вкладного продукта
	 * @throws BusinessException
	 */
	public List<Long> getDepositsSubTypesByTb(final Long type, final Long groupCode, final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility.getAllowedSubTypes");
					query.setParameter("type", type);
					query.setParameter("groupCode", groupCode);
					query.setParameter("tb", tb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список всех подвидов вкладного продукта
	 * @param type - тип вклада
	 * @param groupCode - код группы вкладного продукта
	 * @return список кодов подвидов вкладного продукта
	 * @throws BusinessException
	 */
	public List<Long> getAllDepositSubTypes(final Long type, final Long groupCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility.getAllSubTypes");
					query.setParameter("type", type);
					query.setParameter("groupCode", groupCode);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить информацию о подвидах вклада (для страницы настройки видимости)
	 * @param type - вид вклада
	 * @return
	 * @throws BusinessException
	 */
	public List<DepositEntityVisibilityInfo> getDepositVisibilityInfoList(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DepositEntityVisibilityInfo>>()
			{
				public List<DepositEntityVisibilityInfo> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibilityInfo.getInfoList");
					query.setParameter("type", type);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить доступность вклада для открытия, видимость в тербанках и доступные для открытия подвиды
	 * @param type - вид вклада
	 * @return
	 * @throws BusinessException
	 */
	public DepositEntityVisibility getDepositEntityVisibility(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<DepositEntityVisibility>()
			{
				public DepositEntityVisibility run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibility.getAvailSubTypes");
					query.setParameter("type", type);
					return (DepositEntityVisibility) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить название вида вклада (для настройки видимости)
	 * @param type - вид вклада
	 * @return
	 * @throws BusinessException
	 */
	public String getDepositEntityName(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB.getDepositTypeName");
					query.setLong("type", type);
					query.setMaxResults(1);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
