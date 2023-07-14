package com.rssl.phizic.web.client.cards;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.payments.template.TemplateGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.card.GetExternalCardCurrencyOperation;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 08.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetCardCurrencyAction extends OperationalActionBase
{
	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Map<String, String> messageMap = new HashMap<String, String>();
	private static final String TEMPLATE_ID_FIELD = "template";

	static
	{
		messageMap.put("CARD_NOT_FOUND_MESSAGE", "Карта с таким номером не найдена в системе. Пожалуйста, проверьте номер карты получателя.");
		messageMap.put("PHONE_NOT_FOUND_MESSAGE", "По указанному номеру мобильного телефона не найдено ни одной карты. Пожалуйста, проверьте номер телефона.");
		messageMap.put("PHONE_EXCEPTION_MESSAGE", "Ошибка при получении карты по номеру мобильного телефона ");
		messageMap.put("CARD_EXCEPTION_MESSAGE", "Ошибка при получении информации по карте №");
	}

	private enum PaymentSubType
	{
		CARD,                 //Перевод на карту Сбера
		PHONE;                //перевод на карту по номеру моб.телефона

		public static PaymentSubType fromValue(String value) throws IllegalArgumentException
		{
			if ("card".equals(value.trim()))
				return CARD;
			else if ("phone".equals(value.trim()))
				return PHONE;
			else
				throw new IllegalArgumentException("Некорреткный тип перевода при запросе валюты карты зачисления.");
		}
	}

	private enum MessageType
	{
		EXCEPTION,
		NOT_FOUND;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardCurrencyForm frm = (GetCardCurrencyForm) form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()),
				GetCardCurrencyForm.FORM, DefaultValidationStrategy.getInstance());
		if (processor.process())
		{
			PaymentSubType type = PaymentSubType.fromValue((String) frm.getField(GetCardCurrencyForm.PARAM_TYPE));
			String number = (String) processor.getResult().get(type == PaymentSubType.CARD ? GetCardCurrencyForm.CARD_NUMBER : GetCardCurrencyForm.PHONE_NUMBER);
			try
			{
				GetExternalCardCurrencyOperation operation = createOperation("GetExternalCardCurrencyOperation", "ExternalCardCurrency");
				switch (type)
				{
					case CARD:
					{
						operation.initialize(number);
						break;
					}
					case PHONE:
					{
						Long templateId = null;
						try
						{
							Object templateIdStr = frm.getField(TEMPLATE_ID_FIELD);
							templateId = templateIdStr != null ? Long.parseLong(templateIdStr.toString()) : null;

						}
						catch (NumberFormatException nfe)
						{
							log.error(nfe);
						}

						try
						{
							operation.initializeByPhone(number, templateId);
						}
						catch (BusinessLogicException ble)
						{
							frm.setCurrency(null);
							saveError(request, ble.getMessage());
							return mapping.findForward(FORWARD_START);
						}
						break;
					}
				}
				Card card = operation.getEntity();
				if (card == null)
				{
					frm.setCurrency(null);
					saveError(request, getMessage(type, MessageType.EXCEPTION));
					return mapping.findForward(FORWARD_START);
				}

				frm.setCurrency(card.getCurrency().getCode());
				addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", card));
			}
			catch (Exception e)
			{
				phizLog.error(getMessage(type, MessageType.EXCEPTION) + (type == PaymentSubType.CARD ? MaskUtil.getCutCardNumberForLog(number) : number), e);
				saveError(request, getMessage(type, MessageType.EXCEPTION) + (type == PaymentSubType.CARD ? MaskUtil.getCutCardNumber(number) : number));
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	private String getMessage(PaymentSubType type, MessageType typeMsg)
	{
		return messageMap.get(type.name() + "_" + typeMsg.name() + "_MESSAGE");
	}

	protected boolean isAjax()
	{
		return true;
	}
}
