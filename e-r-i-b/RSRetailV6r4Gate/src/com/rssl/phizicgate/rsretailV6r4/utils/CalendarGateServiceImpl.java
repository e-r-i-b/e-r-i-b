package com.rssl.phizicgate.rsretailV6r4.utils;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author Gainanov
 * @ created 02.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateServiceImpl extends AbstractService implements CalendarGateService
{


	public CalendarGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		try
		{
			GateMessage message = createGateMessage(fromDate, document.getOffice().getSynchKey());
			Document responce = service.sendOnlineMessage(message, null);
			Element dateElement = XmlHelper.selectSingleNode(responce.getDocumentElement(), "nextDate");
			String date = dateElement.getTextContent();
			return DateHelper.parseCalendar(date);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}

	private GateMessage createGateMessage(Calendar fromDate, Comparable fnCash) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage msg = service.createRequest("nextWorkDate_q");
		String date = String.format("%1$te.%1$tm.%1$tY", fromDate);
		msg.addParameter("fromDate", date);
		msg.addParameter("fnCash", fnCash);
		return msg;
	}

	public boolean isHoliday(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
			GateMessage message = createGateMessage(fromDate, document.getOffice().getSynchKey());
			Document responce = service.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, "", null, null));
			Element isHolidayElement = XmlHelper.selectSingleNode(responce.getDocumentElement(), "isHoliday");
			String isHoliday = isHolidayElement.getTextContent();
			return Boolean.getBoolean(isHoliday);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}
