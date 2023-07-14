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

	private static String CONNECTION_GROUND_HEADER = "����� �� ����������� � ������������";
	private static String CONNECTION_EMPOWERD_GROUND_HEADER = "����� �� ����������� ������������� � ������������";
	private static String MONTHLY_GROUND_HEADER = "����������� ����� �� ������������";

	private static String BLOCKING_REASON_DESCRIPTION = "�������� ������� ��� �������� ����������� �����";

	/**
	 * ����� �� ����������� � ������������. ���� �������� ������, ��������� ������ ����������� ������.
	 * @param login - ����� �������������
	 * @throws BusinessException, BusinessLogicException 
	 */
	public void payConnectionCharge(CommonLogin login) throws BusinessException, BusinessLogicException
	{
		//���� ����������� ����� ���������� ����� �� ��������� �����
		if (gateChargeOff(login))
			return;

		ActivePerson activePerson = personService.findByLogin((Login) login);
		CommonLogin personLogin = login;
		boolean isEmpoweredPerson = false;
		// ���� ��� �������� ����� ������� ������������� - ����� ��������� ����� � �������, � �� �
		// �������������
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
		//���� ����� �� ����������� ������� - �� ��������� ������
		if (amount.getDecimal().unscaledValue().equals(BigDecimal.ZERO.unscaledValue())) return;
		
		ChargeOffLog chargeOff = chargeOffLogService.prepareLog(amount, personLogin, ChargeOffType.connection, activePerson.getAgreementDate());

		//��������� ��������� ������� � ����������� �� ����, ���� ���������� (������, �������������)
		String ground = isEmpoweredPerson ? createPaymentGround(CONNECTION_EMPOWERD_GROUND_HEADER,activePerson,chargeOff):createPaymentGround(CONNECTION_GROUND_HEADER,activePerson,chargeOff);
		try
		{
			if (!pay(login, accountLinks, amount, chargeOff, ground))
			throw new BusinessLogicException("�� ������� ������� ����� �� �����������.");
		}
		catch(GateMessagingClientException e)
		{
			throw new BusinessLogicException(e.getMessage());
		}
		catch(GateMessagingException e)
		{
			throw new BusinessLogicException("�� ������� ������� ����� �� �����������. "+e.getMessage());
		}
		//������������ ���������� �������
		Calendar periodFrom = chargeOff.getType().equals(ChargeOffType.connection) ? activePerson.getAgreementDate() : chargeOff.getPeriodUntil();
		chargeOffLogService.createAndWrite(getAmount(personDepartment.getConnectionCharge()), personLogin, ChargeOffType.monthly, periodFrom);
	}

	/**
	 * �������� ����������� ����� �� ������������.
	 * ���� ������ �����, �.�. ��� ��� prepared ��������� ��������� ������, ������� ����� �������� ����� �����.
	 * @param chargeOff - �������������� ��� ������������ ������
	 */
	public void payMonthlyCharge(ChargeOffLog chargeOff) throws BusinessException, BusinessLogicException
	{
		//���� ����������� ����� ���������� ����� �� ��������� �����
		if (gateChargeOff(chargeOff.getLogin()))
			return;

		CommonLogin login = chargeOff.getLogin();
		ActivePerson activePerson = personService.findByLogin((Login)login);
		Department personDepartment = departmentService.findById(activePerson.getDepartmentId());
		List<AccountLink> accountLinks = filterPaymentAbilityLinks(externalResourceService.getLinks(login, AccountLink.class,null));

		try
		{
			Money amount = getAmount(personDepartment.getMonthlyCharge());

			//���� ��� prepared, �� ��������� ��������� ������
			if (chargeOff.getState().equals(ChargeOffState.prepared))
				chargeOffLogService.createAndWrite(amount, chargeOff.getLogin(), ChargeOffType.monthly, chargeOff.getPeriodUntil());

			//���� ��� ������� ����, �� ����� ������ �����, �� ������� ������ ������
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
			//���� ��� ������ - �� �������, �� ������������ ��������, �� ������������ ������ �������
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
		//��������� ������� ��������� �����������
		lock(login);

		//�������� ������ � ���, ��� ������ �� ���������
		chargeOff.setAmount(amount);
		chargeOff.setState(ChargeOffState.dept);

		//���������� ������� ��������� �����, ���� �� ����, ������ 0
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
			throw new BusinessException("�� ������� ������������� �������. "+e);
		}
	}

	/*
	���������� ������, � ������� ����� ������� �����
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
			throw new BusinessException("�� ������� �� ������ ����� ��� �������� �����");
		return accountLinks;
	}

	/**
	 * ���������������� ������ ����������� ��� ���������� ��������
	 * @param links - ����� ��� �������� �����
	 * @return true - ������ �����������, false - ������ �� �����������
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
					throw new GateMessagingClientException("�� ������� ������� ����� �� �����������. �� ���������� ������� �� �����.");
				continue;
			}
			try
			{
				sendPayment(login, link.getNumber(), amount, ground);
			}
			catch (GateMessagingClientException e)
			{
				if (links.size()==i)
					throw new GateMessagingClientException("�� ������� ������� ����� �� �����������."+e.getMessage());
				continue;
			}
			catch (GateMessagingException e)
			{
				if (links.size()==i)
					throw new GateMessagingException("�� ������� ������� ����� �� �����������."+e.getMessage());
				continue;
			}

			//���������� � ������ ��������� ������
			chargeOffLog.setAccount(link.getNumber());
			chargeOffLog.setAmount(amount);
			chargeOffLog.setState(ChargeOffState.payed);
			chargeOffLogService.write(chargeOffLog);

			return true;
		}
		return false;
	}

	/*
	������������ ���������� �������
	 */
	private String createPaymentGround(String header, ActivePerson person, ChargeOffLog chargeOff)
	{
		String ground = header + " �� ������� �������� ������";
		if (chargeOff.getType().equals(ChargeOffType.monthly))
			ground+=" �� ������ � "+String.format("%1$te.%1$tm.%1$tY",chargeOff.getPeriodFrom())+" �� "+String.format("%1$te.%1$tm.%1$tY",chargeOff.getPeriodUntil());
		ground+=" �� �������� �"+person.getAgreementNumber()+" �� "+String.format("%1$te.%1$tm.%1$tY",person.getAgreementDate());
		return ground;
	}

	/*
	����������� ����� �������� � ���� Money. ������ ����� ������������.
	 */
	private Money getAmount(BigDecimal amountDecimal) throws BusinessLogicException, BusinessException
	{
		if (amountDecimal==null)
			throw new BusinessLogicException("�� ������ ����� ��������.");
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
	�������� ����� ��������, �� ���������� � ������� ����� ��������, � ����� �� ������� ������ �� �����
	 */
	private boolean validateAccountAndAmount(AccountLink accountLink, Money amount)  throws BusinessException
	{
		Account account = accountLink.getAccount();

		if(MockHelper.isMockObject(account))
			return false;
		
		if (!amount.getCurrency().compare(account.getCurrency()))
			throw new BusinessException("������ ����� �������� � ������ ����� �������� ������ ���������.");
		return account.getBalance().compareTo(amount)>=0;
	}

	/**
	 * ���������������� �������� �������
	 * @param account - ���� ��������
	 * @param amount - ����� ��������
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
	��������� ��c������ � ����������� ������� ����� ���������� �����
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
