package com.rssl.phizic.web.client.basket.accountingEntity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author osminin
 * @ created 09.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Асинхронное сохранение объекта учета "гараж".
 */
public class AsyncEditGarageAccountingEntityAction extends EditGarageAccountingEntityAction
{
	@Override
	protected boolean isAjax()
	{
		return true;
	}

	@Override
	protected ActionForward createStartActionForward(ActionForm form, ActionMapping mapping) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_ASYNC_FAILED);
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_ASYNC_SUCCESS);
	}
}
