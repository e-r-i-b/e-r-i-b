package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexInsuranceProductOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexInsuranceProductAction extends EditComplexProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditComplexInsuranceProductOperation operation = createOperation(EditComplexInsuranceProductOperation.class);
		EditComplexInsuranceProductForm form = (EditComplexInsuranceProductForm) frm;
		Long id = form.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);

		form.setProductTypeParameters(operation.getProductTypeParameters());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		EditComplexInsuranceProductForm form = (EditComplexInsuranceProductForm) frm;
		return form.createEditForm(form.getProductTypeParameters());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		ComplexInsuranceProduct product = (ComplexInsuranceProduct) entity;
		product.setMinSumInsurance((BigDecimal) data.get("minSumInsurance"));
		product.setMinSum((BigDecimal) data.get("minSum"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditComplexInsuranceProductOperation operation = (EditComplexInsuranceProductOperation) editOperation;
		EditComplexInsuranceProductForm form = (EditComplexInsuranceProductForm) editForm;
		operation.setInsuranceProducts(form.getInsuranceProductIds());
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		ComplexInsuranceProduct product = (ComplexInsuranceProduct) entity;
		frm.setField("minSum", product.getMinSum());
		EditComplexInsuranceProductForm form = (EditComplexInsuranceProductForm) frm;
		form.setField("minSumInsurance", product.getMinSumInsurance());
		List<InsuranceProduct> insuranceProductList = product.getInsuranceProducts();
		Long[] ids = new Long[insuranceProductList.size()];
		int i = 0;
		for (InsuranceProduct insuranceProduct: insuranceProductList)
		{
			Long id = insuranceProduct.getId();
			ids[i++] = id;
			form.setField("productDescrtiptionFor" + id, insuranceProduct.getName());
		}
		form.setInsuranceProductIds(ids);
	}
}
