package com.rssl.phizicgate.mdm.common;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� �� ��� (������ + ��������)
 */

public class ClientWithProductsInfo
{
	private final ClientInfo clientInfo;
	private final ClientProductsInfo productsInfo;

	/**
	 * �����������
	 * @param clientInfo   ���������� �� �������
	 * @param productsInfo ���������� �� ��������� ��������
	 */
	public ClientWithProductsInfo(ClientInfo clientInfo, ClientProductsInfo productsInfo)
	{
		this.clientInfo = clientInfo;
		this.productsInfo = productsInfo;
	}

	/**
	 * @return ���������� �� �������
	 */
	public ClientInfo getClientInfo()
	{
		return clientInfo;
	}

	/**
	 * @return ���������� �� ��������� ��������
	 */
	public ClientProductsInfo getProductsInfo()
	{
		return productsInfo;
	}
}
