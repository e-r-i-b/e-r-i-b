package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.common.forms.Form;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.csaadmin.node.LastNodeInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.DownloadEmployeeMailAttachOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.SessionDataParameterAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.mail.ChooseClientAction;
import com.rssl.phizic.web.util.EmployeeInfoUtil;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.hibernate.exception.ConstraintViolationException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author kligina
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditMailAction extends SessionDataParameterAction
{
	private static final String SAVE_DRAFT        = "SAVE_DRAFT";
	private static final String SEND_DRAFT        = "SEND_DRAFT";
	private static final String CHANGE_NODE_FORWARD     = "ChangeNode";
	private static final String SELECT_CLIENT_FORWARD   = "SelectClient";

	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String FILE_NAME = "fileName"; //название выгружаемого клиенту файла
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //название временного файла

	protected Map<String,String> getAdditionalKeyMethodMap()
    {
	    Map<String, String> map = super.getAdditionalKeyMethodMap();
	    map.put("button.unload", "unload");
		map.put("download.file", "download");
	    return map;
    }

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditMailForm form = (EditMailForm) frm;
		EditMailOperation operation = createOperation(EditMailOperation.class, "MailManagment");
		operation.initialize(form.getId());
		form.setParentId(operation.getEntity().getParentId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		EditMailForm form = (EditMailForm) frm;
		if (form.getParentId() != null)
			return EditMailForm.getReplyForm();

		if (EmployeeInfoUtil.isMailMultiBlockMode())
			return EditMailForm.getCreateNewMailMultiBlockModeForm();

		return EditMailForm.getCreateNewMailSingleBlockModeForm();
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
		frm.setField("fileName", mail.getFileName());
		frm.setField("file", mail.getData());
		frm.setField("mailState", mail.getState());
		frm.setField("newText", "");
		frm.setField("isNotAnswer", mail.getParentId() == null);
		frm.setField("setNewFile", false);
		frm.setField("response_method", mail.getResponseMethod()==null?null:mail.getResponseMethod().getDescription());
		frm.setField("mail_theme", mail.getTheme()==null?null:mail.getTheme().getDescription());
		frm.setField("recipient", mail.getRecipientName());
		frm.setField("recipientId", mail.getRecipientId());

		//просматриваем черновик ответа
		if (mail.getState() == MailState.EMPLOYEE_DRAFT)
		{
			frm.setField("newText",  mail.getBody());
			frm.setField("fileName", mail.getFileName());
			frm.setField("file",     mail.getData());
		}
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EditMailForm frm = (EditMailForm)form;
		EditMailOperation op = (EditMailOperation) operation;
		Mail mail = op.getEntity();
		frm.setField("correspondence", op.getCorrespondence());

		//просматриваем пришедшее письмо
		if (!mail.getSender().equals(op.getLogin()))
		{
			frm.setField("sender", op.getPersonByLogin(mail.getSender()));
			return;
		}

		//если просматриваем отправленное письмо или ответ, то в качестве получателя отображается ссылка на клиента
		// заменяем recipientId на ИД клиента
		if (mail.getState() == MailState.NEW || mail.getParentId() != null)
			frm.setField("recipientId", op.getRecipientId());

		frm.setField("phone", op.getParentMailPhone());

		if (EmployeeInfoUtil.isMailMultiBlockMode() && mail.getState() == MailState.EMPLOYEE_DRAFT)
		{
			UserInfo recipientUserInfo = op.getRecipientUserInfo();
			updateUserInfo(frm, recipientUserInfo);
		}

		//"Ответ предоставлен по телефону" нужно когда мы (mail.getSender().equals(op.getLogin())) отвечаем (mail.getState() != MailState.NEW)
		if (mail.getSender().equals(op.getLogin()) && mail.getState() != MailState.NEW)
		{
			frm.setField("response_by_phone", String.format(ConfigFactory.getConfig(MailConfig.class).getAttntionByPhone(), mail.getRecipientName()));
			return;
		}

		//пришли сюда, значит просматриваем отправленное письмо
		super.updateFormAdditionalData(frm,operation);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase editForm, EditEntityOperation editOperation) throws Exception
	{
		ActionMessages msgs = new ActionMessages();	
		if(Boolean.valueOf((String)editForm.getField("setNewFile")))
		{
			try
			{
				int mailAttachSize = ConfigFactory.getConfig(MailConfig.class).getMailAttachSize();
				FormFile file = (FormFile) editForm.getField("file");
				byte[] data = file.getFileData();
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
			return MailState.EMPLOYEE_DRAFT;

		return MailState.valueOf(state);
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Mail mail = (Mail) entity;
		mail.setBody(mail.getParentId()==null ? (String)data.get("body") : (String)data.get("newText"));
		mail.setDirection(MailDirection.CLIENT);
		mail.setSubject((String) data.get("subject"));
		mail.setState(getMailState((String) data.get("newMailState")));
		mail.setType(MailType.valueOf((String)data.get("type")));
		//если отвечаем, то данные уже есть в письме 
		if (mail.getParentId() == null)
		{
			mail.setRecipientId((Long) data.get("recipientId"));
			mail.setRecipientName((String) data.get("recipient"));
			mail.setRecipientType(RecipientType.PERSON);
		}
	}

	private UserInfo getUserInfo(Map<String, Object> data)
	{
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstname((String) data.get("firstName"));
		userInfo.setPatrname((String) data.get("patrname"));
		userInfo.setSurname((String) data.get("surname"));
		userInfo.setBirthdate(DateHelper.toCalendar((Date) data.get("birthDate")));
		userInfo.setPassport((String) data.get("passport"));
		userInfo.setTb((String) data.get("tb"));
		return userInfo;
	}

	private void updateUserInfo(EditMailForm form, UserInfo userInfo)
	{
		if (userInfo == null)
			return;
		form.setField("firstName", userInfo.getFirstname());
		form.setField("patrname", userInfo.getPatrname());
		form.setField("surname", userInfo.getSurname());
		form.setField("passport", userInfo.getPassport());
		form.setField("birthDate", DateHelper.formatCalendar(userInfo.getBirthdate()));
		form.setField("tb", userInfo.getCbCode());
		form.setField("nodeId", getNodeNumber());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws Exception
	{
		EditMailForm frm = (EditMailForm)form;
		EditMailOperation op = (EditMailOperation) editOperation;
		Mail mail = op.getEntity();

		if(Boolean.valueOf((String)frm.getField("setNewFile")))
		{
			try
			{
				FormFile file = (FormFile)frm.getField("file");
				byte[] data = file.getFileData();
				mail.setData(data);
				mail.setFileName(file.getFileName());
			}
			catch (IOException e)
			{
				throw new IllegalStateException("Файл не найден!", e);
			}
		}

		op.setUserInfo(getUserInfo(validationResult));
		op.setNodeId((Long) validationResult.get("nodeId"));
	}

	protected Long getNodeNumber()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
	}

	private String getParameterName(String name, boolean isChangeNode)
	{
		String parameterName = "filter(" + name + ")";
		if (!isChangeNode)
			return parameterName;
		return "parameters(" + parameterName + ")";
	}

	private ActionForward getRedirect(EditMailOperation operation, String forwardName, Long nodeId)
	{
		UserInfo userInfo = operation.getUserInfo();
		UrlBuilder urlBuilder = new UrlBuilder();
		ActionForward forward = getCurrentMapping().findForward(forwardName);
		urlBuilder.setUrl(forward.getPath());

		boolean isChangeNode = nodeId != null;
		if (isChangeNode)
		{
			urlBuilder.addParameter("nodeId", nodeId.toString());
			urlBuilder.addParameter("action", getCurrentMapping().findForward(SELECT_CLIENT_FORWARD).getPath());
		}
		urlBuilder.addParameter(getParameterName("firstname",   isChangeNode), userInfo.getFirstname());
		urlBuilder.addParameter(getParameterName("surname",     isChangeNode), userInfo.getSurname());
		urlBuilder.addParameter(getParameterName("patrname",    isChangeNode), userInfo.getPatrname());
		urlBuilder.addParameter(getParameterName("birthDay",    isChangeNode), DateHelper.formatDateToStringWithPoint(userInfo.getBirthdate()));
		urlBuilder.addParameter(getParameterName("passport",    isChangeNode), userInfo.getPassport());
		urlBuilder.addParameter(getParameterName("tb",          isChangeNode), userInfo.getTb());
		return new ActionRedirect(urlBuilder.getUrl());
	}

	private ActionForward redirectToNode(EditMailOperation editMailOperation) throws BusinessException, BusinessLogicException
	{
		LastNodeInfoOperation operation = createOperation(LastNodeInfoOperation.class);
		operation.saveOperationContext(Collections.<String, Object>singletonMap(ChooseClientAction.MAIL_KEY, editMailOperation.getEntity()));
		editMailOperation.remove();
		return getRedirect(editMailOperation, CHANGE_NODE_FORWARD, editMailOperation.getNodeId());
	}

	protected void storeOperation(EditMailOperation operation)
	{
		StoreManager.getCurrentStore().save(ChooseClientAction.MAIL_KEY, operation.getEntity());
	}

	private ActionForward redirectToChooseClient(EditMailOperation operation)
	{
		storeOperation(operation);
		return getRedirect(operation, SELECT_CLIENT_FORWARD, null);
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditMailForm form = (EditMailForm) frm;
		EditMailOperation op = (EditMailOperation) operation;
		if (!EmployeeInfoUtil.isMailMultiBlockMode() || form.getParentId() != null)
			return doInBlockSave(op, form);

		if (getNodeNumber().equals(op.getNodeId()))
			return redirectToChooseClient(op);

		return redirectToNode(op);
	}

	protected ActionForward doInBlockSave(EditMailOperation op, EditFormBase frm) throws BusinessLogicException, BusinessException
	{
		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			op.save();
		}
		catch(ConstraintViolationException ignore)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Письмо не отправлено.", false), null);
			phizLog.error("Ошибка при сохранении письма", ignore);
			return createSaveActionForward(op, frm);
		}

		if(op.getEntity().getState() != MailState.EMPLOYEE_DRAFT)
			op.createNotification();

		return createSaveActionForward(op, frm);
	}

	protected DownloadEmployeeMailAttachOperation createDownloadOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		DownloadEmployeeMailAttachOperation operation = createOperation(DownloadEmployeeMailAttachOperation.class);
		Long id = frm.getId();
		operation.initialize(id);
		return  operation;
	}

	//TODO: придумать красивое решение для Action унаследованных не от OperationalActionBase.
	public Pair<String, byte[]> createData(ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailForm form = (EditMailForm) frm;
		DownloadEmployeeMailAttachOperation operation = createDownloadOperation(form);
		String fileName = new String(operation.getFileName().getBytes("Cp1251"), "Cp1252");
		return new Pair<String, byte[]>(fileName , operation.getData());
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
}

