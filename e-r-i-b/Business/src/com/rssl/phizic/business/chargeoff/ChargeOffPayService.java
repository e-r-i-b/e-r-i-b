package com.rssl.phizic.business.chargeoff;

import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.ChargeOffPaymentImpl;
import com.rssl.phizic.business.documents.payments.ClientBusinessDocumentOwner;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.UserBlocksValidator;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Egorova
 * @ created 14.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffPayService
{
	private static PersonService personService = new PersonService();
	private static DepartmentService departmentService = new DepartmentService();
	private static MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static ChargeOffLogService chargeOffLogService = new ChargeOffLogService();
	private static SecurityService securityService = new SecurityService();

	private static String CONNECTION_GROUND_HEADER = "Плата за подключение к обслуживанию";
	private static String CONNECTION_EMPOWERD_GROUND_HEADER = "Плата за подключение представителя к обслуживанию";
	private static String MONTHLY_GROUND_HEADER = "Абонентская плата за обслуживание";

	private static String BLOCKING_REASON_DESCRIPTION = "Нехватка средств для списания абонентской платы";

	/**
	 * плата за подключение к обслуживанию. Если списание удачно, формируем первый ежемесячный платеж.
	 * @param login - логин подключаемого
	 * @throws BusinessException, BusinessLogicException 
	 */
	public void payConnectionCharge(CommonLogin login) throws BusinessException, BusinessLogicException
	{
		//если списывается плата средствами шлюза не списываем здесь
		if (gateChargeOff(login))
			return;

		ActivePerson activePerson = personService.findByLogin((Login) login);
		CommonLogin personLogin = login;
		boolean isEmpoweredPerson = false;
		// если для списания платы передан представитель - будем списывать плату с клиента, а не с
		// представителя
		if (activePerson.getTrustingPersonId() != null)
		{
			Person parent = personService.findById(activePerson.getTrustingPersonId());
			activePerson =  personService.findByLogin(parent.getLogin());
			personLogin = activePerson.getLogin();
			isEmpoweredPerson = true;
		}
		Department personDepartment = departmentService.findById(activePerson.getDepartmentId());
		List<AccountLink> allAccountLinks = externalResourceService.getLinks(personLogin, AccountLink.class,null);
		List<AccountLink> accountLinks = filterPaymentAbilityLinks(allAccountLinks);

		Money amount = getAmount(personDepartment.getConnectionCharge());
		//если плата за подключение нулевая - не формируем платеж
		if (amount.getDecimal().unscaledValue().equals(BigDecimal.ZERO.unscaledValue())) return;
		
		ChargeOffLog chargeOff = chargeOffLogService.prepareLog(amount, personLogin, ChargeOffType.connection, activePerson.getAgreementDate());

		//Формируем основание платежа в зависимости от того, кого подключаем (клиент, представитель)
		String ground = isEmpoweredPerson ? createPaymentGround(CONNECTION_EMPOWERD_GROUND_HEADER,activePerson,chargeOff):createPaymentGround(CONNECTION_GROUND_HEADER,activePerson,chargeOff);
		try
		{
			if (!pay(login, accountLinks, amount, chargeOff, ground))
			throw new BusinessLogicException("Не удалось списать плату за подключение.");
		}
		catch(GateMessagingClientException e)
		{
			throw new BusinessLogicException(e.getMessage());
		}
		catch(GateMessagingException e)
		{
			throw new BusinessLogicException("Не удалось списать плату за подключение. "+e.getMessage());
		}
		//формирование следующего платежа
		Calendar periodFrom = chargeOff.getType().equals(ChargeOffType.connection) ? activePerson.getAgreementDate() : chargeOff.getPeriodUntil();
		chargeOffLogService.createAndWrite(getAmount(personDepartment.getConnectionCharge()), personLogin, ChargeOffType.monthly, periodFrom);
	}

	/**
	 * Взимание ежемесячной платы за обслуживание.
	 * Если платеж новый, т.е. его тип prepared формируем следующий платеж, который будем погашать через месяц.
	 * @param chargeOff - подготовленный или просроченный платеж
	 */
	public void payMonthlyCharge(ChargeOffLog chargeOff) throws BusinessException, BusinessLogicException
	{
		//если списывается плата средствами шлюза не списываем здесь
		if (gateChargeOff(chargeOff.getLogin()))
			return;

		CommonLogin login = chargeOff.getLogin();
		ActivePerson activePerson = personService.findByLogin((Login)login);
		Department personDepartment = departmentService.findById(activePerson.getDepartmentId());
		List<AccountLink> accountLinks = filterPaymentAbilityLinks(externalResourceService.getLinks(login, AccountLink.class,null));

		try
		{
			Money amount = getAmount(personDepartment.getMonthlyCharge());

			//Если тип prepared, то формируем следующий платеж
			if (chargeOff.getState().equals(ChargeOffState.prepared))
				chargeOffLogService.createAndWrite(amount, chargeOff.getLogin(), ChargeOffType.monthly, chargeOff.getPeriodUntil());

			//Если тип платежа долг, то берем старую сумму, ту которую клиент должен
			if (chargeOff.getState().equals(ChargeOffState.dept))
				amount = chargeOff.getAmount();


			try
			{
				if (!pay(login, accountLinks, amount, chargeOff, createPaymentGround(MONTHLY_GROUND_HEADER,activePerson,chargeOff)))
				{
					cannotPay(login, chargeOff, amount);
					return;
				}
			}
			catch(GateMessagingClientException e)
			{
				cannotPay(login, chargeOff, amount);
				return;
			}
			catch(GateMessagingException e)
			{
				cannotPay(login, chargeOff, amount);
				return;
			}

			UserBlocksValidator blocksValidator = new UserBlocksValidator();
			//если нет долгов - всё оплатил, но заблокирован системно, то разблокируем такого клиента
			if (chargeOffLogService.getPersonsDebts(login).isEmpty() && !blocksValidator.checkIfBlockOfThisTypeDoesntExist(login, BlockingReasonType.system))
			{
				GregorianCalendar lockedUntil = new GregorianCalendar();
				securityService.unlock(login, true, null, lockedUntil.getTime());
			}
		}
		catch(BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}		

	}

	private void cannotPay(CommonLogin login, ChargeOffLog chargeOff, Money amount) throws BusinessException
	{
		//блокируем клиента системной блокировкой
		lock(login);

		//апдейтим запись о том, что платеж не состоялся
		chargeOff.setAmount(amount);
		chargeOff.setState(ChargeOffState.dept);

		//Количество попыток неудачных оплат, если не было, значит 0
		Long attempts = (chargeOff.getAttempt()==null)?0:chargeOff.getAttempt();
		chargeOff.setAttempt(attempts.longValue()+1);

		chargeOffLogService.write(chargeOff);
	}


	private void lock(CommonLogin login) throws BusinessException
	{
		UserBlocksValidator blocksValidator = new UserBlocksValidator();
		if (!blocksValidator.checkIfBlockOfThisTypeDoesntExist(login, BlockingReasonType.system))
			return;
		try
		{
		Calendar calendar = new GregorianCalendar();
		securityService.lock(login, calendar.getTime(), null, BlockingReasonType.system, BLOCKING_REASON_DESCRIPTION, null, null);
		}
		catch(SecurityDbException e)
		{
			throw new BusinessException("Не удалось заблокировать клиента. "+e);
		}
	}

	/*
	Фильтрация счетов, с которых можно взимать плату
	 */
	private List<AccountLink> filterPaymentAbilityLinks(List<AccountLink> links) throws BusinessException
	{
		List<AccountLink> accountLinks = new ArrayList<AccountLink>();
		for (AccountLink link: links)
		{
			if (link.getPaymentAbility())
				accountLinks.add(link);
		}
		if (accountLinks.isEmpty())
			throw new BusinessException("Не найдено ни одного счета для списания платы");
		return accountLinks;
	}

	/**
	 * Непосредственная оплата подключения или ползьвания системой
	 * @param links - счета для списания платы
	 * @return true - оплата произведена, false - оплата не произведена
	 * @throws BusinessException
	 */
	private boolean pay(CommonLogin login, List<AccountLink> links, Money amount, ChargeOffLog chargeOffLog, String ground) throws BusinessException, GateMessagingClientException, GateMessagingException
	{
		int i = 0;
		for (AccountLink link : links)
		{
			i++;
			if (!validateAccountAndAmount(link, amount))
			{
				if (links.size()==i)
					throw new GateMessagingClientException("Не удалось списать плату за подключение. Не достаточно средств на счете.");
				continue;
			}
			try
			{
				sendPayment(login, link.getNumber(), amount, ground);
			}
			catch (GateMessagingClientException e)
			{
				if (links.size()==i)
					throw new GateMessagingClientException("Не удалось списать плату за подключение."+e.getMessage());
				continue;
			}
			catch (GateMessagingException e)
			{
				if (links.size()==i)
					throw new GateMessagingException("Не удалось списать плату за подключение."+e.getMessage());
				continue;
			}

			//записываем в журнал удавшийся платеж
			chargeOffLog.setAccount(link.getNumber());
			chargeOffLog.setAmount(amount);
			chargeOffLog.setState(ChargeOffState.payed);
			chargeOffLogService.write(chargeOffLog);

			return true;
		}
		return false;
	}

	/*
	Формирование назначения платежа
	 */
	private String createPaymentGround(String header, ActivePerson person, ChargeOffLog chargeOff)
	{
		String ground = header + " по системе Сбербанк Онлайн";
		if (chargeOff.getType().equals(ChargeOffType.monthly))
			ground+=" за период с "+String.format("%1$te.%1$tm.%1$tY",chargeOff.getPeriodFrom())+" по "+String.format("%1$te.%1$tm.%1$tY",chargeOff.getPeriodUntil());
		ground+=" по Договору №"+person.getAgreementNumber()+" от "+String.format("%1$te.%1$tm.%1$tY",person.getAgreementDate());
		return ground;
	}

	/*
	Преобразуем сумму списания к типу Money. Валюту берем национальную.
	 */
	private Money getAmount(BigDecimal amountDecimal) throws BusinessLogicException, BusinessException
	{
		if (amountDecimal==null)
			throw new BusinessLogicException("Не задана сумма списания.");
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			return new 	Money(amountDecimal, currencyService.getNationalCurrency());
		}
		catch(GateException e)
		{
			throw  new BusinessException(e);
		}
	}

	/*
	Проверка счета списания, на совпадение с валютой суммы списания, а также на наличие средст на счете
	 */
	private boolean validateAccountAndAmount(AccountLink accountLink, Money amount)  throws BusinessException
	{
		Account account = accountLink.getAccount();

		if(MockHelper.isMockObject(account))
			return false;
		
		if (!amount.getCurrency().compare(account.getCurrency()))
			throw new BusinessException("Валюта счета списания и валюта суммы списания должны совпадать.");
		return account.getBalance().compareTo(amount)>=0;
	}

	/**
	 * Непосредственная отправка платежа
	 * @param account - счет списания
	 * @param amount - сумма списания
	 * @throws BusinessException
	 */
	private void sendPayment(CommonLogin login, String account, Money amount, String ground) throws BusinessException, GateMessagingClientException, GateMessagingException
	{
		ActivePerson activePerson = personService.findByLogin((Login)login);
		Department personDepartment = departmentService.findById(activePerson.getDepartmentId());

		ChargeOffPaymentImpl payment = new ChargeOffPaymentImpl();

		payment.setChargeOffAccount(account);
		payment.setChargeOffAmount(amount);
		payment.setGround(ground);
		payment.setDateCreated(Calendar.getInstance());
		payment.setAdmissionDate(payment.getClientCreationDate());
		payment.setDepartment(personDepartment);
		payment.setOwner(new ClientBusinessDocumentOwner(activePerson));
		String clientId = activePerson.getClientId();
		if (activePerson.getTrustingPersonId() != null)
		{
			Person temp = personService.findById(activePerson.getTrustingPersonId());
			clientId = temp.getClientId();
		}
		payment.setExternalOwnerId(clientId);

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		try
		{
			documentService.send(payment.asGateDocument());
		}
		catch(GateMessagingClientException gmce)
		{
			String message = (gmce.getErrorMessage()!=null && !StringHelper.isEmpty(gmce.getErrorMessage().getMessage()))?gmce.getErrorMessage().getMessage():gmce.getMessage();
			throw new GateMessagingClientException(message);
		}
		catch(GateMessagingValidationException e)
		{
			throw new GateMessagingException(e.getMessage(), e.getErrorMessage().getRegExp(), e.getErrorMessage().getMessage());
		}
		catch(GateMessagingException gme)
		{
			throw gme;
		}
		catch (GateLogicException e){
			throw new GateMessagingClientException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/*
	Считываем наcтройку о возможности списать плату средствами шлюза
	 */
	private Boolean gateChargeOff(CommonLogin login) throws BusinessLogicException, BusinessException
	{
		try
		{
			PersonService personService = new PersonService();
			ActivePerson person = personService.findByLoginId(login.getId());
			GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
			Department department = departmentService.findById(person.getDepartmentId());
			return gateInfoService.isNeedChargeOff(department);
		}
		catch(GateException ex)
		{
			throw new BusinessException(ex);
		}
		catch(GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

}
