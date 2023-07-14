package com.rssl.phizic.csaadmin.utils;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationToken;
import com.rssl.phizic.csaadmin.config.CSAAdminConfig;
import com.rssl.phizic.utils.http.UrlBuilder;

/**
 * @author mihaylov
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CSAAdminUtils
{
	/**
	 * Сгенерировать адресс страницы авторизации в блоке по сессии
	 * @param authToken - токен аутентификации
	 * @param node текущая нода
	 * @return адресс страницы авторизации
	 */
	public static String generateEribLoginUrl(AuthenticationToken authToken, NodeInfo node)
	{
		CSAAdminConfig config = ConfigFactory.getConfig(CSAAdminConfig.class);
		UrlBuilder urlBuilder = new UrlBuilder(String.format(config.getNodeUrlPattern(), node.getHostname()));
		urlBuilder.addParameter("token",authToken.getId());
		return urlBuilder.getUrl();
	}

}
