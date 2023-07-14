package com.rssl.phizic.web.finances;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.configuration.EditCardOperationCategoryOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author Koptyaev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCardOperationCategoryOperation operation = createOperation(EditCardOperationCategoryOperation.class);
		EditCardOperationCategoryForm form = (EditCardOperationCategoryForm)frm;
		Long id = form.getId();
		operation.initialize(id);
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditCardOperationCategoryForm.EDIT_FORM;
	}

	/**
	 * ќбновить сужность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	@Override protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CardOperationCategory category = (CardOperationCategory)entity;
		category.setName((String) data.get("name"));
		category.setVisible((Boolean)data.get("visibility"));
		if (!category.getIsDefault()){
			category.setIncome((Boolean) data.get("type"));
		}
		if (category.getId() == null)
		{
			category.setExternalId("internal^"+category.getName()+(category.isIncome()?"in":"out"));
		}

		category.setIsTransfer((Boolean)data.get("transfer"));
		category.setIdInmAPI((String)data.get("idInmAPI"));
		category.setColor((String)data.get("color"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditCardOperationCategoryOperation operation = (EditCardOperationCategoryOperation)editOperation;
		CardOperationCategory category = (CardOperationCategory)operation.getEntity();
		if (!category.getIsDefault() && !category.isForInternalOperations())
			operation.setMccCodes((List<Long>)validationResult.get("mcc"));
	}
	/**
	 * ѕроинициализировать/обновить struts-форму
	 * @param frm форма дл€ обновлени€
	 * @param entity объект дл€ обновлени€.
	 */
	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditCardOperationCategoryForm form = (EditCardOperationCategoryForm)frm;
		CardOperationCategory category = (CardOperationCategory)entity;
		form.setField("name",category.getName());
		form.setField("type", category.isIncome());
		form.setField("isDefault",category.getIsDefault());
		form.setField("forInternalOperations",category.isForInternalOperations());
		form.setField("visibility",category.getVisible());
		form.setField("transfer", category.getIsTransfer());
		form.setField("idInmAPI", category.getIdInmAPI());
		form.setField("color", category.getColor());
	}

	/**
	 * ќбновить форму данными из операции
	 * @param frm форма дл€ обновлени€
	 * @param operation операци€ дл€ получени€ данных.
	 * @throws Exception
	 */
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditCardOperationCategoryOperation op = (EditCardOperationCategoryOperation)operation;
		frm.setField("operations", op.categoryContainsOperations());
		frm.setField("mcc", op.getCategoryMccCodes());
	}
}
