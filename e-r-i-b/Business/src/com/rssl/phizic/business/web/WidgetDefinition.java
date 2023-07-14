package com.rssl.phizic.business.web;

import com.rssl.phizic.business.web.widget.strategy.WidgetInitializer;
import com.rssl.phizic.business.web.widget.strategy.WidgetAccessor;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 15.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� (������) �������
 */
public class WidgetDefinition
{
	/**
	 * ����������� WidgetDefinition
	 */
	private String codename;

	/**
	 * �������� ��� ����������� ������������
	 */
	private String username;

	/**
	 * �������� � �������
	 */
	private String description;

	private Class<? extends Widget> widgetClass;

	/**
	 * ��������� ������� � �������
	 */
	private WidgetAccessor accessor;

	/**
	 * ��������� �������������� �������
	 */
	private WidgetInitializer initializer;

	/**
	 * ������ �������� �������:
	 *  "sync" - ������ ����������� ������ �� ���������
	 *  "async" - ������ ����������� ��������� ajax-�������� ����� �������� ��������
	 */
	private String loadMode;

	/**
	 * URL ������-������
	 */
	private String path;

	/**
	 * ��� ������-��������
	 */
	private String operation;

	/**
	 * �������� �������
	 */
	private String picture;

	/**
	 * �������� ������ ������ �������
	 */
	private String initialSize;

	/**
	 * ������ "������ ����� ������ ������ �� ����������� � ������� � �������"
	 */
	private boolean sizeable;

	/**
	 * ����� �� �������
	 */
	private Long index;

	/**
	 * ������� �����������
	 */
	private boolean availability;

	/**
	 * ����� ���������� � �������
	 */
	private Calendar addingDate;

	/**
	 * ������������ ��������� ���������� ������� �� ��������
	 */
	private Integer maxCount;

	/**
	 * ��������� � ���������� ���������� �������� �� ��������
	 */
	private String maxCountMessage;

	///////////////////////////////////////////////////////////////////////////

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getLoadMode()
	{
		return loadMode;
	}

	public void setLoadMode(String loadMode)
	{
		this.loadMode = loadMode;
	}

	public String getCodename()
	{
		return codename;
	}

	public void setCodename(String codename)
	{
		this.codename = codename;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Class<? extends Widget> getWidgetClass()
	{
		return widgetClass;
	}

	public void setWidgetClass(Class<? extends Widget> widgetClass)
	{
		this.widgetClass = widgetClass;
	}

	public WidgetAccessor getAccessor()
	{
		return accessor;
	}

	public void setAccessor(WidgetAccessor accessor)
	{
		this.accessor = accessor;
	}

	public WidgetInitializer getInitializer()
	{
		return initializer;
	}

	public void setInitializer(WidgetInitializer initializer)
	{
		this.initializer = initializer;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public String getInitialSize()
	{
		return initialSize;
	}

	public void setInitialSize(String initialSize)
	{
		this.initialSize = initialSize;
	}

	public boolean isSizeable()
	{
		return sizeable;
	}

	public void setSizeable(boolean sizeable)
	{
		this.sizeable = sizeable;
	}

	public boolean isAvailability()
	{
		return availability;
	}

	public void setAvailability(boolean availability)
	{
		this.availability = availability;
	}

	public Long getIndex()
	{
		return index;
	}

	public void setIndex(Long index)
	{
		this.index = index;
	}

	public Calendar getAddingDate()
	{
		return addingDate;
	}

	public void setAddingDate(Calendar addingDate)
	{
		this.addingDate = addingDate;
	}

	public Integer getMaxCount()
	{
		return maxCount;
	}

	public void setMaxCount(Integer maxCount)
	{
		this.maxCount = maxCount;
	}

	public String getMaxCountMessage()
	{
		return maxCountMessage;
	}

	public void setMaxCountMessage(String maxCountMessage)
	{
		this.maxCountMessage = maxCountMessage;
	}

	/**
	 * �������� ���������������� ��������� ����������������� ����������� �� def
	 * @param def - ������ ����� ���������
	 */
	public void merge(WidgetDefinition def)
	{
		this.index = def.getIndex();
		this.availability = def.isAvailability();
	}
}
