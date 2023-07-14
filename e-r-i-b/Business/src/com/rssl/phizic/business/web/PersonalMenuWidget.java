package com.rssl.phizic.business.web;

import java.util.LinkedList;
import java.util.List;

/** ������ "������ ����"
 * @author gulov
 * @ created 11.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PersonalMenuWidget extends Widget
{	
	//������ ��������� �������� � ������ �������
	private List<WidgetObjectVisibility> templatesVisibility =  new LinkedList<WidgetObjectVisibility>();
	//������ ��������� ��������� �������� � ������ �������
	private List<WidgetObjectVisibility> favouriteLinksVisibility =  new LinkedList<WidgetObjectVisibility>();

	@Override
	protected Widget clone()
	{
		PersonalMenuWidget newWidget = (PersonalMenuWidget) super.clone();
		newWidget.setTemplatesVisibility(templatesVisibility);
		newWidget.setFavouriteLinksVisibility(favouriteLinksVisibility);
		return newWidget;
	}

	public List<WidgetObjectVisibility> getTemplatesVisibility()
	{
		return templatesVisibility;
	}

	public void setTemplatesVisibility(List<WidgetObjectVisibility> templatesVisibility)
	{
		this.templatesVisibility = templatesVisibility;
	}

	public List<WidgetObjectVisibility> getFavouriteLinksVisibility()
	{
		return favouriteLinksVisibility;
	}

	public void setFavouriteLinksVisibility(List<WidgetObjectVisibility> favouriteLinksVisibility)
	{
		this.favouriteLinksVisibility = favouriteLinksVisibility;
	}

}
