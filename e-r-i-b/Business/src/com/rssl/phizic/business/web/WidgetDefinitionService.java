package com.rssl.phizic.business.web;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * User: moshenko
 * Date: 03.12.2012
 * Time: 21:12:12
 * Сервис для работы с widgetDefinition
 */
public class WidgetDefinitionService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * Добавление или обновление widgetDefinition.
	 * @param definition widgetDefinition
	 * @return widgetDefinition
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public WidgetDefinition addOrUpdate(WidgetDefinition definition) throws BusinessException
	{
		return service.addOrUpdate(definition);
	}

	/**
	 * Удаление widgetDefinition.
	 * @param definition widgetDefinition
	 * @throws BusinessException
	 */
	public void remove(WidgetDefinition definition) throws BusinessException
	{
		service.remove(definition);
	}

	/**
	 * Удаление все записи widgetDefinition.
	 * @throws BusinessException
	 */
	public void removeAll() throws BusinessException
	{
		try
	    {
		   HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		   {
			   public Void run(Session session) throws Exception
			   {
				   Query query = session.getNamedQuery("com.rssl.phizic.business.web.WidgetDefinition.removeAll");
				   query.executeUpdate();
				   return null;
			   }
		   });
	    }
	    catch(Exception ex)
	    {
			throw new BusinessException("Ошибка при удалении отпраленных выписок", ex);
	    }
	}

	/**
	 * добавить или обновить список widgetDefinition.
	 * @param definitionList список widgetDefinition
	 * @return  список widgetDefinition
	 * @throws BusinessException
	 */
	public List<WidgetDefinition> addOrUpdateList(List<WidgetDefinition> definitionList) throws BusinessException
	{
		return service.addOrUpdateList(definitionList);
	}

	/**
	 * получить все widgetDefinition
	 * @return все widgetDefinition
	 * @throws BusinessException
	 */
	public List<WidgetDefinition> getAll() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WidgetDefinition.class);
		return service.find(criteria);
	}

	/**
	 * получить все доступные widgetDefinition
	 * @return все widgetDefinition
	 * @throws BusinessException
	 */
	public List<WidgetDefinition> getAllAvailable() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WidgetDefinition.class);
		criteria.add(Expression.eq("availability",true));
		return service.find(criteria);
	}
}
