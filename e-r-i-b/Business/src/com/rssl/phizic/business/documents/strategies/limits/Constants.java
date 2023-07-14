package com.rssl.phizic.business.documents.strategies.limits;

/**
 * @author khudyakov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String WAIT_ADDITIONAL_CONFIRM_BY_REASON_ERROR_MESSAGE  = "��� ������ id = %s ���������� �������������� ������������� �� �������: %s";
	public static final String BLOCK_DOCUMENT_OPERATION_ERROR_MESSAGE           = "��� ��������� ��������� id = %s �������� ��������������� �����, �������� �� �����������.";
	public static final String PROCESS_DOCUMENT_ERROR_MESSAGE                   = "��� ��������� ��������� id = %s �������� ������, ��� �������� ������� ����� ���������� � ���������� ���������.";
	public static final String ROLLBACK_LIMIT_ERROR_MESSAGE                     = "��� ������ �������� ������� ���������� � externalId = %s ��������� ������. ����� �� ��������.";
	public static final String NOT_FOUND_LIMIT_ERROR_MESSAGE                    = "��� �������� ��������� documentId = %s �� ������ ����� ����� %s";
	public static final String TIME_OUT_ERROR_MESSAGE                           = "��� ���������� ������� id = %s �������� �������� time-out";
	public static final String BLOCK_LIMIT_OPERATIONS_EXCEEDED_MESSAGE          = "�� ��������� 24 ���� �� ��������� ������� � �������� �� ����� %s. ��� �������� ������ � %s. ����������, ��������� ����� �������� ��� ��������� ������� �������.";
	public static final String BLOCK_LIMIT_OPERATION_EXCEEDED_MESSAGE           = "�� �� ������ ������� %s �� ����� ������� ��������� ������. ����������, �������� ����� � ��������� ��������.";
	public static final String EXCEEDED_AMOUNT_BY_TEMPLATE_PAYMENT_MESSAGE      = "��������� ����������� ����� ������ �� �������.";
	public static final String USER_LOCK_ERROR_MESSAGE                          = "�� ������� ������������� ����� ������� ��� ����� ������� ��� ������� id = %s";
	public static final String REQUIRE_ADDITIONAL_CHECK_ERROR_MESSAGE           = "�������� ����� �� ���������� �������� id = %s, ���������� �������������� ��������.";
	public static final String IMPOSSIBLE_PERFORM_OPERATION_ERROR_MESSAGE       = "�������� ����� �� ���������� �������� id = %s, ���������� �������� ����������.";
	public static final String WRONG_RESTRICTION_LIMIT_TYPE_ERROR_MESSAGE       = "������������ ��� �����������: %s ��� ������ id = %s";
	public static final String WRONG_OPERATION_LIMIT_TYPE_ERROR_MESSAGE         = "������������ ��� ��������: %s ��� ������ id = %s";
}
