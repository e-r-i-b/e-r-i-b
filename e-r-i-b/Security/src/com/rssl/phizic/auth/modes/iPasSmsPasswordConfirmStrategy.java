package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;

/**
 * Author: Moshenko
 * Date: 30.04.2010
 * Time: 13:26:03
 */
public class iPasSmsPasswordConfirmStrategy extends SmsPasswordConfirmStrategy
{

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException
	{
		//проверяем preConfirm, на предмет наличия признака отправленного смс, если такого нет то со создаем обычный SmsPasswordConfirmRequest
		if (preConfirm!=null&&!preConfirm.getPreConfimParamMap().isEmpty()&&(Boolean.parseBoolean((String)preConfirm.getPreConfimParamMap().get("SMS"))==true))
		{
	    	return new iPasSmsPasswordConfirmRequest(value,Boolean.parseBoolean((String)preConfirm.getPreConfimParamMap().get("SMS")));
		}
		else
		 {
			 return new iPasSmsPasswordConfirmRequest(value) ;
	     }
	}
	
//	public PreConfirmObject preConfirmActions(final CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
//	{
//		confirmableObject = callBackHandler.getConfimableObject();
//		try
//		{
//			// чтобы в случае ошибки в RSAlarm пароль не сохранился в базе
//			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
//			{
//				public Void run(Session session) throws Exception
//				{
//					checkExistsPasssword(callBackHandler.getLogin());
//					String passString = createSmsPassword(callBackHandler.getLogin());
//					callBackHandler.setPassword(passString);
//					callBackHandler.proceed();
//					return null;
//				}
//			});
//		}
//		catch (SmsPasswordExistException e)
//		{
//			throw new SecurityLogicException("Повторная отправка SMS-сообщения возможна через " + e.getLeftTime());
//		}
//		catch (Exception e)
//		{
//			throw new SecurityLogicException("Не удалось отправить SMS-пароль. Повторите попытку позднее");
//		}
//		PreConfirmObject pre = new PreConfirmObject("SMS","true");
//		pre.setPreConfirmParam("LifeTimePassword",getLifeTimePassword());
//		return pre;
//	}


}