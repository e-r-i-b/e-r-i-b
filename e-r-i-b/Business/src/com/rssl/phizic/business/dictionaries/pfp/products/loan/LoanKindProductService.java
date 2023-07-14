package com.rssl.phizic.business.dictionaries.pfp.products.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoanKindProductService
{
	private static final PFPDictionaryServiceBase simpleService= new PFPDictionaryServiceBase();

	/**
	 * ����� ��� �������
	 * @param id ������������� ���� �������
	 * @return ��� �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public LoanKindProduct getById(Long id) throws BusinessException
	{
		return getById(id, null);
	}

	/**
	 * ����� ��� �������
	 * @param id ������������� ���� �������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public LoanKindProduct getById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(LoanKindProduct.class, id, instance);
	}

	/**
	 * @return ������ ���� ����� �������
	 * @throws BusinessException
	 */
	public List<LoanKindProduct> getAll() throws BusinessException
	{
		return simpleService.getAll(LoanKindProduct.class);
	}

	/**
	 * ��������� ��� �������
	 * @param loanKindProduct ����������� ��� �������
	 * @param instance ��� �������� ������ ��
	 * @return ��� �������
	 * @throws BusinessException
	 */
	public LoanKindProduct addOrUpdate(final LoanKindProduct loanKindProduct, String instance) throws BusinessException
	{
		return simpleService.addOrUpdate(loanKindProduct, instance);
	}

	/**
	 * ������� ��� �������
	 * @param loanKindProduct ��������� ��� �������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final LoanKindProduct loanKindProduct, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			simpleService.removeWithImage(loanKindProduct, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("���������� ������� ��������� �������.", cve);
		}
	}

}
