package com.rssl.phizic.config.jmx;

/**
 * @author osminin
 * @ created 07.04.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BarsConfigMBean
{
	/**
	 * URL веб-сервиса СБОЛ
	 * @return URL веб-сервиса СБОЛ
	 */
	String getBarsUrl(String tb);

	/**
	 * глобальный код участника расчетов БАРС
	 * @return глобальный код участника расчетов БАРС
	 */
	String getBarsPartCode(String tb);
}
