package com.rssl.phizic.business.loanclaim.dictionary;

import java.io.Serializable;

/**
 * @author EgorovaA
 * @ created 21.02.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Соответствие подразделений ЦАС НСИ и системы ЕТСМ"
 */
public class MatchDepartmentsCASNSIAndETSM implements Serializable
{
	private String tbCASNSI;
	private String tbETSM;
	private String osbCASNSI;
	private String osbETSM;

	public String getTbCASNSI()
	{
		return tbCASNSI;
	}

	public void setTbCASNSI(String tbCASNSI)
	{
		this.tbCASNSI = tbCASNSI;
	}

	public String getTbETSM()
	{
		return tbETSM;
	}

	public void setTbETSM(String tbETSM)
	{
		this.tbETSM = tbETSM;
	}

	public String getOsbCASNSI()
	{
		return osbCASNSI;
	}

	public void setOsbCASNSI(String osbCASNSI)
	{
		this.osbCASNSI = osbCASNSI;
	}

	public String getOsbETSM()
	{
		return osbETSM;
	}

	public void setOsbETSM(String osbETSM)
	{
		this.osbETSM = osbETSM;
	}
}
