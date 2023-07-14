package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Ограничение на использование mGUID
 *
 * @author khudyakov
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileMGUIDDataRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		String mGUID = (String) data.get("mGUID");
		if (StringHelper.isEmpty(mGUID))
			throw new RegistrationErrorException();

		return true;
	}
}
