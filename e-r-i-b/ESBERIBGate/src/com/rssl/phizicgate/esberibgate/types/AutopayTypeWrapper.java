package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizicgate.esberibgate.ws.generated.AutopayStatus_Type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 23.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutopayTypeWrapper
{
	private static final Map<AutopayStatus_Type, AutoPayStatusType> accountState = new HashMap<AutopayStatus_Type, AutoPayStatusType>();

	static
	{
		accountState.put(AutopayStatus_Type.New, AutoPayStatusType.New);
		accountState.put(AutopayStatus_Type.OnRegistration, AutoPayStatusType.OnRegistration);
		accountState.put(AutopayStatus_Type.Confirmed, AutoPayStatusType.Confirmed);
		accountState.put(AutopayStatus_Type.Active, AutoPayStatusType.Active);
		accountState.put(AutopayStatus_Type.WaitForAccept, AutoPayStatusType.WaitForAccept);
		accountState.put(AutopayStatus_Type.ErrorRegistration, AutoPayStatusType.ErrorRegistration);
		accountState.put(AutopayStatus_Type.Paused, AutoPayStatusType.Paused);
		accountState.put(AutopayStatus_Type.Closed, AutoPayStatusType.Closed);
		accountState.put(AutopayStatus_Type.WaitingForActivation, AutoPayStatusType.WaitingForActivation);
	}

	public static AutoPayStatusType getAccountState(AutopayStatus_Type autoPayStatus)
	{
		return accountState.get(autoPayStatus);
	}
}
