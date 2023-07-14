package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetCountService;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetService;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.PersonTargetComparator;
import com.rssl.phizic.business.pfp.PersonTargetService;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author mihaylov
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPersonTargetOperation extends NotCheckStateEditPfpOperationBase
{
	private static final TargetService targetService = new TargetService();
	private static final PersonTargetService personTargetService = new PersonTargetService();
	private static final PersonTargetComparator PERSON_TARGET_COMPARATOR = new PersonTargetComparator();

	private PersonTarget personTarget;
	private Target dictionaryTarget;

	public void initialize(Long profileId, Long personTargetId) throws BusinessException, BusinessLogicException
	{
		initializePerson();
		if (profileId == null || profileId == 0)
			throw new IllegalArgumentException("Идентификатор планирования не может быть null");

		personalFinanceProfile = pfpService.getProfileById(profileId);

		if (personalFinanceProfile == null)
			throw new BusinessLogicException("Планирование с id=" + profileId + " не найдено.");

		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("Клиент с id = " + person.getId() + " не имеет доступа к ПФП с id = " + personalFinanceProfile.getId());

		if(personTargetId != null && personTargetId != 0)
			personTarget = getTargetById(personTargetId);
		
		if (personTargetId == null || personTargetId == 0 || personTarget == null)
		{
		    if(getTargetCount() >= getMaxTargetCount())
				throw new BusinessLogicException("Вы не можете добавить новую цель, потому что Вы создали максимальное количество целей.");

			personTarget = new PersonTarget();
			personalFinanceProfile.addPersonTarget(personTarget);
		}
	}

	public void initializeForEdit(Long profileId, Long personTargetId, Long dictionaryTargetId) throws BusinessException, BusinessLogicException
	{
		initialize(profileId,personTargetId);
		if(dictionaryTargetId == null || dictionaryTargetId == 0)
			throw new IllegalArgumentException("Идентификатор планирования не может быть null");
		dictionaryTarget = targetService.getById(dictionaryTargetId, null);
	}


	private PersonTarget getTargetById(Long targetId) throws BusinessLogicException
	{
		PersonTarget personTarget = null;
		for(PersonTarget target: personalFinanceProfile.getPersonTargets())
			if(targetId.equals(target.getId()))
				personTarget = target;
		return personTarget;
	}

	/**
	 * @return возвращаем цель клиента
	 */
	public PersonTarget getPersonTarget()
	{
		return personTarget;
	}

	/**
	 * @return возвращаем цели клиента
	 */
	public List<PersonTarget> getPersonTargets()
	{
		List<PersonTarget> personTargets = new ArrayList<PersonTarget>(personalFinanceProfile.getPersonTargets());
		Collections.sort(personTargets, PERSON_TARGET_COMPARATOR);
		return personTargets;
	}
	/**
	 * @return возвращает цель из справочника
	 */
	public Target getDictionaryTarget()
	{
		return dictionaryTarget;
	}

	/**
	 * Сохранить изменения в цели
	 * @throws BusinessException
	 */
	public void saveTarget() throws BusinessException, BusinessLogicException
	{
		if (!canAddMoreOneTarget(personTarget.getDictionaryTarget(), personTarget.getId()))
			throw new BusinessLogicException("Такая цель у Вас уже создана. Пожалуйста, выберите другую цель.");

		Target laterAllTarget = targetService.findLaterAllTarget();
		if (laterAllTarget != null) // существует в справочнике цель с признаком "дата достижения цели позже остальных"
		{
			if (personTarget.getDictionaryTarget().equals(laterAllTarget.getId()))
			{
				Calendar currMaxDate = personTargetService.getCurrentMaxDate(personalFinanceProfile.getId(), personTarget.getId());
				if (currMaxDate != null && personTarget.getPlannedDate().compareTo(currMaxDate) <= 0)
					throw new BusinessLogicException("Дата достижения этой цели должна быть больше даты достижения остальных целей." +
							" Дата достижения Вашей последней цели " + DateHelper.formatDateWithQuarters(currMaxDate));
			}
			else
			{
				PersonTarget laterAllPersonTarget = personTargetService.findLaterAllPersonTarget(personalFinanceProfile.getId(), personTarget.getId());
				if (laterAllPersonTarget != null && personTarget.getPlannedDate().compareTo(laterAllPersonTarget.getPlannedDate()) >= 0)
					throw new BusinessLogicException("Дата достижения данной цели должна быть меньше даты достижения цели «" + laterAllPersonTarget.getName() +
							"» " + DateHelper.formatDateWithQuarters(laterAllPersonTarget.getPlannedDate()));
			}
		}

		if (dictionaryTarget.isLaterLoans())
		{
			Calendar lastLoanDate = personalFinanceProfile.getLastLoanDate();
			if (lastLoanDate != null && personTarget.getPlannedDate().compareTo(lastLoanDate) <= 0)
				throw new BusinessLogicException("Срок реализации Вашей цели должен быть позже, чем последний платеж по кредиту " +
						DateHelper.formatDateWithQuarters(lastLoanDate) + ". Пожалуйста, укажите другую дату достижения цели");
		}

		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * Удалить цель клиента
	 * @throws BusinessException
	 */
	public void removeTarget() throws BusinessException
	{
		List<PersonTarget> personTargetList = personalFinanceProfile.getPersonTargets();
		if(CollectionUtils.isNotEmpty(personTargetList))
			personTargetList.remove(personTarget);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	 /**
      * Возвращает количество целей клиента
      * @return количество целей клиента
      */
     public int getTargetCount()
     {
          return CollectionUtils.size(personalFinanceProfile.getPersonTargets());
     }

     /**
      * Возвращает максимальное количество целей, которое разрешено завести
      * @return максимальное количество целей
      */
     public Long getMaxTargetCount()
     {
          return TargetCountService.getTargetCountSafe();
     }

	/**
	 * Можно ли добавить еще одну цель заданного типа
	 * @param id - идентификатор цели из справочника
	 * @param currentTargetId - идентификатор текущей (редактируемой) цели
	 * @return true - цель данного типа можно добавить
	 * @throws BusinessException
	 */
     public boolean canAddMoreOneTarget(Long id, Long currentTargetId) throws BusinessException
	{
          return personTargetService.canAddMoreOneTarget(id, personalFinanceProfile.getId(), currentTargetId);
     }
}
