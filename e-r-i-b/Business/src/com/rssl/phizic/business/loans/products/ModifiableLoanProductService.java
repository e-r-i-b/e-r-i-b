package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.LongType;

import java.util.List;
import java.util.Map;

/**
 * —ервис дл€ работы с редактируемыми кредитными продуктами
 * @author Dorzhinov
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class ModifiableLoanProductService
{
	private static final String FILTER_NAME = "loan_product_filter_by_department";
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ƒобавить кредитный продукт
	 * @param product продукт
	 */
	public void add(final ModifiableLoanProduct product) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdateWithConstraintViolationException(product);
					for(LoanCondition condition : product.getConditions())
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
	 * ќбновить кредитный продукт
	 * @param product продукт
	 */
	public void update(final ModifiableLoanProduct product) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdateWithConstraintViolationException(product);

					List<LoanCondition> oldConditions = getConditionsByProduct(product);
					oldConditions.removeAll(product.getConditions());
					for(LoanCondition deletedCondition : oldConditions)
						simpleService.remove(deletedCondition);
					for(LoanCondition condition : product.getConditions())
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
	 * ”далить кредитный продукт
	 * @param product продукт
	 */
	public void remove(final ModifiableLoanProduct product) throws BusinessException
	{
		simpleService.removeWithConstraintViolationException(product);
	}

	/**
	 * —писок всех модифицируемых продуктов с учетом доступа к тербанкам
	 * @return —писок всех модифицируемых продуктов
	 * @throws BusinessException
	 */
	public List<ModifiableLoanProduct> getAllProducts(final boolean isAllTbAccess, final Long employeeLoginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ModifiableLoanProduct>>()
		    {
		        public List<ModifiableLoanProduct> run(Session session) throws Exception
		        {
			        if(!isAllTbAccess)
				        enableFilter(session,employeeLoginId);
		            Query query = session.getNamedQuery(ModifiableLoanProduct.class.getName() + ".getAllProductsByAllowedTB");
		            return query.list();
				}
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	public ModifiableLoanProduct findById ( final Long id ) throws BusinessException
	{
		ModifiableLoanProduct product = simpleService.findById(ModifiableLoanProduct.class,id);
		if (product == null)
			return null;
		for(LoanCondition condition : product.getConditions())
			if((condition.getMinAmount() != null && !condition.getMinAmount().getCurrency().getNumber().equals(condition.getCurrency().getNumber()))
					|| (condition.getMaxAmount() != null && !condition.getMaxAmount().getCurrency().getNumber().equals(condition.getCurrency().getNumber())))
				throw new BusinessException("¬алюта минимальной или максимальной суммы не соответствует валюте услови€.");
		return product;
	}

	/**
	 * —писок кредитных продуктов с учетом фильтрации и доступа к тербанкам
	 * @param filterParams параметры фильтрации
	 * @return —писок кредитных продуктов
	 * @throws BusinessException
	 */
	public List<ModifiableLoanProduct> findByFilter(final Map<String, Object> filterParams, final boolean isAllTbAccess, final Long employeeLoginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ModifiableLoanProduct>>()
		    {
		        public List<ModifiableLoanProduct> run(Session session) throws Exception
		        {
			        if(!isAllTbAccess)
				        enableFilter(session,employeeLoginId);
		            Query query = session.getNamedQuery(ModifiableLoanProduct.class.getName() + ".findByFilter");
			        query.setParameter("kindId", filterParams.get("kind"), new LongType());
			        query.setParameter("productId", filterParams.get("product"), new LongType());
			        query.setParameter("publicity", StringHelper.getNullIfEmpty((String)filterParams.get("publicity")));

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
	 * —писок условий кредитного продукта
	 * @param product кредитный продукт
	 * @return —писок условий кредитного продукта
	 * @throws BusinessException
	 */
	private List<LoanCondition> getConditionsByProduct(final ModifiableLoanProduct product) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanCondition>>()
		{
			public List<LoanCondition> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(ModifiableLoanProduct.class.getName() + ".getConditionsByProduct");
				query.setParameter("productId", product.getId());
				return query.list();
			}
		});
	}

	/**
	 * —писок условий по id условий кредита в разрезе валют
	 * @param id - услови€ кредита в разрезе валют
	 * @return - найденно условие
	 * @throws BusinessException
	 */
	public LoanCondition findLoanConditionById(final Long id) throws BusinessException
	{
		return simpleService.findById(LoanCondition.class, id);
	}

	/**
	 * ѕоиск кредитного продукта по id услови€
	 * @param id - идентификатор услови€
	 * @return -  один найденный кредитный продукт, если найдено больше - то ошибка, если ничего - то Null
	 * @throws BusinessException
	 */
	public ModifiableLoanProduct getOfferProductByConditionId(final Long id)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ModifiableLoanProduct>()
			{
				public ModifiableLoanProduct run(Session session) throws Exception
				{
		           Query query = session.getNamedQuery(ModifiableLoanProduct.class.getName() + ".getOfferProductByConditionId");
		           query.setParameter("id", id);
				   return (ModifiableLoanProduct) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ѕроверка существовани€ опубликованных  кредитных продуктов
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
					Query query = session.getNamedQuery(ModifiableLoanProduct.class.getName() + ".isPublishedProductsExists");
					return !query.uniqueResult().equals(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void enableFilter(Session session, Long employeeLoginId)
	{
		Filter filter = session.enableFilter(FILTER_NAME);
		filter.setParameter("employeeLoginId",employeeLoginId);
	}

	public Integer checkAvailableLoanOffer(final Long tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.loans.product.LoanProductListOperation.count");
					query.setParameter("tb", tb);

					return (Integer) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
