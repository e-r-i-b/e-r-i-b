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
     * ƒобавить поле в набор данных формы
     * @param name им€ пол€
     * @param value значение
     */
    private void appendField(String name, Object value)
    {
        String stringValue = value == null ? null : nullSafeFormat(value);
        XmlHelper.appendSimpleElement(resultRoot, name, stringValue);
    }

	private String nullSafeFormat(Object value)
	{
		if(value instanceof String)
			return ((String) value).replaceAll("\r\n", "\n");   // нужно, чтобы кооректно обрабатывалс€ перенос строк в xslt
		else if (value instanceof Date)
			return DateHelper.toXMLDateFormat((Date) value);
		else
			return String.valueOf(value);
	}

	/**
     * ƒобавить поле в набор данных формы
     * @param name им€ пол€
     * @param value значение
	 * @param attr аттрибуты пол€
     */
    public void appendField(String name, String value, Map<String, String> attr)
    {
        Element newEl = XmlHelper.appendSimpleElement(resultRoot, name, value);
	    newEl.setAttribute("changed", attr.get("changed"));
	    newEl.setAttribute("masked", attr.get("masked"));
    }

	/**
	 * ƒобавить все пол€ формы. »спользуетс€ в процессе редактировани€ - данные невалидны
	 *
	 * @param form форма дл€ получени€ списка полей.
	 * @param valuesSource источник значений.
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
	 * ƒобавить все пол€ формы.
	 * @param form форма дл€ получени€ списка полей
	 * @param valuesMap источник значений
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
     * @return результат построени€
     */
    public Source getFormData()
    {
        return new DOMSource(result);
    }
}
