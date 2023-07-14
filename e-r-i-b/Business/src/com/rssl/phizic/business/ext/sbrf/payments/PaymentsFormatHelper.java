package com.rssl.phizic.business.ext.sbrf.payments;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.json.BasicGsonSingleton;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.security.crypto.*;
import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

/**
 * Методы для работы с платежными документами
 * @author Rtischeva
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsFormatHelper
{
	private static final String CRYPTO_PROVIDER_NAME = "JCP";

	/**
	 * Читает и валидирует пакет платёжных поручений по XSD-схеме
	 * @param payOrdersXml - строка с XML-описанием пакета платёжных поручений (PacketEPD)
	 * @param xsdFile - путь к файлу XSD-схемы
	 * @return DOM-документ с пакетом платёжных поручений (PacketEPD)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Document parsePayOrdersXml(String payOrdersXml, String xsdFile) throws BusinessException, ValidateException
	{
		if (StringHelper.isEmpty(payOrdersXml))
			throw new IllegalArgumentException("Аргумент 'payOrdersXml' не может быть пустым");

		Schema schema;
		try
		{
			schema = XmlHelper.schemaByResourceName(xsdFile);
		}
		catch (SAXException ex)
		{
			throw new BusinessException("Не удалось загрузить XSD-схему " + xsdFile, ex);
		}

		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setSchema(schema);
			// включаем поддержку пространств имён XML
			factory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();

			return documentBuilder.parse(new InputSource(new StringReader((payOrdersXml))));
		}
		catch (SAXParseException ex)
		{
			// сбой на разборе/валидации xml
			throw new ValidateException(ExceptionUtil.formatExceptionString(ex), ex);
		}
		catch (SAXException ex)
		{
			// сбой на разборе/валидации xml
			throw new ValidateException(ex);
		}
		catch (ParserConfigurationException ex)
		{
			throw new BusinessException(ex);
		}
		catch (IOException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Проверяет ЭЦП платёжного поручения
	 * @param payOrderTag - строка с XML-описанием платёжного поручения (тег ED101)
	 * @param certId - id сертификата открытого ключа
	 * @return true, если платёжное поручение соответствует подписи, false иначе
	 */
	public static boolean checkPayOrdersPackDigitalSignature(String payOrderTag, String certId)
	{
		if (StringHelper.isEmpty(payOrderTag))
			throw new IllegalArgumentException("Аргумент 'payOrderTag' не может быть пустым");

		// 1. Получим тег ЭЦП
		String signatureTag = new Scanner(payOrderTag).next("<dsig:SigValue>", "</dsig:SigValue>");

		// 2. Из тега ЭЦП выберем ЭЦП-строку
		String signatureAsBase64 = signatureTag.substring(
				"<dsig:SigValue>".length(), signatureTag.length()-"</dsig:SigValue>".length());

		// 3. Прочитаем ЭЦП из строки
		Signature signature = Signature.fromBase64(signatureAsBase64);

		// 4. Получим тег платёжного поручения без тега ЭЦП
		String payOrderTagWithoutSignature = StringUtils.remove(payOrderTag, signatureTag);

		// 5. Проверим ЭЦП
		CryptoProviderFactory cryptoProviderFactory = CryptoProviderHelper.getFactory(CRYPTO_PROVIDER_NAME);
		CryptoProvider cryptoProvider = cryptoProviderFactory.getProvider();
		CheckSignatureResult result = cryptoProvider.checkSignature(certId, payOrderTagWithoutSignature, signature);
		return result.isSuccessful();
	}

	static class Scanner
	{
		private final String data;

		private int index;

		Scanner(String data)
		{
			this.data = data;
			index = 0;
		}

		public String next(String begin, String end) throws NoSuchElementException
		{
			int beginIndex = data.indexOf(begin, index);
			if (beginIndex < 0)
				throw new NoSuchElementException();

			int endIndex = data.indexOf(end, beginIndex + begin.length());
			if (endIndex < 0)
				throw new NoSuchElementException();
			endIndex += end.length();

			index = endIndex;

			return data.substring(beginIndex, endIndex);
		}
	}


	/**
	 * Сериализовать в json-строку дополнительные поля заказа
	 * @param additionalFields  - доп. поля в виде мапы "ключ -> значение"
	 * @return json-строка с доп. полями (never null, can be empty)
	 */
	public static String serializeAdditionalFields(Map<String, String> additionalFields)
	{
		if (MapUtils.isEmpty(additionalFields))
			return "";
		Gson gson = BasicGsonSingleton.getGson();
		return gson.toJson(additionalFields);
	}

	/**
	 * Десериализовать из json-строки дополнительные поля заказа
	 * @param additionalFieldsJsonString - json-строка, содержащая доп. поля
	 * @return мапа "ключ -> значение" (never null, can be empty)
	 */
	public static Map<String, String> deserializeAdditionalFields( String additionalFieldsJsonString)
	{
		if (StringHelper.isEmpty(additionalFieldsJsonString))
			return Collections.emptyMap();
		Gson gson = BasicGsonSingleton.getGson();
		return gson.fromJson(additionalFieldsJsonString, Map.class);
	}

	/**
	 * Возвращает форму платежа RurPayment. Подробности в CHG087120
	 * @param formName - текущая форма
	 * @return форма платежа
	 */
	public static String getActualRurPaymentForm(String formName)
	{
		if (!FormConstants.RUR_PAYMENT_FORM.equals(formName) && !FormConstants.NEW_RUR_PAYMENT.equals(formName))
			return formName;

		Application application = ApplicationConfig.getIt().getApplicationInfo().getApplication();
		if (application == Application.PhizIC)
			return PermissionUtil.impliesServiceRigid(FormConstants.NEW_RUR_PAYMENT) ? FormConstants.NEW_RUR_PAYMENT : FormConstants.RUR_PAYMENT_FORM;

		if (application == Application.PhizIA || application == Application.WSGateListener)
			return formName;

		if (MobileApiUtil.isMobileApiGT(MobileAPIVersions.V9_00))
		{
			if (PermissionUtil.impliesServiceRigid(FormConstants.NEW_RUR_PAYMENT))
			{
				if (PermissionUtil.impliesServiceRigid(FormConstants.RUR_PAYMENT_FORM))
					return formName;
				else
					return FormConstants.NEW_RUR_PAYMENT;
			}
			else
			{
				if (PermissionUtil.impliesServiceRigid(FormConstants.RUR_PAYMENT_FORM))
					return FormConstants.RUR_PAYMENT_FORM;
			}
		}

		return FormConstants.RUR_PAYMENT_FORM;
	}
}
