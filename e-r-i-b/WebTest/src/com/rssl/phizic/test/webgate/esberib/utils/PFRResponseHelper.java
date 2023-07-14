package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.ws.AnyType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

/**
 * @author gulov
 * @ created 21.02.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRResponseHelper extends BaseResponseHelper
{
	/**
	 * ���������� ������� ������� ��� ��������� ������������� ������� �� ���
	 */
	private static final int RANDOM_RESPONSE_EXISTS = 10;

	/**
	 * ���������� ������� ������� ��� ��������� statusCode
	 */
	private static final int RANDOM_STATUS_CODE = 10;

	/**
	 * ���� xml
	 */
	private static final String XML_FILE_NAME = "com/rssl/phizic/test/webgate/esberib/utils/xml/pfrInfo.xml";

	/**
	 * �������� ���������
	 */
	private static final String NOTE_6_1 = "���-6-1";

	private static final String NOTE_6_2 = "���-6-2";

	private static final String NOTE_6_3 = "���-6-3";

	private static final String NOTE_6_4 = "���-6-4";

	private static final String NOTE_6_5 = "���-6-5";

	private static final String NOTE_6_6 = "���-6-6";

	private static final String TAG_PREFIX = "������������������������/���������_�_���������_���";

	private static final Map<Integer, String> STATUS_CODE_SPEC;

	static
	{
		STATUS_CODE_SPEC = new HashMap<Integer, String>(5);

		STATUS_CODE_SPEC.put(-801, "�� �� ���������������� � ������� ��������������� ������ ����������� �����. " +
			"��� ��������� ������� ���������� ������ ��������� � ����� ������������� ��������� ������.");
		STATUS_CODE_SPEC.put(-802, "�� ������� ������������ ��������� ����� ��������������� �������� �����");
		STATUS_CODE_SPEC.put(-803, "���� ������� � ��������� ���, �� ����� ��� ��� �� ������ c ��������� ������ � ������� �������� (���������)");
		STATUS_CODE_SPEC.put(-804, "�� �� ���������������� � ������� ��������������� ������ ����������� �����. " +
			"��� ��������� ������� ���������� ������ ��������� � ����� ������������� ��������� ������.");
		STATUS_CODE_SPEC.put(-810, "�� ��� ������ ������� ����� �� ����������� �����. ���������� ������");
	}


	public IFXRs_Type createHasInfoInqRs(IFXRq_Type parameters)
	{
		PfrHasInfoInqRq_Type request = parameters.getPfrHasInfoInqRq();
		PfrHasInfoInqRs_Type response_type = new PfrHasInfoInqRs_Type();

		response_type.setRqUID(request.getRqUID());
		response_type.setRqTm(getRqTm());
		response_type.setOperUID(request.getOperUID());

		PfrResult_Type result = new PfrResult_Type();
		result.setSendTime(getStringDateTime(getRandomDate()));
		result.setReceiveTime(getStringDateTime(getRandomDate()));
		result.setResponseExists(getResponseExists());
		response_type.setResult(result);
		response_type.setStatus(getPFRStatus());

		IFXRs_Type response = new IFXRs_Type();
		response.setPfrHasInfoInqRs(response_type);

		return response;
	}

	public IFXRs_Type createGetInfoInqRs(IFXRq_Type parameters) throws Exception
	{
		PfrGetInfoInqRq_Type request = parameters.getPfrGetInfoInqRq();
		PfrGetInfoInqRs_Type response_type = new PfrGetInfoInqRs_Type();

		response_type.setRqUID(request.getRqUID());
		response_type.setRqTm(getRqTm());
		response_type.setOperUID(request.getOperUID());

		PfrResult_Type result = new PfrResult_Type();
		result.setSendTime(getStringDateTime(getRandomDate()));
		result.setReceiveTime(getStringDateTime(getRandomDate()));
		result.setResponseExists(getResponseExists());
		response_type.setResult(result);
		response_type.setStatus(getPFRStatus());
		response_type.setPfrInfo(makePFRInfo());

		IFXRs_Type response = new IFXRs_Type();
		response.setPfrGetInfoInqRs(response_type);

		return response;
	}

	private Object makePFRInfo() throws Exception
	{
		Document document = XmlHelper.loadDocumentFromResource(XML_FILE_NAME);

		String noteType = getNoteType();
		findAndSetElementText(document, TAG_PREFIX + "/����������������", noteType);
		findAndSetElementText(document, TAG_PREFIX + "/�����������������", getStringDate(getRandomDate()));

		findAndSetElementText(document, TAG_PREFIX + "/��������������", "123-123-123 45");
		findAndSetElementText(document, TAG_PREFIX + "/���/�������", "��������");
		findAndSetElementText(document, TAG_PREFIX + "/���/���", "�������");
		findAndSetElementText(document, TAG_PREFIX + "/���/��������", "��������");

		Element prefixElement = XmlHelper.selectSingleNode(document.getDocumentElement(), TAG_PREFIX);

		XmlHelper.appendSimpleElement(prefixElement, "������������������������", getStringDate(getRandomDate()));

		if (!noteType.equals(NOTE_6_6))
		{
			XmlHelper.appendSimpleElement(prefixElement, "���", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "������������������", getRandomDecimal().toString());
			XmlHelper.appendSimpleElement(prefixElement, "���������", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "����������", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_1) || noteType.equals(NOTE_6_2))
		{
			XmlHelper.appendSimpleElement(prefixElement, "��������������", "��������������");
			XmlHelper.appendSimpleElement(prefixElement, "���������������������", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_1))
		{
			XmlHelper.appendSimpleElement(prefixElement, "��������������������", getRandomYear());
			XmlHelper.appendSimpleElement(prefixElement, "�����������������������", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_2) || noteType.equals(NOTE_6_3))
		{
			XmlHelper.appendSimpleElement(prefixElement, "���������������������", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_3) || noteType.equals(NOTE_6_4))
		{
			XmlHelper.appendSimpleElement(prefixElement, "���������������", "���������������");
		}

		if (noteType.equals(NOTE_6_3))
		{
			XmlHelper.appendSimpleElement(prefixElement, "����������������������", getRandomDecimal().toString());
		}

		if (noteType.equals(NOTE_6_5))
		{
			XmlHelper.appendSimpleElement(prefixElement, "�����������������������", getRandomDecimal().toString());
		}

		makeTaxPayment(document);

		Element last = XmlHelper.appendSimpleElement(prefixElement, "������������������������");
		XmlHelper.appendSimpleElement(last, "����������������������");
		XmlHelper.appendSimpleElement(last, "���������������������");
		XmlHelper.appendSimpleElement(last, "���������������������");
		XmlHelper.appendSimpleElement(last, "�������������������");

		XmlHelper.appendSimpleElement(prefixElement, "����������������������������������", String.valueOf(new Random().nextInt(1000) + 1));

		Map<String,String> properties = new HashMap<String, String>();
		properties.put(OutputKeys.INDENT, "yes");
		properties.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
		return new AnyType(XmlHelper.setPropsAndConvertDomToText(document, properties));
	}

	protected BigDecimal getRandomDecimal()
	{
		return super.getRandomDecimal().setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	private void makeTaxPayment(Document document) throws TransformerException
	{
		Random random = new Random();

		int counter = random.nextInt(9) + 1;

		Element prefixElement = XmlHelper.selectSingleNode(document.getDocumentElement(), TAG_PREFIX);

		Element root;

		for (int i = 0; i <= counter; i++)
		{
			root = XmlHelper.appendSimpleElement(prefixElement, "���������������������������");

			XmlHelper.appendSimpleElement(root, "���", getRandomYear());
			XmlHelper.appendSimpleElement(root, "�����������01.01.����", getRandomDecimal().toString());
			XmlHelper.appendSimpleElement(root, "��������������", getRandomDecimal().toString());
		}
	}

	private String getRandomYear()
	{
		Random random = new Random();

		String value = String.valueOf(random.nextInt(10) + 1);

		return "20" + (value.length() < 2 ? "0" + value : value);
	}

	private String getNoteType()
	{
		Random random = new Random();

		int value = random.nextInt(6) + 1;

		return "���-6-" + String.valueOf(value);
	}

	private void findAndSetElementText(Document document, String elementName, String text) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(document.getDocumentElement(), elementName);

		if (element != null)
			setElementText(element, text);
	}

	private void setElementText(Element element, String text)
	{
		String tagText = element.getTextContent();

		if (tagText.equals(""))
			element.setTextContent(text);
	}

	private boolean getResponseExists()
	{
		Random random = new Random();

		int i = random.nextInt(RANDOM_RESPONSE_EXISTS);

		return i == 0 ? false : true;
	}

	private Status_Type getPFRStatus()
	{
		Random random = new Random();

		int i = random.nextInt(RANDOM_STATUS_CODE);

		if (i == 0)
		{
			i = random.nextInt(5);
			int j = 0;

			for (Iterator<Map.Entry<Integer, String>> iterator = STATUS_CODE_SPEC.entrySet().iterator(); iterator.hasNext(); j++)
			{
				if (j == i)
				{
					Map.Entry<Integer, String> entry = iterator.next();

					return new Status_Type(entry.getKey(), entry.getValue(), "", SERVER_STATUS_DESC);
				}
			}
		}
		return new Status_Type(0, "", "", "");
	}

	private String getSNILS()
	{
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		String format = String.format("%%0%dd", 3);
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append("-");
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append("-");
		sb.append(String.format(format, random.nextInt(999) + 1));
		sb.append(" ");
		format = String.format("%%0%dd", 2);
		sb.append(String.format(format, random.nextInt(99) + 1));
		return sb.toString();
	}

	//���������� ���� � ������� 15.03.2011
	protected String getStringDate(Calendar date)
	{
		return new SimpleDateFormat("dd.MM.yyyy").format(DateHelper.toDate(date));
	}
}
