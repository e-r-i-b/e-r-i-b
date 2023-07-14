package com.rssl.phizic.business.web;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

import java.util.*;

/**
 * @author Erkin
 * @ created 17.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контейнер виджетов
 */
@ThreadSafe
public class WebPage extends WebPageBean implements Cloneable
{
	private Map<String, Widget> widgets = new LinkedHashMap<String, Widget>();

	///////////////////////////////////////////////////////////////////////////

	WebPage() {}

	/**
	 * Возвращает все виджеты в контейнере
	 * @return коллекция виджетов в контейнере
	 */
	public synchronized Collection<Widget> getWidgets()
	{
		return Collections.unmodifiableCollection(widgets.values());
	}

	/**
	 * Возвращает виджет по его кодификатору
	 * @param codename - кодификатор виджета, напр., FaceBook12321091
	 * @return виджет или null, если в контейнере нет виджета с таким кодификатором
	 */
	public synchronized Widget getWidget(String codename)
	{
		return widgets.get(codename);
	}

	/**
	 * @param widget
	 * @return true, если виджет есть в контейнере
	 */
	public synchronized boolean containsWidget(Widget widget)
	{
		return widget.getContainer() == this;
	}

	/**
	 * Помещает виджет в контейнер
	 * @param widget - виджет
	 * @throws IllegalStateException виджет уже добавлен в другой контейнер,
	 *  либо в данном контейнере уже есть виджет с аналогичным кодификатором
	 */
	public synchronized void addWidget(Widget widget) throws IllegalStateException
	{
		String widgetCodename = widget.getCodename();

		if (widget.getContainer() != null)
			throw new IllegalStateException("Нельзя поместить виджет " + widgetCodename + " сразу в 2 контейнера");

		if (widgets.containsKey(widgetCodename))
			throw new IllegalStateException("В контейнере " + getClassname() + " уже есть виджет " + widgetCodename);

		widgets.put(widgetCodename, widget);
		widget.setContainer(this);
	}

	/**
	 * Удаляет виджет из контейнера
	 * @param widget - виджет
	 * @throws IllegalStateException в контейнере нет указанного виджета
	 */
	public synchronized void removeWidget(Widget widget) throws IllegalStateException
	{
		String widgetCodename = widget.getCodename();

		if (this != widget.getContainer())
			throw new IllegalStateException("Виджет " + widgetCodename + " не принадлежит контейнеру " + getClassname());

		widgets.remove(widgetCodename);
		widget.setContainer(null);
	}

	/**
	 * Удаляет все виджеты из контейнера
	 * @return удалённые виджеты
	 */
	synchronized Collection<Widget> removeWidgets()
	{
		List<Widget> widgetList = new ArrayList<Widget>(widgets.values());
		widgets.clear();
		for (Widget widget : widgetList)
			widget.setContainer(null);
		return widgetList;
	}

	/**
	 * Возвращает количество виджетов заданного типа
	 * @param definitionCodename - кодификатор дефиниции (в роли типа виджета)
	 * @return количество виджетов заданного типа
	 */
	public synchronized int countWidgets(String definitionCodename)
	{
		int count = 0;
		for (Widget widget : widgets.values()) {
			WidgetDefinition definition = widget.getDefinition();
			if (definitionCodename.equals(definition.getCodename()))
				count++;
		}
		return count;
	}

	///////////////////////////////////////////////////////////////////////////

	protected synchronized WebPage clone()
	{
		try
		{
			// Полное копирование: id, classname
			WebPage newPage = (WebPage) super.clone();
			newPage.widgets = new LinkedHashMap<String, Widget>();

			for (Widget widget : widgets.values())
				newPage.addWidget(widget.clone());

			return newPage;
		}
		catch (CloneNotSupportedException e)
		{
			// TODO: (виджеты) Уточнить класс runtime-исключения
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized boolean equals(Object o)
	{
		if (!super.equals(o))
			return false;

		if (getClass() != o.getClass())
			return false;

		WebPage webPage = (WebPage) o;

		return widgets.equals(webPage.widgets);
	}
}
