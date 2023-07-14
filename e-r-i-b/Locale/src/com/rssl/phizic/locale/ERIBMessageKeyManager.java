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
 * Менеджер для получения ключей по которым производится поиск текстовок для приложения.
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
	 * Получить список ключей по которым необходимо искать текстовку
	 * Разный в зависимости от типа приложения и текущей локали.
	 * Пример:
	 *  1) необходимо получить текстовку в дефолтной локали для основного приложения PhizIC, в результате
	 *   List(key,bundle,localeId)
	 *  2) необходимо получить текстовку в НЕ дефолтной локали для основного приложения PhizIC, в результате
	 *   List{(key,bundle,localeId),
	 *       (key,bundle,defaultLocaleId)}
	 *  3) необходимо получить текстовку в НЕ дефолтной локали для приложения mobile8, в результате
	 *   List{(key.mobile ,bundle,localeId),
	 *       (key,bundle,localeId),
	 *       (key.mobile,bundle,defaultLocaleId),
	 *       (key,bundle,defaultLocaleId)}
	 *
	 * @param application - приложение в рамках которого получается текстовка
	 * @param key - ключ по которому ищется текстовка
	 * @param bundle - бандл в котором ищется текстовка
	 * @param localeId - локаль в рамках которой необходимо найти текстовку
	 * @return список ключей.
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

