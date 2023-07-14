package com.rssl.phizic.config.webapi;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиги WebAPI
 * @author Jatsky
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIConfig extends Config
{
	private static final String TOKEN_LIFETIME = "token.lifetime";
	private static final String COOKIE_NAME = "cookie.name";
	private static final String COOKIE_DOMAIN = "web.api.cookie.domain";
	private static final String URL_SBOL3 = "url.sbol3";
	private static final String DP_COOKIE_NAME = "dp.cookie.name";
	private static final String WEB_API_WS_PROTOCOL = "web.api.ws.protocol";
	private static final String WEB_API_WS_URL = "web.api.ws.url";

	private static final String URL_PREFIX = "web.api.url.";
	private static final String PERSONAL_MENU_COOKIE_NAME = "personal.menu.cookie.name";
	private static final String WEB_CLIENT_PROTOCOL = "web.client.protocol";

	private int tokenLifetime;
	private String cookieName;
	private String urlSBOL3;
	private String dpCookieName;
	private String webApiWsProtocol;
	private String webApiWsUrl;
	private String personalMenuCookieName;
	private String webApiCookieDomain;
	private String webClientProtocol;

	public WebAPIConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		tokenLifetime = getIntProperty(TOKEN_LIFETIME);
		cookieName = getProperty(COOKIE_NAME);
		urlSBOL3 = getProperty(URL_SBOL3);
		dpCookieName = getProperty(DP_COOKIE_NAME);
		webApiWsProtocol = getProperty(WEB_API_WS_PROTOCOL);
		webApiWsUrl = getProperty(WEB_API_WS_URL);
		personalMenuCookieName = getProperty(PERSONAL_MENU_COOKIE_NAME);
		webApiCookieDomain = getProperty(COOKIE_DOMAIN);
		webClientProtocol = getProperty(WEB_CLIENT_PROTOCOL);
	}

	public int getTokenLifetime()
	{
		return tokenLifetime;
	}

	public String getCookieName()
	{
		return cookieName;
	}

	public String getUrlSBOL3()
	{
		return urlSBOL3;
	}

	public String getDpCookieName()
	{
		return dpCookieName;
	}

	public String getWebApiWsProtocol()
	{
		return webApiWsProtocol;
	}

	public String getWebApiWsUrl()
	{
		return webApiWsUrl;
	}

	public String getWebAPIUrl(String operation)
	{
		return getProperty(URL_PREFIX + operation);
	}

	public String getPersonalMenuCookieName()
	{
		return personalMenuCookieName;
	}

	public String getWebApiCookieDomain()
	{
		return webApiCookieDomain;
	}

	public String getWebClientProtocol()
	{
		return webClientProtocol;
	}
}

