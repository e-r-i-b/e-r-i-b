package com.rssl.phizicgate.wsgate.services.notification;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author: Pakhomova
 * @created: 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSubscribeServiceWrapper extends JAXRPCClientSideServiceBase<NotificationSubscribeService_Stub> implements NotificationSubscribeService
{
	public NotificationSubscribeServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/NotificationSubscribeServiceImpl";

		NotificationSubscribeServiceImpl_Impl service = new NotificationSubscribeServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((NotificationSubscribeService_Stub) service.getNotificationSubscribeServicePort(), url);
	}

	/**
	 *
	 * @param account
	 * @throws GateException
	 */
	public void subscribeAccount(Account account) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.notification.generated.Account convertedAccount = new com.rssl.phizicgate.wsgate.services.notification.generated.Account();

			BeanHelper.copyPropertiesWithDifferentTypes(convertedAccount, account, NotificationSubscribeServiceTypesCorrelation.types);

			String encoded = encodeData(convertedAccount.getId());
			convertedAccount.setId(encoded);

			getStub().subscribeAccount(convertedAccount);

		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

	}

	public void unsubscribeAccount(Account account) throws GateException, GateLogicException
	{
		try
			{
				com.rssl.phizicgate.wsgate.services.notification.generated.Account convertedAccount = new com.rssl.phizicgate.wsgate.services.notification.generated.Account();

				BeanHelper.copyPropertiesWithDifferentTypes(convertedAccount, account, NotificationSubscribeServiceTypesCorrelation.types);

				String encoded = encodeData(convertedAccount.getId());
				convertedAccount.setId(encoded);
				getStub().unsubscribeAccount(convertedAccount);

			}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
