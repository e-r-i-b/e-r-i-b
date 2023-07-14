package com.rssl.phizic.operations.dictionaries.mobileoperators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileOperators.MobileOperator;
import com.rssl.phizic.business.mobileOperators.MobileOperatorService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowMobileOperatorsOperation extends OperationBase implements ListEntitiesOperation
{
	private static final MobileOperatorService mobileOperatorService = new MobileOperatorService();
	private List<MobileOperator> operators = null;

	public void init() throws BusinessException
	{
		operators = mobileOperatorService.getAllSortByName();
	}

	public List<MobileOperator> getMobileOperators()
	{
		return operators;
	}
}
