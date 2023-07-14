package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.depo.MockDepoAccount;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.cache.RefreshCacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoAccountService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author lukina
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "depo-account";
	private static final int TIME_TO_REFRESH = 60; // время нахождения объекта в кэше, после которого его можно удалять

	private String accountNumber;

	/**
	 * @return номер счета депо
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber - номер счета депо
	 */
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public Object getValue()
	{
		return getDepoAccount();
	}

	private DepoAccount toDepoAccountFromDb()
	{
		MockDepoAccount account = new MockDepoAccount();
		account.setId(getExternalId());

		return account;
	}

	/**
	 * Очистка кеша для счета депо
	 */
	public void reset() throws BusinessException, BusinessLogicException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearDepoAccountCache(toDepoAccountFromDb());
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

	/**
	 * Обновление кеша для счета депо, если объект пролежал в кэше дольше TIME_TO_REFRESH
	 */
	public void refresh()
	{
		DepoAccount depoAccount = getDepoAccount();
		boolean refresh = GateSingleton.getFactory().service(RefreshCacheService.class).refreshDepoCacheService(depoAccount,TIME_TO_REFRESH);
		if (refresh)
			XmlEntityListCacheSingleton.getInstance().clearCache(depoAccount, DepoAccount.class);
	}

	/**
	 * @return  счет депо
	 */
	public DepoAccount getDepoAccount()
	{
		try
        {
	        DepoAccount depoAccount = GroupResultHelper.getOneResult(getDepoAccountService().getDepoAccount(getExternalId()));
	        return depoAccount;
        }
		catch (InactiveExternalSystemException e)
	    {
		    return getStoredDepoAccount(e);
	    }
		catch (OfflineExternalSystemException e)
		{
			return getStoredDepoAccount(e);
		}
	    catch (Exception e)
	    {
		    //на случай падения в jsp
		    log.error("Ошибка при получении счета депо №" + getAccountNumber(), e);
		    return new MockDepoAccount();
	    }
	}

	private DepoAccount getStoredDepoAccount(Exception e)
	{
		AbstractStoredResource storedDepo = StoredResourceHelper.findStoredResource(this, e);
		if (storedDepo == null)
		{
			log.error("Ошибка при получении счета депо №" + getAccountNumber(), e);
			return new MockDepoAccount();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.DEPOACCOUNT_TYPE_KEY, storedDepo.getEntityUpdateTime()))
		{
			return (DepoAccount) storedDepo;
		}
		else
		{
			// Если данные потеряли актуальность, недоступен баланс
			StoredDepoAccount result = (StoredDepoAccount) storedDepo;
			result.setDebt(null);
			return result;
		}
	}

	/**
	 * @return информация по позиции для счета депо
	 */
	public DepoAccountPosition getDepoAccountPositionInfo() throws GateLogicException
	{
		try
        {
	        DepoAccount depoAccount = getDepoAccount();
	        if (MockHelper.isMockObject(depoAccount))
				return null;

	        return getDepoAccountService().getDepoAccountPosition(depoAccount);
        }
	    catch (GateException e)
	    {
		    log.error("Ошибка при получении информации по позиции для счета депо №" + getAccountNumber(), e);
		    return null;
	    }
	}

	public Client getOwner()
	{
		try
        {
	        DepoAccount depoAccount = getDepoAccount();
	        if (MockHelper.isMockObject(depoAccount))
	            return null;

	        return GroupResultHelper.getOneResult(getDepoAccountService().getDepoAccountOwner(depoAccount));
        }
		catch (InactiveExternalSystemException e)
	    {
		    throw e;
	    }
	    catch (Exception e)
	    {
		    log.error("Ошибка при получении владельца счета депо №" + getAccountNumber(), e);
		    return null;
	    }
	}

	public static DepoAccountService getDepoAccountService()
	{
		return  GateSingleton.getFactory().service(DepoAccountService.class);
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.DEPO_ACCOUNT;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$depoAccountName:" + this.getId();
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredDepoAccount.class;
	}

	public String toString()
    {
        return "Счет депо №" + getNumber();
    }
}
