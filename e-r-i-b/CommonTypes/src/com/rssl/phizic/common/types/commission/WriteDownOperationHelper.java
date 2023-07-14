package com.rssl.phizic.common.types.commission;

import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.lang.StringUtils.strip;

/**
 * @author vagin
 * @ created 14.10.13
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � �������������� ��������.
 */
public class WriteDownOperationHelper
{
	public static final String PARTIAL_ISSUE_OPERATION_NAME = "��������� ������";
	public static final String ACCOUNT_CLOSING_OPERATION_NAME = "�������� �����";

	/**
	 * �������� ����� ���� ������������� � ������ �� �����.
	 * @param operations - ������������� ��������/�����������.
	 * @return money
	 */
	public static Money getCommissionsSum(List<WriteDownOperation> operations)
	{
		Money result = null;
		if (operations != null)
		{
			for (WriteDownOperation operation : operations)
			{
				//��������� ������������� "��������� ������" � "�������� �����"
				if(StringUtils.equalsIgnoreCase(strip(operation.getOperationName()), strip(PARTIAL_ISSUE_OPERATION_NAME)) || StringUtils.equalsIgnoreCase(strip(operation.getOperationName()), strip(ACCOUNT_CLOSING_OPERATION_NAME)))
					continue;
				if(result == null)
					result = new Money(BigDecimal.ZERO, operation.getCurAmount().getCurrency());

				if ("CHARGE".equals(operation.getTurnOver()))
					result = result.add(operation.getCurAmount());
				else if ("RECEIPT".equals(operation.getTurnOver()))
					result = result.sub(operation.getCurAmount());
			}
		}
		return result;
	}
}
