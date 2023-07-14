package com.rssl.phizic.business.documents.decorators;

import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class GateDocumentDecoratorBase
{
	private GateExecutableDocument original;

	public GateDocumentDecoratorBase(GateExecutableDocument original)
	{
		this.original = original;
	}

	public String getExternalId()
	{
		return original.getExternalId();
	}

	public void setExternalId(String externalId)
	{
		original.setExternalId(externalId);
	}

	public String getMbOperCode()
	{
		return original.getMbOperCode();
	}

	public void setMbOperCode(String mbOperCode)
	{
		original.setMbOperCode(mbOperCode);
	}

	public State getState()
	{
		return original.getState();
	}

	public Calendar getExecutionDate()
	{
		return original.getExecutionDate();
	}

	public void setExecutionDate(Calendar executionDate)
	{
		original.setExecutionDate(executionDate);
	}

	public Long getId()
	{
		return original.getId();
	}

	public Calendar getClientCreationDate()
	{
		return original.getClientCreationDate();
	}

	public Long getInternalOwnerId() throws GateException
	{
		return original.getInternalOwnerId();
	}

	public String getExternalOwnerId()
	{
		return original.getExternalOwnerId();
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		original.setExternalOwnerId(externalOwnerId);
	}

	public Office getOffice()
	{
		return original.getOffice();
	}

	public void setOffice(Office office)
	{
		original.setOffice(office);
	}

	public Money getCommission()
	{
		return original.getCommission();
	}

	public void setCommission(Money commission)
	{
		original.setCommission(commission);
	}

	public CommissionOptions getCommissionOptions()
	{
		return original.getCommissionOptions();
	}

	public String getDocumentNumber()
	{
		return original.getDocumentNumber();
	}

	public Calendar getAdmissionDate()
	{
		return original.getAdmissionDate();
	}

	public GateExecutableDocument getOriginal()
	{
		return original;
	}

	public boolean isTemplate()
	{
		return original.isTemplate();
	}

	/**
	 * @return режим интеграции для документа
	 */
	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		return original.getIntegrationMode();
	}

	/**
	 * задать режим интеграции для документа
	 * @param mode режим
	 */
	public void setIntegrationMode(ExternalSystemIntegrationMode mode)
	{
		original.setIntegrationMode(mode);
	}
}
