package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.auth.Login;

import java.util.Date;
import java.util.Calendar;

/**
 * @author Evgrafov
 * @ created 22.12.2005
 * @ $Author: mescheryakova $
 * @ $Revision: 13667 $
 */

public interface PasswordCard
{
	public static final String STATE_REQUESTED = "R";  // ��������� ������ �� ������ ����� (��� �� ������� ������)
	public static final String STATE_NEW       = "N";  // ������� ������� ������� �� ����������� � ������������
	public static final String STATE_RESERVED  = "R";  // ���������� (��������������) ������� �������
	public static final String STATE_ACTIVE    = "A";  // �������� ������� �������, ������������ ��� ��������
	public static final String STATE_EXHAUSTED = "E";  // �������������� ������� ������� ()
	public static final String STATE_BLOCKED   = "B";  // ��������������� �������

	public static final String BLOCK_NONE      = "N";  // �� �������������
	public static final String BLOCK_AUTO      = "A";  // ������������� �������������
	public static final String BLOCK_MANUAL    = "M";  // ������������� �����������

	Long getId ();

	String getNumber ();

	Calendar getIssueDate ();

	Calendar getActivationDate ();

	String getState ();

	Long getPasswordsCount ();

	Long getValidPasswordsCount ();

	Long getWrongAttempts ();

	boolean isActive ();

	boolean isBlocked();

	boolean isExhausted();

	Login getLogin ();

	void setLogin ( Login login );

	String getBlockType();

	String getBlockReason();
}
