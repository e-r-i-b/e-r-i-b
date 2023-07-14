package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;

import java.util.Calendar;

/**
 * @author niculichev
 * @ created 05.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentImpl extends LongOfferImpl implements AutoPayment
{
    private java.lang.String codeService;
    private java.util.Calendar dateAccepted;
    private AutoPaymentStatus reportStatus;
	private java.lang.String requisite;

	/**
	 * @return Код сервиса (маршрута)
	 */
	public String getCodeService()
	{
		return codeService;
	}

	/**
	 * @return состояние автоплатжа
	 */
	public AutoPaymentStatus getReportStatus()
	{
		return reportStatus;
	}

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	public void setReportStatus(AutoPaymentStatus reportStatus)
	{
		this.reportStatus = reportStatus;
	}

	public void setReportStatus(String status)
	{
		if (status == null || status.trim().length() == 0)
		{
			return;
		}
		this.reportStatus = Enum.valueOf(AutoPaymentStatus.class, status);
	}

	public String getRequisite()
	{
		return requisite;
	}

	public void setRequisite(String requisite)
	{
		this.requisite = requisite;
	}

	/**
	 * @return Дата оформления заявки на автоплатеж
	 */
	public Calendar getDateAccepted()
	{
		return dateAccepted;
	}

	public void setDateAccepted(Calendar dateAccepted)
	{
		this.dateAccepted = dateAccepted;
	}
}
