package com.rssl.phizic.web.common.client.reminder;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.reminder.ProcessReminderOperation;
import com.rssl.phizic.web.actions.payments.forms.ConfirmCreateTemplateForm;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.payments.forms.QuicklyCreateTemplateAction;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное создание напоминания
 * @author niculichev
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncProcessReminderAction extends QuicklyCreateTemplateAction
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);
	private static final String PROCESS_FORWARD = "process";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> res = super.getKeyMethodMap();
		res.put("button.changeReminderInfo", "changeReminderInfo");
		res.put("button.delay", "delay");
		res.put("button.delete", "delete");
		return res;
	}

	/**
	 * Веб-метод для откладывания напоминания
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward delay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncProcessReminderForm frm = (AsyncProcessReminderForm) form;
		ProcessReminderOperation operation = createOperation(ProcessReminderOperation.class, "ReminderManagment");
		try
		{
			Map<String, Object> formData = checkDelayFormData(frm);

			operation.initialize(frm.getReminderId());
			operation.delay((Date) formData.get(AsyncProcessReminderForm.DELAY_DATE_FIELD_NAME));

			frm.setState("success");
			ClientInvoiceCounterHelper.resetCounterValue();
			InvoiceMessage.saveMessage(operation.getTemplate(), InvoiceMessage.Type.delay);
		}
		catch (Exception e)
		{
			frm.setState("failure");
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(PROCESS_FORWARD);
	}

	/**
	 * Веб-метод для удаления напоминания
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncProcessReminderForm frm = (AsyncProcessReminderForm) form;
		ProcessReminderOperation operation = createOperation(ProcessReminderOperation.class, "ReminderManagment");
		try
		{
			operation.initialize(frm.getReminderId());
			operation.remove();

			frm.setState("success");
			ClientInvoiceCounterHelper.resetCounterValue();
			InvoiceMessage.saveMessage(operation.getTemplate(), InvoiceMessage.Type.delete);
		}
		catch (Exception e)
		{
			frm.setState("failure");
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(PROCESS_FORWARD);
	}

	protected Map<String, Object> checkFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditTemplateForm.REMINDER_FORM_WITH_NAME);
		if(!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	protected void saveDraftTemplate(ConfirmCreateTemplateForm frm, EditTemplateDocumentOperation operation,  Map<String, Object> formData) throws BusinessLogicException, BusinessException
	{
		TemplateDocument template = operation.getTemplate();
		template.setReminderInfo(buildReminderInfo(formData));

		super.saveDraftTemplate(frm, operation, formData);
	}

	protected Map<String, Object> checkReminderFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditTemplateForm.REMINDER_FORM);
		if(!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	protected Map<String, Object> checkDelayFormData(EditFormBase frm) throws Exception
	{
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), AsyncProcessReminderForm.DELAY_FORM);
		if(!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	/**
	 * Веб-метод для изменения параметров напоминания
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward changeReminderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncProcessReminderForm frm = (AsyncProcessReminderForm) form;

		try
		{
			EditTemplateDocumentOperation operation = createViewOperation(frm);
			Map<String, Object> formData = checkReminderFormData(frm, operation);

			operation.changeReminderInfo(buildReminderInfo(formData),
					// если инвойс уже был выставлен, его нужно оставить
					ClientInvoiceCounterHelper.getResidualReminderDate(operation.getTemplate().getId()));

			frm.setState("success");
		}
		catch (Exception e)
		{
			frm.setState("failure");
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(PROCESS_FORWARD);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
