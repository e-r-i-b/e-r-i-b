package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.EditMoneyBoxOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author vagin
 * @ created 02.02.15
 * @ $Author$
 * @ $Revision$
 * Ёкшен редактировани€ копилки дл€ mobile.
 */
public class EditMoneyBoxMobileAction extends EditMobilePaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("makeLongOffer");
		map.remove("makeAutoTransfer");
		return map;
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			DocumentSource source = createNewDocumentSource(form.getForm(), new RequestValuesSource(currentRequest()), messageCollector);

			EditMoneyBoxOperation operation = createOperation(EditMoneyBoxOperation.class, getServiceName(source));
			operation.initialize(source, getRequestFieldValuesSource());

			return operation;
		}
		catch (FormException e)
		{
			if (e.getCause() instanceof GateLogicException)
				throw new BusinessLogicException(e.getCause().getMessage(), e);
			throw new BusinessException(e);
		}
	}
}
