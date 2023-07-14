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
	 * ������������� �������� ������ �������
	 * @param codename - ����������� ������� �� ��������
	 * @param defname - ����������� ������-���������
	 * @param contname - ����������� ������-����������
	 */
	public final void initializeNew(String codename, String defname, String contname) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("�� ����� ����������� �������");

		if (StringHelper.isEmpty(defname))
			throw new BusinessException("�� ����� ����������� ������-���������");

		if (StringHelper.isEmpty(contname))
			throw new BusinessException("�� ����� ����������� ������-����������");
		
		personData = PersonContext.getPersonDataProvider().getPersonData();

		WidgetDefinition definition = personData.getWidgetDefinition(defname);
		if (definition == null)
			throw new NotFoundException("�� ������� ������-��������� " + defname);

		container = personData.getPage(contname);
		if (container == null)
			throw new NotFoundException("�� ������ ������-��������� " + contname);

		if (getCountWidgetsByCodename(definition.getCodename()) >= definition.getMaxCount())
			throw new BusinessLogicException(definition.getMaxCountMessage());

		widget = createWidget(codename, definition);

		oldWidget = widget;
		oldContainer = container;

		initialize();
		update();
	}


	/**
	 * ������������� ��������
	 * @param codename - ����������� �������
	 */
	public final void initialize(String codename) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("�� ����� ����������� �������");

		personData = PersonContext.getPersonDataProvider().getPersonData();

		widget = (TWidget) personData.getWidget(codename);
		if(widget == null)
			throw new NotFoundException("�� ������ ������ " + codename);
		container = widget.getContainer();

		oldWidget = widget;
		oldContainer = container;

		initialize();

		update();
	}

	/**
	 * ������������� � ������ ������������� ������ ������� � ������� ��� �������� �������
	 * @param codename �����������
	 * @throws BusinessException
	 */
	public final void initForbiddenWidget(String codename) throws BusinessException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("�� ����� ����������� �������");

		personData = PersonContext.getPersonDataProvider().getPersonData();
		widget = (TWidget) personData.getWidget(codename);
	}

	/**
	 * ������������� � ������ ������������� ������ ������� ��� ���������� ������ �������
	 * @param codename - ����������� ������� �� ��������
	 * @param defname - ����������� ������-���������
	 * @param contname - ����������� ������-����������
	 * @throws BusinessException
	 */
	public final void initAddForbiddenWidget(String codename, String defname, String contname) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("�� ����� ����������� �������");

		if (StringHelper.isEmpty(defname))
			throw new BusinessException("�� ����� ����������� ������-���������");

		if (StringHelper.isEmpty(contname))
			throw new BusinessException("�� ����� ����������� ������-����������");

		personData = PersonContext.getPersonDataProvider().getPersonData();

		WidgetDefinition definition = personData.getWidgetDefinition(defname);
		if (definition == null)
			throw new NotFoundException("�� ������� ������-��������� " + defname);

		widget = createWidget(codename, definition);
		container = personData.getPage(contname);
		if (container == null)
			throw new NotFoundException("�� ������ ������-��������� " + contname);
	}

	/**
	 * ����� ����� �������������
	 */
	protected void initialize() throws BusinessException, BusinessLogicException
	{
	}

	/**
	 * ��������������� ���������� ��������� ��������
	 * ��������, � ����� � ���, ��� ��������� ������
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
	 * ��������� �������
	 * @param widgetAsJson JSON-�������������� ������
	 */
	public void setWidget(String widgetAsJson)
	{
		widget = widgetService.<TWidget>createWidgetFromJson(widget.getCodename(), widgetAsJson, widget.getDefinition());
	}

	public void setContainer(String contname) throws BusinessException
	{
		WebPage container = personData.getPage(contname);
		if (container == null)
			throw new BusinessException("�� ������ ������-��������� " + contname);
		this.container = container;
	}

	/**
	 * ��������� ������
	 */
	public void save() throws BusinessException
	{
		// (1) ������� ������ ������ �� ������� ����������
		if (oldContainer.containsWidget(oldWidget))
			oldContainer.removeWidget(oldWidget);

		// 2. ��������� ����� ������ � ����� ���������
		container.addWidget(widget);
	}

	/**
	 * ������� ������
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
	 * @return ��������������� ������
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
	 * �������� �� ������, ��������� �� ������
	 * @return true, ���� ������ ���������
	 */
	public boolean isWidgetChanged()
	{
 		Set<String> changedFields = widget.compareFields(oldWidget);
		changedFields.remove("size");
		return (!changedFields.isEmpty());
	}

	/**
	 * �������� �� ������, ����� �� ������������� ������ �� �������
	 * @return true, ���� ������ ������ ������������� ������
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
	 * ���������� ���������� ��� ��������� � ������� �������� ��������� ���� (codename �� definition)
	 * @param codename - ���
	 * @return ���������� ��������
	 */
	private int getCountWidgetsByCodename(String codename)
	{
		int count = 0;
		for (WebPage webPage : personData.getPages().values())
			count += webPage.countWidgets(codename);
		return count;
	}

	/**
	 * ��������� ������� (������������ ������� �� ������)
	 * @param stop - ���� true - ���������� �������, ���� false - ������
	 */
	public void stopBlinking(Boolean stop)
	{
	}
}
