package com.rssl.phizicgate.manager.services.routablePersistent;

import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;

import java.util.List;

/**
 * ������ ��� ����������� ����������.
 *
 * @author bogdanov
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class StringRouteInfo implements RouteInfoReturner
{
	private String externalId;

	public StringRouteInfo(String externalId)
	{
		this.externalId = externalId;
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(externalId);
	}

	public String getId()
	{
		return IDHelper.restoreOriginalId(externalId);
	}

	/**
	 * ��������� ���������������� ���������� � ������ �����.
	 * @param routeInfoReturners ���������� �������������.
	 * @return ������ �� �������� id.
	 */
	public static String[] toArray(List<? extends RouteInfoReturner> routeInfoReturners)
	{
		String[] s = new String[routeInfoReturners.size()];
		for (int i = 0; i < s.length; i++)
		{
			s[i] = routeInfoReturners.get(i).getId();
		}

		return s;
	}
}
