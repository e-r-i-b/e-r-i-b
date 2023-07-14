package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccountPresenceValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private static final DepartmentService departmentService = new DepartmentService();
	private final ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);

	protected ThreadLocal<String>  currentMessage  = new ThreadLocal<String>();

	public static final String FIELD_O1 = "accountId";
	public static final String FIELD_O2 = "clientId";

	//todo криво, подумать как подругому.
    public AccountPresenceValidator()
    {
        this("—чет не найден");
    }

	public String getMessage()
	{
		String str = currentMessage.get();
		if(str == null)
			return super.getMessage();
		else return str;
	}

    public AccountPresenceValidator(String message)
    {
        setMessage(message);
    }

    public boolean validate(Map values) throws TemporalDocumentException
    {
	    String accountId =(String) retrieveFieldValue(FIELD_O1, values);
        String clientId = (String) retrieveFieldValue(FIELD_O2, values);

	    if ( accountId == null || clientId == null )
			return true;

	    ActivePerson person = null;
		Department department = null;

	    try
	    {
		    PersonService personService = new PersonService();
			person = personService.findByClientId(clientId);
			department = departmentService.findById(person.getDepartmentId());
	    }
	    catch (BusinessException e)
	    {
		    throw new RuntimeException(e);
	    }

	    return getAccountByNumber(accountId, department, person) != null;
    }

	protected Account getAccountByNumber(String accountNumber, Department department, ActivePerson person) throws TemporalDocumentException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		try
		{
			if (((ExtendedDepartment) department).isEsbSupported())
			{
				//провер€ем активность внешней системы
				ExternalSystemHelper.check(externalSystemGateService.findByProduct(department, BankProductType.Deposit));

				//gfl запрос
				GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products = productService.getClientProducts(person.asClient(), Account.class);
				for (Object obj : GroupResultHelper.getResult(products, Account.class))
				{
					Account account = (Account)((Pair<Object, AdditionalProductData>) obj).getFirst();
					if ( account.getNumber().equals(accountNumber))
						return account;
				}
				currentMessage.set("—чет " + accountNumber + " не найден среди счетов клиента.");
				return null;
			}
		}
		catch (SystemException e)
		{
			throw new RuntimeException(e);
		}
		catch (LogicException e)
		{
			throw new RuntimeException(e);
		}

		try
	    {
		    Pair<String, Office> accountInfo = new Pair<String, Office>(accountNumber, department);
		    return GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(accountInfo));
        }
	    catch (LogicException e)
	    {
		    currentMessage.set(e.getMessage());
		    return null;
	    }
		catch(SystemException ex)
		{
			String message1 = "ќшибка при получении информации по счету " + accountNumber;
			log.error(message1,ex);
			throw new TemporalDocumentException(message1, ex);
		}
	}
}
