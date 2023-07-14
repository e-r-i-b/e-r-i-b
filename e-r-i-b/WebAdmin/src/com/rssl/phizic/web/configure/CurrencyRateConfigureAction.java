package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.CurrencyRateConfigureOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Экшен просмотра/редактирования настроек тарифных планов курсов валют
 *
 * @ author: Gololobov
 * @ created: 21.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateConfigureAction extends EditActionBase
{
	private static final String INFO_MESSAGE = "Сообщение, отображаемое при использовании льготного курса для тарифного плана «%s»";
	private static final int MAX_LENGTH_MESSAGE = 100;
	private static final String LENGTH_MESSAGE_ERROR = INFO_MESSAGE+" должно быть не более "+MAX_LENGTH_MESSAGE+" символов.";
	private static final Pattern pattern = Pattern.compile("[А-я,0-9,\\s]");

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CurrencyRateConfigureOperation operation = createOperation(CurrencyRateConfigureOperation.class);
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return CurrencyRatesConfigureForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		CurrencyRatesConfigureForm form = (CurrencyRatesConfigureForm) frm;
		List<TariffPlanConfig> tarifPlanConfigList = (List<TariffPlanConfig>) entity;
		form.setTarifPlanConfigList(tarifPlanConfigList);
		for (TariffPlanConfig tarifPlanConfig : tarifPlanConfigList)
		{
			String tarifPlanCodeType = tarifPlanConfig.getCode();
			if (tarifPlanCodeType != null)
			{
				form.setField("needShowStandartRate_"+tarifPlanCodeType, tarifPlanConfig.isNeedShowStandartRate() ? "1" : "0");
				String privilegedRateMessage = tarifPlanConfig.getPrivilegedRateMessage();
				form.setField("privilegedRate_"+tarifPlanCodeType, StringHelper.isNotEmpty(privilegedRateMessage) ? "1" : "0");
				form.setField("privilegedRateMessage_"+tarifPlanCodeType, privilegedRateMessage);
			}
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		CurrencyRateConfigureOperation operation = (CurrencyRateConfigureOperation) editOperation;
		List<TariffPlanConfig> tarifPlanConfigInitList = (List<TariffPlanConfig>) operation.getEntity();

		for (TariffPlanConfig tarifPlanConfig : tarifPlanConfigInitList)
		{
			String tarifPlanCodeType = tarifPlanConfig.getCode();
			String needShowStandartRate = (String) editForm.getField("needShowStandartRate_"+tarifPlanCodeType);
			tarifPlanConfig.setNeedShowStandartRate(needShowStandartRate.equals("1"));
			String needShowMessage = (String) editForm.getField("privilegedRate_"+tarifPlanCodeType);

			String privilegedRateMessage = (String) editForm.getField("privilegedRateMessage_"+tarifPlanCodeType);
			tarifPlanConfig.setPrivilegedRateMessage(needShowMessage.equals("1") ?
					privilegedRateMessage : null);
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		//Чтобы явно не привязываться к тарифному плану валидация сделана тут а не через FormProcessor
		//Все тарифные платы, которые настраиваются в админке, перечислены в CurrencyRateConfigureOperation
		ActionMessages msgs = super.validateAdditionalFormData(frm, operation);
		List<TariffPlanConfig> tarifPlanConfigInitList = (List<TariffPlanConfig>) operation.getEntity();
		for (TariffPlanConfig tarifPlanConfig : tarifPlanConfigInitList)
		{
			String tarifPlanCodeType = tarifPlanConfig.getCode();
			String needShowMessage = (String) frm.getField("privilegedRate_"+tarifPlanCodeType);
			String message = (String) frm.getField("privilegedRateMessage_"+tarifPlanCodeType);

			if (needShowMessage.equals("1"))
			{	if (StringHelper.isEmpty(message))
					msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Не указано "+String.format(INFO_MESSAGE.toLowerCase(), tarifPlanCodeType), false));
				else
				{
					Matcher matcher = pattern.matcher(message);
					long i = 1;
					long messageLength = 0;
					while (matcher.find())
					{
						messageLength=i++;
					}
					if (messageLength > MAX_LENGTH_MESSAGE)
						msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage(String.format(LENGTH_MESSAGE_ERROR, tarifPlanCodeType), false));
				}
			}
		}
		return msgs;
	}
}