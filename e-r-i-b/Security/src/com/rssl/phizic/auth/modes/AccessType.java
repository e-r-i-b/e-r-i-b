package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.common.types.Application;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 15.12.2006
 * @ $Author: niculichev $
 * @ $Revision: 72565 $
 */

public class AccessType implements Serializable
{
	/**
	 * ���������� ������
	 */
	public static final AccessType simple = new AccessType("simple", SecurityService.SCOPE_CLIENT, Application.PhizIC);
	/**
	 * ���������� ������
	 */
	public static final AccessType secure = new AccessType("secure", SecurityService.SCOPE_CLIENT, Application.PhizIC);

	/**
	 * ������ ��������� ��������
	 */
	public static final AccessType anonymous = new AccessType("anonymous", SecurityService.SCOPE_CLIENT, Application.PhizIC);

	/**
	 * ������ �����������
	 */
	public static final AccessType employee = new AccessType("employee", SecurityService.SCOPE_EMPLOYEE, Application.PhizIA);

	/**
	 * ������ SMS-Banking
	 */
	public static final AccessType smsBanking = new AccessType("smsBanking", SecurityService.SCOPE_CLIENT, Application.PhizIC);

	/**
	 * ������������ ������ ��� ��������� ���������
	 */
	public static final AccessType mobileLimited = new AccessType("mobileLimited", SecurityService.SCOPE_CLIENT, Application.PhizIC);

	/**
	 * �������� ������
	 */
	public static final AccessType guest = new AccessType("guest", SecurityService.SCOPE_CLIENT, Application.PhizIC);

	/**
	 * ��� ������ �������
	 */
	private static final AccessType[] values = new AccessType[]{ simple, secure, anonymous, employee, smsBanking, mobileLimited, guest };

	private final String key;
	private final String scope;
	private final Application application;

	private AccessType(String key, String scope, Application application)
	{
		this.key = key;
		this.scope = scope;
		this.application = application;
	}

	/**
	 * @return �������������
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return SecurityService.SCOPE_EMPLOYEE || SecurityService.SCOPE_CLIENT
	 */
	public String getScope()
	{
		return scope;
	}

	/**
	 * @return ����������, ��� �������� ������ AccessType.
	 */
	public Application getApplication()
	{
		return application;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AccessType that = (AccessType) o;

		if (!key.equals(that.key))
			return false;

		return true;
	}

	public int hashCode()
	{
		return key.hashCode();
	}

	public String toString()
	{
		return key;
	}

	/**
	 * @return ������ ���� ��������
	 */
	public static AccessType[] values()
	{
		return values.clone();
	}

	/**
	 * ����� ��� ������� �� �����
	 * @param str ����
	 * @return ��� �������
	 */
	public static AccessType valueOf(String str)
	{
		for (AccessType value : values)
		{
			if(value.getKey().equals(str))
				return value;
		}

		throw new IllegalArgumentException("str = " + str);
	}
}
