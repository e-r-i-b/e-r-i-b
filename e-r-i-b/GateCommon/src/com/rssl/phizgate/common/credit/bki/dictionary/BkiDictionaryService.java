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
 * C����� ��� ������ �� ������������� ���
 */
public class BkiDictionaryService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * �������� �������� ���� �� ���� ���
	 * @param code ��� ���
	 * @param clazz ����� ����
	 * @return �������� ����
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
	 * �������� ���� � �������� �� �����
	 * @param clazz ����� ����
	 * @param flagName ����������� � �����������
	 * @param flagValue �������� �����������
	 * @return ����
	 */
	public <T extends BkiAbstractDictionaryEntry> T getBkiDictionaryRecordNameByFlag(Class<T> clazz, String flagName, boolean flagValue) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq(flagName, flagValue));
		return databaseService.<T>findSingle(criteria, null, null);
	}

	/**
	 * ����� ������������ ��������� �� ���� ���
	 * @param bkiCode ��� ���
	 * @return ������������ ���������
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
	 * �������� ��� ��� �� ������� ����
	 * �� ������� - ������� �� ��������� �� �����������
	 * @param clazz ����� ����
	 * @param esbCode ������ ��� (null/empty - ������ � ����������� �� �������� 'NULL')
	 * @return ��� ���
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
	 * �������� �� ��������� ��� ����
	 * @param clazz ����� ����
	 * @return ��� ��� �� ���������
	 */
	public <T extends BkiEsbDictionaryEntry> String getDefaultCode(Class<T> clazz) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("isDefault", true));
		List<BkiEsbDictionaryEntry> defaultEntry = databaseService.find(criteria, null, null);
		if (CollectionUtils.isEmpty(defaultEntry))
			throw new ConfigurationException("�� ������ �������� �� ��������� ��� ����������� ��� " + clazz.getSimpleName());
		return defaultEntry.get(0).getCode();
	}
}
