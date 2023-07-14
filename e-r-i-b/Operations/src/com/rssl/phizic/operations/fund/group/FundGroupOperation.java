package com.rssl.phizic.operations.fund.group;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundGroupService;
import com.rssl.phizic.operations.OperationBase;

/**
 * @auhor: tisov
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 * Базовая операция по управлению группой получателей
 */
public abstract class FundGroupOperation extends OperationBase
{
	protected FundGroupService fundGroupService = new FundGroupService();

	public abstract String getForwardName();

	public abstract void initialize(Long id, String name, String phones) throws BusinessException, BusinessLogicException;

}
