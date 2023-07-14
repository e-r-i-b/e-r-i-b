package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailState;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.DownloadClientMailAttachOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.mail.EditMailAction;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр, редактирование, сохранение, отправка письма. Получение вложения.
 * @author Dorzhinov
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMailMobileAction extends EditMailAction
{
	private static final String FORWARD_DOWNLOAD = "Download";

	protected Map<String, String> getAdditionalKeyMethodMap()
    {
	    Map<String, String> map = super.getAdditionalKeyMethodMap();
	    map.put("download", "download");
	    map.put("save", "save");
	    map.put("send", "send");
	    return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		EditMailMobileForm frm = (EditMailMobileForm) form;
		Long id = frm.getId();
		if (id == null) //первое сохранение
		{
			Long parentId = frm.getParentId();
			if (parentId == null) //не ответ
				operation.createNewMail();
			else //ответ
				operation.createReply(parentId);
		}
		else //работа с уже сохраненным письмом
		{
			operation.initialize(operation.createView(id));
			Mail mail = operation.getEntity();
			//если совершается попытка сохранить или отправить уже отправленное письмо
			if (frm.getNewMailState() != null && mail.getState() == MailState.NEW)
				throw new BusinessLogicException("Письмо уже отправлено!");

		}
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		Mail mail = (Mail) entity;
		frm.setId(mail.getId());
	}

	/**
	 * Сохранение
	 */
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailMobileForm form = (EditMailMobileForm) frm;
		form.setNewMailState(MailState.CLIENT_DRAFT.name());
		return super.save(mapping, form, request, response);
	}

	/**
	 * Отправка
	 */
	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailMobileForm form = (EditMailMobileForm) frm;
		form.setNewMailState(MailState.NEW.name());
		return super.save(mapping, form, request, response);
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditMailMobileForm form = (EditMailMobileForm) frm;
		Map<String,Object> fields = new HashMap<String,Object>();
		fields.put("newMailState", form.getNewMailState()); //сохраняемый статус
		fields.put("mailState", form.getNewMailState()); //текущий статус. В действительности не нужен, но заполняем, чтобы заглушить валидатор
		fields.put("type", form.getType());
		fields.put("mail_theme", form.getThemeId());
		fields.put("response_method", form.getResponseMethod());
		fields.put("phone", form.getPhone());
		fields.put("email", form.geteMail());
		fields.put("secondEMail", form.geteMail());
		fields.put("subject", form.getSubject());
		if (form.getParentId() == null) //не ответ
		{
			fields.put("body", form.getBody());
			fields.put("newText", "E"); //заглушаем валидатор
		}
		else //ответ
		{
			fields.put("newText", form.getBody());
			fields.put("body", "E"); //заглушаем валидатор
		}
		return new MapValuesSource(fields);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase editForm, EditEntityOperation editOperation) throws Exception
	{
		EditClientMailOperation op = (EditClientMailOperation) editOperation;
		EditMailMobileForm form = (EditMailMobileForm) editForm;
		ActionMessages msgs = new ActionMessages();
		HttpServletRequest request = currentRequest();
		//request с файлом
		if (ServletFileUpload.isMultipartContent(request))
		{
			FormFile attach = form.getAttach();
			//файл не прикреплен
			int mailAttachSize = ConfigFactory.getConfig(MailConfig.class).getMailAttachSize();
			if (attach == null || attach.getFileSize() <= 0)
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Файл не прикреплен.", false));
			//файл превышает максимально допустимый размер
			else if (attach.getFileSize() > mailAttachSize)
				msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage("Размер файла должен быть не более " +
						mailAttachSize/1024 + " КБайт.", false));
			//есть файл, нет имени файла => такого быть не должно
			else if (StringHelper.isEmpty(form.getFileName()))
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Не указано имя файла.", false));
		}
		//request без файла
		else
		{
			String fileName = form.getFileName();
			String fileNameOld = op.getEntity().getFileName();
			//нет файла, есть имя файла => файл остается без изменений, но нужно проверить не изменилось ли название файла
			if (StringHelper.isNotEmpty(fileName))
			{
				if (StringHelper.isEmpty(fileNameOld))
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Указано имя файла, но файл не прикреплен.", false));
				else if (!fileName.equals(fileNameOld))
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Имя файла не совпадает с сохраненным.", false));
			}
		}
		return msgs;
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException
	{
		EditClientMailOperation op = (EditClientMailOperation) editOperation;
		EditMailMobileForm form = (EditMailMobileForm) editForm;
		op.setTheme(form.getThemeId());
		op.markParentReceived();
		op.updateSubject();
		//Прикрепленный файл
		try {
			String fileName = form.getFileName();
			FormFile attach = form.getAttach();
			//есть файл, есть имя файла => обновление файла
			if (StringHelper.isNotEmpty(fileName) && attach != null)
				op.updateFile(attach.getFileData(), attach.getFileName());
			//нет файла, нет имени файла => удаление файла
			else if (StringHelper.isEmpty(fileName) && attach == null)
				op.updateFile(null, null);
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditClientMailOperation op = (EditClientMailOperation) operation;
		//заполняем форму, чтобы отправить в приложение сохраненное письмо
		updateForm(frm, op.getEntity());
		return getCurrentMapping().findForward(FORWARD_START);
	}

	/**
	 * Получение вложения
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		DownloadClientMailAttachOperation op = createOperation(DownloadClientMailAttachOperation.class);
		EditMailMobileForm frm = (EditMailMobileForm) form;

		op.initialize(frm.getId());
		
		if (StringHelper.isEmpty(op.getFileName()) || ArrayUtils.isEmpty(op.getData()))
			saveError(request, "Не установлено имя файла или отсутствует файл.");
		else
			frm.setFileOutput(new String(Base64.encodeBase64(op.getData())));

		return mapping.findForward(FORWARD_DOWNLOAD);
	}
}
