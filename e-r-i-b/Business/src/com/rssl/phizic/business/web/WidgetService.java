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
	 * Возвращает список персонализированных виджет-контейнеров клиента
	 * ("персонализированные" означает настроенные клиентом)
	 * @param profile - профиль клиента
	 * @param definitions - виджет-дефиниции, доступные пользователю
	 * @return список персонализированных виджет-контейнеров
	 */
	public List<WebPage> getClientPersonalWebPages(Profile profile, Map<String, WidgetDefinition> definitions) throws BusinessException
	{
		ClientWidgetsLoader loader = new ClientWidgetsLoader(profile, definitions);
		return loader.load();
	}

	/**
	 * Возвращает список предопределённых виджет-контейнеров клиента
	 * ("предопределённые" означает доступные клиенту изначально со стандартным набором виджетов)
	 * @param profile - профиль клиента
	 * @param definitions - виджет-дефиниции, доступные пользователю
	 * @return список предопределённых виджет-контейнеров
	 */
	public List<WebPage> getClientDefaultWebPages(Profile profile, Map<String, WidgetDefinition> definitions) throws BusinessException, BusinessLogicException
	{
		// 1. Получаем набор предопределённых страниц
		List<WebPage> defaultPages;
		try
		{
			String pagesString = ResourceHelper.loadResourceAsString(DEFAULT_PAGES_RESOURCE_NAME);
			defaultPages = widgetSerializer.deserializeWebPageList(pagesString, definitions);
		}
		catch (IOException e)
		{
			throw new BusinessException("Ошибка чтения " + DEFAULT_PAGES_RESOURCE_NAME, e);
		}

		// 2. Персонализируем страницы
		List<WebPage> personalPages = new ArrayList<WebPage>(defaultPages.size());
		for (WebPage defaultPage : defaultPages)
			personalPages.add(personalizePage(defaultPage, profile));

		return personalPages;
	}

	/**
	 * Сохраняет виджет-контейнер
	 * @param webPage - виджет-контейнер
	 */
	public void saveWebPage(WebPage webPage) throws BusinessException
	{
		WidgetsSaver saver = new WidgetsSaver(webPage);
		saver.save();
	}

	/**
	 * Удаляет персонализированные виджет-контейнеры клиента (вместе с виджетами)
	 * @param profile - профиль клиента
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
	 * Создаёт виджет
	 * @param codename - кодификатор виджета, пришедший из браузера
	 * @param definition - виджет-дефиниция нового виджета
	 * @return вновь созданный виджет
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
			throw new BusinessException("Сбой при попытке создать виджет класса " + definition.getClass(), e);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException("Сбой при попытке создать виджет класса " + definition.getClass(), e);
		}
	}

	/**
	 * Создаёт виджет по json-строке
	 * @param codename - кодификатор нового виджета (который сгенерирован в браузере)
	 * @param json - данные для нового виджета в формате json
	 * @param definition - дефиниция для нового виджета
	 * @return вновь созданный виджет
	 */
	public <TWidget extends Widget> TWidget createWidgetFromJson(String codename, String json, WidgetDefinition definition)
	{
		// noinspection unchecked
		TWidget widget = (TWidget) widgetSerializer.deserializeWidget(definition, json);
		widget.setCodename(codename);
		return widget;
	}

	/**
	 * Возвращает виджет в виде json-строки
	 * @param widget - виджет
	 * @return виджет в виде json-строки
	 */
	public String getWidgetAsJson(Widget widget)
	{
		return widgetSerializer.serializeWidget(widget);
	}

	/**
	 * Возвращает дефиницию в виде json-строки
	 * @param definition - дефиниция виджета
	 * @return дефиниция виджета в виде json-строки
	 */
	public String getDefinitionAsJson(WidgetDefinition definition)
	{
		return widgetSerializer.serializeDefinition(definition);
	}

	/**
	 * Создаёт полную копию виджет-контейнера
	 *
	 * Копия виджет-контейнера получает:
	 *  - идентификатор оригинала
	 *  - копию полей classname, location, layout
	 *  - копию коллекции виджетов
	 *  - профиль оригинала
	 * Копия каждого виджета получает:
	 *  - кодификатор оригинала
	 *  - виджет-дефиницию оригинала
	 *  - копию настроек (body)
	 *  - контейнер-копию
	 * 
	 * Метод используется:
	 *  для целей восстановления из резервной копии (backup)
	 *
	 * @param webPage - оригинал виджет-контейнера
	 * @return персонализированная копия виджет-контейнера
	 */
	public WebPage copyPage(WebPage webPage) throws BusinessException
	{
		return webPage.clone();
	}

	/**
	 * Создаёт персональную копию виджет-контейнера для указанного пользователя
	 *
	 * Копия виджет-контейнера получает:
	 *  - null в качестве идентификатора
	 *  - копию полей classname, location, layout
	 *  - копию коллекции виджетов
	 *  - указанный профиль
	 * Копия каждого виджета получает:
	 *  - новый кодификатор
	 *  - виджет-дефиницию оригинала
	 *  - копию настроек (body)
	 *  - контейнер-копию
	 * 
	 * @param webPage - оригинал виджет-контейнера
	 * @param profile - профиль клиента
	 * @return персонализированная копия виджет-контейнера
	 */
	private WebPage personalizePage(WebPage webPage, Profile profile) throws BusinessException, BusinessLogicException
	{
		// 1. Клонируем контейнер полностью
		WebPage webPageCopy = webPage.clone();

		// 2. Сбрасываем id и переопределяем профиль у копии
		webPageCopy.setId(null);
		webPageCopy.setProfile(profile);

		// 3. Вынимаем из контейнера все виджеты для последующего переопределения их кодификаторов
		Collection<Widget> widgets = webPageCopy.removeWidgets();

		// 4. Инициализируем виджеты
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

		// 5. Обновляем массив с раскладкой виджетов
		webPageCopy.setLayout(layout);

		return webPageCopy;
	}

	private String generateWidgetCodename(Long loginId, WidgetDefinition definition) throws BusinessException
	{
		// При изменении алогритма см. widget/WidgetContainer.js generateWidgetCodename()

		// NOTE: Ограничение на кодификатор виджета 80 символов
		// Сейчас сюда уже входит codename виджет-дефиниции (25), login_id (20), текущее время (20) и 1 разделитель
		// Пояснение: 64-битный лонг записывается максимум 20 символами из диапазона 0-9 
		String codename = definition.getCodename();
		Long widgetId = (new Date()).getTime();
		return String.format("%s%d_%d", codename, loginId, widgetId);
	}
}
