package com.rssl.phizic.web.templates;

import com.rssl.phizic.operations.templates.AgreementTemplateOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 26.01.2007 Time: 13:01:40 To change this template use File
 * | Settings | File Templates.
 */
public class AgreementTemplateAction extends OperationalActionBase
{
    public static final String FORWARD_START   = "Start";


    protected Map<String,String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                throws Exception
    {
		AgreementTemplateForm frm = (AgreementTemplateForm)form;
	    AgreementTemplateOperation operation = createOperation(AgreementTemplateOperation.class);
	    operation.setPerson(frm.getPerson());

	    frm.setClient(operation.getPerson());

	    Set<PersonDocument> personDocuments = operation.getPerson().getPersonDocuments();
	    if (personDocuments != null)
	    {
		    for(PersonDocument document : personDocuments)
		    {
			    if (document.getDocumentMain())
			    {
			        frm.setActiveDocument(document);
				    break;
			    }
		    }
	    }

	    frm.setTemplates(operation.getDocTemplateList());
        return mapping.findForward(FORWARD_START);
    }
}
