package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.MockAccount;
import com.rssl.phizic.business.accounts.MockCode;
import com.rssl.phizic.business.accounts.MockOffice;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 13:58:23 */
public class AccountLink extends ErmbProductLink implements PaymentAbilityERL<Account>
{
	public static final String CODE_PREFIX = "account";
	private static final AccountTargetService targetService = new AccountTargetService();

	private Boolean paymentAbility;
	private String number;
	private String description;
	private Currency currency;

	private String officeTB;
	private String officeOSB;
	private String officeVSP;

	private Boolean closedState;

	public Boolean getPaymentAbility()
	{
	    return paymentAbility != null && paymentAbility;
	}

	public void setPaymentAbility(Boolean paymentAbility)
	{
	    this.paymentAbility = paymentAbility;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	/**
	 * @return ����� �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number - ����� �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	public Account getValue()
    {
        return getAccount();
    }

	/**
	 *
	 * @return ������
	 */
	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public String getOfficeTB()
	{
		return officeTB;
	}

	public void setOfficeTB(String officeTB)
	{
		this.officeTB = officeTB;
	}

	public String getOfficeOSB()
	{
		return officeOSB;
	}

	public void setOfficeOSB(String officeOSB)
	{
		this.officeOSB = officeOSB;
	}

	public String getOfficeVSP()
	{
		return officeVSP;
	}

	public void setOfficeVSP(String officeVSP)
	{
		this.officeVSP = officeVSP;
	}

	/**
	 * @return ������� ������������� ������ ��������� � �������� ������
	 */
	public Boolean getClosedState()
	{
		return closedState;
	}

	/**
	 * ������ ������� ������������� ������ ��������� � �������� ������
	 * @param closedState - �������
	 */
	public void setClosedState(Boolean closedState)
	{
		this.closedState = closedState;
	}

	public Account getAccount()
    {
        try
        {
	        Account account = GroupResultHelper.getOneResult(getBankrollService().getAccount(getExternalId()));
	        if (account == null)
	        {
		        log.error("������ ��� ��������� ����� �" +getNumber());
		        return createMockAccount();
	        }
	        return account;
        }
        catch (InactiveExternalSystemException e)
        {
	        return getStoredAccount(e);
        }
        catch (OfflineExternalSystemException e)
        {
	        return getStoredAccount(e);
        }
	    catch (Exception e)
	    {
		    log.error("������ ��� ��������� ����� �" +getNumber(), e);
	        return createMockAccount();
	    }
	}

	private Account getStoredAccount(Exception e)
	{
		AbstractStoredResource storedResource = StoredResourceHelper.findStoredResource(this, e);
		if (storedResource == null)
		{
			log.error("������ ��� ��������� ����� �" + getNumber(), e);
			return createMockAccount();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.ACCOUNT_TYPE_KEY, storedResource.getEntityUpdateTime()))
		{
			return (Account) storedResource;
		}
		else
		{
			// ���� ������ �������� ������������, ���������� ������
			StoredAccount result = (StoredAccount) storedResource;
			result.setBalance(null);
			result.setMaxSumWrite(null);
			result.setMinimumBalance(null);
			return result;
		}
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	private Account createMockAccount()
	{
		MockAccount accountInt = new MockAccount();
	    accountInt.setId(getExternalId());
	    accountInt.setNumber(getNumber());
	    return accountInt;
	}

	public Office getOffice() throws BusinessException
	{
		Account account = getAccount();
		if (MockHelper.isMockObject(account))
			return new MockOffice();

		return account.getOffice();
	}

	public Money getMaxSumWrite()
	{
		Account accountInt = getAccount();
		if (MockHelper.isMockObject(accountInt))
			return null;
		return accountInt.getMaxSumWrite();
	}

	public Account toLinkedObjectInDBView()
	{
		MockAccount account = new MockAccount();
		account.setId(getExternalId());
		account.setNumber(getNumber());
		MockOffice office = new MockOffice();
		office.setCode(new MockCode(getOfficeTB(), getOfficeOSB(), getOfficeVSP()));
		account.setOffice(office);

		return account;
	}

	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearAccountCache(toLinkedObjectInDBView());
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

	public String toString()
    {
	    return "���� �" + getNumber();
    }

	/**
	 * ���������� �����, �������, ��� �����, ���� ������ ������ �����
 	 * @param o
	 * @return
	 */
	public int compareTo(Object o)
	{
		AccountLink link = (AccountLink)o;

		return this.getNumber().compareTo(link.getNumber());
	}

	public Client getAccountClient() throws BusinessException, BusinessLogicException
	{
		Account account = getAccount();
		if (MockHelper.isMockObject(account))
		{
			log.error("������ ��� ��������� ���������� � ��������� ����� �"+getNumber());
			return null;
		}

		return account.getAccountClient();
	}

	private static BankrollService getBankrollService()
	{
	    return GateSingleton.getFactory().service(BankrollService.class);
	}
	public Money getRest()
	{
		return getAccount().getBalance();
	}

	public ResourceType getResourceType()
	{
		return ResourceType.ACCOUNT;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$accountName:" + this.getId();
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredAccount.class;
	}

	/**
	 * ���������� true, ���� ��� ����� � false, ���� ����
	 * @return
	 */
	public boolean isDeposit()
	{
		String subString = number.substring(0, 3);
		if(subString.equals("423") || subString.equals("426"))
			return true;
		return false;
	}

	/**
	 * @return ����, � ������� �������� �����
	 */
	public AccountTarget getTarget()
	{
		try
		{
			return targetService.findTargetByAccountId(getId());
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ����, ����������� � ����� �" + getNumber(), e);
			return null;
		}
	}
}
