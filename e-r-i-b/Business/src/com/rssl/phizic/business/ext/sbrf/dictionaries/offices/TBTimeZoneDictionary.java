package com.rssl.phizic.business.ext.sbrf.dictionaries.offices;

import com.rssl.phizic.config.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 23.11.2011
 * Time: 16:36:09
 */
public class TBTimeZoneDictionary extends Config
{
	private static final String TB_CODE = "com.rssl.iccs.tb.main.code.";
	private static final String TB_TIME_ZONE = "com.rssl.iccs.tb.time.zone.";
	private static final String TB_COUNT = "com.rssl.iccs.tb.count";

	//TБ и время разници с москвой
	private Map<String, String> tbTimeZone = new HashMap<String, String>();

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public TBTimeZoneDictionary(PropertyReader reader)
	{
		super(reader);
	}

	private void fillMaps(String num)
	{
		String mainTb = getProperty(TB_CODE + num);
		String timeZone = getProperty(TB_TIME_ZONE + num);
		tbTimeZone.put(mainTb, timeZone);
	}

	/**
	 * вернуть часовой пояс по номеру ТБ
	 * @param tb тб
	 * @return часовой пояс
	 */
	public static String getTbTimeZone(String tb)
	{
		return ConfigFactory.getConfig(TBTimeZoneDictionary.class).getTbTimeZone0(tb);
	}

	private String getTbTimeZone0(String tb)
	{
		return tbTimeZone.get(tb);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		Long tbCount = Long.parseLong(getProperty(TB_COUNT));

		for (int i = 1; i <= tbCount; i++)
		{
			fillMaps(String.valueOf(i));
		}
	}
}

