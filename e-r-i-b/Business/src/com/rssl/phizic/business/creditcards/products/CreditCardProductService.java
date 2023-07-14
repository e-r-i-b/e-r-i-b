package com.rssl.phizic.business.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы с карточными кредитными продуктами
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardProductService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Добавить карточный кредитный продукт
	 * @param product продукт
	 */
	public void add(final CreditCardProduct product) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdateWithConstraintViolationException(product);
					for(CreditCardCondition condition : product.getConditions())
						simpleService.add(condition);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить карточный кредитный продукт
	 * @param product продукт
	 */
	public void update(final CreditCardProduct product) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdateWithConstraintViolationException(product);

					List<CreditCardCondition> oldConditions = getConditionsByProduct(product);
					oldConditions.removeAll(product.getConditions());
					for(CreditCardCondition deletedCondition : oldConditions)
						simpleService.remove(deletedCondition);
					for(CreditCardCondition condition : product.getConditions())
						simpleService.addOrUpdate(condition);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить карточный кредитный продукт
	 * @param product продукт
	 */
	public void remove(final CreditCardProduct product) throws BusinessException
	{
		simpleService.removeWithConstraintViolationException(product);
	}

	public CreditCardProduct findById ( final Long id ) throws BusinessException
	{
		CreditCardProduct product = simpleService.findById(CreditCardProduct.class,id);
		if (product == null)
			return null;
		for(CreditCardCondition condition : product.getConditions())
		{
			if(!condition.getMinCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber())
					|| !condition.getMaxCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber()))
				throw new BusinessException("Валюта минимального или максимального лимита не соответствует валюте условия.");
			if(!condition.getFirstYearPayment().getCurrency().getNumber().equals(condition.getCurrency().getNumber())
					|| !condition.getNextYearPayment().getCurrency().getNumber().equals(condition.getCurrency().getNumber()))
				throw new BusinessException("Валюта платы за годовое обслуживание не соответствует валюте условия.");
		}
		return product;
	}

	/**
	 * Список всех карточных кредитных продуктов
	 * @return Список карточных кредитных продуктов
	 * @throws BusinessException
	 */
	public List<CreditCardProduct> getAll() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditCardProduct>>()
		    {
		        public List<CreditCardProduct> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".getAll");
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
	 * Дефолтный продукт для предодобренных карточных кредитных продуктов
	 * @return Дефолтный продукт
	 * @throws BusinessException
	 */
	public CreditCardProduct getDefaultForPreappovedProducts() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CreditCardProduct>()
		    {
		        public CreditCardProduct run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".getDefaultForPreappovedProducts");
			        //noinspection unchecked
			        return (CreditCardProduct) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Список карточных кредитных продуктов, у которых установлен минимальный кредитный лимит в заданной валюте и меньше либо равный заданного лимита
	 * @param currency валюта лимита
	 * @param creditLimit величина кредитного лимита, с которым сравнивается минимальный кредитный лимит условий в разрезе валют
	 * @param include включать заданный кредитный лимит: true - включать, false - не включать
	 * @return Список карточных кредитных продуктов
	 * @throws BusinessException
	 */
	public List<CreditCardProduct> getAvailableProducts(final String currency, final String creditLimit, final String include) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditCardProduct>>()
			{
				public List<CreditCardProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".getAvailableProducts");
					query.setParameter("currency", currency);
					query.setParameter("creditLimit", creditLimit.replace('.', ','));
					query.setParameter("include", include);
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
	 * Список карточных кредитных продуктов, у которых установлен минимальный кредитный лимит в заданной валюте и меньше либо равный заданного лимита
	 * @param currency валюта лимита
	 * @param creditLimit величина кредитного лимита, с которым сравнивается минимальный кредитный лимит условий в разрезе валют
	 * @return Список карточных кредитных продуктов
	 * @throws BusinessException
	 */
	public List<CreditCardProduct> getPublicApprovedProducts(final String currency, final BigDecimal creditLimit) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditCardProduct>>()
			{
				public List<CreditCardProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".getPublicApprovedProducts");
					query.setParameter("currency", currency);
					query.setBigDecimal("creditLimit", creditLimit);
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
	 * Проверка существования опубликованных карточных кредитных продуктов
	 * @return true если есть; иначе - false
	 * @throws BusinessException
	 */
	public Boolean isPublishedProductsExists() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".isPublishedProductsExists");
					return !query.uniqueResult().equals(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Список условий карточного кредитного продукта
	 * @param product карточный кредитный продукт
	 * @return Список условий карточного кредитного продукта
	 * @throws BusinessException
	 */
	private List<CreditCardCondition> getConditionsByProduct(final CreditCardProduct product) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditCardCondition>>()
		{
			public List<CreditCardCondition> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".getConditionsByProduct");
				query.setParameter("productId", product.getId());
				return query.list();
			}
		});
	}

	/**
	 * Получить карточный кредитный продукт по id условия
	 * @param id - идентификатор условия
	 * @return  - карточный кредитный продукт
	 * @throws Exception
	 */
	public CreditCardProduct findCreditCardProductByCreditCardConditionId(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CreditCardProduct>()
			{
				public CreditCardProduct run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditCardProduct.class.getName() + ".findCreditCardProductByCreditCardConditionId");
					query.setParameter("conditionId", id);
					return (CreditCardProduct) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	public CreditCardCondition findCreditCardConditionById(final Long id) throws BusinessException
	{
		return simpleService.findById(CreditCardCondition.class, id);
	}
}
