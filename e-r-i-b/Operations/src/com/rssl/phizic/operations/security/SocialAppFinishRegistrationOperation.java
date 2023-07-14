package com.rssl.phizic.operations.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.Map;

/**
 * �������� ���������� �����������(��������� PIN) � ����� � socialAPI
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialAppFinishRegistrationOperation extends SocialAppRegistrationOperation
{
	/**
	 *
	 * @param data ������ �����
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public SocialAppFinishRegistrationOperation(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		super(data);
	}

	@Override protected void confirmReg(Map<String, Object> data, String mGUID) throws BusinessLogicException, BusinessException
	{
		return;
	}
}