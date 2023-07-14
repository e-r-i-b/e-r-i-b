package com.rssl.phizic.web.client.security;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.security.LogoffAction;
import com.rssl.phizic.web.util.CookieUtil;
import com.rssl.phizic.web.wsclient.webApi.WebAPIRequestHelper;
import com.rssl.phizic.web.wsclient.webApi.exceptions.WebAPIException;
import com.rssl.phizic.web.wsclient.webApi.exceptions.WebAPILogicException;

/**
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class LogoffClientAction extends LogoffAction
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	protected void preClearCache() throws WebAPIException, WebAPILogicException
	{
		String cookieValue = CookieUtil.getCookieValueByName(ConfigFactory.getConfig(WebAPIConfig.class).getCookieName());
		log.debug("Отправка LOGOFF в WebAPI. cookie = " + cookieValue);
		if (Application.PhizIC.equals(LogThreadContext.getApplication()) && cookieValue != null)
			WebAPIRequestHelper.sendLogoffRq(LogThreadContext.getIPAddress());
	}
}
