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
 * ������ ��� ������ � ���������� �����������
 * @author Rtischeva
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsFormatHelper
{
	private static final String CRYPTO_PROVIDER_NAME = "JCP";

	/**
	 * ������ � ���������� ����� �������� ��������� �� XSD-�����
	 * @param payOrdersXml - ������ � XML-��������� ������ �������� ��������� (PacketEPD)
	 * @param xsdFile - ���� � ����� XSD-�����
	 * @return DOM-�������� � ������� �������� ��������� (PacketEPD)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static Document parsePayOrdersXml(String payOrdersXml, String xsdFile) throws BusinessException, ValidateException
	{
		if (StringHelper.isEmpty(payOrdersXml))
			throw new IllegalArgumentException("�������� 'payOrdersXml' �� ����� ���� ������");

		Schema schema;
		try
		{
			schema = XmlHelper.schemaByResourceName(xsdFile);
		}
		catch (SAXException ex)
		{
			throw new BusinessException("�� ������� ��������� XSD-����� " + xsdFile, ex);
		}

		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setSchema(schema);
			// �������� ��������� ����������� ��� XML
			factory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();

			return documentBuilder.parse(new InputSource(new StringReader((payOrdersXml))));
		}
		catch (SAXParseException ex)
		{
			// ���� �� �������/��������� xml
			throw new ValidateException(ExceptionUtil.formatExceptionString(ex), ex);
		}
		catch (SAXException ex)
		{
			// ���� �� �������/��������� xml
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
	 * ��������� ��� ��������� ���������
	 * @param payOrderTag - ������ � XML-��������� ��������� ��������� (��� ED101)
	 * @param certId - id ����������� ��������� �����
	 * @return true, ���� �������� ��������� ������������� �������, false �����
	 */
	public static boolean checkPayOrdersPackDigitalSignature(String payOrderTag, String certId)
	{
		if (StringHelper.isEmpty(payOrderTag))
			throw new IllegalArgumentException("�������� 'payOrderTag' �� ����� ���� ������");

		// 1. ������� ��� ���
		String signatureTag = new Scanner(payOrderTag).next("<dsig:SigValue>", "</dsig:SigValue>");

		// 2. �� ���� ��� ������� ���-������
		String signatureAsBase64 = signatureTag.substring(
				"<dsig:SigValue>".length(), signatureTag.length()-"</dsig:SigValue>".length());

		// 3. ��������� ��� �� ������
		Signature signature = Signature.fromBase64(signatureAsBase64);

		// 4. ������� ��� ��������� ��������� ��� ���� ���
		String payOrderTagWithoutSignature = StringUtils.remove(payOrderTag, signatureTag);

		// 5. �������� ���
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
	 * ������������� � json-������ �������������� ���� ������
	 * @param additionalFields  - ���. ���� � ���� ���� "���� -> ��������"
	 * @return json-������ � ���. ������ (never null, can be empty)
	 */
	public static String serializeAdditionalFields(Map<String, String> additionalFields)
	{
		if (MapUtils.isEmpty(additionalFields))
			return "";
		Gson gson = BasicGsonSingleton.getGson();
		return gson.toJson(additionalFields);
	}

	/**
	 * ��������������� �� json-������ �������������� ���� ������
	 * @param additionalFieldsJsonString - json-������, ���������� ���. ����
	 * @return ���� "���� -> ��������" (never null, can be empty)
	 */
	public static Map<String, String> deserializeAdditionalFields( String additionalFieldsJsonString)
	{
		if (StringHelper.isEmpty(additionalFieldsJsonString))
			return Collections.emptyMap();
		Gson gson = BasicGsonSingleton.getGson();
		return gson.fromJson(additionalFieldsJsonString, Map.class);
	}

	/**
	 * ���������� ����� ������� RurPayment. ����������� � CHG087120
	 * @param formName - ������� �����
	 * @return ����� �������
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
