package com.rssl.phizic.web.quick.pay;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.quick.pay.*;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.quick.pay.EditQuickPaymentPanelOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import com.rssl.phizic.web.util.ServiceProviderUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.*;

/**
 * Редактирование/создание панели быстрой оплаты.
 * @author komarov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditQuickPaymentPanelAction  extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditQuickPaymentPanelOperation editOperation = createOperation(EditQuickPaymentPanelOperation.class, "QuickPaymentPanelService");
		Long id = frm.getId();
		if(id != null && id != 0)
		{
			editOperation.initialize(id);
			return editOperation;
		}
		editOperation.initialize();
		return editOperation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditQuickPaymentPanelForm)frm).createForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		QuickPaymentPanel panel = (QuickPaymentPanel) entity;

		Calendar periodFrom = DateHelper.createCalendar(data.get("periodFrom"), null);
		Calendar periodTo = DateHelper.createCalendar(data.get("periodTo"), null);
		if (periodTo != null)
		{
			//Дата окончания периода отображения (до 23:59:59)
			periodTo.setTime(DateUtils.addDays(periodTo.getTime(),1));
			periodTo.setTime(DateUtils.truncate(periodTo.getTime(),Calendar.DATE));
			periodTo.setTime(DateUtils.addSeconds(periodTo.getTime(),-1));
		}

		panel.setState(QuickPaymentPanelState.valueOf((String)data.get("state")));
		panel.setName((String)data.get("name"));
		panel.setPeriodFrom(periodFrom);
		panel.setPeriodTo(periodTo);

	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
		EditQuickPaymentPanelOperation op = (EditQuickPaymentPanelOperation) editOperation;
		EditQuickPaymentPanelForm form = (EditQuickPaymentPanelForm) editForm;
		QuickPaymentPanel panel = op.getEntity();
		List<PanelBlock> blocks = panel.getPanelBlocks();

		op.setDepartments(form.getSelectedIds());
		op.clearProviders();
		Long[] orders = form.getOrderIds();
		for(int i = 0; i < orders.length; i++ )
		{
			String providerId = (String)form.getField("providerId"+orders[i]);
			if(StringHelper.isNotEmpty(providerId))
			{
				PanelBlock block = new PanelBlock();
				block.setProviderId(Long.parseLong(providerId));
				block.setShow(BooleanUtils.toBoolean((String)form.getField("providerShow"+orders[i])));
				block.setOrder(Long.parseLong((String)form.getField("orderIndex"+orders[i])));
				block.setProviderName((String)form.getField("providerAlias"+orders[i]));
				block.setShowName(BooleanUtils.toBoolean((String)form.getField("providerNameShow"+orders[i])));
				block.setProviderFieldName((String)form.getField("providerField"+orders[i]));
				block.setProviderFieldAmount((String)form.getField("providerFieldSumm"+orders[i]));
				block.setSumm(new BigDecimalParser().parse((String)form.getField("summ"+orders[i])));
				blocks.add(block);
			}
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditQuickPaymentPanelForm form =  (EditQuickPaymentPanelForm) frm;
		QuickPaymentPanel panel = (QuickPaymentPanel) entity;

		Date periodFrom = DateHelper.toDate(panel.getPeriodFrom());
		if (periodFrom == null)
			periodFrom =  DateHelper.toDate(DateHelper.getCurrentDate());
		Date periodTo =   DateHelper.toDate(panel.getPeriodTo());

		form.setField("state",       panel.getState().name());
		form.setField("name",        panel.getName());
		form.setField("periodFrom",  periodFrom);
		form.setField("periodTo",    periodTo);
		form.setField(EditQuickPaymentPanelForm.USE_DEFAULT_PROVIDER_IMAGE + 0, true);

		List<PanelBlock> blocks = panel.getPanelBlocks();
		form.setDepartments(panel.getDepartments());
		form.setPanelBlocks(blocks);
		Long[] orders = new Long[blocks.size()];
		int num = 0;
		for(PanelBlock block : blocks)
		{

			//Нулевой блок не заполнен, но он должен оставаться нулевым.
			if(StringHelper.isEmpty(block.getProviderFieldName()) && num == 0)
			{				
				orders = new Long[blocks.size()+1];
				orders[0] = 0L;
				num++;
			}

			form.setField("providerId"+num,        block.getProviderId());
			form.setField("providerShow"+num,      block.isShow());
			form.setField("orderIndex"+num,        block.getOrder());
			form.setField("providerName"+num,      ServiceProviderUtil.getProviderName(block.getProviderId()));
			form.setField("providerNameShow"+num,  block.isShowName());
			form.setField("providerAlias"+num,     block.getProviderName());
			form.setField("providerImage"+num,     block.getImage());
			form.setField("providerImageId"+num,   block.getImage() == null ? null : block.getImage().getId());
			form.setField("providerField"+num,     block.getProviderFieldName());
			form.setField("providerFieldSumm"+num, block.getProviderFieldAmount());
			form.setField("summ"+num,              block.getSumm());
			form.setField(EditQuickPaymentPanelForm.USE_DEFAULT_PROVIDER_IMAGE + num, block.getImage()==null);
			orders[num] = Long.valueOf(num);
			num++;
		}
		form.setField("blocksCount", orders.length == 0 ? 1 : orders.length);
		form.setOrderIds(orders);		
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
		EditQuickPaymentPanelForm form =  (EditQuickPaymentPanelForm) frm;
		EditQuickPaymentPanelOperation op = (EditQuickPaymentPanelOperation) operation;
		if(!frm.isFromStart())
		{
			form.setDepartments(op.getDepartments(form.getSelectedIds()));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
	    EditQuickPaymentPanelForm form    = (EditQuickPaymentPanelForm) frm;
		ActionMessages msgs               = validateImageFormData(frm, operation);
		EditQuickPaymentPanelOperation op = (EditQuickPaymentPanelOperation) operation;

		if(!msgs.isEmpty())
			return msgs;

		//Проверяем добавлены ли департаменты.
		if(ArrayUtils.isEmpty(form.getSelectedIds()) || op.getDepartments(form.getSelectedIds()).isEmpty())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Не добавлен ни один ТБ!", false));
			return msgs;
		}

		Long[] orders = form.getOrderIds();
		if(orders.length <= 0)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Пожалуйста, выберите поставщика, иконка которого будет отображаться на панели!", false));
			return msgs;
		}
		//Блок оплаты всех мобильных не отображаем и он единственный.
		if(!BooleanUtils.toBoolean((String)form.getField("providerShow0")) && orders.length == 1)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Пожалуйста, выберите поставщика, иконка которого будет отображаться на панели!", false));
			return msgs;
		}
		
		Set<String> providerAliases = new HashSet<String>();
		for(int i = 0; i < orders.length; i++ )
		{
			if ( StringHelper.isNotEmpty((String)form.getField("providerAlias"+orders[i])) &&
				 !providerAliases.add(   (String)form.getField("providerAlias"+orders[i])))
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Пожалуйста, укажите разные названия иконок для отображения на панели.", false));
				return msgs;
			}
		}
		return msgs;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditQuickPaymentPanelOperation op = (EditQuickPaymentPanelOperation) operation;
		EditQuickPaymentPanelForm form    = (EditQuickPaymentPanelForm) frm;
		op.setImageIds(form.getOrderIds());
		return super.doSave(operation, mapping, frm);

	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditQuickPaymentPanelOperation editOperation = (EditQuickPaymentPanelOperation) operation;
		QuickPaymentPanel panel =  editOperation.getEntity();
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
		return  new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() +"?id=" + panel.getId(), true);
	}

}
