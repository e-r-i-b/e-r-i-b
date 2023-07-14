package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.certification.EditCertificateOperation;
import com.rssl.phizic.operations.certification.GetPersonCertificateOperation;
import com.rssl.phizic.operations.certification.WrongCertificateStatusException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 06.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListPersonCertificateAction extends SaveFilterParameterAction
{
    protected Map<String, String> getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.block", "block");
	    map.put("button.activate", "activate");
        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetPersonCertificateOperation operation = createOperation(GetPersonCertificateOperation.class);
		operation.setPersonId(PersonUtils.getPersonId(currentRequest()));

		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditCertificateOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPersonCertificateForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("startDate", DateHelper.toCalendar((Date) filterParams.get("startDate")));
		query.setParameter("endDate", DateHelper.toCalendar(DateHelper.add((Date) filterParams.get("endDate"), 0, 0, 1)));//прибавляем 1 день, чтобы учитывалоcь _время_ окончания действия сертификата
		query.setParameter("status", filterParams.get("status") );
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListPersonCertificateForm frm = (ListPersonCertificateForm) form;
		GetPersonCertificateOperation op = (GetPersonCertificateOperation) operation;

		frm.setActivePerson( op.getActivePerson() );
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		EditCertificateOperation op = (EditCertificateOperation) operation;
		try
		{
			super.doRemove(operation, id);
		}
		catch(WrongCertificateStatusException ex)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.person.wrong.certStatus",
					    op.getEntity().getCertificate().getId());
			msgs.add("person", message);
		}
		return msgs;
	}

	public ActionForward block(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    ActionMessages messages= new ActionMessages();
	    EditCertificateOperation operation = createOperation(EditCertificateOperation.class);

		ListPersonCertificateForm frm = (ListPersonCertificateForm)form;
	    String[] ids = frm.getSelectedIds();
	    for (String id : ids)
	    {
		    operation.initialize( Long.parseLong(id) );
		    try
		    {
		        operation.block();
		    }
		    catch(WrongCertificateStatusException ex)
		    {
			    ActionMessage message = new ActionMessage("com.rssl.phizic.web.person.wrong.certStatus",
			        operation.getEntity().getCertificate().getId());
			    messages.add("person", message);
		    }

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
	    }

	    if(messages.size()!=0)
	        saveErrors(request,messages);

	    return start(mapping, form, request, response);
    }

    public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    ActionMessages messages = new ActionMessages();

	    ListPersonCertificateForm frm = (ListPersonCertificateForm)form;

	     EditCertificateOperation operation = createOperation(EditCertificateOperation.class);

	    String[] selected = frm.getSelectedIds();
	    for (String id : selected)
	    {
		    try
		    {
				operation.initialize( Long.parseLong(id) );
				operation.activate();
		    }
		    catch(WrongCertificateStatusException ex)
		    {
			    ActionMessage message = new ActionMessage("com.rssl.phizic.web.person.wrong.certStatus",
					    operation.getEntity().getCertificate().getId());
			    messages.add("person", message);
		    }

		    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
	    }
	    if(messages.size()!=0)
	        saveErrors(request, messages);

	    return start(mapping, form, request, response);
    }
}
