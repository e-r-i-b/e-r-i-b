package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.xml.rpc.ServiceException;

/**
 * @author Gulov
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Создает заглушку для вызова веб-сервиса, который возвращает записи сообщений ЕРМБ
 * Заполняет запрос из полей фильтра
 */
public class SmsLogWrapper
{
	private static final String DEFAULT_MESSAGE_TYPE = "both";

	/**
	 * Возвращает данные из веб-сервиса
	 * @param map - фильтр
	 * @return - данные из веб-сервиса
	 * @throws BusinessException
	 */
	public SmsLogRs_Type getSmsLog(Map<FilterField, Object> map) throws BusinessException
	{
		SmsLogRq_Type request = new SmsLogRq_Type();
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTime(Calendar.getInstance());
		makeFilter(request, map);
		makePagination(request, map);
		String sessionId = StringUtils.trimToNull((String) map.get(FilterField.SESSION_ID));
		if (sessionId != null)
			request.setSessionID(sessionId);
		return invokeMessageLog(getStub(), request);
	}

	private SmsLogRs_Type invokeMessageLog(SmsLogServiceStub stub, SmsLogRq_Type request) throws BusinessException
	{
		try
		{
			return stub.getMessageLog(request);
		}
		catch (RemoteException e)
		{
			throw new BusinessException("Ошибка вызова веб-сервиса журнала ЕРМБ", e);
		}
	}

	/**
	 * Создание заглушки для вызова
	 * @return - заглушка
	 */
	private SmsLogServiceStub getStub()
	{
		try
		{
			SmsLogServiceLocator locator = new SmsLogServiceLocator();
			ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
			locator.setsmsLogServiceEndpointAddress(config.getSmsLogUrl());
			return (SmsLogServiceStub) locator.getsmsLogService();
		}
		catch (ServiceException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void makePagination(SmsLogRq_Type request, Map<FilterField, Object> map)
	{
		Pagination_Type pagination = new Pagination_Type();
		request.setPagination(pagination);
		pagination.setSize(BigInteger.valueOf((Integer) map.get(FilterField.PAGINATION_SIZE)));
		pagination.setOffset(BigInteger.valueOf((Integer) map.get(FilterField.PAGINATION_OFFSET)));
	}

	private void makeFilter(SmsLogRq_Type request, Map<FilterField, Object> map) throws BusinessException
	{
		Filter_Type filter = new Filter_Type();
		request.setFilter(filter);
		Calendar fromDate = createCalendar(map, FilterField.FROM_DATE, FilterField.FROM_TIME);
		Calendar toDate = createCalendar(map, FilterField.TO_DATE, FilterField.TO_TIME);
		filter.setBeginTime(fromDate);
		filter.setEndTime(toDate);
		filter.setMessageType(DEFAULT_MESSAGE_TYPE);
		makePhone(filter, map.get(FilterField.PHONE));
		makeClient(filter, map);
	}

	private Calendar createCalendar(Map<FilterField, Object> map, FilterField fieldDate, FilterField fieldTime) throws BusinessException
	{
		try
		{
			return DateHelper.createCalendar(map.get(fieldDate), map.get(fieldTime));
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка создания даты из полей " + fieldDate + ", " + fieldTime, e);
		}
	}

	private void makePhone(Filter_Type filter, Object phone)
	{
		String value = StringUtils.trimToNull((String) phone);
		if (value == null)
			return;
		filter.setPhone(value);
	}

	private void makeClient(Filter_Type filter, Map<FilterField, Object> map)
	{
		Name_Type name = makeName(map.get(FilterField.FIO));
		Date birthDay = getBirthDay(map.get(FilterField.BIRTHDAY));
		IdentityCard_Type document = makeDocument(map);
		String tb = makeTb(map.get(FilterField.TB));

		if (name != null || birthDay != null || document != null || tb != null)
		{
			ClientRq_Type client = new ClientRq_Type();
			filter.setClient(client);
			if (name != null)
				client.setName(name);
			if (birthDay != null)
				client.setBirthday(birthDay);
			if (document != null)
				client.setIdentityCard(document);
			if (tb != null)
				client.setTb(tb);
		}
	}

	private String makeTb(Object tb)
	{
		String value = StringUtils.trimToNull((String) tb);
		if (value == null)
			return null;
		else
			return StringHelper.appendLeadingZeros(value, 3);
	}

	private Name_Type makeName(Object fio)
	{
		String value = StringUtils.trimToNull((String) fio);
		if (value == null)
			return null;
		String[] name = ((String) fio).split(" ");
		if (name.length <= 1)
			return null;
		Name_Type result = new Name_Type();
		result.setLastname(name[0]);
		if (name.length > 1)
			result.setFirstname(name[1]);
		if (name.length > 2)
			result.setMiddlename(name[2]);
		return result;
	}

	private Date getBirthDay(Object birthDay)
	{
		return (Date) birthDay;
	}

	private IdentityCard_Type makeDocument(Map<FilterField, Object> map)
	{
		String documentType = StringUtils.trimToNull((String) map.get(FilterField.DOCUMENT_TYPE));
		String documentSeries = StringUtils.trimToNull((String) map.get(FilterField.DOCUMENT_SERIES));
		String documentNumber = StringUtils.trimToNull((String) map.get(FilterField.DOCUMENT_NUMBER));
		if (documentType == null || documentNumber == null)
			return null;
		IdentityCard_Type result = new IdentityCard_Type();
		if (documentType != null)
			result.setIdType(documentType);
		if (documentSeries != null)
			result.setIdSeries(documentSeries);
		if (documentNumber != null)
			result.setIdNum(documentNumber);
		return result;
	}
}
