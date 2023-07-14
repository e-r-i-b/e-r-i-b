package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmPaymentReceiverOperation;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationBase;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kidyaev
 * @ created 30.11.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditPaymentReceiverActionBase extends EditActionBase
{
	protected Form getEditForm(EditFormBase form)
	{
		EditPaymentReceiverForm frm = (EditPaymentReceiverForm) form;

		return BuildReceiversForm.createForm(frm.getKind());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		frm.setFromStart(true);

		EditEntityOperation operation = createEditOperation(frm);
		doStart(operation, frm);

		return getCurrentMapping().findForward(FORWARD_START + ((PaymentReceiverBase) operation.getEntity()).getKind());
	}

    protected void updateEntity(Object entity, Map<String, Object> data)
    {
		PaymentReceiverBase paymentReceiver = (PaymentReceiverBase) entity;

	    BeanHelper.SetPropertiesFromMapFull(paymentReceiver , data);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws BusinessException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
	{
		PaymentReceiverBase tmpReceiver = (PaymentReceiverBase) entity;
		PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
		ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(tmpReceiver.getKind());
		List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
		for (FieldDescriptor e : fieldDescriptors)
		{
			frm.setField(e.getName(), dictionary.calculateFieldValue(tmpReceiver, e));
		}
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		EditPaymentReceiverForm frm = (EditPaymentReceiverForm) form;
		EditPaymentReceiverOperationBase op = (EditPaymentReceiverOperationBase) operation;

		frm.setActivePerson(op.getPerson());
		frm.setKind(op.getEntity().getKind());
		frm.setState(op.getEntity().getState());
		frm.setModified(PersonOperationMode.shadow.equals(op.getMode()));
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws BusinessException
	{
		EditPaymentReceiverOperationBase op = (EditPaymentReceiverOperationBase) operation;
		//‘иксируем данные, введенные пользователем
		addLogParameters(frm);
		//≈сли недоступна услуга ConfirmPaymentReceiverOperation, то автоматически устанавливаем статус активен
		op.save(!checkAccess(ConfirmPaymentReceiverOperation.class));
		return createSaveActionForward(op, frm);
	}
}
