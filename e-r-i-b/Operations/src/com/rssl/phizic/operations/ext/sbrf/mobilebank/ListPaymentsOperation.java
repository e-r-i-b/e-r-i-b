package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��������� ������ �������� ���������� �����
 */
public class ListPaymentsOperation extends MobileBankOperationBase
{
	public void initialize() throws BusinessException
	{
		super.initialize();
	}

	/**
	 * ���������� ������� ����������� �� ���� ������ �������� � ���� ������ �����
	 * @param phoneCode - ��� ������ ��������
	 * @param cardCode - ��� �����
	 * @return ������� ����������� ��� null, ���� �� ������
	 */
	public RegistrationProfile getRegistrationProfile(String phoneCode, String cardCode) throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationProfile(getLogin(), phoneCode, cardCode);
	}
}
