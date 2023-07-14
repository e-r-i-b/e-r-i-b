package com.rssl.phizic.dataaccess.query;

import java.util.List;

/**
 * @author Krenev
 * @ created 09.02.2009
 * @ $Author$
 * @ $Revision$
 */
public interface PaginalDataSource<E> extends List<E>
{
	public void setOffset(int value);

	public void setSize(int value);

	public void setOrderParameters(List<OrderParameter> value);

	public int getAllItemsCount();
}
