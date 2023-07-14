package com.rssl.phizgate.common.basket;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


/**
 * @author osminin
 * @ created 13.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������������ �������������� ���������� ��������� ����������� "������� ��������"
 */
public class RequisitesHelper
{
	/**
	 * ������������ ������ ����� � ������
	 * @param fields ������ �����
	 * @return ������ � ��������� �����
	 */
	public static String serialize(List<? extends Field> fields) throws Exception
	{
		if (fields == null)
		{
			return null;
		}

		RequisitesSerializer serializer = new RequisitesSerializer(fields);
		return serializer.serialize();
	}

	/**
	 * ��������������� ������ � ������ �����
	 * @param data ������ � ������� � �����, ���������� ������� serialize
	 * @return ����������������� ������.
	 */
	public static List<Field> deserialize(String data) throws DocumentException
	{
		if (StringHelper.isEmpty(data))
		{
			return Collections.EMPTY_LIST;
		}
		RequisitesSAXSource requisitesSAXSource = new RequisitesSAXSource(data);
		return requisitesSAXSource.getSource();
	}

	/**
	 * �������� ����� �� ����������
	 * @param data ��������� � ���� xml
	 * @return �������� ������� �����
	 * @throws DocumentException
	 */
	public static BigDecimal getAmount(String data) throws DocumentException
	{
		List<Field> fields = deserialize(data);
		if(CollectionUtils.isEmpty(fields))
			return null;

		for(Field field : fields)
		{
			if(field.isMainSum())
				return new BigDecimal((String) field.getValue());
		}

		return null;
	}
}
