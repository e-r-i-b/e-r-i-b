package com.rssl.phizic.web.atm.payments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.GetCommonPaymentListOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.payments.ClientDocumentsListActionBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.*;

/**
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowPaymentListATMAction extends ClientDocumentsListActionBase
{

	private enum PaymentState {
		ATM_STATES_BATCH,
		INITIAL,
		DRAFT,
		SAVED,
		EXECUTED,
		REFUSED,
		RECALLED,
		ERROR,
		DELAYED_DISPATCH,
		DISPATCHED,
		WAIT_CONFIRM,
		UNKNOW,
		SENT,
		SUCCESSED,
		PARTLY_EXECUTED,
		SAVED_TEMPLATE,
		TEMPLATE,
		WAIT_CONFIRM_TEMPLATE,
		DELETED,
		STATEMENT_READY,
		ADOPTED,
		OFFLINE_SAVED,
		OFFLINE_DELAYED,
		BILLING_CONFIRM_TIMEOUT,
		ABS_RECALL_TIMEOUT,
		INITIAL_LONG_OFFER,
		BILLING_GATE_CONFIRM_TIMEOUT,
		ABS_GATE_RECALL_TIMEOUT;
	}

	private static final String ALL_STATES = "all";

	//формируем пол€ фильтрации дл€ валидации
	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ShowPaymentListATMForm frm = (ShowPaymentListATMForm) form;
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(FilterPeriodHelper.FROM_DATE_FIELD_NAME, frm.getFrom());
		filter.put(FilterPeriodHelper.TO_DATE_FIELD_NAME, frm.getTo());
		filter.put(ShowPaymentListATMForm.CREATION_TYPE, frm.getCreationType());
		return new MapValuesSource(filter);
	}

	private boolean isPaymentStatusClear(ShowPaymentListATMForm form) throws BusinessException
	{
		if (StringHelper.isEmpty(form.getStatus()) || form.getStatus().equals(ALL_STATES))
			return true;
		return false;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowPaymentListATMForm.FORM;
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME, FilterPeriodHelper.PERIOD_TYPE_MONTH);
		return parameters;
	}

	/**
	 * ѕолучение данных и обновление формы.
	 * @param filterParams параметры фильтрации.
	 * @param operation операци€ дл€ получени€ данных
	 * @param frm форма дл€ обновлени€.
	 */
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams, (GetCommonPaymentListOperation)operation, frm);
		frm.setData(query.executeList());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams, GetCommonPaymentListOperation operation, ListFormBase frm) throws BusinessException
	{
		super.fillQuery(query, filterParams);
		ShowPaymentListATMForm form  = (ShowPaymentListATMForm) frm;
		Object creationType = filterParams.get(ShowPaymentListATMForm.CREATION_TYPE);
		List formIds = Collections.EMPTY_LIST;

		if(creationType == null)
		{
			//если null - так и сеттим, чтобы вернулась истори€ по всем типам
			query.setParameter("creationType", creationType);
		}
		else
		{
			try{
				//пытаемс€ найти соответствующий тип по строке и засеттить
				query.setParameter("creationType", CreationType.valueOf(creationType.toString()).toValue());
			}
			catch(IllegalArgumentException ae)
			{
				throw new BusinessException(ae.getMessage(),ae.getCause());
			}
		}


		try
		{
			formIds = ArrayUtils.isEmpty(form.getForm()) ? Collections.EMPTY_LIST : getIds(operation, form);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}

		query.setListParameters(ShowPaymentListATMForm.FORM_ID, formIds, MAX_BIND_VARS_FOR_INT_COUNT);

		query.setParameter(ShowPaymentListATMForm.PAYMENT_STATUS, isPaymentStatusClear(form) ? null : getPaymentStatus(form));

	}

	private String getPaymentStatus(ShowPaymentListATMForm form) throws BusinessException
	{
		if(isPaymentStatusClear(form))
			return null;

		try
		{
			PaymentState.valueOf(form.getStatus().toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			throw new BusinessException("Ќедопустимый статус документа. ", e);
		}

		return  form.getStatus().toUpperCase();
	}

	private List<String> getIds(GetCommonPaymentListOperation operation, ShowPaymentListATMForm form) throws BusinessException, BusinessLogicException
	{

		List<String> formIds = new ArrayList();
		Map<String, String> formNamesIds = new HashMap<String, String>();

		if(form.getForm().length > MAX_BIND_VARS_FOR_INT_COUNT)
			throw new BusinessLogicException("ѕервышено допустимое кличество форм. ");

		formNamesIds = getAllSystemForms(operation);

		for(String frmName : form.getForm()) {
			if(formNamesIds.keySet().contains(frmName.toLowerCase())) {
				formIds.add(formNamesIds.get(frmName.toLowerCase()));
			} else {
				throw new BusinessException("Ќедопустимое им€ формы. ");
			}
		}

		return formIds;
	}

	private Map<String, String> getAllSystemForms(GetCommonPaymentListOperation operation) throws BusinessException, BusinessLogicException
	{

		List<FilterPaymentForm> filterPaymentForms = null;

		filterPaymentForms = operation.getFilterPaymentForms();

		if (filterPaymentForms == null || filterPaymentForms.isEmpty())
			return Collections.EMPTY_MAP;

		Map<String, String> map = new HashMap<String, String>();
		for(FilterPaymentForm frm : filterPaymentForms) {
			map.put(frm.getName().toLowerCase(),frm.getIds());
		}
		return map;
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		return mapping.getPath() + "/null";
	}
}
