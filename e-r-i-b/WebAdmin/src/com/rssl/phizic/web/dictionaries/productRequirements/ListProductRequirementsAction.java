package com.rssl.phizic.web.dictionaries.productRequirements;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirementType;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.productRequirements.ListProductRequirementsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Arrays;

/**
 * @author lepihina
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListProductRequirementsAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListProductRequirementsOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListProductRequirementsForm frm = (ListProductRequirementsForm) form;
		ListProductRequirementsOperation op = (ListProductRequirementsOperation) operation;

		frm.setProductRequirementTypes(Arrays.asList(ProductRequirementType.values()));
		frm.setAccountTypes(op.getAccountTypes());
	}
}
