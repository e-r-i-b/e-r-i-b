package com.rssl.phizic.gorod.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Gainanov
 * @ created 25.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecipientListQCache extends MessagesCache
{
	private List<Class> classes;
	private static final String CARD_KEY = "/ReqCard/Card/pan" ;
	private static final String SERVICE_KEY = "/RSASMsg/AnsCard/Card/ListOfLinkAbonent/Abonent/Service";
	protected String getCacheName()
	{
		return RecipientListQCache.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			Element element = XmlHelper.selectSingleNode(request.getDocumentElement(), CARD_KEY);
			if(element == null)
				return null;

			String key = element.getTextContent();
			if(XmlHelper.selectSingleNode(request.getDocumentElement(), "/ReqCard/ListOfMoreInfo/moreInfo[@xpRef='" + SERVICE_KEY + "']") != null)
				key+= "|" + SERVICE_KEY;
			return key;
		}
		catch (TransformerException e)
		{
			log.error("Ошибка при получении ключа для сообщения:", e);
		}
		return null;
	}

	public List<Class> getCacheClasses()
	{
		if(classes==null)
		{
			classes = new ArrayList<Class>();
			classes.add(Recipient.class);
		}
		return classes;
	}

	public void clear(Object object) throws GateException
	{
	}
}
