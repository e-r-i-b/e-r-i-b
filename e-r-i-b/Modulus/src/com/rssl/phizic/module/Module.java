package com.rssl.phizic.module;

import com.rssl.common.forms.FormsEngine;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.gate.GateEngine;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.logging.system.LogEngine;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.payment.PaymentEngine;
import com.rssl.phizic.permission.PermissionEngine;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������
 * ������������� ����������:
 * - � ���, ��� ������ �����������/�����������
 * - � ���, ��� ������������� ������ ����� ������
 * - � ����������, ��������������� � ������
 */
@ThreadSafe
public interface Module
{
	String KEY = Module.class.getName();

	/**
	 * @return ��� ������
	 */
	String getName();

	/**
	 * @return ����������, � ������� �������� ������������� ������
	 */
	Application getApplication();

	/**
	 * ���������� ��������� ������
	 * @return ��������� ������ (never null)
	 */
	ModuleLoader getModuleLoader();

	/**
	 * ���������� �������� �� ���������� ������� ������
	 * @return �������� ������ ������ (never null)
	 */
	WorkManager getWorkManager();

	/**
	 * ���������� �������� �� �������
	 * @return �������� �� ������� (never null)
	 */
	EngineManager getEngineManager();

	/**
	 * ���������� ������ �����������
	 * @return ������ ����������� (never null)
	 */
	LogEngine getLogEngine();

	/**
	 * ���������� ������ ����������
	 * @return ������ ���������� ��� null, ���� ������ �� ����������� ���������
	 */
	HibernateEngine getHibernateEngine();

	/**
	 * ���������� ������ ���������
	 * @return ������ ��������� ��� null, ���� ������ �� ���������� ������ ���������
	 */
	TextEngine getTextEngine();

	/**
	 * ���������� ������ ��������������
	 * @return ������ �������������� ��� null, ���� ������ �� ������������� ��������������
	 */
	AuthEngine getAuthEngine();

	/**
	 * ���������� ������ ������ ������������
	 * @return ������ ������ ��� null, ���� ������ �� ������������� ������ ������������
	 */
	SessionEngine getSessionEngine();

	/**
	 * ���������� ������ ����
	 * @return ������ ���� ��� null, ���� ������ �� ������������ ������ � ������� �������
	 */
	PermissionEngine getPermissionEngine();

	/**
	 * ���������� ������ ������
	 * @return ������ ������ ��� null, ���� ������ �� ���������� ������ ������
	 */
	GateEngine getGateEngine();

	/**
	 * ���������� ������ �������������� � �������������
	 * @return ������ �������������� � ������������� ��� null, ���� ������ �� ��������������� � �������������
	 */
	UserInteractEngine getUserInteractEngine();

	/**
	 * ���������� ������ �� ������
	 * @return ������ �� ������ ��� null, ���� ������ �� �������� � �������
	 */
	FormsEngine getFormsEngine();

	/**
	 * ���������� ������ �� ���������� ���������
	 * @return ������ �������� ��� null, ���� ������ �� �������� � ����������� ����������
	 */
	BankrollEngine getBankrollEngine();

	/**
	 * ���������� ������ �������� ���-���������
	 * @return ������ �������� ���-��������� ��� null, ���� ������ �� ���������� �������� ���-���������
	 */
	MessagingEngine getMessagingEngine();

	/**
	 * ���������� ������ ��������
	 * @return ������ �������� ��� null, ���� ������ �� ������������ �������
	 */
	PaymentEngine getPaymentEngine();

	/**
	 * ���������� ������ �������������
	 * @return ������ ������������� ��� null, ���� ������ �� �������� � ���������������
	 */
	ConfirmEngine getConfirmEngine();
}
