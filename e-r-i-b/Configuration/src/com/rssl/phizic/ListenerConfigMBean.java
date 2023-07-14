package com.rssl.phizic;

/**
 * @author Omeliyanchuk
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface ListenerConfigMBean
{
	/**
	 *
	 * @return  Адрес к обратному веб-сервису на стороне ИКФЛ
	 */
	String getUrl();

	/**
	 * Адрес к обратному веб-сервису на стороне ИКФЛ
	 * @param url
	 */
	void setUrl(String url);

	/**
	 * @return адрес для редиректа входящего сообщения от ВС в блок
	 */
	String getRedirectServiceUrl();

	/**
	 * @param nodeId - номер блока
	 * @return - адрес веб-сервисов для блока
	 */
	public String getNodeWebServiceUrl(Long nodeId);
}
