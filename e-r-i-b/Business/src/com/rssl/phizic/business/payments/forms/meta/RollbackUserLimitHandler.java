package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.exceptions.WrongDocumentTypeException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Хэндлер для восстановления лимита в случае если пришел отказ
 * @author niculichev
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class RollbackUserLimitHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject object, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(object instanceof AbstractAccountsTransfer))
			throw new WrongDocumentTypeException((BusinessDocument)object, AbstractAccountsTransfer.class);

		AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) object;

		try
		{

			Calendar operationDate = getOperationDate(transfer);
			// если списание с лимита происходило в другой день, восстанавление лимита не корректно
			if (DateHelper.getCurrentDate().compareTo(DateHelper.clearTime((Calendar)operationDate.clone())) != 0)
			{
				return;
			}

			//noinspection unchecked
			DocumentLimitManager.createRevertLimitManager(transfer.getOperationUID(), operationDate).revertLimits();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private Calendar getOperationDate(AbstractAccountsTransfer transfer)
	{
		return transfer.getAdditionalOperationChannel() != null ? transfer.getAdditionalOperationDate() : transfer.getClientOperationDate();
	}
}
