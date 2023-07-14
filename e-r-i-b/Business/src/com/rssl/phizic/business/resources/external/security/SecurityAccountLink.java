package com.rssl.phizic.business.resources.external.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.StoredResourceHelper;
import com.rssl.phizic.business.securityAccount.MockSecurityAccount;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.security.SecurityAccountService;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class SecurityAccountLink  extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "security";
	private String number;
	private boolean onStorageInBank;

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public boolean getOnStorageInBank()
	{
		return onStorageInBank;
	}

	public void setOnStorageInBank(boolean onStorageInBank)
	{
		this.onStorageInBank = onStorageInBank;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public Object getValue() throws BusinessException, BusinessLogicException
	{
		return getSecurityAccount();
	}

	public SecurityAccount getSecurityAccount()
	{
		try
		{
			SecurityAccount securityAccount = GateSingleton.getFactory().service(SecurityAccountService.class).getSecurityAccount(getExternalId());
			return securityAccount;
		}
		catch (InactiveExternalSystemException e)
		{
			return getStoredSecurityAccount(e);
		}
		catch (OfflineExternalSystemException e)
		{
			return getStoredSecurityAccount(e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении сберегательного сертификата по externalId " + getExternalId(), e);
			return  new MockSecurityAccount();
		}
	}

	private SecurityAccount getStoredSecurityAccount(Exception e)
	{
		AbstractStoredResource storedSecurityAccount = StoredResourceHelper.findStoredResource(this, e);
		if (storedSecurityAccount == null)
		{
			log.error("Ошибка при получении информации по сберегательному сертификату номер " + getNumber(), e);
			return toSecurityAccountFromDb();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.SECURITY_ACC_TYPE_KEY, storedSecurityAccount.getEntityUpdateTime()))
		{
			return (SecurityAccount) storedSecurityAccount;
		}
		else
		{
			StoredSecurityAccount result = (StoredSecurityAccount) storedSecurityAccount;
			result.setIncomeAmt(null);
			result.setNominalAmount(null);
			return result;
		}
	}

	private SecurityAccount toSecurityAccountFromDb()
	{
		MockSecurityAccount mockSecurityAccount = new MockSecurityAccount();
		mockSecurityAccount.setId(getExternalId());
		return mockSecurityAccount;
	}

	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearSecurityAccountCache(toSecurityAccountFromDb());
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
		return ResourceType.SECURITY_ACCOUNT;
	}

	public Class getStoredResourceType()
	{
		return StoredSecurityAccount.class;
	}

	public String getPatternForFavouriteLink()
	{
		return  "$$securityAccountLink:" + this.getId();
	}
}
