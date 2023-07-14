package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author lepihina
 * @ created 21.04.14
 * $Author$
 * $Revision$
 * Сервис для работы с МСС-кодами
 */
public class MerchantCategoryCodeService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.dictionaries.finances.";
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Вернуть тип карточной операции по коду
	 * @param code - код типа операций
	 * @return тип карточной операции
	 */
	public MerchantCategoryCode findByCode(final Long code) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MerchantCategoryCode>()
			{
				public MerchantCategoryCode run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getMCCByCode");
					query.setParameter("code", code);
					//noinspection unchecked
					return (MerchantCategoryCode)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск MCC-кодов
	 * @param mccCodes - список идентификаторов кодов
	 * @return список MCC-кодов с привязанными операциями
	 */
	public List<MerchantCategoryCode> findMCCByCode(final Collection<Long> mccCodes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<MerchantCategoryCode>>()
			{
				public List<MerchantCategoryCode> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findMCCByCode");
					query.setParameterList("mccCodes", mccCodes);
					//noinspection unchecked
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
	 * Обновляет/удаляет список МСС-кодов по идентификатору категории
	 * @param categoryExternalId externalId идентификатору категории
	 * @param income доходность категории
	 * @throws BusinessException
	 */
	public void updateOrDeleteMCC(final String categoryExternalId, final boolean income) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query deleteQuery = session.getNamedQuery(QUERY_PREFIX + (income ? "deleteEmptyOutcomeMCC" : "deleteEmptyIncomeMCC"));
					deleteQuery.setParameter("categoryExternalId", categoryExternalId);
					deleteQuery.executeUpdate();

					Query updateQuery = session.getNamedQuery(QUERY_PREFIX + (income ? "setNullIncomeByCategory" : "setNullOutcomeByCategory"));
					updateQuery.setParameter("categoryExternalId", categoryExternalId);
					updateQuery.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает список MCC-кодов для указанной категории
	 * @param categoryExternalId - внешний идентификатор категории
	 * @return список mcc-кодов
	 * @throws BusinessException
	 */
	public List<Long> getCategoryMCCodes(final String categoryExternalId) throws  BusinessException
	{
		if (categoryExternalId == null)
			return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCategoryMCCodes");
					query.setParameter("categoryId", categoryExternalId);
					//noinspection unchecked
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
	 * Добавление/сохранение МСС-кода
	 * @param mcc - МСС-код
	 * @throws BusinessException
	 */
	public void addOrUpdate(final MerchantCategoryCode mcc) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(mcc);
					session.flush();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновляет список mcc-кодов
	 * @param mccList - список mcc-кодов
	 * @throws BusinessException
	 */
	public void addOrUpdateMCCList(List<MerchantCategoryCode> mccList) throws BusinessException
	{
		simpleService.addOrUpdateList(mccList);
	}

	/**
	 * Список mcc-кодов, привязанных к категории, строкой
	 * @param categoryExternalId - внешний идентификатор категории
	 * @return список строкой
	 */
	public String getByCategoryAsString(final String categoryExternalId)
	{
		String mccCodesStr = "";
		try
		{
			List<Long> codesList = getCategoryMCCodes(categoryExternalId);
			mccCodesStr = StringUtils.join(codesList, ", ");
		}
		catch (BusinessException ignore){}
		return mccCodesStr;
	}

	/**
	 * Обновляет и удаляет список мсс-кодов с заданными кодами
	 * @param mccCodes список кодов
	 * @param income тип категории для которого обнуляем категорию в mcc-коде
	 * @throws BusinessException
	 */
	public void updateOrDeleteMCCByCodes(final List<Long> mccCodes, final boolean income) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + (income ? "setNullIncome" : "setNullOutcome"));
					query.setParameterList("mccIds", mccCodes);
					query.executeUpdate();

					Query deleteQuery = session.getNamedQuery(QUERY_PREFIX + "deleteNullMCC");
					deleteQuery.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
