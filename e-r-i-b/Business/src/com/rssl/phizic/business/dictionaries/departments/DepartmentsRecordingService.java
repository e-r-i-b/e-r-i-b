package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author Balovtsev
 * @version 17.04.13 14:52
 */
public class DepartmentsRecordingService
{
	private static final SimpleService service = new SimpleService();

	/**
	 *
	 * Ищет в Departments запись с соотвествующими значениями tb, osb и office.
	 *
	 * @return Collection&lt;String&gt;
	 * @throws Exception
	 */
	public Collection<String> findCodesContainsInDepartments(final Collection<String> codes, final String instance) throws Exception
	{
		if (!ConfigFactory.getConfig(BusinessSettingsConfig.class).isSpoobkEncoding())
		{
			return Collections.EMPTY_LIST;
		}

		return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<String>>()
		{
			public List<String> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findCodesContainsInDepartments")
						.setParameterList("codes", codes);

				return query.list();
			}
		});
	}

	/**
	 *
	 * Возвращает сущность содержащую коды ТБ, ОСБ, ВСП в кодификации как ЕРИБ так и СПООБК2. Поиск осуществляется
	 * по ТБ, ОСБ, ВСП в кодировке ЕРИБ.
	 *
	 * @param tb
	 * @param osb
	 * @param office
	 *
	 * @return DepartmentsRecoding
	 *
	 * @throws Exception
	 */
	public DepartmentsRecoding getDepartmentsRecodingByEribCodes(final String tb, final String osb, final String office) throws Exception
	{
		if (!ConfigFactory.getConfig(BusinessSettingsConfig.class).isSpoobkEncoding())
		{
			return null;
		}

		DepartmentsRecoding recoding = HibernateExecutor.getInstance().execute(new HibernateAction<DepartmentsRecoding>()
		{
			public DepartmentsRecoding run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentsRecodingByErib")
									 .setParameter("tb", tb)
									 .setParameter("osb", osb)
									 .setParameter("office", office);

				return (DepartmentsRecoding) query.uniqueResult();
			}
		});

		if (recoding == null)
		{
			throw new BusinessException("Не найдено соответствия в справочнике перекодировки.");
		}

		return recoding;
	}

	public DepartmentsRecoding getDepartmentsRecodingByEribCodesInternal(final String tb, final String osb, final String office) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<DepartmentsRecoding>()
		{
			public DepartmentsRecoding run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentsRecodingByErib")
						.setParameter("tb", tb)
						.setParameter("osb", osb)
						.setParameter("office", office);

				return (DepartmentsRecoding) query.uniqueResult();
			}
		});
	}

	/**
	 * Найти перекодировку по данным СПООБК
	 * @param tb тербанк
	 * @param osb осб
	 * @param vsp всп
	 * @return запись справочника перекодировок
	 */
	public DepartmentsRecoding findDepartmentRecodingBySpoobk(final String tb, final String osb, final String vsp) throws BusinessException
    {

        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<DepartmentsRecoding>()
            {

                public DepartmentsRecoding run(Session session) throws Exception
                {

                    Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentRecodingBySpoobk");
                    query.setParameter("tb2", StringHelper.removeLeadingZeros(tb));
                    query.setParameter("osb2", StringHelper.appendLeadingZeros(osb, 4));
                    query.setParameter("vsp2", StringHelper.appendLeadingZeros(StringHelper.getEmptyIfNull(vsp), 5));

                    return (DepartmentsRecoding) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }

	}

	public Set<DepartmentsRecoding> findDepartmentsForLoadToSpoobk(final String instance) throws Exception
	{
		if (!ConfigFactory.getConfig(BusinessSettingsConfig.class).isSpoobkEncoding())
		{
			return Collections.EMPTY_SET;
		}

		return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Set<DepartmentsRecoding>>()
		{

			public Set<DepartmentsRecoding> run(Session session) throws Exception
			{
				Set<DepartmentsRecoding> values = new HashSet<DepartmentsRecoding>();

				Iterator it = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentsForLoadToSpoobk")
									 .list()
									 .iterator();

				while (it.hasNext())
				{
					Object[] objects = (Object[]) it.next();

					DepartmentsRecoding value = new DepartmentsRecoding();
					String tb     = StringHelper.getEmptyIfNull((String) objects[0]);
					String osb    = StringHelper.getEmptyIfNull((String) objects[1]);
					String office = StringHelper.getEmptyIfNull((String) objects[2]);

					value.setTbErib        (tb);
					value.setOsbErib       (osb);
					value.setOfficeErib    (office);
					value.setTbSpoobk2     (tb);
					value.setOsbSpoobk2    (osb);
					value.setOfficeSpoobk2 (office);

					values.add(value);
				}

				return values;
			}
		});
	}

	/**
	 *
	 * Получает список значений полей DESPATCH из АС СПООБК2.
	 *
	 * @return List&lt;String&gt;
	 */
	public List<String> getDespatchFromSpoobk(final Collection<String> codes, final String instance) throws Exception
	{
		if (!ConfigFactory.getConfig(BusinessSettingsConfig.class).isSpoobkEncoding())
		{
			return Collections.EMPTY_LIST;
		}

		final String spoobkTableName = ConfigFactory.getConfig(BusinessSettingsConfig.class).getSpoobkTableName();
		if (StringHelper.isEmpty(spoobkTableName))
		{
			return null;
		}
		final String linkName = ConfigFactory.getConfig(BusinessSettingsConfig.class).getSpoobkLinkName();

		ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(instance),
				"com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.getDespatchFromSpoobk");
		query.setParameterList("codes", codes);
		query.setParameter("table_name", spoobkTableName);
		query.setParameter("linkName",   linkName);
		return query.executeListInternal();
	}

	/**
	 * Получить код подразделения в справочнике Departments ЕРИБ из справочника перекодировки.
	 * @param officeCode - код подразделения
	 * @return код подразделения
	 * @throws BusinessException
	 */
	public Code getRecordingOfficeCode(ExtendedCodeImpl officeCode) throws BusinessException
	{
		//ищем по despatch. При этом когда формируем despatch, дополняем значение полей ТБ, ОСБ и ВСП лидирующими нулями до 2-х, 4-х и 4-х знаков соответственно (despatch - 10 символов)
		String tb = StringHelper.appendLeadingZeros(officeCode.getRegion(), 2);
		String osb = StringHelper.appendLeadingZeros(officeCode.getBranch(), 4);
		String office = StringHelper.appendLeadingZeros(officeCode.getOffice(), 4);
		String despatch = tb + osb + office;

		DetachedCriteria criteria = DetachedCriteria.forClass(DepartmentsRecoding.class);
		criteria.add(Expression.eq("despatch", despatch));
		DepartmentsRecoding department = service.findSingle(criteria);
		if (department == null)
			return null;
		return new ExtendedCodeImpl(department.getTbErib(), department.getOsbErib(), department.getOfficeErib());
	}
}
