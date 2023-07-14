package com.rssl.phizic.locale;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.locale.entities.MultiLanguageApplications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ��������� ������ �� ������� ������������ ����� ��������� ��� ����������.
 */
public class ERIBMessageKeyManager
{

	private static final Map<MultiLanguageApplications,String> keySuffix = new HashMap<MultiLanguageApplications, String>(3);
	static
	{
		keySuffix.put(MultiLanguageApplications.atmApi,".atm");
		keySuffix.put(MultiLanguageApplications.webApi,".WebAPI");
		keySuffix.put(MultiLanguageApplications.mApi,".mobile");
	}

	/**
	 * �������� ������ ������ �� ������� ���������� ������ ���������
	 * ������ � ����������� �� ���� ���������� � ������� ������.
	 * ������:
	 *  1) ���������� �������� ��������� � ��������� ������ ��� ��������� ���������� PhizIC, � ����������
	 *   List(key,bundle,localeId)
	 *  2) ���������� �������� ��������� � �� ��������� ������ ��� ��������� ���������� PhizIC, � ����������
	 *   List{(key,bundle,localeId),
	 *       (key,bundle,defaultLocaleId)}
	 *  3) ���������� �������� ��������� � �� ��������� ������ ��� ���������� mobile8, � ����������
	 *   List{(key.mobile ,bundle,localeId),
	 *       (key,bundle,localeId),
	 *       (key.mobile,bundle,defaultLocaleId),
	 *       (key,bundle,defaultLocaleId)}
	 *
	 * @param application - ���������� � ������ �������� ���������� ���������
	 * @param key - ���� �� �������� ������ ���������
	 * @param bundle - ����� � ������� ������ ���������
	 * @param localeId - ������ � ������ ������� ���������� ����� ���������
	 * @return ������ ������.
	 */
	public static List<ERIBMessageKey> getEribMessageKeyList(Application application, String key, String bundle, String localeId)
	{
		String defaultLocaleId = ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId();

		List<ERIBMessageKey> result = new ArrayList<ERIBMessageKey>();
		result.addAll(getKeys(application, key, bundle, localeId));
		if(!defaultLocaleId.equals(localeId))
			result.addAll(getKeys(application, key, bundle, defaultLocaleId));
		return result;
	}

	private static List<ERIBMessageKey> getKeys(Application application, String key, String bundle, String localeId)
	{
		List<ERIBMessageKey> result = new ArrayList<ERIBMessageKey>();
		MultiLanguageApplications multiLanguageApplication = MultiLanguageApplications.fromApplication(application);
		String applicationSuffix = keySuffix.get(multiLanguageApplication);
		if(applicationSuffix != null)
			result.add(new ERIBMessageKey(key + applicationSuffix,bundle,localeId));
		result.add(new ERIBMessageKey(key,bundle,localeId));
		return result;
	}

}

