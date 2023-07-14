package com.rssl.phizic.web.persons;

import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author akrenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListSegmentCodeTypesForm extends ListFormBase<SegmentCodeType>
{
	private static final List<SegmentCodeType> codeTypeList = Arrays.asList(SegmentCodeType.values());

	public List<SegmentCodeType> getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return codeTypeList;
	}
}
