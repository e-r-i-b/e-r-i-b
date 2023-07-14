package com.rssl.phizic.business.security.auth;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Gulov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка существования клиента в МБ
 */
public class GuestMobileBankExist implements StageVerifier
{
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		GuestLogin guestLogin  = (GuestLogin) context.getLogin();
		if (StringHelper.isNotEmpty(context.getUserAlias()))
			return false;
		if (GuestClaimType.debet_card.equals(context.getGuestClaimType()))
			return false;
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			boolean mobileBankExist = CollectionUtils.isNotEmpty(mobileBankService.getCardsByPhone(guestLogin.getAuthPhone()));
			AuthenticationContext.getContext().setMB(mobileBankExist);
			return mobileBankExist;
		}
		catch (GateException e)
		{
			throw new SecurityException(e);
		}
		catch (GateLogicException e)
		{
			throw new SecurityException(e);
		}
	}
}
