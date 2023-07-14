package com.rssl.auth.csa.back.servises.operations;

import com.rssl.phizic.common.types.csa.IdentificationType;

/**
 * @author tisov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 * Упрощённый контекст идентификации для логирования (содержит только тип идентификации и параметр)
 */
public class SimpleLogIdentificationContext implements LogProfileIdentificationContext
{
	private IdentificationType identificationType;
	private String identificationParam;

	/**
	 * @param identificationType - тип идентификации
	 * @param identificationParam - параметр идентификации
	 */
	public SimpleLogIdentificationContext(IdentificationType identificationType, String identificationParam)
	{
		this.identificationType = identificationType;
		this.identificationParam = identificationParam;
	}

	public IdentificationType getIdentificationType()
	{
		return identificationType;
	}

	public String getIdentificationParam()
	{
		return identificationParam;
	}
}
