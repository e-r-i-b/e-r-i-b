package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author chegodaev
 * @ created 28.07.14
 * @ $Author$
 * @ $Revision$
 ����������� XML-�����: ��������� ��������� � �������� �����
 */
public class TransformDataUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static String transform(String message, String messageType) throws BusinessException
	{
		Document document = null;
		if(message==null || message.length()==0)
			return null;

		try
		{
			if (messageType.equals(com.rssl.phizic.logging.messaging.System.jdbc.toValue()))
				return message;

			document = XmlHelper.parse(message);
		}
		catch (Exception e)
		{
			log.warn("�� ������� ������������� ������ � XMl-�������������: " + e.getMessage(), e);
			//���� �� ����� ��������� ���������, �� ���������� ��� ����
			return message;
		}

		try
		{
			StringWriter stringWriter = new StringWriter();
			InnerSerializer serial = new InnerSerializer(stringWriter);
			serial.serialize(document);
			return XmlHelper.unescape(stringWriter.toString());
		}
		catch(IOException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
