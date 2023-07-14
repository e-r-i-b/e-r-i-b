package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.AccountsHelper;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.RURAccountFilter;
import com.rssl.phizic.business.resources.external.comparator.CurrencyComparator;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: egorovaav $
 * @ $Revision:5365 $
 */

/**
 * !!!!!!!!!! Если надо что-то добавить из AccountInfo, то добавляйте в AccountListSourceWithInfo
 */
public class AccountListSource extends CachedEntityListSourceBase
{
	private static final DepartmentService departmentService = new DepartmentService();
    protected AccountFilter accountFilter;

    public AccountListSource(EntityListDefinition definition)
    {
        this(definition, new RURAccountFilter() );
    }

    public AccountListSource(EntityListDefinition definition, AccountFilter accountFilter)
    {
	    super(definition);
        this.accountFilter = accountFilter;
    }

    public AccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	    super(definition);
        try
        {
            String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
            Class  filterClass     = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

            accountFilter = (AccountFilter) filterClass.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new BusinessException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new BusinessException(e);
        }
        catch (InstantiationException e)
        {
            throw new BusinessException(e);
        }
    }

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();
		try
		{
			List<AccountLink> accountLinks = getAccountsList();
			Collections.sort(accountLinks, getAccountLinkComparator());

			AccountLink accountLinkToCompare = getAccountLinkToCompare(params);

			for(AccountLink accountLink: accountLinks)
			{
				Account account = accountLink.getAccount();
				if (!MockHelper.isMockObject(account) && skipStoredResource(account)
						&& AccountsHelper.isPermittedForFinancialTransactions(account)
						&& compareCurrencies(accountLink, accountLinkToCompare))
				{
					String key = account.getNumber();

					builder.openEntityTag(key);
					builder.appentField("currencyCode", account.getCurrency().getCode());
					builder.appentField("linkId", accountLink.getId().toString());
					builder.appentField("code", accountLink.getCode());
					builder.appentField("type", account.getDescription());
					builder.appentField("name", accountLink.getName());
					String ammountStr = account.getBalance() == null ? null : String.valueOf(account.getBalance().getDecimal());
					builder.appentField("amountDecimal", ammountStr);
					addInfo(builder, accountLink);
					builder.appentField("isOpen", String.valueOf(account.getAccountState() == AccountState.OPENED));

					Calendar openingDate = account.getOpenDate();
					if (openingDate != null)
						builder.appentField("openingDate", DateHelper.toXMLDateFormat(openingDate.getTime()));

					Office office = accountLink.getOffice();
					builder.appentField("officeName", office.getName());
					builder.appentField("officeAddress", office.getAddress());
					builder.appentField("officeRegionCode", office.getCode().getFields().get("region"));
					builder.appentField("officeBranchCode", office.getCode().getFields().get("branch"));
					String officeVSP = office.getCode().getFields().get("office");
					builder.appentField("officeOfficeCode", officeVSP);
					builder.appentField("isImaOpening", office.isOpenIMAOffice() ? "1" : "0");
					builder.appentField("officeSynchKey", office.getSynchKey().toString());
					builder.appentField("isUseStoredResource", String.valueOf(account instanceof AbstractStoredResource));
					builder.appentField("state", account.getAccountState().toString());
					builder.closeEntityTag();
				}
			}
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("Ошибка при добавлении счетов", ex);
		}
		
		builder.closeEntityListTag();
		return convertToReturnValue(builder.toString());
	}

	protected boolean skipStoredResource(Account account)
	{
		return !(account instanceof AbstractStoredResource);
	}

	/**
	 * Добавить информацию из других источников.
	 * @param builder
	 * @param accountLink
	 */
	protected void addInfo(EntityListBuilder builder, AccountLink accountLink)
	{

	}

    protected List<AccountLink> getAccountsList() throws BusinessException, BusinessLogicException
    {
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    return personData == null ? new ArrayList<AccountLink>() : personData.getAccounts(accountFilter);
    }

	protected Comparator getAccountLinkComparator()
	{
		return new CurrencyComparator();
	}

	protected AccountLink getAccountLinkToCompare(Map<String, String> params) throws BusinessException
	{
		return null;
	}

	protected boolean compareCurrencies(AccountLink accountLink, AccountLink accountLinkToCompare) throws BusinessException
	{
		return true;
	}
}
