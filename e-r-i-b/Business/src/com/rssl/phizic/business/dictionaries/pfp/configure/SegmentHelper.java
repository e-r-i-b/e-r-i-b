package com.rssl.phizic.business.dictionaries.pfp.configure;

import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author komarov
 * @ created 13.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер работы с сегментами в ПФП
 */

public class SegmentHelper
{
	private static final List<SegmentCodeType> SEGMENT_LIST;

	static
	{
		List<SegmentCodeType> segments = new ArrayList<SegmentCodeType>();
		segments.add(SegmentCodeType.NOTEXISTS);
		segments.add(SegmentCodeType.VIP);
		segments.add(SegmentCodeType.MVCPOTENTIAL);
		segments.add(SegmentCodeType.MVC);
		SEGMENT_LIST = Collections.unmodifiableList(segments);
	}

	/**
	 * @return возможные сегменты
	 */
	public static List<SegmentCodeType> getSegmentList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return SEGMENT_LIST;
	}

	/**
	 * Преобразовывает null в NOTEXISTS.
	 * @param segment сегмент клиента
	 * @return адаптированный для ПФП сегмент клиента
	 */
	public static SegmentCodeType getSegmentCodeType(SegmentCodeType segment)
	{
		//если сегмент не задан, считаем что это NOTEXISTS
		if (segment == null)
			return SegmentCodeType.NOTEXISTS;
		return segment;
	}
}
