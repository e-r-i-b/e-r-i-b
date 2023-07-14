package com.rssl.phizic.web.cards.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.sberbankForEveryDay.ViewGuestDebitCardClaimOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * ѕросмотр за€вки гост€ на дебетовые карты
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewGuestDebitCardClaimAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewGuestDebitCardClaimOperation operation = createOperation(ViewGuestDebitCardClaimOperation.class);
		ViewGuestDebitCardClaimForm form = (ViewGuestDebitCardClaimForm) frm;
		operation.initialize(form.getId());
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
		ViewGuestDebitCardClaimForm form = (ViewGuestDebitCardClaimForm) frm;
		IssueCardDocumentImpl document = (IssueCardDocumentImpl) entity;
		form.setClaim(document);
	}
}
