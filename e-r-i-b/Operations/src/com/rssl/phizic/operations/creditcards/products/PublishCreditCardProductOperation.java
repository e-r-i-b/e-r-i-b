package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class PublishCreditCardProductOperation extends OperationBase
{
	private static final CreditCardProductService service = new CreditCardProductService();

	private CreditCardProduct product;

	public void initialize(Long productId) throws BusinessException
	{
		product = service.findById(productId);
	}

	public void publish() throws Exception
	{
		if(product.getPublicity() == Publicity.PUBLISHED)
			throw new BusinessLogicException("������ ������� ��� �����������.");
		if(product.getConditions().isEmpty())
			throw new BusinessLogicException("�� �� ������ ������������ ��������� ��������� �������, ��� �������� �� ������ ������� �������������� � ����� ��� ���������� �������. ����������, �������� �������.");
		product.setPublicity(Publicity.PUBLISHED);
		service.update(product);
	}

	public void unpublish() throws Exception
	{
		if(product.getPublicity() == Publicity.UNPUBLISHED)
			throw new BusinessLogicException("������ ������� ��� ���� � ����������.");
		product.setPublicity(Publicity.UNPUBLISHED);
		service.update(product);
	}
}
