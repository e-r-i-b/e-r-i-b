package com.rssl.phizic.business.groups;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёлемент группы пользователей
 */
public class GroupElement implements Serializable
{
	private Group group;

	private CommonLogin login;

	///////////////////////////////////////////////////////////////////////////

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public int hashCode()
	{
		int result = group != null ? group.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GroupElement that = (GroupElement) o;

		if (group != null ? !group.equals(that.group) : that.group != null)
			return false;
		if (login != null ? !login.equals(that.login) : that.login != null)
			return false;

		return true;
	}
}
