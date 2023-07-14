package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.IMAProductService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Mescheryakova
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningClaimHandler extends BusinessDocumentHandlerBase
{
	private static final IMAProductService imaService = new IMAProductService();
	private static final String IS_NOT_ACTUALITY = "Условия размещения средств по выбранному ОМС изменились. Пожалуйста, отредактируйте или создайте новую заявку";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new DocumentException("Неверный тип платежа. Ожидается IMAOpeningClaimHandler");

		IMAOpeningClaim imaOpeningClaim  = (IMAOpeningClaim) document;

		try
		{
			IMAProduct imaProduct = imaService.findById(imaOpeningClaim.getIMAProductID());

			boolean isNameEquals = imaProduct.getDefaultLocaleName().equals(imaOpeningClaim.getDefaultLocaleIMAProductName());
			boolean isTypeEquals = imaProduct.getType().equals(imaOpeningClaim.getIMAProductType());
			boolean isSubTypeEquals = imaProduct.getSubType().equals(imaOpeningClaim.getIMAProductSubType());
			if (isNameEquals && isTypeEquals && isSubTypeEquals)
				return;
			
			throw new DocumentLogicException(IS_NOT_ACTUALITY);
		}
		catch(BusinessException e)
		{
			throw new DocumentException("Ошибка проверки изменения ОМС", e);
		}
	}
}
