package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.certification.EditCertDemandAdminOperation;
import com.rssl.phizic.operations.certification.WrongCertificateStatusException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 02.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class RefuseCertDemandAction extends OperationalActionBase
{
    public static final String FORWARD_START = "Start";
	public static final String FORWARD_LIST = "List";

    protected Map getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.refuse", "refuse");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	   return mapping.findForward(FORWARD_START);
    }

    public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
	    RefuseCertDemandForm frm = (RefuseCertDemandForm) form;

		MapValuesSource valuesSource = new MapValuesSource(frm.getFields());
		Form refuseForm = RefuseCertDemandForm.REFUSE_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, refuseForm);
	    ActionMessages messages= new ActionMessages();
		if(processor.process())
		{
			EditCertDemandAdminOperation operation = createOperation(EditCertDemandAdminOperation.class);
			operation.initialize(frm.getId());
			try
			{
			   operation.refuseDemand( (String)frm.getField("refuseReason") );
			}
			catch(WrongCertificateStatusException e)
			{
			   ActionMessage message = new ActionMessage("com.rssl.phizic.web.certificate.error.certStatusNotMatch");
			   messages.add("certification", message);
			   saveErrors(request, messages);
			   return mapping.findForward(FORWARD_START);
			}

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

			return mapping.findForward(FORWARD_LIST);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
    }
}
