package com.rssl.phizic.operations.widget;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.Layout;
import com.rssl.phizic.business.web.Location;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 14.07.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция с виджет-контейнером
 */
public class WidgetContainerOperation extends OperationBase
{
	private Location location;

	private Layout layout;

	private WebPage container;

	private Login login;

	/**
	 * Инициализация операции
	 * @param codename - кодовое обозначение контейнера
	 */
	public void initialize(String codename) throws BusinessException
	{
		if (StringHelper.isEmpty(codename))
			throw new BusinessException("Не указан codename виджет-контейнера");

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		container = personData.getPage(codename);
		if (container == null)
			throw new BusinessException("Не найден виджет-контейнер " + codename);

		location = container.getLocation();
		layout = container.getLayout();
		login = personData.getPerson().getLogin();
	}

	public WebPage getContainer()
	{
		return container;
	}

	public void setLocation(String location) throws BusinessException
	{
		try
		{
			this.location = Location.valueOf(location);
		}
		catch (IllegalArgumentException e)
		{
			throw new BusinessException("Указан недопустимый код позиции контейнера: " + location, e);
		}
	}

	public void setLayout(Layout layout)
	{
		this.layout = layout;
	}

	public Login getLogin()
	{
		return login;
	}

	public void save() throws BusinessException
	{
		if (layout != null)
			container.setLayout(layout);

		if (location != null)
			container.setLocation(location);
	}
}
