package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * �������� �������� ���� ���������� ��������
 */
public class RemoveCreditProductTypeOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CreditProductTypeService crtditTypeSrvice = new CreditProductTypeService();
	private CreditProductType entity;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		entity = crtditTypeSrvice.findeByCode(String.valueOf(id));
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		try
		{
			crtditTypeSrvice.remove(entity);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("�� �� ������ ������� ��� ���������� ��������, � �������� ����������� ��������� �������� � ���������. ������� ������� �� ��������� ��������� � ��������� ��������",e);
		}
	}

	public Object getEntity()
	{
		return entity;
	}
}
