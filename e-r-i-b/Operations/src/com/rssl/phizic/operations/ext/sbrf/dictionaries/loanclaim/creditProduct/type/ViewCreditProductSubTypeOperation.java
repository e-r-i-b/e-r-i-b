package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Moshenko
 * @ created 27.12.2013
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ������ ��� ����� ����� ���������� ��������
 */
public class ViewCreditProductSubTypeOperation extends OperationBase
{
	private static final CreditProductService creditProductSrvice = new CreditProductService();
	private static final DepartmentService departmentService = new DepartmentService();
	private CreditProduct entity;

	public void initialize(Long id) throws BusinessException
	{
		entity = creditProductSrvice.findById(id);

		if (entity == null)
			throw new BusinessException("��������� �������� � id: " + id + " �� ������");
	}

	public CreditProduct getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}

	/**
	 * @return ������ ������� ��
	 * @throws BusinessException
	 */
	public List<String> getTerbanksNumbers() throws BusinessException
	{
		return departmentService.getTerbanksNumbers();
	}

}
