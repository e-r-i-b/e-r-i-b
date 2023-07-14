package com.rssl.phizic.test.webgate.ryazan.common;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.rmi.RemoteException;

/**
 * User: Moshenko
 * Date: 28.09.2010
 * Time: 16:11:40
 */
public class MessageUpdate
{

	public String echoString(String sendMessageRequest) throws RemoteException
	{
		return sendMessageRequest;
	}

	public String requestAttr(String sendMessageRequest) throws RemoteException
	{
		String codeRecipient = getCode(true,sendMessageRequest);
		String codeService = getCode(false,sendMessageRequest);
	    String result = getDocument("com/rssl/phizic/test/webgate/ryazan/xml/requestBillingAttrA.xml");
		result = result.replaceFirst("CODE_RECIPIENT",codeRecipient);
		result = result.replaceFirst("CODE_SERVICE",codeService);
		return result;
	}

	public String preparePayment(String sendMessageRequest) throws RemoteException
	{
		String codeRecipient = getCode(true,sendMessageRequest);
		String codeService = getCode(false,sendMessageRequest);
		String result = getDocument("com/rssl/phizic/test/webgate/ryazan/xml/prepareBillingPaymentA.xml");
		result = result.replaceFirst("CODE_RECIPIENT",codeRecipient);
		result = result.replaceFirst("CODE_SERVICE",codeService);
		return result;
	}

	public String executePayment(String sendMessageRequest) throws RemoteException
	{
		String codeRecipient = getCode(true,sendMessageRequest);
		String codeService = getCode(false,sendMessageRequest);
		String result = getDocument("com/rssl/phizic/test/webgate/ryazan/xml/executeBillingPaymentA.xml");
		result = result.replaceFirst("CODE_RECIPIENT",codeRecipient);
		result = result.replaceFirst("CODE_SERVICE",codeService);
		return result;
	}

	public String revokePayment(String sendMessageRequest) throws RemoteException
	{
		return getDocument("com/rssl/phizic/test/webgate/ryazan/xml/revokeBillPaymentA.xml");
	}

	private String getDocument(String path) throws RemoteException
	{
		try
		{
			Document result = XmlHelper.loadDocumentFromResource(path);
			return XmlHelper.convertDomToText(result);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}


	//true  -  <CodeRecipient>
	//false -  <CodeService>
	private String getCode(boolean code,String targetMessage)
	{
		return  code == true ? targetMessage.split("<CodeRecipient>")[1].split("</CodeRecipient>")[0] : targetMessage.split("<CodeService>")[1].split("</CodeService>")[0];
	}


}
