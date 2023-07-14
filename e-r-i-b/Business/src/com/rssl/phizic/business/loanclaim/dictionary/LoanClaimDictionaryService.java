package com.rssl.phizic.business.loanclaim.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.loanclaim.dictionary.*;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 11.02.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы со справочниками заявки на кредит
 */
public class LoanClaimDictionaryService
{
	private static final SimpleService service = new SimpleService();


	public List<CategoryOfPosition> getCategoryOfPositionDictionary() throws BusinessException
	{
		return service.getAll(CategoryOfPosition.class);
	}

	public List<Education> getEducationDictionary() throws BusinessException
	{
		return service.getAll(Education.class);
	}

	public List<FamilyRelation> getFamilyRelationDictionary() throws BusinessException
	{
		return service.getAll(FamilyRelation.class);
	}

	public List<FamilyStatus> getFamilyStatusDictionary() throws BusinessException
	{
		return service.getAll(FamilyStatus.class);
	}

	public List<FormOfIncorporation> getFormOfIncorporationDictionary() throws BusinessException
	{
		return service.getAll(FormOfIncorporation.class);
	}

	public List<JobExperience> getJobExperienceDictionary() throws BusinessException
	{
		return service.getAll(JobExperience.class);
	}

	public List<KindOfActivity> getKindOfActivityDictionary() throws BusinessException
	{
		return service.getAll(KindOfActivity.class);
	}

	public List<LoanPaymentMethod> getLoanPaymentMethodDictionary() throws BusinessException
	{
		return service.getAll(LoanPaymentMethod.class);
	}

	public List<LoanPaymentPeriod> getLoanPaymentPeriodDictionary() throws BusinessException
	{
		return service.getAll(LoanPaymentPeriod.class);
	}

	public List<NumberOfEmployees> getNumberOfEmployeesDictionary() throws BusinessException
	{
		return service.getAll(NumberOfEmployees.class);
	}

	public List<Region> getRegionDictionary() throws BusinessException
	{
		return service.getAll(Region.class);
	}

	public List<ResidenceRight> getResidenceRightDictionary() throws BusinessException
	{
		return service.getAll(ResidenceRight.class);
	}

	public List<TypeOfArea> getTypeOfAreaDictionary() throws BusinessException
	{
		return service.getAll(TypeOfArea.class);
	}

	public List<TypeOfCity> getTypeOfCityDictionary() throws BusinessException
	{
		return service.getAll(TypeOfCity.class);
	}

	public List<TypeOfDebit> getTypeOfDebitDictionary() throws BusinessException
	{
		return service.getAll(TypeOfDebit.class);
	}

	public List<TypeOfLocality> getTypeOfLocalityDictionary() throws BusinessException
	{
		return service.getAll(TypeOfLocality.class);
	}

	public List<TypeOfRealty> getTypeOfRealtyDictionary() throws BusinessException
	{
		return service.getAll(TypeOfRealty.class);
	}

	public List<TypeOfStreet> getTypeOfStreetDictionary() throws BusinessException
	{
		return service.getAll(TypeOfStreet.class);
	}

	public List<TypeOfVehicle> getTypeOfVehicleDictionary() throws BusinessException
	{
		return service.getAll(TypeOfVehicle.class);
	}

	public List<WorkOnContract> getWorkOnContractDictionary() throws BusinessException
	{
		return service.getAll(WorkOnContract.class);
	}

	/**
	 * @return доступные в заявке способы получения кредита
	 */
	public List<LoanIssueMethod> getLoanIssueMethodAvailable() throws BusinessException
	{
		/*
		* Опорный объект: LOANCLAIM_LOAN_ISSUE_METHOD
		* Предикаты доступа: TABLE ACCESS FULL
		* Кардинальность: не более 6
		*/
		DetachedCriteria criteria = DetachedCriteria.forClass(LoanIssueMethod.class);
		criteria.add(Expression.eq("availableInClaim", true));
		return service.find(criteria);
	}

	/**
	 * Получить значение справочника по коду
	 * @param code - код в справочнике
	 * @param clazz - класс бина справочника
	 * @return запись справочника
	 */
	public <T extends AbstractDictionaryEntry> T getDictionaryElementByCode(String code, Class<T> clazz)
	{
		if (StringHelper.isEmpty(code))
		    throw new IllegalArgumentException("Аргумент 'code' не может быть пустым");

		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
			criteria.add(Expression.eq("code", code));

			return service.<T>findSingle(criteria, null, null);
		}
		catch (Exception e)
		{
			throw new InternalErrorException("Ошибка получения значения справочника по коду", e);
		}
	}

	/**
	 * Метод получения идентификатора (строка) подразделения в ЕТСМ по указанному департаменту
	 * @param departmentCode - код департамента (ТБ ОСБ ВСП) (never null)
	 * @return идентификатор подразделения в ЕТСМ (never null nor empty)
	 */
	public String getDepratmentETSMCode(Code departmentCode)
	{
		Map<String, String> codeFields = departmentCode.getFields();
		final String tb = codeFields.get("region");
		final String osb = codeFields.get("branch");
		final String vsp = codeFields.get("office");
		if (StringHelper.isEmpty(tb))
			throw new IllegalArgumentException("В коде подразделения не указан ТБ");

		// A. Подразделение уровня ТБ
		if (StringHelper.isEmpty(osb))
			return tb;

		// B. Подразделение других уровней
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session)
				{
					String newTB = tb;
					String newOSB = osb;

					Query query = session.getNamedQuery("LoanClaimDictionaryService.getDepratmentETSMCode");
					query.setString("tbCASNSI", tb);
					query.setString("osbCASNSI", osb);
					MatchDepartmentsCASNSIAndETSM match = (MatchDepartmentsCASNSIAndETSM) query.uniqueResult();
					if (match != null)
					{
						newTB = match.getTbETSM();
						newOSB = match.getOsbETSM();
					}

					return newTB
							+ StringHelper.addLeadingZeros(StringHelper.getEmptyIfNull(newOSB), 4)
							+ StringHelper.addLeadingZeros(StringHelper.getEmptyIfNull(vsp), 5);
				}
			});
		}
		catch (Exception e)
		{
			throw new InternalErrorException(e);
		}
	}
}
