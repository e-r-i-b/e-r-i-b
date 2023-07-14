package com.rssl.phizicgate.rsV55.deposit;

import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.common.types.Currency;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

/**
 * @author Danilov
 * @ created 30.07.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductServiceImpl extends AbstractService implements DepositProductService
{

	public Document getDepositsInfo(Office office) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		GateMessage request = service.createRequest("getDepositList_q");
		//TODO request.addParameter("filial", "0");в текущеёй версии в rtxml.mac по умолчанию проставляется нулевой филиал
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
			for(int i=0;i<list.getLength();++i)
			{
				Node node = list.item(i);
				String currencyId = node.getTextContent();
				Currency cur = currencyService.findById(currencyId);
				if(cur==null)
					throw new GateException("Не найдена валюта с кодом " + currencyId);
				node.setTextContent(cur.getCode());
			}
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка при получении списка видов вкладов", e);
		}

		return doc;
	}

	public Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
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
			for(int i=0;i<list.getLength();++i)
			{
				if("currency".equals(list.item(i).getAttributes().getNamedItem("field").getTextContent()))
				{
					Currency cur = currencyService.findById(list.item(i).getTextContent());
					if(cur==null)
						throw new GateException("Не найдена валюта с кодом " + list.item(i).getTextContent());
					list.item(i).setTextContent(cur.getCode());
				}
			}

			list = XmlHelper.selectNodeList(doc.getDocumentElement(),"dictionaries/entity-list");
			for(int i=0;i<list.getLength();++i)
			{
				if("currencies".equals(list.item(i).getAttributes().getNamedItem("name").getTextContent()))
				{
					NodeList listCurrencies = list.item(i).getChildNodes();
					for(int j = 0; j<listCurrencies.getLength();++j)
					{
						Node myNode = listCurrencies.item(j).getAttributes().getNamedItem("key");
						Currency cur = currencyService.findById(myNode.getTextContent());
						if(cur==null)
							throw new GateException("Не найдена валюта с кодом " + myNode.getTextContent());
						myNode.setTextContent(cur.getCode());
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