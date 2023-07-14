package com.rssl.phizic.web.client.moneyBox;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateMoneyBoxOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * Асинхронный экшен создания заявки на копилку.
 */
public class AsyncCreateMoneyBoxAction extends EditDocumentActionBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.next");
		map.remove("button.prev");
		map.remove("button.saveAsDraft");
		map.remove("button.makeLongOffer");
		map.remove("button.edit_template");
		map.remove("button.makeAutoTransfer");
		return map;
	}

	@Override
	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			Long paymentId = frm.getId();

			DocumentSource source = null;
			if(paymentId != null && paymentId > 0L)
			{
				source = new ExistingSource(paymentId, new ChecksOwnersPaymentValidator());
			}
			else
			{
				source = new NewDocumentSource(FormConstants.CREATE_MONEY_BOX_FORM, new RequestValuesSource(currentRequest()), getNewDocumentCreationType(), CreationSourceType.ordinary, messageCollector);
			}

			CreateMoneyBoxOperation operation = createOperation(CreateMoneyBoxOperation.class, DocumentHelper.getClientServiceName(source));
			operation.initialize(source, getRequestFieldValuesSource());

			return operation;
		}
		catch (FormException e)
		{
			if (e.getCause() instanceof GateLogicException)
				throw new BusinessLogicException(e.getCause().getMessage(), e);
			throw new BusinessException(e);
		}
	}

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, operation,valuesSource);
		frm.setType("create");
	}

	public ActionForward editPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		CreatePaymentForm frm = (CreatePaymentForm) form;
		try
		{
			EditDocumentOperation operation = createEditOperation(request, frm, messageCollector);
			editSave((CreateMoneyBoxOperation)operation);
			saveOperation(request, operation);

			FieldValuesSource valuesSource = operation.getFieldValuesSource();
			if(MaskPaymentFieldUtils.isRequireMasking())
				valuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, valuesSource), valuesSource);

			updateForm(frm, operation, valuesSource);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward("Failed");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward("Failed");
		}
		return mapping.findForward("ShowEdit");
	}

	protected void editSave(CreateMoneyBoxOperation operation) throws BusinessLogicException, BusinessException
	{
		operation.edit();
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		CreateMoneyBoxOperation operation = getOperation(request);

		Metadata metadata = operation.getMetadata();
		FieldValuesSource valuesSource = getRequestFieldValuesSource();

		Form validateForm = metadata.getForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, validateForm, DocumentValidationStrategy.getInstance());

		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			//передаем на форму ошибки валидации
			updateForm(frm, operation, getShowFormFieldValuesSource(operation));
			return mapping.findForward("Failed");
		}
		Map<String, Object> formData = processor.getResult();
		try
		{
			operation.updateDocument(formData);
			operation.save();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward("Failed");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward("Failed");
		}
		frm.setId(operation.getDocumentId());
		return mapping.findForward("Success");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPaymentForm frm = (EditPaymentForm)form;
		try
		{
			DocumentSource source = new ExistingSource(frm.getId(), new ChecksOwnersPaymentValidator());
			RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, source.getMetadata().getName());
			removeOperation.initialize(source);
			removeOperation.remove();
			saveMessage(request, StrutsUtils.getMessage("moneyBox.remove.claim.message.success", "moneyboxBundle"));
			return mapping.findForward("Success");
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward("Failed");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return mapping.findForward("Failed");
		}
	}

	protected boolean isAjax()
	{
		return true;
	}
}
