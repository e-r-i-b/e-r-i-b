package com.rssl.phizic.common.types;

/**
 * @author egorova
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public enum ApplicationType
{
	/*
	���������� ����������
	 */
	Client,
	/*
	���������� ����������
	 */
	Admin,
	/**
	��������� ����������
 	 */
	Mobile,
	/**
	 * WebAPI
	 */
	WebAPI,
	/**
	 * �� ���������
	 */
	Other;

	/**
	 * @param application ����������
	 * @return ���������� ��� ���������� �� ����������
	 */
	public static ApplicationType getApplicationType(Application application)
	{
		if(application == null)
			return Other;

		switch (application)
		{
			case PhizIC: return Client;
			case PhizIA: return Admin;
			case WebAPI: return WebAPI;
			default:
				if (new ApplicationInfo().isMobileApi())
					return Mobile;
				return Other;
		}
	}
}
