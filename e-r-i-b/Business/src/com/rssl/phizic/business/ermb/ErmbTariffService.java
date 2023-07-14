package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * User: moshenko
 * Date: 05.10.12
 * Time: 11:17
 * Сервис для работы с тарифами ЕРМБ
 */
public class ErmbTariffService
{
	private final static SimpleService simpleService = new SimpleService();

	/**
	 * Обмновить или обновить тариф
	 * @param tarif ЕРМБ тариф
	 * @throws BusinessException
	 */
	public void addOrUpdate(ErmbTariff tarif) throws BusinessException
	{
		simpleService.addOrUpdate(tarif, null);
	}

	/**
	 * Найти тариф EРМБ
	 * @param id  id тарифа ЕРМБ
	 * @return тариф ЕРМБ
	 * @throws BusinessException
	 */
	public ErmbTariff findById(final Long id) throws BusinessException
	{
		return simpleService.findById(ErmbTariff.class,id,null);
	}


	/**
	 * @return Список доступных тарифов
	 */
	public List<ErmbTariff> getAllTariffs() throws BusinessException
	{
		return simpleService.getAll(ErmbTariff.class);
	}

	/**
	 * Возвращает активный тариф по коду
	 * @param code - код тарифа
	 * @return тариф или null, если тариф по указанному коду не найден
	 */
	public ErmbTariff getTariffByCode(String code) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ErmbTariff.class);
		criteria.add(Expression.eq("code",code));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Возвращает тариф по имени, без учета регистра
	 * @param name - имя тарифа
	 * @return тариф или null, если тариф по указанному имени не найден
	 */
	public ErmbTariff getTariffByName(String name) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ErmbTariff.class);
		criteria.add(Expression.ilike("name", name));
		return simpleService.findSingle(criteria);
	}

	/**
	 * @param tariff Тариф на удаление.
	 * @throws BusinessException
	 */
	public void remove(ErmbTariff tariff) throws BusinessException, DisconnectNotAvailibleErmbTariffException
	{
		try
		{
			simpleService.removeWithConstraintViolationException(tariff);
		}
		catch (ConstraintViolationException e)
		{
			throw new DisconnectNotAvailibleErmbTariffException(e,tariff.getId());
		}
	}

	/**
	 * Пере привязываем всех пользователей с одно тарифа на другой
	 * @param fromTariffId Тариф id с которого меняем.
	 * @param toTariffId Тариф на который меняем.
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void changeTariff(final Long fromTariffId,final Long toTariffId) throws BusinessException
	{
			try
			{
				HibernateExecutor.getInstance(null).execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbTariff.changeTariff");
						query.setParameter("fromTariffId", fromTariffId);
						query.setParameter("toTariffId", toTariffId);
						query.executeUpdate();
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
