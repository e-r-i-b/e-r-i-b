package com.rssl.phizic.web.commissions;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.commission.EditTBCommissionsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 20.09.13
 * @ $Author$
 * @ $Revision$
 * Ёкшен настройки отображени€ сумм микроопераций(комиссий) дл€ платежей.
 */
public class EditTBCommissionAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.save", "save");
		return map;
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		EditTBCommissionsOperation operation = createOperation(EditTBCommissionsOperation.class);
		EditTBCommissionForm frm = (EditTBCommissionForm) form;
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		updateFormAdditionalData(form, operation);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTBCommissionForm frm = (EditTBCommissionForm) form;
		EditTBCommissionsOperation operation = (EditTBCommissionsOperation)createListOperation(frm);
		String[] selected = frm.getSelectedIds();
		operation.save(selected);
		return start(mapping, form, request, response);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		EditTBCommissionForm frm = (EditTBCommissionForm) form;
		EditTBCommissionsOperation op = (EditTBCommissionsOperation) operation;

		frm.setCurrencySettings(op.getCurrencySettings());
		frm.setRurSettings(op.getRurSettings());
	}
}
