package com.rssl.phizic.web.deposits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.deposits.PromoCodeDepositOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * Экшен просмотра/редактирования настроек промо - кодов для открытия вкладов
 *
 * @ author: Gololobov
 * @ created: 12.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositSettingsAction extends EditActionBase
{
	private static final int TITLE_FIELD_LENGTH = 150;
	private static final int TEXT_FIELD_LENGTH = 400;

	private static final String FIELD_LENGTH_ERROR = "Длина поля \"Заголовок\" для сообщения \"%s\" не должно превышать %s символов.";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PromoCodeDepositOperation operation = createOperation(PromoCodeDepositOperation.class,"PromoCodeDepositSettingsService");
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		PromoCodeDepositSettingsForm form = (PromoCodeDepositSettingsForm) frm;
		return form.getForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		PromoCodesDepositConfig promoCodesDepositConfig = (PromoCodesDepositConfig) editOperation.getEntity();
		//Обновление общих для промокодов настроек
		promoCodesDepositConfig.setAccessibleSymbols((String) validationResult.get(PromoCodesDepositConfig.ACCESSIBLE_SYMBOLS));
		promoCodesDepositConfig.setMinCountSymbols((Integer) validationResult.get(PromoCodesDepositConfig.MIN_COUNT_SYMBOLS));
		promoCodesDepositConfig.setMaxCountSymbols((Integer) validationResult.get(PromoCodesDepositConfig.MAX_COUNT_SYMBOLS));
        Integer maxUnsuccessfullIterations = validationResult.get(PromoCodesDepositConfig.MAX_UNSUCCESSFULL_ITERATIONS) == null ? null : (Integer) validationResult.get(PromoCodesDepositConfig.MAX_UNSUCCESSFULL_ITERATIONS);
		promoCodesDepositConfig.setMaxUnsuccessfulIteration(maxUnsuccessfullIterations);
        Integer blockingTimeMinutes = validationResult.get(PromoCodesDepositConfig.BLOCKING_TIMEMINUTES) == null ? null : (Integer) validationResult.get(PromoCodesDepositConfig.BLOCKING_TIMEMINUTES);
		promoCodesDepositConfig.setBlockingTimeMinutes(blockingTimeMinutes);

		//Обновление сообщений для промо - кодов
		Map<String, PromoCodesMessage> messagesMap        = promoCodesDepositConfig.getPromoCodesMessagesMap();
		Map<String, PromoCodesMessage> defaultMessagesMap = promoCodesDepositConfig.getDefaultPromoCodesMessagesMap();
		if (MapUtils.isEmpty(messagesMap))
			return;

		for (String key : messagesMap.keySet())
		{
			PromoCodesMessage promoCodesMessage = messagesMap.get(key);
			String messageTitle = (String) editForm.getField(String.format(PromoCodesDepositConfig.PROMO_CODES_MESSAGE_TITLE, key));
			String messageText  = (String) editForm.getField(String.format(PromoCodesDepositConfig.PROMO_CODES_MESSAGE_TEXT, key));
			//Обновляются не все поля настроек сообщения
			//Заголовок сообщения
			promoCodesMessage.setTitle(StringHelper.isEmpty(messageTitle) ? defaultMessagesMap.get(key).getTitle() : messageTitle);
			//Текст сообщения
			promoCodesMessage.setText(StringHelper.isEmpty(messageText) ? defaultMessagesMap.get(key).getText() : messageText);
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		PromoCodeDepositSettingsForm form = (PromoCodeDepositSettingsForm) frm;
		PromoCodesDepositConfig promoCodesConfig = (PromoCodesDepositConfig) entity;
		if (promoCodesConfig == null)
			return;

		form.setField(promoCodesConfig.ACCESSIBLE_SYMBOLS, promoCodesConfig.getAccessibleSymbols());
		form.setField(promoCodesConfig.MIN_COUNT_SYMBOLS, promoCodesConfig.getMinCountSymbols());
		form.setField(promoCodesConfig.MAX_COUNT_SYMBOLS, promoCodesConfig.getMaxCountSymbols());
		form.setField(promoCodesConfig.MAX_UNSUCCESSFULL_ITERATIONS, promoCodesConfig.getMaxUnsuccessfulIteration());
		form.setField(promoCodesConfig.BLOCKING_TIMEMINUTES, promoCodesConfig.getBlockingTimeMinutes());
	}

	/**
	 * Заполнение данных по сообщениям промо-кодов
	 * @param promoCodesConfig
	 */
	private void fillPromoCodesMessages(PromoCodeDepositSettingsForm form, PromoCodesDepositConfig promoCodesConfig)
	{
		if (promoCodesConfig == null || CollectionUtils.isEmpty(promoCodesConfig.getPromoCodesMessagesMap().values()))
			return;
		Map<String, PromoCodesMessage> messagesMap        = promoCodesConfig.getPromoCodesMessagesMap();
		Map<String, PromoCodesMessage> defaultMessagesMap = promoCodesConfig.getDefaultPromoCodesMessagesMap();
		for (String key : messagesMap.keySet())
		{
			PromoCodesMessage promoCodesMessage = messagesMap.get(key);
			form.setField(promoCodesMessage.getNumber(), promoCodesMessage.getNumber());
			form.setField(String.format(promoCodesConfig.PROMO_CODES_MESSAGE_TYPE, promoCodesMessage.getNumber()),
					promoCodesMessage.getType() != null ? promoCodesMessage.getType().getDescription() : null);

			String titleField   = String.format(promoCodesConfig.PROMO_CODES_MESSAGE_TITLE, promoCodesMessage.getNumber());
			String messageField = String.format(promoCodesConfig.PROMO_CODES_MESSAGE_TEXT,  promoCodesMessage.getNumber());

			form.setField(titleField,   promoCodesMessage.getTitle());
			form.setField(messageField, promoCodesMessage.getText());
			form.setField(String.format(promoCodesConfig.PROMO_CODES_MESSAGE_EVENT, promoCodesMessage.getNumber()), promoCodesMessage.getEvent());

			String defaultTitle = defaultMessagesMap.get(key).getTitle();
			String defaultText  = defaultMessagesMap.get(key).getText();
			String title        = messagesMap.get(key).getTitle();
			String text         = messagesMap.get(key).getText();

			form.setField(titleField.concat(".default"),   defaultTitle.equals(title));
			form.setField(messageField.concat(".default"), defaultText.equals(text));
		}
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		//Чтобы явно не привязываться к конкретным номерам сообщений, проверка по ним сделана тут а не через FormProcessor
		ActionMessages actionMessages = super.validateAdditionalFormData(frm, operation);
		//Проверка скорректированных сотрудником данных по сообщениям промо - кодов
		PromoCodeDepositSettingsForm form = (PromoCodeDepositSettingsForm) frm;
		PromoCodeDepositOperation  promoCodeDepositOperation = (PromoCodeDepositOperation) operation;
		PromoCodesDepositConfig promoCodesConfig = (PromoCodesDepositConfig) promoCodeDepositOperation.getEntity();
		Map<String, Object> fields = form.getFields();
		Map<String, PromoCodesMessage> promoCodesMessageMap = promoCodesConfig.getPromoCodesMessagesMap();

		if (promoCodesConfig != null && MapUtils.isNotEmpty(promoCodesMessageMap)
				&& MapUtils.isNotEmpty(fields))
		{
			for (String messageNumber : promoCodesMessageMap.keySet())
			{
				String titleKey = String.format(promoCodesConfig.PROMO_CODES_MESSAGE_TITLE, messageNumber);
				String textKey = String.format(promoCodesConfig.PROMO_CODES_MESSAGE_TEXT, messageNumber);
				String messageTitle = (String) fields.get(titleKey);
				String messageText = (String) fields.get(textKey);

				//Проверка длинны поля "Заголовок"
				if (messageTitle.length() > TITLE_FIELD_LENGTH)
				{
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(String.format(FIELD_LENGTH_ERROR, messageNumber, TITLE_FIELD_LENGTH), false));
				}
				//Проверка длинны поля "Текст"
				if (messageText.length() > TEXT_FIELD_LENGTH)
				{
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(String.format(FIELD_LENGTH_ERROR, messageNumber, TEXT_FIELD_LENGTH), false));
				}
			}
		}
		return actionMessages;
	}

    @Override protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
    {
        PromoCodeDepositSettingsForm form = (PromoCodeDepositSettingsForm) frm;
        PromoCodesDepositConfig promoCodesConfig = (PromoCodesDepositConfig) operation.getEntity();
        if (promoCodesConfig == null)
            return;
        form.getPromoCodesMessagesMap().putAll(promoCodesConfig.getPromoCodesMessagesMap());
        fillPromoCodesMessages(form, promoCodesConfig);
	}
}
