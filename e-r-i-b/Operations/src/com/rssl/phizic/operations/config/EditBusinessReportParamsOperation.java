package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.reports.BusinessReportConfig;
import com.rssl.phizic.logging.monitoring.MonitoringReportService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MailFormat;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * �������� �������������� ���������� ������ �� ������ ���������
 * @author gladishev
 * @ created 13.02.2015
 * @ $Author$
 * @ $Revision$
 */

public class EditBusinessReportParamsOperation extends EditPropertiesOperation
{
	private static final String UNLOAD_ERROR = "��� ������������ ������ �������� ������.";
	private static final MonitoringReportService MONITORING_REPORT_SERVICE = new MonitoringReportService("CSAAdmin");
	private Log log = PhizICLogFactory.getLog(LogModule.Core);

	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		DbPropertyService.updateProperties(getEntity(), PropertyCategory.CSAAdminScheduler, com.rssl.phizic.logging.messaging.System.CSAAdmin.toValue());
		DbPropertyService.updateProperties(getEntity(), PropertyCategory.Log, "Log");
		super.save();
	}

	/**
	 * ��������� ����� ������� ��������.
	 *
	 * @param params ��������� �������
	 * @return ����� �����.
	 * @throws BusinessException
	 */
	public byte[] unload(Map<String, Object> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			byte[] bytes = MONITORING_REPORT_SERVICE.buildReport(params);

			if ((Boolean)params.get(BusinessReportConfig.SEND_ON_KEY))
			{
				//�������� ������� ���������.
				sendReport(bytes, params);
			}
			return bytes;
		}
		catch (LogicException le)
		{
			throw new BusinessLogicException(le);
		}
		catch (Exception e)
		{
			log.error(UNLOAD_ERROR, e);
			throw new BusinessException(UNLOAD_ERROR);
		}
	}

	private void sendReport(byte[] report, Map<String,Object> params)
	{
		try
		{
			Map<String, byte[]> attach = new HashMap<String, byte[]>();
			attach.put("monitoring_report_" + new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()) + ".xls|application/vnd.ms-excel", report);
			String receivers = (String)params.get(BusinessReportConfig.MAIL_RECEIVERS_KEY);
			MailHelper.sendEMail((String)params.get(BusinessReportConfig.MAIL_THEME_KEY), new StringReader((String)params.get(BusinessReportConfig.MAIL_TEXT_KEY)), receivers, MailFormat.PLAIN_TEXT, MailHelper.getCurrentDateFields(), attach);
		}
		catch (IOException e)
		{
			log.error("�� ������� ��������� ��������� � ����������� ������ ��������", e);
		}
		catch (BusinessException e)
		{
			log.error("�� ������� ��������� ��������� � ����������� ������ ��������", e);
		}
	}

	/**
	 * ��������� ������ ���, �� ������� �� ��������� ������������� ������.
	 * @param fromDate ��������� ����
	 * @param toDate �������� ����
	 * @return ������ ��� (� ���� ������ ����� ������� dd.mm), �� ������� �� ��������� ������������� ������
	 * @throws BusinessException
	 */
	public List<String> getMissingDate(final Calendar fromDate, final Calendar toDate) throws BusinessException
	{
		try
		{
			return MONITORING_REPORT_SERVICE.getMissingDate(fromDate, toDate);
		}
		catch (SystemException e)
		{
			log.error("������ ��� ��������� ������ ���, �� ������� �� ��������� ������������� ������.", e);
			throw new BusinessException(e);
		}
	}
}
