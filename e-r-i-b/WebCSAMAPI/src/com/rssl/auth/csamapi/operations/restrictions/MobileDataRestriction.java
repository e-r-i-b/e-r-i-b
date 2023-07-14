package com.rssl.auth.csamapi.operations.restrictions;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;

import java.util.Map;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ����
 */
public interface MobileDataRestriction
{
	boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException;
}
