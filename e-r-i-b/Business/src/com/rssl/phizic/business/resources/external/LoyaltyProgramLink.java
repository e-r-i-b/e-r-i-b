package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loyaltyProgram.mock.MockLoyaltyProgram;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Информация о программе лояльности клиента
 * @author lukina
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "loyalty-program";
    private LoyaltyProgramState state;
	private BigDecimal balance;
	private Calendar updateTime;

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public Calendar getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime)
	{
		this.updateTime = updateTime;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public LoyaltyProgram getValue() throws BusinessException, BusinessLogicException
	{
		return getLoyaltyProgram();
	}

	private LoyaltyProgram toLoyaltyProgramFromDb()
	{
		MockLoyaltyProgram program = new MockLoyaltyProgram();
		program.setExternalId(getExternalId());

		return program;
	}

	public void reset()
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearLoyaltyProgramCache(toLoyaltyProgramFromDb());
		}
		catch (Exception e)
		{
			log.error("Ошибка очистки кеша программы лояльности.", e);
		}
	}

	public ResourceType getResourceType()
	{
		return ResourceType.LOYALTY_PROGRAM;
	}

	public String getPatternForFavouriteLink()
	{
		return  "$$loyaltyProgranLink:" + this.getId();
	}

	public LoyaltyProgram getLoyaltyProgram()
	{
		try
		{
			LoyaltyProgramService loyaltyProgramService = GateSingleton.getFactory().service(LoyaltyProgramService.class);
			return loyaltyProgramService.getClientLoyaltyProgram(getExternalId());
		}
		catch (IKFLException e)
		{
			log.error("Ошибка при получении программы лояльности по externalId " + getExternalId(), e);
			return null;
		}
	}

	public LoyaltyProgramState getState()
	{
		return state;
	}

	public void setState(LoyaltyProgramState state)
	{
		this.state = state;
	}
}
