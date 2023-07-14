package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author Kidyaev
 * @ created 01.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class EditBankrollsOperation extends PersonOperationBase
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private List<AccountLink> accountLinks;

	private List<AccountLink> changedAccounts;

    public void setPersonId(Long value) throws BusinessException, BusinessLogicException
    {
        super.setPersonId(value);
        initialize();
    }

    private void initialize() throws BusinessException, BusinessLogicException
    {
		accountLinks = externalResourceService.getLinks(getPerson().getLogin(), AccountLink.class,getInstanceName());
		changedAccounts = new ArrayList<AccountLink>();
    }

    public void updateAccountPaymentAbility(AccountLink accountLink, boolean paymentAbylity)
    {
	    if(accountLinks.contains(accountLink))
	    {
		    accountLink.setPaymentAbility(paymentAbylity);
             changedAccounts.add(accountLink);
	    }
	    else
	    {
		    log.warn("Попытка работы с несуществующий счетом");
	    }
    }

    @Transactional
    public void update() throws BusinessException, BusinessLogicException
    {
	    validate();
        try
        {
            HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    updateAccounts(session);
                    return null;
                }
            });
        }
        catch(BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }

        initialize();
    }

	/**
	 * проверка, что остался по крайней мере один счет для оплаты.
	 * @throws BusinessLogicException нет счета для оплаты
	 */
	public void validate() throws BusinessLogicException
	{
		int accounts = 0;
		if(changedAccounts.size()==0)
			return;
		
		for (AccountLink changedAccount : changedAccounts)
		{
			if(changedAccount.getPaymentAbility())
				accounts++;
		}

		if(accounts!=0)
			return;//счет для оплаты есть

		boolean finded = false;

		for (AccountLink accountLink : accountLinks)
		{
			if(accountLink.getPaymentAbility())
			{
				for (AccountLink changedAccount : changedAccounts)
				{
					if(accountLink.compareTo(changedAccount)==0)
					{
						finded = true;
						break;
					}
				}
				if(!finded)
					accounts++;
				finded = false;
			}
		}

		if(accounts==0)
			throw new BusinessLogicException("Не найден счет для оплаты услуг");

	}

	/**
     * @return номер счета(возможность снятия наличных=true|false)
     */

    public String getChangedAccountsStr() throws BusinessException
    {
        List<AccountLink> list = changedAccounts;
        StringBuffer result = new StringBuffer();

        for (AccountLink link : list)
        {
            result.append(link.getNumber());
            result.append("(возможность снятия наличных=");
            result.append(link.getPaymentAbility());
            result.append(");");
        }

        return result.toString();
    }

    private void updateAccounts(Session session) throws BusinessException
    {
        for (AccountLink accountLink : changedAccounts)
        {
            session.update(accountLink);
        }
    }

	/**
	 * @return счета для снятия платы.
	 */
	public List<AccountLink> getChangedAccounts()
	{
		return Collections.unmodifiableList(changedAccounts);
	}

}
