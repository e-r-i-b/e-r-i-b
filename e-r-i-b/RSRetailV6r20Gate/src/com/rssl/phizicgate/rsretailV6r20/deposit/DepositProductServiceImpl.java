package com.rssl.phizicgate.rsretailV6r20.deposit;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.transform.TransformerException;

/**
 * @author Danilov
 * @ created 30.07.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductServiceImpl extends AbstractService implements DepositProductService
{
	public Document getDepositsInfo(Office office) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage request = service.createRequest("getDepositList_q");
		Document doc = null;
		try
		{
			doc = service.sendOnlineMessage(request, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}

		try
		{
			NodeList list = XmlHelper.selectNodeList(doc.getDocumentElement(),"deposit/currency");
			int len = list.getLength();
			for(int i=0;i< len;++i)
			{
				Node node = list.item(i);
				String currencyId = node.getTextContent();
				if("RUR".equals(currencyId))
				{
					node.setTextContent("RUB");
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка при получении списка видов вкладов", e);
		}
		return doc;
	}

	public Document getDepositProduct(Document params, Office office) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		Document  doc = null;
		try
		{
			doc = service.sendOnlineMessage(params, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}

		try
		{
			NodeList list = XmlHelper.selectNodeList(doc.getDocumentElement(),"data/element/value");
			int len = list.getLength();
			for(int i=0;i<len;++i)
			{
				Node node = list.item(i);
				Node fieldAttribute = node.getAttributes().getNamedItem("field");
				if("currency".equals(fieldAttribute.getTextContent()))
				{
					String curCode = node.getTextContent();
					if("RUR".equals(curCode))
						node.setTextContent("RUB");
				}
				if("periodMin".equals(fieldAttribute.getTextContent()))
				{
					fieldAttribute.setTextContent("period");
					continue;
				}
				if("periodMax".equals(fieldAttribute.getTextContent()))
				{
					fieldAttribute.setTextContent("maxPeriod");
					continue;
				}
				if("period".equals(fieldAttribute.getTextContent()))
				{
					fieldAttribute.setTextContent("paymentPeriod");
					continue;
				}
			}

			NodeList list1 = XmlHelper.selectNodeList(doc.getDocumentElement(),"dictionaries/entity-list");
			int len1 = list1.getLength();
			for(int i=0;i<len1;++i)
			{
				Node nameAttribute = list1.item(i).getAttributes().getNamedItem("name");
				if("currencies".equals(nameAttribute.getTextContent()))
				{
					NodeList listCurrencies = list1.item(i).getChildNodes();
					int lenCurrencies = listCurrencies.getLength();
					for(int j = 0; j< lenCurrencies;++j)
					{
						Node keyAttribute = listCurrencies.item(j).getAttributes().getNamedItem("key");
						String currencyId = keyAttribute.getTextContent();
						if("RUR".equals(currencyId))
							keyAttribute.setTextContent("RUB");
					}
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка при создании депозитного продукта", e);
		}
		return doc;
	}

	public DepositProductServiceImpl(GateFactory factory)
    {
        super(factory);
    }
}