package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.*;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.EditInsuranceProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductActionBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceProductAction extends EditProductActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException
	{
		EditInsuranceProductOperation operation = createOperation(EditInsuranceProductOperation.class);
		Long id = frm.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditInsuranceProductForm) frm).createEditForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		InsuranceProduct product = (InsuranceProduct) entity;
		Boolean forComplex = (Boolean) data.get("forComplex");
		product.setForComplex(forComplex);
		product.setMinAge((Long) data.get("minAge"));
		product.setMaxAge((Long) data.get("maxAge"));
		super.updateEntity(entity, data);
	}

	private boolean checkPeriodInfo(boolean isComplex, Long typeId, String period,
	                                BigDecimal minSum, BigDecimal maxSum)
	{
		if (typeId == null || StringHelper.isEmpty(period))
			return false;

		if (!isComplex && minSum == null && maxSum == null)
			return false;

		return true;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditInsuranceProductForm form = (EditInsuranceProductForm) editForm;
		EditInsuranceProductOperation operation = (EditInsuranceProductOperation) editOperation;
		InsuranceProduct product = operation.getEntity();
		Long typeParentId = (Long) validationResult.get("typeParentId");
		Long typeId = (Long) validationResult.get("typeId");
		operation.setType(typeId == null? typeParentId: typeId);
		operation.setCompany((Long) validationResult.get("insuranceCompanyId"));

		operation.clearPeriodsInformation();
		Long[] typeIds = form.getPeriodTypeIds();
		if (ArrayUtils.isEmpty(typeIds))
			return;

		operation.setPeriodTypes(typeIds);
		Long defaultPeriodRowNum = (Long) validationResult.get("defaultPeriod");
		for (Long i: form.getLineNumbers())
		{
			Long peraiodTypeId = (Long) validationResult.get("idPeriodType" + i);
			String period = (String) validationResult.get("period" + i);
			BigDecimal minSum = (BigDecimal) validationResult.get("minSum" + i);
			BigDecimal maxSum = (BigDecimal) validationResult.get("maxSum" + i);
			if (checkPeriodInfo(product.isForComplex(), peraiodTypeId, period, minSum, maxSum))
			{
				operation.addPeriodTypeInfo(i.equals(defaultPeriodRowNum), peraiodTypeId, period, minSum, maxSum);
			}
		}
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		InsuranceProduct product = (InsuranceProduct) entity;
		EditInsuranceProductForm form = (EditInsuranceProductForm) frm;
		InsuranceCompany insuranceCompany = product.getInsuranceCompany();
		form.setField("insuranceCompanyId", insuranceCompany == null? null: product.getInsuranceCompany().getId());
		form.setField("insuranceCompanyName", insuranceCompany == null? null: product.getInsuranceCompany().getName());
		form.setField("forComplex", product.isForComplex());
		form.setField("minAge", product.getMinAge());
		form.setField("maxAge", product.getMaxAge());

		List<InsuranceDatePeriod> periodTypes = product.getPeriods();
		int size = periodTypes.size();
		Long[] lineNumbers = new Long[size];
		for (int i = 0; i < size; i++)
		{
			InsuranceDatePeriod datePeriod = periodTypes.get(i);
			PeriodType periodType = datePeriod.getType();
			lineNumbers[i] = Long.valueOf(i);
			form.setField("idPeriodType" + i, periodType.getId());
			form.setField("namePeriodType" + i, periodType.getName());
			form.setField("period" + i, datePeriod.getPeriod());
			form.setField("minSum" + i, datePeriod.getMinSum());
			form.setField("maxSum" + i, datePeriod.getMaxSum());
			if (datePeriod.getDefaultPeriod())
				form.setField("defaultPeriod", i);	
		}
		form.setField("periodsCount", size);
		form.setLineNumbers(lineNumbers);
		InsuranceType type = product.getType();
		if (type == null)
			return;

		if (type.getParent() == null)
		{
			form.setField("typeParentId", type.getId());
			return;
		}

		form.setField("typeId", type.getId());
		form.setField("typeParentId", type.getParent().getId());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		EditInsuranceProductOperation op = (EditInsuranceProductOperation) operation;
		frm.setField("insuranceTypeList", op.getInsuranceType());
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages errors = super.validateAdditionalFormData(frm,operation);
		EditInsuranceProductOperation op = (EditInsuranceProductOperation) operation;
		boolean forComplex = BooleanUtils.toBoolean((String)frm.getField("forComplex"));
		if(!op.canSave(forComplex))
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете отредактировать страховой продукт, который входит в состав комплексного продукта. Пожалуйста, укажите другой страховой продукт для комплексного продукта, а затем повторите операцию.",false));
		return errors;
	}
}
