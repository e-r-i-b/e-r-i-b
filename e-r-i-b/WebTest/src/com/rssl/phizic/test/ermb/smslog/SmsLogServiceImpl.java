package com.rssl.phizic.test.ermb.smslog;

import com.rssl.phizic.test.ermb.smslog.generated.*;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Gulov
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class SmsLogServiceImpl implements ErmbSmsLogService
{
	private static final String XML_FILE_NAME = "com/rssl/phizic/test/ermb/smslog/response.xml";
	private static final BigInteger ERROR_CODE = BigInteger.valueOf(-1);

    public com.rssl.phizic.test.ermb.smslog.generated.SmsLogRs_Type getMessageLog(com.rssl.phizic.test.ermb.smslog.generated.SmsLogRq_Type req) throws java.rmi.RemoteException
    {
	    SmsLogRs_Type result = new SmsLogRs_Type();
	    result.setRqUID(req.getRqUID());
	    Document document = loadDocument();
	    Element root = document.getDocumentElement();
	    if (!XmlHelper.tagTest("logEntries", root))
	    {
			result.setStatus(ERROR_CODE);
		    return result;
	    }
	    result.setStatus(BigInteger.ZERO);
	    Response_Type response = new Response_Type();
	    result.setResponse(response);
	    makeLogEntries(root, response);
	    response.setSessionID("1234");
	    return result;
    }

	private void makeLogEntries(Element root, Response_Type response) throws RemoteException
	{
		final List<LogEntry_Type> logEntries = new ArrayList<LogEntry_Type>();
		try
		{
			XmlHelper.foreach(root, "logEntries/logEntry", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					LogEntry_Type entry = new LogEntry_Type();
					entry.setLogNum(Long.parseLong(getElementText(element, "logNum")));
					makeMessage(element, entry);
					makeClient(element, entry);
					entry.setSystem(getElementText(element, "system"));
					Element temp = exists(element, "extinfo");
					if (temp != null)
						entry.setExtinfo(XmlHelper.getElementText(temp));
					logEntries.add(entry);
				}
			});
		}
		catch (Exception e)
		{
			throw new RemoteException("Ошибка при обработке записей сообщений", e);
		}
		Element temp = exists(root, "hasMoreMessages");
		if (temp != null)
			response.setHasMoreMessages(Boolean.valueOf(XmlHelper.getElementText(temp)));
		LogEntry_Type[] entries = new LogEntry_Type[logEntries.size()];
		logEntries.toArray(entries);
		response.setLogEntries(entries);
	}

	private void makeClient(Element root, LogEntry_Type entry) throws RemoteException
	{
		Element clientElement = exists(root, "client");
		if (clientElement == null)
			return;
		ClientRs_Type client = new ClientRs_Type();
		entry.setClient(client);
		client.setLastname(getElementText(clientElement, "lastname"));
		client.setFirstname(getElementText(clientElement, "firstname"));
		client.setTb(getElementText(clientElement,"tb"));
		Element temp = exists(clientElement, "middlename");
		if (temp != null)
			client.setMiddlename(XmlHelper.getElementText(temp));
		temp = exists(clientElement, "birthday");
		if (temp != null)
			client.setBirthday(XMLDatatypeHelper.parseDate(XmlHelper.getElementText(temp)).getTime());
		makeIdentityCard(clientElement, client);
	}

	private void makeIdentityCard(Element root, ClientRs_Type client) throws RemoteException
	{
		Element identityCardElement = getElement(root, "identityCard");
		IdentityCard_Type identityCard = new IdentityCard_Type();
		client.setIdentityCard(identityCard);
		identityCard.setIdType(getElementText(identityCardElement, "idType"));
		Element temp = exists(identityCardElement, "idSeries");
		if (temp != null)
			identityCard.setIdSeries(XmlHelper.getElementText(temp));
		identityCard.setIdNum(getElementText(identityCardElement, "idNum"));
		temp = exists(identityCardElement, "issuedBy");
		if (temp != null)
			identityCard.setIssuedBy(XmlHelper.getElementText(temp));
		temp = exists(identityCardElement, "issueDt");
		if (temp != null)
			identityCard.setIssueDt(XMLDatatypeHelper.parseDate(XmlHelper.getElementText(temp)).getTime());
	}

	private void makeMessage(Element root, LogEntry_Type entry) throws RemoteException
	{
		String type = getElementText(root, "messageType");
		if (!("in".equals(type) || "out".equals(type) || "both".equals(type)))
		{
			throw new RemoteException("Неверный тип сообщения в заглушке");
		}
		entry.setMessageType(type);
		if ("in".equals(type) || "both".equals(type))
		{
			MessageIn_Type in = new MessageIn_Type();
			entry.setMessageIn(in);
			Element element = getElement(root, "messageIn");
			in.setRqID(getElementText(element, "rqID"));
			in.setPhone(getElementText(element, "phone"));
			in.setText(getElementText(element, "text"));
			in.setReceiveTime(XMLDatatypeHelper.parseDateTime(getElementText(element, "receiveTime")));
		}
		if ("out".equals(type) || "both".equals(type))
		{
			MessageOut_Type out = new MessageOut_Type();
			entry.setMessageOut(out);
			Element messageOut = getElement(root, "messageOut");
			out.setRqID(getElementText(messageOut, "rqID"));
			out.setPhone(getElementText(messageOut, "phone"));
			out.setText(getElementText(messageOut, "text"));
			out.setCreateTime(XMLDatatypeHelper.parseDateTime(getElementText(messageOut, "createTime")));

			Element temp = exists(messageOut, "sendTime");
			if (temp != null)
				out.setSendTime(XMLDatatypeHelper.parseDateTime(XmlHelper.getElementText(temp)));
			temp = exists(messageOut, "sendStatus");
			if (temp != null)
				out.setSendStatus(XmlHelper.getElementText(temp));
			temp = exists(messageOut, "smsCount");
			if (temp != null)
				out.setSmsCount(BigInteger.valueOf(Long.parseLong(XmlHelper.getElementText(temp))));
			temp = exists(messageOut, "deliverTime");
			if (temp != null)
				out.setDeliverTime(XMLDatatypeHelper.parseDateTime(XmlHelper.getElementText(temp)));
			temp = exists(messageOut, "deliverStatus");
			if (temp != null)
				out.setDeliverStatus(XmlHelper.getElementText(temp));
			temp = exists(messageOut, "resource");
			if (temp != null)
			{
				ResourceId_Type resource = new ResourceId_Type();
				out.setResource(resource);
				makeResource(getElement(messageOut, "resource"), resource);
			}
		}
	}

	private Element exists(Element root, String tagName)
	{
		NodeList nodeList = root.getElementsByTagName(tagName);
		if (nodeList.getLength() > 0)
			return (Element) nodeList.item(0);
		return null;
	}

	private void makeResource(Element root, ResourceId_Type resource) throws RemoteException
	{
		String type = getElementText(root, "type");
		if ("account".equals(type) || "card".equals(type) || "loan".equals(type))
		{
			resource.setType(type);
			String value = getElementText(root, type);
			if ("account".equals(type))
				resource.setAccount(value);
			else if ("card".equals(type))
				resource.setCard(value);
			else
				resource.setLoan(value);
		}
	}

	private String getElementText(Element root, String tagName) throws RemoteException
	{
		return XmlHelper.getElementText(getElement(root, tagName));
	}

	private Element getElement(Element root, String tagName) throws RemoteException
	{
		try
		{
			return XmlHelper.selectSingleNode(root, tagName);
		}
		catch (TransformerException e)
		{
			throw new RemoteException("Не найден тег " + tagName);
		}
	}

	private Document loadDocument() throws RemoteException
	{
		try
		{
			return XmlHelper.loadDocumentFromResource(XML_FILE_NAME);
		}
		catch (Exception e)
		{
			throw new RemoteException("Ошибка загрузки файла заглушки " + XML_FILE_NAME, e);
		}
	}
}
