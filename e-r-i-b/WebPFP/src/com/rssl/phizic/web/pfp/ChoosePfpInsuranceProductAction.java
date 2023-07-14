package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.pfp.ChoosePfpProductOperation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 31.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ChoosePfpInsuranceProductAction extends ChoosePfpProductAction
{
	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		ChoosePfpInsuranceProductForm frm = (ChoosePfpInsuranceProductForm) form;
		ChoosePfpProductOperation operation = (ChoosePfpProductOperation) op;
		try
		{
			List<DictionaryProductType> availableProducts = operation.getProfile().getPersonRiskProfile().getAvailableProducts();
			if (availableProducts.contains(DictionaryProductType.INSURANCE))
			{
				Query insuranceQuery = operation.createQuery(DictionaryProductType.INSURANCE.name());
				frm.setInsuranceProductList(insuranceQuery.<InsuranceProduct>executeList());
			}
			if (availableProducts.contains(DictionaryProductType.PENSION))
			{
				Query pensionQuery = operation.createQuery(DictionaryProductType.PENSION.name());
				frm.setPensionProductList(pensionQuery.<PensionProduct>executeList());
			}
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
