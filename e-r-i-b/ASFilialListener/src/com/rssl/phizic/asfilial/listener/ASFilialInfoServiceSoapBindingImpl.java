/**
 * ASFilialInfoServiceSoapBindingImpl.java
 *
 */

package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.asfilial.listener.generated.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClassHelper;

import java.rmi.RemoteException;

public class ASFilialInfoServiceSoapBindingImpl implements com.rssl.phizic.asfilial.listener.generated.ASFilialInfoService
{
	private static final ASFilialInfoService delegateService = createASFilialInfoService();

	public QueryProfileRsType queryProfile(QueryProfileRqType parameters) throws RemoteException
	{
		return delegateService.queryProfile(parameters);
	}

	public UpdateProfileRsType updateProfile(UpdateProfileRqType parameters) throws RemoteException
	{
		return delegateService.updateProfile(parameters);
	}

	public ConfirmPhoneHolderRsType confirmPhoneHolder(ConfirmPhoneHolderRqType parameters) throws RemoteException
	{
		return delegateService.confirmPhoneHolder(parameters);
	}

	public RequestPhoneHolderRsType requestPhoneHolder(RequestPhoneHolderRqType parameters) throws RemoteException
	{
		return delegateService.requestPhoneHolder(parameters);
	}

	private static ASFilialInfoService createASFilialInfoService()
	{
		ASFilialConfig config = ConfigFactory.getConfig(ASFilialConfig.class);
		try
		{
			Class backServiceListener = ClassHelper.loadClass(config.getListenerClassKey());
			return (ASFilialInfoService) backServiceListener.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
