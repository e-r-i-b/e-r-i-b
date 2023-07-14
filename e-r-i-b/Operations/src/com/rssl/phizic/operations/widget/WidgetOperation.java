package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.web.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Set;

/**
 * @author Barinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"LocalVariableHidesMemberVariable"})
public abstract class WidgetOperation<TWidget extends Widget> extends OperationBase
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final WidgetService widgetService = new WidgetService();

	private PersonData personData;

	private TWidget oldWidget;

	private WebPage oldContainer;

	private TWidget widget;

	private WebPage container;

	private boolean isUseStoredResource;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация создания нового виджета
	 * @param codename - кодификатор виджета из браузера
	 * @param defname - кодификатор виджет-дефиниции
	 * @param contname - кодификатор виджет-контейнера
	 */
	public final void initializeNew(String codename, String defname, String contname) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("Не задан кодификатор виджета");

		if (StringHelper.isEmpty(defname))
			throw new BusinessException("Не задан кодификатор виджет-дефиниции");

		if (StringHelper.isEmpty(contname))
			throw new BusinessException("Не задан кодификатор виджет-контейнера");
		
		personData = PersonContext.getPersonDataProvider().getPersonData();

		WidgetDefinition definition = personData.getWidgetDefinition(defname);
		if (definition == null)
			throw new NotFoundException("Не найдена виджет-дефиниция " + defname);

		container = personData.getPage(contname);
		if (container == null)
			throw new NotFoundException("Не найден виджет-контейнер " + contname);

		if (getCountWidgetsByCodename(definition.getCodename()) >= definition.getMaxCount())
			throw new BusinessLogicException(definition.getMaxCountMessage());

		widget = createWidget(codename, definition);

		oldWidget = widget;
		oldContainer = container;

		initialize();
		update();
	}


	/**
	 * инициализация операции
	 * @param codename - кодификатор виджета
	 */
	public final void initialize(String codename) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("Не задан кодификатор виджета");

		personData = PersonContext.getPersonDataProvider().getPersonData();

		widget = (TWidget) personData.getWidget(codename);
		if(widget == null)
			throw new NotFoundException("Не найден виджет " + codename);
		container = widget.getContainer();

		oldWidget = widget;
		oldContainer = container;

		initialize();

		update();
	}

	/**
	 * Инициализация в случае возникновения ошибки доступа к виджету при загрузке виджета
	 * @param codename кодификатор
	 * @throws BusinessException
	 */
	public final void initForbiddenWidget(String codename) throws BusinessException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("Не задан кодификатор виджета");

		personData = PersonContext.getPersonDataProvider().getPersonData();
		widget = (TWidget) personData.getWidget(codename);
	}

	/**
	 * Инициализация в случае возникновения ошибки доступа при добавлении нового виджета
	 * @param codename - кодификатор виджета из браузера
	 * @param defname - кодификатор виджет-дефиниции
	 * @param contname - кодификатор виджет-контейнера
	 * @throws BusinessException
	 */
	public final void initAddForbiddenWidget(String codename, String defname, String contname) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("Не задан кодификатор виджета");

		if (StringHelper.isEmpty(defname))
			throw new BusinessException("Не задан кодификатор виджет-дефиниции");

		if (StringHelper.isEmpty(contname))
			throw new BusinessException("Не задан кодификатор виджет-контейнера");

		personData = PersonContext.getPersonDataProvider().getPersonData();

		WidgetDefinition definition = personData.getWidgetDefinition(defname);
		if (definition == null)
			throw new NotFoundException("Не найдена виджет-дефиниция " + defname);

		widget = createWidget(codename, definition);
		container = personData.getPage(contname);
		if (container == null)
			throw new NotFoundException("Не найден виджет-контейнер " + contname);
	}

	/**
	 * Общая часть инициализации
	 */
	protected void initialize() throws BusinessException, BusinessLogicException
	{
	}

	/**
	 * Актуализировать переменные состояния операции
	 * Например, в связи с тем, что поменялся виджет
	 */
	public void update()  throws BusinessException
	{
	}

	private TWidget createWidget(String codename, WidgetDefinition definition) throws BusinessException, BusinessLogicException
	{
		// noinspection unchecked
		return (TWidget) widgetService.createWidget(codename, definition);
	}
	
	/**
	 * изменение виджета
	 * @param widgetAsJson JSON-серилазованный виджет
	 */
	public void setWidget(String widgetAsJson)
	{
		widget = widgetService.<TWidget>createWidgetFromJson(widget.getCodename(), widgetAsJson, widget.getDefinition());
	}

	public void setContainer(String contname) throws BusinessException
	{
		WebPage container = personData.getPage(contname);
		if (container == null)
			throw new BusinessException("Не найден виджет-контейнер " + contname);
		this.container = container;
	}

	/**
	 * сохранить виджет
	 */
	public void save() throws BusinessException
	{
		// (1) Убираем старый виджет из старого контейнера
		if (oldContainer.containsWidget(oldWidget))
			oldContainer.removeWidget(oldWidget);

		// 2. Добавляем новый виджет в новый контейнер
		container.addWidget(widget);
	}

	/**
	 * удалить виджет
	 */
	public void remove() throws BusinessException
	{
		container.removeWidget(widget);
	}

	protected PersonData getPersonData()
	{
		return personData;
	}

	/**
	 * @return сериализованный виджет
	 */
	public String getSerializedWidget()
	{
		return widgetService.getWidgetAsJson(widget);
	}

	public TWidget getWidget()
	{
		return widget;
	}

	public TWidget getOldWidget()
	{
		return oldWidget;
	}

	/**
	 * Отвечает на вопрос, изменился ли виджет
	 * @return true, если виджет изменился
	 */
	public boolean isWidgetChanged()
	{
 		Set<String> changedFields = widget.compareFields(oldWidget);
		changedFields.remove("size");
		return (!changedFields.isEmpty());
	}

	/**
	 * Отвечает на вопрос, нужно ли перезагружать виджет на клиенте
	 * @return true, если клиент должен перезагружить виджет
	 */
	public boolean shouldReloadClient()
	{
		return isWidgetChanged();
	}

	protected void setUseStoredResource(boolean isUseStoredResource)
	{
		this.isUseStoredResource = isUseStoredResource;
	}

	public boolean checkUseStoredResource()
	{   
		return isUseStoredResource;
	}

	/**
	 * Возвращает количество уже имеющихся у клиента виджетов заданного типа (codename из definition)
	 * @param codename - код
	 * @return количество виджетов
	 */
	private int getCountWidgetsByCodename(String codename)
	{
		int count = 0;
		for (WebPage webPage : personData.getPages().values())
			count += webPage.countWidgets(codename);
		return count;
	}

	/**
	 * Остановка мигания (пользователь кликнул на виджет)
	 * @param stop - если true - остановить мигание, если false - мигать
	 */
	public void stopBlinking(Boolean stop)
	{
	}
}
