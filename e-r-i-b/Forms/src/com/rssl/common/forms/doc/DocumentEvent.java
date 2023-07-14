package com.rssl.common.forms.doc;

/**
 * @author hudyakov
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 * ��������� �������, ������� ��������� � ���������� (==���������� ��������� ���������),
 * �� ������������� ������� �������� �������� � �� ��� ���� ��������� (��� �������
 * � state-machine.xml) 
 */

public enum DocumentEvent
{
	INITIAL,            // ������ ��� ������ (������ ������)
	SAVE,               // ���������� ���������
	SAVED,              // ������
	SAVEASDRAFT,        // ���������� ���������, ��� ��������
	CALCDEBT,           // ��������� �������������
	SAVEASTEMPLATE,     // ���������� ���������, ��� ������
	DELETE,             // �������� ���������
	EDIT,               // �������������� ���������
	EDIT_TEMPLATE,      // �������������� ������� ���������
	DELAY_DISPATCH,     // �������� ����������� ��������� ��������� �����
	DISPATCH,           // ����������� ��������� ���������� �����
	RETURN,             // ������� ���������
	SUCCESS,            // ������ ���������
	RECEIVE,            // ��������� ��������� ������ �����������
	APPROVE,            //
	ACCEPT,             // ��������� ��������� ����������� ����� ;)
	SEND,               // �������� ��������� � ��������� �������
	COMPLETION,         // �������� ��������� �� ���������
	CANCELATION,        // ������������ ���������
	CONSIDERATION,      // ������������ ���������
	MODIFICATION,       // ��������� ���������
	EXECUTE,            // ���������� ���������
	PARTLY_EXECUTED,     // �������� � ����, �� �������� � ������.
	REFUSE,             // ����� ���������
	RECALL,             // ����� ���������
	ERROR,              // ������
	CONFIRM,            // �����������
	CONFIRM_TEMPLATE,   // �����������
	SPECIFY,            // ��������
	STATEMENT_READY,
	DOUNKNOW,            // ������� �������� � �����c UNKNOW
	DOWAITCONFIRM,        // ������� � ��������� �������� ��������������� �������������(WAIT_CONFIRM)
	ADOPT,               // ������ �������
	//������� ��� ���
	SAVE_PERSONAL_DATA,     // ���������� ������ ������
	EDIT_RISK_PROFILE,       // ������� �� ����� �������������� ���� �������
	CHOOSE_CARD,             // ������� �� ����� ������ ��������� �����
	SAVE_RISK_PROFILE,      // ���������� ������ ������
	INIT_PERSON_PORTFOLIOS, //������������� ��������� �������
	COMPLETE_PORTFOLIOS,    //���������� ������������ ���������
	COMPLETE,               //���������� ����������� ���
	BACK,                    //����������� �� ���������� ��� ������������
	REPEAT_SEND,              // ��������� ��������
	ABS_RECALL_TIMEOUT,      // ��� ������� ������� �� ����� ��� ������ ��������� �� ���
	ABS_GATE_RECALL_TIMEOUT, // ��� ������� ������� �� ������� ������� ��� ������ ��������� �� ���
	BILLING_CONFIRM_TIMEOUT,          // ��� ������� ������� �� ����� ��� �������� ��������� � �������
	BILLING_GATE_CONFIRM_TIMEOUT,      // ��� ������� ������� �� ������� ������� ��� �������� ��������� � �������
	RECOVERDELETED,                    //������������ ��������� �������� ��������
	TICKETS_WAITING,                   //�������� ������� (��� ������ ����� ���������)
	UPGRADE,                           // ��������������� ��������
	DOWAITTM,                //�������� ��������� ������ �� ���������� �����
	NEED_VISIT_OFFICE,       //��������� ����� � ���������
	ETSM_SEND,               //��������� ������ �� ������� � ETSM
	TRANSFERT_IMPOSSIBLE,
	APPROVE_MUST_CONFIRM
}