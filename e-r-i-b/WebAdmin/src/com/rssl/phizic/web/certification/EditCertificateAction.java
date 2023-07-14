package com.rssl.phizic.web.certification;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.certification.EditCertificateOperation;
import com.rssl.phizic.operations.certification.WrongCertificateStatusException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.security.certification.CertOwn;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 27.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditCertificateAction extends EditActionBase
{
    protected Map getAdditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.activate", "activate");

        return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		if(frm.getId() == null)
	        throw new BusinessException("Не установлен идентификатор сертификата");

	    EditCertificateOperation operation = createOperation(EditCertificateOperation.class);
	    operation.initialize(frm.getId());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, BusinessLogicException
	{
		EditCertificateForm frm = (EditCertificateForm)form;

		frm.setCertOwn((CertOwn) operation.getEntity());
	}

	protected void updateForm(EditFormBase frm, Object entity) {}

	protected void updateEntity(Object entity, Map<String, Object> data) {}

    public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response) throws Exception
    {
		EditCertificateForm frm = (EditCertificateForm)form;
	    ActionMessages messages= new ActionMessages();
		    
		if(frm.getId() == null)
	        throw new BusinessException("Не установлен идентификатор сертификата");

		EditCertificateOperation operation = createOperation(EditCertificateOperation.class);

        operation.initialize(frm.getId());
	    try
	    {
		    operation.activate();
		    frm.setCertOwn( operation.getEntity() );
	    }
	    catch(WrongCertificateStatusException ex)
		{
			    ActionMessage message = new ActionMessage("com.rssl.phizic.web.certificate.wrong.certStatus",
					    operation.getEntity().getCertificate().getId());
			    messages.add("certification", message);
		}
	    catch(BusinessLogicException ex)
	    {
		    ActionMessage message = new ActionMessage(ex.getMessage());
		    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
	    }

	    if(messages.size() != 0)
	        saveErrors(request, messages);

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

	    return mapping.findForward(FORWARD_START);
    }
}
