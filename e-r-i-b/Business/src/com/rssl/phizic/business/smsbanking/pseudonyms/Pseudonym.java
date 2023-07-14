package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class Pseudonym
{
	private Long    id;
	private String  value;  // external_id из Link'a 
	private String  name;   // псевдоним
	private CommonLogin login; // кому собственно принадлежит псевдоним
	private boolean hasAccess; // доступ

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

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

	public boolean isHasAccess()
	{
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess)
	{
		this.hasAccess = hasAccess;
	}

	public abstract ExternalResourceLink getLink() throws BusinessException, BusinessLogicException;

	public abstract String getNumber() throws BusinessException, BusinessLogicException;
}
