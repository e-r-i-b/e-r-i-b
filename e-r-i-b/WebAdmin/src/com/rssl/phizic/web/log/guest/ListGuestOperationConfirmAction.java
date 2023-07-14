package com.rssl.phizic.web.log.guest;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.confirm.GuestOperationConfirmLogEntry;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.guest.ListGuestOperationConfirmOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author osminin
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшн просмотра гостевого журнала подтверждений операций
 */
public class ListGuestOperationConfirmAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListGuestOperationConfirmOperation.class);
	}

	@Override
	protected void doStart(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListGuestOperationConfirmForm form = (ListGuestOperationConfirmForm) frm;

		Query query = operation.createQuery(getQueryName(form));
		query.setParameter("operationUID", form.getOUID());

		form.setData(query.<GuestOperationConfirmLogEntry>executeList());
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return null;
	}
}
