package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.gate.loanclaim.type.ChannelCBRegAApproveType;
import com.rssl.phizic.gate.loanclaim.type.ChannelPFRRegAApproveType;
import org.apache.commons.lang.BooleanUtils;

/**
 * Хендлер для заполнения полей текущим каналом в заявке на кредит
 * @author Rtischeva
 * @ created 13.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimChannelSettingHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;

		ChannelCBRegAApproveType channelCBRegAApproveType = ChannelCBRegAApproveType.fromApplication(ApplicationInfo.getCurrentApplication());
		extendedLoanClaim.setChannelCBRegAApprove(channelCBRegAApproveType);
		Object agreeRequestPFR = extendedLoanClaim.getAttribute("agreeRequestPFP").getValue();
		if (agreeRequestPFR != null && BooleanUtils.isTrue((Boolean) agreeRequestPFR))
		{
			ChannelPFRRegAApproveType channelPFRRegAApproveType = ChannelPFRRegAApproveType.fromApplication(ApplicationInfo.getCurrentApplication());
			extendedLoanClaim.setChannelPFRRegAApprove(channelPFRRegAApproveType);
		}
	}
}
