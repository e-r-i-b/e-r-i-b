package com.rssl.phizic.web.payments;

import com.rssl.common.forms.*;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.documents.payments.EditP2PAutoTransferClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.EditPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн редактирования платежа в админке
 * @author niculichev
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentAction extends EditDocumentActionBase
{

	private static final String TO_AUTO_TRANSFER_INFO            = "ToAutoTransferInfo";

	protected ActionForward startEditPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		try
		{
			EditDocumentOperation operation = createEditOperation(request, frm, messageCollector);
			if (operation.upgrade())
				return DefaultDocumentAction.createDefaultEditForward(operation.getDocument().getId(), operation.getDocumentSate());
			saveOperation(request, operation);
			FieldValuesSource valuesSource = operation.getFieldValuesSource();

			if(MaskPaymentFieldUtils.isRequireMasking() && operation.getDocument() instanceof EditP2PAutoTransferClaim)
				valuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, valuesSource), valuesSource);
			updateForm(frm, operation, valuesSource);
			addLogParameters(new BeanLogParemetersReader("Данные документа", operation.getDocument()));

			return mapping.findForward(FORWARD_SHOW_FORM);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);

			return createForwardBack(frm, mapping);
		}
		catch (TemporalBusinessException ignored)
		{
			ActionMessage message = new ActionMessage("По техническим причинам операция временно недоступна. Повторите попытку позже", false);
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, message, null);

			return createForwardBack(frm, mapping);
		}
		catch (BusinessLogicException e)
		{
			ActionMessage message = new ActionMessage(e.getMessage(), false);
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, message, null);

			return createForwardBack(frm, mapping);
		}
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		try
		{
			Long paymentId = frm.getId();

			DocumentSource source = null;
			if(paymentId != null && paymentId > 0L)
			{
				source = createExistingDocumentSource(paymentId);
			}
			else
			{
				source = createNewDocumentSource(frm.getForm(), new RequestValuesSource(currentRequest()));
			}

			EditDocumentOperation operation = createOperation(EditPaymentOperation.class, getServiceName(source));
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

	protected DocumentSource createExistingDocumentSource(Long paymentId) throws BusinessException, BusinessLogicException
	{
		return new ExistingSource(paymentId, new ChecksOwnersPaymentValidator());
	}

	protected void updateForm(CreatePaymentForm form, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation, valuesSource);

		CreateEmployeePaymentForm frm = (CreateEmployeePaymentForm) form;
		frm.setActivePerson((ActivePerson) operation.getPerson());
	}

	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(operation.getDocumentSate().getEmployeeForm());
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}

	protected String getServiceName(DocumentSource source)
	{
		return DocumentHelper.getEmployeeServiceName(source);
	}

	protected ActionForward findBackForward(ActionMapping mapping, BusinessDocument document)
	{
		// для автоподписки переходим на первый шаг создания автоплатежа
		if((document instanceof JurPayment) && ((JurPayment) document).isLongOffer())
			return mapping.findForward(FORWARD_BACK_AUTOSUB);

		return super.findBackForward(mapping, document);
	}

	public ActionForward prev(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDocumentOperation operation = getOperation(request);
		operation.saveAsInitial();

		return super.prev(mapping, form,request,response);
	}

	private DocumentSource createNewDocumentSource(String formName, FieldValuesSource initalFieldValuesSource) throws BusinessException, BusinessLogicException
	{
		return new NewDocumentSource(formName, initalFieldValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary);
	}

	private ActionForward createForwardBack(CreatePaymentForm form, ActionMapping mapping)
	{
		if (isP2PForm(form.getForm()))
			return createAutoTransferForwardBack((CreateEmployeePaymentForm)form, mapping);
		//в дальнейшем forward создается в зависимости от формы, на данный момент переходим на форму поиска поставщика
		return mapping.findForward(FORWARD_BACK);
	}

	private ActionForward createAutoTransferForwardBack(CreateEmployeePaymentForm form, ActionMapping mapping)
	{
		String path = mapping.findForward(TO_AUTO_TRANSFER_INFO).getPath() + "?" + "id" + "=" + form.getAutoSubNumber();
		return new ActionForward(path);
	}

	private boolean  isP2PForm(String formName)
	{
		return (formName.equals(FormConstants.CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM)
			|| formName.equals(FormConstants.EDIT_P2P_AUTO_TRANSFER_CLAIM_FORM)
			|| formName.equals(FormConstants.CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM)
			|| formName.equals(FormConstants.DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM)
			|| formName.equals(FormConstants.RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM));
	}
}
