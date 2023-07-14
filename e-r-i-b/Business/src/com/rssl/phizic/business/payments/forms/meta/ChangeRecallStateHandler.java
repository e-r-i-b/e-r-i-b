package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.RollbackOrderClaim;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.einvoicing.RecallState;
import com.rssl.phizic.logging.LogThreadContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Хендлер изменения статуса возврата или отмены в EInvoicing.
 *
 * @author bogdanov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeRecallStateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		RecallState state = RecallState.valueOf(getParameter("state"));

		if (document instanceof RollbackOrderClaim)
		{
			changeStateForRefund((RollbackOrderClaim) document, state);
			return;
		}

		throw new DocumentException("Документ должен быть: " + RollbackOrderClaim.class);
	}

	private void changeStateForRefund(RollbackOrderClaim document, RecallState newState) throws DocumentException, DocumentLogicException
	{
		boolean resetLogThreadContext = LogThreadContext.getIPAddress() == null;
		try
		{
			if (resetLogThreadContext)
				LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			ShopHelper.get().changeRefundStatus(document.getRefundUuid(), newState, document.getIdFromPaymentSystem(), document);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch(UnknownHostException e)
		{
			throw new DocumentException(e);
		}
		finally
		{
			if (resetLogThreadContext)
				LogThreadContext.clear();
		}
	}
}
