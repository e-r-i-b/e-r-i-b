package com.rssl.phizic.web.common.mobile.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.locale.user.ChangeUserLocaleOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;
import java.util.Map;


/**
 * @author komarov
 * @ created 20.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ChangeUserLocaleOperation op = createOperation(ChangeUserLocaleOperation.class);
		op.initialize(((ChangeLocaleForm)frm).getLocaleId());
		return op;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ChangeLocaleForm.EDIT_FORM;
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}


}
