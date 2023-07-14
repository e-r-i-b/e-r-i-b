package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author mihaylov
 * @ created 17.11.2011
 * @ $Author$
 * @ $Revision$
 */

/*Отчет о количестве сотрудников iOS на дату*/
public class CountIOSReport extends SubReports implements ReportAdditionalInfo 
{
	private static final Long LAST_DAY_COUNT = 90L;	

	private Long id;
	private ReportAbstract reportId;

	private String tbName; 
	private String osbName;
	private String vspName;

	private Long totalCount; //общее количество  клиентов,  которые хотя бы раз входили на  текущую дату формирования отчета
	private Long lastCount;  //количество  клиентов,  которые входили за 90 дней от даты формирования отчета

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public ReportAbstract getReportId()
	{
		return reportId;
	}

	public void setReportId(ReportAbstract reportId)
	{
		this.reportId = reportId;
	}	

	public String getTbName()
	{
		return tbName;
	}

	public void setTbName(String tbName)
	{
		this.tbName = tbName;
	}

	public String getOsbName()
	{
		return osbName;
	}

	public void setOsbName(String osbName)
	{
		this.osbName = osbName;
	}

	public String getVspName()
	{
		return vspName;
	}

	public void setVspName(String vspName)
	{
		this.vspName = vspName;
	}

	public Long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Long totalCount)
	{
		this.totalCount = totalCount;
	}

	public Long getLastCount()
	{
		return lastCount;
	}

	public void setLastCount(Long lastCount)
	{
		this.lastCount = lastCount;
	}

	public Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException
	{
		//обнуляем необязательные поля
		this.setTbName(null);
		this.setOsbName(null);
		this.setVspName(null);
		this.setReportId(report);
		Long departmentId = Long.valueOf(obj[0].toString());
		Stack<String> names = ReportHelper.getDepartmentNames(departmentId);
		if(CollectionUtils.isNotEmpty(names))
			this.setTbName(names.pop());
		if(CollectionUtils.isNotEmpty(names))
			this.setOsbName(names.pop());
		if(CollectionUtils.isNotEmpty(names))
			this.setVspName(names.pop());		
		this.setTotalCount(Long.valueOf(obj[1].toString()));
		this.setLastCount(Long.valueOf(obj[2].toString()));
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.CountIOSReport";
	}

	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		// параметры фильтрации
		Map<String, Object>  map = new HashMap<String, Object>();
		map.put("applications", ApplicationUtil.getMobileApiNameList());
		map.put("descriptionKey", Constants.CLIENT_IOS_AUTENTIFICATION_KEY);
		map.put("lastDayCount", LAST_DAY_COUNT);// количество дней за которые необходимо подсчитать входы
		Calendar startDate = DateHelper.getCurrentDate();
		startDate.setTime( report.getStartDate().getTime() );
		startDate.add(Calendar.DATE, 1);      // +1 день
		map.put("startDate", startDate);
		return map;
	}

	public boolean isIds()
	{
		return false;
	}

	public List processData(List list)
	{
		return list;
	}	
}
