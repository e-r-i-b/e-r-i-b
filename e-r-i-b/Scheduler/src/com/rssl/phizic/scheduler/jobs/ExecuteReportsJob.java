package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.ext.sbrf.reports.*;
import com.rssl.phizic.business.ext.sbrf.reports.it.*;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 23.07.2010
 * @ $Author$
 * @ $Revision$
 */

/*
	Получает и сохраняет информацию для отчетов со статусом "Отправлен"
 */

public class ExecuteReportsJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private ReportServiceSBRF reportService = new ReportServiceSBRF();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private volatile boolean isInterrupt = false;

	public ExecuteReportsJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			List<ReportAbstract> reports = reportService.findByState(StateReport.SEND, getInstanceName());  // получаем отчеты со статусом "Обрабатывается"

			for (ReportAbstract report: reports)
			{
				if(isInterrupt)
					break;

				SubReports subReports = null;

				switch(report.getType())         // проверяем тип отчета
				{
					case TypeReport.CONTRACT_TB:
						subReports = new ContractTB();
						break;
					case TypeReport.CONTRACT_OSB:
						subReports = new ContractOSB();
						break;
					case TypeReport.CONTRACT_VSP:
						subReports = new ContractVSP();
						break;
					case TypeReport.CONTRACT_OKR:
						subReports = new ContractOKR();
						break;
					case TypeReport.ACTIVE_USERS_TB:
						subReports = new ActiveUsersTB();
						break;
					case TypeReport.ACTIVE_USERS_VSP:
						subReports = new ActiveUsersVSP();
						break;
					case TypeReport.OPERATIONS_SBRF:
						subReports = new CountOperationsSBRFReport();
						break;
					case TypeReport.OPERATIONS_TB:
						subReports = new CountOperationsTBReport();
						break;
					case TypeReport.OPERATIONS_VSP:
						subReports = new CountOperationsVSPReport();
						break;
					case TypeReport.BUSINESS_PARAMS:
						subReports = new BusinessParamsITReport();
						break;
					case TypeReport.QUALITY_PERIOD:
						subReports = new QualityOperationPeriodITReport();
						break;
					case TypeReport.QUALITY_DATE:
						subReports = new CountOperationsDateITReport();
						break;
					case TypeReport.PROACTIVE_MONITORING:
						subReports = new ProactiveReport();
						break;
					case TypeReport.SYSTEM_IDLE:
						subReports = new SystemIdleSubReports();
						break;
					case TypeReport.COUNT_IOS_USER:
						subReports = new CountIOSReport();
						break;
					default:
						report.setState(StateReport.ERROR);
						reportService.update(report, getInstanceName());
						log.error("Неизвестный тип отчета: " + report.getType());
				}

				if (subReports != null)
				{
					try
					{
						List<ReportAdditionalInfo> fullReports = subReports.getSubReportsList(report);     // смотрим, состоит ли отчет из подотчетов

						if (fullReports == null)
						{
							fullReports = new ArrayList<ReportAdditionalInfo>();
							fullReports.add((ReportAdditionalInfo) subReports);        // подотчетов нет, обрабатываем сам отчет
						}

						for(ReportAdditionalInfo fullReport : fullReports)
						{

							String ids = null;
							if (fullReport.isIds())
								ids = report.getParams();

							List list = reportService.getReportInfo(report.getStartDate(), ids, fullReport.getQueryName(report), fullReport.getAdditionalParams(report), getInstanceName()); // запускаем sql-запрос для формирования отчета
							list = fullReport.processData(list);

					    	if (list != null)
					    	{
					    		// пишем в базу инфу по конкретному отчету
		        	    	    for(int i = 0; i < list.size(); i++)
		        	    	    {
			    	    	        try
			    	    	        {
			    	    	            Object obj = fullReport.buildReportByArrayOfObject((Object[])list.get(i), report);
			    	    	            reportService.add(obj, getInstanceName());
			    	    	        }
			    	    	        catch(Exception e)
			    	    	        {
					    	            report.setState(StateReport.ERROR);
					    	            log.error(e.getMessage(), e);
			    	    	        }
		        	    	    }

							    report.setState(StateReport.EXECUTED);  // отчет готов, меняем статус
					    	}
					    	else
					    	    report.setState(StateReport.ERROR);
						}
					}
					catch(Exception e)
					{
						report.setState(StateReport.ERROR);
						log.error(e.getMessage(), e);
					}
					finally
					{
						reportService.update(report, getInstanceName());  // обновляем информацию об отчете в БД (смена статуса)
					}
				}
			}
		}
		catch(Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	private String getInstanceName()
	{
		return "Report";
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}

