package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 11.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class BackInPassingPFPOperation extends EditPfpOperationBase
{
	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException
	{
		if (isEmployee())
			return;

		if (profile.isCompleted() && !isAvailableStartPlaning())
			throw new AccessException("Невозможно отредактировать пройденное пданирование.");
	}

	/**
	 * вернуться на пердыдущий шаг планирования 
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void back() throws BusinessException, BusinessLogicException
	{
		fireEvent(DocumentEvent.BACK);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
