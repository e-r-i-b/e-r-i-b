package com.rssl.phizic.business.profile.smartaddressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author bogdanov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */

public class MBKPhoneLoaderCallBack implements com.rssl.phizgate.common.profile.MBKPhoneLoaderCallBack
{
	private static final AvatarJurnalService jurnalService = new AvatarJurnalService();

	public void onAdd(String phone)
	{
	}

	public void onDelete(String phone) throws GateException
	{
		try
		{
			jurnalService.deleteAvatarInner(phone);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
