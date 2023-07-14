package com.rssl.phizic.logging.operations.context;

import com.rssl.phizic.utils.StringHelper;

/** ����� ��� ������ � OperationContext
 * @author akrenev
 * @ created 11.10.2011
 * @ $Author$
 * @ $Revision$
 */
public final class OperationContextUtil
{

	private static void setOperationUID(PossibleAddingOperationUIDObject source)
	{
		if (source == null)
		{
			return;
		}
		source.setOperationUID(OperationContext.getCurrentOperUID());
	}

	private static String getOperationUID(PossibleAddingOperationUIDObject source)
	{
		if (source == null)
		{
			return null;
		}
		return source.getOperationUID();
	}

	/**
	 * �������������� operationUID ��������� � ���������
	 * @param source ���������������� ��������
	 */
	public static final void synchronizeObjectAndOperationContext(PossibleAddingOperationUIDObject source)
	{
		String operationUID = getOperationUID(source);
		if (StringHelper.isEmpty(operationUID))
		{
			setOperationUID(source);
			return;
		}
		OperationContext.setCurrentOperUID(operationUID);
	}

	/**
	 * �������������� operationUID ��������� � �������
	 * @param source ���������������� ������
	 */
	public static final void synchronizeObjectAndOperationContext(Object source)
	{
	    if (source instanceof PossibleAddingOperationUIDObject)
	    {
			synchronizeObjectAndOperationContext((PossibleAddingOperationUIDObject) source);
	    }
	}
}
