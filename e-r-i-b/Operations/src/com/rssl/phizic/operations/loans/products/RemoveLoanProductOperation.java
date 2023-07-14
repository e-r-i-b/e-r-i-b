package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.products.*;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.List;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLoanProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final ModifiableLoanProductService modifiableLoanProductService = new ModifiableLoanProductService();

	private LoanProductBase loanProduct;

	/**
	 * �������������
	 * @param productId ID ��������� ������� ��� ��������
	 */
	public void initialize(Long productId) throws BusinessException
	{
		loanProduct = modifiableLoanProductService.findById(productId);

		if (loanProduct == null)
			loanProduct = loanProductService.findById(productId);

		if(loanProduct == null)
			throw new BusinessException("��������� ������� � Id = " + productId + " �� ������");
	}

	/**
	 * ������� ��������� �������
	 */
	public void remove() throws BusinessLogicException, BusinessException
	{
		if(loanProduct instanceof ModifiableLoanProduct)
		{
			ModifiableLoanProduct product = (ModifiableLoanProduct) loanProduct;
			if(product.getPublicity() == Publicity.PUBLISHED)
				throw new BusinessLogicException("�� �� ������ ������� ������ ��������� �������, ��� ��� �� �������� �������� ��� ���������� �������. ����� ������� ��������� �������, ������� ��� � ����������.");
			if(!product.getConditions().isEmpty())
				throw new BusinessLogicException("�� �� ������ ������� ��������� �������, ��� �������� ������� ������� ��������������  � ������ �������. ��� ��� ��������, ������� ������� ������� ��� ������ ������, ����� ��������� ��������.");

			modifiableLoanProductService.remove(product);
		}
		else if(loanProduct instanceof LoanProduct)
			loanProductService.remove((LoanProduct)loanProduct);
	}

	public Object getEntity()
	{
		return loanProduct;
	}

	public boolean checkBeforeRemove(RemoveLoanProductOperation operation, String id) throws BusinessException, DataAccessException
	{
		Query query = operation.createQuery("list")
				    .setParameter("productId", id);
		List rezult = query.executeList();
		if (rezult.size() == 0)
			return true;
		return false;
	}
}
