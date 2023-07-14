package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.RURAccountFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author osminin
 * @ created 16.12.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountsDepositsListSource implements EntityListSource
{
	private static final String FILTER_CLASS_NAME_PARAMETER = "filter";
	private static DepositService depositService;

    private AccountFilter accountFilter;

    public AccountsDepositsListSource()
    {
        this( new RURAccountFilter() );
    }

    public AccountsDepositsListSource(AccountFilter accountFilter)
    {
        this.accountFilter = accountFilter;
    }

    public AccountsDepositsListSource(Map parameters) throws BusinessException
    {
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

	public Source getSource(final Map<String, String> params) throws BusinessException, BusinessLogicException
	{EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = getAccountsList();

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		if (depositService == null)
						depositService = GateSingleton.getFactory().service(DepositService.class);

		for(AccountLink accountLink: accountLinks)
		{
			Account account = accountLink.getAccount();
			if(!MockHelper.isMockObject(account))
			{
				String key = account.getNumber();

				builder.openEntityTag(key);
				builder.appentField("currencyCode", account.getCurrency().getCode());
				builder.appentField("type", account.getDescription());
				String ammountStr = String.valueOf(account.getBalance().getDecimal());
				builder.appentField("amountDecimal",  ammountStr);
				builder.appentField("contractNumber", account.getAgreementNumber());
				builder.appentField("isOpen", String.valueOf(account.getAccountState() == AccountState.OPENED));
				try
				{
					Deposit deposit = depositService.getDepositById(account.getId());
					DepositInfo depositInfo = depositService.getDepositInfo(deposit);
					Calendar finishingDate = deposit.getEndDate();
					if (finishingDate!=null){
						builder.appentField("depositFinishingDate",DateHelper.toXMLDateFormat(finishingDate.getTime()));
					}
					builder.appentField("depositType",deposit.getDescription());
					builder.appentField("depositContractNumber", depositInfo.getAgreementNumber());
					builder.appentField("depositId", deposit.getId());
					Calendar openingDate = deposit.getOpenDate();
					if (openingDate != null){
						builder.appentField("depositOpeningDate", DateHelper.toXMLDateFormat(openingDate.getTime()));
					}
				}
				catch (GateException e)
				{
					throw new BusinessException(e);
				}
				catch (GateLogicException e)
				{
					throw new BusinessLogicException(e);
				}

				Calendar openingDate = account.getOpenDate();
				if (openingDate != null)
					builder.appentField("openingDate", DateHelper.toXMLDateFormat(openingDate.getTime()));

				builder.closeEntityTag();
			}

		}

		builder.closeEntityListTag();
		return builder;
	}

	private List<AccountLink> getAccountsList() throws BusinessException, BusinessLogicException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		return (provider == null) ? new ArrayList<AccountLink>() : provider.getPersonData().getAccounts(accountFilter);
    }
}
