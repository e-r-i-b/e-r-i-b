package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankConstants.SBERBANK_SERVICE_PROVIDERS_LIST_URL;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 27.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Композитор SMS-запросов
 */
public class SmsCommandComposer
{
	/**
	 * Максимальное число управляющих SMS-запросов на главной странице
	 */
	private static final int TOP_CONTROL_SMSREQUEST_LIMIT = 3;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ServiceProviderService providerService = new ServiceProviderService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Формирует список SMS-запросов по подключению
	 * @param cardNumber - номер карты,
	 * если требуется указывать карту в запросе (последние N цифр)
	 *  (null, если не требуется)
	 * @return список шаблонов
	 */
	public List<SmsCommand> composeRegistrationSmsRequests(String cardNumber)
	{
		String cardCode = null;
		if (!StringHelper.isEmpty(cardNumber))
			cardCode = MobileBankUtils.getCardCode(cardNumber);
		String cmdPostfix = "";
		if (!StringHelper.isEmpty(cardCode))
			cmdPostfix = "   " + cardCode;

		List<SmsCommand> commands = new LinkedList<SmsCommand>();
		SmsCommand sms;

		sms = new SmsCommand();
		sms.setName("Список всех карт подключения");
		sms.setFormat("Инфо" + cmdPostfix);
		sms.setExample("Инфо" + cmdPostfix);
		commands.add(sms);

/*
		sms = new SmsCommand();
		sms.setName("Блокировка подключения");
		sms.setFormat("04" + cmdPostfix);
		sms.setExample("04" + cmdPostfix);
		commands.add(sms);
*/

/*
		sms = new SmsCommand();
		sms.setName("Разблокировака подключения");
		sms.setFormat("05" + cmdPostfix);
		sms.setExample("05" + cmdPostfix);
		commands.add(sms);
*/

		return commands;
	}

	/**
	 * Формирует список SMS-шаблонов на оплату
	 * @param templates - коллекция SMS-шаблонов в формате шлюза МБ
	 * @param prefixes - мап префиксов для СМС-комманд "код_получателя -> префикс" (см. CHG026525, CHG043545)
	 * @param cardNumber - номер карты, если нужно, чтобы номер карты участвовал в формате SMS-комманды
	 * иначе null
	 * @return список SMS-шаблонов на оплату
	 */
	public List<SmsCommand> composePaymentSmsTemplates(Collection<MobileBankTemplate> templates, Map<String, String> prefixes, String cardNumber)
	{
		if (templates.isEmpty())
			return Collections.emptyList();

		String cardCode = "";
		if (!StringHelper.isEmpty(cardNumber))
			cardCode = MobileBankUtils.getCardCode(cardNumber);

		Map<String, Integer> commandIndexes = new HashMap<String, Integer>();
		List<SmsCommand> cardCommands = new LinkedList<SmsCommand>();
		for (MobileBankTemplate template : templates) {
			String recipientCode = template.getRecipient();
			BillingServiceProvider provider = getProvider(recipientCode);
			if(provider == null)
			{
				log.error("Не найден поставщик " + recipientCode);
				continue;
			}
			String recipientName = provider.getName();
			String prefix = prefixes.get(recipientCode);
			Integer commandIndex = commandIndexes.get(recipientCode);
			if (commandIndex == null)
				commandIndex = 1;
			for (String payerCode : template.getPayerCodes()) {
				SmsCommand command = new SmsCommand();
				command.setRecipientCode(recipientCode);
				command.setPayerCode(payerCode);
				command.setName(getPaymentSmsCommandName(recipientName, payerCode));
				if(provider.isStandartTemplate())
				{
					command.setFormat(getPaymentSmsCommandFormat(commandIndex.toString(), recipientCode, prefix, cardCode));
					command.setExample(getPaymentSmsCommandExample(commandIndex.toString(), recipientCode, prefix, cardCode));
				}
				else
				{
					command.setFormat(provider.getTemplateFormat().replaceAll("#counter#",commandIndex.toString()));
					command.setExample(provider.getTemplateExample().replaceAll("#counter#",commandIndex.toString()));
				}
				cardCommands.add(command);
				commandIndex++;
			}
			commandIndexes.put(recipientCode, commandIndex);
		}
		return cardCommands;
	}

