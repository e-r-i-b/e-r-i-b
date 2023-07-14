package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderSubType;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.util.MaskPhoneNumberUtil;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Стратегия учета лимита на получателя ЕРМБ по номеру телефона
 *
 * @author khudyakov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */
public class RecipientByPhoneDocumentLimitStrategy extends RecipientDocumentLimitStrategy
{
	public RecipientByPhoneDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (!super.needUse(document))
		{
			return false;
		}

		ServiceProviderShort provider = DocumentHelper.getDocumentProvider(document);
		if (provider == null)
		{
			return false;
		}

		if (ServiceProviderSubType.mobile != provider.getSubType())
		{
			return false;
		}

		return !MaskPhoneNumberUtil.isOurPhone(LimitHelper.getExternalPhoneProviderValue(document));
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		return limitService.findActiveLimits(getDocument(), LimitType.EXTERNAL_PHONE, ChannelType.ERMB_SMS);
	}

	protected void writeToJournal(LimitDocumentInfo limitDocumentInfo) throws BusinessException, BusinessLogicException
	{
		if(CollectionUtils.isNotEmpty(limits))
		{
			String externalPhone = LimitHelper.getExternalPhoneProviderValue(getDocument());
			limitDocumentInfo.setExternalPhone(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(externalPhone));
		}
	}
}
