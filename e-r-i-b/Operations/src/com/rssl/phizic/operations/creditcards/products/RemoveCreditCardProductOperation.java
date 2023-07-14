package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemoveCreditCardProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	private CreditCardProduct product;

	/**
	 * �������������
	 * @param productId ID ���������� ���������� �������� ��� ��������
	 */
	public void initialize(Long productId) throws BusinessException
	{
		product = creditCardProductService.findById(productId);

		if(product == null)
			throw new BusinessException("��������� ��������� ������� � Id = " + productId + " �� ������");
	}

	/**
	 * ������� ��������� ��������� �������
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		if(product.getPublicity() == Publicity.PUBLISHED)
			throw new BusinessLogicException("�� �� ������ ������� ������ ��������� ��������� �������, ��� ��� �� �������� �������� ��� ���������� ������. ����� ������� ��������� ��������� �������, ������� ��� � ���������� � ��������� ��������.");
		if(!product.getConditions().isEmpty())
			throw new BusinessLogicException("�� �� ������ ������� �������, ��� �������� ������� ������� �������������� � ������. ������� ������� ��� ������ ������ � ��������� ��������.");

		creditCardProductService.remove(product);
	}

	public Object getEntity()
	{
		return product;
	}
}
