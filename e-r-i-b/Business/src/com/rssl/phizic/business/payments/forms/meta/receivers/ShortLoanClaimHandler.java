package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Проверяет, что по выбранному кредитному продукту еще нет заявок, отправленных на рассмотрение, в случаи наличия  пред одобренного предложения
 */
public class ShortLoanClaimHandler  extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService service = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof ShortLoanClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается ShortLoanClaim)");
		}

		ShortLoanClaim claim = (ShortLoanClaim) document;
		try
		{
			//Если привязано пред одобренное предложение
			if (StringHelper.isNotEmpty(claim.getLoanOfferId()))
			{
				//Если есть уже отправленное в банк.
				if(service.isExistDispatchedClaim(claim))
					throw new DocumentLogicException("Вы не можете повторно отправить в банк заявку на выдачу предодобренного кредита, пока предыдущая заявка не обработана в банке.");
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}


}
