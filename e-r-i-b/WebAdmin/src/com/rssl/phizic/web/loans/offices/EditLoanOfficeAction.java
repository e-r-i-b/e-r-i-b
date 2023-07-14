package com.rssl.phizic.web.loans.offices;

import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.loans.offices.EditLoanOfficeOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanOfficeAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditLoanOfficeForm.FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLoanOfficeForm frm = (EditLoanOfficeForm) form;

		EditLoanOfficeOperation operation = createOperation(EditLoanOfficeOperation.class);
		operation.initialize(frm.getSynchKey());
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		LoanOffice office = (LoanOffice) entity;

		frm.setField("synchKey", office.getSynchKey());
		frm.setField("name", office.getName());
		frm.setField("address", office.getAddress());
		frm.setField("telephone", office.getTelephone());
		frm.setField("description", office.getInfo());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		LoanOffice office = (LoanOffice) entity;

		office.setName((String) data.get("name"));
		office.setAddress((String) data.get("address"));
		office.setTelephone((String) data.get("telephone"));
		office.setInfo((String) data.get("description"));
	}
}
