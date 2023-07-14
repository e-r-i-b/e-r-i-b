package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Map;

/**
 * ’ендлер, проставл€ющий код депозитного продукта, разрешенного дл€ открыти€.
 * “олько дл€ случаев, когда нет возможности получать код иным способом (дл€ mApi < 8)
 *
 * @author EgorovaA
 * @ created 18.08.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningDepositGroupHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Ќеверный тип платежа id=" + document.getId() +
					" (ќжидаетс€ AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if ((MobileApiUtil.isMobileApiLT(MobileAPIVersions.V8_00) || ApplicationUtil.isATMApi()) && StringHelper.isEmpty(accountOpeningClaim.getAccountGroup()))
		{
			try
			{
				String depositType = String.valueOf(accountOpeningClaim.getAccountType());
				MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
				Map<String, String> allowedCodes = mobileApiConfig.getOldDepositCodesList();
				accountOpeningClaim.setAccountGroup(allowedCodes.get(depositType));

			}
			catch (Exception e)
			{
				throw new DocumentException(e);
			}
		}
	}
}
