package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.mock.MockLoan;
import com.rssl.phizic.business.resources.external.LoanFilter;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.NullLoanFilter;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanListSource extends CachedEntityListSourceBase
{
	private LoanFilter loanFilter;

	public LoanListSource(EntityListDefinition definition)
	{
		this(definition, new NullLoanFilter() );
	}

    public LoanListSource(EntityListDefinition definition, LoanFilter loanFilter)
    {
	    super(definition);
        this.loanFilter = loanFilter;
    }

    public LoanListSource(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	    super(definition);
        try
        {
            String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
            Class  filterClass     = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

            loanFilter = (LoanFilter) filterClass.newInstance();
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

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		if (provider == null){
			builder.closeEntityListTag();
			return convertToReturnValue(builder.toString());
		}

		List<LoanLink> loanLinks = provider.getPersonData().getLoans(loanFilter);

	    for (LoanLink loanLink : loanLinks)
	    {
	        Loan loan = loanLink.getLoan();
		    if(loan!=null && !(loan instanceof MockLoan))
		    {
				try
				{
					String key = loan.getAccountNumber();
					EntityBuilder entityBuilder = new EntityBuilder();
					entityBuilder.openEntityTag(key);
					entityBuilder.appentField("type", loan.getDescription());
					entityBuilder.appentField("name", loanLink.getName());
					entityBuilder.appentField("id", loan.getId());
					entityBuilder.appentField("loanLinkId", loanLink.getId().toString());
					entityBuilder.appentField("amountDecimal", String.valueOf(loan.getLoanAmount().getDecimal()));
					entityBuilder.appentField("balanceAmount", String.valueOf(loan.getBalanceAmount().getDecimal()));
					entityBuilder.appentField("currencyCode", loan.getLoanAmount().getCurrency().getCode());
					entityBuilder.appentField("nextPayAmount", loan.getNextPaymentAmount().getDecimal().toString());
					entityBuilder.appentField("nextPayCurrencyCode", loan.getNextPaymentAmount().getCurrency().getCode());
					entityBuilder.appentField("state", loan.getState().toString());

					Calendar nextPaymentDate = loan.getNextPaymentDate();
					if (nextPaymentDate != null)
					{
						entityBuilder.appentField("nextPayDate", DateHelper.getXmlDateTimeRussianFormat(nextPaymentDate.getTime()));
					}

					entityBuilder.appentField("code", loanLink.getCode());
					//информация о следующем платеже

					LoansService service = GateSingleton.getFactory().service(LoansService.class);
					ScheduleItem item = service.getNextScheduleItem(loan);
					entityBuilder.appentField("principalAmount", String.valueOf(item.getPrincipalAmount().getDecimal()));
					entityBuilder.appentField("interestsAmount", String.valueOf(item.getInterestsAmount().getDecimal()));
					entityBuilder.appentField("totalPaymentAmount", String.valueOf(item.getTotalPaymentAmount().getDecimal()));
					Map<PenaltyDateDebtItemType, DateDebtItem> debtsMap = item.getPenaltyDateDebtItemMap();

					for (PenaltyDateDebtItemType debtType : debtsMap.keySet())
					{
						entityBuilder.appentField(debtType.toString(), String.valueOf(debtsMap.get(debtType).getAmount().getDecimal()));
					}
					entityBuilder.closeEntityTag();
					builder.addEntity(entityBuilder);
				}
				catch (InactiveExternalSystemException e)
				{
					// технологический перерыв - исключение бросаем дальше
					throw e;
				}
				catch (Exception e)
				{
					//глушим любое исключение, пропуская текущий продукт
					log.error("Ошибка при получении информации по кредиту, номер ссудного счета - " + loanLink.getNumber(),e);
				}
		    }
	    }

	    builder.closeEntityListTag();

	    return convertToReturnValue(builder.toString());
    }
}
