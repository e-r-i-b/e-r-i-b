package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import javax.xml.transform.Source;
import java.util.Map;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: osminin $
 * @ $Revision$
 */
//TODO ����� �����, ���� ��� �� �������� �� ��������������,
//�������� ������ 1 �����, ������������ DOM
public interface EntityListSource
{
	/**
	 * ���������� �� ���� xml ������� EntityList � ���� Source.
	 * @param params ��������� �������� EntityList (�������� ������ etc)
	 * @return �������� XML
	 * @throws BusinessException
	 */
    Source getSource( Map<String,String> params ) throws BusinessException, BusinessLogicException;

	/**
	 * ���������� �� ���� xml ������� EntityList � ���� Document
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	Document getDocument ( Map<String,String> params ) throws BusinessException, BusinessLogicException;
}
