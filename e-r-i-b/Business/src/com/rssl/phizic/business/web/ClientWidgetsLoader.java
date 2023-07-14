package com.rssl.phizic.business.web;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.EntityUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Erkin
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузчик виджетов клиента
 */
@NonThreadSafe
class ClientWidgetsLoader
{
	private static final WidgetSerializer serializer = new WidgetSerializer();

	private final Profile profile;

	private final Map<String, WidgetDefinition> definitions;

	private Map<Long, WebPage> containers;

	private List<WidgetBean> beans;

	///////////////////////////////////////////////////////////////////////////

	ClientWidgetsLoader(Profile profile, Map<String, WidgetDefinition> definitions)
	{
		this.profile = profile;
		this.definitions = new HashMap<String, WidgetDefinition>(definitions);
	}

	List<WebPage> load() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					// 1. Получаем виджет-контейнеры
					Query query = session.getNamedQuery("com.rssl.phizic.business.web.WebPage.getByProfile");
					query.setParameter("profile", profile);
					// noinspection unchecked
					List<WebPage> webPagesList = query.list();
					containers = EntityUtils.mapEntitiesById(webPagesList);

					// 2. Получаем бины виджетов
					if (!containers.isEmpty())
					{
						// noinspection ReuseOfLocalVariable
						query = session.getNamedQuery("com.rssl.phizic.business.web.WidgetBean.getByWebPageId");
						query.setParameterList("webPageIds", containers.keySet());
						// noinspection unchecked
						beans = query.list();
					}
					else beans = Collections.emptyList();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Сбой при загрузке виджет-контейнеров клиента", e);
		}

		// 4. Раскладываем виджеты по контейнерам
		buildWidgets();

		return new LinkedList<WebPage>(containers.values());
	}

	private void buildWidgets() throws BusinessException
	{
		for (WidgetBean bean : beans)
		{
			try
			{
				WidgetDefinition definition = getWidgetDefinition(bean);
				WebPage webPage = getWidgetContainer(bean);
				if (definition != null && webPage != null)
				{
					Widget widget = createWidget(bean, definition);
					webPage.addWidget(widget);
				}
			}
			catch (Exception e)
			{
				// TODO: (виджеты) создавать фиктивный виджет Исполнитель Еркин С
				throw new BusinessException("Ошибка при загрузке виджета ", e);
			}
		}
	}

	private Widget createWidget(WidgetBean bean, WidgetDefinition definition)
	{
		Widget widget = serializer.deserializeWidget(definition, bean.getBody());
		widget.setCodename(bean.getCodename());
		widget.setDefinition(definition);
		return widget;
	}

	private WidgetDefinition getWidgetDefinition(WidgetBean bean) throws BusinessException
	{
		String definitionCodename = bean.getDefinitionCodename();
		return definitions.get(definitionCodename);
	}

	private WebPage getWidgetContainer(WidgetBean bean) throws BusinessException
	{
		Long webPageId = bean.getWebPageId();
		return containers.get(webPageId);
	}
}
