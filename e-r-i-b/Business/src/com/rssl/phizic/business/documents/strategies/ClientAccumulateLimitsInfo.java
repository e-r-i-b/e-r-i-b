package com.rssl.phizic.business.documents.strategies;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.limits.users.LimitInfo;
import com.rssl.phizic.business.limits.users.OperType;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Информация по накопленным лимитам в переданных транзакциях
 * @author niculichev
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccumulateLimitsInfo
{
	private static final String DELIMITER = ",";
	private static final String EXTERNAL_LIMIT_AMOUNT_MESSAGE   = "Накопленная сумму для лимита на получателя не может быть расчитам через транзакции одного клиента";
	private static final String EXTERNAL_LIMIT_COUNT_MESSAGE    = "Количество опрераций для лимита на получателя не может быть расчитам через транзакции одного клиента";
	private static final String EXTERNAL_LIMIT_MESSAGE          = "В информациях по лимитам не должно быть записей по переводам внешнему получателю";

	private Map<String, Money> amounts = new HashMap<String, Money>();
	private Map<String, Long> counts = new HashMap<String, Long>();

	public ClientAccumulateLimitsInfo(List<LimitDocumentInfo> limitDocumentInfos) throws BusinessException
	{
		if(CollectionUtils.isEmpty(limitDocumentInfos))
			return;

		// считаем накопленные суммы по лимитам
		for(LimitDocumentInfo documentInfo : limitDocumentInfos)
			addLimitDocumentInfo(documentInfo);
	}

	/**
	 * Накопленная сумма в рамках лимита
	 * @param limit лимит
	 * @return сумма
	 */
	public Long getAccumulateCount(Limit limit) throws BusinessException
	{
		if(limit.getType() == LimitType.EXTERNAL_CARD || limit.getType() == LimitType.EXTERNAL_PHONE)
			throw new BusinessException(EXTERNAL_LIMIT_COUNT_MESSAGE);

		return getAccumulateCount(buildLimitKey(limit.getType(), limit.getRestrictionType(), limit.getGroupRisk() != null ? limit.getGroupRisk().getExternalId() : null));
	}

	/**
	 * Количество операций в рамках лимита
	 * @param limit лимит
	 * @return количество операций
	 */
	public Money getAccumulateAmount(Limit limit) throws BusinessException
	{
		if(limit.getType() == LimitType.EXTERNAL_CARD || limit.getType() == LimitType.EXTERNAL_PHONE)
			throw new BusinessException(EXTERNAL_LIMIT_AMOUNT_MESSAGE);

		return getAccumulateAmount(buildLimitKey(limit.getType(), limit.getRestrictionType(), limit.getGroupRisk() != null ? limit.getGroupRisk().getExternalId() : null));
	}

	public Money getOverallAccumulateAmount(Limit limit) throws BusinessException
	{
		return getAccumulateAmount(buildLimitKey(limit.getType(), limit.getRestrictionType(), null));
	}

	private void addLimitDocumentInfo(LimitDocumentInfo documentInfo) throws BusinessException
	{
		List<LimitInfo> limitInfos = documentInfo.getLimitInfos();
		if(CollectionUtils.isEmpty(limitInfos))
			return;

		for(LimitInfo limitInfo : limitInfos)
		{
			if(limitInfo.getLimitType() == LimitType.EXTERNAL_CARD || limitInfo.getLimitType() == LimitType.EXTERNAL_PHONE)
				throw new BusinessException(EXTERNAL_LIMIT_MESSAGE);

			RestrictionType restrictionType = limitInfo.getRestrictionType();
			switch (restrictionType)
			{
				case DESCENDING:
				case CARD_ALL_AMOUNT_IN_DAY:
				case PHONE_ALL_AMOUNT_IN_DAY:
				case MAX_AMOUNT_BY_TEMPLATE:
				case MIN_AMOUNT:
				{
					// не нужны в этом цикле
					break;
				}
				case OVERALL_AMOUNT_PER_DAY:
				case AMOUNT_IN_DAY:
				case IMSI:
				{
					if(documentInfo.getOperationDate().compareTo(DateHelper.getPreviousDay()) >= 0)
						addLimitAmount(limitInfo, documentInfo.getAmount(), documentInfo.getOperationType());

					break;
				}
				case OPERATION_COUNT_IN_DAY:
				{
					if(documentInfo.getOperationDate().compareTo(DateHelper.getPreviousDay()) >= 0)
						addLimitCount(limitInfo, documentInfo.getOperationType());
					break;
				}
				case OPERATION_COUNT_IN_HOUR:
				{
					if(documentInfo.getOperationDate().compareTo(DateHelper.getPreviousHours(1)) >= 0)
						addLimitCount(limitInfo, documentInfo.getOperationType());
					break;
				}
			}
		}
	}

	private void addLimitAmount(LimitInfo limitInfo, Money money, OperType operType) throws BusinessException
	{
		String key =  buildLimitKey(limitInfo.getLimitType(), limitInfo.getRestrictionType(), limitInfo.getExternalGroupRisk());
		Money res = getAccumulateAmount(key);

		switch (operType)
		{
			case commit:
				res = res.add(money);
				break;
			case rollback:
				res = res.sub(money);
		}

		amounts.put(key, res);
	}

	private void addLimitCount(LimitInfo limitInfo, OperType operType) throws BusinessException
	{
		String key =  buildLimitKey(limitInfo.getLimitType(), limitInfo.getRestrictionType(), limitInfo.getExternalGroupRisk());
		long res = getAccumulateCount(key);

		switch (operType)
		{
			case commit:
				res = res + 1;
				break;
			case rollback:
				res = res - 1;
		}

		counts.put(key, res);
	}

	private Long getAccumulateCount(String key) throws BusinessException
	{
		Long count = counts.get(key);
		return count == null ? 0L : count;
	}

	private Money getAccumulateAmount(String key) throws BusinessException
	{
		Money money = amounts.get(key);
		return money == null ? new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency()) : money;
	}

	private String buildLimitKey(LimitType limiType, RestrictionType restrictionType, String externalGroupRisk)
	{
		StringBuilder builder = new StringBuilder();

		builder.append(limiType.name());
		builder.append(DELIMITER).append(restrictionType.name());

		if(StringHelper.isNotEmpty(externalGroupRisk))
			builder.append(DELIMITER).append(externalGroupRisk);

		return builder.toString();
	}
}
