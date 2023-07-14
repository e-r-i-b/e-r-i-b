package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author osminin
 * @ created 21.12.2010
 * @ $Author$
 * @ $Revision$
 *
 * Хэндлер специально для того, что бы кидать вместо гейтового исключения о том, что опперация недоступна,
 * исключение для выбора счета и карты с одинаковой валютой, когда подразделение не подключено к шине,
 * клиент СРБ и осуществляет конверсионную операцию счет-карта
 * Лучше способа не придумано.
 */
public class AccountToCardConvertionSRBDenyHandler extends BusinessDocumentHandlerBase
{
	private static final String ERROR_MESSAGE_PARAMETER_NAME = "error-message";

	public void process(StateObject object, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(object instanceof GateExecutableDocument))
			return;

		if (!(object instanceof InternalTransfer))
			return;

		InternalTransfer document = (InternalTransfer) object;
		if (document.getType()!= AccountToCardTransfer.class)
			return; // если операция не счет-карта, то пропускаем

		if (!document.isConvertion())
			return; // если документ не конверсионный, то пропускаем

		try
		{
			ExtendedDepartment department = (ExtendedDepartment) document.getDepartment();
			if (department.isEsbSupported())
				return; // если подразделение подключено к шине, то пропускаем
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		try
		{
			if (!ESBHelper.isSRBPayment(document))
			{
				return; // если клиент не СРБ, то пропускаем
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}

		// кидаем исключение с нужным сообщением
		throw new DocumentLogicException(getParameter(ERROR_MESSAGE_PARAMETER_NAME));
	}
}
