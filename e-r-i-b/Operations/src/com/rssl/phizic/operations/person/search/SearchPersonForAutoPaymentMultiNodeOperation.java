package com.rssl.phizic.operations.person.search;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.person.search.multinode.SearchPersonMultiNodeOperation;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author komarov
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class SearchPersonForAutoPaymentMultiNodeOperation extends SearchPersonMultiNodeOperation
{

	private static final SimpleService simpleService = new SimpleService();

	@Override
	protected void updateAdditionalData(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		switch (getUserVisitingMode())
		{
			//поиск по ДУЛ
			case EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT: super.updateAdditionalData(identityData);	return;
			//поиск по банковской карте
			case EMPLOYEE_INPUT_BY_CARD: getIdentityData(identityData); return;
		}

		throw new BusinessException("Некорректный тип входа");
	}

	private void getIdentityData(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		try
		{
			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
			Office office = departmentService.findById(employee.getDepartmentId());

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(new Pair<String, Office>((String) data.get("cardNumber"), office)));
			if (client == null)
				throw new BusinessLogicException("По указанным данным клиент не найден в системе.");

			data.put("surName",     client.getSurName());
			data.put("firstName",   client.getFirstName());
			data.put("patrName",    client.getPatrName());
			data.put("birthDay",    client.getBirthDay().getTime());
			data.put("region",      LoginHelper.getEmployeeOfficeRegion());

			if (CollectionUtils.isEmpty(client.getDocuments()))
				throw new BusinessLogicException("По указанным данным клиент не найден в системе.");

			ClientDocument document = client.getDocuments().get(0);
			data.put("documentNumber",  document.getDocNumber());
			data.put("documentSeries",  document.getDocSeries());
			data.put("documentType",    ClientDocumentType.PASSPORT_WAY.name());
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

	@Override
	protected void updatePerson(ActivePerson person, Map<String, Object> identityData, AuthData authData) throws BusinessException
	{
		Login login = person.getLogin();
		if (StringHelper.isEmpty(login.getLastLogonCardDepartment()))
			LoginHelper.updateClientLogonData(login, authData);

		//подтягиваем продукты клиента
		loadProducts(person);

		//если клиент зашел по карте, регистрируем ее
		if (getUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
		{
			login.setLastLogonCardNumber(authData.getPAN());

			simpleService.update(login);
		}
	}

	@Override
	protected List<Class> getResourcesClasses(ActivePerson person) throws BusinessException
	{
		List<Class> resources = new ArrayList<Class>();

		try
		{
			ExternalSystemHelper.check(getExternalSystem(person, BankProductTypeWrapper.getBankProductType(Card.class)));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		resources.add(Card.class);

		try
		{
			ExternalSystemHelper.check(getExternalSystem(person, BankProductTypeWrapper.getBankProductType(Account.class)));
			resources.add(Account.class);
		}
		catch (InactiveExternalSystemException e)
		{
			// Если неактивна внешняя система для счетов, то просто не добавляем счета
			// к списку продуктов для обновления.
			log.error("Неактивна внешняя система для счетов. " + e.getMessage());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}

		return resources;
	}
}
