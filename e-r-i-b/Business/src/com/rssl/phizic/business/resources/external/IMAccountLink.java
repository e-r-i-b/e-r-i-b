package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.MockOffice;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.imaccounts.MockIMAccount;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.config.ResourceTypeKey;

import java.util.Calendar;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountLink extends ShowInMobileProductLink implements PaymentAbilityERL<IMAccount>
{
	public static final String CODE_PREFIX = "im-account";

	private String number;
	private Currency currency;

	/**
	 * @return ОМС клиента для данной ссылки
	 */
	public IMAccount getImAccount()
	{
		try
		{
			IMAccount imAccount = GroupResultHelper.getOneResult( getIMAccountService().getIMAccount(getExternalId()) );
			return imAccount;
		}
		catch (InactiveExternalSystemException e)
	    {
		    return getStoredIMAccount(e);
	    }
		catch (OfflineExternalSystemException e)
		{
			return getStoredIMAccount(e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении информации по ОМС за номером " + getNumber(), e);
			return createMockIMAccount();
		}
	}

	private IMAccount getStoredIMAccount(Exception e)
	{
		AbstractStoredResource storedIMAccount = StoredResourceHelper.findStoredResource(this, e);
		if (storedIMAccount == null)
		{
			log.error("Ошибка при получении информации по ОМС за номером " + getNumber(), e);
			return createMockIMAccount();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.IMACCOUNT_TYPE_KEY, storedIMAccount.getEntityUpdateTime()))
		{
			return (IMAccount) storedIMAccount;
		}
		else
		{
			// Если данные потеряли актуальность, недоступен баланс
			StoredIMAccount result = (StoredIMAccount) storedIMAccount;
			result.setBalance(null);
			result.setMaxSumWrite(null);
			return result;
		}
	}

	private IMAccount createMockIMAccount()
	{
		MockIMAccount imaccount = new MockIMAccount();
	    imaccount.setId(getExternalId());
	    imaccount.setNumber(getNumber());
	    return imaccount;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public String getAgreementNumber()
	{
		return getImAccount().getAgreementNumber();
	}

	public Calendar getOpenDate()
	{
		return getImAccount().getOpenDate();
	}

    public IMAccountState getState()
    {
	    return getImAccount().getState();
    }

	public IMAccount getValue()
	{        
		return getImAccount();
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public static IMAccountService getIMAccountService()
	{
		return GateSingleton.getFactory().service(IMAccountService.class);
	}

	public IMAccount toLinkedObjectInDBView()
	{
		MockIMAccount account = new MockIMAccount();
		account.setId(getExternalId());
		account.setNumber(getNumber());

		return account;
	}
	
	public void reset() throws BusinessException, BusinessLogicException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearIMACache(toLinkedObjectInDBView());
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

	public ResourceType getResourceType()
	{
		return ResourceType.IM_ACCOUNT;
	}

	public Money getRest()
	{
		IMAccount imaccount = getImAccount();
		return (imaccount != null) ? imaccount.getBalance() : null;
	}

	public Office getOffice() throws BusinessException
	{
		IMAccount imaccount = getImAccount();
		if (MockHelper.isMockObject(imaccount))
			return new MockOffice();

		return imaccount.getOffice();
	}

	public Money getMaxSumWrite()
	{
		IMAccount imAccountInt = getImAccount();
		if (MockHelper.isMockObject(imAccountInt))
			return null;
		return imAccountInt.getMaxSumWrite();
	}

	/**
	 * Получение клиента-владельца ОМС
	 * @return клиент
	 */
	public Client getImAccountClient()
	{
		IMAccount imAccount = getImAccount();
		if (MockHelper.isMockObject(imAccount))
		{
			log.error("Ошибка при получении информации о владельце ОМС №" + getNumber());
			return null;
		}

		if (imAccount instanceof AbstractStoredResource)
		{
			try
			{
				return PersonContext.getPersonDataProvider().getPersonData().getPerson().asClient();
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при получении информации о владельце карты №" + getNumber(), e);
				return null;
			}
		}

		try
		{
			return GroupResultHelper.getOneResult(getIMAccountService().getOwnerInfo(imAccount));
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении информации о владельце ОМС №" + getNumber(), e);
			return null;
		}
	}

	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof IMAccountLink))
		{
			return false;
		}

		if (!super.equals(o))
		{
			return false;
		}

		IMAccountLink that = (IMAccountLink) o;

		return number != null ? number.equals(that.number) : that.number == null;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + (number != null ? number.hashCode() : 0);
		return result;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$imaName:" + this.getId();
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredIMAccount.class;
	}

	public String toString()
    {
        return "ОМС №" + getNumber();
    }
}
