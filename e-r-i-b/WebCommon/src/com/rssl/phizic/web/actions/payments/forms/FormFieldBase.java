package com.rssl.phizic.web.actions.payments.forms;

/**
 * @author Erkin
 * @ created 27.11.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class FormFieldBase implements FormField
{
	private String name;

	private String userName;

	private String marker = null;

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
	}

	public String toString()
	{
		return "FormFieldBase{" +
				"name='" + name + '\'' +
				", userName='" + userName + '\'' +
				", marker='" + marker + '\'' +
				'}';
	}
}
