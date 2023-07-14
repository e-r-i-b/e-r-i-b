package com.rssl.phizic.operations.person.mdm;


import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.mdm.client.service.MDMClientService;
import com.rssl.phizic.mdm.client.service.types.ClientInformation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Операция поиска информации в МДМ
 * @author komarov
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */
public class SearchMDMInformationOperation extends PersonOperationBase implements ViewEntityOperation<String>
{
	private static final String MESSAGE = "Не удалось найти идентификатор МДМ";
	private static final PersonDocumentTypeComparator DOCUMENT_COMPARATOR = new PersonDocumentTypeComparator();
	private static final MDMClientService mdmService = new MDMClientService();

	private String mdmId;

	/**
	 * Инициализирует операцию
	 * @param personId идентификатор профиля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		initializeViaPersonId(personId);
		mdmId = getMdmId(getPerson());

	}

	/**
	 * Ищет идентификатор мдм во внешней системе
	 * @param personId идентификатор профиля в блоке
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void search(Long personId) throws BusinessException, BusinessLogicException
	{
		initializeViaPersonId(personId);
		mdmId = getExternalSystemMdmId(getPerson());
	}

	public String getEntity() throws BusinessException, BusinessLogicException
	{
		return mdmId;
	}

	private String getMdmId(Person person) throws BusinessException, BusinessLogicException
	{
		try
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			Long clientId = person.getId();
			employeeData.setCurrentClientId(clientId);
			String mdm_Id = employeeData.getCurrentClientMdmId();
			if (mdm_Id == null)
			{
				Document response = CSABackRequestHelper.getClientProfileIdRequestDataRq(PersonHelper.buildUserInfo(person));
				String stringValue =  XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.PROFILE_ID_TAG);
				Long csaProfileId = Long.parseLong(stringValue);

				mdm_Id = getStoredMdmId(csaProfileId);
				employeeData.setCurrentClientCsaProfileId(csaProfileId);
				employeeData.setCurrentClientMdmId(mdm_Id);
			}
			return mdm_Id;
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessLogicException(MESSAGE, e);
		}
		catch (BackLogicException ble)
		{
			throw new BusinessLogicException(ble.getMessage(), ble);
		}
		catch (Exception e)
		{
			throw new BusinessLogicException(MESSAGE, e);
		}
	}

	private String getExternalSystemMdmId(Person person) throws BusinessException, BusinessLogicException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		ClientInformation information = createClientInformation(person, employeeData.getCurrentClientCsaProfileId());
		try
		{
			String mdmIdValue = getExternalMDMId(information);
			employeeData.setCurrentClientMdmId(mdmIdValue);
			return mdmIdValue;
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessLogicException(MESSAGE, e);
		}
		catch (Exception e)
		{
			throw new BusinessLogicException(MESSAGE, e);
		}
	}

	private String getExternalMDMId(ClientInformation information) throws BusinessException, BusinessLogicException
	{
		try
		{
			return mdmService.getExternalSystemMdmId(information);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private String getStoredMdmId(Long profileId) throws BusinessException, BusinessLogicException
	{
		try
		{
			return mdmService.getStoredMdmId(profileId);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}



	private ClientInformation createClientInformation(Person person, Long csaProfileId) throws BusinessException
	{
		ClientInformation information = new ClientInformation();
		information.setInnerId(csaProfileId);
		information.setBirthday(person.getBirthDay());
		information.setCardNum(person.getLogin().getLastLogonCardNumber());
		information.setLastName(person.getSurName());
		information.setFirstName(person.getFirstName());
		information.setMiddleName(person.getPatrName());
		setDocument(information, getDocument(person));
		return information;
	}

	private PersonDocument getDocument(Person person) throws BusinessException
	{

		List<PersonDocument> documentList = new ArrayList<PersonDocument>(person.getPersonDocuments());
		Collections.sort(documentList, DOCUMENT_COMPARATOR);
		PersonDocument document = documentList.get(0);
		if (document == null)
		{
			throw new BusinessException("У клиента "+ person.getId()+" не найден паспорт");
		}
		return document;
	}

	private void setDocument(ClientInformation information, PersonDocument document)
	{
		PersonDocumentType type = document.getDocumentType();
		information.setDocumentSeries(type == PersonDocumentType.PASSPORT_WAY ? null                         : document.getDocumentSeries());
		information.setDocumentNumber(type == PersonDocumentType.PASSPORT_WAY ? document.getDocumentSeries() : document.getDocumentNumber());
		information.setDocumentType(ClientDocumentType.valueOf(type.toValue()));
	}
}
