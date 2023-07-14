package com.rssl.phizic.operations.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.operations.restrictions.AutoPaymentRestriction;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetAutoPaymentInfoOperation extends OperationBase<AutoPaymentRestriction> implements ViewEntityOperation
{
	private static final DepartmentService departmentService = new DepartmentService();

	private AutoPaymentLink link;
	private Department department;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		AutoPaymentRestriction restriction = getRestriction();
		link = PersonContext.getPersonDataProvider().getPersonData().getAutoPayment(id);

		if (!restriction.accept(link) )
			throw new BusinessLogicException("� ��� ��� ���� ������� �� �������� ������� �����������.");

		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		department = departmentService.findById(departmentId);
	}

	/**
	 * �������� ������ ���������� ������� �� ��������� ������
	 * @param fromDate ��������� ����
	 * @param toDate �������� ����
	 * @return ������ ����������
	 */
	public List<ScheduleItem> getScheduleItems(Calendar fromDate, Calendar toDate) throws BusinessException, BusinessLogicException
	{
		try
		{
			AutoPaymentService service = GateSingleton.getFactory().service(AutoPaymentService.class);
			return service.getSheduleReport(link.getValue(), fromDate, toDate);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * ���������� ������ ����������� � ������ ���, ���� ���� ������
	 * @param fromDate  - ��������� ���� (���.)
	 * @param toDate    - �������� ���� (���.)
	 * @return  - ��� ��� ������� � ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<ScheduleItem> getScheduleItemsMobile(Calendar fromDate, Calendar toDate) throws BusinessException, BusinessLogicException
	{
		List<ScheduleItem> scheduleItems = null;

		if (fromDate != null && toDate != null)
			scheduleItems = getScheduleItems(fromDate, toDate);
		else if(fromDate == null && toDate == null)
			scheduleItems = getAllScheduleReport();
		else
			throw new BusinessLogicException("������� ��� ����: ������ � ����� �������");

		return scheduleItems;
	}

	/**
	 * �������� ���� ������ ���������� �����������
	 * @return ���� ������ ����������
	 */
	public List<ScheduleItem> getAllScheduleReport() throws BusinessException, BusinessLogicException
	{
		try
		{
			AutoPaymentService service = GateSingleton.getFactory().service(AutoPaymentService.class);
			return service.getSheduleReport(link.getValue());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * �������� ���������������/������������� ��������
	 * @return ���������������/������������� ��������.
	 */
	public AutoPaymentLink getEntity() throws BusinessException, BusinessLogicException
	{
		return link;
	}

	public Department getCurrentDepartment() throws BusinessException
	{
		return department;
	}

	public Department getTopLevelDepartment() throws BusinessException
	{
		return departmentService.getTB(department);
	}
}
