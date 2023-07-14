package com.rssl.phizgate.common.credit.bki.dictionary;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * User: Moshenko
 * Date: 02.10.14
 * Time: 15:49
 * Cервис для работы со справочниками БКИ
 */
public class BkiDictionaryService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * Получить название поля по коду БКИ
	 * @param code код БКИ
	 * @param clazz класс поля
	 * @return название поля
	 */
	public String getBkiDictionaryRecordNameByCode(String code, Class clazz) throws Exception
	{
		if (StringHelper.isEmpty(code))
			return null;
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("code", code));
		BkiAbstractDictionaryEntry bkiDictionaryEntry = databaseService.findSingle(criteria, null, null);
		if (bkiDictionaryEntry == null)
			return null;
		return bkiDictionaryEntry.getName();
	}

	/**
	 * Получить поле с условием по флагу
	 * @param clazz класс поля
	 * @param flagName ограничение в справочнике
	 * @param flagValue значение ограничения
	 * @return поле
	 */
	public <T extends BkiAbstractDictionaryEntry> T getBkiDictionaryRecordNameByFlag(Class<T> clazz, String flagName, boolean flagValue) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq(flagName, flagValue));
		return databaseService.<T>findSingle(criteria, null, null);
	}

	/**
	 * Найти наименование документа по коду БКИ
	 * @param bkiCode код БКИ
	 * @return наименование документа
	 * @throws Exception
	 */
	public String getNameByBkiCode(String bkiCode) throws Exception
	{
		if (StringHelper.isEmpty(bkiCode))
			return null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BkiPrimaryIDType.class);
		criteria.add(Expression.eq("bkiCode", bkiCode));
		BkiAbstractDictionaryEntry bkiDictionaryEntry = databaseService.findSingle(criteria, null, null);
		if (bkiDictionaryEntry == null)
			return null;
		return bkiDictionaryEntry.getName();
	}

	/**
	 * Получить код БКИ по шинному коду
	 * Не найдено - берется по умолчанию из справочника
	 * @param clazz класс поля
	 * @param esbCode шинный код (null/empty - ищется в справочнике по значению 'NULL')
	 * @return код БКИ
	 */
	public <T extends BkiEsbDictionaryEntry> String getCodeByEsbCode(Class<T> clazz, String esbCode) throws Exception
	{
		if (StringHelper.isEmpty(esbCode))
			return getDefaultCode(clazz);

		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("esbCode", esbCode));
		BkiEsbDictionaryEntry bkiEsbDictionaryEntry = databaseService.findSingle(criteria, null, null);
		if (bkiEsbDictionaryEntry == null)
			return getDefaultCode(clazz);
		return bkiEsbDictionaryEntry.getCode();
	}

	/**
	 * Значение по умолчанию для поля
	 * @param clazz класс поля
	 * @return код БКИ по умолчанию
	 */
	public <T extends BkiEsbDictionaryEntry> String getDefaultCode(Class<T> clazz) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("isDefault", true));
		List<BkiEsbDictionaryEntry> defaultEntry = databaseService.find(criteria, null, null);
		if (CollectionUtils.isEmpty(defaultEntry))
			throw new ConfigurationException("Не задано значение по умолчанию для справочника БКИ " + clazz.getSimpleName());
		return defaultEntry.get(0).getCode();
	}
}
