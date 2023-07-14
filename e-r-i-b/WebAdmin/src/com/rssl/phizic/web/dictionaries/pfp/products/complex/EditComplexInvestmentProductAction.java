package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInvestmentProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexInvestmentProductOperationBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditComplexInvestmentProductAction extends EditComplexProductAction
{
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditComplexInvestmentProductOperationBase operation = (EditComplexInvestmentProductOperationBase) editOperation;
		EditComplexInvestmentProductFormBase form = (EditComplexInvestmentProductFormBase) editForm;
		operation.setFundProducts(form.getFundProductIds());
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		ComplexInvestmentProductBase product = (ComplexInvestmentProductBase) entity;
		for (PortfolioType type: PortfolioType.values())
		{
			ProductParameters productParameters = product.getParameters(type);
			productParameters.setMinSum((BigDecimal) data.get(type.name().concat("minSum")));
		}
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		ComplexInvestmentProductBase product = (ComplexInvestmentProductBase) entity;
		for (PortfolioType type: PortfolioType.values())
		{
			frm.setField(type.name().concat("minSum"),      product.getParameters(type).getMinSum());
		}
		EditComplexInvestmentProductFormBase form = (EditComplexInvestmentProductFormBase) frm;
		List<FundProduct> fundProducts = product.getFundProducts();
		Long[] ids = new Long[fundProducts.size()];
		int i = 0;
		for (FundProduct fundProduct: fundProducts)
		{
			Long id = fundProduct.getId();
			ids[i++] = id;
			form.setField("fundProductNameFor" + id, fundProduct.getName());
		}

		form.setFundProductIds(ids);
	}
}
