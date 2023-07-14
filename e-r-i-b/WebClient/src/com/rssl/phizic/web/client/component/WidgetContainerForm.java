package com.rssl.phizic.web.client.component;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Erkin
 * @ created 14.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetContainerForm extends ActionFormBase
{
	/**
	 * ����������� ����������
	 * �������� � �������
	 */
	private String codename;

	/**
	 * ��������� ����������
	 * �������� � �������
	 */
	private String location;

	/**
	 * ���������
	 * �������� � �������
	 */
	private String layout;

	private WebPage container;

	/**
	 * URL ������ ����������
	 * �� ���� ���������
	 * ����� ��� �������� ������������� �� jsp
	 */
	private String containerActionPath;

	private Login login;

	///////////////////////////////////////////////////////////////////////////

	public String getCodename()
	{
		return codename;
	}

	public void setCodename(String codename)
	{
		this.codename = codename;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getLayout()
	{
		return layout;
	}

	public void setLayout(String layout)
	{
		this.layout = layout;
	}

	public WebPage getContainer()
	{
		return container;
	}

	public void setContainer(WebPage container)
	{
		this.container = container;
	}

	public String getContainerActionPath()
	{
		return containerActionPath;
	}

	public void setContainerActionPath(String containerActionPath)
	{
		this.containerActionPath = containerActionPath;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}
}
