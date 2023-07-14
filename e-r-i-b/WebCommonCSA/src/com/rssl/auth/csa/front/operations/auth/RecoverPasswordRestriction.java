package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;

/**
 * ����������� �� ��������������� �������������� ������, �������� ����������� ����������
 * @author niculichev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class RecoverPasswordRestriction implements Restriction
{
	public boolean check()
	{
		// ��� ������ ��� �������������� ������ �� ��������
		if (PayOrderHelper.isUECPaymentSession())
			return false;

		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.isAccessRecoverPassword();
	}
}
