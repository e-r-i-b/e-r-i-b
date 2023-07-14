package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.common.types.documents.State;

/**
 * Сохраняет риск профиль клиента
 * @author komarov
 * @ created 26.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ChangePersonRiskProfileOperation extends EditPfpOperationBase
{
	private static final State SHOW_RISK_PROFILE_FORM = new State("SHOW_RISK_PROFILE_FORM");

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(SHOW_RISK_PROFILE_FORM);
	}

	/**
	 * Сохраняет профиль
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

}
