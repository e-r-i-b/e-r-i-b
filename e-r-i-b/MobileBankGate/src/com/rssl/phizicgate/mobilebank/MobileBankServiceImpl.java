package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizgate.mobilebank.GatePaymentTemplateImpl;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.MBKRegistrationParser;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.impl.AbstractDataSourceServiceGate;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizgate.mobilebank.cache.KeyGenerator;
import com.rssl.phizicgate.mobilebank.csa.ChangePassByCardNumberForErmbClientJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.ChangePassByCardNumberJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.GetClientByCardNumberJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.GetClientByLoginJDBCAction;
import com.rssl.phizicgate.way4u.messaging.Way4uUserInfoService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static com.rssl.phizgate.mobilebank.GatePhoneHelper.hidePhoneNumber;
import static com.rssl.phizic.gate.mobilebank.MobileBankCardStatus.ACTIVE;
import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;
import static com.rssl.phizicgate.mobilebank.SamplesListParser.PAYER_ID_PATTERN;
import static com.rssl.phizicgate.mobilebank.SamplesListParser.RECIPIENT_CODE_PATTERN;

/**
 * @author Erkin
 * @ created 14.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankServiceImpl extends AbstractDataSourceServiceGate implements MobileBankService
{
	private MobileBankRegistrationHelper registrationHelper;
	private MobileBankIMSIHelper imsiHelper;

	private static final int MAX_PAYERS_PER_RECIPIENT = 5;
	private static final int MAX_SMS_LENGTH = 500;
	private static final String TEMPLATE_ERROR = "���� ��� ��������� �������� �������� �� �����������";

	private static final String MOB = ".mob";
	private static final String ERIB = "erib";
	private static final String TB = "region";

	private static final String TEMPLATE_CACHE = "mobilebank-template-cache";
	private static final String STATUS_CACHE = "mobilebank-status-cache";
	private static final String MOBILE_CONTACT_CACHE = "mobilebank-contact-cache";
	private static final String OFFER_CONFIRM_CACHE = "mobilebank-offer-cache";

	private final Cache templateCache;
	private final Cache statusCache;
	private final Cache contactCache;
	private final Cache offerCache;

	private JDBCActionExecutor executor2 = null;
	private volatile JDBCActionExecutor reportExecutor = null;

	private final Object lock = new Object();

	///////////////////////////////////////////////////////////////////////////
	private static CounterService counterService = new CounterService();

	private static final char EXTERNAL_ID_SEPARATOR = '|';

	private static final GroupResult<String, List<GatePaymentTemplate>>
			EMPTY_PAYMENT_TEMPLATES_GRESULT = new GroupResult<String, List<GatePaymentTemplate>>();

	private final MBKRegistrationParser parser = new MBKRegistrationParser();


	/**
	 * ����������� ����������� �����
	 * @param factory ������� ������
	 */
	public MobileBankServiceImpl(GateFactory factory)
	{
		super(factory);

		registrationHelper = new MobileBankRegistrationHelper(getJDBCActionExecutor());
		imsiHelper = new MobileBankIMSIHelper(getJDBCActionExecutor());

		templateCache = CacheProvider.getCache(TEMPLATE_CACHE);
		statusCache = CacheProvider.getCache(STATUS_CACHE);
		contactCache = CacheProvider.getCache(MOBILE_CONTACT_CACHE);
		offerCache = CacheProvider.getCache(OFFER_CONFIRM_CACHE);

		executor2 = createExecutor("jdbc/MobileBank2");
	}

	protected String getDataSourceName()
	{
		return ConfigFactory.getConfig(com.rssl.phizic.jmx.MobileBankConfig.class).getDataSourceName();
	}

	private String getReportDataSourceName()
	{
   	    return ConfigFactory.getConfig(com.rssl.phizic.jmx.MobileBankConfig.class).getDataSourceReportName();
	}

	protected JDBCActionExecutor getReportJDBCActionExecutor()
	{
		if (reportExecutor != null)
		{
			return reportExecutor;
		}
		synchronized (lock)
		{
			if (reportExecutor != null)
			{
				return reportExecutor;
			}
			reportExecutor = createExecutor(getReportDataSourceName());
		}
		return reportExecutor;
	}

	@Override
	protected System getSystem()
	{
		return System.jdbc;
	}

	///////////////////////////////////////////////////////////////////////////
	// ������ �� ������� �����������

	public GroupResult<String, List<MobileBankRegistration>> getRegistrations(Boolean alternative, String... cardNumbers)
	{
		log.trace("��������� ������ �����������...");

		GroupResult<String, List<MobileBankRegistration>> result =
				new GroupResult<String, List<MobileBankRegistration>>();
		if (ArrayUtils.isEmpty(cardNumbers))
			return result;

		// 1. ����� ��������� �� ������� ����
		Set<String> cardNumberSet = new HashSet<String>(Arrays.asList(cardNumbers));

		for (String cardNumber : cardNumberSet)
		{
			try
			{
				GetRegistrationMode mode = alternative == null ? GetRegistrationMode.SOLF : (alternative ? GetRegistrationMode.BOTH : GetRegistrationMode.SOLID);

				List<MobileBankRegistration> resultList = registrationHelper.getRegistrations(cardNumber, mode);
				result.putResult(cardNumber, resultList);
			}
			catch (SystemException e)
			{
				result.putException(cardNumber, e);
			}
		}

		log.trace("������ ����������� �������");
		return result;
	}

	public List<MobileBankRegistration3> getRegistrations3(String cardNumber)
	{
		try
		{
			return registrationHelper.getRegistrations3(cardNumber);
		}
		catch (SystemException e)
		{
			log.error("������ ��� ��������� ������ ����������� �� �����: " + cardNumber, e);
			return Collections.emptyList();
		}
	}

	public List<MobileBankRegistration> getRegistrationsPack(Boolean alternative, String... cardNumbers) throws GateException, GateLogicException
	{
		log.trace("��������� ������ �����������...");

		List<MobileBankRegistration> result = new ArrayList<MobileBankRegistration>();

		if (ArrayUtils.isEmpty(cardNumbers))
			return result;

		GetRegistrationMode mode = alternative == null ? GetRegistrationMode.SOLF : (alternative ? GetRegistrationMode.BOTH : GetRegistrationMode.SOLID);
		try
		{
			result = registrationHelper.getRegistrationsPack(Arrays.asList(cardNumbers), mode);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}

		log.trace("������ ����������� �������");
		return result;
	}

	///////////////////////////////////////////////////////////////////////////
	// ������ �� ������� �������� ��������

	private static final SamplesListParser samplesListParser = new SamplesListParser();

	public GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> getTemplates(Long count, MobileBankCardInfo... cards)
	{
		if (count != null && count<=0)
			throw new IllegalArgumentException("Argument 'count' must be null or positive");

		log.trace("��������� ������ �������� ��������...");

		GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result =
				new GroupResult<MobileBankCardInfo, List<MobileBankTemplate>>();
		if (cards == null)
			return result;

		// 1. ����� ��������� �� ������� ����
		Set<MobileBankCardInfo> cardInfoSet = new LinkedHashSet<MobileBankCardInfo>(cards.length);
		CollectionUtils.addAll(cardInfoSet, cards);

		for (MobileBankCardInfo cardInfo : cardInfoSet)
		{
			if (cardInfo == null)
				throw new NullPointerException("�������� 'cardInfo' �� ����� ���� ������");

			try
			{
				String key = KeyGenerator.getKey(cardInfo.getCardNumber(), cardInfo.getMobilePhoneNumber());
				Element element = templateCache.get(key);
				if (element == null)
				{
					List<MobileBankTemplate> templatesList = loadTemplates(cardInfo, count, false);
					templateCache.put(new Element(key, templatesList));
					result.putResult(cardInfo, templatesList);
				}
				else
				{
					result.putResult(cardInfo, (List<MobileBankTemplate>) element.getObjectValue());
				}
			}
			catch (SystemException e)
			{
				result.putException(cardInfo, e);
			}
		}

		log.trace("������ �������� �������� �������");
		return result;
	}

	public void addTemplates(MobileBankCardInfo registration, List<MobileBankTemplate> newTemplates) throws GateException, GateLogicException
	{
		if (registration == null)
			throw new NullPointerException("Argument 'registration' cannot be null");
		if (newTemplates == null)
			throw new NullPointerException("Argument 'newTemplates' cannot be null");

		log.trace("���������� ����� �������� �������� � ������...");
		if (newTemplates.isEmpty()) {
			log.trace("����� �������� �� �������, ��������� ������");
			return;
		}

		try	{
			Map<String, MobileBankTemplate> allTemplatesMap =
					mapTemplates(loadTemplates(registration, null, true));

			// �������� ���������
			Map<String, Set<String>> changes = new LinkedHashMap<String, Set<String>>();
			for (MobileBankTemplate newTemplate : newTemplates) {
				String recipient = newTemplate.getRecipient();

				MobileBankTemplate oldTemplate = allTemplatesMap.get(recipient);
				if (oldTemplate == null)
					throw new GateLogicException("�� �� ������ �������� ������ ������� ���������� � ������� SMS-�������� �������� ������");

				Set<String> payers = new LinkedHashSet<String>();
				payers.addAll(Arrays.asList(oldTemplate.getPayerCodes()));
				payers.addAll(Arrays.asList(newTemplate.getPayerCodes()));
				if (payers.size() != oldTemplate.getPayerCodes().length)
					changes.put(recipient, payers);
			}

			templateCache.remove(KeyGenerator.getKey(registration.getCardNumber(), registration.getMobilePhoneNumber()));

			if (changes.isEmpty()) {
				log.trace("����������� ������� ��� ������������ � �������� ������, ��������� ������");
				return;
			}

			updateTemplates(registration, changes);

		} catch (SystemException ex) {
			log.error(ex.getMessage(), ex);
			throw new GateException(ex);
		}
	}

	public void removeTemplates(MobileBankCardInfo registration, List<MobileBankTemplate> removingTemplates) throws GateException
	{
		if (registration == null)
			throw new NullPointerException("Argument 'registration' cannot be null");
		if (removingTemplates == null)
			throw new NullPointerException("Argument 'removingTemplates' cannot be null");

		log.trace("�������� �������� �������� �� ������...");
		if (removingTemplates.isEmpty()) {
			log.trace("�� ������� ������� �� ��������, ������� ������");
			return;
		}

		try	{
			Map<String, MobileBankTemplate> allTemplatesMap =
					mapTemplates(loadTemplates(registration, null, false));

			// �������� ���������
			Map<String, Set<String>> changes = new LinkedHashMap<String, Set<String>>();
			for (MobileBankTemplate removingTemplate : removingTemplates) {
				String recipient = removingTemplate.getRecipient();

				MobileBankTemplate oldTemplate = allTemplatesMap.get(recipient);
				// �� ����� ������� ��, ���� � ��� ����
				if (oldTemplate == null)
					continue;

				Set<String> payers = new LinkedHashSet<String>();
				payers.addAll(Arrays.asList(oldTemplate.getPayerCodes()));
				payers.removeAll(Arrays.asList(removingTemplate.getPayerCodes()));
				if (payers.size() != oldTemplate.getPayerCodes().length)
					changes.put(recipient, payers);
			}

			templateCache.remove(KeyGenerator.getKey(registration.getCardNumber(), registration.getMobilePhoneNumber()));

			if (changes.isEmpty()) {
				log.trace("��������� ������� ����������� � �������� ������, ������� ������");
				return;
			}

			updateTemplates(registration, changes);

		} catch (SystemException ex) {
			log.error(ex.getMessage(), ex);
			throw new GateException(ex);
		}
	}

	/**
	 * ��������� ������� �� ��
	 * @param cardInfo - �����������
	 * @param limit - ����������� ������ �� ����� ���������� ����������� ��������
	 *  (null = ���)
	 * @param includeEmpty - �������� � ��������� ������� ��� ����� �����������
	 * @return ������ ��������
	 */
	private List<MobileBankTemplate> loadTemplates(MobileBankCardInfo cardInfo, Long limit, boolean includeEmpty) throws SystemException
	{
		if (cardInfo == null)
			throw new NullPointerException("Argument 'cardInfo' cannot be null");

		String cardNumber = cardInfo.getCardNumber();
		if (cardNumber == null)
			throw new NullPointerException("Argument 'cardInfo.cardNumber' cannot be null");

		String phone = cardInfo.getMobilePhoneNumber();
		if (phone == null)
			throw new NullPointerException("Argument 'cardInfo.mobilePhoneNumber' cannot be null");
		phone = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phone);

		// --------------------------------------

		try
		{
			String resultString = executeJDBCAction(new GetSamplesJDBCAction(cardNumber, phone));

			SampleInfo sampleInfo = parse(samplesListParser, resultString);

			List<MobileBankTemplate> result = Collections.emptyList();
			if (sampleInfo != null) {
				SampleTransformer samplesTransformer = new SampleTransformer(cardInfo, limit, includeEmpty);
				result = transform(samplesTransformer, sampleInfo);
				log.trace("������� " + samplesTransformer.getCount() + " �������� �������� �� ����������� " + cardInfo);
			}
			else log.trace("������� 0 �������� �������� �� ����������� " + cardInfo);
			return result;
		}
		catch (GateException e)
		{
			log.error(TEMPLATE_ERROR + cardInfo + ": " + e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * ��������� ������� �������� ��� �������� �����������
	 * @param registration - �����������
	 * @param changes - ��� ��������� "���������� -> ������_������������"
	 */
	private void updateTemplates(MobileBankCardInfo registration, Map<String, Set<String>> changes) throws SystemException
	{
		if (registration == null)
			throw new NullPointerException("Argument 'registration' cannot be null");

		String cardNumber = registration.getCardNumber();
		if (cardNumber == null)
			throw new NullPointerException("Argument 'registration.cardNumber' cannot be null");

		String phoneNumber = registration.getMobilePhoneNumber();
		if (phoneNumber == null)
			throw new NullPointerException("Argument 'registration.mobilePhoneNumber' cannot be null");
		phoneNumber = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phoneNumber);

		log.trace("���������� ������ �������� �������� " +
				"��� �����������: " + cardNumber + " " + phoneNumber);

		// �������� ������ ����������
		for (Map.Entry<String, Set<String>> entry : changes.entrySet()) {
			String recipient = entry.getKey();
			if (!RECIPIENT_CODE_PATTERN.matcher(recipient).matches())
				throw new GateException("������������ ��� ����������: " + recipient);

			Set<String> payers = entry.getValue();

			// �������� ������� "�� ����� N ����������� �� ������ ����������"
			if (payers.size() > MAX_PAYERS_PER_RECIPIENT)
				throw new GateException("��� ���������� " + recipient + " " +
						"������� ����� " + MAX_PAYERS_PER_RECIPIENT + " ������������");

			for (String payer : payers) {
				if (!PAYER_ID_PATTERN.matcher(payer).matches())
					throw new GateException("������������ ������������� �����������: " + payer);
			}
		}

		String destlistString = formatSampleDestList(changes);
	    String  resultString = executeJDBCAction(new UpdateSamplesJDBCAction(cardNumber, phoneNumber, destlistString));

		if (!StringHelper.isEmpty(resultString)) {
			log.error("���� ��� ���������� ������ �������� ��������: " + resultString);
			throw new GateException(resultString);
		}

		log.trace("������ �������� �������� ������� �������. " +
				"��������� ��������: " + changes.size());
	}

	private String formatSampleDestList(Map<String, Set<String>> destlist)
	{
		List<String> parts = new ArrayList<String>(destlist.size());
		for (Map.Entry<String, Set<String>> entry : destlist.entrySet()) {
			String recipient = entry.getKey();
			if (StringHelper.isEmpty(recipient))
				continue;

			Set<String> payers = entry.getValue();
			StringHelper.removeBlankStrings(payers);
			parts.add(recipient + ":" + StringUtils.join(payers, ":"));
		}
		return "DESTLIST=" + StringUtils.join(parts, ",") + ";";
	}


	///////////////////////////////////////////////////////////////////////////
	// �������� ���

	public void sendSMS(String text, String textToLog, int id, String phone)
			throws GateException
	{
		log.trace("�������� ��� � ID=" + id + " �� ������ �������� '" + hidePhoneNumber(phone) + "' ...");

		if (StringUtils.isBlank(text))
			throw new IllegalArgumentException("Argument 'text' cannot be null or blank");
		if (text.length() > MAX_SMS_LENGTH )
			throw new IllegalArgumentException("� ��������� ���������� ����� 500 ��������");
		// ��������������� ����� �������� � ����������� � �.�. �� ����
		String phoneMB = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phone);
		try
		{
			executor2.execute(new SendSmsJDBCAction(phoneMB, text, textToLog, id, null));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		log.trace("��� id=" + id + " ������� ����������");
	}

	public Map<String, SendMessageError> sendSMSWithIMSICheck(MessageInfo messageInfo, String... toPhones) throws GateException
	{
		return imsiHelper.sendSmsWithIMSICheck(messageInfo,ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId(), toPhones);
	}

	public Card getCardByPhone(Client client, String phone)  throws GateException
	{
		String cardNumber = getCardNumberByPhone(client, phone);
		if (StringHelper.isEmpty(cardNumber))
			return null;

		try
		{
			BankrollHelper bankrollHelper = new BankrollHelper(GateSingleton.getFactory());
			return bankrollHelper.getCardByNumber(client, cardNumber);
		}
		catch (GateLogicException e)
		{
			// ��� ��������� ����� �������� �� ����� ������� ������ ��� ����������� ������������,
			// ������� ��������� � GateException
			throw new GateException(e);
		}
	}

	public String getCardNumberByPhone(Client client, String phone)  throws GateException
	{
		if (StringUtils.isBlank(phone))
			throw new IllegalArgumentException("�������� 'phone' �� ����� ���� ������");
		//+7.
		phone = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phone);

		String codeRegion = null;
		String codeBranch = null;

		if (client != null && client.getOffice() != null)
		{
			ExtendedCodeGateImpl code  = new ExtendedCodeGateImpl(client.getOffice().getCode());
			codeRegion = code.getRegion();
			codeBranch = code.getBranch();
		}

		log.trace("��������� ������ ����� �� ������ ���������� ��������.");

		try
		{
			return executeJDBCAction(new GetCardNumberByPhoneJDBCAction(phone, codeRegion, codeBranch));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public UserInfo getClientByPhone(String phone)  throws GateException
	{
		String cardNumber = getCardNumberByPhone(null, phone);
		if (StringHelper.isEmpty(cardNumber))
			return null;
		return getClientByCardNumber(cardNumber);
	}

	public QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws GateException
	{
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("�������� 'phoneNumber' �� ����� ���� ������.");
		}

		if ( status == null ||
			 status == QuickServiceStatusCode.QUICK_SERVICE_STATUS_REPEAT_QUERY ||
			 status == QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN )
		{
			throw new IllegalArgumentException("�������� 'status' �� ����� ���� ������ ��� ��������� �������� " +
											   "������ QUICK_SERVICE_STATUS_REPEAT_QUERY ��� QUICK_SERVICE_STATUS_UNKNOWN");
		}

		log.trace("����� ������� ������� �������� ��� ������ " + hidePhoneNumber(phoneNumber) + ".");

		String phone = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phoneNumber);

		try
		{
			QuickServiceStatusCode result = executeJDBCAction(new SendQuickServiceStatusJDBCAction(phone, status));

			statusCache.remove(phone);

			return result;
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public QuickServiceStatusCode getQuickServiceStatus(String phoneNumber) throws GateException
	{
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("�������� 'phoneNumber' �� ����� ���� ������.");
		}
		log.trace("��������� ������� ������� �������� ��� ������ " + hidePhoneNumber(phoneNumber) + ".");

		String phone = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phoneNumber);

		try
		{
			Element element = statusCache.get(phone);
			if (element == null)
			{
				QuickServiceStatusCode result = executeJDBCAction( new GetQuickServiceStatusJDBCAction(phone));
				statusCache.put(new Element(phone, result));
				return result;
			}
			return (QuickServiceStatusCode) element.getObjectValue();
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������������� ������

	/**
	 * �������� ������� � ��� "���������� -> ������"
	 * @param templates - �������
	 * @return ���
	 */
	private static Map<String, MobileBankTemplate>
	mapTemplates(Collection<MobileBankTemplate> templates)
	{
		Map<String, MobileBankTemplate> map =
				new LinkedHashMap<String, MobileBankTemplate>(templates.size());
		for (MobileBankTemplate template : templates)
			map.put(template.getRecipient(), template);
		return map;
	}

	private <T> T parse(Parser<T> parser, String string) throws GateException
	{
		try
		{
			return parser.parse(string);
		}
		catch (ParseException ex)
		{
			log.error(ex.getMessage(), ex);
			throw new GateException(ex);
		}
	}

	private <Output, Input> Output transform(Transformer<Output, Input> transformer, Input input) throws GateException
	{
		try
		{
			return transformer.transform(input);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void addRegistration(Client client, String cardNumber, String phoneNumber, String netType,  MobileBankTariff packetType) throws GateException
	{
		//��������� ������ ��� ��������
		//1� ���� (255 ����)
		String unloadEntity = registrationHelper.buildRegistration(
				cardNumber,
				phoneNumber,
				netType,
				getClientDocumentNumber(client),
				client.getOffice().getCode().getFields().get(TB),
				packetType);

		//�������� ����� ���������� ��������
		MobileBankConfig config = ConfigFactory.getConfig(MobileBankConfig.class);
		String unloadDir = config.getClientRegUnloadDir();
		if (StringHelper.isEmpty(unloadDir))
			throw new GateException("�� ����� ������� �������� ����������� ��");
		String fileName = getClientRegFileName();
		//���������
		//1.������� ���� ����, � ����� ����
		File tempFile = null;
		try
		{
			tempFile = FileHelper.writeTmp(new ByteArrayInputStream(unloadEntity.getBytes()));
		}
		catch (IOException e)
		{
			log.error("������ ��� �������� ��� ������ � tmp �����, �������� ����������� ��.", e);
			throw new GateException(e);
		}


		//2.�������� �� �����
		String filePath = FileHelper.normalizePath(unloadDir + File.separator + fileName);
		File destFile = new File(filePath);
		if (destFile.exists())
			throw new GateException("���� � ������" + filePath + "��� ����������.");

		try
		{
			FileUtils.copyFile(tempFile, destFile);
		}
		catch (IOException e)
		{
			log.error("������ ��� ������ ����������� �� � ����.", e);
			throw new GateException(e);
		}
		//3.������� ����
		if (tempFile!=null)
			tempFile.delete();
	}

	private String getClientDocumentNumber(Client client)
	{
		for (ClientDocument clientdocument : client.getDocuments())
		{
			if(clientdocument.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			{
			  String series = StringUtils.remove(clientdocument.getDocSeries(), " ");
			  String seriesAndNumber = StringUtils.substring(series,0,2)+" "+StringUtils.substring(series,2,4)+" "+ StringUtils.remove(clientdocument.getDocNumber(), " ");
			  return StringUtils.rightPad(seriesAndNumber,19);
			}
		}

		return StringUtils.rightPad("", 19);
	}

	public UserInfo getClientByCardNumber(final String cardNumber) throws GateException
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("�������� 'cardNumber' �� ����� ���� ������.");
		}

		try
		{
			return getDirectUserInfoByCardNumber(cardNumber);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	private String getClientRegFileName() throws GateException
	{ //�erib� + ����� � ������� ddmmyy� + ������������ ������ ���������������� ������ + ".mob"
		try
		{
			return ERIB + DateHelper.formatDateDDMMYY(DateHelper.getCurrentDate())
				        + counterService.getNext(Counters.MB_POKET_REGISTRATION_NUMBER) + MOB;
		}
		catch (CounterException e)
		{
			log.error("������ ��� ������� ������������ ��� ���������������� ������ M�.", e);
			throw new GateException(e);
		}
	}

	public UserInfo getClientByLogin(String userId) throws GateException
	{
		try
		{
			return getDirectUserInfoByUserId(userId);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public String getMobileContact(String phoneNumbersToFind) throws GateException
	{
		try
		{
			Element element = contactCache.get(phoneNumbersToFind);
			if (element == null)
			{
				String result = executeJDBCAction(new GetMobileContactJDBCAction(phoneNumbersToFind));
				contactCache.put(new Element(phoneNumbersToFind, result));
				return result;
			}

			return (String) element.getObjectValue();
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void sendOfferMessageSMS(String text, String textLog, Long id, String phone) throws GateException
	{
		log.trace("�������� ��� � ID=" + id + " �� ������ �������� '" + hidePhoneNumber(phone) + "' ...");

		if (StringUtils.isBlank(text))
			throw new IllegalArgumentException("'text' �� ����� ���� ������");
		if (text.length() > MAX_SMS_LENGTH )
			throw new IllegalArgumentException("� ��������� ���������� ����� 500 ��������");
		// ��������������� ����� �������� � ����������� � �.�. �� ����
		String phoneMB = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(phone);
		try
		{
			executeJDBCAction(new OfferSendMessageJDBCAction(phoneMB, text, textLog, id, null));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		log.trace("��� id=" + id + " ������� ����������");
	}

	public List<AcceptInfo> getOfferConfirm() throws GateException
	{
		try
		{
			String key = KeyGenerator.getKey();
			Element element = offerCache.get(key);
			if (element == null)
			{
				List<AcceptInfo> result = executeJDBCAction(new OfferConfirmJDBCAction(null));
				offerCache.put(new Element(key, result));
				return result;
			}

			return (List<AcceptInfo>) element.getObjectValue();
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void sendOfferQuit(Long id) throws GateException
	{
		try
		{
			executeJDBCAction(new OfferQuitJDBCAction(null, id));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public Set<String> getCardsByPhone(String phone) throws GateException
	{
		String formattedPhone = preparePhoneForCardsRequest(phone);
		try
		{
			 return executeJDBCAction(new GetCardNumbersByPhoneNumberJDBCAction(formattedPhone));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public Set<String> getCardsByPhoneViaReportDB(String phone) throws GateException
	{
		String formattedPhone = preparePhoneForCardsRequest(phone);
		try
		{
			String dataSourceName = ConfigFactory.getConfig(com.rssl.phizic.jmx.MobileBankConfig.class).getDataSourceReportName();
			return new JDBCActionExecutor(dataSourceName, getSystem()).execute(new GetCardNumbersByPhoneNumberViaReportDBJDBCAction(formattedPhone));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	private String preparePhoneForCardsRequest(String notFormattedPhone)
	{
		if (StringUtils.isBlank(notFormattedPhone))
			throw new IllegalArgumentException("�������� 'phone' �� ����� ���� ������");
		//+7.
		String phone = MBKConstants.MBK_PHONE_NUMBER_FORMAT.translate(notFormattedPhone);

		log.trace("��������� ������� ���� �� ������ ���������� ��������.");
		return phone;
	}

	public BeginMigrationResult beginMigrationErmb(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException
	{
		if (CollectionUtils.isEmpty(cards) && CollectionUtils.isEmpty(phoneMigrateNumbers) && CollectionUtils.isEmpty(phoneDeleteNumbers))
			throw new IllegalArgumentException("��� ��������� ������� �����");

		if (CollectionUtils.isNotEmpty(cards))
		{
			Set<String> cardNumbers = new HashSet<String>();
			for (MbkCard card : cards)
			{
				String cardNumber = card.getNumber();
				if (cardNumbers.contains(cardNumber))
					throw new IllegalArgumentException("����� ���������� ���� ����� � �������������� ��������");
				cardNumbers.add(cardNumber);
			}
		}

		if (migrationType != MigrationType.LIST && CollectionUtils.isNotEmpty(phoneDeleteNumbers))
			throw new IllegalArgumentException("�������� �� �������� ������ ������������ ������ ��� ��������� ��������");

		log.trace("������ ��������� �������� ���");
		try
		{
			BeginMigrationResult result = executeJDBCAction(new BeginMigrationJDBCAction(cards, phoneMigrateNumbers, phoneDeleteNumbers, migrationType));
			clearRegistrationCache(result.getMbkConnectionInfo());
			return result;
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	private void clearRegistrationCache(List<MbkConnectionInfo> mbkConnectionInfo)
	{
		Set<String> cardNumbers = new HashSet<String>();
		Set<String> phones = new HashSet<String>();
		for (MbkConnectionInfo info : mbkConnectionInfo)
		{
			cardNumbers.add(info.getPymentCard());
			phones.add(MBKConstants.MBK_PHONE_NUMBER_FORMAT.format(info.getPhoneNumber()));
			for (InfoCardImpl infoCard : info.getInfoCards())
			{
				cardNumbers.add(infoCard.getPan());
			}
		}

		Cache registration1Cache = CacheProvider.getCache("mobilebank-registration-cache");
		Cache registration2Cache = CacheProvider.getCache("mobilebank-registration2-cache");
		Cache registration3Cache = CacheProvider.getCache("mobilebank-registration3-cache");
		Cache clientByCardCache = CacheProvider.getCache("mobilebank-clientbycard-cache");
		for (String cardNumber : cardNumbers)
		{
			registration1Cache.remove(cardNumber);
			registration2Cache.remove(cardNumber);
			registration3Cache.remove(cardNumber);
			clientByCardCache.remove(cardNumber);
		}

		Cache cardsByPhoneCache = CacheProvider.getCache("mobilebank-cardsbyphone-cache");
		for (String phone : phones)
		{
			cardsByPhoneCache.remove(phone);
		}

		log.info("����� �������� ���-���� ������� ��� �� ������: " + StringUtils.join(cardNumbers, ',')
				+ " � ���������: " + StringUtils.join(phones, ','));
	}

	public List<CommitMigrationResult> commitMigrationErmb(Long migrationId) throws GateException
	{

		if (migrationId == null)
			throw new IllegalArgumentException("�������� 'migrationId' �� ����� ���� ������");

		log.trace("������ ��������� ������������� �������� ���");
		try
		{
			return executeJDBCAction(new CommitMigrationJDBCAction(migrationId));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void rollbackMigration(Long migrationId) throws GateException
	{
		if (migrationId == null)
			throw new IllegalArgumentException("�������� 'migrationId' �� ����� ���� ������");

		log.trace("������ ��������� ������ �������� ���");
		try
		{
			 executeJDBCAction(new RollbackMigrationJDBCAction(migrationId));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void reverseMigration(Long migrationId, ClientTariffInfo clientTariffInfo) throws GateException
	{
		if (migrationId == null)
			throw new IllegalArgumentException("�������� 'migrationId' �� ����� ���� ������");

		log.trace("������ ��������� ������� �������� ���");
		try
		{
			executeJDBCAction(new ReverseMigrationJDBCAction(migrationId, clientTariffInfo));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws GateException
	{
		log.trace("��������� ����������� ������� ���������");
		try
		{
			return executeJDBCAction(new ProviderDisconnectedPhoneJDBCAction(maxResults));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void updateDisconnectedPhonesState(List<Integer> processedPhones) throws GateException
	{
		log.trace("���������� ������� ����������� ������� ���������");
		try
		{
			executeJDBCAction(new UpdaterStateDisconnectedPhoneJDBCAction(processedPhones));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public List<P2PRequest> getMobilePaymentCardRequests() throws GateException
	{
		log.trace("��������� �������� ���, �� ������� ����� ���������� ����� �������� (P2P)");
		try
		{
			return executeJDBCAction(new GetP2PRequestJDBCAction());
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void sendMobilePaymentCardResult(MobilePaymentCardResult cardResult) throws GateException
	{
		log.trace("�������� ������� �� ������ ��� � ��������� ������� ���� �� ������ �������� (P2P)");
		try
		{
			executeJDBCAction(new MobilePaymentCardResultJDBCAction(cardResult));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

// �������������� ���� ��� �� ���������� � ���� �������� ��������� �� "���������� ������" � ������ ������� ������ � ���� ���������� �������(� ��).
// ������������� ������� �� ������������ � �������� ���������� ��� �� ������ ���������� � ����:
// 1) ��� ��������� ���������� � ����� �� ������ ����� ����������� ���� � CRDDWI
// 2) ���� ���� ��������� � �������� ipas, ������� ����������� ������� ����� �� ����� ������ � ���������� ����������� ��� ������������ ��� ������� ���.

	private UserInfo getDirectUserInfoByUserId(String userId) throws SystemException
	{
		if (!ConfigFactory.getConfig(com.rssl.phizic.jmx.MobileBankConfig.class).isUseWay4uDirect())
		{
			return executor2.execute(new GetClientByLoginJDBCAction(userId));
		}

		try
		{
			return Way4uUserInfoService.getInstance().getUserInfoByUserId(userId, true);
		}
		catch (GateTimeOutException e)
		{
			throw new SystemException(e);
		}
	}

	private UserInfo getDirectUserInfoByCardNumber(String cardNumber) throws SystemException
	{
		if (!ConfigFactory.getConfig(com.rssl.phizic.jmx.MobileBankConfig.class).isUseWay4uDirect())
		{
			return executor2.execute(new GetClientByCardNumberJDBCAction(cardNumber));
		}

		try
		{
			return Way4uUserInfoService.getInstance().getUserInfoByCardNumber(cardNumber, true);
		}
		catch (GateTimeOutException e)
		{
			throw new SystemException(e);
		}
	}

	public List<MBKRegistration> getMbkRegistrationsForErmb() throws GateException
	{
		try
		{
			ResultSet resultSet = executeJDBCAction(new GetRegistrationsForErmbJDBCAction());
			if (resultSet == null)
				return new LinkedList<MBKRegistration>();
			return parser.parseResultSet(resultSet);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (SQLException e)
		{
			throw new GateException(e);
		}
	}

	public void confirmMbRegistrationsLoading(List<Long> regIds) throws GateException
	{
		if (regIds.isEmpty())
			return;

		try
		{
			executeJDBCAction(new ConfirmLoadingRegistrationForErmbJDBCAction(regIds));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void sendMbRegistrationProcessingResult(long id, MBKRegistrationResultCode resultCode, String errorDescr) throws GateException
	{
		try
		{
			executeJDBCAction(new RegistrationResultForErmbJDBCAction(id, resultCode.mbkValue, errorDescr));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void updateErmbPhonesInMb(List<ERMBPhone> phones) throws GateException
	{
		log.trace("���������� � �� ��� ������� ���������, ������������������ � ����");
		try
		{
			executeJDBCAction(new ErmbManagePhonesJDBCAction(phones));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public List<MBKPhone> getPhonesForPeriod(Calendar start) throws GateException
	{
		log.trace("�������� ������ ��������� ��������� �� ������� ����");
		try
		{
			return getReportJDBCActionExecutor().execute(new UpdatedPhonesJDBCAction(start));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public List<UESIMessage> getUESIMessagesFromMBK() throws GateException
	{
		log.trace("��������� ��������� ���������������� ���������� ���");
		try
		{
			return executeJDBCAction(new UESIMessagesJDBCAction());
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public void confirmUESIMessages(List<Long> ids) throws GateException
	{
		log.trace("������������� ��������� ��������� ���������������� ���������� ���");
		try
		{
			executeJDBCAction(new UESIConfirmMessagesJDBCAction(ids));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public Map<String, SendMessageError> sendIMSICheck(String... phones)
	{
		return imsiHelper.sendIMSICheck(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId(), phones);
	}

	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws GateException, GateLogicException
	{
		try
		{
			return registrationHelper.getRegPhonesByCardNumbers(cardNumbers, mode);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public List<MobileBankRegistration> getRegistrations4(String cardNumber, GetRegistrationMode mode) throws SystemException
	{
		return registrationHelper.getRegistrations(cardNumber, mode);
	}

	public Boolean generatePassword(String cardNumber) throws GateException
	{
		try
		{
			return executeJDBCAction(new ChangePassByCardNumberJDBCAction(cardNumber));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public Boolean generatePasswordForErmbClient(String cardNumber,  String  phoneNumber) throws GateException
	{
		try
		{
			return executeJDBCAction(new ChangePassByCardNumberForErmbClientJDBCAction(cardNumber, phoneNumber));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	public GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers)
	{
		if (ArrayUtils.isEmpty(cardNumbers))
			return EMPTY_PAYMENT_TEMPLATES_GRESULT;

		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);

		// 1. ��������� ����������� ��
		GroupResult<String, List<MobileBankRegistration>> result1
				= mobileBankService.getRegistrations(false, cardNumbers);
		List<MobileBankRegistration> registrations
				= GroupResultHelper.joinValues(result1);

		// 1.2 ����� ����
		List<MobileBankCardInfo> mbCardInfosList = collectCardInfos(registrations);
		MobileBankCardInfo[] mbCardInfos
				= mbCardInfosList.toArray(new MobileBankCardInfo[mbCardInfosList.size()]);

		// 2. ��������� SMS-�������� ��
		// TODO: ���� ������ ������ result2?
		GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result2
				= mobileBankService.getTemplates(null, mbCardInfos);
		Map<MobileBankCardInfo, List<MobileBankTemplate>> smsTemplates
				= result2.getResults();


		// 3. ����������� SMS-�������� � ������� ��������
		Map<String, List<GatePaymentTemplate>> paymentTemplates
				= buildPaymentTemplates(smsTemplates);

		// 4. ���������� ����������
		GroupResult<String, List<GatePaymentTemplate>> result
				= new GroupResult<String, List<GatePaymentTemplate>>();
		result.setExceptions(result1.getExceptions());
		result.setResults(paymentTemplates);

		return result;
	}

	private List<MobileBankCardInfo> collectCardInfos(List<MobileBankRegistration> registrations)
	{
		List<MobileBankCardInfo> result = new ArrayList<MobileBankCardInfo>(registrations.size());
		for (MobileBankRegistration registration : registrations)
			result.add(registration.getMainCardInfo());
		return result;
	}

	private Map<String, List<GatePaymentTemplate>> buildPaymentTemplates(
			Map<MobileBankCardInfo, List<MobileBankTemplate>> smsTemplates)
	{
		Map<String, List<GatePaymentTemplate>> result
				= new LinkedHashMap<String, List<GatePaymentTemplate>>();

		for (Map.Entry<MobileBankCardInfo, List<MobileBankTemplate>> entry : smsTemplates.entrySet()) {
			MobileBankCardInfo cardInfo = entry.getKey();
			String cardNumber = cardInfo.getCardNumber();
			String phone = cardInfo.getMobilePhoneNumber();

			List<GatePaymentTemplate> gatePaymentTemplatesList = result.get(cardNumber);
			if (gatePaymentTemplatesList == null) {
				gatePaymentTemplatesList = new LinkedList<GatePaymentTemplate>();
				result.put(cardNumber, gatePaymentTemplatesList);
			}

			for (MobileBankTemplate smsTemplate : entry.getValue()) {
				String recipient = smsTemplate.getRecipient();
				for (String payer : smsTemplate.getPayerCodes()) {
					GatePaymentTemplateImpl paymentTemplate
							= buildPaymentTemplate(cardNumber, phone, recipient, payer);
					paymentTemplate.setActive(cardInfo.getStatus() == ACTIVE);
					gatePaymentTemplatesList.add(paymentTemplate);
				}
			}
		}

		return result;
	}

	private GatePaymentTemplateImpl buildPaymentTemplate(
			String cardNumber,
			String phone,
			String recipient,
			String payer)
	{
		String externalId = cardNumber
				+ EXTERNAL_ID_SEPARATOR + MBK_PHONE_NUMBER_FORMAT.translate(phone)
				+ EXTERNAL_ID_SEPARATOR + recipient
				+ EXTERNAL_ID_SEPARATOR + payer
				;

		GatePaymentTemplateImpl paymentTemplate = new GatePaymentTemplateImpl();
		paymentTemplate.setExternalId(externalId);
		paymentTemplate.setCardNumber(cardNumber);
		paymentTemplate.setPhone(phone);
		paymentTemplate.setRecipientCode(recipient);
		paymentTemplate.setPayerCode(payer);

		return paymentTemplate;
	}

	///////////////////////////////////////////////////////////////////////////

	public GatePaymentTemplate getPaymentTemplate(String externalId)
			throws GateException, GateLogicException
	{
		if (StringHelper.isEmpty(externalId))
			throw new IllegalArgumentException("Argument 'externalId' cannot be empty");

		// 1. ���������� ������� ID
		String[] chunks = StringUtils.split(externalId, EXTERNAL_ID_SEPARATOR);
		if (chunks.length != 4)
			throw new GateException("������� ID ������� ������� ����� ������������ ���");
		String cardNumber = chunks[0];
		String phone = chunks[1];
		String recipient = chunks[2];
		String payer = chunks[3];

		// 2. ������� ���������� �� �����
		MobileBankCardInfo cardInfo = getCardInfo(cardNumber, phone);
		if (cardInfo == null)
			return null;

		// 3. ������ ������ � ��
		List<MobileBankTemplate> templates = getPaymentTemplates(cardInfo);
		if (CollectionUtils.isEmpty(templates))
			return null;
		for (MobileBankTemplate smsTemplate : templates) {
			boolean found = smsTemplate.getRecipient().equals(recipient);
			found=found && ArrayUtils.contains(smsTemplate.getPayerCodes(), payer);
			// 4.A � �� ���� ������ � ��������� ID
			if (found) {
				GatePaymentTemplateImpl paymentTemplate
						= buildPaymentTemplate(cardInfo.getCardNumber(), phone, recipient, payer);
				paymentTemplate.setActive(cardInfo.getStatus() == ACTIVE);
				return paymentTemplate;
			}
		}

		// 4.B � �� ����������� ������ � ��������� ID
		return null;
	}

	private MobileBankCardInfo getCardInfo(String cardNumber, String phoneNumber)
			throws GateException, GateLogicException
	{
		PhoneNumber phone = PhoneNumber.fromString(phoneNumber);

		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);

		try
		{


			GroupResult<String, List<MobileBankRegistration>> result2
					= mobileBankService.getRegistrations(false, cardNumber);
			List<MobileBankRegistration> registrations = GroupResultHelper.getOneResult(result2);
			for (MobileBankRegistration registration : registrations) {
				MobileBankCardInfo cardInfo = registration.getMainCardInfo();
				PhoneNumber registrationPhone = PhoneNumber.fromString(cardInfo.getMobilePhoneNumber());
				if (registrationPhone.equals(phone))
					return cardInfo;
			}
			return null;
		}
		catch (SystemException ex)
		{
			throw new GateException(ex);
		}
		catch (LogicException ex)
		{
			throw new GateLogicException(ex);
		}
	}

	private List<MobileBankTemplate> getPaymentTemplates(MobileBankCardInfo cardInfo)
			throws GateException
	{
		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result1
					= mobileBankService.getTemplates(null, cardInfo);
			return GroupResultHelper.getOneResult(result1);
		}
		catch (SystemException ex)
		{
			throw new GateException(ex);
		}
		catch (LogicException ex)
		{
			throw new GateException(ex);
		}
	}
}