	/**
	 * Формирует список SMS-запросов на оплату с карты
	 * @return список SMS-запросов на оплату с карты
	 */
	public List<SmsCommand> composePaymentSmsRequest()
	{
		List<SmsCommand> commands = new LinkedList<SmsCommand>();

		SmsCommand sms;

		sms = new SmsCommand();
		sms.setName("Перевод с карты на карту");
		sms.setFormat("PEREVOD " +
				nobr("[4 последние цифры номера карты отправителя]") + " " +
				nobr("[4 последние цифры номера карты получателя]") + " " +
				nobr("[Сумма перевода]"));
		sms.setExample("PEREVOD 9876 7876 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Оплата услуг");
		sms.setFormat(nobr("[" + sberbankProvidersLink() + "]") + " " +
				nobr("[Идентификатор клиента]") + " " +
				nobr("[Сумма]"));
		// TODO: ссылка и подсказка
		sms.setExample("BEELINE 9036577678 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Совершение платежа в ЕИРЦ города Москвы");
		sms.setFormat("EIRC " +
				nobr("[Код плательщика]") + " " +
				nobr("[Месяц]") + " " +
				nobr("[Сумма (рубли, копейки)]"));
		sms.setExample("EIRC 1234567890 127676,56");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Погашение кредита");
		sms.setFormat("CREDIT " +
				nobr("[Номер ссудного счета]") + " " +
				nobr("[Сумма в рублях]"));
		sms.setExample("CREDIT 15675468787658676565 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Совершение платежей в благотворительный фонд «Подари жизнь»");
		sms.setFormat("GRANTLIFE " + nobr("[Сумма в рублях]"));
		sms.setExample("GRANTLIFE 600000");
		commands.add(sms);
		
		sms = new SmsCommand();
		sms.setName("Оплата телефона подключения");
		sms.setFormat("[Код оператора телефона, с которого отправляются SMS на выполнение операций] " +
				"[Сумма]");
		sms.setExample("MTS 350");
		commands.add(sms);

		return commands;
	}

	private String sberbankProvidersLink()
	{
		return link(SBERBANK_SERVICE_PROVIDERS_LIST_URL,
				"Код поставщика услуги",
				"Просмотреть список поставщиков услуг «Мобильного банка»",
				true);
	}

