package com.rssl.phizic.web.log.ermb;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.auxiliary.smslog.ErmbSmsHistoryOperation;
import com.rssl.phizic.business.ermb.auxiliary.smslog.FilterField;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Action списка смс сообщений, полученного из веб-сервиса
 */
public class ErmbSmsHistoryListAction extends SaveFilterParameterAction
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ErmbSmsHistoryListForm form = (ErmbSmsHistoryListForm) frm;
		ErmbSmsHistoryOperation result = createOperation(ErmbSmsHistoryOperation.class);
		// ≈сли вход в данный журнал осуществл€етс€ из анкеты клиента, инициализируем операцию клиентов
		if (NumericUtil.isNotEmpty(form.getPersonId()))
			result.initialize(form.getPersonId());
		return result;
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		ErmbSmsHistoryListForm form = (ErmbSmsHistoryListForm) frm;
		if (form.getPersonId() == null || form.getPersonId() == 0)
		{
			return ErmbSmsHistoryListForm.FILTER_FORM;
		}
		else
		{
			return ErmbSmsHistoryListForm.PERSON_FILTER_FORM;
		}
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException
	{
		ErmbSmsHistoryOperation smsLogOperation = (ErmbSmsHistoryOperation) operation;
		Person person = smsLogOperation.getPerson();
		if (!frm.isFromStart() || person != null)
		{
			ErmbSmsHistoryListForm form = (ErmbSmsHistoryListForm) frm;
			Map<FilterField, Object> map = new EnumMap<FilterField, Object>(FilterField.class);
			fillEnumMap(filterParams, map);
			map.put(FilterField.SESSION_ID, StringHelper.getNullIfEmpty(form.getSessionId()));
			smsLogOperation.setPaginationSize(form.getPaginationSize());
			smsLogOperation.setPaginationOffset(form.getPaginationOffset());
			smsLogOperation.initialize(map);
			form.setData(smsLogOperation.getData());
			form.setSessionId(smsLogOperation.getSessionId());
			if (person != null)
			{
				form.setActivePerson(person);
				fillFilterFromPerson(person, filterParams);
			}
		}
		if (frm.isFromStart())
		{
			String temp = (String) currentRequest().getAttribute("$$pagination_offset0");
			if (StringHelper.isNotEmpty(temp))
				currentRequest().setAttribute("$$pagination_offset0", 0);
			temp = (String) currentRequest().getAttribute("$$pagination_size0");
			if (StringHelper.isNotEmpty(temp))
				currentRequest().setAttribute("$$pagination_size0", 0);
		}
		frm.setFilters(filterParams);
	}

	private void fillEnumMap(Map<String, Object> filterParams, Map<FilterField, Object> map)
	{
		for (Map.Entry<String, Object> entry : filterParams.entrySet())
			map.put(FilterField.fromValue(entry.getKey()), entry.getValue());
	}

	/**
	 * ¬озвращает параметры фильтра в случае если вход в данный журнал осуществл€етс€ из анкеты клиента,
	 * @param frm - форма
	 * @param operation - операци€
	 * @return - заполненные пол€ фильтра
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Override
	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParams = super.getFilterParams(frm, operation);
		ErmbSmsHistoryOperation smsHistoryOperation = (ErmbSmsHistoryOperation) operation;
		if (smsHistoryOperation.getPerson() != null)
			fillFilterFromPerson(smsHistoryOperation.getPerson(), filterParams);
		return filterParams;
	}

	/**
	 * «аполнение полей фильтра из анкеты клиента
	 * @param person - клиент
	 * @param filterParams - параметры фильтра
	 */
	private void fillFilterFromPerson(Person person, Map<String, Object> filterParams) throws BusinessException
	{
		filterParams.put(FilterField.FIO.value(), person.getFullName());
		PersonDocument document = PersonHelper.getMainPersonDocument(person);
		if (document != null)
		{
			PersonDocumentType docType = document.getDocumentType();
			filterParams.put(FilterField.DOCUMENT_TYPE.value(), PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(docType)));
			if (docType == PersonDocumentType.PASSPORT_WAY)
			{
				filterParams.put(FilterField.DOCUMENT_NUMBER.value(), document.getDocumentSeries());
			}
			else
			{
				filterParams.put(FilterField.DOCUMENT_SERIES.value(), document.getDocumentSeries());
				filterParams.put(FilterField.DOCUMENT_NUMBER.value(), document.getDocumentNumber());
			}
		}
		filterParams.put(FilterField.BIRTHDAY.value(), DateHelper.clearTime(person.getBirthDay()).getTime());
		filterParams.put(FilterField.TB.value(), StringHelper.addLeadingZeros(PersonHelper.getPersonTb(person), 3));
	}

	@Override
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParams = super.getDefaultFilterParameters(frm, operation);
		Calendar end = Calendar.getInstance();
		Calendar start = DateHelper.getPreviousNDay(end, 6);
		filterParams.put(FilterField.FROM_DATE.value(), DateHelper.formatDateToStringWithPoint(start));
		filterParams.put(FilterField.FROM_TIME.value(), DateHelper.BEGIN_DAY_TIME);
		filterParams.put(FilterField.TO_DATE.value(), DateHelper.formatDateToStringWithPoint(end));
		filterParams.put(FilterField.TO_TIME.value(), DateHelper.END_DAY_TIME);

		return filterParams;
	}
}
