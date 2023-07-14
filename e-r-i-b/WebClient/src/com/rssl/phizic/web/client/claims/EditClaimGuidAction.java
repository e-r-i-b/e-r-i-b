package com.rssl.phizic.web.client.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.CaptchaConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.GuidDocumentSource;
import com.rssl.phizic.business.smsbanking.pseudonyms.NullPseudonymException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.operations.payment.CreateFormPaymentGuidOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentCaptchaForm;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentAction;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eMakarov
 * @ created 18.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditClaimGuidAction extends EditPaymentAction
{
	public static final String NEXT_STAGE_FORWARD = "DefaultForward";

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("button.save", "save");
        map.put("button.next", "start");
	    map.put("button.capthca.refresh", "refreshCaptcha");
        return map;
    }

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			DocumentSource source;

			String guid = frm.getGuid();
			if (guid != null)
			{
				source = new GuidDocumentSource(guid);
			}
			else
			{
				source = createNewDocumentSource(frm.getForm(), new RequestValuesSource(request), messageCollector);
				String code = RandomHelper.rand(10);
				((CreatePaymentCaptchaForm)frm).setCaptchaId(code);
			}

			EditDocumentOperation operation = createOperation(CreateFormPaymentGuidOperation.class, source.getMetadata().getName());
			operation.initialize(source, new RequestValuesSource(request));
			return operation;
		}
		catch (NullPseudonymException e)
		{
			throw new BusinessException(e);
		}
	}

	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		CreateFormPaymentGuidOperation oper = (CreateFormPaymentGuidOperation)operation;
		ActionForward temp = getCurrentMapping().findForward(NEXT_STAGE_FORWARD);
		return new ActionForward(temp .getPath() + "?guid="+oper.getGuid(), true);
	}

	public ActionForward refreshCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		CreatePaymentForm frm = (CreatePaymentForm) form;
        EditDocumentOperation operation = createEditOperation(request, frm, messageCollector);
		RequestValuesSource valuesSource = new RequestValuesSource(request);

		updateForm(frm, operation, valuesSource);
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    CreatePaymentCaptchaForm frm = (CreatePaymentCaptchaForm) form;
	    MessageCollector messageCollector = new MessageCollector();
	    try
		{
			if (frm.getCaptchaCode() == null || "".equals(frm.getCaptchaCode()))
			{
				throw new SecurityLogicException("введите символы, представленные на картинке.");
			}
			if (!CaptchaConfirmStrategy.validateResponseForID(frm.getCaptchaId(), frm.getCaptchaCode()))
			{
				throw new SecurityLogicException("Код с картинки указан неверно. Введите код повторно.");
			}
		}
	    catch (SecurityLogicException e)
	    {
		    ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SecurityMessages.translateException(e), false));
            saveErrors(request, errors);
	        return mapping.findForward(FORWARD_SHOW_FORM);
	    }
		catch (Exception e)
		{
			//should not happen, may be thrown if the id is not valid
			throw new RuntimeException(e);
		}
	    finally
	    {
			EditDocumentOperation operation = createEditOperation(request, frm, messageCollector);
		    Form dateForm = operation.getMetadata().getForm();
		    RequestValuesSource valuesSource = new RequestValuesSource(request);
		    // если при проверке пароля будут проблемы, то CreatePaymentForm.getHtml вернет NullPointerException
		    updateForm(frm, operation, valuesSource);
		    saveOperation(request, operation);
	    }

	    return super.save(mapping, form, request, response);
    }
}
