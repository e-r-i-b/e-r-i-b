package com.rssl.phizic.security.crypto;

import java.util.Date;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 20.12.2006
 * @ $Author: gladishev $
 * @ $Revision: 5701 $
 */
public interface Certificate extends Serializable
{
	/**
	 * @return ID �����������
	 */
	String getId();

	/**
	 * @return ������������ �����������
	 */
	String getName();

	/**
	 * @return �����������
	 */
	String getOrganization();

	/**
	 * @return ����� �� (GMT)
	 */
	public Date getExpiration();

	/**
	 * @return ����� (GMT)
	 */
	public Date getIssue();

	/**
	 * @return ��������
	 */
	public String getDescription();

	/**
	 * @return ����� �������� ����
	 */
	public String getKeyPairId();

	/**
	 * �������� ������������ �� ���������
	 */
	public boolean equals(Object obj);

	/**
	 * @return ��� ��� �����������
	 */
	public int hashCode();

}

