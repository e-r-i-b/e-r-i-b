package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.rmi.RemoteException;
import javax.xml.validation.Schema;

/**
 * @author mihaylov
 * @ created 08.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class EsbEribBackServiceImpl implements EsbEribDepoBackService
{
	private static Schema schema;
	private static final String SCHEMA_FILE = "com/rssl/phizic/ws/esberiblistener/depo/xml/depo.xsd";

	private static final String DOC_STATE_UPDATE_TAG = "DocStateUpdateRq";
	private static final String SEC_DIC_UPDATE_TAG = "SecDicInfoRq";

	public EsbEribBackServiceImpl() throws RemoteException
	{
		try
		{
			schema = XmlHelper.schemaByFileName(SCHEMA_FILE);
		}
		catch (SAXException e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new RemoteException("Internal error", e);
		}
	}

	public String doIFX(String req) throws RemoteException
	{
		validate(req);

		return makeResponse(req);
	}

	private String makeResponse(String req) throws RemoteException
	{
		try
		{
			Document documentRequest = XmlHelper.parse(req);
			Element root = documentRequest.getDocumentElement();
			Document response = null;
			if(DOC_STATE_UPDATE_TAG.equals(root.getNodeName()))
				response = getDocumentStateUpdater(documentRequest).updateDocumentsState();
			else if(SEC_DIC_UPDATE_TAG.equals(root.getNodeName()))
			{
				response = getSecurityDicUpdater(documentRequest).updateSecurityDictionary();
			}
			return XmlHelper.convertDomToText(response);
		}
		catch (Exception e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new RemoteException("Internal error", e);
		}
	}

	protected EsbEribDocumentUpdaterBase getDocumentStateUpdater(Document docStateUpdateRq)
	{
		return new EsbEribDocumentUpdater(docStateUpdateRq);
	}

	protected EsbEribSecurityDicUpdater getSecurityDicUpdater(Document secDicInfoRq)
	{
		return new EsbEribSecurityDicUpdater(secDicInfoRq);
	}

	/**
	 * Валидация сообщения
	 * @param xmlString - сообщение
	 * @throws java.rmi.RemoteException
	 */
	private void validate(String xmlString) throws java.rmi.RemoteException
	{
		try
		{
			XSDSchemeValidator.validate(schema, xmlString);
		}
		catch (ValidateException e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new RemoteException("Internal error", e);
		}
	}
}
