package com.rssl.common.forms.xslt;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.DateHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.util.Map;
import java.util.Date;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.12.2005
 * @ $Author: niculichev $
 * @ $Revision: 52919 $
 */

public class FormDataBuilder
{
	// Result document
	private Document result;
    private Element  resultRoot;

	/**
	 * default ctor
	 */
    public FormDataBuilder()
    {
	    try
	    {
		    result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		    resultRoot = result.createElement("form-data");
		    result.appendChild(resultRoot);
		    resultRoot = result.getDocumentElement();
	    }
	    catch (ParserConfigurationException e)
	    {
		    throw new RuntimeException(e);
	    }
    }

    /**
     * �������� ���� � ����� ������ �����
     * @param name ��� ����
     * @param value ��������
     */
    private void appendField(String name, Object value)
    {
        String stringValue = value == null ? null : nullSafeFormat(value);
        XmlHelper.appendSimpleElement(resultRoot, name, stringValue);
    }

	private String nullSafeFormat(Object value)
	{
		if(value instanceof String)
			return ((String) value).replaceAll("\r\n", "\n");   // �����, ����� ��������� ������������� ������� ����� � xslt
		else if (value instanceof Date)
			return DateHelper.toXMLDateFormat((Date) value);
		else
			return String.valueOf(value);
	}

	/**
     * �������� ���� � ����� ������ �����
     * @param name ��� ����
     * @param value ��������
	 * @param attr ��������� ����
     */
    public void appendField(String name, String value, Map<String, String> attr)
    {
        Element newEl = XmlHelper.appendSimpleElement(resultRoot, name, value);
	    newEl.setAttribute("changed", attr.get("changed"));
	    newEl.setAttribute("masked", attr.get("masked"));
    }

	/**
	 * �������� ��� ���� �����. ������������ � �������� �������������� - ������ ���������
	 *
	 * @param form ����� ��� ��������� ������ �����.
	 * @param valuesSource �������� ��������.
	 * @return this
	 */
	public FormDataBuilder appentAllFields(Form form, FieldValuesSource valuesSource)
	{
		for (Field field : form.getFields())
		{
			String fieldName = field.getName();
			Object fieldValue = valuesSource.getValue(fieldName);
			appendField(fieldName, fieldValue);
		}

		return this;
	}

	/**
	 * �������� ��� ���� �����.
	 * @param form ����� ��� ��������� ������ �����
	 * @param valuesMap �������� ��������
	 * @return this
	 */
	public FormDataBuilder appentAllFileds(Form form, Map<String, Object> valuesMap)
	{
		for (Field field : form.getFields())
		{
			String fieldName = field.getName();
			Object fieldValue = valuesMap.get(fieldName);
			appendField(fieldName, fieldValue);
		}
		return this;
	}

    /**
     * @return ��������� ����������
     */
    public Source getFormData()
    {
        return new DOMSource(result);
    }
}
