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
 * ���������� SMS-��������
 */
public class SmsCommandComposer
{
	/**
	 * ������������ ����� ����������� SMS-�������� �� ������� ��������
	 */
	private static final int TOP_CONTROL_SMSREQUEST_LIMIT = 3;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ServiceProviderService providerService = new ServiceProviderService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ��������� ������ SMS-�������� �� �����������
	 * @param cardNumber - ����� �����,
	 * ���� ��������� ��������� ����� � ������� (��������� N ����)
	 *  (null, ���� �� ���������)
	 * @return ������ ��������
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
		sms.setName("������ ���� ���� �����������");
		sms.setFormat("����" + cmdPostfix);
		sms.setExample("����" + cmdPostfix);
		commands.add(sms);

/*
		sms = new SmsCommand();
		sms.setName("���������� �����������");
		sms.setFormat("04" + cmdPostfix);
		sms.setExample("04" + cmdPostfix);
		commands.add(sms);
*/

/*
		sms = new SmsCommand();
		sms.setName("�������������� �����������");
		sms.setFormat("05" + cmdPostfix);
		sms.setExample("05" + cmdPostfix);
		commands.add(sms);
*/

		return commands;
	}

	/**
	 * ��������� ������ SMS-�������� �� ������
	 * @param templates - ��������� SMS-�������� � ������� ����� ��
	 * @param prefixes - ��� ��������� ��� ���-������� "���_���������� -> �������" (��. CHG026525, CHG043545)
	 * @param cardNumber - ����� �����, ���� �����, ����� ����� ����� ���������� � ������� SMS-��������
	 * ����� null
	 * @return ������ SMS-�������� �� ������
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
				log.error("�� ������ ��������� " + recipientCode);
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
	 * ��������� ������ SMS-�������� �� ������ � �����
	 * @return ������ SMS-�������� �� ������ � �����
	 */
	public List<SmsCommand> composePaymentSmsRequest()
	{
		List<SmsCommand> commands = new LinkedList<SmsCommand>();

		SmsCommand sms;

		sms = new SmsCommand();
		sms.setName("������� � ����� �� �����");
		sms.setFormat("PEREVOD " +
				nobr("[4 ��������� ����� ������ ����� �����������]") + " " +
				nobr("[4 ��������� ����� ������ ����� ����������]") + " " +
				nobr("[����� ��������]"));
		sms.setExample("PEREVOD 9876 7876 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("������ �����");
		sms.setFormat(nobr("[" + sberbankProvidersLink() + "]") + " " +
				nobr("[������������� �������]") + " " +
				nobr("[�����]"));
		// TODO: ������ � ���������
		sms.setExample("BEELINE 9036577678 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("���������� ������� � ���� ������ ������");
		sms.setFormat("EIRC " +
				nobr("[��� �����������]") + " " +
				nobr("[�����]") + " " +
				nobr("[����� (�����, �������)]"));
		sms.setExample("EIRC 1234567890 127676,56");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("��������� �������");
		sms.setFormat("CREDIT " +
				nobr("[����� �������� �����]") + " " +
				nobr("[����� � ������]"));
		sms.setExample("CREDIT 15675468787658676565 600");
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("���������� �������� � ����������������� ���� ������� ������");
		sms.setFormat("GRANTLIFE " + nobr("[����� � ������]"));
		sms.setExample("GRANTLIFE 600000");
		commands.add(sms);
		
		sms = new SmsCommand();
		sms.setName("������ �������� �����������");
		sms.setFormat("[��� ��������� ��������, � �������� ������������ SMS �� ���������� ��������] " +
				"[�����]");
		sms.setExample("MTS 350");
		commands.add(sms);

		return commands;
	}

	private String sberbankProvidersLink()
	{
		return link(SBERBANK_SERVICE_PROVIDERS_LIST_URL,
				"��� ���������� ������",
				"����������� ������ ����������� ����� ����������� �����",
				true);
	}

	/**
	 * ��������� ������ ����������� SMS-�������� �� �����
	 * (����������, ������, ��������� ��������)
	 * @param cardNumber - ����� �����
	 * @return ������ SMS-��������
	 */
	public List<SmsCommand> composeCardControlSmsRequests(String cardNumber)
	{
		List<SmsCommand> commands = new LinkedList<SmsCommand>();

		// 4 ��������� ����� ������ �����
		String cardId = StringUtils.right(cardNumber, 4);

		SmsCommand sms;

		sms = new SmsCommand();
		sms.setName("������ �����");
		sms.setFormat(String.format("������ %s", cardId));
		sms.setExample(String.format("������ %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("��������� ��������");
		sms.setFormat(String.format("������� %s", cardId));
		sms.setExample(String.format("������� %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("���������� �����");
		sms.setFormat(String.format("���� %s", cardId));
		sms.setExample(String.format("���� %s", cardId));
		commands.add(sms);

		sms = new SmsCommand();
		sms.setName("������ ���� ���� �����������");
		sms.setFormat(String.format("���� %s", cardId));
		sms.setExample(String.format("���� %s", cardId));
		commands.add(sms);

		return commands;
	}

	/**
	 * ��������� ������ ��������� SMS-�������� �� �����
	 * �� ������ ����������� SMS-�������� � SMS-�������� �� ������
	 * @param cardShortcut - ����� (�������� ���� ��������������)
	 * @return ������ SMS-��������
	 */
	public List<SmsCommand> composeFavoriteSmsRequests(CardShortcut cardShortcut)
	{
		if (cardShortcut == null)
			throw new NullPointerException("Argument 'cardShortcut' cannot be null");
		List<SmsCommand> controlSmsRequests = cardShortcut.getControlSmsRequests();
		List<SmsCommand> paymentSmsTemplates = cardShortcut.getPaymentSmsTemplates();

		List<SmsCommand> list = new LinkedList<SmsCommand>();
		// (1) N ������ ����������� SMS-��������
		if (!CollectionUtils.isEmpty(controlSmsRequests)) {
			Iterator<SmsCommand> it = controlSmsRequests.iterator();
			for (int i=0; i< TOP_CONTROL_SMSREQUEST_LIMIT; i++) {
				list.add(it.next());
			}
		}

		// (2) ������ SMS-������ �� ������
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
		return String.format("������ %s, %s", recipientName, payerCode);
	}

	/**
	 * ��������� ������ ���-�������
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
			recipientCode = "���_����������";
		prefix = StringHelper.getEmptyIfNull(prefix);
		cardCode = StringHelper.getEmptyIfNull(cardCode);

		return String.format("%s %s %s [�����] %s", recipientCode, commandIndex, prefix, cardCode);
	}

	/**
	 * ��������� ������ ���-�������
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
			recipientCode = "���_����������";
		prefix = StringHelper.getEmptyIfNull(prefix);
		cardCode = StringHelper.getEmptyIfNull(cardCode);

		return String.format("%s %s %s 100 %s", recipientCode, commandIndex, prefix, cardCode);
	}

	/**
	 * ���������� ���������� (����������) �� ��� ����
	 * @param providerCode - ��� ���������� � ��
	 * @return ���������
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
			log.error("���� ��� ��������� ���������� " + providerCode, ex);
		}
		return null;
	}
}
