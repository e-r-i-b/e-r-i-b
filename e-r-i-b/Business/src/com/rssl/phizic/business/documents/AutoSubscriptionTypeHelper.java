package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionTypeSAXSource;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaySchemeBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * �������������� ���� ������������ � ��� � �������
 * @author niculichev
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionTypeHelper
{
	public static String serialize(Map<AutoSubType, Object> autoSubShemes) throws DocumentException
	{
		if(autoSubShemes == null)
			return null;

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("entity-list", Collections.singletonMap("name", "auto-sub-payment-types"));

		for(AutoSubType key : autoSubShemes.keySet())
			builder.append(((AutoPaySchemeBase) autoSubShemes.get(key)).getParametersByXml());

		builder.closeEntityTag("entity-list");

		return builder.toString();

	}

	/**
	 * ��������������� ������ � ������ �����
	 * @param autoSubShemes ������ � ������� � �����, ���������� ������� serialize
	 * @return ����������������� ������.
	 */
	public static Map<AutoSubType, Object> deserialize(String autoSubShemes) throws DocumentException
	{
		if (StringHelper.isEmpty(autoSubShemes))
		{
			return null;
		}

		AutoSubscriptionTypeSAXSource AutoSubSAXSource = new AutoSubscriptionTypeSAXSource(autoSubShemes);
		return AutoSubSAXSource.getSource();
	}
}
