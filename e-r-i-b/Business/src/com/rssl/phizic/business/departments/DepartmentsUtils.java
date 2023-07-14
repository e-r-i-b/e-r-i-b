package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 05.12.14
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentsUtils
{
	private static DepartmentService departmentService = new DepartmentService();

	/**
	 * �������� ����� ��������� �� �� CB_CODE � ������ ���������.
	 * ������ ���� @CbCode �����:
	 * ��� �������� � 2 �������
	 * ��� ������������� ����� � 2 �������
	 * ��� ��� � 4 �������
	 * ��� ���� ����������� ������ ��� ��������, ��� ������������� � ����������� ������
	 * @param cbCode ������ ����
	 * @return ����� �������� TB � ����������� ������.
	 */
	public static String getTBByCbCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("�������� ���� �� ����� ���� null.");
		}

		return getTbBySynonymAndIdentical(getCutTBByCBCode(cbCode));
	}

	/**
	 * �������� ����� �� �� CBCode
	 * @param cbCode CBCode
	 * @return ����� ��
	 */
	public static String getCutTBByCBCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("�������� ���� �� ����� ���� null.");
		}

		return cbCode.substring(0, 2);
	}

	/**
	 * �������� �� �� ��������������� �������������
	 * @param departmentId ������������� �������������
	 * @return �� � ������ ��������
	 * @throws BusinessException
	 */
	public static String getTbByDepartmentId(Long departmentId) throws BusinessException
	{
		if (departmentId == null)
		{
			throw new IllegalArgumentException("������������� ������������� �� ����� ���� null.");
		}

		List<Code> codes = departmentService.getCodesByDepartmentIds(Collections.singletonList(departmentId));

		if (CollectionUtils.isEmpty(codes))
		{
			throw new BusinessException("�� ������� ������������� ������������ �� id " + departmentId);
		}

		String tb = ((ExtendedCodeImpl) codes.get(0)).getRegion();
		return getTbBySynonymAndIdentical(tb);
	}

	/**
	 * @param tb �������
	 * @return ����� ��������� ��
	 */
	public static String getTbBySynonymAndIdentical(String tb)
	{
		if (StringHelper.isEmpty(tb))
		{
			throw new IllegalArgumentException("����� �������� �� ����� ���� null.");
		}

		String tbBySynonym = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(tb);
		return StringHelper.addLeadingZeros(tbBySynonym, 2);
	}
}
