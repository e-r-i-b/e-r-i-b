package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;

/**
 * �������� ����������� ������ � ������������
 * @author basharin
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */

public interface AutoPaymentRestriction  extends Restriction
{
	/**
	* �������� ����������� ������ � ������������
	* @param link ����������
	*/
	boolean accept(AutoPaymentLink link) throws BusinessException;
}
