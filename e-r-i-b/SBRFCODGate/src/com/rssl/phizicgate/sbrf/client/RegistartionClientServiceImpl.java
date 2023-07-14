package com.rssl.phizicgate.sbrf.client;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizgate.ext.sbrf.common.messaging.CODFormatResponseHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceImpl extends AbstractService implements RegistartionClientService
{
	public RegistartionClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		getWebService().sendOnlineMessage( registerRequest, null );
	}

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		getWebService().sendOnlineMessage( registerRequest, null );
	}

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Регистрация клиента по клиенту не поддерживается.");
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		Document responce = getWebService().sendOfflineMessage( createAgreementCancelation(client.getId(),trustingClientId,date,type), null );
		CancelationCallBackImpl callback = new CancelationCallBackImpl();
		callback.setId(getMessageIdFromResponce(responce));
		return callback;

	}

	private WebBankServiceFacade getWebService()
	{
		return getFactory().service(WebBankServiceFacade.class);
	}

	private String getMessageIdFromResponce(Document responce)
	{
		if(responce!=null)
			//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
			return XmlHelper.getSimpleElementValue(responce.getDocumentElement(), CODFormatResponseHandler.MESSAGE_ID_TAG);
		else
			return null;
	}

	private Document createAgreementCancelation(String clientId, String trustingClientId, Calendar date, CancelationType type)
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();
		Element  root     = null;
		switch(type)
		{
			case admin_request:root = document.createElement("agreementCancellationMandatory_q");break;
			case client_request:root = document.createElement("agreementCancellation_q");break;
			case without_charge:root = document.createElement("agreementCancellationWithoutCharge_q");break;
		}

		document.appendChild(root);
		if(date!=null)
		{
			XmlHelper.appendSimpleElement(root, "stopDate", DateHelper.toXMLDateFormat(date.getTime()));
		}
		else
		{
			XmlHelper.appendSimpleElement(root, "stopDate", DateHelper.toXMLDateFormat(DateHelper.getCurrentDate().getTime()));	
		}
		XmlHelper.appendSimpleElement(root, "id", clientId);
		if(trustingClientId!=null)
		{
			XmlHelper.appendSimpleElement(root, "trustingClientId", trustingClientId);
		}

		return document;
	}
}
