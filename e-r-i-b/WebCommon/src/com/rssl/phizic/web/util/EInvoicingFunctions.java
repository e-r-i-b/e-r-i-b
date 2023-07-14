package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import static com.rssl.phizic.einvoicing.EInvoicingConstants.UEC_PAY_INFO;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.ERIBFrontConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Erkin
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * TLD-������� ��� ���������� e-invoicing
 */
public class EInvoicingFunctions
{
	/**
	 * @return true, ���� ������� ����� ������������ - ������ ��������� ��������� � ����� ���
	 */
	public static boolean isUECPaymentSession()
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		if (authContext == null)
			return false;
		return StringHelper.isNotEmpty(authContext.getAuthenticationParameter(UEC_PAY_INFO)) && authContext.getVisitingMode() == UserVisitingMode.PAYORDER_PAYMENT ;
	}

	/**
	 * ���������� ����� ���
	 * @return ����� ����� ���
	 */
	public static String getUECWebSiteUrl()
	{
		ERIBFrontConfig frontConfig = ConfigFactory.getConfig(ERIBFrontConfig.class);
		return frontConfig.getUECWebSiteUrl();
	}
}
