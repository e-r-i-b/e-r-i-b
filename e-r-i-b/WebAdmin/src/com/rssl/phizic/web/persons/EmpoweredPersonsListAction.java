package com.rssl.phizic.web.persons;

import com.rssl.phizic.operations.person.GetEmpoweredPersonsListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: gulov $
 * @ $Revision: 49166 $
 */

public class EmpoweredPersonsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		EmpoweredPersonsListForm         frm       = (EmpoweredPersonsListForm) form;
		GetEmpoweredPersonsListOperation operation = createOperation(GetEmpoweredPersonsListOperation.class);
		operation.initialize(frm.getPerson()); 

		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("DeleteEmpoweredPersonOperation");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		EmpoweredPersonsListForm         frm = (EmpoweredPersonsListForm) form;
		GetEmpoweredPersonsListOperation op  = (GetEmpoweredPersonsListOperation) operation;
		addLogParameters(new BeanLogParemetersReader("Клиент", op.getPerson()));

		frm.setActivePerson(op.getPerson());
	}
}