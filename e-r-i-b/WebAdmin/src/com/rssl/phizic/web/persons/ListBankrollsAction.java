package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.operations.person.ExternalIdClientSource;
import com.rssl.phizic.operations.person.ListBankrollsOperation;
import com.rssl.phizic.operations.person.PersonIdClientSource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;

import java.util.Map;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 06.10.2005
 * Time: 14:19:58
 */
public class ListBankrollsAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListBankrollsOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListBankrollsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws ParseException, BusinessException, BusinessLogicException
	{
		fillForm((ListBankrollsForm) form);
	}

	private void fillForm(ListBankrollsForm frm) throws BusinessException, BusinessLogicException, ParseException
	{
		PersonIdClientSource personIdSource = new PersonIdClientSource((frm.getPerson()));
		Client frmClient = personIdSource.getClient();

		String clientId = frmClient == null ? "" : frmClient.getId();
		ListBankrollsOperation operation = createOperation(ListBankrollsOperation.class);
		ExternalIdClientSource clientSource = new ExternalIdClientSource(clientId);

		Client client = clientSource.getClient();

		frm.setClient(client);

		if(client != null)
		{
			operation.initialize(clientSource);
			if (frm.getType().equals("cards"))
				frm.setData(operation.getCards());
			else
				frm.setData(operation.getAccouts());
		}
	}
}
