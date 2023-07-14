package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Map;

public class AccountOpenValidator  extends AccountPresenceValidator
{
	// если этот параметр задан, в validate не используем переданный маппинг, зато используем
	// значения переданных параметров с теми же именами, что и FIELD_01, FIELD_02
	public static final String USE_PAREMETERS_INSTEAD_MAP_VALUES = "useParameters";
	// вместо счетов проверяем карты
	public static final String FIND_CARDS = "findCards";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
	private DepartmentService departmentService = new DepartmentService();

	public AccountOpenValidator()
	{
		this("Счет закрыт");
	}

	public AccountOpenValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		PersonService personService = new PersonService();
		boolean useParams =  "true".equals(getParameter(USE_PAREMETERS_INSTEAD_MAP_VALUES));
		boolean findCards =  "true".equals(getParameter(FIND_CARDS));

		String accountId = useParams ? getParameter(FIELD_O1) : (String) retrieveFieldValue(FIELD_O1, values);
        String clientId = useParams ? getParameter(FIELD_O2) : (String) retrieveFieldValue(FIELD_O2, values);

		if ( accountId == null || clientId == null )
			return true;

		ActivePerson person = null;
		Department department = null;

		try
		{
			person = personService.findByClientId( clientId );
			department = departmentService.findById(person.getDepartmentId());
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException(ex);
		}
		if(person == null)
			return true;

		InputMode inputMode = null;
		try
		{
			inputMode = gateInfoService.getAccountInputMode(department);
		}
		catch (IKFLException ex)
		{
			String message1 = "Ошибка при получении информации о возможностях шлюза для департамента " + department.getFullName();
			log.error(message1,ex);
			throw new TemporalDocumentException(message1,ex);
		}

		Account account = null;
		Card card = null;
		try
		{
			if(InputMode.IMPORT.equals(inputMode))
				account = findCards ? null : GroupResultHelper.getOneResult(bankrollService.getAccount(accountId));
			if(InputMode.MANUAL.equals(inputMode))
				account = findCards ? null : getAccountByNumber(accountId, department, person);

			card = findCards ? GroupResultHelper.getOneResult(bankrollService.getCard(accountId)) : null;
		}
		catch (IKFLException ex)
		{
			String message1 = "Ошибка при получении информации по сущности с идентификатором " + accountId;
			log.error(message1,ex);
			throw new TemporalDocumentException(message1,ex);
		}

		if (!findCards && account != null && !(account.getAccountState() == AccountState.OPENED))
		{
			currentMessage.set("Невозможно добавить закрытый счет");
			return false;
		}
		return checkResource(findCards, card, account, person);
	}

	protected boolean checkResource(boolean findCards, Card card, Account account, ActivePerson person) throws TemporalDocumentException
	{
		return findCards || account != null;
	}

}
