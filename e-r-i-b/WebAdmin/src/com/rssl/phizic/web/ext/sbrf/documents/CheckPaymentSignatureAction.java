package com.rssl.phizic.web.ext.sbrf.documents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.CheckPaymentSignatureOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CheckPaymentSignatureAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

    protected Map<String, String> getKeyMethodMap()
    {
         Map<String,String> map = new HashMap<String, String>();
         map.put("button.check.signature", "check");
         return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		CheckPaymentSignatureOperation operation = createOperation(CheckPaymentSignatureOperation.class); //для проверки доступа.
		return mapping.findForward(FORWARD_START);
    }

    public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    CheckPaymentSignatureOperation operation = createOperation(CheckPaymentSignatureOperation.class);

	    FormProcessor<ActionMessages,?> processor = createFormProcessor(new RequestValuesSource(request), createForm());

	    if(processor.process())
	    {
		    Map<String, Object> result = processor.getResult();
		    BigInteger          id = (BigInteger) result.get("paymentId");

		    try
		    {
			    operation.initialize(id.longValue());
			    operation.validate();

			    CheckPaymentSignatureForm frm = (CheckPaymentSignatureForm) form;

			    frm.setMetadata(operation.getMetadata());
			    frm.setSignature(operation.getSignature());
			    frm.setSuccess(operation.isSuccess());
			    frm.setDocumentContent(operation.getDocumentContent());

			    addLogParameters(new SimpleLogParametersReader("Идентификатор платежа", id));
		    }
		    catch (BusinessLogicException e)
		    {
			    saveError(request, e);
		    }

	    }
	    else
	    {
		    saveErrors(request, processor.getErrors());
	    }


	    return mapping.findForward(FORWARD_START);
    }

	private Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb.setName("paymentId");
		fb.setDescription("Идентификатор платежа");
		fb.setType("integer");
		fb.addValidators(new RequiredFieldValidator());

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

}