package com.rssl.phizic.operations.pseudonyms;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.List;

/**
 * @author eMakarov
 * @ created 20.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPseudonymsListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PersonService personService = new PersonService();
	private static final PseudonymService pseudonymService = new PseudonymService();
	private static final BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private ActivePerson person;

	/**
	 * использовать в клиентском приложении
 	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		PersonData personData = provider.getPersonData();
		Login login = personData.getPerson().getLogin();
		person = personService.findByLogin(login);
	}

	/**
	 * в админе надо передать id клиента
	 * @param personId id клиента
	 * @throws BusinessException
	 */
	public void initialize(long personId) throws BusinessException
	{
		person = (ActivePerson) personService.findById(personId);
	}

	public List<Pseudonym> getLinks() throws BusinessException
	{
		if (person == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен клиент");
		}

		if (person.getLogin() == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен login клиента");
		}

		return pseudonymService.findClientPseudonyms(person.getLogin());
	}

	public List<Pseudonym> getAccessibleLinks() throws BusinessException
	{
		if (person == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен клиент");
		}

		if (person.getLogin() == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен login клиента");
		}

		return pseudonymService.findAccessibleClientPseudonyms(person.getLogin());
	}

	public Pseudonym getLink(String name) throws BusinessException
	{
		if (person == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен клиент");
		}

		if (person.getLogin() == null)
		{
			throw new BusinessException("Ќевозможно получить список псевдонимов:не установлен login клиента");
		}

		Pseudonym pseudo = pseudonymService.findByPseudonymName(person.getLogin(), name);

		if (pseudo == null)
			throw new BusinessException("ѕсевдоним " + name + " не найден");

		return pseudo;
	}

	public void saveChanges(List<Pseudonym> pseudonyms) throws BusinessException
	{
		for (Pseudonym pseudonym : pseudonyms)
		{
			pseudonymService.addOrUpdate(pseudonym);
		}
	}

	public CommonLogin getLogin()
	{
		if (person != null && person.getLogin() != null)
		{
			return person.getLogin();
		}
		return null;
	}
	//TODO причем здесь этот метод?
	public List<TransactionBase> getPseudoTransactions(Pseudonym pseudo) throws BusinessLogicException, BusinessException
	{
		List<TransactionBase> transactions = null;
		// TODO обработка ошибок в GroupResult
		try
		{

			ExternalResourceLink link = pseudo.getLink();
			Object value = link.getValue();

			if((link instanceof CardLink) && MockHelper.isMockObject(value))
				return null;

			transactions = GroupResultHelper.getOneResult(bankrollService.getAbstract(5L, value)).getTransactions();
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		return transactions;
	}
}
