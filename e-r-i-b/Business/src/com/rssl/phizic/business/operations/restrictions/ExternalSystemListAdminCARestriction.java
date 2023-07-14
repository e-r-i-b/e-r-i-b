package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * @author: vagin
 * @ created: 07.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalSystemListAdminCARestriction extends Restriction
{
	public boolean accept() throws BusinessException;
}
