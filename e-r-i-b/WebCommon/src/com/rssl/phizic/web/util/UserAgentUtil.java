package com.rssl.phizic.web.util;

/**
 * @author Rydvanskiy
 * @ created 15.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class UserAgentUtil
{
	// ����� �� UserAgent`�
	public static UserAgent findByName (String name) throws IllegalArgumentException
	{
	if ( name == null) throw new IllegalArgumentException("�������� name �� ����� ���� null");

	for (UserAgent agent : UserAgent.values())
           if (name.equals(agent.getName())) return agent;

	return UserAgent.web;
	}
}
