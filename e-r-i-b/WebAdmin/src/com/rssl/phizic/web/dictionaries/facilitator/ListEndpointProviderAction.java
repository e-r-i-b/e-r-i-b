package com.rssl.phizic.web.dictionaries.facilitator;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.facilitator.ListEndpointProviderOperation;
import com.rssl.phizic.operations.dictionaries.provider.ActivateOrLockServiceProviderOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен для редактирования настроек фасилитатора и списка КПУ
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListEndpointProviderAction extends SaveFilterParameterAction
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keys = super.getAditionalKeyMethodMap();
		keys.put("button.save", "save");
		return keys;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException
	{
		ListEndpointProviderForm frm = (ListEndpointProviderForm) form;
		ListEndpointProviderOperation operation = createOperation(ListEndpointProviderOperation.class, "FacilitatorsDictionaryManagement");
		Long id = frm.getId();
		if (frm.getIdEndpointProvider() == 0)
			operation.initialize(id);
		else
			operation.initializeByEndpointProvider(id, frm.getIdEndpointProvider());
		return operation;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException
	{
		ListEndpointProviderForm form = (ListEndpointProviderForm) frm;
		ListEndpointProviderOperation op = (ListEndpointProviderOperation) operation;

		form.setFacilitator((InternetShopsServiceProvider) op.getEntity());
		form.setFacilitatorProperties(op.getProviderProperties());
		form.setData(op.getEndpointProviders());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}


	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		ListEndpointProviderForm frm = (ListEndpointProviderForm) form;
		ListEndpointProviderOperation op = (ListEndpointProviderOperation)operation;

		if (frm.getIdEndpointProvider() == 0)
		{
			op.setMaxResults(frm.getPaginationSize() + 1);
			op.setFirstResult(frm.getPaginationOffset());
			op.findEndpointProviders();
		}

		form.setFilters(filterParams);
		updateFormAdditionalData(frm, op);
	}

	/**
	 * Асинхронное сохранение параметров КПУ
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - реквест
	 * @param response - респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListEndpointProviderForm frm = (ListEndpointProviderForm) form;
		ListEndpointProviderOperation op = (ListEndpointProviderOperation) createListOperation(frm);

		String facilitatorProperty = frm.getFacilitatorProperty();
		if (StringUtils.isNotEmpty(facilitatorProperty))
		{
			String[] propertyArray = StringUtils.split(facilitatorProperty, "-");
			op.saveFacilitatorProperties(propertyArray);
			if (ListEndpointProviderOperation.FACILITATOR_EINVOICING_PARAMETER_NAME.equals(propertyArray[0]))
				activateOrLock(frm, request, propertyArray[1].equals("1"));
		}
		String propertyString = frm.getPropertyString();
		if (StringUtils.isNotEmpty(propertyString))
		{
			String[] stringsArray = StringUtils.split(propertyString, ';');
			List <List<String>> changedPropertiesList = new ArrayList<List<String>>();
			for (String row : stringsArray)
			{
				changedPropertiesList.add(Arrays.asList(StringUtils.split(row, '-')));
			}
			if (CollectionUtils.isNotEmpty(changedPropertiesList))
			{
				for(FacilitatorProvider provider : op.updateEndpointProviderProperties(changedPropertiesList))
				{
					addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", provider));
				}
			}
		}
		response.getOutputStream().println(" ");
		return null;
	}

	/**
	 * Активация/блокировка пставщика-фасилитатора
	 * @param form - форма
	 * @param request - реквест
	 * @param doActivate - признак: true - активировать, false - заблокировать
	 * @throws Exception
	 */
	private void activateOrLock(ActionForm form, HttpServletRequest request, boolean doActivate) throws Exception
	{
		ListEndpointProviderForm frm = (ListEndpointProviderForm) form;
		ActivateOrLockServiceProviderOperation operation = createOperation("ActivateOrLockServiceProviderOperation");
		try
		{
			operation.initialize(frm.getId());
			if (doActivate)
				operation.activate();
			else
				operation.lock();
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
	}
}

