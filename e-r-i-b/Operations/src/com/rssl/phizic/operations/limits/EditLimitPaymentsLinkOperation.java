package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.link.LimitPaymentsLink;
import com.rssl.phizic.business.limits.link.LimitPaymentsLinkService;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author vagin
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditLimitPaymentsLinkOperation extends EditDictionaryEntityOperationBase<List<LimitPaymentsLink>, DepartmentRestriction>
{
	private static final LimitPaymentsLinkService limitPaymentsLinkService = new LimitPaymentsLinkService();
	private static final GroupRiskService groupRiskService = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();

	private List<LimitPaymentsLink> paymentLinkLimit = new ArrayList<LimitPaymentsLink>();
	private List<GroupRisk> groupRisks = new ArrayList<GroupRisk>();

	public List<LimitPaymentsLink> getEntity() throws BusinessException, BusinessLogicException
	{
		return Collections.unmodifiableList(paymentLinkLimit);
	}

	/**
	 * ������������� �������� ������� ����� ��������(��� ������ ������� �������� ����������)
	 * @param departmentId ������������� �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long departmentId) throws BusinessException, BusinessLogicException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException(String.format("�������������: id = %s �� �������� ��������� ��� �������� ������������", departmentId));

		//������� ����� �������� ���� ��� �� �������� ���������
		Department department = departmentService.findById(departmentId, getInstanceName());
		paymentLinkLimit = limitPaymentsLinkService.findAll(department.getRegion(), getInstanceName());
		if (CollectionUtils.isEmpty(paymentLinkLimit))
		{
			paymentLinkLimit = LimitHelper.createPaymentLimitLinks(department.getRegion());
		}

		groupRisks = groupRiskService.getAllGroupsRisk(getInstanceName());
	}

	public void doSave() throws BusinessException
	{
		limitPaymentsLinkService.addOrUpdateList(paymentLinkLimit, getInstanceName());
	}

	/**
	 * @return ������ ���� ����� ������.
	 */
	public List<GroupRisk> getGroupRisks()
	{
		return groupRisks;
	}

	/**
	 * ���������� ������ ����� �� ��������������.
	 *
	 * @param index �������������.
	 * @return ������ �����.
	 */
	public GroupRisk getGroupRiskById(Long index) throws BusinessException
	{
		for (GroupRisk groupRisk : groupRisks)
		{
			if (groupRisk.getId().equals(index))
				return groupRisk;
		}

		throw new BusinessException("�� ������� ������ ����� � ��������������� " + index);
	}
}
