package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.person.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kidyaev
 * @ created 10.10.2005
 * @ $Author$
 * @ $Revision$
 */

public class ListClientsAction extends SaveFilterParameterAction
{
	private static final String FORWARD_EDIT = "Edit";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.import", "importClient");

		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetClientsOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListClientsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws BusinessException, BusinessLogicException, GateLogicException, GateException
	{
		ListClientsForm     frm = (ListClientsForm)     form;
		GetClientsOperation op  = (GetClientsOperation) operation;

		int listLimit = webPageConfig().getListLimit();

		ClientImpl template = new ClientImpl();
		template.setFirstName((String) filterParams.get("firstName"));
		template.setSurName((String) filterParams.get("surName"));
		template.setPatrName((String) filterParams.get("patrName"));
		template.setId((String) filterParams.get("id"));
		Object birthDate = (filterParams.get("birthDate") instanceof Date) ?
				filterParams.get("birthDate") :	StringHelper.getNullIfEmpty((String)filterParams.get("birthDate"));
		template.setBirthDay(DateHelper.toCalendar((Date) birthDate));
		List<Client> clients = op.getClientsByTemplate(template, listLimit);
		frm.setData(clients);
	}

	public final ActionForward importClient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientsForm frm = (ListClientsForm) form;
		ActionMessages msgs = new ActionMessages();

		String[] ids = frm.getSelectedIds();

		if (ids.length < 1)
			throw new BusinessException("Клиент для импорта не выбран.");

		String clientId = ids[0];
		Set<String> clientIds = null;

		//преобразуем в set, заодно выбираем наибольший.
		for(int i=0; i< ids.length; i++)
		{
			if(ids[i].compareTo(clientId) > 0)
				clientId = ids[i];

			if(clientIds==null)
				clientIds = new HashSet();

			clientIds.add(ids[i]);
		}
		clientIds.remove(clientId);

		ImportClientOperation operation = createOperation(ImportClientOperation.class);
		operation.setClientId(clientId);
		operation.setClientIds(ids.length ==0 ? null:clientIds);

		try
		{
			final String tempId = clientId;
			final ActivePerson person = operation.createPerson();
			
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ListBankrollsOperation resourceOperation = createOperation(ListBankrollsOperation.class);
					resourceOperation.initialize(new ExternalIdClientSource(tempId));

					if (checkAccess(AddAccountOperation.class))     //проверяем возможность импорта счетов
					{
						AddAccountOperation addAccountOperation = createOperation(AddAccountOperation.class);
						addAccountOperation.setPersonId(person.getId());
						addAccountOperation.addResources(resourceOperation.getAccouts());
						addAccountOperation.save();
					}
					if (checkAccess(AddCardOperation.class))         //проверяем возможность импорта карт
					{
						AddCardOperation addCardOperation = createOperation(AddCardOperation.class);
						addCardOperation.setPersonId(person.getId());
						addCardOperation.addResources(resourceOperation.getCards());
						addCardOperation.save();
					}

					return null;
				}
			});

			addLogParameters(new BeanLogParemetersReader("Импортируемая сущность", person));

			return PersonUtils.redirectWithPersonId(mapping.findForward(FORWARD_EDIT), person.getId());
		}
		catch (BusinessLogicException be)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(be.getMessage(), false));
			saveErrors(request, msgs);

			return start(mapping, form, request, response);
		}
	}
}
