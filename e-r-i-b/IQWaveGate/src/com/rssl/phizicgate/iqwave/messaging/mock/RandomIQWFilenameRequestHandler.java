package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Random;
import javax.xml.transform.TransformerException;

/**
 *  для того чтоб не создавались автоплатежи с одинаковым externalId у разных клиентов
 *  делаем последние 4 цифры номера телефона случайными
 * @author basharin
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class RandomIQWFilenameRequestHandler extends IQWFilenameRequestHandler
{

	public RandomIQWFilenameRequestHandler(String filename) throws GateException
	{
		super(filename);
	}


	public Document proccessRequest(Document response) throws GateException
	{
		Document document = super.proccessRequest(response);
		try
		{
			XmlHelper.foreach(XmlHelper.selectSingleNode(document.getDocumentElement(), Constants.BODY_TAG), Constants.AUTO_PAY_ITEM_TEG, new ForeachElementAction()
			{
				public void execute(Element element) throws GateException, GateLogicException
				{
					try
					{
						Element el = XmlHelper.selectSingleNode(element, Constants.AUTO_PAY_TEL_NO_TEG);
						Random rand = new Random();
						String telNumber = el.getTextContent();
						if (telNumber.length() > 4)
						{
							telNumber = telNumber.substring(0, telNumber.length() - 4);
							telNumber = telNumber.concat(RandomHelper.rand(4,RandomHelper.DIGITS));
							el.setTextContent(telNumber);
						}
					}
					catch (TransformerException e)
					{
						throw new GateException(e);
					}
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		return document;
	}
}
