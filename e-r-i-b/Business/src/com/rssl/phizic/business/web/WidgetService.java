package com.rssl.phizic.business.web;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.web.widget.strategy.WidgetAccessor;
import com.rssl.phizic.business.web.widget.strategy.WidgetInitializer;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.IOException;
import java.util.*;

/**
 * @author Erkin
 * @ created 09.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class WidgetService
{
	private static final String DEFAULT_PAGES_RESOURCE_NAME = "default_pages.cfg.json";

	private static final WidgetSerializer widgetSerializer = new WidgetSerializer();
	
	/**
	 * ���������� ������ ������������������� ������-����������� �������
	 * ("�������������������" �������� ����������� ��������)
	 * @param profile - ������� �������
	 * @param definitions - ������-���������, ��������� ������������
	 * @return ������ ������������������� ������-�����������
	 */
	public List<WebPage> getClientPersonalWebPages(Profile profile, Map<String, WidgetDefinition> definitions) throws BusinessException
	{
		ClientWidgetsLoader loader = new ClientWidgetsLoader(profile, definitions);
		return loader.load();
	}

	/**
	 * ���������� ������ ��������������� ������-����������� �������
	 * ("���������������" �������� ��������� ������� ���������� �� ����������� ������� ��������)
	 * @param profile - ������� �������
	 * @param definitions - ������-���������, ��������� ������������
	 * @return ������ ��������������� ������-�����������
	 */
	public List<WebPage> getClientDefaultWebPages(Profile profile, Map<String, WidgetDefinition> definitions) throws BusinessException, BusinessLogicException
	{
		// 1. �������� ����� ��������������� �������
		List<WebPage> defaultPages;
		try
		{
			String pagesString = ResourceHelper.loadResourceAsString(DEFAULT_PAGES_RESOURCE_NAME);
			defaultPages = widgetSerializer.deserializeWebPageList(pagesString, definitions);
		}
		catch (IOException e)
		{
			throw new BusinessException("������ ������ " + DEFAULT_PAGES_RESOURCE_NAME, e);
		}

		// 2. ��������������� ��������
		List<WebPage> personalPages = new ArrayList<WebPage>(defaultPages.size());
		for (WebPage defaultPage : defaultPages)
			personalPages.add(personalizePage(defaultPage, profile));

		return personalPages;
	}

	/**
	 * ��������� ������-���������
	 * @param webPage - ������-���������
	 */
	public void saveWebPage(WebPage webPage) throws BusinessException
	{
		WidgetsSaver saver = new WidgetsSaver(webPage);
		saver.save();
	}

	/**
	 * ������� ������������������� ������-���������� ������� (������ � ���������)
	 * @param profile - ������� �������
	 */
	public void removeClientPersonalWebPages(final Profile profile) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.web.WebPage.removeByProfile");
					query.setParameter("profile", profile);
					query.executeUpdate();
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
	 * ������ ������
	 * @param codename - ����������� �������, ��������� �� ��������
	 * @param definition - ������-��������� ������ �������
	 * @return ����� ��������� ������
	 */
	public Widget createWidget(String codename, WidgetDefinition definition) throws BusinessException, BusinessLogicException
	{
		try
		{
			Class<? extends Widget> widgetClass = definition.getWidgetClass();
			WidgetInitializer initializer = definition.getInitializer();

			Widget newWidget = widgetClass.newInstance();
			newWidget.setCodename(codename);
			newWidget.setDefinition(definition);
			newWidget.setTitle(definition.getUsername());
			newWidget.setSize(definition.getInitialSize());
			newWidget.setRollUp(false);

			//noinspection unchecked
			initializer.init(newWidget);

			return newWidget;
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException("���� ��� ������� ������� ������ ������ " + definition.getClass(), e);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException("���� ��� ������� ������� ������ ������ " + definition.getClass(), e);
		}
	}

	/**
	 * ������ ������ �� json-������
	 * @param codename - ����������� ������ ������� (������� ������������ � ��������)
	 * @param json - ������ ��� ������ ������� � ������� json
	 * @param definition - ��������� ��� ������ �������
	 * @return ����� ��������� ������
	 */
	public <TWidget extends Widget> TWidget createWidgetFromJson(String codename, String json, WidgetDefinition definition)
	{
		// noinspection unchecked
		TWidget widget = (TWidget) widgetSerializer.deserializeWidget(definition, json);
		widget.setCodename(codename);
		return widget;
	}

	/**
	 * ���������� ������ � ���� json-������
	 * @param widget - ������
	 * @return ������ � ���� json-������
	 */
	public String getWidgetAsJson(Widget widget)
	{
		return widgetSerializer.serializeWidget(widget);
	}

	/**
	 * ���������� ��������� � ���� json-������
	 * @param definition - ��������� �������
	 * @return ��������� ������� � ���� json-������
	 */
	public String getDefinitionAsJson(WidgetDefinition definition)
	{
		return widgetSerializer.serializeDefinition(definition);
	}

	/**
	 * ������ ������ ����� ������-����������
	 *
	 * ����� ������-���������� ��������:
	 *  - ������������� ���������
	 *  - ����� ����� classname, location, layout
	 *  - ����� ��������� ��������
	 *  - ������� ���������
	 * ����� ������� ������� ��������:
	 *  - ����������� ���������
	 *  - ������-��������� ���������
	 *  - ����� �������� (body)
	 *  - ���������-�����
	 * 
	 * ����� ������������:
	 *  ��� ����� �������������� �� ��������� ����� (backup)
	 *
	 * @param webPage - �������� ������-����������
	 * @return ������������������� ����� ������-����������
	 */
	public WebPage copyPage(WebPage webPage) throws BusinessException
	{
		return webPage.clone();
	}

	/**
	 * ������ ������������ ����� ������-���������� ��� ���������� ������������
	 *
	 * ����� ������-���������� ��������:
	 *  - null � �������� ��������������
	 *  - ����� ����� classname, location, layout
	 *  - ����� ��������� ��������
	 *  - ��������� �������
	 * ����� ������� ������� ��������:
	 *  - ����� �����������
	 *  - ������-��������� ���������
	 *  - ����� �������� (body)
	 *  - ���������-�����
	 * 
	 * @param webPage - �������� ������-����������
	 * @param profile - ������� �������
	 * @return ������������������� ����� ������-����������
	 */
	private WebPage personalizePage(WebPage webPage, Profile profile) throws BusinessException, BusinessLogicException
	{
		// 1. ��������� ��������� ���������
		WebPage webPageCopy = webPage.clone();

		// 2. ���������� id � �������������� ������� � �����
		webPageCopy.setId(null);
		webPageCopy.setProfile(profile);

		// 3. �������� �� ���������� ��� ������� ��� ������������ ��������������� �� �������������
		Collection<Widget> widgets = webPageCopy.removeWidgets();

		// 4. �������������� �������
		Layout layout = webPageCopy.getLayout();
		for (Widget widget : widgets)
		{
			String originCodename = widget.getCodename();
			WidgetDefinition definition = widget.getDefinition();
			WidgetAccessor accessor = definition.getAccessor();
			WidgetInitializer initializer = definition.getInitializer();

			if (accessor.access(definition))
			{
				String personalCodename = generateWidgetCodename(profile.getLoginId(), definition);
				widget.setCodename(personalCodename);
				//noinspection unchecked
				initializer.init(widget);
				webPageCopy.addWidget(widget);

				layout = layout.renameWidget(originCodename, personalCodename);
			}
			else
			{
				layout = layout.removeWidget(originCodename);
			}
		}

		// 5. ��������� ������ � ���������� ��������
		webPageCopy.setLayout(layout);

		return webPageCopy;
	}

	private String generateWidgetCodename(Long loginId, WidgetDefinition definition) throws BusinessException
	{
		// ��� ��������� ��������� ��. widget/WidgetContainer.js generateWidgetCodename()

		// NOTE: ����������� �� ����������� ������� 80 ��������
		// ������ ���� ��� ������ codename ������-��������� (25), login_id (20), ������� ����� (20) � 1 �����������
		// ���������: 64-������ ���� ������������ �������� 20 ��������� �� ��������� 0-9 
		String codename = definition.getCodename();
		Long widgetId = (new Date()).getTime();
		return String.format("%s%d_%d", codename, loginId, widgetId);
	}
}
