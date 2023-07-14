package com.rssl.phizic.business.dictionaries.pfp.risk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������
 */

public class RiskService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param instance ��� �������� ������ ��
	 * @return ������ ���� ��������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<Risk> getAll(String instance) throws BusinessException
	{
		return service.getAll(Risk.class, Order.asc("name"), instance);
	}

	/**
	 * @param id ������������� �����
	 * @param instance ��� �������� ������ ��
	 * @return ����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Risk getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(Risk.class, id, instance);
	}

	/**
	 * �������� ����
	 * @param risk ����
	 * @param instance ��� �������� ������ ��
	 * @return ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Risk addOrUpdate(final Risk risk, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(risk, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("���� � ����� ��������� ��� ����������.", e);
		}
	}

	/**
	 * ������� ����
	 * @param risk ��������� ����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove(final Risk risk, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.remove(risk, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("���������� ������� ����.", e);
		}
	}

}
