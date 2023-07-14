package com.rssl.phizic.business.ermb.auxiliary.cod;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableService;
import com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceServiceLocator;
import com.rssl.phizic.business.ermb.auxiliary.cod.message.generated.ClientType;
import com.rssl.phizic.business.ermb.auxiliary.cod.message.generated.ERMBConnActionRq;
import com.rssl.phizic.business.ermb.auxiliary.cod.message.generated.ERMBConnActionRs;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

/**
 * Сервис для включения/отключения признака подключения к ЕРМБ
 * @author Puzikov
 * @ created 12.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CODWebService
{
	private static final String version = "1.0";

	/**
	 * Установить признак подключения клиента к ЕРМБ по ФИО,ДУЛ,ДР
	 *
	 * @param connect true - подключить, false - отключить
	 * @param person клиент
	 * @param document документ клиента
	 */
	public void connectClient(boolean connect, Person person, PersonDocument document) throws BusinessException
	{
		String message = buildXml(connect, person, document);
		MBVEnableService service = getStub();
		message = invokeSendConnect(message, service);
		ERMBConnActionRs response = parseResponse(message);
		String resultCode = response.getResult();
		String resultMessage = StringHelper.getEmptyIfNull(response.getResultMessage());
		if ("000_Success".equals(resultCode))
			return;
		else if ("001_No_Parameter".equals(resultCode))
			throw new BusinessException("Не найден запрашиваемый параметр " + resultMessage);
		else if ("002_No_Connect".equals(resultCode))
			throw new BusinessException("Нет связи с одним из узлов системы " + resultMessage);
		else if ("003_Already_Done".equals(resultCode))
			return;
		else if ("004_Exception".equals(resultCode))
			throw new BusinessException("Код программной ошибки " + resultMessage);
	}

	private ERMBConnActionRs parseResponse(String message) throws BusinessException
	{
		try
		{
			return JAXBUtils.unmarshalBean(ERMBConnActionRs.class, message);
		}
		catch (JAXBException e)
		{
			throw new BusinessException("Ошибка парсинга ответа от веб-сервиса при добавлении ЕРМБ профиля клиенту в COD", e);
		}
	}

	private String invokeSendConnect(String request, MBVEnableService stub) throws BusinessException
	{
		try
		{
			return stub.sendMessage(request);
		}
		catch (RemoteException e)
		{
			throw new BusinessException(e);
		}
	}

	private MBVEnableService getStub() throws BusinessException
	{
		ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
		try
		{
			MBVEnableServiceServiceLocator locator = new MBVEnableServiceServiceLocator();
			locator.setMBVEnableServiceEndpointAddress(config.getCodServiceUrl());
			return locator.getMBVEnableService();
		}
		catch (ServiceException e)
		{
			throw new BusinessException(e);
		}
	}

	private String buildXml(boolean connect, Person person, PersonDocument document) throws BusinessException
	{

		ERMBConnActionRq message = new ERMBConnActionRq();
		message.setVersion(version);
		message.setRqUID(new RandomGUID().getStringValue());
		message.setRqTm(Calendar.getInstance());
		message.setAction(connect ? "001" : "000");
		ClientType clientType = new ClientType();
		clientType.setBirthday(person.getBirthDay());
		ClientType.PersonName personName = new ClientType.PersonName();
		personName.setLastName(person.getSurName());
		personName.setFirstName(person.getFirstName());
		personName.setMiddleName(person.getPatrName());
		clientType.setPersonName(personName);
		ClientType.IdentityCard identityCard = new ClientType.IdentityCard();
		PersonDocumentType personDocumentType = document.getDocumentType();
		identityCard.setIdType(PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(personDocumentType)));
		if (PersonDocumentType.PASSPORT_WAY == personDocumentType)
		{
			throw new IllegalArgumentException("Нельзя использовать паспорт Way");
		}
		identityCard.setIdNum(document.getDocumentNumber());
		identityCard.setIdSeries(document.getDocumentSeries());
		clientType.setIdentityCard(identityCard);
		message.setClient(clientType);
		try
		{
			return JAXBUtils.marshalBean(message);
		}
		catch (JAXBException e)
		{
			throw new BusinessException("Ошибка создания строки запроса веб-сервиса для включения признака подключения к ЕРМБ", e);
		}
	}
}
