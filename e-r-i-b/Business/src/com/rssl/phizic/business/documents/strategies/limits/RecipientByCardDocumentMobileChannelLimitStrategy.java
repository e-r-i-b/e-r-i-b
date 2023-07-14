package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/** Стратегия учета лимита на получателя по номеру карты (краудгифтинг)
 * @author muhin
 * @ created 05.11.14
 * @ $Author$
 * @ $Revision$
 */
public class RecipientByCardDocumentMobileChannelLimitStrategy extends DocumentLimitStrategyBase
{
	public RecipientByCardDocumentMobileChannelLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		if (config.getApplicationInfo().isMobileApi() && super.needUse(document))
		{
			//лимит применяется только в рамках краудгифтинга
			return isFundPayment(document);
		}
		return false;
	}

	@Override
	public List<Limit> getStrategyLimits() throws BusinessException
	{
		return limitService.findActiveLimits(getDocument(), LimitType.EXTERNAL_CARD, ChannelType.MOBILE_API);
	}

	protected void writeToJournal(LimitDocumentInfo limitDocumentInfo) throws BusinessException, BusinessLogicException
	{
		if(CollectionUtils.isNotEmpty(limits))
			limitDocumentInfo.setExternalCard(LimitHelper.getExternalCardProviderValue(getDocument()));
	}

	private boolean isFundPayment(BusinessDocument document)
	{
		if (!(document instanceof RurPayment))
		{
			return false;
		}
		RurPayment payment = (RurPayment) document;
		return StringHelper.isNotEmpty(payment.getFundResponseId());
	}
}
