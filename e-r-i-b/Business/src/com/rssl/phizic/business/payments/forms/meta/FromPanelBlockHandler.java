package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.quick.pay.PanelLogEntryType;
import com.rssl.phizic.logging.quick.pay.QuickPaymentPanelLogHelper;


/**
 * Сохраняет статискику оплаты услуг с ПБО
 * @author komarov
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class FromPanelBlockHandler extends BusinessDocumentHandlerBase
{
    private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
			return;
		JurPayment payment = (JurPayment) document;

		Long panelId = payment.getPanelBlockId();
		if(panelId == null)
			return;

		try
		{
			addLogEntry(panelId, PanelLogEntryType.OPERATION, payment.getDestinationAmount());
			payment.setPanelBlockId(null);
		}
		catch(Exception ex)
		{
			throw new DocumentException(ex);
		}
	}

	private void addLogEntry(Long blokId, PanelLogEntryType type, Money amount) throws Exception
	{
		if (blokId == null)
			return;

		Department tb = departmentService.getTB(PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId());
		if(tb == null)
			return;

		QuickPaymentPanelLogHelper.addLogEntry(blokId, type, amount,tb.getRegion());
	}
}
