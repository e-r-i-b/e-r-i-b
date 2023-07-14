package com.rssl.phizic.web.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.quick.pay.EditQuickPaymentPanelOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * ѕросмотр панели быстрой оплаты сотрудником.
 * @author komarov
 * @ created 13.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewQuickPaymentPanelAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditQuickPaymentPanelOperation editOperation = createOperation(EditQuickPaymentPanelOperation.class, "QuickPaymentPanelService");
		Long id = frm.getId();
		if (id != null && id != 0)
			editOperation.initialize(id);
		return editOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewQuickPaymentPanelForm form= (ViewQuickPaymentPanelForm)frm;
		form.setPanel((QuickPaymentPanel)operation.getEntity());
	}
}
