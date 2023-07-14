package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 22.02.2011
 * @ $Author$
 * @ $Revision$
 * ��������� ������������� ���� ���������.
 */
class CPFLRequisiteField
{
	//������ �������� ���� templates �� ���� ����� ����.
	private static final Map<String, FieldDataType> templatesMap = new HashMap<String, FieldDataType>();
	//������ �������� ���� templates �� ��������� ���������� ��������(��� ���������).
	private static final Map<String, String> templatesRegexpsMap = new HashMap<String, String>();

	static
	{
		templatesMap.put("D", FieldDataType.integer);
		templatesMap.put("M", FieldDataType.integer);
		templatesMap.put("G", FieldDataType.integer);
		templatesMap.put("9", FieldDataType.integer);
		templatesMap.put("R", FieldDataType.integer);
		templatesMap.put("K", FieldDataType.integer);
		templatesMap.put("T", FieldDataType.list);
		templatesMap.put("U", FieldDataType.list);
		templatesMap.put("P", FieldDataType.string);//����� � ������
		templatesMap.put("A", FieldDataType.string);//����� � ������
		templatesMap.put("N", FieldDataType.string);//����� � �����		
		templatesMap.put("X", FieldDataType.string);//����� �������� �������
		templatesMap.put("S", FieldDataType.string);//����� �������� �������

		templatesRegexpsMap.put("D", "\\d");
		templatesRegexpsMap.put("M", "\\d");
		templatesRegexpsMap.put("G", "\\d");
		templatesRegexpsMap.put("9", "\\d");
		templatesRegexpsMap.put("R", "\\d");
		templatesRegexpsMap.put("K", "\\d");
		templatesRegexpsMap.put("P", "[\\d ]");//����� � ������
		templatesRegexpsMap.put("A", "[a-zA-Z�-��-� ]");//����� � ������
		templatesRegexpsMap.put("N", "[a-zA-Z�-��-�\\d]");//����� � �����
		templatesRegexpsMap.put("X", ".");//����� �������� �������
		templatesRegexpsMap.put("S", ".");//����� �������� �������
	}

	private String prefix;
	private String postfix;
	private Long maxLength;
	private Long minLength;
	private String template;
	private List<String> menu;

	CPFLRequisiteField(Element element) throws GateException
	{
		//���� ���� template �������������� ����� length, �� ������������ ���������� �������� ��� ����� ������������
		//��������� length. ���� ���� length �����������, �� ������������ ���������� �������� ��� �����
		//������������ ������ ������� template. ���� template �� ����� �������� �� ���������� ��������� ��������
		template = XmlHelper.getSimpleElementValue(element, "template");
		int templateLength = template.length();
		if (templateLength > 1)
		{
			template = template.substring(0, 1);
		}
		if (!templatesMap.containsKey(template))
		{
			throw new GateException("����������� ��� ������� " + template);
		}

		if ("U".equals(template))
		{
			menu = new ArrayList<String>();
			try
			{
				XmlHelper.foreach(element, "./menu", new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						menu.add(element.getTextContent());
					}
				});
			}
			catch (Exception e)
			{
				throw new GateException(e);
			}
		}
		else
		{
			//������ ���� �� ������������ ��� template = �U�
			String lengthValue = XmlHelper.getSimpleElementValue(element, "length");
			maxLength = StringHelper.isEmpty(lengthValue) ? templateLength : Long.valueOf(lengthValue);
			String minlengthValue = XmlHelper.getSimpleElementValue(element, "minLength");
			minLength = StringHelper.isEmpty(minlengthValue) ? 0 : Long.valueOf(minlengthValue);
			prefix = XmlHelper.getSimpleElementValue(element, "prefix");
			postfix = XmlHelper.getSimpleElementValue(element, "postfix");
		}
	}

	FieldDataType getType()
	{
		return templatesMap.get(template);
	}

	// ���� ������ � ���� template �������� ������� �������� �� P, A, S, X, �� ���� enteredData ��������
	boolean isRequired()
	{
		return !"P".equals(template) && !"A".equals(template) && !"S".equals(template) && !"X".equals(template);
	}

	String getPrefix()
	{
		return prefix;
	}

	String getPostfix()
	{
		return postfix;
	}

	Long getMaxLength()
	{
		return maxLength;
	}

	Long getMinLength()
	{
		return minLength;
	}

	String getTemplate()
	{
		return template;
	}

	List<String> getMenu()
	{
		return menu;
	}

	/**
	 * @return ���������� ���������, ��������������� ������� ���� (�� ������ ��������� template � ������ ����)
	 */
	String getRegexp()
	{
		if (FieldDataType.list == getType())
		{
			return null; //��� ���������� ������� ��� ��� ���������.
		}
		return getAllowedChars() + "{" + minLength + "," + maxLength + "}";
	}

	/**
	 * @return ��� ���������, ����������� ��������� ���������� ��������.
	 */
	String getAllowedChars()
	{
		String regexp = templatesRegexpsMap.get(template);
		if (regexp == null)
		{
			throw new IllegalStateException("���������� ���������, ����������� ��������� ���������� �������� ������� " + template);
		}
		return regexp;
	}
}
