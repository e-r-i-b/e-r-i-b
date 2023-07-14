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
 * ��������, ��������������, ����������, �������� ������. ��������� ��������.
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
		if (id == null) //������ ����������
		{
			Long parentId = frm.getParentId();
			if (parentId == null) //�� �����
				operation.createNewMail();
			else //�����
				operation.createReply(parentId);
		}
		else //������ � ��� ����������� �������
		{
			operation.initialize(operation.createView(id));
			Mail mail = operation.getEntity();
			//���� ����������� ������� ��������� ��� ��������� ��� ������������ ������
			if (frm.getNewMailState() != null && mail.getState() == MailState.NEW)
				throw new BusinessLogicException("������ ��� ����������!");

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
	 * ����������
	 */
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMailMobileForm form = (EditMailMobileForm) frm;
		form.setNewMailState(MailState.CLIENT_DRAFT.name());
		return super.save(mapping, form, request, response);
	}

	/**
	 * ��������
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
		fields.put("newMailState", form.getNewMailState()); //����������� ������
		fields.put("mailState", form.getNewMailState()); //������� ������. � ���������������� �� �����, �� ���������, ����� ��������� ���������
		fields.put("type", form.getType());
		fields.put("mail_theme", form.getThemeId());
		fields.put("response_method", form.getResponseMethod());
		fields.put("phone", form.getPhone());
		fields.put("email", form.geteMail());
		fields.put("secondEMail", form.geteMail());
		fields.put("subject", form.getSubject());
		if (form.getParentId() == null) //�� �����
		{
			fields.put("body", form.getBody());
			fields.put("newText", "E"); //��������� ���������
		}
		else //�����
		{
			fields.put("newText", form.getBody());
			fields.put("body", "E"); //��������� ���������
		}
		return new MapValuesSource(fields);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase editForm, EditEntityOperation editOperation) throws Exception
	{
		EditClientMailOperation op = (EditClientMailOperation) editOperation;
		EditMailMobileForm form = (EditMailMobileForm) editForm;
		ActionMessages msgs = new ActionMessages();
		HttpServletRequest request = currentRequest();
		//request � ������
		if (ServletFileUpload.isMultipartContent(request))
		{
			FormFile attach = form.getAttach();
			//���� �� ����������
			int mailAttachSize = ConfigFactory.getConfig(MailConfig.class).getMailAttachSize();
			if (attach == null || attach.getFileSize() <= 0)
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("���� �� ����������.", false));
			//���� ��������� ����������� ���������� ������
			else if (attach.getFileSize() > mailAttachSize)
				msgs.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage("������ ����� ������ ���� �� ����� " +
						mailAttachSize/1024 + " �����.", false));
			//���� ����, ��� ����� ����� => ������ ���� �� ������
			else if (StringHelper.isEmpty(form.getFileName()))
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�� ������� ��� �����.", false));
		}
		//request ��� �����
		else
		{
			String fileName = form.getFileName();
			String fileNameOld = op.getEntity().getFileName();
			//��� �����, ���� ��� ����� => ���� �������� ��� ���������, �� ����� ��������� �� ���������� �� �������� �����
			if (StringHelper.isNotEmpty(fileName))
			{
				if (StringHelper.isEmpty(fileNameOld))
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("������� ��� �����, �� ���� �� ����������.", false));
				else if (!fileName.equals(fileNameOld))
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("��� ����� �� ��������� � �����������.", false));
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
		//������������� ����
		try {
			String fileName = form.getFileName();
			FormFile attach = form.getAttach();
			//���� ����, ���� ��� ����� => ���������� �����
			if (StringHelper.isNotEmpty(fileName) && attach != null)
				op.updateFile(attach.getFileData(), attach.getFileName());
			//��� �����, ��� ����� ����� => �������� �����
			else if (StringHelper.isEmpty(fileName) && attach == null)
				op.updateFile(null, null);
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditClientMailOperation op = (EditClientMailOperation) operation;
		//��������� �����, ����� ��������� � ���������� ����������� ������
		updateForm(frm, op.getEntity());
		return getCurrentMapping().findForward(FORWARD_START);
	}

	/**
	 * ��������� ��������
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		DownloadClientMailAttachOperation op = createOperation(DownloadClientMailAttachOperation.class);
		EditMailMobileForm frm = (EditMailMobileForm) form;

		op.initialize(frm.getId());
		
		if (StringHelper.isEmpty(op.getFileName()) || ArrayUtils.isEmpty(op.getData()))
			saveError(request, "�� ����������� ��� ����� ��� ����������� ����.");
		else
			frm.setFileOutput(new String(Base64.encodeBase64(op.getData())));

		return mapping.findForward(FORWARD_DOWNLOAD);
	}
}
