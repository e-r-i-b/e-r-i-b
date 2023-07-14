package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.HashSet;
import java.util.Set;

/**
 * Условие на то, что платеж создается на основе подтвржденного шаблона
 * @author egorova
 * @ created 05.07.2010
 * @ $Author$
 * @ $Revision$
 *  Алгоритм:
 *  1.	Проверяем операцию на возможность превышения лимитов по группе риска, заводим флаг (превыше/не превышен лимит)
	2.	Проверяем, если операция оплаты по шаблону
		Нет, идем на шаг 3
		Да:
		1. Переводов вклад-карта, вклад-вклад?
			Да
                1. Проверяем настройку в админке, нужно ли подтверждать такие операции. Настройка включена - - нужно подтверждать
				2. Проверяем совпадение ключевых полей. Изменились - нужно подтверждать
				3. Проверяем кратность суммы оплаты с суммой шаблона. Превысили в n раз - нужно подтверждать
				4. Нужно подтверждать
		2.	Проверяем канал оплаты: mAPI >= 5.00
			1.	Да, идем на шаг 2.6.
		3.	Проверяем статус шаблона, по которому выполняется операция:
			1.	Статус шаблона сверх лимитный
				1.	Да, идем на шаг 2.7.
		4.	Проверяем превышение оплаты по лимитам группы риска
			1.	Да,  разрешенную сумму превысили, кнопки подтверждения по чеку/смс/.. отрисовавыем (выход)
		5.	Проверяем флаг “подтверждения операций по шаблонам СМС-паролем”
			1.	Да, кнопки подтверждения по чеку/смс/… отрисовываем (выход)
		6.	Проверяем превышение значения суммы операции по шаблону со значением суммы шаблона
			1.	Да, превышена. Проверяем канал оплаты: mAPI?
				1.  Да, подтверждение по otp не нужно, переходим к следующему шагу
				2.  Нет, кнопки подтверждения по чеку/смс/… отрисовываем (выход)
		7.	Проверяем изменение ключевых полей
			1.	Да, значения изменены, кнопки подтверждения по чеку/смс/… отрисовываем (выход)
	3.	Проверяем платеж – возможно это оплата на наш номер телефона:
		1.	Да, проверяем флаг превышения лимитов по группе риска.
			1.	Да, кнопки подтверждения по чеку/смс/… отрисовываем (выход)
			2.	Нет, без подтверждения по чеку/смс/… (выход)
		2.	Нет, двигаемся дальше.
	4.	Проверяем оплата по шаблону мобильного банка
		1.	Нет, идем на шаг 5.
		2.	Да, шаблон МБ. Проверяем канал оплаты: mAPI доавторизационная зона?
			1.  Да, идем на шаг 5.
			2.  Нет
				1.	Проверяем превышение суммы по лимиту
					1.	Да, сумма превышена, кнопки подтверждения по чеку/смс/.. отрисовываем (выход)
					2.	Нет, не превышена, кнопки подтверждения по чеку/смс/.. не торисовываем (выход)
	5.	Показываем кнопки подтверждения по чеку/смс/… (выход)

 */

