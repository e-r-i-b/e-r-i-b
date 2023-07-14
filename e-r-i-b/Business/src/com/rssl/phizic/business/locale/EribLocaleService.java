package com.rssl.phizic.business.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;

import java.util.List;

/**
 * ������-������ ��� ������ � ��������
 * @author komarov
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EribLocaleService extends MultiInstanceEribLocaleService
{

	@Override
	public ERIBLocale addOrUpdate(ERIBLocale locale, String instanceName) throws BusinessException
	{
		try
		{
			return super.addOrUpdate(locale, instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� ���������/�������� ������", e);
		}
	}

	@Override
	public ERIBLocale update(ERIBLocale locale, String instanceName) throws BusinessException
	{
		try
		{
			return super.update(locale, instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� �������� ������", e);
		}
	}

	@Override
	public ERIBLocale add(ERIBLocale locale, String instanceName) throws BusinessException
	{
		try
		{
			return super.add(locale, instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� ��������� ������", e);
		}
	}

	@Override
	public void delete(ERIBLocale locale, String instanceName) throws BusinessException
	{
		try
		{
			super.delete(locale, instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� ������� ������", e);
		}
	}

	@Override
	public ERIBLocale getById(String id, String instanceName) throws BusinessException
	{
		try
		{
			return super.getById(id, instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� �������� ������ � id="+id, e);
		}
	}

	@Override
	public List<ERIBLocale> getAll(String instanceName) throws BusinessException
	{
		try
		{
			return super.getAll(instanceName);
		}
		catch (SystemException e)
		{
			throw new BusinessException("�� ������� �������� ������ �������", e);
		}
	}
}
