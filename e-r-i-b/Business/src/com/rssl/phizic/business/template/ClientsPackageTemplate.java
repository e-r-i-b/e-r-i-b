package com.rssl.phizic.business.template;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 15.02.2007
 * Time: 17:34:57
 * To change this template use File | Settings | File Templates.
 */
public class ClientsPackageTemplate implements Serializable
{
	private CommonLogin     login;
	private Long            id;
	private PackageTemplate packageTemplate;

	/**
	 * @return логин пользователя, которому подключен пакет
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return пекет, которой пренадлежит пользователю
	 */
	public PackageTemplate getPackageTemplate()
	{
		return packageTemplate;
	}

	public void setPackageTemplate(PackageTemplate packageTemplate)
	{
		this.packageTemplate = packageTemplate;
	}
}
