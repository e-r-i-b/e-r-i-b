package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.operations.restrictions.TbNumberRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author niculichev
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class LimitOperationBase extends EditDictionaryEntityOperationBase<Limit, TbNumberRestriction>
{
	private static final String EXIST_ENTERED_LIMIT_ERROR_MESSAGE   = "¬ систему уже добавлен лимит со статусом \"¬веден\".";
	private static final String EXIST_DRAFT_LIMIT_ERROR_MESSAGE     = "¬ систему уже добавлен лимит со статусом \"„ерновик\". ѕожалуйста, отредактируйте существующий лимит";
	private static final String DATE_LIMIT_ERROR_MESSAGE            = "ƒата начала действи€ лимита не может быть меньше текущей даты";
	private static final String NOT_FOUND_GROUP_RISK                = "Ќевозможно создать кумул€тивный лимит потому, что группа риска с id: %d не найдена";
	private static final String NOT_FOUND_GROUP_RISK_ID             = "ѕустой id группы риска при создании лимита";
	private static final String CAN_EDIT_LIMIT_ERROR_MESSAGE        = "—охранение и подтверждение лимита возможно только в статусе \"„ерновик\"";
	private static final String CAN_ADD_LIMIT_INTERSECTION_ERROR_MESSAGE  = "ƒобавление введенного лимита невозможно, так как имеютс€ иные лимиты в указанные даты и/или период добавлени€ находитс€ в прошлом.";

	protected static final LimitService limitService = new LimitService();
	protected static final GroupRiskService groupRiskService = new GroupRiskService();
	protected static final SimpleService simpleService = new SimpleService();

	protected Limit limit;

	protected void checkCorrectLimit() throws BusinessLogicException, BusinessException
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.MILLISECOND, 0);

		if (limit.getStartDate().compareTo(Calendar.getInstance()) <= 0)
		{
			throw new BusinessLogicException(DATE_LIMIT_ERROR_MESSAGE);
		}

        if (limit.getType() == LimitType.OVERALL_AMOUNT_PER_DAY)
        {
            if (limitService.isLimitsDatesIntersected(limit, getInstanceName()))
                throw new BusinessLogicException(CAN_ADD_LIMIT_INTERSECTION_ERROR_MESSAGE);
        }
        else
        {
            if (isNewLimit() && limitService.isExistLimit(limit, Status.DRAFT, getInstanceName()))
            {
                // в списке может быть только одна запись со статусом черновик
                throw new BusinessLogicException(EXIST_DRAFT_LIMIT_ERROR_MESSAGE);
            }

            // подтверждение и сохранение лимита возможно только дл€ черновиков
            if(!isNewLimit() && limit.getStatus() != Status.DRAFT)
                throw new BusinessException(CAN_EDIT_LIMIT_ERROR_MESSAGE);
        }

	}

	public Limit getEntity() throws BusinessException, BusinessLogicException
	{
		return limit;
	}

	public List<GroupRisk> getAvailableGroupRisk() throws BusinessException
	{
		//если проиходит редакирование лимита то не надо показывать все группы риска т.к. их нельз€ редактировать
		if (!isNewLimit())
			return Collections.singletonList(limit.getGroupRisk());

		return groupRiskService.getAllGroupsRisk(getInstanceName());
	}

	public void setGroupRisk(Long groupRiskId) throws BusinessException
	{
		// группу риска обновл€ем только если создаем новый лимит
		if(limit.getStatus() == null)
		{
			if (groupRiskId == null)
				throw new BusinessException(NOT_FOUND_GROUP_RISK_ID);

			GroupRisk groupRisk = simpleService.findById(GroupRisk.class, groupRiskId, getInstanceName());
			if (groupRisk == null)
				throw new ResourceNotFoundBusinessException(String.format(NOT_FOUND_GROUP_RISK, groupRiskId), GroupRisk.class);

			limit.setGroupRisk(groupRisk);
		}
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		checkCorrectLimit();
		limitService.addOrUpdate(limit, getInstanceName());
	}

	public void confirm() throws BusinessException, BusinessLogicException
	{
		checkCorrectLimit();

		if(limitService.isExistLimit(limit, Status.ENTERED, getInstanceName()))
		{
			// в списке может быть только одна запись со статусом введен
			throw new BusinessLogicException(EXIST_ENTERED_LIMIT_ERROR_MESSAGE);
		}

		limit.setState(LimitState.CONFIRMED);

		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					limitService.updateEndDate(limit, getInstanceName());
					doSave();
					MultiBlockModeDictionaryHelper.updateDictionary(getEntityClass());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	protected boolean isNewLimit()
	{
		return true;
	}
}
