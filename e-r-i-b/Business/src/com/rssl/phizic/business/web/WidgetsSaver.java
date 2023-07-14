package com.rssl.phizic.business.web;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сохранятор виджетов
 */
class WidgetsSaver
{
	private static final WidgetSerializer serializer = new WidgetSerializer();

	private final WebPage container;

	///////////////////////////////////////////////////////////////////////////

	WidgetsSaver(WebPage container)
	{
		this.container = container;
	}

	void save() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					boolean isNewContainer = (container.getId() == null);

					// (1) Удаляем старые виджеты в контейнере
					if (!isNewContainer) {
						Query query = session.getNamedQuery("com.rssl.phizic.business.web.WidgetBean.removeByWebPageId");
						query.setParameterList("webPageIds", Collections.singleton(container.getId()));
						query.executeUpdate();
					}

					// 2. Сохраняем контейнер
					session.saveOrUpdate(container);

					// 3. Сохраняем новые виджеты в контейнере
					List<WidgetBean> beans = getWidgetBeans();
					for (WidgetBean bean : beans)
						session.save(bean);

					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private List<WidgetBean> getWidgetBeans() throws BusinessException
	{
		Collection<Widget> widgets = container.getWidgets();
		List<WidgetBean> beans = new ArrayList<WidgetBean>(widgets.size());
		for (Widget widget : widgets)
			beans.add(createWidgetBean(widget));
		return beans;
	}

	private WidgetBean createWidgetBean(Widget widget) throws BusinessException
	{
		WidgetDefinition definition = widget.getDefinition();
		String body = serializer.serializeWidget(widget);

		WidgetBean bean = new WidgetBean();
		bean.setCodename(widget.getCodename());
		bean.setBody(body);
		bean.setDefinitionCodename(definition.getCodename());
		bean.setWebPageId(container.getId());

		return bean;
	}
}
