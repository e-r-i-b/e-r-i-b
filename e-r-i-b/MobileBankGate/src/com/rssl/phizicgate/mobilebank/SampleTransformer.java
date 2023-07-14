package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankTemplateImpl;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.Transformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
class SampleTransformer implements Transformer<List<MobileBankTemplate>, SampleInfo>
{
	private final MobileBankCardInfo cardInfo;

	private final Long maxCount;

	private final boolean collectEmptySamples;

	private long count = 0;

	/**
	 * Конструктор
	 * @param cardInfo
	 * @param maxCount
	 * @param collectEmptySamples - флажок "собирать шаблоны без плательщиков"
	 */
	SampleTransformer(MobileBankCardInfo cardInfo, Long maxCount, boolean collectEmptySamples)
	{
		if (cardInfo == null)
			throw new NullPointerException("Аргумент 'cardInfo' не может быть null");
		if (maxCount != null && maxCount<=0)
			throw new IllegalArgumentException("Argument 'maxCount' must be null or positive");

		this.cardInfo = cardInfo;
		this.maxCount = maxCount;
		this.collectEmptySamples = collectEmptySamples;
	}

	public List<MobileBankTemplate> transform(SampleInfo sampleInfo)
	{
		if (sampleInfo == null)
			throw new NullPointerException("Argument 'sampleInfo' cannot be null");

		Map<String, Set<String>> destList = sampleInfo.getDestList();
		if (MapUtils.isEmpty(destList))
			return Collections.emptyList();

		List<MobileBankTemplate> result =
				new ArrayList<MobileBankTemplate>(destList.size());
		for (Map.Entry<String, Set<String>> entry : destList.entrySet())
		{
			String recipient = entry.getKey();
			List<String> payers = Collections.emptyList();
			if (!CollectionUtils.isEmpty(entry.getValue())) {
				payers = new ArrayList<String>(entry.getValue());
				if (maxCount != null)
					payers = ListUtil.truncateList(payers, (int) (maxCount-count));
			}
			// Шаблон без идентификаторов плательщика не нужен
			if (!collectEmptySamples && payers.isEmpty())
				continue;

			MobileBankTemplateImpl template = new MobileBankTemplateImpl();
			template.setCardInfo(cardInfo);
			template.setRecipient(recipient);
			template.setPayerCodes(payers.toArray(new String[payers.size()]));
			result.add(template);

			count += payers.size();
			if (maxCount != null && count >= maxCount)
				break;
		}

		return result;
	}

	long getCount()
	{
		return count;
	}
}
