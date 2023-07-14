package com.rssl.phizic.test.webgate.sofia.jaxrpc;

import com.rssl.phizic.test.webgate.sofia.jaxrpc.generated.WebBankServiceIF;
import com.rssl.phizic.test.webgate.sofia.common.MessageHelper;

import java.rmi.RemoteException;

/**
 * User: Moshenko
 * Date: 28.09.2010
 * Time: 17:31:17
 */
public class WebBankServiceBean implements WebBankServiceIF
{
	public String sendMessage(String message) throws RemoteException
	{
		  return new MessageHelper().getCurentMessage(message);
	}
}
