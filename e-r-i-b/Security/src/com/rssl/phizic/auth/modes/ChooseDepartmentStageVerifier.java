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
		≈сли на предыдущем шаге аутентификации найдено несколько пользователей по данным из ÷—ј, однозначно
		логин мы определить не можем, следовательно он не установлен, нужно показать пользователю экран выбора.
		ƒругих вариантов успешного завершени€ первого шага (вызов completeStage();) без найденного логина быть
		не должно. «авершение первого шага без логина выполн€етс€ в PostCSALoginAction#start.
		 */
		return context.getLogin() == null;
	}
}
