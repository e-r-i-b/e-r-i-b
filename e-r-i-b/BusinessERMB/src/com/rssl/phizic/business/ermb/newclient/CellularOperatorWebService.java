package com.rssl.phizic.business.ermb.newclient;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.newclient.generated.Sberbank_SetNewClientsLocator;
import com.rssl.phizic.business.ermb.newclient.generated.Sberbank_SetNewClientsSoap;
import com.rssl.phizic.business.ermb.newclient.generated.Sberbank_SetNewClientsSoapStub;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientResultType;
import com.rssl.phizic.business.ermb.newclient.message.generated.Response;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEvent;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.xml.sax.SAXException;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

/**
 * @author Gulov
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Вызов веб-сервиса уведомления ОСС о новых клиентах
 */
public class CellularOperatorWebService
{
	private static final String RESPONSE_SCHEMA = "com/rssl/phizic/business/ermb/newclient/message/new-client-response.xsd";
	private final String url;
	private final String login;
	private final String password;

	/**
	 * ctor
	 * @param url адрес веб-сервиса для уведомлений
	 * @param login Логин к веб-сервису
	 * @param password Пароль к веб-сервису
	 */
	public CellularOperatorWebService(String url, String login, String password)
	{
		this.url = url;
		this.login = login;
		this.password = password;
	}

	/**
	 * Уведомить ОСС о новых регистрациях клиентов
	 * @param registrations новые регистрации клиентов
	 * @return список обработанных телефонов
	 */
	public Set<PhoneNumber> notify(List<ErmbRegistrationEvent> registrations) throws BusinessException
	{
		NewClientsMessageBuilder builder = new NewClientsMessageBuilder();
		String output = null;
		try
		{
			output = invoke(builder.build(registrations, login, password));
			Response response = JAXBUtils.unmarshalBean(Response.class, output, XmlHelper.schemaByFileName(RESPONSE_SCHEMA));
			List<ClientResultType> processedPhones = getProcessedPhones(response);
			Set<PhoneNumber> result = new LinkedHashSet<PhoneNumber>();
			for (ClientResultType phone : processedPhones)
			{
				if (phone.getResult() == 0)
					result.add(PhoneNumber.fromString(phone.getMSISDN()));
			}
			return result;
		}
		catch (RemoteException e)
		{
			throw new BusinessException("Ошибка вызова веб-сервиса уведомления ОСС о новых клиентах", e);
		}
		catch (SAXException e)
		{
			throw new BusinessException("Ошибка создания xsd-схемы: " + RESPONSE_SCHEMA, e);
		}
		catch (JAXBException e)
		{
			throw new BusinessException("Ошибка разбора ответа веб-сервиса уведомления ОСС о новых клиентах:\n " + output, e);
		}
	}
	/**
	 * Вызвать веб-сервис
	 * @param request - xml-строка сообщения
	 * @return ответ веб-сервиса
	 * @throws BusinessException
	 */
	private String invoke(String request) throws RemoteException, BusinessException
	{
		Sberbank_SetNewClientsSoap stub = getStub();
		return stub.XMLMessage(request);
	}

	private List<ClientResultType> getProcessedPhones(Response response)
	{
		if (response.getOperationResult() != 0) // ни один из номеров не принят оператором
			return Collections.emptyList();
		if (response.getClientResultS() == null)
			return Collections.emptyList();
		List<ClientResultType> phones = response.getClientResultS().getClientResult();
		if (CollectionUtils.isEmpty(phones))
			return Collections.emptyList();
		return phones;
	}

	private Sberbank_SetNewClientsSoap getStub() throws BusinessException
	{
		ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
		Sberbank_SetNewClientsLocator locator = new Sberbank_SetNewClientsLocator();
		locator.setSberbank_SetNewClientsSoapEndpointAddress(url);
		Sberbank_SetNewClientsSoapStub stub = null;
		try
		{
			stub = (Sberbank_SetNewClientsSoapStub) locator.getSberbank_SetNewClientsSoap();
		}
		catch (ServiceException e)
		{
			throw new BusinessException("Ошибка определения урла веб-сервиса уведомления ОСС о новых клиентах", e);
		}
		stub.setTimeout(config.getNewClientTimeout());
		return stub;
	}
}
