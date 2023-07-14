package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import org.hibernate.Session;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с идентификаторами корзины
 *
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class BasketIdentifierService
{
	private static final SimpleService service = new SimpleService();

	private static class HA implements HibernateAction<BasketIndetifierType>
	{
		private final DocumentType type;

		private HA(DocumentType type)
		{
			this.type = type;
		}

		public BasketIndetifierType run(Session session) throws Exception
		{
			return (BasketIndetifierType) session.getNamedQuery("com.rssl.phizic.business.dictionaries.basketident.findBySystemId")
					.setParameter("systemId", type)
					.uniqueResult();
		}
	}

	private static final Cache<String, Map<String, BasketIndetifierType>> identifiers = new Cache<String, Map<String, BasketIndetifierType>>(
			new OnCacheOutOfDateListener<String, Map<String, BasketIndetifierType>>()
	{
		public Map<String, BasketIndetifierType> onRefresh(final String key)
		{
			try
			{
				Map<String, BasketIndetifierType> map = new HashMap<String, BasketIndetifierType>();

				for (DocumentType t : DocumentType.values())
				{
					BasketIndetifierType bit = HibernateExecutor.getInstance().execute(new HA(t));
					if (bit != null)
						map.put(t.name(), bit);
				}
				return map;
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}, 15L);

	/**
	 * Получение информации о типе документа.
	 *
	 * @return информация по отображению.
	 * @throws BusinessException
	 */
	public Map<String, BasketIndetifierType> getIdentifierMap() throws BusinessException
	{
		return Collections.unmodifiableMap(identifiers.getValue(""));
	}

	public List<BasketIndetifierType> getIdentifiers() throws BusinessException
	{
		return service.getAll(BasketIndetifierType.class);
	}

	public BasketIndetifierType getInedtifier(long id) throws BusinessException
	{
		return service.findById(BasketIndetifierType.class, id);
	}

	public AttributeForBasketIdentType getAttribute(long id) throws BusinessException
	{
		return service.findById(AttributeForBasketIdentType.class, id);
	}

	public void removeIdent(long id) throws BusinessException
	{
		BasketIndetifierType tp = service.findById(BasketIndetifierType.class, id);
		if (tp != null)
		{
			service.remove(tp);
		}
	}

	public void removeAttribute(long id) throws BusinessException
	{
		AttributeForBasketIdentType attr = service.findById(AttributeForBasketIdentType.class, id);
		if (attr != null)
		{
			service.remove(attr);
		}
	}

	public void addOrUpdate(final BasketIndetifierType identifier) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					service.addOrUpdate(identifier);
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
