package com.rssl.phizic.operations.loanclaim.officeclaim;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.loanclaim.officeClaim.OfficeLoanClaimCaheService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Nady
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция получения для клиента всех заявок на кредит, созданных в каналах отличных от УКО
 */
public class GetOfficeLoanClaimsOperation extends OperationBase implements ListEntitiesOperation
{
	private static final OfficeLoanClaimCaheService officeLoanService = new OfficeLoanClaimCaheService();

	private List<OfficeLoanClaim> officeLoanClaims;
	public void initialize()
	{
		officeLoanClaims = officeLoanService.getOfficeLoanClaimList();
	}

	public List<OfficeLoanClaim> getOfficeLoanClaims()
	{
		return officeLoanClaims;
	}
}
