package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.operations.person.EditBankrollsOperation;
import com.rssl.phizic.operations.person.GetResourcesOperation;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import com.rssl.phizic.web.persons.ListPersonResourcesAction;
import com.rssl.phizic.web.persons.ListPersonResourcesForm;
import com.rssl.phizic.web.persons.PersonUtils;
import org.apache.struts.action.*;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 09.10.2009
 * @ $Author$
 * @ $Revision$
 */

//Для СБРФ в системе должны быть только открытые счета
public class SBRFListPersonResourcesAction extends ListPersonResourcesAction
{
	protected void updateAdditionalResources(ActivePerson person, GetResourcesOperation op, ListPersonResourcesForm frm) throws BusinessException, BusinessLogicException
	{
		if (op.isEsbSupported() && person.getCreationType() == CreationType.UDBO)
		{
			frm.setImAccounts(op.getIMAccounts());
		}
	}

	public ActionForward savePaymentAbilities(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Long                    personId        = PersonUtils.getPersonId(request);
        ListPersonResourcesForm frm             = (ListPersonResourcesForm) form;
        List<String> selectedIds     = Arrays.asList( frm.getSelectedPaymentAbleAccounts() );

        EditBankrollsOperation editOperation   = createOperation(EditBankrollsOperation.class);
        editOperation.setPersonId(personId);

        GetResourcesOperation operation       = createOperation(GetResourcesOperation.class);
        operation.setPersonId(personId);

        List<AccountLink>  resources   = operation.getActiveAccounts();

	    try
	    {
		    if (selectedIds.size() > 3)
			{
				throw new BusinessLogicException("Вы можете выбрать не более трех счетов для оплаты");
			}

			for (AccountLink accountLink : resources)
			{
				String accountId = accountLink.getExternalId();
				editOperation.updateAccountPaymentAbility(accountLink, selectedIds.contains(accountId));
			}

		    addLogParameters(new BeanLogParemetersReader("Клиент", operation.getPerson()));
		    addLogParameters(new CollectionLogParametersReader("Счета для снятия платы", editOperation.getChangedAccounts()));

		    editOperation.validate();
			editOperation.switchToShadow();
			editOperation.update();
	    }
	    catch(BusinessLogicException ex)
	    {
		    ActionMessages messageContainer = new ActionMessages();
		    ActionMessage message = new ActionMessage(ex.getMessage(),false);
		    messageContainer.add(ActionMessages.GLOBAL_MESSAGE, message);
	        saveErrors(request, messageContainer);
	    }
	    stopLogParameters();
        return start(mapping, form, request, response);
    }
}
