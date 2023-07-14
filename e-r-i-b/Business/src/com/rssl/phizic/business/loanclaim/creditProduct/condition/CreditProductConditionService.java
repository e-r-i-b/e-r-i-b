package com.rssl.phizic.business.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author Moshenko
 * @ created 15.01.2014
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ���������  �� ��������� ���������.
 */
public class CreditProductConditionService
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������/��������.
	 * @param condition ����� ������� �� ���������� ��������.
	 * @throws BusinessException
	 */
	public void addOrUpdate(CreditProductCondition condition) throws BusinessException
	{
		 simpleService.addOrUpdateWithConstraintViolationException(condition);
	}

	/**
	 * ����� �� id
	 * @param id id
	 * @return ����� ������� �� ���������� ��������.
	 * @throws BusinessException
	 */
	public CreditProductCondition findeById(Long id) throws BusinessException
	{
		return simpleService.findById(CreditProductCondition.class,id);
	}

	/**
	 * @param condition ����� ������� �� ���������� ��������.
	 * @param currCode �������� ��� ������
	 * @return ����������� ���������� ������� ��� ������, ���� ��� ������ �� null;
	 * ����������� ��������� ���������� ������� � ����� ������ �������� ����� ���� ������ ������� ����,
	 * �� ������������ �� ������ ������ ���������� �������.
	 * ��� ������ ������ ������ ������� ����� ���� ������ ���� ����������� ��������� �������.
	 */
	public CurrencyCreditProductCondition getActiveCurrCondition(CreditProductCondition condition,String currCode)
	{

		List<CurrencyCreditProductCondition> condList = getAllCurrencyCondition(condition, currCode);
		if (condList.isEmpty())
			return null;


		CurrencyCreditProductCondition activeCond = condList.get(0);
		for(CurrencyCreditProductCondition cond:condList)
		{
			Calendar startDate = cond.getStartDate();
			if (startDate.compareTo(activeCond.getStartDate()) >= 0 && startDate.compareTo(Calendar.getInstance()) <= 0)
				activeCond = cond;
		}

		activeCond = activeCond.getStartDate().compareTo(Calendar.getInstance()) <= 0 ? activeCond : null;
	 	return activeCond;

	}



	/**
	 * @param condition ����� ������� �� ���������� ��������.
	 * @param currCode �������� ��� ������
	 * @return ����� ���������� ������� ��� ������, ���� ��� ������ �� null;
	 * ����� ��������� ���������� ������� � ����� ������ �������� ������ �������.
	 * ��� ������ ������ ������ ������� ����� ���� ������ ���� ����� ��������� �������.
	 */
	public CurrencyCreditProductCondition getNewCurrCondition(CreditProductCondition condition,String currCode)
	{
		List<CurrencyCreditProductCondition> condList = getAllCurrencyCondition(condition, currCode);
		if (condList.isEmpty())
			return null;


		CurrencyCreditProductCondition newCond = condList.get(0);
		for(CurrencyCreditProductCondition cond:condList)
		{
			Calendar startDate  = cond.getStartDate();
			if (startDate.compareTo(newCond.getStartDate()) > 0 && startDate.compareTo(Calendar.getInstance()) > 0)
				newCond = cond;
        }

		newCond = newCond.getStartDate().compareTo(Calendar.getInstance()) > 0 ? newCond : null;
		return  newCond;
	}
	/**
	 *
	 * �������� ������ ���� �������� ������� �� ������, �� ������ �������.
	 * ���� ������ �� ����� �� ������ ������.
	 * @param condition ����� �������.
	 * @param currCode ��� ������. (RUB,USD,EUR)
	 * @return
	 */
	public List<CurrencyCreditProductCondition> getAllCurrencyCondition(CreditProductCondition condition, String currCode)
	{
		List<CurrencyCreditProductCondition> result = new ArrayList<CurrencyCreditProductCondition>();
		List<CurrencyCreditProductCondition> currCondList;
		if (condition.getCurrConditions() == null)
			currCondList = new ArrayList<CurrencyCreditProductCondition>();
		else
			currCondList = new ArrayList<CurrencyCreditProductCondition> (condition.getCurrConditions());
		Collections.sort(currCondList, new CurrencyConditionDateComporator());
		for (CurrencyCreditProductCondition currCond:currCondList)
		{
			Currency currency =  currCond.getCurrency();
			if (StringHelper.equals(currency.getCode(), currCode))
				result.add(currCond);
		}
		return result;
	}
	/**
	 * @param condition ����� �������.
	 * @return �������� ������ ���� ����������� �������(�� 3� �������), �� ������ �������.
	 */
	public List<CurrencyCreditProductCondition> getAllActiveCurrCondition(CreditProductCondition condition)
	{
		List<CurrencyCreditProductCondition> result = new ArrayList<CurrencyCreditProductCondition>();
		String[] currArr = {"RUB","EUR","USD"};
		for (String curr:currArr)
		{
			CurrencyCreditProductCondition currCond = getActiveCurrCondition(condition,curr);
			if (currCond != null)
				result.add(currCond);
		}
		return result;
	}

	/**
	 *
	 * ���������� ������ �������� � ��������� ������� ���������� �������
	 *
	 * @param condition ��������� ������� ��� �������� ����� ��������� ����� ���������� �������
	 * @return List&lt;CurrencyCreditProductCondition&gt;
	 */
	public List<CurrencyCreditProductCondition> getClientAvailableCurrConditions(CreditProductCondition condition)
	{
		List<CurrencyCreditProductCondition> result = new ArrayList<CurrencyCreditProductCondition>();
		List<CurrencyCreditProductCondition> allCurCondition   = getAllActiveCurrCondition(condition);

		for (CurrencyCreditProductCondition currCondition:allCurCondition)
		{
			if (currCondition.isClientAvaliable())
			{
				result.add(currCondition);
			}
		}
		return result;
	}

	/**
	 * @param condition ����� ������� �� ���������� ��������.
	 * @param currCode ��� ������
	 * @return �������� �������� �������.
	 */
	public List<CurrencyCreditProductCondition> getArchCurrCondition(CreditProductCondition condition,String currCode)
	{
		List<CurrencyCreditProductCondition> allCond = getAllCurrencyCondition(condition,currCode);
		CurrencyCreditProductCondition  currentCond = getActiveCurrCondition(condition,currCode);
		List<CurrencyCreditProductCondition> archCond = new ArrayList<CurrencyCreditProductCondition>();
		for (CurrencyCreditProductCondition cond: allCond)
		{
			if (cond == currentCond)
				break;
			archCond.add(cond);
		}
		return archCond;
	}

	/**
	 * ����� �� id
	 * @param id id
	 * @return ���������� ������� �� ���������� ��������.
	 * @throws BusinessException
	 */
	public CurrencyCreditProductCondition findCurrCondById(Long id) throws BusinessException
	{
		return simpleService.findById(CurrencyCreditProductCondition.class,id);
	}

	/**
	 * ���������� ������ ��������� ������� ��� ������� ����������� ��������� �������:
	 * <br/>
	 * 1. ��� ������������;
	 * <br/>
	 * 2. ��� ������� ������� - ��� �������� � ��������� �������;
	 * <br/>
	 * 3. ���� ��������, ��������� ��� ������� ���������� �������.
	 *
	 * @return List&lt;CreditProductCondition&gt;
	 */
	public List<CreditProductCondition> getPublicityConditions() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CreditProductCondition.class)
													.add(Expression.eq("published", true))
													.add(Expression.isNotNull("creditProduct"))
													.add(Expression.isNotNull("creditProductType"))
													.add(Expression.isNotEmpty("currConditions"));

		List<CreditProductCondition> conditions = simpleService.find(criteria);
		return getPublicityConditionsWithActiveCurrConditions(conditions);
	}

	/**
	 * ���������� ������ ��������� ������� ��� ������� ����������� ��������� �������:
	 * <br/>
	 * 1. ��� ������������;
	 * <br/>
	 * 2. ��� ������� ������� - ��� �������� � ��������� �������;
	 * <br/>
	 * 3. ���� ��������, ��������� ��� ������� ���������� �������.
	 * <br/>
	 * 4. ��� �������� � ���� ������.
     * @return List&lt;CreditProductCondition&gt;
	 */
	public List<CreditProductCondition> getAvaliableConditions() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CreditProductCondition.class)
				.add(Expression.eq("published", true))
				.add(Expression.eq("selectionAvaliable", true))
				.add(Expression.isNotNull("creditProduct"))
				.add(Expression.isNotNull("creditProductType"))
				.add(Expression.isNotEmpty("currConditions"));

		List<CreditProductCondition> conditions = simpleService.find(criteria);
		return getPublicityConditionsWithActiveCurrConditions(conditions);
	}

	/**
	 * @param product ��������� ��������.
	 * @return ������ �������������� �������.
	 * @throws BusinessException
	 */
	public List<CreditProductCondition> getPublishedConditionsByProduct(CreditProduct product) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CreditProductCondition.class)
				.add(Expression.eq("creditProduct",product))
				.add(Expression.eq("published",true));

		List<CreditProductCondition>  conditions = simpleService.find(criteria);
		if (conditions.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		return conditions;
	}

	/**
	 *
	 * ������ �������������� ��������� ������� ��������� �� ���� �������� ���������� ������ �������� ���������� �������. � ������ ������ ���������� ������ ������.
	 *
	 * @param  productCode ��� ��������
	 * @return List
	 */
	public List<CreditProductCondition> getConditionsByProductCode(final String productCode)
	{
		if (StringHelper.isEmpty(productCode))
		{
			return new ArrayList<CreditProductCondition>();
		}

		try
		{
			List<CreditProductCondition> conditions =  HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditProductCondition>>()
			{
				public List<CreditProductCondition> run(Session session) throws Exception
				{
					return  session.getNamedQuery("com.rssl.phizic.business.loanclaim.creditProduct.condition.getConditionsByProductCode")
   								                                     .setParameter("productCode", productCode)
																	 .list();
				}
			});
			return  getPublicityConditionsWithActiveCurrConditions(conditions);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return new ArrayList<CreditProductCondition>();
		}
	}

	private List<CreditProductCondition> getPublicityConditionsWithActiveCurrConditions(List<CreditProductCondition> creditProductConditions)
	{
		if (creditProductConditions.isEmpty())
		{
			return Collections.emptyList();
		}
		List<CreditProductCondition> result = new ArrayList<CreditProductCondition>();
		for (CreditProductCondition condition:creditProductConditions)
		{
			List<CurrencyCreditProductCondition> currConditions = getClientAvailableCurrConditions(condition);
			if (!currConditions.isEmpty())
			{
				if (condition.getCurrConditions().size() > currConditions.size())
				{
					condition.getCurrConditions().clear();
					condition.getCurrConditions().addAll(currConditions);
				}
				result.add(condition);
			}
		}

		return result;
	}

	/**
	 * @param condition ����� ������� �� ���������� ��������.
	 * @throws BusinessException
	 */
	public void remove(CreditProductCondition condition) throws BusinessException
	{
		simpleService.remove(condition);
	}

	/**
	 * @param remCurrCond �� �������� ������� �� ��������.
	 * @throws BusinessException
	 */
	public void remove(Set<CurrencyCreditProductCondition> remCurrCond) throws BusinessException
	{
		simpleService.removeList(new ArrayList<CurrencyCreditProductCondition>(remCurrCond));
	}
}
