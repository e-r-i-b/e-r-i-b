package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub;
import com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceLocator;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class WebBankServiceFacadeImpl extends CODWebBankServiceSupport
{
	public static final String ENDPOINT_COD = "cod";
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	/**
	 * ctor
	 * @param factory фабрика
	 */
    public WebBankServiceFacadeImpl(GateFactory factory) throws GateException
	{
        super(factory);
    }

	private WebBankServiceIFBindingStub getStub() throws GateException
	{
		try
        {
            //ЦОД
	        WebBankServiceLocator codLocator = new WebBankServiceLocator();
			codLocator.setWebBankServiceIFPortEndpointAddress(ServiceReturnHelper.getInstance().getEndURL(getFactory()));
	        Long timeOutValue = ConfigFactory.getConfig(GateConnectionConfig.class).getConnectionTimeout();
	        if(timeOutValue != null)
				((WebBankServiceIFBindingStub)codLocator.getWebBankServiceIFPort()).setTimeout(timeOutValue.intValue());
            return (WebBankServiceIFBindingStub) codLocator.getWebBankServiceIFPort();

        }
        catch (ServiceException e)
        {
            throw new GateException(e);
        }
	}

    protected MessageData makeRequest(MessageData request, MessageInfo messageInfo) throws GateException, GateTimeOutException
	{
		String response;
		try
		{
			if( messageInfo.getEndpoint().equals(ENDPOINT_COD))
				response = getStub().sendMessage(request.getBodyAsString(null));
			else
				throw new GateException("Undefined gate type - " + messageInfo.getEndpoint());
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("получена ошибка таймаута при отправке сообщения к ШЕСК.");
				throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
			}
			throw new GateException(e);
		}

		CODMessageData responceMessageData = new CODMessageData();
		responceMessageData.setBody(response);

		return responceMessageData;
	}
}