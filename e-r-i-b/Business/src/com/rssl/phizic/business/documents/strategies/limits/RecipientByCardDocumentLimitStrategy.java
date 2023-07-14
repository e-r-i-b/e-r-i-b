package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.limits.users.LimitInfo;
import com.rssl.phizic.business.limits.users.OperType;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Стратегия учета лимита на получателя ЕРМБ по номеру карты
 *
 * @author khudyakov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */
public class RecipientByCardDocumentLimitStrategy extends RecipientDocumentLimitStrategy
{
	public RecipientByCardDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (super.needUse(document))
		{
			return isCardsTransfer(document);
		}
		return false;
	}

	@Override
	public List<Limit> getStrategyLimits() throws BusinessException
	{
		return limitService.findActiveLimits(getDocument(), LimitType.EXTERNAL_CARD, ChannelType.ERMB_SMS);
	}

	protected void writeToJournal(LimitDocumentInfo limitDocumentInfo) throws BusinessException, BusinessLogicException
	{
		if(CollectionUtils.isNotEmpty(limits))
			limitDocumentInfo.setExternalCard(LimitHelper.getExternalCardProviderValue(getDocument()));
	}

	private boolean isCardsTransfer(BusinessDocument document)
	{
		if (!(document instanceof GateDocument))
		{
			return false;
		}

		Class<? extends GateDocument> type = ((GateDocument) document).getType();
		return (type == ExternalCardsTransferToOurBank.class || type == ExternalCardsTransferToOtherBank.class || type == InternalCardsTransfer.class);
	}
}
