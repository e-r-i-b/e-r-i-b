package com.rssl.phizic.locale.services;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.locale.entities.ERIBLocale;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * ������ ��� ������ � �������������� ��������
 * @author koptyaev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ��������
 */
public class MultiInstanceEribLocaleService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * �������� ��� �������� ������
	 * @param locale ������
	 * @param instanceName ��� �������� ��
	 * @return ������������ ������
	 * @throws SystemException
	 */
	public ERIBLocale addOrUpdate(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.addOrUpdate(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * �������� ������
	 * @param locale ������
	 * @param instanceName ��� �������� ��
	 * @return ���������� ������
	 * @throws SystemException
	 */
	public ERIBLocale update(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.update(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
	/**
	 * �������� ������
	 * @param locale ������
	 * @param instanceName ��� �������� ��
	 * @return ����������� ������
	 * @throws SystemException
	 */
	public ERIBLocale add(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.add(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * ������� ������
	 * @param locale ������
	 * @param instanceName ��� �������� ��
	 * @throws SystemException
	 */
	public void delete(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			databaseService.delete(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * �������� ������ �� id
	 * @param id �������������
	 * @param instanceName ��� �������� ��
	 * @return ������
	 * @throws SystemException
	 */
	public ERIBLocale getById(String id, String instanceName) throws SystemException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ERIBLocale.class).add(Expression.eq("id", id));
			return databaseService.findSingle(criteria, null, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * �������� ���� ������ �������
	 * @param instanceName ��� �������� ��
	 * @return ������ �������
	 * @throws SystemException
	 */
	public List<ERIBLocale> getAll(String instanceName) throws SystemException
	{
		try
		{
			return databaseService.find(DetachedCriteria.forClass(ERIBLocale.class), null, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
}
