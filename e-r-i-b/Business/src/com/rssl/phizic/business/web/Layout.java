package com.rssl.phizic.business.web;

import com.rssl.phizic.common.types.annotation.Immutable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Массив раскладки виджет-контейнера
 * Например: ["Weather1355050137571","CardBlock1"]
 */
@Immutable
public class Layout
{
	private static final WidgetSerializer serializer = new WidgetSerializer();

	/**
	 * Массив из строк и массивов строк
	 */
	private final List<Object> elements;

	///////////////////////////////////////////////////////////////////////////

	Layout(List<Object> elements)
	{
		this.elements = new ArrayList<Object>(elements);
	}

	/**
	 * Создаёт массив раскладки из строкового представления
	 * @param layoutAsString - раскладка в виде строки
	 * @return массив раскладки
	 */
	public static Layout valueOf(String layoutAsString) throws IllegalArgumentException
	{
		try
		{
			return serializer.deserializeLayout(layoutAsString);
		}
		catch (RuntimeException e)
		{
			throw new IllegalArgumentException("Массив раскладки имеет недопустивый вид: " + layoutAsString, e);
		}
	}

	List<Object> getElements()
	{
		return Collections.unmodifiableList(elements);
	}

	Layout removeWidget(String widgetCodename)
	{
		List<Object> newElements = removeWidget(elements, widgetCodename);
		return (newElements == elements) ? this : new Layout(newElements);
	}

	Layout renameWidget(String widgetFromCodename, String widgetToCodename)
	{
		List<Object> newElements = renameWidget(elements, widgetFromCodename, widgetToCodename);
		return (newElements == elements) ? this : new Layout(newElements);
	}

	@Override
	public String toString()
	{
		return serializer.serializeLayout(this);
	}

	@Override
	public int hashCode()
	{
		return elements.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Layout layout = (Layout) o;

		return elements.equals(layout.elements);
	}

	private static List<Object> removeWidget(List<Object> elements, String widgetCodename)
	{
		List<Object> newElements = new ArrayList<Object>(elements);
		for (ListIterator<Object> it = newElements.listIterator(); it.hasNext();)
		{
			Object element = it.next();

			Object element2 = element;
			//noinspection ChainOfInstanceofChecks
			if (element instanceof List) {
				//noinspection unchecked
				element2 = removeWidget((List<Object>) element, widgetCodename);
			}
			else if (element instanceof String) {
				if (widgetCodename.equals(element))
					element2 = null;
			}

			if (element2 != element) {
				if (element2 != null)
					it.set(element2);
				else it.remove();
				return newElements;
			}
		}

		return elements;
	}

	private static List<Object> renameWidget(List<Object> elements, String widgetFromCodename, String widgetToCodename)
	{
		List<Object> newElements = new ArrayList<Object>(elements);
		for (ListIterator<Object> it = newElements.listIterator(); it.hasNext();)
		{
			Object element = it.next();

			Object element2 = element;
			//noinspection ChainOfInstanceofChecks
			if (element instanceof List) {
				//noinspection unchecked
				element2 = renameWidget((List<Object>) element, widgetFromCodename, widgetToCodename);
			}
			else if (element instanceof String) {
				if (widgetFromCodename.equals(element))
					element2 = widgetToCodename;
			}

			if (element2 != element) {
				it.set(element2);
				return newElements;
			}
		}
		return elements;
	}
}
