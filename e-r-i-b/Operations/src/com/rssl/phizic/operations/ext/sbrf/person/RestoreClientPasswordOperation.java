package com.rssl.phizic.operations.ext.sbrf.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl;
import com.rssl.phizgate.common.documents.payments.PersonNameImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.operations.person.EditPersonOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author vagin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 * Операция востановления пароля
 */
public class RestoreClientPasswordOperation extends EditPersonOperation
{
	private static final ErmbProfileBusinessService ermbProfileBusinessService = new ErmbProfileBusinessService();

	private List<CardLink> cardLinks = new ArrayList<CardLink>();
	//Тип восстановления пароля(по логину/по карте)
	public enum RestoreType
	{
		BY_CARD,
		BY_LOGIN;

		public static RestoreType fromValue(String value)
		{
			if("card".equals(value))
				return BY_CARD;
			else if("login".equals(value))
				return BY_LOGIN;
			else
				throw new IllegalArgumentException("Некорректное значение перечислителя");
		}
	}

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		setPersonId(personId);
		cardLinks = getCardLinks();
	}

	/**
	 * Подключен ли к ЕРМБ
	 * @return true
	 * @throws BusinessException
	 */
	public boolean hasERMBProfile() throws BusinessException
	{
		ErmbProfileImpl ermbProfile = ermbProfileBusinessService.findByPersonId(getPersonId());
		return ermbProfile != null;
	}
	/**
	 * Восстановить пароль.
	 * @param login - логин
	 */
	public void restorePassword(String login, boolean ignoreIMSICheck) throws BusinessException, BusinessLogicException
	{
		//Отправляем запрос в ЦСА
		try
		{
			CSABackRequestHelper.sendGeneratePasswordRq(login, getEmployeeInfo(), ignoreIMSICheck);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessLogicException("Не удалось сгенерировать пароль для заданного клиента.", e);
		}
	}

	/**
	 * Восстановление пароля по номеру карточки подключенной к МБ
	 * @param cardLinkId - id выбранной карты
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void restorePasswordByCardNumber(String cardLinkId, boolean ignoreIMSICheck) throws BusinessException, BusinessLogicException
	{
		for(CardLink link: cardLinks)
		{
			if(link.getId().equals(Long.valueOf(cardLinkId)))
			{
				try
				{
					MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
					UserInfo userInfo = mobileBankService.getClientByCardNumber(link.getNumber());
					if (userInfo == null)
						throw new BusinessLogicException("По указаной карте невозможно получить информацию о пользователе.");
					restorePassword(userInfo.getUserId(), ignoreIMSICheck);
					return;
				}
				catch (GateException e)
				{
					throw new BusinessException(e);
				}
				catch (GateLogicException e)
				{
					throw new BusinessLogicException(e);
				}
			}
		}
		throw new BusinessLogicException("По выбранному идентификатору не найдена карта клиента.");
	}

	/**
	 * Сгенерировать пароль для клиента по данным из профиля.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void restorePasswordByProfile(boolean ignoreIMSICheck) throws BusinessException, BusinessLogicException, FailureIdentificationException
	{
		//Отправляем запрос в ЦСА
		try
		{
			CSABackRequestHelper.sendGeneratePassword2Rq(getClientInfo(), getEmployeeInfo(), ignoreIMSICheck);
		}
		catch (FailureIdentificationException e)
		{
			throw e;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackException ignore)
		{
			throw new BusinessLogicException("Не удалось сгенерировать пароль для заданного клиента.");
		}
	}

	/**
	 * Сгенерировать пароль для клиента по данным из профиля.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void checkIMSIByProfile() throws BusinessException, BusinessLogicException, CheckIMSIException
	{
		//Отправляем запрос в ЦСА
		try
		{
			CSABackRequestHelper.sendCheckIMSIRq(getPerson().getLogin().getUserId(), getEmployeeInfo());
		}
		catch (CheckIMSIException e)
		{
			throw e;
		}
		catch (MobileBankRegistrationNotFoundException e)
		{
			throw new BusinessLogicException(getMessageConfig().message("personsBundle", "mobilebank.registration.not.found.error.message"), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackException ignore)
		{
			throw new BusinessLogicException("Не удалось сгенерировать пароль для заданного клиента.");
		}
	}

	/**
	 * Получение UserInfo клиента.
	 * @return UserInfo.
	 * @throws BusinessException
	 */
	private Map<String, String> getClientInfo() throws BusinessException, FailureIdentificationException
	{
		ActivePerson person = getPerson();
		Map<String, String> info = new HashMap<String, String>();
		info.put("firstname", person.getFirstName());
		info.put("surname", person.getSurName());
		info.put("patrname", person.getPatrName());
		info.put("birthdate", DateHelper.formatDateToString(person.getBirthDay()));

		StringBuilder passport = new StringBuilder();
		PersonDocument document = PersonHelper.getPersonDocument(person, PersonDocumentType.PASSPORT_WAY);
		if (document == null)
		{
			throw new FailureIdentificationException("У клиента "+ person.getId()+" не найден паспорт WAY");
		}
		passport.append(StringHelper.getEmptyIfNull(document.getDocumentSeries())).append(StringHelper.getEmptyIfNull(document.getDocumentNumber()));
		info.put("passport", passport.toString());

		String tbCode = new SBRFOfficeCodeAdapter(departmentService.findById(person.getDepartmentId()).getCode()).getRegion();
		info.put("tb", StringHelper.appendLeadingZeros(tbCode, 2));
		return info;
	}

	/**
	 * Получение карт заданного клиента подключенных к МБ
	 * @return список карт
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<CardLink> getMobileCardLinks()
	{
		return cardLinks;
	}

	private EmployeeInfo getEmployeeInfo() throws BusinessException
	{
		Employee empl = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		PersonNameImpl personName = new PersonNameImpl();
		personName.setFullName(empl.getFullName());
		EmployeeInfoImpl info = new EmployeeInfoImpl();
		info.setGuid(empl.getLogin().getUserId());
		info.setPersonName(personName);
		return info;
	}

	public void checkIMSIByCardNumber(String cardLinkId) throws BusinessLogicException, BusinessException, CheckIMSIException
	{
		for (CardLink link : cardLinks)
		{
			if (link.getId().equals(Long.valueOf(cardLinkId)))
			{
				try
				{
					MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
					UserInfo userInfo = mobileBankService.getClientByCardNumber(link.getNumber());
					if (userInfo == null)
						throw new BusinessLogicException("По указаной карте невозможно получить информацию о пользователе.");
					checkIMSI(userInfo.getUserId());
					return;
				}
				catch (GateException e)
				{
					throw new BusinessException(e);
				}
				catch (GateLogicException e)
				{
					throw new BusinessLogicException(e);
				}
			}
		}
		throw new BusinessLogicException("По выбранному идентификатору не найдена карта клиента.");
	}

	public void checkIMSI(String login) throws BusinessException, CheckIMSIException, BusinessLogicException
	{
		try
		{
			CSABackRequestHelper.sendCheckIMSIRq(login, getEmployeeInfo());
		}
		catch (CheckIMSIException e)
		{
			throw e;
		}
		catch (MobileBankRegistrationNotFoundException e)
		{
			throw new BusinessLogicException(getMessageConfig().message("personsBundle", "mobilebank.registration.not.found.error.message"), e);
		}
		catch (BackException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		return;
	}
}
