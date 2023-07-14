package com.rssl.phizic.common.types;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author EgorovaA
 * @ created 01.09.14
 * @ $Author$
 * @ $Revision$
 */
public interface TariffPlan extends Serializable
{
	String getCode();

	String getName();

	Calendar getDateBegin();

	Calendar getDateEnd();

	boolean isState();

	String getUnknownCode();

}
