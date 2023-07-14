package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceDepositProductService
{
	private static final MultiInstanceSimpleService multiInstanceSimpleService = new MultiInstanceSimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final String CURRENT = "current";

	/**
	 * @param instanceName - инстанс БД
	 * @return Список всех депозитных продуктов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<DepositProduct> getAllProducts(String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.getAll(DepositProduct.class,instanceName);
	}

	/**
	 * Список депозитных продуктов, название которых содержит nameFilter
	 * @param nameFilter название (часть названия) вклада
	 * @param instanceName - инстанс БД
	 * @return список DepositProduct
	 */
	public List<DepositProduct> getProductsByName(String nameFilter, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class);
		criteria.add(Expression.ilike("name", nameFilter, MatchMode.ANYWHERE));
		return multiInstanceSimpleService.find(criteria,instanceName);
	}

	/**
	 * Возвращает список депозитных продуктов, которые неразрешены для открытия в СБОЛ
	 * @param instanceName - инстанс БД
	 * @return список депозитных продуктов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<DepositProduct> getNotAvailableOnlineProducts(String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<DepositProduct>>()
			{
				public List<DepositProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getNotOnlineAvailable");
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
	 * Получение депозитного продукта по номеру вида
	 * @param productId - номер вида депозитного продукта
	 * @param instanceName - инстанс БД
	 * @return DepositProduct
	 */
	public DepositProduct findByProductId(final Long productId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositProduct>()
			{
				public DepositProduct run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getByProductId");
					query.setParameter("productId", productId);
					return (DepositProduct) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instanceName - инстанс БД
	 * @return общее описание для ДП
	 * @throws BusinessException
	 */
	public DepositGlobal getGlobal(String instanceName) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositGlobal>()
		    {
		        public DepositGlobal run(Session session) throws Exception
		        {
			        DepositGlobal global = (DepositGlobal)session.getNamedQuery("GetDepositGlobal").uniqueResult();
			        if( global==null )
			            throw new BusinessException("Не найдено глобальное описание депозитных продуктов.");
			        return global;
		        }
		    });
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}


	/**
	 * Установить общее описание депозитных продуктов.
	 * @param global - общее описание депозитных продуктов.
	 * @param instanceName - инстанс БД
	 * @throws BusinessException
	 */
	public void setGlobal(final DepositGlobal global, String instanceName) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        global.setKey(CURRENT);
			        session.saveOrUpdate(global);
			        dictionaryRecordChangeInfoService.addChangesToLog(session, global, ChangeType.update);
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
	 * Обновить продукт в БД
	 * @param product - продукт для обновления
	 * @param instanceName - инстанс БД
	 * @return обновленный продукт
	 * @throws BusinessException
	 */
	public DepositProduct update(final DepositProduct product, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DepositProduct>()
			{
				public DepositProduct run(Session session) throws Exception
				{
					session.saveOrUpdate(product);
					dictionaryRecordChangeInfoService.addChangesToLog(session, product, ChangeType.update);
					return product;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить депозитный продукт
	 * @param product продукт
	 * @param instanceName инстанс БД
	 */
	public void remove(final DepositProduct product, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(product);
					dictionaryRecordChangeInfoService.addChangesToLog(session, product, ChangeType.delete);
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
	 * @param productsForDelete мапа вклад - подвклады
	 * @param instanceName инстанс БД
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void removeDepositProducts(Map<Long, List<Long>> productsForDelete, String instanceName) throws BusinessException, BusinessLogicException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class);
		criteria.add(Expression.in("productId", productsForDelete.keySet()));
		List<DepositProduct> depositProducts = multiInstanceSimpleService.find(criteria,instanceName);

		for (DepositProduct depositProduct : depositProducts)
		{
			List<Long> subKindIds = productsForDelete.get(depositProduct.getProductId());

			if(depositProduct == null)
				throw new BusinessLogicException("Не найден вид вклада с id = " + depositProduct.getId());

			if (CollectionUtils.isEmpty(subKindIds))  // если указан только ай-дишник вида вклада
			{
				remove(depositProduct, instanceName); //удаляем его целиком, включая подвиды
				continue;
			}
			try
			{
				// Получаем DOM описание продукта
				Document document = XmlHelper.parse(depositProduct.getDescription());

				//удаляем из документа подвиды
				DepositProductHelper.removeSubKinds(document.getDocumentElement(), subKindIds);

				// уточняем название вклада на основе оставшихся данных
				DepositProductHelper.updateProductName(document.getDocumentElement());

				//возвращаем порезанный документ во вклад
				depositProduct.setDescription(XmlHelper.convertDomToText(document, "windows-1251"));
				//выставляем время последнего апдейта - текущее
				depositProduct.setLastUpdateDate(Calendar.getInstance());
				//и апдейтим
				update(depositProduct, instanceName);
			}
			catch (BusinessException e)
			{
				throw e;
			}
			catch(BusinessLogicException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}
	}

}
