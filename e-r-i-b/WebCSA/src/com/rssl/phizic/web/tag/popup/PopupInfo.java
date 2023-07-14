package com.rssl.phizic.web.tag.popup;

/**
 * Инфомация об открывающемся окне
 * @author niculichev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class PopupInfo
{
	private String id;
	private String defaultMessage;

	public PopupInfo()
	{
	}

	public PopupInfo(String id, String defaultMessage)
	{
		this.id = id;
		this.defaultMessage = defaultMessage;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDefaultMessage()
	{
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage)
	{
		this.defaultMessage = defaultMessage;
	}
}
