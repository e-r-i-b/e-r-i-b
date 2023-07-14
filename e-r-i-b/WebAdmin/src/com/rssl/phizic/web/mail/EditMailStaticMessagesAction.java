package com.rssl.phizic.web.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.EditMailStaticMessagesOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import static com.rssl.phizic.web.mail.EditMailStaticMessagesForm.*;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import com.rssl.phizic.web.validators.ImageSizeValidator;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditMailStaticMessagesAction extends EditActionBase
{
	private static final int FILE_SIZE = 30;
	protected static final String FORWARD_LIST = "List";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();

		map.put("button.cancel", "cancel");
		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditMailStaticMessagesOperation operation = createOperation(EditMailStaticMessagesOperation.class, "MailMessagesManagment");
        operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FORM;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation oper) throws Exception
	{
		ActionMessages messgs = new ActionMessages();
		String formText = (String) frm.getField(FORM_TEXT_FIELD);
		EditMailStaticMessagesOperation operation = (EditMailStaticMessagesOperation) oper;

		//1. проверяем существуют ли картинки, идентификаторы которых указаны в тексте (../images.do?id=XXX)
		List<String> notAttachedImagesIds = operation.getNotAttachedImagesIds(formText);
		if (!notAttachedImagesIds.isEmpty())
		{
			ActionMessage message = new ActionMessage("validation.unknown-images", StringUtils.join(notAttachedImagesIds, ", "));
			messgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}

		//2. Сохраняем добавленные пользователем изображения в БД (приходится это делать здесь((()
		Map<String, byte[]> filesData = getValidFilesData(frm.getFields(), messgs);
		if (!filesData.isEmpty())
			formText = operation.saveNewImages(formText, filesData);

		frm.setField(FORM_TEXT_FIELD, formText);
		
		return messgs;
	}

	private Map<String, byte[]> getValidFilesData(Map<String, Object> fields, ActionMessages messages) throws BusinessException
	{
		Map<String, byte[]> result = new HashMap<String, byte[]>();

		for (String fieldName : fields.keySet())
		{
			if (fieldName.startsWith("image"))
			{
				FormFile file = (FormFile) fields.get(fieldName);
				if (StringHelper.isEmpty(file.getFileName()))
					continue;
				
				ActionMessages mess = new ActionMessages();
				mess.add(FileNotEmptyValidator.validate(file));
				mess.add(ImageSizeValidator.validate(file, FILE_SIZE, FILE_SIZE, CompareValidator.LESS_EQUAL,CompareValidator.LESS_EQUAL));
				if (!mess.isEmpty())
					messages.add(mess);
				else
				{
					try
					{
						result.put(file.getFileName(), file.getFileData());
					}
					catch (IOException ioe)
					{
						throw new BusinessException(ioe);
					}
				}
			}
		}
		return result;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Pair<StaticMessage, StaticMessage> messages = (Pair<StaticMessage, StaticMessage>) entity;
		StaticMessage formText = messages.getFirst();
		formText.setText((String) data.get(FORM_TEXT_FIELD));

		StaticMessage messageText = messages.getSecond();
		messageText.setText((String) data.get(MESSAGE_TEXT_FIELD));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Pair<StaticMessage, StaticMessage> pair = (Pair<StaticMessage, StaticMessage>) entity;
		frm.setField(FORM_TEXT_FIELD, pair.getFirst().getText());
		frm.setField(MESSAGE_TEXT_FIELD, pair.getSecond().getText());
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//удаляем все сохраненные при валидации картинки
		EditMailStaticMessagesForm frm = (EditMailStaticMessagesForm) form;
		EditMailStaticMessagesOperation operation = (EditMailStaticMessagesOperation) createEditOperation(frm);
		operation.removeExcessImages();

		return mapping.findForward(FORWARD_LIST);
	}
}
