package com.rssl.phizicgate.sbcms.messaging;

import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizicgate.sbcms.messaging.mock.MockCMSMessagingService;
import com.rssl.phizicgate.sbcms.messaging.cache.MessagesCacheManagerImpl;
import com.rssl.phizicgate.sbcms.bankroll.CardControlInfoCalculator;
import com.rssl.phizicgate.sbcms.ws.generated.PosGate_ServiceLocator;
import com.rssl.phizicgate.sbcms.ws.generated.PosGateSOAPStub;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import java.io.StringReader;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CMSMessagingService extends MessagingServiceSupport
{
	public static final String ENDPOINT_CMS = "cms";

	private static CMSMessagingService service = null;

	public static PosGateSOAPStub binding;
	private static final String errorResponseTagName = "WAY4_ERROR";
	private static final String errorTypeTagName = "ERR_CODE";
	private static final String errorMessTagName = "ERR_MESS";
	private static final String errorTypeClient = "ERROR_CLIENT";

	public CMSMessagingService() throws GateException
	{
		super(GateSingleton.getFactory(), MessageLogService.getMessageLogWriter(),
				"SBCMS", ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return MessagesCacheManagerImpl.getInstance();
	}

	/**
	 * получаем инстанс сервиса
	 * @return CMSMessagingService
	 * @throws GateException
	 */
	public static synchronized CMSMessagingService getInstance() throws GateException
	{
		if (service != null)
			return service;
		try
		{
			if(CMSConfig.IsMock())
				service = new MockCMSMessagingService();
			else
			{
				service = new CMSMessagingService();
				PosGate_ServiceLocator serviceLocator = new PosGate_ServiceLocator();
				serviceLocator.setPosGateSOAPEndpointAddress(CMSConfig.getProperty(CMSConfig.URL_WS_CMS_KEY));
				binding = (PosGateSOAPStub) serviceLocator.getPosGateSOAP();

			}
			return service;
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
	}

	public GateMessage createRequest(String proCodeName, Card card) throws GateException
	{
		try
		{
			GateMessage msg = super.createRequest("WAY4_REQUEST");
			msg.addParameter("PAN", card.getNumber());		
			msg.addParameter("DIG_CODE", CardControlInfoCalculator.getCardControlInfo(""));
			msg.addParameter("PRO_CODE", proCodeName);
			return msg;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		String response;
		try
		{
			response = binding.pqQuery(messageData.getBodyAsString(null));
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		CMSMessageData responceMessageData = new CMSMessageData();
		responceMessageData.setBody(response);
		return responceMessageData;
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		CMSMessageData     cmsResponse = (CMSMessageData) response;
		CMSResponseHandler cmsHandler  = new CMSResponseHandler(messageInfo);
		SAXParserFactory factory     = SAXParserFactory.newInstance();
		SAXParser saxParser;

		try
		{
			saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(cmsResponse.getBody())), cmsHandler);
			cmsResponse.setId(cmsHandler.getMessageId());
			if (cmsHandler.getResponceTag().equals(errorResponseTagName))
			{
				Document document = (Document) cmsHandler.getBody();
				String errorType = document.getDocumentElement().getElementsByTagName(errorTypeTagName).item(0).getTextContent();
				String errorMessage = document.getDocumentElement().getElementsByTagName(errorMessTagName).item(0).getTextContent();
				if (errorType.equals(errorTypeClient))
					throw new GateLogicException(errorMessage);
				else
					throw new GateException(errorMessage);
			}
//todo. Комментарии оставлены на случай, если из ПЦ сообщение все таки с <message>...</message> придет. Убрать как станет понятно.
//			cmsResponse.setDate(cmsHandler.getMessageDate());
//			cmsResponse.setAbonent(cmsHandler.getFromAbonent());
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
		catch(ParserConfigurationException e)
		{
			throw new GateException(e);
		}

		return cmsHandler;
	}
}
