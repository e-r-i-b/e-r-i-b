package com.rssl.phizic.web.ima;

import com.rssl.phizic.web.ext.sbrf.payments.IMAOpeningLicenseAction;
import com.rssl.phizic.operations.ima.IMAOpeningLicenseOperation;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Pankin
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningLicenseAdminAction extends IMAOpeningLicenseAction
{
	protected IMAOpeningLicenseOperation getIMAOpeningLicenseOperation() throws BusinessException
	{
		if (checkAccess(IMAOpeningLicenseOperation.class, "ViewPaymentList"))
			return createOperation(IMAOpeningLicenseOperation.class, "ViewPaymentList");
		else
			return createOperation(IMAOpeningLicenseOperation.class, "ViewPaymentListUseClientForm");
	}
}
