package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ �� ���������� ����������
 */

public class InsuranceCompanyService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param id ������������� ��������� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ��������� ��������
	 * @throws BusinessException
	 */
	public InsuranceCompany getById(Long id, String instance) throws BusinessException
	{
		return service.findById(InsuranceCompany.class, id, instance);
	}

	/**
	 * ��������� � �� ��������� ��������
	 * @param company ��������� ��������
	 * @param instance ��� �������� ������ ��
	 * @return ��������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public InsuranceCompany addOrUpdate(InsuranceCompany company, String instance) throws BusinessException, BusinessLogicException
	{
		return service.addOrUpdate(company, instance);
	}

	/**
	 * �������� ��������� ��������
	 * @param company ��������� ��������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove(InsuranceCompany company, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.removeWithImage(company, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("���������� ������� ��������� ��������.", cve);
		}
	}
}