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
 * —ервис дл€ работы с карточными продуктами
 */
public class CardProductService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final MultiLocaleSimpleService localedSimpleService = new MultiLocaleSimpleService();

	/**
	 * Ќайти продукт по уникальному ключу
	 * ¬ продукт попадают только те виды, которые доступны на текущую и более поздние даты
	 * @param id - уникальный ключ
	 * @return - продукт
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
	 * Ќайти продукт по уникальному ключу
	 * ¬ продукт попадают только те виды, которые доступны на текущую и более поздние даты
	 * @param id - уникальный ключ
	 * @return - продукт
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
	 * —охранить продукт
	 * @param product - продукт
	 * @throws BusinessException
	 */
	public void save(final CardProduct product) throws BusinessException
	{
		simpleService.addOrUpdate(product);
	}

	/**
	 * ”далить продукт
	 * @param product - продукт
	 * @throws BusinessException
	 */
	public void remove(final CardProduct product) throws BusinessException
	{
		simpleService.remove(product);
	}

	/**
	 * ѕолучить список активных продуктов виртуальных карт, т.е. виртаульные карты, доступный онлайн и с датой закрыти€ вклада больше либо равной текущей
	 * @return  - список активых виртуальных карт, либо null
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
	 * –азбор списка объектов из Ѕƒ: полна€ верси€ карточного продукта и только подход€щие им виды
	 * @return собранный полный список объектов CardProduct
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
	 * ќбновить поле " ћаксимальна€ дата закрыти€ вида карточного продукта" всех карточных продуктов, у которых был изменен хот€ бы один вид карточного продута
	 */
	public void updateStopOpenDateAllCardProducts() throws BusinessException
	{
		try
		{
			List<CardProduct> cardProducts = getModificationsCardProducts();  // продукты, у которых дата модификации текуща€

			if (cardProducts == null)
			    return;

			List<CardProduct> updateCardProducts = new ArrayList<CardProduct>(); // список подготовленных к обновлению карточных продуктов

			for(CardProduct cardProduct : cardProducts)
			{
			    Calendar maxDateCalendar = null;

				// поиск максимальной даты прекращени€ открыти€ вкладов в видах продуктов
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
	 * список подготовленных к обновлению карточных продуктов
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
	 * ѕоиск активных карточных продуктов
	 * @param type - тип продукта
	 * @param online - признак активности
	 * @param date - дата активности
	 * @return - список активных продуктов
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
