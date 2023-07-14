package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.receptiontimes.ConsiderReceptionTimeDocumentsLoader;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.hibernate.Session;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Загрузчик времени приема документов
 * @author gladishev
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class UpdateReceptionTimesTask extends SafeTaskBase
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void safeExecute() throws Exception
	{
		List<Pair<String, String>> paymentList = ConsiderReceptionTimeDocumentsLoader.loadConsiderReceptionTimePaymentTypes();
		List<String> paymentTypeNames = new ArrayList<String>();
		for (Pair<String, String> pair: paymentList)
		{
			addNewReceptionTimes(pair.getFirst(), pair.getSecond());
			paymentTypeNames.add(pair.getFirst());
		}

		ReceptionTimeService receptionTimeService = new ReceptionTimeService();
		receptionTimeService.removeOldRecords(paymentTypeNames);
	}

	/**
	 * Находит все департаменты, у которых нет времени приема для документа с именем formName
	 * и добавляет для них время приема с параметрами по-умолчанию
	 * @param paymentTypeName - имя формы документа
	 * @param description - описание документа
	 */
	private void addNewReceptionTimes(final String paymentTypeName, final String description) throws Exception
	{
		ReceptionTimeService receptionTimeService = new ReceptionTimeService();
		Time defaultStartTime = receptionTimeService.getDefaultStartTime();
		Time defaultEndTime = receptionTimeService.getDefaultEndTime();

		List<ReceptionTime> times = new ArrayList<ReceptionTime>();
		List<Department> departments = receptionTimeService.findDepartmentsWithoutRecTimeByPaymentId(paymentTypeName);
		for (Department department : departments)
		{
			ReceptionTime receptionTime = new ReceptionTime();
			receptionTime.setPaymentType(paymentTypeName);
			receptionTime.setDepartmentId(department.getId());
			receptionTime.setPaymentTypeDescription(description);
			receptionTime.setReceptionTimeStart(defaultStartTime);
			receptionTime.setReceptionTimeEnd(defaultEndTime);
			receptionTime.setUseParentSettings(!DepartmentService.isTB(department));

			times.add(receptionTime);
		}

		if (times.isEmpty())
			return;

		final List<ReceptionTime> res = times;
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				for (ReceptionTime obj : res)
				{
					session.save(obj);
					log.info("ReceptionTime for payment " + paymentTypeName + ", departmentId= " +
									obj.getDepartmentId() + " updated");
				}
				return null;
			}
		});
	}
}
