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
			throw new IllegalArgumentException("������������� ������������ �� ����� ���� null");

		personalFinanceProfile = pfpService.getProfileById(profileId);

		if (personalFinanceProfile == null)
			throw new BusinessLogicException("������������ � id=" + profileId + " �� �������.");

		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("������ � id = " + person.getId() + " �� ����� ������� � ��� � id = " + personalFinanceProfile.getId());

		if(personTargetId != null && personTargetId != 0)
			personTarget = getTargetById(personTargetId);
		
		if (personTargetId == null || personTargetId == 0 || personTarget == null)
		{
		    if(getTargetCount() >= getMaxTargetCount())
				throw new BusinessLogicException("�� �� ������ �������� ����� ����, ������ ��� �� ������� ������������ ���������� �����.");

			personTarget = new PersonTarget();
			personalFinanceProfile.addPersonTarget(personTarget);
		}
	}

	public void initializeForEdit(Long profileId, Long personTargetId, Long dictionaryTargetId) throws BusinessException, BusinessLogicException
	{
		initialize(profileId,personTargetId);
		if(dictionaryTargetId == null || dictionaryTargetId == 0)
			throw new IllegalArgumentException("������������� ������������ �� ����� ���� null");
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
	 * @return ���������� ���� �������
	 */
	public PersonTarget getPersonTarget()
	{
		return personTarget;
	}

	/**
	 * @return ���������� ���� �������
	 */
	public List<PersonTarget> getPersonTargets()
	{
		List<PersonTarget> personTargets = new ArrayList<PersonTarget>(personalFinanceProfile.getPersonTargets());
		Collections.sort(personTargets, PERSON_TARGET_COMPARATOR);
		return personTargets;
	}
	/**
	 * @return ���������� ���� �� �����������
	 */
	public Target getDictionaryTarget()
	{
		return dictionaryTarget;
	}

	/**
	 * ��������� ��������� � ����
	 * @throws BusinessException
	 */
	public void saveTarget() throws BusinessException, BusinessLogicException
	{
		if (!canAddMoreOneTarget(personTarget.getDictionaryTarget(), personTarget.getId()))
			throw new BusinessLogicException("����� ���� � ��� ��� �������. ����������, �������� ������ ����.");

		Target laterAllTarget = targetService.findLaterAllTarget();
		if (laterAllTarget != null) // ���������� � ����������� ���� � ��������� "���� ���������� ���� ����� ���������"
		{
			if (personTarget.getDictionaryTarget().equals(laterAllTarget.getId()))
			{
				Calendar currMaxDate = personTargetService.getCurrentMaxDate(personalFinanceProfile.getId(), personTarget.getId());
				if (currMaxDate != null && personTarget.getPlannedDate().compareTo(currMaxDate) <= 0)
					throw new BusinessLogicException("���� ���������� ���� ���� ������ ���� ������ ���� ���������� ��������� �����." +
							" ���� ���������� ����� ��������� ���� " + DateHelper.formatDateWithQuarters(currMaxDate));
			}
			else
			{
				PersonTarget laterAllPersonTarget = personTargetService.findLaterAllPersonTarget(personalFinanceProfile.getId(), personTarget.getId());
				if (laterAllPersonTarget != null && personTarget.getPlannedDate().compareTo(laterAllPersonTarget.getPlannedDate()) >= 0)
					throw new BusinessLogicException("���� ���������� ������ ���� ������ ���� ������ ���� ���������� ���� �" + laterAllPersonTarget.getName() +
							"� " + DateHelper.formatDateWithQuarters(laterAllPersonTarget.getPlannedDate()));
			}
		}

		if (dictionaryTarget.isLaterLoans())
		{
			Calendar lastLoanDate = personalFinanceProfile.getLastLoanDate();
			if (lastLoanDate != null && personTarget.getPlannedDate().compareTo(lastLoanDate) <= 0)
				throw new BusinessLogicException("���� ���������� ����� ���� ������ ���� �����, ��� ��������� ������ �� ������� " +
						DateHelper.formatDateWithQuarters(lastLoanDate) + ". ����������, ������� ������ ���� ���������� ����");
		}

		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * ������� ���� �������
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
      * ���������� ���������� ����� �������
      * @return ���������� ����� �������
      */
     public int getTargetCount()
     {
          return CollectionUtils.size(personalFinanceProfile.getPersonTargets());
     }

     /**
      * ���������� ������������ ���������� �����, ������� ��������� �������
      * @return ������������ ���������� �����
      */
     public Long getMaxTargetCount()
     {
          return TargetCountService.getTargetCountSafe();
     }

	/**
	 * ����� �� �������� ��� ���� ���� ��������� ����
	 * @param id - ������������� ���� �� �����������
	 * @param currentTargetId - ������������� ������� (�������������) ����
	 * @return true - ���� ������� ���� ����� ��������
	 * @throws BusinessException
	 */
     public boolean canAddMoreOneTarget(Long id, Long currentTargetId) throws BusinessException
	{
          return personTargetService.canAddMoreOneTarget(id, personalFinanceProfile.getId(), currentTargetId);
     }
}
