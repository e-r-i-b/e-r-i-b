package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaySchemeBase
{
	protected static final String CLIENT_HINT   = "clientHint";
	protected static final String FIELD         = "field";
	protected static final String ENTITY        = "entity";

	private Map<String, String> parameters = new HashMap<String, String>();
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getClientHint()
	{
		return getStringValue(CLIENT_HINT);
	}

	public void setClientHint(String clientHint)
	{
		setStringValue(CLIENT_HINT, clientHint);
	}

	/**
	 * ��������� ���������� �����������
	 * @param params ��������� ����� xml
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void setParametersByXml(String params) throws GateLogicException, GateException
	{
		if(StringHelper.isEmpty(params))
			parameters.clear();

		parameters.putAll(new AutoPaymentSettingSAXSource(params).getSource());
	}

	/**
	 * @return ��������� ����������� ����� xml
	 */
	public String getParametersByXml()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * �������� �������� ��������� � ���� String
	 * @param fieldName ��� ��������� �����������
	 * @return ��������
	 */
	protected String getStringValue(String fieldName)
	{
		return parameters.get(fieldName);
	}

	/**
	 * ���������� �������� ��������� � ���� String
	 * @param fieldName ��� ���������
	 * @param value �������� ���������(� ������ null, ��������� �� ����)
	 */
	protected void setStringValue(String fieldName, String value)
	{
		if(value != null)
			parameters.put(fieldName, value);
		else
			parameters.remove(fieldName);
	}

	/**
	 * �������� �������� ��������� � ���� Boolean
	 * @param fieldName ��� ���������
	 * @return ��������
	 */
	protected Boolean getBooleanValue(String fieldName)
	{
		String value = parameters.get(fieldName);
		if(StringHelper.isEmpty(value))
			return null;

		return Boolean.valueOf(value);
	}

	/**
	 * ���������� �������� ����� Boolean
	 * @param fieldName ��� ���������
	 * @param value ��������(� ������ null, ��������� �� ����)
	 */
	protected void setBooleanValue(String fieldName, Boolean value)
	{
		if(value != null)
			parameters.put(fieldName, BooleanUtils.toStringTrueFalse(value));
		else
			parameters.remove(fieldName);
	}

	/**
	 * �������� �������� ��������� � ���� BigDecimal
	 * @param fieldName ��� ���������
	 * @return ��������
	 */
	protected BigDecimal getBigDecimalValue(String fieldName)
	{
		String value = parameters.get(fieldName);
		if(StringHelper.isEmpty(value))
			return null;

		try
		{
			return NumericUtil.parseBigDecimal(StringUtils.deleteWhitespace(value));
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("���������� �������� �������� � BigDecimal", e);
		}
	}

	/**
	 * �������� �������� ��������� � ���� �����
	 * @param enumType ����� �����
	 * @param fieldName ��� ����
	 * @param <T> �������� �����
	 * @return ��������
	 */
	protected <T extends Enum<T>> T getEnumValue(Class<T> enumType, String fieldName)
	{
		String value = parameters.get(fieldName);
		if (StringHelper.isEmpty(value))
			return null;
		else
			return Enum.valueOf(enumType, value);
	}

	/**
	 * ���������� �������� �����
	 * @param fieldName ��� ����
	 * @param value ��������
	 * @param <T> �������� �����
	 */
	protected <T extends Enum<T>> void setEnumValue(String fieldName, T value)
	{
		if(value == null)
			parameters.remove(fieldName);
		else
			parameters.put(fieldName, value.name());
	}

	/**
	 * ���������� �������� ��������� � ���� BigDecimal
	 * @param fieldName ��� ���������
	 * @param value ��������(� ������ null, ��������� �� ����)
	 */
	protected void setBigDecimalValue(String fieldName, BigDecimal value)
	{
		if(value != null)
			parameters.put(fieldName, value.toString());
		else
			parameters.remove(fieldName);
	}

	/**
	 * ��������� ��������� ������������� ���������
	 * @param nameField ��� ���������
	 * @param value ��������
	 * @return ��������� ������������� ��������� ���� <field name="nameField">value</field>
	 * (������ ������ ���� �������� null)
	 */
	protected String fieldBuilder(String nameField, Object value)
	{
		// ���� �������� ���, �� ��� ������ ����������
		if(value == null)
			return StringUtils.EMPTY;

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.createEntityTag(FIELD,
				value.toString(), Collections.singletonMap("name", nameField));
		return builder.toString();
	}
}
