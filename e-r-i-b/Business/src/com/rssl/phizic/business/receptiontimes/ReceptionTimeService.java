package com.rssl.phizic.business.receptiontimes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.payments.AccountClosingPayment;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DocumentConfig;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 19.08.2009
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ��������� ������ ���������� (� ��������������)
 */
public class ReceptionTimeService
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final MultiInstanceSimpleService multiInstanceSimpleService = new MultiInstanceSimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 *
	 * @param receptionTimeId id ������� ������ ����������
	 * @return ����� ������ ���������� �� ��������� ��
	 * @throws BusinessException
	 */
	public ReceptionTime findById(Long receptionTimeId, String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.findById(ReceptionTime.class, receptionTimeId, instanceName);
	}

	/**
	 * @param departmentId  depId
	 * @return ������� �� ������� receptiontimes ������ ������ ��� ������� departmentId
	 * @throws BusinessException
	 */
	public List<ReceptionTime> findByDepartmentId(final Long departmentId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ReceptionTime>>()
			{
				public List<ReceptionTime> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.receptiontimes.GetReceptionTimesByDepartmentId");
					query.setParameter("departmentId", departmentId);
					return (List<ReceptionTime>)query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 *
	 * @param departmentId  �� ������������, ��� �������� ����������� ������ ����� ������
	 * @param paymentType  - ��� �������, ��� ������� ����������� �����
	 * @return  ����� ������ ���������� ��� ������� ������������ � ������ ����� �������
	 * @throws BusinessException
	 */
	public ReceptionTime findByPaymentTypeInDepartment(final Long departmentId, final String paymentType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ReceptionTime>()
			{
				public ReceptionTime run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.receptiontimes.GetReceptionTimeByTypeInDepartment");
					query.setParameter("departmentId", departmentId);
					query.setParameter("paymentType", paymentType);
					return (ReceptionTime)query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * ���������� ������ �������������, � ������� ��� ������� ������ ��� �������
	 * @param paymentType  - ��� �������
	 * @return  ������ �������������
	 * @throws BusinessException
	 */
	public List<Department> findDepartmentsWithoutRecTimeByPaymentId(final String paymentType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.receptiontimes.GetDepartmentsWithoutRecTimeByPaymentId");
					query.setParameter("paymentType", paymentType);
					return query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * ���������� ���� � ��������� ������� �������� ���������
	 * @param document - ������ �������� ��� �������� �������� ��������� ����� �������� ���������
	 * @return ���� � ����������� �������� ���������: ��������� ����� ����������� ��� ��� � �.�.
	 */
	public WorkTimeInfo getWorkTimeInfo(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		CalendarGateService calendarGateService = GateSingleton.getFactory().service(CalendarGateService.class);

		// ������ ����� ������ ����������
		Department department = (Department) document.getDepartment();
		ReceptionTime receptionTime = getRecepionTime(department, ((GateDocument)document).getType());

		// ����������� ����� � ������ �������� ����� ������������
		Calendar currentTime = DateHelper.getCurrentTimeWithRawOffset(department.getTimeZone());

		try
		{
			Time endCheckTime   = receptionTime == null ? getDefaultEndTime()   : receptionTime.getReceptionTimeEnd();
			Time startCheckTime = receptionTime == null ? getDefaultStartTime() : receptionTime.getReceptionTimeStart();

			Time time = new Time(DateHelper.getTime(currentTime).getTimeInMillis());

			// ���� ������� �������, ������� �� ���������
			if(checkDocumentHolidays(document) && calendarGateService.isHoliday(currentTime, (GateDocument) document))
				return new WorkTimeInfo(receptionTime, TimeMatching.TOO_LATE);

			// ���� ����� ������ �� "�������"
		    if (endCheckTime.compareTo(startCheckTime) > 0)
		    {
				if(time.compareTo(startCheckTime) < 0)
					return new WorkTimeInfo(receptionTime, TimeMatching.TOO_EARLY);
			    else if(time.compareTo(endCheckTime) > 0)
					return new WorkTimeInfo(receptionTime, TimeMatching.TOO_LATE);
			    return new WorkTimeInfo(receptionTime, TimeMatching.RIGHT_NOW);
		    }
			else
		    {
			    //������� ����� �� �������� �� ����� ������ ������
			    //  ���� ��� ������ ������(���������� ��� ���������) � ������ �������
				if(time.compareTo(startCheckTime) + time.compareTo(endCheckTime) == 0)
					return new WorkTimeInfo(receptionTime, TimeMatching.TOO_EARLY);
			    else
					return new WorkTimeInfo(receptionTime, TimeMatching.RIGHT_NOW);
		    }
		}
		catch (IllegalArgumentException e)
		{
			throw new BusinessLogicException("�������� ������ ��������� �����, ������ ���� hh:mm:ss", e);
		}
		catch (TemporalGateException e)
		{
			throw new TemporalBusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private boolean checkDocumentHolidays(BusinessDocument document)
	{
		return !(document instanceof AccountClosingPayment);
	}

	/**
	 * ������������ ��������� ������� ������ ���������� � ������ ������������ �������������
	 * (����� ����� "������������ ��������� ������������ �������������")
	 * @param department - ������� �����������, � �������� ���� ���������
	 * @param paymentType - �������� �������
	 * @return ����� ������ ���������� ���� null ���� �� �������.
	 * @throws BusinessException
	 */
	public ReceptionTime getRecepionTime(Department department, Class paymentType) throws BusinessException
	{
		return getRecepionTime(department, paymentType.getSimpleName());
	}

	/**
	 * ������������ ��������� ������� ������ ���������� � ������ ������������ �������������
	 * (����� ����� "������������ ��������� ������������ �������������")
	 * @param department - ������� �����������, � �������� ���� ���������
	 * @param paymentType - �������� �������
	 * @return ����� ������ ���������� ���� null ���� �� �������.
	 * @throws BusinessException
	 */
	public ReceptionTime getRecepionTime(Department department, String paymentType) throws BusinessException
	{
		if (DepartmentService.isTB(department))
		{
			return  findByPaymentTypeInDepartment(department.getId(), paymentType);
		}
		ReceptionTime receptionTime = findByPaymentTypeInDepartment(department.getId(), paymentType);

		if(receptionTime == null)
		{
			Department tb = departmentService.getTB(department);
			return findByPaymentTypeInDepartment(tb.getId(), paymentType);
		}
		else if (!receptionTime.isUseParentSettings())
		{
			return receptionTime;
		}
		return getRecepionTime(departmentService.getParent(department), paymentType);
	}

	public void save(List<ReceptionTime> receptionTimes, String instanceName) throws BusinessException
	{
		for (ReceptionTime time : receptionTimes)
		{
			multiInstanceSimpleService.addOrUpdate(time, instanceName);
			dictionaryRecordChangeInfoService.addChangesToLog(time, ChangeType.update);
		}
	}

	/**
	 * ������ ����� ������ ���������� �������������, ���� � ���� �� ����
	 * @param department - �������������, �������� ������ �����
	 * @throws BusinessException
	 */
	public void addRecepientTime(Department department, String instanceName) throws BusinessException
	{
		List<ReceptionTime> receptionTimesExistence = findByDepartmentId(department.getId(), instanceName);
		if (!receptionTimesExistence.isEmpty())
			return;
		if (!DepartmentService.isTB(department))
		{
			saveParentReceptionTime(department, instanceName);
			return;
		}
		save(buildNewReceptionTimes(department.getId()), instanceName);
	}

	public void removeRecepientTime(Long departmentId, String instanceName) throws BusinessException
	{
		List<ReceptionTime> receptionTimes = findByDepartmentId(departmentId, instanceName);
		if(CollectionUtils.isEmpty(receptionTimes))
			return;
		for (ReceptionTime time : receptionTimes)
		{
			multiInstanceSimpleService.remove(time, instanceName);
			dictionaryRecordChangeInfoService.addChangesToLog(time, ChangeType.delete);
		}
	}

	/**
	 *
	 * @param department
	 * @throws BusinessException
	 */
	private void saveParentReceptionTime(Department department, String instanceName) throws BusinessException
	{
		//����� ����� ������ ���� ����������� ���������: ����� ������ ���������� �� ��������� �������������. ������������ �� ������������� �������������.
		Department parent = departmentService.getParent(department,instanceName);
		// ������� ��� ������, ����� ��������� ������� ��� ��� ��������������� ���
		if(parent == null)
			parent = departmentService.getTB(department,instanceName);

		List<ReceptionTime> parentReceptionTimes = findByDepartmentId(parent.getId(), instanceName);
		//���� ������������ ������������ �����������, �� ����� ����� ��� ������ �����.
		List<ReceptionTime> receptionTimesExistence = findByDepartmentId(department.getId(), instanceName);

		List<ReceptionTime> receptionTimes = new ArrayList<ReceptionTime>();
		for (ReceptionTime parentReceptionTime : parentReceptionTimes)
		{
			ReceptionTime receptionTimeExist = findExistence(parentReceptionTime.getPaymentType(), receptionTimesExistence);
			ReceptionTime receptionTime = (receptionTimeExist == null)?	new ReceptionTime(): receptionTimeExist;
			receptionTime.setDepartmentId(department.getId());
			receptionTime.setUseParentSettings(true);
			receptionTime.setPaymentType(parentReceptionTime.getPaymentType());
			receptionTime.setPaymentTypeDescription(parentReceptionTime.getPaymentTypeDescription());
			receptionTimes.add(receptionTime);
		}
		save(receptionTimes, instanceName);
	}

	private ReceptionTime findExistence(String documentName, List<ReceptionTime> receptionTimes)
	{
		if (receptionTimes.isEmpty())
			return null;
		for (ReceptionTime receptionTime : receptionTimes)
		{
			if(receptionTime.getPaymentType().equals(documentName))
				return receptionTime;
		}
		return null;
	}

	/**
	 *
	 * @return  ���� ��� ������������� ��� ������ ������ - ����� ��������� ���������, ����������� ����� ������ � �������� ��� ��� �� ���������
	 */
	public List<ReceptionTime> buildNewReceptionTimes(Long departmentId) throws BusinessException
	{
		List<ReceptionTime> entities = new ArrayList<ReceptionTime>();
		try
		{
			Time timeStart = getDefaultStartTime();
			Time timeEnd = getDefaultEndTime();

			for (Pair<String,  String> pair : ConsiderReceptionTimeDocumentsLoader.loadConsiderReceptionTimePaymentTypes())
			{
				ReceptionTime entity = new ReceptionTime();

                entity.setPaymentType(pair.getFirst());
				entity.setDepartmentId(departmentId);
                entity.setPaymentTypeDescription(pair.getSecond());
				entity.setReceptionTimeStart(timeStart);
				entity.setReceptionTimeEnd(timeEnd);
				entities.add(entity);
			}
			return entities;
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 *
	 * @return  ����� �� �������� iccs.properties, ����������� �� ���������
	 * @throws BusinessException
	 */
	public Time getDefaultStartTime() throws BusinessException
	{
		return Time.valueOf(ConfigFactory.getConfig(DocumentConfig.class).getStartReceptionTime());
	}

	public Time getDefaultEndTime() throws BusinessException
	{
		return Time.valueOf(ConfigFactory.getConfig(DocumentConfig.class).getEndReceptionTime());
	}

	/**
	 * �������� ������� � ��������������� �����������
	 * @param paymentTypes - ���������� ������ ����� ��������
	 * @throws BusinessException
	 */
	public void removeOldRecords(final List<String> paymentTypes)  throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.receptiontimes.removeOldRecords");
			        query.setParameterList("payments", paymentTypes);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}
