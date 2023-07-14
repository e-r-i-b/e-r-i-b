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

	//T� � ����� ������� � �������
	private Map<String, String> tbTimeZone = new HashMap<String, String>();

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
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
	 * ������� ������� ���� �� ������ ��
	 * @param tb ��
	 * @return ������� ����
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

