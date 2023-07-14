package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author mihaylov
 * @ created 25.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция отображения риск профиля клиента.
 */
public class ShowPfpRiskProfileOperation extends EditPfpOperationBase
{
	private static final State SHOW_RISK_PROFILE_FORM = new State("SHOW_RISK_PROFILE_FORM");
	
	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(SHOW_RISK_PROFILE_FORM);
	}

	public void nextStep() throws BusinessException, BusinessLogicException
	{
		getExecutor().fireEvent(new ObjectEvent(DocumentEvent.INIT_PERSON_PORTFOLIOS,ObjectEvent.CLIENT_EVENT_TYPE));
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
