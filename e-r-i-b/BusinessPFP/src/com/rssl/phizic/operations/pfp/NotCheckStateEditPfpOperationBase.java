package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция прохождения персонального финансового планирования, не проверяющая статус планирования
 */
public abstract class NotCheckStateEditPfpOperationBase extends EditPfpOperationBase
{
	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException {}
}
