package com.rssl.phizic.operations.card.products;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author gulov
 * @ created 12.10.2011
 * @ $Authors$
 * @ $Revision$
 */
public class RemoveCardProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CardProductService service = new CardProductService();

	private CardProduct product;

	/**
	 * ���������������� ��������� ��������� �������
	 * @param id ������������� �������� ��� ��������.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		product = service.findById(id);
		if (product == null)
			throw new BusinessException("�� ������ ��������� ������� � id = " + id);
	}

	/**
	 * �������� ���������� ��������
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		service.remove(product);
	}

	/**
	 * ��������� ��������
	 * @return - ��������� �������
	 */
	public CardProduct getEntity()
	{
		return product;
	}
}
