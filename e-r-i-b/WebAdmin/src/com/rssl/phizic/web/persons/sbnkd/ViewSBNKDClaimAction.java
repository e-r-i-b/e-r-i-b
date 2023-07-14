package com.rssl.phizic.web.persons.sbnkd;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.sberbankForEveryDay.ViewSBNKDClaimOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * просмотр заявки СБНКД
 * @author basharin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ViewSBNKDClaimAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewSBNKDClaimOperation operation = createOperation(ViewSBNKDClaimOperation.class);
		ViewSBNKDClaimForm form = (ViewSBNKDClaimForm) frm;
		try
		{
			operation.initialize(form.getId(), form.getPerson());
		}
		catch (GateException e)
		{
			new BusinessLogicException(e);
		}
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ViewSBNKDClaimForm form = (ViewSBNKDClaimForm) frm;
		IssueCardDocumentImpl document = (IssueCardDocumentImpl) entity;
		form.setClaim(document);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ViewSBNKDClaimForm form = (ViewSBNKDClaimForm)frm;
		ViewSBNKDClaimOperation op = (ViewSBNKDClaimOperation) operation;
		form.setActivePerson(op.getActivePerson());
	}

}
