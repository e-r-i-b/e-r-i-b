package com.rssl.phizic.gate;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
public interface Routable
{
	/**
	 * ��������� � ������� ���������� � �������������
	 * @param info
	 */
	void storeRouteInfo(String info);

	/**
	 * �������� �� ������� ���������� � �������������
	 * @return info
	 */
	String restoreRouteInfo();

	/**
	 * ������ �� ������� ���������� ��� �������������,
	 * ������������ ������������ ��������� �������.
	 * @return ���������� � �������������
	 */
	 String removeRouteInfo();
}
