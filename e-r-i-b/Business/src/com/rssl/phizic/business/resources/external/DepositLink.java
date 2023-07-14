package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Omeliyanchuk
 * @ created 02.10.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * обертка вокруг Deposit, для использования только в PersonContext
 */
public class DepositLink extends ExternalResourceLinkBase implements Comparable
{
	public static final String CODE_PREFIX = "deposit";
	public DepositLink(Deposit deposit)
	{
		setExternalId(deposit.getId());
	}

	public Object getValue() throws BusinessException, BusinessLogicException
	{
		return getDeposit();
	}

	private Deposit deposit;
	private DepositInfo depositInfo;

	public Deposit getDeposit() throws BusinessException, BusinessLogicException
	{
		if(deposit!=null)
			return deposit;

		try
		{
			deposit=getDepositService().getDepositById(getExternalId());
		}
		catch(GateException ex)
		{
			throw new BusinessException("Ошибка при получении информации о вкладе:",ex);
		}
		catch(GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}

		return deposit;
	}

	public DepositInfo getDepositInfo() throws BusinessException, BusinessLogicException
	{
		if (depositInfo!= null){
			return depositInfo;
		}

		try
		{
			depositInfo = getDepositService().getDepositInfo(getDeposit());
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка при получении дополнительной информации о вкладе:", e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		return depositInfo;
	}

	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearDepositCache(getDeposit());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private DepositService getDepositService()
	{
		return GateSingleton.getFactory().service(DepositService.class);
	}

	public int compareTo(Object o)
	{
		return getExternalId().compareTo( ((DepositLink)o).getExternalId());
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public boolean equals(Object o)
    {
        if (this == o)
        {
	        return true;
        }
        if (o == null || !(o instanceof ExternalResourceLink))
        {
	        return false;
        }

        final DepositLink that = (DepositLink) o;

        if (!getExternalId().equals(that.getExternalId()))
        {
	        return false;
        }

        return true;
    }

    public int hashCode()
    {
	    return getExternalId().hashCode();
    }

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$depositName:" + this.getId();
	}

	public String toString()
    {
        return "Вклад №" + getNumber();
    }
}
