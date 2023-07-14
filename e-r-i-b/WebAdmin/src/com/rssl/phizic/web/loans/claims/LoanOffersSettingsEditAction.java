package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loanclaim.LoanOffersSettingsEditOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
/**
 * @author Nady
 * @ created 11.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн редактировани€ настроек дл€ кредитных оферт.
 */
public class LoanOffersSettingsEditAction  extends EditPropertiesActionBase<LoanOffersSettingsEditOperation>
{
	@Override
	protected LoanOffersSettingsEditOperation getEditOperation() throws BusinessException
	{
		return createOperation(LoanOffersSettingsEditOperation.class);
	}
}
