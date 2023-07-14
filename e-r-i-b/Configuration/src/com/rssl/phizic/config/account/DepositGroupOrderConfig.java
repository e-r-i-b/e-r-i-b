package com.rssl.phizic.config.account;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

/**
 * Конфиг для получения порядка сортировки вкладных продуктов по группам
 *
 * @author EgorovaA
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class DepositGroupOrderConfig extends Config
{
	public static final String DEPOSIT_GROUP_ORDER_PARAMS = "com.rssl.iccs.depositGroup.order";

	private Element depositGroupOrder;

	public DepositGroupOrderConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		String orderParams = getProperty(DEPOSIT_GROUP_ORDER_PARAMS);
		depositGroupOrder = buildEntityList(orderParams);
	}

	public Element getDepositGroupOrder()
	{
		return depositGroupOrder;
	}

	public Element buildEntityList(String orderParams)
	{
		String[] ordersArray = orderParams.replace(" ", "").split(",");

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document listDoc = documentBuilder.newDocument();
		Element listRoot = listDoc.createElement("sortList");

		for (String order : ordersArray)
		{
			Element element = listDoc.createElement("numberPosition");
			element.setTextContent(order);
			listRoot.appendChild(element);
		}
		return listRoot;

	}
}
