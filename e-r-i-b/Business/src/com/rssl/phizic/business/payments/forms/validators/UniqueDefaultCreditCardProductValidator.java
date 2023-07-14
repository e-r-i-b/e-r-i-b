package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;

import java.util.Map;

/**
 * ��������� ��������� ������������ ����������� ���������� ��������
 * @author sergunin
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class UniqueDefaultCreditCardProductValidator extends MultiFieldsValidatorBase
{
	private static final CreditCardProductService serviceProvider = new CreditCardProductService();
	public static final String PRODUCT_ID = "productId";
	public static final String IS_PRE_APPROVED = "useForPreapprovedOffers";
	public static final String IS_DEFAULT = "defaultForPreapprovedOffers";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		boolean isPreApproved = (Boolean) retrieveFieldValue(IS_PRE_APPROVED, values);
		boolean isDefault = (Boolean) retrieveFieldValue(IS_DEFAULT, values);
		Long productId = (Long) retrieveFieldValue(PRODUCT_ID, values);

		if (!isDefault)
			return true;

		//������ ������� ��������� ���������������� �����������
		if (!isPreApproved && isDefault)
			return false;

		// ���������� ���������� ������ ��������
		boolean isNew = productId == null;

		CreditCardProduct currentDefaultProduct;
		try
		{
			currentDefaultProduct = serviceProvider.getDefaultForPreappovedProducts();
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("������ ��� ��������� ������ ���������. ", e);
		}

		// ���� ��� ������������ �� ���������
		if(currentDefaultProduct == null) {
			return true;
		}

		//���� ����������� �����, �� ������ ������������� ������
		if (isNew)
		{
			return currentDefaultProduct == null;
		}
		//���� ������������� ������, �� ������ ���������
		else
		{
			return productId.equals(currentDefaultProduct.getId());
		}
	}
}
