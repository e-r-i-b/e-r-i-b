package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.Response_Type;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogRs_Type;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gulov
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логика работы с веб-сервисом журнала ЕРМБ
 */
public class ErmbSmsHistoryOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PersonService personService = new PersonService();
	private static final int INIT_PAGINATION_SIZE = 50;
	private static final int INIT_PAGINATION_OFFSET = 0;

	private List<SmsLogEntry> data = Collections.emptyList();
	private String sessionId;
	private Person person;
	private int paginationSize;
	private int paginationOffset;

	/**
	 * Инициализация операции полями фильтра и вызов веб-сервиса
	 * @param map - поля и значения фильтра
	 * @throws BusinessException
	 */
	public void initialize(Map<FilterField, Object> map) throws BusinessException
	{
		map.put(FilterField.PAGINATION_SIZE, paginationSize);
		map.put(FilterField.PAGINATION_OFFSET, paginationOffset);
		SmsLogRs_Type smsLog = getSmsLog(Collections.unmodifiableMap(map));
		setData(smsLog);
		setSession(smsLog.getResponse());
	}

	/**
	 * Инициализация операции анкетой клиента
	 * @param personId - уникальный код анкеты клиента
	 * @throws BusinessException
	 */
	public void initialize(Long personId) throws BusinessException
	{
		if (personId != null && personId > 0)
		{
			person = personService.findById(personId);
			if (person == null)
				throw new BusinessException("Не найден клиент с ID = " + personId);
		}
	}

	private SmsLogRs_Type getSmsLog(Map<FilterField, Object> map) throws BusinessException
	{
		SmsLogWrapper smsLogWrapper = new SmsLogWrapper();
		return smsLogWrapper.getSmsLog(map);
	}

	//Все записи, не вошедшие в запрос с учетом пагинации, заполняются пустышками.
	private void setData(SmsLogRs_Type smsLog) throws BusinessException
	{
		int status = smsLog.getStatus().intValue();
		if (status == 0)
		{
			Response_Type response = smsLog.getResponse();
			LogEntry_Type[] entries = response.getLogEntries();
			if (ArrayUtils.isEmpty(entries))
			{
				data = Collections.emptyList();
				return;
			}
			int pageSize = Math.min(paginationSize, entries.length);
			int dataSize = paginationOffset + pageSize;
			//Если пришел признак, что есть еще записи, или вернулось больше, чем помещается на странице,
			// то добавляется еще пустая запись в конец для отображения стрелки.
			if (BooleanUtils.isTrue(response.getHasMoreMessages()) || entries.length > paginationSize)
			{
				dataSize++;
			}
			data = new ArrayList<SmsLogEntry>(dataSize);

			for (int i = 0; i < dataSize; i++)
			{
				//Заполнить одну страницу данных
				if (i < pageSize)
				{
					data.add(i, new SmsLogEntry(entries[i]));
				}
				//Остальное пустышками
				else
				{
					data.add(i, new SmsLogEntry());
				}
			}
			//сдвинуть данные на текущую страницу
			Collections.rotate(data, paginationOffset);
		}
		else
		{
			throw new BusinessException("Ошибка получения списка СМС. Код ошибки: " + status);
		}
	}

	public String getSessionId()
	{
		return sessionId;
	}

	private void setSession(Response_Type response)
	{
		sessionId = null;
		if (response != null)
			if (response.getSessionID() != null)
				sessionId = response.getSessionID();
	}

	public List<SmsLogEntry> getData()
	{
		return Collections.unmodifiableList(data);
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPaginationSize(String paginationSize)
	{
		this.paginationSize = (StringHelper.isNotEmpty(paginationSize)) ? Integer.parseInt(paginationSize) : INIT_PAGINATION_SIZE;
	}

	public void setPaginationOffset(String paginationOffset)
	{
		this.paginationOffset = (StringHelper.isNotEmpty(paginationOffset)) ? Integer.parseInt(paginationOffset) : INIT_PAGINATION_OFFSET;
	}
}
