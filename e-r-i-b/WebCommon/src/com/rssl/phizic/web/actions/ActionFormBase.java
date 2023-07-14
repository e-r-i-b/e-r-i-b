package com.rssl.phizic.web.actions;

import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import org.apache.struts.action.ActionForm;

/**
 * @author Erkin
 * @ created 18.01.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class ActionFormBase extends ActionForm
{
	private boolean isFromStart = false;
	private boolean widgetAvailable = false;
	private String mobileApiVersion;
	private AutoConfirmRequestType autoConfirmRequestType;

	/**
	 * пришел ли запрос со старта(с метода start)
	 * Ќеобходимо, чтобы сделать разную отрисовку списка на старте, и при последующей работе.
	 * @return true - да, с метода старт
	 */
	public boolean isFromStart()
	{
		return isFromStart;
	}

	public void setFromStart(boolean fromStart)
	{
		isFromStart = fromStart;
	}

	public String getMobileApiVersion()
	{
		return mobileApiVersion;
	}

	public void setMobileApiVersion(String mobileApiVersion)
	{
		this.mobileApiVersion = mobileApiVersion;
	}

	/**
	 * ƒоступны ли виджеты на данной странице
	 * @return true - доступны
	 */
	public boolean getWidgetAvailable()
	{
		return widgetAvailable;
	}

	public void setWidgetAvailable(boolean widgetAvailable)
	{
		this.widgetAvailable = widgetAvailable;
	}

	/**
	 * @return “ип запроса, дл€ которого необходимо отображение окна подтверждени€
	 */
	public AutoConfirmRequestType getAutoConfirmRequestType()
	{
		return autoConfirmRequestType;
	}

	public void setAutoConfirmRequestType(AutoConfirmRequestType autoConfirmRequestType)
	{
		this.autoConfirmRequestType = autoConfirmRequestType;
	}
}
