package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 11.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���������� � ���������� ������������
 */
public class ReplanPFPOperation extends StartPlaningOperationBase
{
	/**
	 * ���������� � ���������� ������������
	 * @throws BusinessException
	 */
	public void replan() throws BusinessException, BusinessLogicException
	{
		pfpService.removeNotCompleted(getPerson().getLogin());
		initialize((PersonalFinanceProfile) null);
		personalFinanceProfile = pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
