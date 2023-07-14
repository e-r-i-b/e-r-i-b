package com.rssl.phizic.web.templates;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.DublicateBanksDocumentNameException;
import com.rssl.phizic.business.template.BanksDocument;
import com.rssl.phizic.operations.templates.EditBanksDocumentOperation;
import com.rssl.phizic.operations.templates.DownloadBankDocumentOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 16:43:49
 * To change this template use File | Settings | File Templates.
 */
public class EditBanksDocumentAction extends EditActionBase
{
    protected Map<String,String> getAdditionalKeyMethodMap()
    {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("button.doc.save", "save");
	    map.put("button.upload", "upload");

	    return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditBanksDocumentOperation operation = createOperation(EditBanksDocumentOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(frm.getId());
		else
			operation.initializeNew();

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditBanksDocumentForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
	 	BanksDocument document = (BanksDocument) entity;

		frm.setField("name", document.getName());
		frm.setField("description", document.getDescription());
		frm.setField("fileName", document.getFileName());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		BanksDocument document = (BanksDocument) entity;

		document.setName((String) data.get("name"));
		document.setFileName((String) data.get("fileName"));
		document.setDescription((String) data.get("description"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws IOException
	{
		EditBanksDocumentForm      frm = (EditBanksDocumentForm) form;
		EditBanksDocumentOperation op  = (EditBanksDocumentOperation) editOperation;
		BanksDocument document = op.getEntity();
		document.setData( (frm.getTemplate() != null) ? frm.getTemplate().getFileData() : null);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EditBanksDocumentForm frm  = (EditBanksDocumentForm) form;
		ActionMessages        msgs = new ActionMessages();
		if (validate(frm.getTemplate()) != null)
			msgs.add(ActionMessages.GLOBAL_MESSAGE, validate(frm.getTemplate()));

		return msgs;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch(DublicateBanksDocumentNameException ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Документ с таким именем уже существует",false));
			saveErrors(currentRequest(), msgs);

			return mapping.findForward(FORWARD_START);
		}
	    catch (IllegalStateException e)
		{
		    msgs.add(ActionMessages.GLOBAL_MESSAGE,
				   new ActionMessage("Файл не найден!",false));
			saveErrors(currentRequest(), msgs);

			return mapping.findForward(FORWARD_START);
		}
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBanksDocumentForm frm = (EditBanksDocumentForm)form;
		DownloadBankDocumentOperation operation = createOperation(DownloadBankDocumentOperation.class, "BankTemplatesManagement");
		operation.initialize(frm.getId());
		BanksDocument document = operation.getBanksDocument();
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(document.getData());

			response.setContentLength(buffer.size());

			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment; filename=" + document.getName() + ".doc");
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

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getBanksDocument()));

		return mapping.findForward(FORWARD_START);
	}

	private ActionMessage validate(FormFile file)
	{
	   ActionMessage msg = null;
	   String fileName = new String(file.getFileName());
	   if ( file == null || fileName.length() == 0 || file.getFileSize() == 0)
	   {
	      msg = new ActionMessage("Выберите файл", false);
	   }
	   return msg;
	}
}
