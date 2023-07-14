package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.manager.services.objects.CancelationCallBackWithRouteInfo;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author hudyakov
 * @ created 02.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceImpl extends PersistentServiceBase<RegistartionClientService> implements RegistartionClientService
{
	public RegistartionClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		delegate.register(removeRouteInfo(office), removeRouteInfo(registerRequest));
	}

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		delegate.update(removeRouteInfo(office), removeRouteInfo(registerRequest));
	}

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		delegate.update(client, lastUpdateDate, isNew, user);
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		CancelationCallBack callback = delegate.cancellation(removeRouteInfo(client), trustingClientId, date, type, reason);
		return new CancelationCallBackWithRouteInfo(callback, getRouteInfo());
	}

	//Убираем суффиксы "|<id_внешней_системы>"
	private Document removeRouteInfo(Document document) throws GateException
	{
		try
		{
			restoreElementValue(document, "/agreementRegistration_q/owner/id");
			restoreElementValue(document, "/agreementRegistration_q/trustingClient");
			restoreElementValue(document, "/agreementModification_q/clientId");
			return document;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private void restoreElementValue(Document registerRequest, String xpath) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(registerRequest.getDocumentElement(), xpath);
		if (element != null)
		{
			String clientId = element.getTextContent();
			element.setTextContent(removeRouteInfo(clientId));
		}
	}
}
