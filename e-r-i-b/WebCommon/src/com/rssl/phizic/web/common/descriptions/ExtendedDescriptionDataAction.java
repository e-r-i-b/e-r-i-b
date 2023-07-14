package com.rssl.phizic.web.common.descriptions;

import com.rssl.phizic.business.descriptions.ExtendedDescriptionData;
import com.rssl.phizic.operations.descriptions.GetExtendedDescriptionDataOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн чтоб подт€нуть расширенное описание правил, договоров и т.д.
 * @author niculichev
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedDescriptionDataAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExtendedDescriptionDataForm frm = (ExtendedDescriptionDataForm) form;

		GetExtendedDescriptionDataOperation operation = createOperation(GetExtendedDescriptionDataOperation.class);
		operation.initialize(getDescriptionKey(frm));

		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(ExtendedDescriptionDataForm form, GetExtendedDescriptionDataOperation operation) throws BusinessException, BusinessLogicException
	{
		ExtendedDescriptionData data = operation.getEntity();
		form.setContent(data.getContent());
	}

	protected String getDescriptionKey(ExtendedDescriptionDataForm frm)
	{
		return frm.getName();
	}
}
