package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bankroll.TransactionComparator;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.cards.CardInfoFormHelper;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 27.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен для показа графической выписки по карте
 */
public class ShowCardGraphicAbstractAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowCardGraphicAbstractForm form = (ShowCardGraphicAbstractForm)frm;
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(form.getId());

		if (operation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getCard().getCard()));
		}
		return operation;
	}

	private GetCardInfoOperation createGetCardInfoOperation(ShowCardGraphicAbstractForm form)
			throws BusinessException, BusinessLogicException
	{
		GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
		cardInfoOperation.initialize(form.getId());
		return cardInfoOperation;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();

		Object typePeriod = frm.getFilter(DatePeriodFilter.TYPE_PERIOD);

		if (typePeriod == null)
		{
			parameters.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_MONTH);
		}
		else if (typePeriod.equals(DatePeriodFilter.TYPE_PERIOD_WEEK))
		{
			parameters.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_WEEK);
		}
		else if (typePeriod.equals(DatePeriodFilter.TYPE_PERIOD_PERIOD))
		{
			try
			{
				Calendar to   = DateHelper.fromDMYDateToDate( frm.getFilter(DatePeriodFilter.TO_DATE).toString() );
				Calendar from = DateHelper.fromDMYDateToDate( frm.getFilter(DatePeriodFilter.FROM_DATE).toString() );

				//если больше чем полгода, то устанавливаем последний месяц
				if(DateHelper.diff(to, from) > ShowCardGraphicAbstractForm.MAX_PERIOD)
				{
					from = DateHelper.getCurrentDate();
					to = DateHelper.getCurrentDate();
					from.add(Calendar.MONTH, -1);
				}

				parameters.put(DatePeriodFilter.TO_DATE,   to.getTime());
				parameters.put(DatePeriodFilter.FROM_DATE, from.getTime());
			}
			catch (ParseException ignored) {}

			parameters.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		}

		return parameters;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowCardGraphicAbstractForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation op, ListFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowCardGraphicAbstractForm frm = (ShowCardGraphicAbstractForm)form;
		GetCardAbstractOperation getCardAbstractOperation = (GetCardAbstractOperation) op;

		// 1. Получаем выписку по СКС в указанном диапазоне
		DatePeriodFilter datePeriodFilter = new DatePeriodFilter(filterParams).normalize();
		getCardAbstractOperation.setDateFrom(datePeriodFilter.getFromCalendar());
		getCardAbstractOperation.setDateTo(datePeriodFilter.getToCalendar());
		AccountAbstract cardAccountAbstract = getCardAbstractOperation.getCardAccountAbstract();
		if (cardAccountAbstract!= null && cardAccountAbstract.getTransactions() != null)
			Collections.sort(cardAccountAbstract.getTransactions(), new TransactionComparator(TransactionComparator.DESC));
		frm.setCardAccountAbstract(cardAccountAbstract);

		// 2. Выставляем прочие атрибуты формы: доп.карты и т.п.
		updateFormAdditionalData(frm, op);

		// 3. Запоминаем значения фильтра
		Map<String, Object> updatedFilterParams = new HashMap<String, Object>(filterParams);
		updatedFilterParams.putAll(datePeriodFilter.getParameters());
		frm.setFilters(updatedFilterParams);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		ShowCardGraphicAbstractForm frm = (ShowCardGraphicAbstractForm)form;
		GetCardInfoOperation getCardInfoOperation = createGetCardInfoOperation(frm);
		frm.setCardLink(getCardInfoOperation.getEntity());
		//Информация по остальным картам
		CardInfoFormHelper.setAdditionalCards(frm, createOperation(GetCardsOperation.class));
	}
}
