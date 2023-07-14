package com.rssl.phizic.operations.person.list;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.business.clients.list.ClientInformationService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения списка клиентов из ЦСА Бек
 */

public class ListClientOperation extends OperationBase implements ListEntitiesOperation
{
	private static final ClientInformationService service = new ClientInformationService();

	private String fio;
	private String document;
	private Calendar birthday;
	private String login;
	private CreationType creationType;
	private String agreementNumber;
	private String phoneNumber;
	private String tb;
	private int maxResults;
	private int firstResult;

	private Map<String, String> allowedTB;

	/**
	 * инициализация операции доступными ТБ
	 */
	public void initialize() throws BusinessException
	{
		List<Department> allowedDepartments = AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();

		allowedTB = new HashMap<String, String>();
		for (Department allowedDepartment : allowedDepartments)
			allowedTB.put(new ExtendedCodeImpl(allowedDepartment.getCode()).getRegion(), allowedDepartment.getName());
	}

	/**
	 * задать фио
	 * @param fio фио
	 */
	public void setFio(String fio)
	{
		this.fio = fio;
	}

	/**
	 * задать документ
	 * @param document документ
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * задать дату рождения
	 * @param birthday дата рождения
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * задать логин
	 * @param login логин
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * задать тип договора
	 * @param creationType тип договора
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * задать номер договора
	 * @param agreementNumber номер договора
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	/**
	 * задать номер телефона
	 * @param phoneNumber номер телефона
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * задать ТБ
	 * @param tb ТБ
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * задать максимальное количество клиентов
	 * @param maxResults максимальное количество клиентов
	 */
	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	/**
	 * задать смещение выборки
	 * @param firstResult смещение выборки
	 */
	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	/**
	 * @return список доступных ТБ
	 */
	public Map<String, String> getAllowedTB()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return allowedTB;
	}

	/**
	 * получить список клиентов
	 * @return список клиентов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<ClientInformation> getClientList() throws BusinessException, BusinessLogicException
	{
		return service.getClientsInformation(fio, document, birthday, login, creationType, agreementNumber, phoneNumber, getTBList(), maxResults, firstResult);
	}

	private List<String> getTBList()
	{
		Set<String> tbSet = allowedTB.keySet();

		if (StringHelper.isEmpty(tb))
			return new ArrayList<String>(tbSet);

		if (tbSet.contains(tb))
			return Collections.singletonList(tb);

		return Collections.emptyList();
	}
}
