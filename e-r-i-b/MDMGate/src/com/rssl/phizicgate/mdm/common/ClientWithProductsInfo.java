package com.rssl.phizicgate.mdm.common;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * полная информация из мдм (клиент + продукты)
 */

public class ClientWithProductsInfo
{
	private final ClientInfo clientInfo;
	private final ClientProductsInfo productsInfo;

	/**
	 * конструктор
	 * @param clientInfo   информация по клиенту
	 * @param productsInfo информация по продуктам клиентов
	 */
	public ClientWithProductsInfo(ClientInfo clientInfo, ClientProductsInfo productsInfo)
	{
		this.clientInfo = clientInfo;
		this.productsInfo = productsInfo;
	}

	/**
	 * @return информация по клиенту
	 */
	public ClientInfo getClientInfo()
	{
		return clientInfo;
	}

	/**
	 * @return информация по продуктам клиентов
	 */
	public ClientProductsInfo getProductsInfo()
	{
		return productsInfo;
	}
}
