package com.rssl.phizic.web.common.socialApi.finances.targets;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.profile.images.ImageService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.targets.EditAccountTargetOperation;
import com.rssl.phizic.operations.finances.targets.RemoveAccountTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @version 01.10.13 10:32
 */
public class TargetsEditMobileAction extends EditActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keys = new HashMap<String, String>();
		keys.put("save",   "save");
		keys.put("remove", "remove");
		return keys;
	}

	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditAccountTargetOperation.class);
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		AccountTarget target = (AccountTarget) entity;
		target.setNameComment((String) data.get("comment"));
		target.setAmount(new Money((BigDecimal) data.get("amount"), MoneyUtil.getNationalCurrency()));
		target.setPlannedDate(DateHelper.toCalendar((Date) data.get("date")));

		if(TargetType.OTHER == target.getDictionaryTarget())
		{
			target.setName((String) data.get("name"));
		}
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditAccountTargetOperation operation = (EditAccountTargetOperation)	editOperation;
		operation.setRemoveImage((Boolean)validationResult.get("clearImage"));
	}

	@Override
	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation o) throws Exception
	{
		TargetsEditMobileForm form   = (TargetsEditMobileForm) frm;
		Map<String, String>   source = new HashMap<String, String>();

		source.put("comment", form.getComment());
		source.put("date",    form.getDate());
		source.put("amount",  form.getAmount());
		source.put("name",    form.getName());
		source.put("clearImage", form.getClearImage());
		source.putAll(getAdditionalFormProcessorValueSource(frm, o));

		return new MapValuesSource(source);
	}

	protected Map<? extends String, ? extends String> getAdditionalFormProcessorValueSource(EditFormBase frm, EditEntityOperation o) throws Exception
	{
		Map<String, String> source = new HashMap<String, String>();
		source.put("type", ((EditAccountTargetOperation) o).getEntity().getDictionaryTarget().name());
		return source;
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		TargetsEditMobileForm form = (TargetsEditMobileForm)frm;
		if (frm.getId() == null)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Введите значение в поле Идентификатор цели", false));
		}
		else
		{
			EditAccountTargetOperation op = (EditAccountTargetOperation) operation;

			HttpServletRequest request = currentRequest();
			//кривой депрекейшен
			//noinspection deprecation
			if (ServletFileUpload.isMultipartContent(request))
			{
				FormFile file = form.getFile();
				op.initialize(form.getId(), file.getFileData(), ImageService.getFileExtention(file.getFileName()));
			}
			else
			{
				op.initializeById(form.getId());
			}
		}

		return actionMessages;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return TargetsEditMobileForm.EDIT_FORM;
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	/**
	 * Удалить цель
	 * @param mapping маппинг
	 * @param frm форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public final ActionForward remove(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TargetsEditMobileForm form = (TargetsEditMobileForm) frm;

		RemoveAccountTargetOperation operation = createOperation(RemoveAccountTargetOperation.class);
		operation.initialize(form.getId());
		operation.remove();

		return getCurrentMapping().findForward(FORWARD_START);
	}
}
