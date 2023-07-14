package com.rssl.phizic.business.web;

/** Используется в виджете "Личное меню" для хранения двух списков: списка видимости шаблонов и списка видимости избранных операций
 * @ author Rtischeva
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class WidgetObjectVisibility
{
	private final Long id;
	
	private final boolean visible;

	public WidgetObjectVisibility(Long id, boolean visible)
	{
		this.id = id;
		this.visible = visible;
	}

	public Long getId()
	{
		return id;
	}


	public boolean isVisible()
	{
		return visible;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WidgetObjectVisibility that = (WidgetObjectVisibility) o;

		if (visible != that.visible)
			return false;
		if (!id.equals(that.id))
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
}
