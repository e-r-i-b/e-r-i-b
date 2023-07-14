package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bankroll.ReverseTransactionAbstract;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AbstractBase;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.MockHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:18:04 */
public class GetAccountAbstractOperation extends OperationBase<AccountFilter>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    protected static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

	protected AccountLink accountLink;
	protected List<AccountLink> accountLinks;

    private Calendar  dateFrom;
    private Calendar  dateTo;
	private boolean withCardUseInfo;

	protected boolean isBackError;
	protected boolean isUseStoredResource;
	protected boolean isError;
	
	//Текстовое сообщение об ошибке для отображения пользователю при получении выписки по счету/вкладу
	protected final Map<AccountLink, String> accountAbstractMsgErrorMap = new HashMap<AccountLink, String>();

	public boolean isBackError()
	{
		return isBackError;
	}

	public void initialize(Long accountLinkId) throws BusinessException, BusinessLogicException
    {
	    PersonDataProvider provider = PersonContext.getPersonDataProvider();

	    AccountLink tmpAccountLink = provider.getPersonData().getAccount(accountLinkId);
	    AccountFilter restriction = getRestriction();
	    Account account = tmpAccountLink.getAccount();

	    isUseStoredResource = (account instanceof AbstractStoredResource);
	    
	    accountLinks = new ArrayList<AccountLink>();
	    if(MockHelper.isMockObject(account))
	    {
		    //не можем проверить доступ, поэтому выписку давать не будем
	        isBackError = true;
	    }
		if( !restriction.accept(tmpAccountLink) )
			log.error("Ошибка доступа. Счет "+account.getNumber()+" исключен из списка", new RestrictionViolationException(" Счет: " + account.getNumber()));
		else
		{
			accountLink = tmpAccountLink;
			accountLinks.add(accountLink);
		}
   }

	public void initialize(List<AccountLink> accountLinks) throws BusinessException
    {
		this.isUseStoredResource = false;
	    this.accountLinks = new ArrayList<AccountLink>();

	    for (AccountLink accountLink : accountLinks)
	    {
		    Account account = accountLink.getAccount();
		    if(MockHelper.isMockObject(account))
		    {
		        isBackError = true;
		    }
			if( !getRestriction().accept(accountLink) )
			{
				log.error("Ошибка доступа. Счет "+account.getNumber()+" исключен из списка", new RestrictionViolationException(" Счет: " + account.getNumber()));
			}
			else
			{
				this.accountLinks.add(accountLink);
			}

		    isUseStoredResource = !isUseStoredResource ? (account instanceof AbstractStoredResource) : isUseStoredResource;
	    }
    }

    public Calendar getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(Calendar dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public Calendar getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(Calendar dateTo)
    {
        this.dateTo = dateTo;
    }

	public boolean isWithCardUseInfo()
	{
		return withCardUseInfo;
	}

	public void setWithCardUseInfo(boolean withCardUseInfo)
	{
		this.withCardUseInfo = withCardUseInfo;
	}

	public AccountLink getAccount()
    {
        return accountLink;
    }

    public AccountAbstract getAccountAbstract() throws BusinessLogicException
    {
        try
        {
	        if (accountLink == null)
	        {
		        isBackError=true;
		        return null;
	        }

		    Account account = accountLink.getAccount();
		    if(!MockHelper.isMockObject(account))
		    {
			    return (AccountAbstract)bankrollService.getAbstract(account, dateFrom, dateTo, withCardUseInfo);
		    }
		    else
		    {
			    isBackError=true;
			    return null;
		    }
        }
        catch (GateException ge)
        {
	        isBackError=true;
            log.error("Невозможно получить информацию по счету "+accountLink.getNumber(), ge);
	        return null;
        }
	    catch (GateLogicException e)
		{
			isBackError=true;
			log.error("Невозможно получить информацию по счету "+accountLink.getNumber(), e);
			return null;
		}
	    catch(InactiveExternalSystemException es)
		{
			log.error(es.getMessage(),es);
			return null;
		}
    }

	public Map<AccountLink, AccountAbstract> getAccountAbstract(Long count)
    {
	    Account[] accounts = getAccounts();

	    if(ArrayUtils.isEmpty(accounts))
		    return Collections.emptyMap();

	    if (isUseStoredResource())
	    {
		    isError = true;
		    for (AccountLink link : accountLinks)
		    {
			    accountAbstractMsgErrorMap.put(link, StoredResourceMessages.getUnreachableStatement());
		    }
		    return Collections.emptyMap();
	    }

	    GroupResult<Object, AbstractBase> baseAbsract = bankrollService.getAbstract(count, accounts);

	    Map<Object, AbstractBase> results = baseAbsract.getResults();
        Map<Object, IKFLException> exceptions = baseAbsract.getExceptions();

	    Map<AccountLink, AccountAbstract> accountAbstract = new HashMap<AccountLink, AccountAbstract>();
	    AccountLink currentAccountLink;
	    for (Map.Entry<Object, AbstractBase> resultEntry : results.entrySet())
	    {
		    Account account = (Account) resultEntry.getKey();
		    currentAccountLink = BankrollServiceHelper.findAccountLinkByNumber(account.getNumber(), accountLinks);
		    if (currentAccountLink == null)
		    {
			    log.error("Получена выписка не по тому счету. Счет №" + account.getNumber());
			    continue;
		    }

		    AccountAbstract resultAccountAbstract = (AccountAbstract) resultEntry.getValue();
		    if (resultAccountAbstract != null)
				resultAccountAbstract = new ReverseTransactionAbstract(resultAccountAbstract);

		    accountAbstract.put(currentAccountLink, resultAccountAbstract);
	    }
	    for (Map.Entry<Object, IKFLException> exceptionEntry : exceptions.entrySet())
	    {
		    Account account = (Account) exceptionEntry.getKey();
		    IKFLException ikflException = exceptionEntry.getValue();

		    currentAccountLink = BankrollServiceHelper.findAccountLinkByNumber(account.getNumber(), accountLinks);
		    if (currentAccountLink != null)
		    {
			    accountAbstract.put(currentAccountLink, null);
			    if (ikflException instanceof GateLogicException)
			        accountAbstractMsgErrorMap.put(currentAccountLink,ikflException.getMessage());
			    else
				    accountAbstractMsgErrorMap.put(currentAccountLink, "Информация по данному вкладу временно недоступна.");
		    }
		    log.error("Произошла ошибка при получении выписки по счету №" + account.getNumber(), ikflException);
	    }
	    //Общая ошибка только если по всем счетам пришла ошибка
	    if (MapUtils.isEmpty(results) && !MapUtils.isEmpty(exceptions))
		    isBackError = true;

        return accountAbstract;
    }

	private Account[] getAccounts()
	{
		List<Account> accounts = new ArrayList<Account>();
		if (accountLinks == null || accountLinks.isEmpty())
		{
			return accounts.toArray(new Account[accounts.size()]);
		}

		for(AccountLink link : accountLinks)
		{
			accounts.add(link.getAccount());
		}
		return accounts.toArray(new Account[accounts.size()]);
	}

	public Map<AccountLink, String> getAccountAbstractMsgErrorMap()
	{
		return Collections.unmodifiableMap(accountAbstractMsgErrorMap);
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	/**
	 *
	 * @return флаг наличия ошибок
	 */
	public boolean isError()
	{
		return isError;
	}
}
