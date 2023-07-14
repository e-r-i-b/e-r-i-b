package com.rssl.phizic.web.messageTranslate;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.translateMessages.LogType;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.messageTranslate.MessageTranslateEditOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateEditAction extends EditActionBase
{
	 protected static final String CSA_FORWARD_SUCCESS = "SuccessCSA";
	/**
	 * —оздать и проинициализировать операцию (операци€ редактировани€).
	 * @param frm форма
	 * @return созданна€ операци€.
	 */
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		MessageTranslateEditOperation operation;
		MessageTranslateEditForm form = (MessageTranslateEditForm)frm;
		if(form.getIsCSA())
			operation = createOperation("MessageTranslateCSAEditOperation", "CSAMesageDictionaryManagement");
		else
			operation = createOperation("MessageTranslateEditOperation", "SettingMessageTranslate");
		operation.initialize(frm.getId());
		return operation;
	}

	/**
	 * ¬ернуть форму редактировани€.
	 * ¬ большинстве случаев(до исправлени€ ENH00319) будет возвращатьс€ frm.EDIT_FORM
	 * @param frm struts-форма
	 * @return форма редактировани€
	 */
	protected Form getEditForm(EditFormBase frm)
	{
		return MessageTranslateEditForm.EDIT_MESSAGE_TRANSLATE_FORM;
	}

	/**
	 * ќбновить сужность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		MessageTranslate messageTranslate = (MessageTranslate) entity;
		messageTranslate.setCode((String) data.get("code"));
		messageTranslate.setTranslate((String) data.get("translate"));
		messageTranslate.setType(TypeMessageTranslate.valueOf((String) data.get("type")));
		messageTranslate.setIsNew((Boolean)data.get("isNew"));
		messageTranslate.setLogType(data.get("logType").equals(LogType.financial.toString()) ? LogType.financial : LogType.nonFinancial);
	}

	/**
	 * ѕроинициализировать/обновить struts-форму
	 * @param frm форма дл€ обновлени€
	 * @param entity объект дл€ обновлени€.
	 */
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		MessageTranslate messageTranslate = (MessageTranslate) entity;
		if (messageTranslate == null)
			return;
		
		frm.setField("code", messageTranslate.getCode());
		frm.setField("translate", messageTranslate.getTranslate());
		frm.setField("type", messageTranslate.getType() == null ? null : messageTranslate.getType().name());
		frm.setField("isNew", messageTranslate.getIsNew());
		frm.setField("logType", messageTranslate.getLogType() == null ? LogType.nonFinancial : messageTranslate.getLogType());

	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		MessageTranslateEditForm form = (MessageTranslateEditForm)frm;
		if(form.getIsCSA())
			return getCurrentMapping().findForward(CSA_FORWARD_SUCCESS);
		else
			return getCurrentMapping().findForward(FORWARD_SUCCESS);
	}

}
