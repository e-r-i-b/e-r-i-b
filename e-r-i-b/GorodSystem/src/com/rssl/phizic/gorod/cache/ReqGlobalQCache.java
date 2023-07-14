package com.rssl.phizic.gorod.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/*
Кешируемое сообщение
<?xml version="1.0" encoding="windows-1251"?>
<RSASMsg xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="gorod.xsd">
    <ReqGlobal>
        <Credential>
            <pan>9910050002999999</pan>
            <pin>0781</pin>
        </Credential>
        <ListOfMoreInfo request="false">
            <moreInfo action="stop" maxItems="all" request="true" xpRef="/RSASMsg/AnsGlobal/Global/ListOfAbonent">
                <ListOfFilterVal>
                    <val code="SERVICEID">99959118</val>
                    <val code="ACCOUNT">13652</val>
                    <val code="plusAddress">false</val>
                </ListOfFilterVal>
            </moreInfo>
            <moreInfo action="stop" maxItems="all" request="true" xpRef="/RSASMsg/AnsGlobal/Global/ListOfAbonent/Abonent"/>
        </ListOfMoreInfo>
        <TerminalID>00000000019999</TerminalID>
    </ReqGlobal>
</RSASMsg>
*/

public class ReqGlobalQCache extends MessagesCache
{
	private static final String SERVICE_ID_KEY = "/ReqGlobal/ListOfMoreInfo/moreInfo/ListOfFilterVal/val[@code='SERVICEID']";
	private static final String ACCOUNT_KEY = "/ReqGlobal/ListOfMoreInfo/moreInfo/ListOfFilterVal/val[@code='ACCOUNT']";
	private List<Class> classes;

	protected String getCacheName()
	{
		return ReqGlobalQCache.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			String serviceKey = XmlHelper.selectSingleNode(request.getDocumentElement(), SERVICE_ID_KEY).getTextContent();
			String accountKey = XmlHelper.selectSingleNode(request.getDocumentElement(), ACCOUNT_KEY).getTextContent();
			return  serviceKey + "|" + accountKey;

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

	public void clear(Object object) throws GateException, GateLogicException
	{
		
	}
}
