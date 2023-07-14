package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Обработчик запрос информации по последним операциям по программе лояльности
 * @author gladishev
 * @ created 21.07.2014
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyStatementRequestHandler extends LoyaltyRequestHandler
{
	private static final List<String> OPER_TYPES = new ArrayList<String>(4);
	static
	{
		OPER_TYPES.add("Оплата баллами");
		OPER_TYPES.add("Начисление баллов");
		OPER_TYPES.add("Возврат на оплату");
		OPER_TYPES.add("Возврат на начисление");
	}

	public LoyaltyStatementRequestHandler(String filename) throws GateException
	{
		super(filename);
	}

	@Override
	public Document proccessRequest(Document request) throws GateException
	{
		try
		{
			Document document = super.proccessRequest(request);
			/*
			XmlHelper.foreach(document.getDocumentElement(), "//Transaction", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
//					Calendar date = DateHelper.getPreviousNDay(Calendar.getInstance(), new Random().nextInt(100));
//					XmlHelper.selectSingleNode(element, "DateTime").setTextContent(new SimpleDateFormat("yyyyMMddHHmmss").format(date.getTime()));
//					XmlHelper.selectSingleNode(element, "OperationKind").setTextContent(OPER_TYPES.get(new Random().nextInt(4)));
				}
			});
			 */
			return document;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
