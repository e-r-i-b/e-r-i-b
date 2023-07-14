package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * ќпераци€ редактировани€ типов кредитных продуктов.
 */
public class EditCreditProductTypeOperation extends OperationBase implements EditEntityOperation
{
	private static final CreditProductTypeService creditTypeSrvice = new CreditProductTypeService();
	private CreditProductType entity;

	public void initialize(Long code) throws BusinessException
	{
		entity = creditTypeSrvice.findeByCode(String.valueOf(code));

		if (entity == null)
			throw new BusinessException(" редитный продукта с кодом" + code + " не найден");
	}

	public void initializeNew() throws BusinessException
	{
		entity = new CreditProductType();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		creditTypeSrvice.addOrUpdate(entity);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}
}
