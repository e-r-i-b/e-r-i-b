package com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFundService;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ редактировани€ пенсионной программы
 */

public class EditPensionProductOperation extends EditProductOperationBase<PensionProduct>
{
	private static final PensionFundService pensionFundService = new PensionFundService();

	protected Class<PensionProduct> getProductClass()
	{
		return PensionProduct.class;
	}

	@Override
	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.PENSION;
	}

	protected PensionProduct getNewProduct()
	{
		return new PensionProduct();
	}

	/**
	 * @param id идентификатор пенсионного фонда
	 * @return пенсионный фонд
	 * @throws BusinessException
	 */
	public PensionFund getPensionFundById(Long id) throws BusinessException
	{
		return pensionFundService.findById(id, getInstanceName());
	}

	/**
	 * @return список всех пенсионных фондов
	 * @throws BusinessException
	 */
	public List<PensionFund> getAllPensionFunds() throws BusinessException
	{
		return pensionFundService.getAll(getInstanceName());
	}
}
