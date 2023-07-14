package com.rssl.phizic.business.dictionaries.ageRequirement;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * @author EgorovaA
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 */
public class AgeRequirementService extends SimpleService
{
	private static final String PENSION_CODE = "1";

	/**
	 * ����� ������ � ���������� ������������ �� ���� �������� (1-���������, 0-�����������)
	 * @param code - ��� ��������
	 * @return ������ � ���������� ������������
	 * @throws BusinessException
	 */
	public AgeRequirement getAgeRequirementBySegment(String code) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AgeRequirement.class);
		Calendar calendar = Calendar.getInstance();
		criteria.add(Expression.le("dateBegin", calendar));
		criteria.add(Expression.eq("code", code));
		return findSingle(criteria);
	}

	public AgeRequirement getPensionRequirement() throws BusinessException
	{
		return getAgeRequirementBySegment(PENSION_CODE);
	}
}
