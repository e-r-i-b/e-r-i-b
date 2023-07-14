package com.rssl.phizic.operations.loyaltyProgram;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loyaltyProgram.LoyaltyProgramHelper;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.config.loyalty.LoyaltyHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author lukina
 * @ created 15.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramInfoOperation  extends OperationBase implements ViewEntityOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final int MAX_ITEMS = 200;

	private LoyaltyProgramLink link;
	private boolean isBackError = false;
	private boolean overMaxItems = false;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		link = PersonContext.getPersonDataProvider().getPersonData().getLoyaltyProgram();
	}

	/**
	 * Получение полного списка операций по бонусному счету
	 * @param fromDate - начальная дата
	 * @param toDate - конечная дата
	 * @return список операций
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(Date fromDate, Date toDate) throws BusinessLogicException, BusinessException
	{
		try
		{
			LoyaltyProgramService loyaltyProgramService = GateSingleton.getFactory().service(LoyaltyProgramService.class);
			LoyaltyProgram program = loyaltyProgramService.getClientLoyaltyProgram(link.getExternalId());
			List<LoyaltyProgramOperation> operations =  loyaltyProgramService.getLoyaltyOperationInfo(program);
			List<LoyaltyProgramOperation> res = new ArrayList<LoyaltyProgramOperation>();
			int numOfItems = 0;
			for (LoyaltyProgramOperation op : operations)
			{
 				if (!op.getOperationDate().before(DateHelper.toCalendar(fromDate)) && op.getOperationDate().before(DateHelper.endOfDay(DateHelper.toCalendar(toDate))))
			    {
					if (numOfItems < MAX_ITEMS)
				        res.add(op);
				    numOfItems++;
				    if (numOfItems > MAX_ITEMS)
				    {
						overMaxItems = true;
					    break;
				    }
			    }
			}
			Collections.sort(res, new LoyaltyProgramOperationsComparator());
			return res;
		}
		catch (GateLogicException e)
		{
			isBackError = true;
			log.error("Ошибка при получении списка операций по бонусному счету", e);
			throw new BusinessLogicException(e);
		}
		catch (GateException ex)
		{			
			isBackError = true;
			log.error("Ошибка при получении списка операций по бонусному счету", ex);
			return null;
		}
	}

	public boolean isOverMaxItems()
	{
		return overMaxItems;
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public LoyaltyProgramLink getEntity()
	{
		return link;
	}

	/**
	 * @return пароль для контактного центра
	 */
	public String getCallCentrePassw()
	{
		String hash = LoyaltyHelper.generateHash(PersonContext.getPersonDataProvider().getPersonData().getLogin().getLastLogonCardNumber());
		if (StringHelper.isNotEmpty(hash))
			return hash.substring(0, 5);
		return hash;
	}

	public LoyaltyProgramLink getLoyaltyProgramLink() throws BusinessException, BusinessLogicException
	{
		try
		{
			LoyaltyProgramHelper.updateLoyaltyProgram();
		}
		catch (Exception ex){
			isBackError = true;
			log.error("Ошибка при получении информации по бонусному счету", ex);
		}

		return PersonContext.getPersonDataProvider().getPersonData().getLoyaltyProgram();
	}
}
