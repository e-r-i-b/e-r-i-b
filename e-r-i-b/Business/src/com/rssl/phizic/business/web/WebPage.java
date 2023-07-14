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
 * ��������� ��������
 */
@ThreadSafe
public class WebPage extends WebPageBean implements Cloneable
{
	private Map<String, Widget> widgets = new LinkedHashMap<String, Widget>();

	///////////////////////////////////////////////////////////////////////////

	WebPage() {}

	/**
	 * ���������� ��� ������� � ����������
	 * @return ��������� �������� � ����������
	 */
	public synchronized Collection<Widget> getWidgets()
	{
		return Collections.unmodifiableCollection(widgets.values());
	}

	/**
	 * ���������� ������ �� ��� ������������
	 * @param codename - ����������� �������, ����., FaceBook12321091
	 * @return ������ ��� null, ���� � ���������� ��� ������� � ����� �������������
	 */
	public synchronized Widget getWidget(String codename)
	{
		return widgets.get(codename);
	}

	/**
	 * @param widget
	 * @return true, ���� ������ ���� � ����������
	 */
	public synchronized boolean containsWidget(Widget widget)
	{
		return widget.getContainer() == this;
	}

	/**
	 * �������� ������ � ���������
	 * @param widget - ������
	 * @throws IllegalStateException ������ ��� �������� � ������ ���������,
	 *  ���� � ������ ���������� ��� ���� ������ � ����������� �������������
	 */
	public synchronized void addWidget(Widget widget) throws IllegalStateException
	{
		String widgetCodename = widget.getCodename();

		if (widget.getContainer() != null)
			throw new IllegalStateException("������ ��������� ������ " + widgetCodename + " ����� � 2 ����������");

		if (widgets.containsKey(widgetCodename))
			throw new IllegalStateException("� ���������� " + getClassname() + " ��� ���� ������ " + widgetCodename);

		widgets.put(widgetCodename, widget);
		widget.setContainer(this);
	}

	/**
	 * ������� ������ �� ����������
	 * @param widget - ������
	 * @throws IllegalStateException � ���������� ��� ���������� �������
	 */
	public synchronized void removeWidget(Widget widget) throws IllegalStateException
	{
		String widgetCodename = widget.getCodename();

		if (this != widget.getContainer())
			throw new IllegalStateException("������ " + widgetCodename + " �� ����������� ���������� " + getClassname());

		widgets.remove(widgetCodename);
		widget.setContainer(null);
	}

	/**
	 * ������� ��� ������� �� ����������
	 * @return �������� �������
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
	 * ���������� ���������� �������� ��������� ����
	 * @param definitionCodename - ����������� ��������� (� ���� ���� �������)
	 * @return ���������� �������� ��������� ����
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
			// ������ �����������: id, classname
			WebPage newPage = (WebPage) super.clone();
			newPage.widgets = new LinkedHashMap<String, Widget>();

			for (Widget widget : widgets.values())
				newPage.addWidget(widget.clone());

			return newPage;
		}
		catch (CloneNotSupportedException e)
		{
			// TODO: (�������) �������� ����� runtime-����������
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
