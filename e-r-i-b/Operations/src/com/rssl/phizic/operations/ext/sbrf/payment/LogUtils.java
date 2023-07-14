package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentTemplateLink;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.MaskUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Erkin
 * @ created 03.02.2011
 * @ $Author$
 * @ $Revision$
 */
class LogUtils
{
	private static final String EMPTY = "(empty)";

	private static final char EXTERNAL_ID_SEPARATOR = '|';

	static String formatCardLinkList(Collection<CardLink> cardLinks)
	{
		if (CollectionUtils.isEmpty(cardLinks))
			return EMPTY;

		List<String> strings = new ArrayList<String>(cardLinks.size());
		for (CardLink link : cardLinks)
			strings.add(MaskUtil.getCutCardNumberForLog(link.getNumber()));

		return StringUtils.join(strings, ", ") + " (" + strings.size() + " штук)";
	}

	static String formatPaymentTemplateLinkList(Collection<PaymentTemplateLink> templateLinks)
	{
		if (CollectionUtils.isEmpty(templateLinks))
			return EMPTY;

		List<String> strings = new ArrayList<String>(templateLinks.size());
		for (PaymentTemplateLink link : templateLinks)
			strings.add(formatPaymentTemplateLink(link));

		return StringUtils.join(strings, ", ") + " (" + strings.size() + " штук)";
	}

	static String formatPaymentTemplateLink(PaymentTemplateLink link)
	{
		String externalId = link.getExternalId();
		return formatPaymentTemplateExternalId(externalId);
	}

	static String formatGatePaymentTemplateList(Collection<GatePaymentTemplate> templates)
	{
		if (CollectionUtils.isEmpty(templates))
			return EMPTY;

		List<String> strings = new ArrayList<String>(templates.size());
		for (GatePaymentTemplate template : templates)
			strings.add(formatGatePaymentTemplate(template));

		return StringUtils.join(strings, ", ") + " (" + strings.size() + " штук)";
	}

	static String formatGatePaymentTemplate(GatePaymentTemplate template)
	{
		if (template == null)
			return EMPTY;
		return formatPaymentTemplateExternalId(template.getExternalId());
	}

	static String formatProviderList(Collection<BillingServiceProvider> providers)
	{
		if (CollectionUtils.isEmpty(providers))
			return EMPTY;

		List<String> strings = new ArrayList<String>(providers.size());
		for (BillingServiceProvider provider : providers)
			strings.add(formatProvider(provider));

		return StringUtils.join(strings, ", ") + " (" + strings.size() + " штук)";
	}

	static String formatProvider(BillingServiceProvider provider)
	{
		if (provider == null)
			return EMPTY;
		return String.format("%s (ID=%d)", provider.getName(), provider.getId());
	}

	private static String formatPaymentTemplateExternalId(String externalId)
	{
		if (StringHelper.isEmpty(externalId))
			return EMPTY;

		String[] chunks = StringUtils.split(externalId, EXTERNAL_ID_SEPARATOR);
		String cardNumber = chunks[0];
		String phoneNumber = chunks.length > 1 ? chunks[1] : EMPTY;
		String tail = chunks.length > 2 ? StringUtils.join(chunks, EXTERNAL_ID_SEPARATOR, 2, chunks.length) : EMPTY;

		return MaskUtil.getCutCardNumberForLog(cardNumber) + EXTERNAL_ID_SEPARATOR
				+ PhoneNumberUtil.getCutPhoneNumber(phoneNumber) + EXTERNAL_ID_SEPARATOR
				+ tail;
	}
}
