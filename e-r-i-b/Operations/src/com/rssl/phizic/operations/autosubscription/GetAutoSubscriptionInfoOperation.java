package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.autopayments.ScheduleItemByDateComparator;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.operations.ViewEntityOperation;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author basharin
 * @ created 31.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class GetAutoSubscriptionInfoOperation extends AutoSubscriptionOperationBase implements ViewEntityOperation
{
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	protected static final DepartmentService departmentService = new DepartmentService();

	private List<ScheduleItem> scheduleItems;
	private AutoSubscriptionLink link;
	private AutoSubscriptionDetailInfo autoSubscriptionInfo;
	private Department department;
	private String textUpdateSheduleItemsError;
	private boolean updateSheduleItemsError;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		initialize(id, null, null);
	}

	public void initialize(Long id, Calendar startDate, Calendar endDate) throws BusinessException, BusinessLogicException
	{
		super.initialize();

		updateSheduleItemsError = false;
		link = getPersonData().getAutoSubscriptionLink(id);
		autoSubscriptionInfo = link.getAutoSubscriptionInfo();

		if (autoSubscriptionInfo == null)
			throw new BusinessException("Ошибка при получении детальной информации по автоплатежу");

		try
		{
			List<ScheduleItem> scheduleReports = link.getSheduleReport(startDate, endDate);
			Collections.sort(scheduleReports, new ScheduleItemByDateComparator());
			scheduleItems = scheduleReports;
		}
		catch (BusinessLogicException ex)
		{
			updateSheduleItemsError = true;
			textUpdateSheduleItemsError = ex.getMessage();
		}
		catch (BusinessException ex)
		{
			updateSheduleItemsError = true;
			textUpdateSheduleItemsError = "Список платежей временно недоступен. Повторите попытку позже.";
		}
		department = departmentService.findById(getPerson().getDepartmentId());
	}

	public AutoSubscriptionLink getEntity() throws BusinessException, BusinessLogicException
	{
		return link;
	}

	/**
	 * возвращает график исполнения платежа за указанный период.
	 * 
	 * @return график исполнения.
	 */
	public List<ScheduleItem> getScheduleItems() throws BusinessException, BusinessLogicException
	{
		return Collections.unmodifiableList(scheduleItems);
	}

	public Department getCurrentDepartment() throws BusinessException
	{
		return department;
	}

	public Department getTopLevelDepartment() throws BusinessException
	{
		return departmentService.getTB(department);
	}

	public String getTextUpdateSheduleItemsError()
	{
		return textUpdateSheduleItemsError;
	}

	public boolean isUpdateSheduleItemsError()
	{
		return updateSheduleItemsError;
	}

	public AutoSubscriptionDetailInfo getAutoSubscriptionInfo()
	{
		return autoSubscriptionInfo;
	}
}

