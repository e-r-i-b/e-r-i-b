package com.rssl.phizic.business.ermb.newclient;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientSToAddType;
import com.rssl.phizic.business.ermb.newclient.message.generated.ClientToAddType;
import com.rssl.phizic.business.ermb.newclient.message.generated.Request;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEvent;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.xml.sax.SAXException;

import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * @author Gulov
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс построитель запроса к веб-сервису уведомления ОСС о добавлении новых клиентов
 */
class NewClientsMessageBuilder
{
	private static final String REQUEST_SCHEMA = "com/rssl/phizic/business/ermb/newclient/message/new-client-request.xsd";
	private static final PhoneNumberFormat OSS_PHONE_FORMAT = PhoneNumberFormat.MOBILE_INTERANTIONAL;
	private static final CounterService counterService = new CounterService();

	public String build(List<ErmbRegistrationEvent> registrations, String login, String password) throws BusinessException
	{
		Request request = new Request();
		request.setLogin(login);
		request.setPwd(password);
		request.setRequestId(generateRequestId());
		request.setClientSToAdd(buildClientList(registrations));
		return convertRequest(request);
	}

	private String convertRequest(Request request) throws BusinessException
	{
		try
		{
			return JAXBUtils.marshalBean(request, XmlHelper.schemaByFileName(REQUEST_SCHEMA), false);
		}
		catch (SAXException e)
		{
			throw new BusinessException("Ошибка создания xsd-схемы: " + REQUEST_SCHEMA, e);
		}
		catch (JAXBException e)
		{
			throw new BusinessException("Ошибка создания строки запроса веб-сервиса уведомления ОСС о новых клиентах", e);
		}
	}

	private Long generateRequestId() throws BusinessException
	{
		try
		{
			return counterService.getNext(Counters.ERMB_NEW_CLIENT_SERVICE);
		}
		catch (CounterException e)
		{
			throw new BusinessException("Ошибка генерации нового значения запроса веб-сервиса уведомления ОСС о новых клиентах", e);
		}
	}

	private ClientSToAddType buildClientList(List<ErmbRegistrationEvent> registrations)
	{
		ClientSToAddType result = new ClientSToAddType();
		for (ErmbRegistrationEvent registration : registrations)
		{
			ClientToAddType clientToAddType = new ClientToAddType();
			clientToAddType.setMSISDN(OSS_PHONE_FORMAT.format(registration.getPhone()));
			clientToAddType.setChangedAt(XMLDatatypeHelper.getXMLCalendar(registration.getConnectionDate()));
			result.getClientToAdd().add(clientToAddType);
		}
		return result;
	}
}
