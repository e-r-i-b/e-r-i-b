package com.rssl.phizic.operations.test.social;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;

/**
 * @author sergunin
 * @ created 03.09.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отправка (http) запроса к социальному приложению (socialAPI)
 */
public class SendSocialRequestOperation extends SendAbstractRequestOperation
{
	private static final String JAXB_MOBILE8_PACKAGE = "com.rssl.phizic.business.test.mobile8.generated";

	protected String getJaxbContextPath() throws BusinessException
	{
			return JAXB_MOBILE8_PACKAGE;
	}

	protected void updateVersion(String key, Object value){}

}
