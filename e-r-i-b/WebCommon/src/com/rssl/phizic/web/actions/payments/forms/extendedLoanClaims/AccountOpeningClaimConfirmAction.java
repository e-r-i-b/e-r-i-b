package com.rssl.phizic.web.actions.payments.forms.extendedLoanClaims;

import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;

import java.util.Map;

/**
 * @author Rtischeva
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningClaimConfirmAction extends ConfirmDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.next");
		map.remove("button.prev");
		map.remove("button.saveAsDraft");
		map.remove("makeLongOffer");
		map.remove("button.makeLongOffer");
		map.remove("button.remove");
		map.remove("button.edit");
		map.remove("button.search");
		map.remove("back");
		map.remove("button.edit_template");
		map.remove("button.makeAutoTransfer");
		map.remove("afterAccountOpening");
		map.remove("button.changeConditions");
		return map;
	}
}
