package com.rssl.phizic.web.common.client.ext.sbrf.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.DownloadClientMailAttachOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.io.ObjectInputStreamWithLoader;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.hibernate.exception.ConstraintViolationException;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author kligina
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditMailAction extends EditActionBase
{
	private static final String ID_PARAMETER_NAME = "id";
	private static final String REPLY_FORWARD     = "Reply";
	private static final String SAVE_DRAFT        = "SAVE_DRAFT";
	private static final String SEND_DRAFT        = "SEND_DRAFT";

	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String FILE_NAME = "fileName"; //название выгружаемого клиенту файла
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //название временного файла

	protected Map<String,String> getAdditionalKeyMethodMap()
    {
	    Map<String, String> map = super.getAdditionalKeyMethodMap();
	    map.put("button.upload", "unload");
	    map.put("download.file", "download");
	    map.put("button.reply",  "reply");
	    return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		operation.initialize(form.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditMailForm.getForm();
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase editForm, EditEntityOperation editOperation) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		
		if(Boolean.valueOf((String)editForm.getField("setNewFile")))
		{
			try
			{
				FormFile file = (FormFile) editForm.getField("file");
				if(file == null)
					return msgs;				
				byte[] data = file.getFileData();
				int mailAttachSize = ConfigFactory.getConfig(MailConfig.class).getMailAttachSize();
				if (data!= null && data.length > mailAttachSize)
				{
		            msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage("Размер файла должен быть не более " +
				            mailAttachSize/1024 + " кб",false));
					editForm.setField("setNewFile", null);
					editForm.setField("fileName", null);
					editForm.setField("file", null);
				}
			}
			catch (IOException ignore)
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage("Файл не найден",false));
			}
		}
		return msgs;
	}

	private MailState getMailState(String state)
	{
		if (SEND_DRAFT.equals(state))
			return MailState.NEW;

		if (SAVE_DRAFT.equals(state))
			return MailState.CLIENT_DRAFT;

		return MailState.valueOf(state);
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Mail mail = (Mail) entity;
		mail.setState(getMailState((String) data.get("newMailState")));
		mail.setBody(mail.getParentId()==null ? (String)data.get("body") : (String)data.get("newText"));//parentId есть только при ответе на сообщение.
		mail.setDirection(MailDirection.ADMIN);
		mail.setSubject((String)data.get("subject"));
		mail.setType(MailType.valueOf((String)data.get("type")));
		mail.setResponseMethod(MailResponseMethod.valueOf((String)data.get("response_method")));
		if(mail.getResponseMethod() == MailResponseMethod.BY_PHONE)
		{
			mail.setPhone((String)data.get("phone"));
			mail.setEmail(null);
		}
		else
		{
			mail.setEmail((String)data.get("email"));
			mail.setPhone(null);
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws BusinessException
	{
		EditClientMailOperation op = (EditClientMailOperation) editOperation;
		op.setTheme((String)editForm.getField("mail_theme"));
		Mail mail = op.getEntity();
		if(Boolean.valueOf((String)editForm.getField("setNewFile")))
		{
			try
			{
				FormFile file = (FormFile) editForm.getField("file");
				byte[] data = file.getFileData();
				mail.setData(data);
				mail.setFileName(file.getFileName());
			}
			catch (IOException e)
			{
				throw new IllegalStateException("Файл не найден!", e);
			}
		}
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Mail mail = (Mail) entity;
		frm.setField("num", mail.getNum());
		frm.setField("type", mail.getType());
		frm.setField("subject", mail.getSubject());
		frm.setField("body", mail.getBody());
		frm.setField("phone", mail.getPhone());
		frm.setField("email", mail.getEmail());
		frm.setField("secondEMail", mail.getEmail());
		frm.setField("fileName", mail.getFileName());
		frm.setField("file", mail.getData());
		frm.setField("mailState", mail.getState());
		frm.setField("newText", "");
		frm.setField("isNotAnswer", mail.getParentId() == null);
		frm.setField("setNewFile", StringUtils.isEmpty(mail.getFileName()));
		frm.setField("response_method", mail.getResponseMethod());
		frm.setField("mail_theme", mail.getTheme() == null ? null : mail.getTheme().getId());
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditClientMailOperation op = (EditClientMailOperation) operation;
		Mail mail = op.getEntity();

		String phone = mail.getPhone() == null ? MobileBankManager.getMainPhoneForCurrentUser() : mail.getPhone();
		// телефон маскируется в SIMPLE_NUMBER формате, размаскировать нужно тоже в нем
		String phoneRes = (StringHelper.isEmpty(phone)? null :PhoneNumberFormat.SIMPLE_NUMBER.format(PhoneNumber.fromString(phone)));

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		if(MaskPaymentFieldUtils.isRequireMasking())
			// размаскируем значениями из сущности
			valuesSource = MaskPaymentFieldUtils.wrapUnmaskValuesSource(getEditForm(frm), valuesSource, new MapValuesSource(Collections.singletonMap("phone", phoneRes)));

		return valuesSource;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditMailForm frm = (EditMailForm)form;
		EditClientMailOperation op = (EditClientMailOperation) operation;
		Mail mail = op.getEntity();

		frm.setThemes(op.getAllThemes());

		String phone = mail.getPhone() == null ? MobileBankManager.getMainPhoneForCurrentUser() : mail.getPhone();

		FieldValuesSource valuesSource = new MapValuesSource(Collections.singletonMap("phone", phone));
		if(MaskPaymentFieldUtils.isRequireMasking())
			valuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getEditForm(frm), valuesSource);

		frm.setField("phone", valuesSource.getValue("phone"));
		frm.setMaskedField("phone", valuesSource.isMasked("phone"));

		//просматриваем черновик ответа
		if (mail.getState() == MailState.CLIENT_DRAFT)
		{
			frm.setField("newText",  mail.getBody());
			frm.setField("fileName", mail.getFileName());
			frm.setField("file",     mail.getData());
		}

		//получено от админа
		if (!mail.getSender().getId().equals(op.getLogin().getId()))
		{
			frm.setField("sender", mail.getSender());
		}

		frm.setField("correspondence", op.getCorrespondence());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (ConstraintViolationException ignore)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Письмо не отправлено.", false), null);
			return createSaveActionForward(operation, frm);
		}
	}

	protected DownloadClientMailAttachOperation createDownloadOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		DownloadClientMailAttachOperation operation = createOperation(DownloadClientMailAttachOperation.class);
		Long id = frm.getId();
		operation.initialize(id);
		return operation;
	}

	/**
	 * ответ на письмо
	 * @param mapping  маппинг
	 * @param form     форма
	 * @param request  реквест
	 * @param response респунс
	 * @return ActionForward
	 * @throws BusinessException
	 */
	public final ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailForm frm = (EditMailForm) form;
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(REPLY_FORWARD));
		redirect.addParameter(ID_PARAMETER_NAME, operation.createReply(frm.getId()));
		return redirect;
	}

	//TODO: придумать красивое решение для Action унаследованных не от OperationalActionBase.
	public Pair<String, byte[]> createData(ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailForm form = (EditMailForm) frm;
		DownloadClientMailAttachOperation operation = createDownloadOperation(form);
		String fileName = new String(operation.getFileName().getBytes("Cp1251"), "Cp1252");
		return new Pair<String, byte[]>(fileName , operation.getData());
	}

	protected void outData(byte[] data, ServletOutputStream outputStream) throws Exception
	{
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * Метод создает данные для выгрузки, сохраняет во временном файле
	 * и преоткрывает страницу выгрузки
	 * @param mapping стратс.маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард для обновления страницы после нажатия на кнопку выгрузить
	 */
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		phizLog.debug("1 Начало выгрузки");
		Pair<String, byte[]> data = createData(form, request, response);

		if (data != null)
		{
			phizLog.debug("2 Данные для выгрузки получены");
			Long loginId = AuthModule.getAuthModule().getPrincipal().getLogin().getId();
			//сохраняем файл во временной директории для последующей выгрузки
			String tmpFilePath = System.getProperty("java.io.tmpdir") + String.format(TMP_FILE_NAME, loginId.toString());
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(tmpFilePath));
			outputStream.writeObject(data.getSecond());
			outputStream.flush();
			outputStream.close();
			HttpSession appSession =  currentRequest().getSession();
			appSession.setAttribute(FILE_NAME, data.getFirst());
			appSession.setAttribute(TMP_FILE_NAME, tmpFilePath);

			((FilterActionForm)form).setField("relocateToDownload", true); //флаг о необходимости выгрузки данных
			phizLog.debug("3 Файл для выгрузки сохранен во временную директорию.");
		}
		else
		{
			phizLog.debug("999 Не удалось получить данные для выгрузки");
		}
		return start(mapping, form, request, response);
	}

	/**
	 * Кидает в response данные для выгрузки.
	 * Метод должен быть выполнен после перезагрузки страницы.
	 * Для этого нобходимо на jsp после загрузки страницы если
	 * флаг relocateToDownload=true выполнить window.location на данный метод.
	 * @return при успешно результате (т.е. файл был выгружен) метод должен возвращать null,
	 *         иначе форму выгрузки с сообщением об ошибке
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		phizLog.debug("5 Начат процесс зыгрузки.");
		String filePath = HttpSessionUtils.getSessionAttribute(request, TMP_FILE_NAME);
		File file = null;
		ObjectInputStream inputStream = null;
		byte[] data;
		try
		{
			file = new File(filePath);
			if (!file.exists())
			{
				phizLog.debug("9999 Нет файла для выгрузки.");
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Произошла ошибка при выгрузке файла", false));
				saveErrors(request, msgs);
				return mapping.findForward(FORWARD_START);
			}

            inputStream = new ObjectInputStreamWithLoader(new FileInputStream(file), Thread.currentThread().getContextClassLoader());
			data = (byte[]) inputStream.readObject();
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
			if (!file.delete())
			{
				log.info("Не удалось удалить временный файл выгрузки ( "+ file.getName()+" ).");
			}
		}

		if (data != null)
		{
			phizLog.debug("6 данные для выгрузки получены.");
			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename=" + HttpSessionUtils.getSessionAttribute(request, FILE_NAME));
			response.setHeader("Pragma", null);
			response.setHeader("Cache-Control", null);

			outData(data, response.getOutputStream());

			phizLog.debug("7 данные для выгрузки успешно отправлены.");
			HttpSessionUtils.removeSessionAttribute(request, FILE_NAME);
			HttpSessionUtils.removeSessionAttribute(request, TMP_FILE_NAME);
		}
		else
		{
			phizLog.debug("99999 нет данных для выгрузки (data is null).");
		}

		return null;
	}
}
