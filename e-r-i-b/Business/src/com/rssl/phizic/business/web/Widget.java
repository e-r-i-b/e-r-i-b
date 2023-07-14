package com.rssl.phizic.business.web;

import com.rssl.phizic.utils.ClassHelper;

import java.util.Set;

/**
 * @author Erkin
 * @ created 17.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� �������.
 * �������� ��������� �������.
 * ��������� � ������� ������� (����� transient-�����) � ���� json-������.
 *
 * �����! transient-���� �� �������� � ���� � �� ���������� � �������.
 * ������� ��� ������������ �� ����� �������, � ����� ��� ���������� ��������.
 */
public abstract class Widget implements Cloneable
{
	private String codename;

	private transient WidgetDefinition definition;

	private transient WebPage container;

	private String title;

	private String size; // wide/compact

	/**
	 * ������ ������/�������
	 */
	private boolean rollUp = false; // true => ������

	/**
	 * ������ "������ / �� ������"
	 */
	private boolean blinking = false;

	///////////////////////////////////////////////////////////////////////////

	public String getCodename()
	{
		return codename;
	}

	void setCodename(String codename)
	{
		this.codename = codename;
	}

	public WidgetDefinition getDefinition()
	{
		return definition;
	}

	void setDefinition(WidgetDefinition definition)
	{
		this.definition = definition;
	}

	public WebPage getContainer()
	{
		return container;
	}

	void setContainer(WebPage container)
	{
		this.container = container;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public boolean isRollUp()
	{
		return rollUp;
	}

	public void setRollUp(boolean rollUp)
	{
		this.rollUp = rollUp;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean isBlinking()
	{
		return blinking;
	}

	public void setBlinking(boolean blinking)
	{
		this.blinking = blinking;
	}

	protected Widget clone()
	{
		try
		{
			Widget newWidget = (Widget) super.clone();
			// ����� ������ ���������� ��� ���������� 
			newWidget.container = null;
			return newWidget;
		}
		catch (CloneNotSupportedException e)
		{
			// TODO: (�������) �������� ����� runtime-����������
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode()
	{
		return codename.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof Widget))
			return false;
		return (compareFields(o).size()!= 0);
	}

	/**
	 * ���������� �������� ����� �������, ������� ����������
	 * @param o - ������ ������
	 * @return
	 */
	public Set<String> compareFields(Object o)
	{
		return ClassHelper.compareFields(this, o) ;
	}
}
