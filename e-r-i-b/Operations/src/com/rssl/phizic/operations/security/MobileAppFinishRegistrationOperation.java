package com.rssl.phizic.operations.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.Map;

/**
 * Операция завершения регистрации(установка PIN) и входа в mAPI
 * @author Jatsky
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class MobileAppFinishRegistrationOperation extends MobileAppRegistrationOperation
{
	/**
	 *
	 * @param data Данные формы
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public MobileAppFinishRegistrationOperation(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		super(data);
	}

	@Override protected void confirmReg(Map<String, Object> data, String mGUID) throws BusinessLogicException, BusinessException
	{
		return;
	}
}