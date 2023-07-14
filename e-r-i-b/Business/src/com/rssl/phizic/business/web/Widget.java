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
 * Базовый класс виджета.
 * Содержит настройки виджета.
 * Передаётся в браузер целиком (кроме transient-полей) в виде json-строки.
 *
 * ВАЖНО! transient-поля не кладутся в базу и не передаются в браузер.
 * Поэтому они сбрасываются на входе клиента, а также при сохранении настроек.
 */
public abstract class Widget implements Cloneable
{
	private String codename;

	private transient WidgetDefinition definition;

	private transient WebPage container;

	private String title;

	private String size; // wide/compact

	/**
	 * Виджет свёрнут/раскрыт
	 */
	private boolean rollUp = false; // true => свёрнут

	/**
	 * Флажок "мигать / не мигать"
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
			// новый виджет изначально вне контейнера 
			newWidget.container = null;
			return newWidget;
		}
		catch (CloneNotSupportedException e)
		{
			// TODO: (виджеты) Уточнить класс runtime-исключения
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
	 * Возвращает названия полей виджета, которые изменились
	 * @param o - старый виджет
	 * @return
	 */
	public Set<String> compareFields(Object o)
	{
		return ClassHelper.compareFields(this, o) ;
	}
}
