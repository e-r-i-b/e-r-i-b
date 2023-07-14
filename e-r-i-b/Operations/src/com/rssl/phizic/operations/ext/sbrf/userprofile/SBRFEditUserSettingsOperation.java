package com.rssl.phizic.operations.ext.sbrf.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.operations.userprofile.EditUserSettingsOperation;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 12.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class SBRFEditUserSettingsOperation extends EditUserSettingsOperation
{
	public Collection<String> getMobilePhones() throws BusinessException
	{
		return MobileBankManager.getInfoPhones(getUser(), null);
	}
}
