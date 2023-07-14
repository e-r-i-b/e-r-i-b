package com.rssl.phizic.web.certification;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.certification.EditCertificateOperation;
import com.rssl.phizic.operations.certification.GetCertificateListAdminOperation;
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
 * @ created 26.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListCertificateAction extends SaveFilterParameterAction
{
    protected Map getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.block", "block");
	    map.put("button.activate", "activate");
        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation( GetCertificateListAdminOperation.class );
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditCertificateOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCertificateForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("firstName",filterParams.get("firstName") );
		query.setParameter("patrName",filterParams.get("patrName") );
		query.setParameter("surName",filterParams.get("surName") );
		query.setParameter("startDate",filterParams.get("startDate") );
		query.setParameter("endDate", DateHelper.add((Date) filterParams.get("endDate"), 0, 0, 1) );//прибавляем 1 день, чтобы учитывалоcь _время_ окончания действия сертификата
		query.setParameter("pinNumber",filterParams.get("pinNumber") );
		query.setParameter("status",filterParams.get("status") );
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

    public ActionForward block(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    ActionMessages messages= new ActionMessages();
	    EditCertificateOperation operation = createOperation(EditCertificateOperation.class);

		ListCertificateForm frm = (ListCertificateForm)form;
	    String[] ids = frm.getSelectedIds();
	    for (String id : ids)
	    {
		    operation.initialize( Long.parseLong(id) );
		    try
		    {
		        operation.block();

			    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		    }
		    catch(WrongCertificateStatusException ex)
		    {
			    ActionMessage message = new ActionMessage("com.rssl.phizic.web.certificate.wrong.certStatus",
			        operation.getEntity().getCertificate().getId());
			    messages.add("certification", message);
		    }
	    }

	    if(messages.size()!=0)
	        saveErrors(request,messages);

	    return start(mapping, form, request, response);
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
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.certificate.wrong.certStatus",
			    op.getEntity().getCertificate().getId());
				    msgs.add("certification", message);
		    }
		return msgs;
	}

    public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    ActionMessages messages = new ActionMessages();

	    ListCertificateForm frm = (ListCertificateForm)form;

	     EditCertificateOperation operation = createOperation(EditCertificateOperation.class);

	    String[] selected = frm.getSelectedIds();
	    for (String id : selected)
	    {
		    try
		    {
				operation.initialize( Long.parseLong(id) );
				operation.activate();

			    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));
		    }
		    catch(WrongCertificateStatusException ex)
		    {
			    ActionMessage message = new ActionMessage("com.rssl.phizic.web.certificate.wrong.certStatus",
					    operation.getEntity().getCertificate().getId());
			    messages.add("certification", message);
		    }
	    }
	    if(messages.size()!=0)
	        saveErrors(request, messages);

	    return start(mapping, form, request, response);
    }

}
