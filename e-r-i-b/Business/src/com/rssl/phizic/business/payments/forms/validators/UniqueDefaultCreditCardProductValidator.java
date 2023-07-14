package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;

import java.util.Map;

/**
 * ¬алидатор провер€ет уникальность дефолтность кредитного продукта
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

		//Ќельз€ сделать дефолтным непредодобренное предложение
		if (!isPreApproved && isDefault)
			return false;

		// происходит добавление нового продукта
		boolean isNew = productId == null;

		CreditCardProduct currentDefaultProduct;
		try
		{
			currentDefaultProduct = serviceProvider.getDefaultForPreappovedProducts();
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("ќшибка при получении списка продуктов. ", e);
		}

		// если нет используемых по умолчанию
		if(currentDefaultProduct == null) {
			return true;
		}

		//если добавл€етс€ новый, то должен отсутствовать старый
		if (isNew)
		{
			return currentDefaultProduct == null;
		}
		//если редактируетс€ старый, то должны совпадать
		else
		{
			return productId.equals(currentDefaultProduct.getId());
		}
	}
}
