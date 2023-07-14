package com.rssl.phizic.business.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.commission.TransferType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author niculichev
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class TariffService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Поиск тарифа комиссии по идентификатору
	 * @param id идентификатор
	 * @return тариф
	 * @throws BusinessException
	 */
	public Tariff findById(Long id) throws BusinessException
	{
		return simpleService.findById(Tariff.class, id);
	}

	/**
	 * Поиск тарифа комиссии по коду валюты и типу перевода
	 * @param currencyCode код валюты
	 * @param transferType тип перевода
	 * @return тариф
	 * @throws BusinessException
	 */
	public Tariff findByTypeAndCur(final String currencyCode, final TransferType transferType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Tariff>()
			{
				public Tariff run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(Tariff.class)
						.add(Restrictions.eq("currencyCode", currencyCode))
						.add(Restrictions.eq("transferType", transferType));

					return (Tariff) criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Добавление/обновление тарифа
	 * @param tariff тариф
	 * @return
	 * @throws BusinessException
	 */
	public void addOrUpdate(Tariff tariff) throws BusinessException
	{
		simpleService.addOrUpdate(tariff);
	}

	/**
	 * Получение тарифов по типу перевода
	 * @param transferType тип перевода
	 * @return
	 * @throws BusinessException
	 */
	public List<Tariff> getTariffs(final TransferType transferType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Tariff>>()
			{
				public List<Tariff> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(Tariff.class)
						.add(Restrictions.eq("transferType", transferType));

					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаления тарифа
	 * @param tariff тариф, который нужно удалить
	 * @throws BusinessException
	 */
	public void remove(Tariff tariff) throws BusinessException
	{
		simpleService.remove(tariff);
	}
}
