package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * ������� �� �������� ��������� ��������� ��� ����������� ����
 * @author basharin
 * @ created 19.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ConnectionUDBOClaimStrategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		/**
		 *  ����������� ����
		 */
		if (object instanceof RemoteConnectionUDBOClaim)
			return false;

		return true;
	}
}
