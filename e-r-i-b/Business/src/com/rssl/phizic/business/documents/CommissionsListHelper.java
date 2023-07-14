package com.rssl.phizic.business.documents;

import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

import static org.apache.commons.lang.StringUtils.strip;

/**
 * @author vagin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ��������������� ��������.
 */
public class CommissionsListHelper
{
	/**
	 * ��������� ���������� �������������
	 * @param document - ������
	 * @return WriteDownOperations.xml
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static Element getCommissionDictionary(AbstractPaymentDocument document) throws Exception
	{
		List<WriteDownOperation> operations = document.getWriteDownOperations();
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("Operations");
		if (operations != null)
		{
			BigDecimal chargeAmount = BigDecimal.ZERO;
			for (WriteDownOperation operation : operations)
			{
				if(operation.getTurnOver().equals("RECEIPT") || isEspecialOperation(operation))
				{
					builder.openEntityTag("Operation");
					builder.createEntityTag("Name", StringHelper.isNotEmpty(operation.getOperationName()) ? operation.getOperationName() : "����������");
					builder.createEntityTag("TurnOver", operation.getTurnOver());
					builder.createEntityTag("CurAmount", operation.getCurAmount().getDecimal().toPlainString());
					builder.createEntityTag("CurAmountCurrency", operation.getCurAmount().getCurrency().getCode());
					builder.closeEntityTag("Operation");
				}
				//�������� ������������� ������� � ����.
				else
				{
					chargeAmount = chargeAmount.add(operation.getCurAmount().getDecimal());
				}
			}
			if (chargeAmount.compareTo(BigDecimal.ZERO) > 0)
			{
				builder.openEntityTag("Operation");
				builder.createEntityTag("Name", "��������");
				builder.createEntityTag("TurnOver", "CHARGE");
				builder.createEntityTag("CurAmount", chargeAmount.toPlainString());
				//������ ���� ������������� ����������. ���� ������ � ��� ������� - ������������� ����� ����.
				builder.createEntityTag("CurAmountCurrency", operations.get(0).getCurAmount().getCurrency().getCode());
				builder.closeEntityTag("Operation");
			}
		}

		builder.closeEntityTag("Operations");
		return XmlHelper.parse(builder.toString()).getDocumentElement();
	}

	/**
	 * @return ���������� ������ ���������� ������������� ��������.
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static Element getEmptyCommissionDictionary() throws IOException, SAXException, ParserConfigurationException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("Operations");
		builder.closeEntityTag("Operations");

		return XmlHelper.parse(builder.toString()).getDocumentElement();
	}

	/**
	 * �������� �� �������� "������": "��������� ������","�������� �����" - �� ��������� � �����������, ������������ ��� ����� ��������/���������� ��� �������� �������.
	 * @param operation - �������������� ��������
	 * @return true/false
	 */
	private static boolean isEspecialOperation(WriteDownOperation operation)
	{
		return StringUtils.equalsIgnoreCase(strip(operation.getOperationName()), strip(WriteDownOperationHelper.PARTIAL_ISSUE_OPERATION_NAME)) || StringUtils.equalsIgnoreCase(strip(operation.getOperationName()), strip(WriteDownOperationHelper.ACCOUNT_CLOSING_OPERATION_NAME));
	}
}
