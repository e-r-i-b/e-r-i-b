package com.rssl.phizic.auth.modes;

/**
 @author Pankin
 @ created 14.12.2010
 @ $Author$
 @ $Revision$
 */
public class ChooseDepartmentStageVerifier implements StageVerifier
{
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		/*
		���� �� ���������� ���� �������������� ������� ��������� ������������� �� ������ �� ���, ����������
		����� �� ���������� �� �����, ������������� �� �� ����������, ����� �������� ������������ ����� ������.
		������ ��������� ��������� ���������� ������� ���� (����� completeStage();) ��� ���������� ������ ����
		�� ������. ���������� ������� ���� ��� ������ ����������� � PostCSALoginAction#start.
		 */
		return context.getLogin() == null;
	}
}
