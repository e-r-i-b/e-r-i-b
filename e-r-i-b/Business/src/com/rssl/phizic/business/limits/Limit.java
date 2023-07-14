package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MoneyHelper;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author gulov
 * @ created 17.08.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Сущность - кумулятивный лимит
 */
public class Limit extends MultiBlockDictionaryRecordBase implements Serializable
{
	private static final String EMPTY_STRING = "";
	private static final String IMSI_LOG_RESTRICTION_TYPE = "IMSI";
	private static final String OBSTRUCTION_LOG_RESTRICTION_TYPE = "Заградительный";
	private static final String DUMB_TB = "888";

	private Long id;                                //уникальный код лимита
	private String tb;                              //тербанк, в котором введен лимит
	private LimitType type;                         //тип лимита
	private OperationType operationType;            //тип действия при превышении лимита
	private RestrictionType restrictionType;        //тип ограничения
	private ChannelType channelType;                //канал учета лимита
	private Calendar creationDate;                  //дата ввода
	private Calendar startDate;                     //дата начала действия
	private Money amount;                           //величина лимита
	private Status status;                          //статус
	private Long operationCount;                    //количество операций
	private GroupRisk groupRisk;                    //группа риска
	private LimitState state = LimitState.DRAFT;    // состояние лимита, изначально черновик
	private SecurityType securityType;              //уровень безопасности
	private Calendar endDate;                       //дата окончания действия лимита

	public Limit() {}

    public Limit(LimitType limitType, ChannelType channelType)
    {
        this.tb             = DUMB_TB;
        this.type           = limitType;
        this.channelType    = channelType;
        this.creationDate   = Calendar.getInstance();
    }

	public Limit(String tb, LimitType limitType, ChannelType channelType)
	{
		this.tb             = tb;
		this.type           = limitType;
		this.channelType    = channelType;
		this.creationDate   = Calendar.getInstance();
	}

	public Limit(String tb, LimitType limitType, ChannelType channelType, SecurityType securityType)
	{
		this(tb, limitType, channelType);
		this.securityType = securityType;
	}

	public Limit(Limit limit, LimitType type)
	{
		this(limit.getTb(),
				type,
				limit.getChannelType(),
				limit.getOperationType(),
				limit.getRestrictionType(),
				limit.getStartDate(),
				limit.getAmount());

		this.creationDate = limit.getCreationDate();
		this.status = limit.getStatus();
		this.groupRisk = limit.getGroupRisk();
		this.operationCount = limit.getOperationCount();

	}

	public Limit(String tb, LimitType limitType, ChannelType channelType, OperationType operationType, RestrictionType restrictionType, Calendar startDate)
	{
		this(tb, limitType, channelType);
		this.operationType = operationType;
		this.restrictionType = restrictionType;
		this.startDate = startDate;
	}

	public Limit(String tb, LimitType limitType, ChannelType channelType, OperationType operationType, RestrictionType restrictionType, Calendar startDate, Money amount)
	{
		this(tb, limitType, channelType, operationType, restrictionType, startDate);
		this.amount = amount;
	}

	public Limit(String tb, LimitType limitType, ChannelType channelType, OperationType operationType, RestrictionType restrictionType, Calendar startDate, Long operationCount)
	{
		this(tb, limitType, channelType, operationType, restrictionType, startDate);
		this.operationCount = operationCount;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public LimitType getType()
	{
		return type;
	}

	public void setType(LimitType type)
	{
		this.type = type;
	}

	public GroupRisk getGroupRisk()
	{
		return groupRisk;
	}

	public void setGroupRisk(GroupRisk groupRisk)
	{
		this.groupRisk = groupRisk;
	}

	public OperationType getOperationType()
	{
		return operationType;
	}

	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	public Long getOperationCount()
	{
		return operationCount;
	}

	public void setOperationCount(Long operationCount)
	{
		this.operationCount = operationCount;
	}

	public RestrictionType getRestrictionType()
	{
		return restrictionType;
	}

	public void setRestrictionType(RestrictionType restrictionType)
	{
		this.restrictionType = restrictionType;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public LimitState getState()
	{
		return state;
	}

	public void setState(LimitState state)
	{
		this.state = state;
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	/*********************************************
	 * Методы, используемые для логирования, возвращают данные в удобном для отображения формате
	 */

	public String getLogCreationDate()
	{
		return DateHelper.formatDateToString2(creationDate);
	}

	public String getLogGroupRisk()
	{
		StringBuilder logGroupRisk = new StringBuilder();
		if (groupRisk != null)
		{
			logGroupRisk.append("ID:").append(groupRisk.getId()).append(", ");
			logGroupRisk.append(groupRisk.getName());
		}
		return logGroupRisk.toString();
	}

	public String getLogAmount()
	{
		return (amount == null) ? EMPTY_STRING : MoneyHelper.formatMoney(amount);
	}

	public String getLogStartDate()
	{
		return DateHelper.formatDateToString2(startDate);
	}

	public String getLogEmployeeLogin()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin().getId().toString();
	}

	public String getLogRestrictionType()
	{
		if (type == LimitType.IMSI)
			return IMSI_LOG_RESTRICTION_TYPE;

		if (type == LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATION || type == LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS)
			return OBSTRUCTION_LOG_RESTRICTION_TYPE;

		return (restrictionType == null) ? EMPTY_STRING : restrictionType.getDescription();
	}

	public String getLogOperationType()
	{
		return (operationType == null) ? EMPTY_STRING : operationType.getDescription();
	}

	public String getLogChannelType()
	{
		return channelType.getDescription();
	}
}
