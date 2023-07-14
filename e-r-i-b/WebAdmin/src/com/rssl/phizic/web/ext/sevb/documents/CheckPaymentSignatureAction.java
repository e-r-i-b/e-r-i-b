package com.rssl.phizic.web.ext.sevb.documents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ext.sevb.payment.CheckPaymentSignatureOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.io.StringWriter;
import java.io.IOException;

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
		CheckPaymentSignatureOperation operation = createOperation(CheckPaymentSignatureOperation.class); //��� �������� �������.
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
			    frm.setDocumentContent(transform(operation.getDocumentContent()));

			    addLogParameters(new SimpleLogParametersReader("������������� �������", id));
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
		fb.setDescription("������������� �������");
		fb.setType("integer");
		fb.addValidators(new RequiredFieldValidator());

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	String transform(String message) throws BusinessException
	{
		Document document = null;
		if(message==null || message.length()==0)
			return null;

		try
		{
			document = XmlHelper.parse(message);
		}
		catch (Exception e)
		{
			//���� �� ����� ��������� ���������, �� ���������� ��� ����
			return message;
		}

		try
		{
			StringWriter stringWriter = new StringWriter();
			InnerSerializer serial = new InnerSerializer(stringWriter);
			serial.serialize(document);
			return stringWriter.toString();
		}
		catch(IOException ex)
		{
			throw new BusinessException(ex);
		}
	}

}