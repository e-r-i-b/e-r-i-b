package com.rssl.phizic.auth;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.ApplicationType;

import java.util.List;
import java.util.Calendar;
import java.io.Serializable;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface CommonLogin extends Serializable, ConfirmableObject, Entity
{
	String getUserId();
	String getCsaUserId();
	String getLastIpAddress();

	List<LoginBlock> getBlocks();

	/**
	 * @return ���������� �������� ������� ������
	 */
	public long getWrongLoginAttempts();

	// ���� ���������� �������� �����
	Calendar getLastLogonDate();

	/**
	 * @return ����� ����� ���������� �������� ����� (��� null) 
	 */
	String getLastLogonCardNumber();

	/**
	 * @return ��� ���������������� �����, � �������� ��������� �����, �� ������� ��� �������� ��������� ������� ���� (��� null)
	 */
	String getLastLogonCardDepartment();

	/**
	 * @return ���������� true, ���� � ������� ���� ����������� � ���������� �����
	 * (�� �������� ����� ���� �� ��������������)
	 */
	boolean isMobileBankConnected();

    /**
     * ������� ���� ��� ���� �������� ������
     * @return
     */
    boolean isFirstLogin();

	// ���� �������� ����� � �������
	Calendar getLogonDate();

	/**
	 * @return ��� ��� �����, � �������� ��������� �����, �� ������� ��� �������� ��������� ������� ���� (��� null)
	 */
	String getLastLogonCardOSB();
	
	/**
	 * @return ��� ��� �����, � �������� ��������� �����, �� ������� ��� �������� ��������� ������� ���� (��� null)
	 */
	String getLastLogonCardVSP();

	/**
	 * @return ��� TB �����, � �������� ��������� �����, �� ������� ��� �������� ��������� ������� ���� (��� null)
	 */
	String getLastLogonCardTB();

	boolean isDeleted();
}
