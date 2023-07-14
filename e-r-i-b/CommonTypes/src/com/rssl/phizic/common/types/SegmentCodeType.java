package com.rssl.phizic.common.types;

/**
 * @author gololobov
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 *
 * Код сегмента, к которому относят клиента
 */

public enum SegmentCodeType
{
	/**
	 * Отсутствует
	 */
	NOTEXISTS(0L),
	/**
	 * потенциально МВС (массовый высокодоходный сегмент)
	 */
	MVCPOTENTIAL(2L),
	/**
	 * ВИП
	 */
	VIP(3L),
	/**
	 * МВС (массовый высокодоходный сегмент)
	 */
	MVC(4L);

	private static final String NOT_EXISTS_CODE_TYPE = "отсутствует";
	private static final String MVC_POTENTIAL_CODE_TYPE = "потенциально МВС";
	private static final String VIP_CODE_TYPE = "ВИП";
	private static final String MVC_CODE_TYPE = "МВС";

	private Long value;

	SegmentCodeType(Long value)
	{
		this.value = value;
	}

	/**
	 * @return - описание кода сегента для отображения пользователю
	 */
	public String getDescription()
	{
		if (value.equals(NOTEXISTS.value))
			return NOT_EXISTS_CODE_TYPE;
		if (value.equals(MVCPOTENTIAL.value))
			return MVC_POTENTIAL_CODE_TYPE;
		if (value.equals(VIP.value))
			return VIP_CODE_TYPE;
		if (value.equals(MVC.value))
			return MVC_CODE_TYPE;
		return null;
	}

	public static SegmentCodeType fromValue(Long value)
	{
		if (value == null || value.equals(NOTEXISTS.value))
			return NOTEXISTS;
		if (value.equals(MVCPOTENTIAL.value))
			return MVCPOTENTIAL;
		if (value.equals(VIP.value))
			return VIP;
		if (value.equals(MVC.value))
			return MVC;
		throw new IllegalArgumentException("Неизвестный код сегмента, к которому относят клиента [" + value + "]");
	}
}
