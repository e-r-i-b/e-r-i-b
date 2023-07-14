package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.operations.OperationBase;

import java.util.Arrays;
import java.util.List;

/**
 * �������� ��������� ������ ���������
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ListSegmentOperation extends OperationBase
{
	/**
	 * @return ������ ���� ���������
	 */
	public List<Segment> getAllSegments()
	{
		return Arrays.asList(Segment.values());
	}
}
