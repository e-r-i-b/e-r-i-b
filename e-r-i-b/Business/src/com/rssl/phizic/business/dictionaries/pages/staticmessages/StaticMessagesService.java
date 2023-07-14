package com.rssl.phizic.business.dictionaries.pages.staticmessages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.PropertyConfig;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class StaticMessagesService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Поиск сообщения по ключу
	 * @param key - ключ сообщения
	 * @return сообщение
	 */
	public StaticMessage findByKey(String key) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(StaticMessage.class).add(Expression.eq("key", key));
		return simpleService.findSingle(criteria);
	}

	/**
	 * Добавить сообщение
	 * @param staticMessage сообщение
	 * @return сообщение
	 */
	public StaticMessage addOrUpdate(StaticMessage staticMessage) throws BusinessException
	{
		return simpleService.addOrUpdate(staticMessage);
	}

	/**
	 * Добавить связь сообщения и картинки.
	 * @param imageMessage связка
	 * @return связка
	 */
	public ImageMessage add(ImageMessage imageMessage) throws BusinessException
	{
		return simpleService.add(imageMessage);
	}

	/**
	 * Возвращает все привязанные к сообщению картинки
	 * @param messageKey - ключ сообщения
	 * @return список катинок
	 */
	public Map<Long, Image> getAttachedImages(final String messageKey) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Map<Long, Image>>()
		    {
		        public Map<Long, Image> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(StaticMessage.class.getName() + ".fingAttachedImages");
			        query.setParameter("messageKey", messageKey);

		            List<Image> images = query.list();
			        Map<Long, Image> result = new HashMap<Long, Image>();
			        for (Image image : images)
				        result.put(image.getId(), image);

			        return result;
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
