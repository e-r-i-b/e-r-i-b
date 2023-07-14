package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.DepositAbstract;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Calendar;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:18:04 */
@Deprecated //Получение выписки по депозитам не реализовано в маршрутизации шлюзов
public class GetDepositAbstractOperation extends OperationBase
{
    private static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

	private DepositLink deposit;
    private Calendar dateFrom;
    private Calendar    dateTo;

    public void initialize(Long depositLinkId) throws BusinessException
    {
	    PersonDataProvider provider = PersonContext.getPersonDataProvider();
	    deposit = provider.getPersonData().getDeposit(depositLinkId);
    }

	public void initialize(String depositLinkId) throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		deposit = provider.getPersonData().getDepositByExternalId(depositLinkId);
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

    public DepositAbstract getDepositAbstract() throws BusinessLogicException, BusinessException
    {
		//Если потребуется использование метода для депозитов, необходимо реализовать на уровне шлюзов получение выписки по депозитам
		throw new UnsupportedOperationException("Получение выписки по депозитам не реализовано");
    }

    public DepositAbstract getDepositAbstract(Long count) throws BusinessLogicException, BusinessException
    {
        try
        {
	        return (DepositAbstract) GroupResultHelper.getOneResult(bankrollService.getAbstract(count, deposit.getDeposit()));
        }
        catch (SystemException e)
        {
	        throw new BusinessException(e);
        }
	    catch (LogicException e)
	    {
		    throw new BusinessLogicException(e);
	    }
    }

	public DepositLink getDeposit()
	{
		return deposit;
	}
}
