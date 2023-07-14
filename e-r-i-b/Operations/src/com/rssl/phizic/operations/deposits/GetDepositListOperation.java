package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.*;
import java.math.BigDecimal;

/**
 * @author Evgrafov
 * @ created 23.05.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */
public class GetDepositListOperation extends OperationBase implements ListEntitiesOperation
{
	private static PersonService personService = new PersonService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	List<? extends Deposit> depositsList = null;

	private String state;
	private String depositType;
	private BigDecimal sumFrom;
	private BigDecimal sumTo;
	private String sumCurrency;
	private Date closeDateFrom;
	private Date closeDateTo;
	private Date openDateFrom;
	private Date openDateTo;
	private String duration;

	public static PersonService getPersonService()
	{
		return personService;
	}

	public static void setPersonService(PersonService personService)
	{
		GetDepositListOperation.personService = personService;
	}

	/**
	 * все возможные виды вкладов клиента.
	 * @return
	 */
	public Set<String> getDepositsKindList() throws BusinessException
	{
		Map<String,String> descriptions = new HashMap<String,String>();
		try
		{
			List<? extends Deposit> deposits = getClientsDeposits();
			for (Deposit deposit : deposits)
			{
				descriptions.put(deposit.getDescription(), "1");
			}
			return descriptions.keySet();
		}
		catch(GateException ex)
		{
			throw new BusinessException(ex);
		}
	}

	private List<? extends Deposit> getClientsDeposits() throws GateException,BusinessException
	{
		if(depositsList == null)
		{
			String clientId = getClientIdForDeposits();
			DepositService depositService = GateSingleton.getFactory().service(DepositService.class);
			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

			try
			{
				depositsList = depositService.getClientDeposits(clientService.getClientById(clientId));

			}
			catch (GateLogicException ex)
			{
				throw new GateException(ex);
			}
		}

		return depositsList;
	}

	private String getClientIdForDeposits() throws BusinessException
	{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

			ActivePerson person = personData.getPerson();
			String clientId = null;

			//для представителя получаем депозиты его доверителя.
			if(person.getTrustingPersonId()==null)
				clientId = personData.getPerson().getClientId();
			else
			{
				Person realPerson = personService.findById(person.getTrustingPersonId());
				clientId = realPerson.getClientId();
			}
		return clientId;
	}

	/**
	 *
	 * @return список
	 * @throws BusinessException
	 */
	public List<DepositLink> getList()
	{

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		List<DepositLink> depositLinks = null;
		try
		{
			depositLinks = personData.getDeposits();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения списка вкладов", e);
			return depositLinks;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка получения списка вкладов", e);
			return depositLinks;
		}
		List<DepositLink> retVal = new ArrayList<DepositLink>();

		for (DepositLink depositLink : depositLinks)
		{
			Deposit deposit = null;
			try
			{
				deposit = depositLink.getDeposit();
			}
			catch (BusinessException ex)
			{
				log.error("Ошибка при получении информации о вкладе:", ex);
				continue;
			}
			catch (BusinessLogicException ex)
			{
				log.error("Ошибка при получении информации о вкладе:", ex);
				continue;
			}
			/*todo временное решение. Т.к. подробная информацияя по вкладам клиента подтягивается только в момент входа пользователя в систему,
			а на главной странице подтягивается из кеша, в момент добавления вклада в Retail.
			Добавлена проверка, чтобы не добавлялся вклад в список на странице "счета и карты", если нет информации по вкладу.
			*/
			//todo это фильтр, т.к. вклады получаем напрямую из ритейл, и другой возможности отсортировать я не вижу
			if (depositType != null && depositType.length() != 0)
			{
				if (!depositType.equals(deposit.getDescription()))
					continue;
			}
			if (state != null && state.equals("O") && deposit.getState().equals(DepositState.closed))
			{
				continue;
			}
			if (state != null && state.equals("C") && deposit.getState().equals(DepositState.open))
			{
				continue;
			}
			if (openDateTo != null && openDateFrom != null)
			{
				if (deposit.getOpenDate() == null || !dateBetween(openDateFrom, openDateTo, deposit.getOpenDate().getTime()))
					continue;
			}
			if (closeDateTo != null && closeDateFrom != null)
			{
				if (deposit.getOpenDate() == null || !dateBetween(closeDateFrom, closeDateTo, deposit.getCloseDate().getTime()))
					continue;
			}
			if (sumFrom != null && sumTo != null)
			{
				Money ammount = deposit.getAmount();
				if (ammount == null)
					continue;
				if (ammount.getDecimal().compareTo(sumFrom) < 0 || ammount.getDecimal().compareTo(sumTo) > 0)
					continue;
			}
			if (sumCurrency != null && sumCurrency.length() > 0)
			{
				Money ammount = deposit.getAmount();
				if (ammount == null)
					continue;

				if (!ammount.getCurrency().getCode().equals(sumCurrency))
					continue;
			}
			if (duration != null && duration.length() != 0)
			{
				if (deposit.getDuration() == null)
					continue;

				long durationDays = deposit.getDuration();

				if (duration.equals("more") && durationDays < 1080)//свыше 3 лет
					continue;
				if (duration.equals("30") && durationDays > 30)//до 30 дней
					continue;
				if (duration.equals("90") && (durationDays > 90 || durationDays < 31))//от 31 до 90 дней
					continue;
				if (duration.equals("180") && (durationDays > 180 || durationDays < 91))//от 91 до 180 дней<
					continue;
				if (duration.equals("360") && (durationDays > 360 || durationDays < 181))//от 181 дней до 1 года
					continue;
				if (duration.equals("1080") && (durationDays > 1080 || durationDays < 361))//от 1 до 3 лет
					continue;
			}

			retVal.add(depositLink);
		}
		return retVal;
	}

	private boolean dateBetween(Date from, Date to, Date date)
	{
		return !((date.compareTo(from)<0) || (date.compareTo(to)>0));
	}

	public Date getCloseDateFrom()
	{
		return closeDateFrom;
	}

	public void setCloseDateFrom(Date closeDateFrom)
	{
		this.closeDateFrom = closeDateFrom;
	}

	public Date getCloseDateTo()
	{
		return closeDateTo;
	}

	public void setCloseDateTo(Date closeDateTo)
	{
		this.closeDateTo = closeDateTo;
	}

	public String getDepositType()
	{
		return depositType;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public Date getOpenDateFrom()
	{
		return openDateFrom;
	}

	public void setOpenDateFrom(Date openDateFrom)
	{
		this.openDateFrom = openDateFrom;
	}

	public Date getOpenDateTo()
	{
		return openDateTo;
	}

	public void setOpenDateTo(Date openDateTo)
	{
		this.openDateTo = openDateTo;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getSumCurrency()
	{
		return sumCurrency;
	}

	public void setSumCurrency(String sumCurrency)
	{
		this.sumCurrency = sumCurrency;
	}

	public BigDecimal getSumFrom()
	{
		return sumFrom;
	}

	public void setSumFrom(BigDecimal sumFrom)
	{
		this.sumFrom = sumFrom;
	}

	public BigDecimal getSumTo()
	{
		return sumTo;
	}

	public void setSumTo(BigDecimal sumTo)
	{
		this.sumTo = sumTo;
	}
}