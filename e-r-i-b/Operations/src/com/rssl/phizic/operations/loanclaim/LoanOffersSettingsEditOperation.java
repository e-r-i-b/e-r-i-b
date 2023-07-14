package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

/**
 * @author Nady
 * @ created 11.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция редактирования настроек для кредитных оферт.
 */
public class LoanOffersSettingsEditOperation extends EditPropertiesOperation<Restriction>
{
	public void save() throws BusinessException, BusinessLogicException
	{
		super.save();
	}
}