	/**
	 * Формирует список управляющих SMS-запросов по карте
	 * (блокировка, баланс, последние операции)
	 * @param cardNumber - номер карты
	 * @return список SMS-запросов
	 */
	public List<SmsCommand> composeCardControlSmsRequests(String cardNumber)
	{
		List<SmsCommand> commands = new LinkedList<SmsCommand>();

		// 4 последние цифры номера карты
		String cardId = StringUtils.right(cardNumber, 4);

		SmsCommand sms;

		sms = new SmsCommand();
		sms.setName("Баланс карты");
		sms.setFormat(String.format("Баланс %s", cardId));
		sms.setExample(String.format("Баланс %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Последние операции");
		sms.setFormat(String.format("История %s", cardId));
		sms.setExample(String.format("История %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Блокировка карты");
		sms.setFormat(String.format("Блок %s", cardId));
		sms.setExample(String.format("Блок %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("Список всех карт подключения");
		sms.setFormat(String.format("Инфо %s", cardId));
		sms.setExample(String.format("Инфо %s", cardId));
		commands.add(sms);

		return commands;
	}

	/**
	 * Формирует список избранных SMS-запросов по карте
	 * на основе управляющих SMS-запросов и SMS-шаблонов на оплату
	 * @param cardShortcut - карта (основная либо дополнительная)
	 * @return список SMS-запросов
	 */
	public List<SmsCommand> composeFavoriteSmsRequests(CardShortcut cardShortcut)
	{
		if (cardShortcut == null)
			throw new NullPointerException("Argument 'cardShortcut' cannot be null");
		List<SmsCommand> controlSmsRequests = cardShortcut.getControlSmsRequests();
		List<SmsCommand> paymentSmsTemplates = cardShortcut.getPaymentSmsTemplates();

		List<SmsCommand> list = new LinkedList<SmsCommand>();
		// (1) N первых управляющих SMS-запросов
		if (!CollectionUtils.isEmpty(controlSmsRequests)) {
			Iterator<SmsCommand> it = controlSmsRequests.iterator();
			for (int i=0; i< TOP_CONTROL_SMSREQUEST_LIMIT; i++) {
				list.add(it.next());
			}
		}

		// (2) Первый SMS-шаблон на оплату
		if (!CollectionUtils.isEmpty(paymentSmsTemplates))
			list.add(paymentSmsTemplates.iterator().next());
		return list;
	}

	private String nobr(String text)
	{
		return "<nobr>" + text + "</nobr>";
	}

	private String link(String url, String text, String hint, boolean external)
	{
		if (StringHelper.isEmpty(url))
			throw new IllegalArgumentException("Argument 'url' cannot be null nor empty");
		if (text == null)
			text = "";
		if (hint == null)
			hint = "";
		String target = external ? "_blank" : "_self";
		return String.format(
				"<a href=\"%s\" title=\"%s\" target=\"%s\">%s</a>",
				url, hint, target, text);
	}

	private String getPaymentSmsCommandName(String recipientName, String payerCode)
	{
		return String.format("Оплата %s, %s", recipientName, payerCode);
	}

	/**
	 * Формирует формат СМС-запроса
	 * @param commandIndex
	 * @param recipientCode
	 * @param prefix
	 * @param cardCode
	 * @return
	 */
	@SuppressWarnings({"AssignmentToMethodParameter"})
	public String getPaymentSmsCommandFormat(String commandIndex, String recipientCode, String prefix, String cardCode)
	{
		if (StringHelper.isEmpty(recipientCode))
			recipientCode = "код_поставщика";
		prefix = StringHelper.getEmptyIfNull(prefix);
		cardCode = StringHelper.getEmptyIfNull(cardCode);

		return String.format("%s %s %s [сумма] %s", recipientCode, commandIndex, prefix, cardCode);
	}

	/**
	 * Формирует пример СМС-запроса
	 * @param commandIndex
	 * @param recipientCode
	 * @param prefix
	 * @param cardCode
	 * @return
	 */
	@SuppressWarnings({"AssignmentToMethodParameter"})
	public String getPaymentSmsCommandExample(String commandIndex, String recipientCode, String prefix, String cardCode)
	{
		if (StringHelper.isEmpty(recipientCode))
			recipientCode = "код_поставщика";
		prefix = StringHelper.getEmptyIfNull(prefix);
		cardCode = StringHelper.getEmptyIfNull(cardCode);

		return String.format("%s %s %s 100 %s", recipientCode, commandIndex, prefix, cardCode);
	}

	/**
	 * Возвращает получателя (поставщика) по его коду
	 * @param providerCode - код поставщика в МБ
	 * @return поставщик
	 */
	private static BillingServiceProvider getProvider(String providerCode)
	{
		if (StringHelper.isEmpty(providerCode))
			throw new IllegalArgumentException("Argument 'providerCode' cannot be null or empty");

		try
		{
			return providerService.findByMobileBankCode(providerCode);
		}
		catch (BusinessException ex)
		{
			log.error("Сбой при получении поставщика " + providerCode, ex);
		}
		return null;
	}
}
