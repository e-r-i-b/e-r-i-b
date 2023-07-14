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
	public static final String STATE_REQUESTED = "R";  // отправлен запрос на печать карты (еще не созданы пароли)
	public static final String STATE_NEW       = "N";  // готова€ таблица паролей не прив€занна€ к пользователю
	public static final String STATE_RESERVED  = "R";  // Ќеактивна€ (дополнительна€) таблица паролей
	public static final String STATE_ACTIVE    = "A";  // јктивна€ таблица паролей, используетс€ дл€ проверки
	public static final String STATE_EXHAUSTED = "E";  // »спользованна€ таблица паролей ()
	public static final String STATE_BLOCKED   = "B";  // «аблокированна€ таблица

	public static final String BLOCK_NONE      = "N";  // Ќе заблокирована
	public static final String BLOCK_AUTO      = "A";  // «аблокировано автоматически
	public static final String BLOCK_MANUAL    = "M";  // «аблокировано сотрудником

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
