package com.rssl.phizic.web.templates;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.DublicateDocTemplateNameException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.validators.FileSizeLimitValidator;
import org.apache.struts.action.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 16:43:49
 * To change this template use File | Settings | File Templates.
 */
public class EditTemplateDocumentAction extends EditActionBase
{
	private static final int SIMPLE_MAX_FILE_SIZE = 5*1024*1024;
	private static final int MAX_FILE_SIZE = 1024*1024*1024;

	protected Map<String,String> getAdditionalKeyMethodMap()
    {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("button.upload", "upload");

	    return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditTemplateDocumentOperation operation = createOperation(EditTemplateDocumentOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
		   operation.initialize(id);
		else
		  operation.initializeNew();

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditTemplateDocumentForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		DocTemplate docTemplate = (DocTemplate) entity;

		frm.setField("description", docTemplate.getDescription());
		frm.setField("name", docTemplate.getName());
		frm.setField("fileName", docTemplate.getFileName());
		frm.setField("fileType", docTemplate.getFileType());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation)
	{
		EditTemplateDocumentForm frm = (EditTemplateDocumentForm) form;

		frm.setField("saveBigFileQuestion", false);
		frm.setField("saveBigFile", false);
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
	    DocTemplate docTemplate = (DocTemplate) entity;

		docTemplate.setName((String) data.get("name"));
		docTemplate.setDescription((String) data.get("description"));
		docTemplate.setFileName((String) data.get("fileName"));
		docTemplate.setFileType((String) data.get("fileType"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException, BusinessLogicException
	{
	 	EditTemplateDocumentForm frm = (EditTemplateDocumentForm) form;
		DocTemplate docTemplate = (DocTemplate) editOperation.getEntity();
		try
		{
			byte[] data = frm.getTemplate().getFileData();
			docTemplate.setData(data);
		}
		catch (IOException e)
		{
			throw new IllegalStateException("Файл шаблона не найден!", e);
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws BusinessException, ParseException
	{
		EditTemplateDocumentForm frm = (EditTemplateDocumentForm) form;
		EditTemplateDocumentOperation op = (EditTemplateDocumentOperation) operation;

		return validate(frm, op.getEntity());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase form) throws Exception
	{
		HttpServletRequest request = currentRequest();
		EditTemplateDocumentForm frm = (EditTemplateDocumentForm) form;
		EditTemplateDocumentOperation op = (EditTemplateDocumentOperation) operation;

		ActionForward actionForward = mapping.findForward(FORWARD_SUCCESS);

		if (op.getEntity().getData().length > SIMPLE_MAX_FILE_SIZE && !"true".equals(frm.getField("saveBigFile")))
		{
			frm.setField("saveBigFileQuestion",true);
			actionForward = mapping.findForward(FORWARD_START);
		}
		else
		{
			try
		    {
			    //Фиксируем данные, введенные пользователем
				addLogParameters(frm);
				operation.save();
		    }
			catch(DublicateDocTemplateNameException ex)
			{
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Шаблон с таким именем уже существует",false));
				saveErrors(request, messages);
				actionForward = mapping.findForward(FORWARD_START);
			}
			catch (IllegalStateException e)
			{
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Файл шаблона не найден!",false));
				saveErrors(request, messages);
				actionForward = mapping.findForward(FORWARD_START);
            }
		}
		return actionForward;
	}

	private ActionMessages validate(EditTemplateDocumentForm frm, DocTemplate docTemplate) throws ParseException
	{
		ActionMessages msgs = new ActionMessages();
		if(frm.getTemplate().getFileSize()!=0 || docTemplate == null)
			msgs = FileSizeLimitValidator.validate(frm.getTemplate(), MAX_FILE_SIZE);

		if(frm.getTemplate().getFileSize()!=0)
		{
			String fileName = new String((String)frm.getField("fileName"));
			String fileType = new String((String)frm.getField("fileType"));
			if (!fileName.toUpperCase().endsWith(fileType))
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Неправильный тип файла", false));
			}
		}
		// template создается, только при нажатии на кнопку "Обзор"
		// при выводе сообщения об ошибке template теряется
		else
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не найден", false));
		}

		return msgs;
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplateDocumentForm frm = (EditTemplateDocumentForm) form;
		EditTemplateDocumentOperation operation = (EditTemplateDocumentOperation) createEditOperation(frm);
		DocTemplate docTemplate = operation.getEntity();
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(docTemplate.getData());

			response.setContentLength(buffer.size());

			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment; filename=template.doc");
			response.setHeader("cache-control", "");

			ServletOutputStream outStream = response.getOutputStream();
			buffer.writeTo(outStream);
			outStream.flush();
			outStream.close();
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		addLogParameters(new BeanLogParemetersReader("Выгружаемая сущность", docTemplate));

		return mapping.findForward(FORWARD_START);
	}
}
