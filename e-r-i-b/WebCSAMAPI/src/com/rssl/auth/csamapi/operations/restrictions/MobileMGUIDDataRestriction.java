package com.rssl.auth.csamapi.operations.restrictions;

import com.rssl.auth.csamapi.exceptions.RegistrationErrorException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 * Ограничение на использование mGUID
 */
public class MobileMGUIDDataRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		String guid = (String) data.get(Constants.MGUID_FIELD);

		if (StringHelper.isEmpty(guid))
		{
			throw new RegistrationErrorException();
		}

		return true;
	}
}
