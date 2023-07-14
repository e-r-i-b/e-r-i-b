package com.rssl.phizic.web.actions.templates;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.receivers.PaymentReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 15.08.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class GoodsAndServicesPaymentAction extends OperationalActionBase
{
    private static final String FORWARD_START = "Start";
    private static final String FORWARD_NEXT  = "Next";
	private static final String FORWARD_TEMPLATE = "Template";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();

        map.put("button.next",  "next");

        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    GoodsAndServicesPaymentForm frm = (GoodsAndServicesPaymentForm)form;
        updateForm(frm);

        return findForwardByAppointment(mapping, frm);
    }
	private ActionForward findForwardByAppointment(ActionMapping mapping, GoodsAndServicesPaymentForm frm)
	{
		String appoinment = frm.getAppointment();
		ActionForward forward = mapping.findForward(appoinment);
		if( forward == null )
			return mapping.findForward(FORWARD_START);
		else
			return forward;
	}

    public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        GoodsAndServicesPaymentForm      frm          = (GoodsAndServicesPaymentForm)form;
        MapValuesSource                  fieldsSource = new MapValuesSource(frm.getFields());
        FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource, getFieldForm());
        ActionForward                    actionForward;

        if ( processor.process() )
        {
            Map<String, Object> result      = processor.getResult();
            String              receiverKey = (String) result.get("receiverSelect");

            UrlBuilder urlBuilder = new UrlBuilder();
	        Map<String, String> parameters = new HashMap<String, String>();

             if(frm.isTemplate())
            {
                urlBuilder.setUrl(mapping.findForward(FORWARD_TEMPLATE).getPath());
	            parameters.put("type", frm.getType());
            }
	        else
	            urlBuilder.setUrl( mapping.findForward(FORWARD_NEXT).getPath() );
	        parameters.put("appointment", frm.getAppointment());
	        parameters.put("receiverKey", receiverKey);
	        urlBuilder.addParameters(parameters);

	        addLogParameters(new CompositeLogParametersReader(
						new SimpleLogParametersReader("appointment", frm.getAppointment()),
						new SimpleLogParametersReader("receiverKey", result.get("receiverSelect")),
						new SimpleLogParametersReader("type", frm.getType())
			        ));

            actionForward = new ActionForward(urlBuilder.getUrl(), true);
        }
        else
        {
            saveErrors(request, processor.getErrors());
            actionForward = start(mapping, form, request, response);
        }

        return actionForward;
    }

    private void updateForm(GoodsAndServicesPaymentForm form) throws BusinessException, BusinessLogicException, DataAccessException
    {
	    String              paymentAppointment = form.getAppointment();
	    Map<String, String> receivers          = buildReceiversMap(paymentAppointment);

	    PaymentReceiversDictionary dictionary          = new PaymentReceiversDictionary();
		PaymentDescriptor paymentDescriptions = dictionary.getPaymentDescription(paymentAppointment);
		form.setDescription(paymentDescriptions.getDescription());
		form.setDetailedDescription(paymentDescriptions.getDetailedDescription());

        form.setReceivers(receivers);
    }

    private Form getFieldForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        // Получатель
        FieldBuilder fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("receiverSelect");
        fieldBuilder.setDescription("Получатель платежа");

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Не выбран получатель платежа");

        fieldBuilder.setValidators(requiredFieldValidator);
        formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }

	private Map<String, String> buildReceiversMap(String paymentAppointment) throws BusinessException
	{
		Map<String, String>        result              = new HashMap<String, String>();
		PaymentReceiversDictionary dictionary          = new PaymentReceiversDictionary();
		List<ReceiverDescriptor>   receiverDescriptors = dictionary.getReceiverDescriptors(paymentAppointment);

		for ( ReceiverDescriptor receiverDescriptor : receiverDescriptors )
		{
			String receiverKey         = receiverDescriptor.getKey();
			String receiverDescription = receiverDescriptor.getDescription();

			result.put(receiverKey, receiverDescription);

			addLogParameters(new SimpleLogParametersReader(receiverKey, receiverDescription));
		}

		return result;
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form)
	{
		GoodsAndServicesPaymentForm frm = (GoodsAndServicesPaymentForm)form;
		return mapping.getPath()+  "/" + frm.getAppointment();
	}
}
