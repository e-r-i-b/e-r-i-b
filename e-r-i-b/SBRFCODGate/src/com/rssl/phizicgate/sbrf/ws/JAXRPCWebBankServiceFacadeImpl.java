package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankService;
import com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankServiceIF;
import com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankService_Impl;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author Roshka
 * @ created 09.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class JAXRPCWebBankServiceFacadeImpl extends CODWebBankServiceSupport
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	public static final String ENDPOINT_COD = "cod";

	private StubTimeoutUrlCodWrapper wrapper;

	public JAXRPCWebBankServiceFacadeImpl(GateFactory factory) throws GateException
	{
		super(factory);

		//ЦОД
		wrapper = new StubTimeoutUrlCodWrapper();
	}

	protected MessageData makeRequest(MessageData request, MessageInfo messageInfo) throws GateException, GateLogicException
	{
		String response;
		try
		{
			if (messageInfo.getEndpoint().equals(ENDPOINT_COD))
				response = wrapper.getStub().sendMessage(request.getBodyAsString(null));
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

		CODMessageData responceMessage = new CODMessageData();
		responceMessage.setBody(response);

		return responceMessage;
	}

	private class StubTimeoutUrlCodWrapper extends StubTimeoutUrlWrapper<WebBankServiceIF>
	{
		protected String getUrl()
		{
			try
			{
				return ServiceReturnHelper.getInstance().getEndURL(getFactory());
			}
			catch (GateException e)
			{
				throw new RuntimeException(e);
			}
		}

		protected void createStub()
		{
			WebBankService service = new WebBankService_Impl();
			try
			{
				setStub(service.getWebBankServiceIFPort());
			}
			catch (ServiceException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}