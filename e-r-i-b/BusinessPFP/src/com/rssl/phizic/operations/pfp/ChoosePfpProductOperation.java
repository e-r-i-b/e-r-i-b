package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * @author mihaylov
 * @ created 15.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChoosePfpProductOperation extends EditPfpOperationBase implements ListEntitiesOperation
{
	private static final State EDIT_PERSON_PORTFOLIOS = new State("EDIT_PERSON_PORTFOLIOS");

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(EDIT_PERSON_PORTFOLIOS);
	}

	@QueryParameter
	public Integer getClientAge()
	{
		return PersonHelper.getPersonAge(getPerson());
	}

	@QueryParameter
	public String getClientSegment()
	{
		return getCurrentClientSegment().name();
	}
}
