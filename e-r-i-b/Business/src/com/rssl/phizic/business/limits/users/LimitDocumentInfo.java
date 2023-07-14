package com.rssl.phizic.business.limits.users;

import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */
public class LimitDocumentInfo
{
	private static final String LIMITS_INFO_DELIMITER = ";";
	private static final String LIMITS_INFO_PARAMS_DELIMITER = ",";

	private String externalId;
	private String documentExternalId;
	private Long profileId;
	private Money amount;
	private OperType operationType;
	private ChannelType channelType;
	private Calendar operationDate;
	private String externalPhone;
	private String externalCard;
	private List<LimitInfo>  limitInfos = new ArrayList<LimitInfo>();

	public LimitDocumentInfo()
	{
	}

	public LimitDocumentInfo(LimitDocumentInfo documentInfo)
	{
		this.documentExternalId = documentInfo.getDocumentExternalId();
		this.profileId = documentInfo.getProfileId();
		this.amount = documentInfo.getAmount();
		this.operationType = documentInfo.getOperationType();
		this.channelType = documentInfo.getChannelType();
		this.operationDate = documentInfo.getOperationDate();

		if(CollectionUtils.isNotEmpty(documentInfo.getLimitInfos()))
		{
			for(LimitInfo limitInfo : documentInfo.getLimitInfos())
				limitInfos.add(new LimitInfo(limitInfo));
		}
	}

	public LimitDocumentInfo(String documentExternalId, Calendar operationDate, Long profileId, Money amount, OperType operationType, ChannelType channelType)
	{
		this.documentExternalId = documentExternalId;
		this.operationDate = operationDate;
		this.profileId = profileId;
		this.amount = amount;
		this.operationType = operationType;
		this.channelType = channelType;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getDocumentExternalId()
	{
		return documentExternalId;
	}

	public void setDocumentExternalId(String documentExternalId)
	{
		this.documentExternalId = documentExternalId;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public OperType getOperationType()
	{
		return operationType;
	}

	public void setOperationType(OperType operationType)
	{
		this.operationType = operationType;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public List<LimitInfo> getLimitInfos()
	{
		return limitInfos;
	}

	public void setLimitInfos(List<LimitInfo> limitInfos)
	{
		this.limitInfos = limitInfos;
	}

	public void addLimitInfo(LimitInfo limitInfo)
	{
		limitInfos.add(limitInfo);
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public String getExternalPhone()
	{
		return externalPhone;
	}

	public void setExternalPhone(String externalPhone)
	{
		this.externalPhone = externalPhone;
	}

	public String getExternalCard()
	{
		return externalCard;
	}

	public void setExternalCard(String externalCard)
	{
		this.externalCard = externalCard;
	}

	/**
	 * @return отработавшие лимиты в виде строки
	 */
	public String getLimitInfosAsString()
	{
		StringBuilder builder = new StringBuilder();
		for(LimitInfo limitInfo : limitInfos)
		{
			builder.append(limitInfo.getLimitType().name())
					.append(LIMITS_INFO_PARAMS_DELIMITER).append(limitInfo.getRestrictionType().name())
					.append(LIMITS_INFO_PARAMS_DELIMITER).append(limitInfo.getExternalGroupRisk() == null ? "" : limitInfo.getExternalGroupRisk())
					.append(LIMITS_INFO_DELIMITER);
		}

		return builder.toString();
	}

	/**
	 * установить отработавшие лимиты в виде строки
	 * @param limitInfosAsString лимиты в виде строки
	 */
	public void setLimitInfosAsString(String limitInfosAsString)
	{
		List<LimitInfo> infos = new ArrayList<LimitInfo>();
		if(StringHelper.isEmpty(limitInfosAsString))
			return;

		String[] strInfos = limitInfosAsString.split(LIMITS_INFO_DELIMITER);
		for(String strInfo : strInfos)
		{
			if(StringHelper.isEmpty(strInfo))
				continue;

			String[] res = strInfo.split(LIMITS_INFO_PARAMS_DELIMITER, 3);
			infos.add(new LimitInfo(
					LimitType.valueOf(StringUtils.trim(res[0])),
					RestrictionType.valueOf(StringUtils.trim(res[1])),
					res[2]));
		}

		setLimitInfos(infos);
	}
}
