package com.rssl.phizicgate.manager.services.routablePersistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.manager.services.objects.CancelationCallBackWithRouteInfo;
import com.rssl.phizicgate.manager.services.objects.ClientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceImpl extends RoutablePersistentServiceBase<RegistartionClientService> implements RegistartionClientService
{
	public RegistartionClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected RegistartionClientService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(RegistartionClientService.class);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		endService(officeInner.getRouteInfo()).register(officeInner, removeRouteInfo(registerRequest));
	}

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		endService(officeInner.getRouteInfo()).update(officeInner, removeRouteInfo(registerRequest));
	}

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		endService(clientInner.getRouteInfo()).update(clientInner, lastUpdateDate, isNew, user);
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		CancelationCallBack callback = endService(clientInner.getRouteInfo()).cancellation(clientInner, trustingClientId, date, type, reason);
		return new CancelationCallBackWithRouteInfo(callback, clientInner.getRouteInfo());
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
