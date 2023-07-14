package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 16.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class PeriodTypeService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param id ������������� ���� �������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws BusinessException
	 */
	public PeriodType getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(PeriodType.class, id, instance);
	}

	/**
	 * @param ids ��������������
	 * @param instance ��� �������� ������ ��
	 * @return ������ ����� �������� �� ���������������
	 * @throws BusinessException
	 */
	public List<PeriodType> getListByIds(Long[] ids, String instance) throws BusinessException
	{
		return service.getListByIds(PeriodType.class, ids, instance);
	}

	/**
	 * �������� ��� �������
	 * @param type  ��� �������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws BusinessException
	 */
	public PeriodType addOrUpdate(final PeriodType type, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(type, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("��� ������� � ����� ��������� ��� ����������. ����������, ������� ������ ��������.", cve);
		}
	}

	/**
	 * ������� ��� �������
	 * @param type ��������� ��� �������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final PeriodType type, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.remove(type, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("���������� ������� ��� �������.", cve);
		}
	}
}
