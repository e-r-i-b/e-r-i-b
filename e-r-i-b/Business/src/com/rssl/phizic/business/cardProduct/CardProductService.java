package com.rssl.phizic.business.cardProduct;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.locale.MultiLocaleDetachedCriteria;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.locale.MultiLocaleSimpleService;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 * @author gulov
 * @ created 06.10.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � ���������� ����������
 */
public class CardProductService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final MultiLocaleSimpleService localedSimpleService = new MultiLocaleSimpleService();

	/**
	 * ����� ������� �� ����������� �����
	 * � ������� �������� ������ �� ����, ������� �������� �� ������� � ����� ������� ����
	 * @param id - ���������� ����
	 * @return - �������
	 * @throws BusinessException
	 */
	public CardProduct findById(final Long id) throws BusinessException
	{
		try
		{
			return simpleService.findById(CardProduct.class, id);
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� ����������� �����
	 * � ������� �������� ������ �� ����, ������� �������� �� ������� � ����� ������� ����
	 * @param id - ���������� ����
	 * @return - �������
	 * @throws BusinessException
	 */
	public CardProduct findLocaledById(final Long id) throws BusinessException
	{
		try
		{
			return localedSimpleService.findById(CardProduct.class, id, null);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������
	 * @param product - �������
	 * @throws BusinessException
	 */
	public void save(final CardProduct product) throws BusinessException
	{
		simpleService.addOrUpdate(product);
	}

	/**
	 * ������� �������
	 * @param product - �������
	 * @throws BusinessException
	 */
	public void remove(final CardProduct product) throws BusinessException
	{
		simpleService.remove(product);
	}

	/**
	 * �������� ������ �������� ��������� ����������� ����, �.�. ����������� �����, ��������� ������ � � ����� �������� ������ ������ ���� ������ �������
	 * @return  - ������ ������� ����������� ����, ���� null
	 * @throws BusinessException
	 */
	public List<CardProduct> findActivityVirtualCards() throws BusinessException
	{
		try
		{
			Calendar currentDate = DateHelper.getCurrentDate();
			DetachedCriteria criteria = MultiLocaleDetachedCriteria.forClassInLocale(CardProduct.class, MultiLocaleContext.getLocaleId());
			criteria.add(Restrictions.eq("type", CardProductType.VIRTUAL))
				.add(Restrictions.eq("online", true))
				.add(Restrictions.ge("stopOpenDate", currentDate))
				.createAlias("kindOfProducts", "kinds")
				.add(Restrictions.ge("kinds.stopOpenDeposit", currentDate))
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			return buildCardProductList(simpleService.find(criteria));

		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ ������ �������� �� ��: ������ ������ ���������� �������� � ������ ���������� �� ����
	 * @return ��������� ������ ������ �������� CardProduct
	 */
	private List<CardProduct> buildCardProductList(List lists)
	{
		Iterator iter = lists.iterator();

		Map<Long, List<CASNSICardProduct>> tempProducts = new HashMap<Long, List<CASNSICardProduct>>();
		Map<Long, CardProduct> cardProducts = new HashMap<Long, CardProduct>();
		while ( iter.hasNext() )
		{
		    Map map = (Map) iter.next();
		    CardProduct cardProduct = (CardProduct) map.get(Criteria.ROOT_ALIAS);
		    CASNSICardProduct kind = (CASNSICardProduct) map.get("kinds");

			if (!tempProducts.containsKey(cardProduct.getId()))
			{
				tempProducts.put(cardProduct.getId(), new ArrayList<CASNSICardProduct>());
				cardProducts.put(cardProduct.getId(), cardProduct);
			}
			tempProducts.get(cardProduct.getId()).add(kind);
		}
		for (Map.Entry<Long, List<CASNSICardProduct>> entry : tempProducts.entrySet())
			cardProducts.get(entry.getKey()).setKindOfProducts(entry.getValue());
		return new ArrayList<CardProduct>(cardProducts.values());
	}	

	/**
	 * �������� ���� " ������������ ���� �������� ���� ���������� ��������" ���� ��������� ���������, � ������� ��� ������� ���� �� ���� ��� ���������� �������
	 */
	public void updateStopOpenDateAllCardProducts() throws BusinessException
	{
		try
		{
			List<CardProduct> cardProducts = getModificationsCardProducts();  // ��������, � ������� ���� ����������� �������

			if (cardProducts == null)
			    return;

			List<CardProduct> updateCardProducts = new ArrayList<CardProduct>(); // ������ �������������� � ���������� ��������� ���������

			for(CardProduct cardProduct : cardProducts)
			{
			    Calendar maxDateCalendar = null;

				// ����� ������������ ���� ����������� �������� ������� � ����� ���������
			    for(CASNSICardProduct casnsiCardProduct : cardProduct.getKindOfProducts())
			    {
			        if (maxDateCalendar == null || maxDateCalendar.compareTo(casnsiCardProduct.getStopOpenDeposit()) < 0)
				        maxDateCalendar = casnsiCardProduct.getStopOpenDeposit();
			    }
			    cardProduct.setStopOpenDate(maxDateCalendar);
			    updateCardProducts.add(cardProduct);
			}

			if (!updateCardProducts.isEmpty())
			    simpleService.addOrUpdateList(updateCardProducts);
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ������ �������������� � ���������� ��������� ���������
	 */
	public List<CardProduct> getModificationsCardProducts() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardProduct>>()
			{
				public List<CardProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.cardProduct.CardProduct.getModificationProducts");
					query.setParameter("current_date", DateHelper.getCurrentDate());
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
	 * ����� �������� ��������� ���������
	 * @param type - ��� ��������
	 * @param online - ������� ����������
	 * @param date - ���� ����������
	 * @return - ������ �������� ���������
	 * @throws BusinessException
	 */
	public Boolean activeCardProductExists(final CardProductType type, final boolean online,
	                                       final Calendar date) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CardProduct.class.getName() + ".countActiveCardProduct");
					query.setParameter("type", type);
					query.setParameter("online", online);
					query.setParameter("date", date);

					return ((Integer) query.uniqueResult()) > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