public class PaymentByTemplateCondition implements StrategyCondition
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Set<String> NOT_COMFIRMABLE_STATES = new HashSet<String>(); //список статусов, для которых возможно подтверждение без смс и пр..
	//только при создании копий из документов с такими статусами будет происходить проверка неободимости доп подтверждени. для остальныз статусов - подтверждение обязательно.
	static
	{
		NOT_COMFIRMABLE_STATES.add("TEMPLATE");
		NOT_COMFIRMABLE_STATES.add("WAIT_CONFIRM_TEMPLATE");
	}

	/**
	 * Возвращем необходимость проверки объекта. Отвечает объект определенным условиям или нет.
	 * @param object - платеж для проверки
	 * @return true, если объект прошел проверку и его подтверждать НЕ надо, false - объект необходимо подтверждать.
	 */
	public boolean checkCondition(ConfirmableObject object)
	{
		if (!(object instanceof BusinessDocument))
			return false;

		BusinessDocument document = (BusinessDocument) object;

		try
		{
			//1. если нет превышения лимита по группе риска, то такой платеж не требует доп подтверждения.
			boolean within = !LimitHelper.needAdditionalConfirm(document);

			//2. проверка, если платеж создан по шаблону
			if (document.isByTemplate())
			{
				TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
				//Если платеж был создан на основе шаблона, который уже стёрли. Если нет, то статус у шаблона Должен быть любой, кроме не подвержденного шаблона
				// (т.к. подтвержденный платеж может выступить шаблоном.
				// (кнопка "повторить платеж"): если реквизиты не менялись, то такой платеж подтверждать повторно не требуется.) , то подтверждаем.
				if (template == null || template.getState() == null || !NOT_COMFIRMABLE_STATES.contains(template.getState().getCode()))
					return preReturnAction(document, false);

				//2.1 Для платежей между своими счетами вклад-вклад, вклад-карта
				if (template.getType() == ClientAccountsTransfer.class || template.getType() == AccountToCardTransfer.class)
				{
					 //необходимо проверять установленную флаг "подтверждения операций по шаблонам СМС-паролем"
					 //2.1.1 Если флаг установлен, то всегда подтверждать по смс.
					if (checkConfirmSMSSetting(document, template))
						return preReturnAction(document, false);

					//2.1.2 Если изменились ключевые реквизиты, то необходимо потверждение.
					if (!document.equalsKeyEssentials(template))
						return preReturnAction(document, false);

					//2.1.3 если сумма платежа увеличивается более, чем в N(настройка) раз, то необходимо подтверждать платеж по СМС или чеком
					if (!checkSumm(document, template))
						return preReturnAction(document, false);
					//2.1.4
					return preReturnAction(document, within);
				}

				// Если шаблон подтвержден в КЦ(Сверхлимитынй). Проверяем на совпедение ключевых реквизитов.
				if (template.getState().getCode().equals("TEMPLATE"))
				{
					return preReturnAction(document, document.equalsKeyEssentials(template));
				}


				//2.2 Проверяем канал оплаты
				if (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))
				{
					//2.4 Проверяем превышение оплаты по лимитам группы риска
					if (!within)
						return preReturnAction(document, false);

					//2.5 Проверяем флаг “подтверждения операций по шаблонам СМС-паролем” - настройка в АРМ сотрудника подтверждать все операции по шаблону СМС-паролем
					if (checkConfirmSMSSetting(document, template))
						return preReturnAction(document, false);
				}

				//2.6 если сумма платежа увеличивается более, чем в N(настройка) раз, то необходимо подтверждать платеж по СМС или чеком
				if (!checkSumm(document, template))
					return preReturnAction(document, false);

				//2.7 проверяем совпадение ключевых полей.
				return preReturnAction(document, document.equalsKeyEssentials(template));
			}
			//3. проверяем является ли платеж платежом в адрес поставщиков мобильной связи по своему номеру телефона.
			if (BillingPaymentHelper.isSelfMobileNumberPayment(document))
				return within;
			//4. проверка, если платеж создан по шаблону мобильного банка
			if (!(new ServicePaymentLikeMobileTemplateCondition().checkCondition(document)) && !MobileApiUtil.isLimitedScheme())
				return within;
			//5. Показываем кнопки подтверждения
			return preReturnAction(document, false);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return preReturnAction(document, false);
		}
		catch(BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			return preReturnAction(document, false);
		}
	}

	/**
	 * проверяет сумму в платеже.
	 * Если сумма платежа относительно суммы в шаблоне увеличилась больше чем в factor (задается в iccs.properties),
	 * то проверяем канал оплаты:
	 *      mAPI, подтверждение по otp не нужно, но флаг превышения лимита в платеже нужно сохранить
	 *      не mAPI, такой документ необходимо подтверждать по СМС.
	 *
	 * @param document документ.
	 * @param template шаблон.
	 * @return true если подтверждать не надо.
	 */
	private boolean checkSumm(BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			return true;

		AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
		boolean sumIncreasedOverLimit = DocumentHelper.checkPaymentByTemplateSum(document, template);
		payment.setSumIncreasedOverLimit(sumIncreasedOverLimit);
		//в mAPI подтверждение по otp не нужно
		return ApplicationUtil.isMobileApi() || !sumIncreasedOverLimit;
	}

	public String getWarning()
	{
		return null;
	}

    /**
     * Если документ не прошел проверку и его надо подтверждать (checkCondition вернул false), то в платеже взводится соответствующий флаг
     * @param document подтверждаемый документ
     * @param result результат, возвращаемый методом checkCondition
     * @return result
     */
    private boolean preReturnAction(BusinessDocument document, boolean result)
    {
        if (!result && document instanceof AbstractPaymentDocument)
            ((AbstractPaymentDocument)document).setPaymentFromTemplateNeedConfirm(true);
        return result;
    }

	/**
	 * Проверка настройки подтверждения операций по шаблону СМС-паролем
	 * @param document - проверяемый документ
	 * @return true - оплата по шаблону не подтвержденному в КЦ с включенной настройкой подтверждения операций СМС-паролем.
	 * @throws BusinessException
	 */
	protected boolean checkConfirmSMSSetting(BusinessDocument document) throws BusinessException
	{
		if (document.isByTemplate())
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
			if (template == null)
			{
				throw new BusinessException("не найден шаблон id = " + document.getTemplateId());
			}

			return checkConfirmSMSSetting(document, template);
		}
		return false;
	}

	/**
	 * Проверка настройки подтверждения операций по шаблону СМС-паролем
	 * @param document - проверяемый документ
	 * @param template - шаблон
	 * @return true - оплата по шаблону не подтвержденному в КЦ с включенной настройкой подтверждения операций СМС-паролем.
	 * @throws BusinessException
	 */
	protected boolean checkConfirmSMSSetting(BusinessDocument document, TemplateDocument template) throws BusinessException
	{
		//Подтверждать все операции по шаблону в адрес стороннего получателя, вклад-вклад, вклад-карта СМС-паролем:
		if(template.getType() == ClientAccountsTransfer.class || template.getType() == AccountToCardTransfer.class)
		{
			//Если заполнено поле «Подтверждать все операции по шаблону в адрес стороннего получателя, вклад – вклад, вклад – карта СМС – паролем»
			//на форме «Установка режима «Подтверждать операции по шаблону смс – паролем»», то операции вклад – вклад, вклад – карта
			//ВСЕГДА подтверждается смс – паролем.
			return DocumentHelper.getTemplateConfirmSetting((Department) document.getDepartment());
		}

		String templateState = template.getState().getCode();
		return DocumentHelper.getTemplateConfirmSetting((Department) document.getDepartment())
					&& ((templateState.equals("WAIT_CONFIRM_TEMPLATE") && (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))));
	}
}
