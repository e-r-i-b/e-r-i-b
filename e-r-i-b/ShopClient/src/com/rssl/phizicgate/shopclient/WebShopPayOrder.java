package com.rssl.phizicgate.shopclient;

/**
 * @author gulov
 * @ created 05.04.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ��� � ������� ��������� ��������� ����
 */
public class WebShopPayOrder
{
	/**
	 * ������������� ��������� � ������� ����
	 */
	private final String uuId;

	/**
	 * ������������� ��������� � ��������-��������
	 */
	private final String externalId;

	/**
	 * ������� ������ ���������
	 */
	private final String state;

	private final String utrrno; // ���������� ��� �������� SVFE

	///////////////////////////////////////////////////////////////////////////

	public WebShopPayOrder(String uuId, String externalId, String state, String utrrno)
	{
		this.uuId = uuId;
		this.externalId = externalId;
		this.state = state;
		this.utrrno = utrrno;
	}

	public String getUuId()
	{
		return uuId;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public String getState()
	{
		return state;
	}

	public String getUtrrno()
	{
		return utrrno;
	}
}
