package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.security.Security;
import com.rssl.phizic.business.dictionaries.security.SecurityService;
import com.rssl.phizic.business.documents.SecuritiesTransferClaim;
import com.rssl.phizic.common.types.Money;

/**
 * ’ендлер провер€ет изменение номинальной стоимости ценных бумаг.
 * @author komarov
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class SecuritiesTransferClaimHandler extends BusinessDocumentHandlerBase
{
	private static final SecurityService securityService = new SecurityService();
	private static final String MESSAGE  =
			        "Ќоминальна€ стоимость ценных бумаг, указанных в поручении, изменилась в справочнике ценных бумаг.";


	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof SecuritiesTransferClaim))
			throw new DocumentException("Ќеверный тип платежа. ќжидаетс€ SecuritiesTransferClaim");
		
		SecuritiesTransferClaim payment = (SecuritiesTransferClaim) document;
		String insideCode = payment.getInsideCode();

		try
		{
			Security security = securityService.findByInsideCode(insideCode);
			String nominalAmount = payment.getAttribute("nominal-amount").getStringValue();
			Money sNominalAmount = security.getNominal();
			String securityNominalAmount = sNominalAmount == null ? "" : security.getNominal().getDecimal().toString();
			if(!securityNominalAmount.equals(nominalAmount))
			{
				payment.getAttribute("nominal-amount").setStringValue(securityNominalAmount);
				throw new DocumentLogicException(MESSAGE);
			}
		}
		catch(BusinessException e)
		{
		    throw new DocumentException(e);
		}

	}
}
