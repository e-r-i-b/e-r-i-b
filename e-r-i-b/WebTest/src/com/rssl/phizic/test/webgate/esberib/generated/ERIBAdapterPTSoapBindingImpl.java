/**
 * ERIBAdapterPTSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.test.webgate.esberib.utils.ResponseHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ERIBAdapterPTSoapBindingImpl implements com.rssl.phizic.test.webgate.esberib.generated.ERIBAdapterPT
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	//Для определения какой метод вызвать. key - название поля класса IFXRq_Type
	//value -  ResponseHelper
	private static Map<String, String> getHelpMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("bankAcctInqRq", "createBankAcctInqRs");
		map.put("cardAcctDInqRq", "createCardAcctDInqRs");
		map.put("acctInqRq", "createAcctInqRs");
		map.put("CCAcctExtStmtInqRq", "createCCAcctExtStmtInqRs");
		map.put("bankAcctStmtImgInqRq", "createBankAcctStmtImgInqRs");
		map.put("cardBlockRq", "createCardBlockRs");
		map.put("cardReissuePlaceRq", "createCardReissuePlaceRs");
		map.put("xferAddRq", "createXferAddRs");
		map.put("svcAcctAudRq","createSvcAcctAudRs");
		map.put("svcAcctDelRq","createSvcAcctDelRs");
		map.put("loanInqRq","createLoanInqRs");
		map.put("loanPaymentRq","createLoanPaymentRs");
		map.put("acctInfoRq","createAcctInfoRs");
		map.put("depAcctStmtInqRq","createAccountAbstract");
		map.put("imaAcctInRq", "createImaAcctInRs");
		map.put("bankAcctStmtInqRq", "createBankAcctStmtInqRs");
		map.put("bankAcctFullStmtInqRq", "createBankAcctFullStmtInqRs");
		map.put("depoAccTranRq","createDepoAccTranRs");
		map.put("depoAccSecRegRq","createDepoAccSecRegRs");
		map.put("depoArRq","createDepoArRs");
		map.put("custInqRq", "createCustInqRs");
		map.put("serviceStmtRq", "createServiceStmtRs");
		map.put("depoAccInfoRq", "createDepoAccInfoRs");
		map.put("depoDeptsInfoRq", "createDepoDeptsInfoRs");
		map.put("depoAccSecInfoRq", "createDepoAccSecInfoRs");
		map.put("depoDeptDetInfoRq", "createDepoDeptDetInfoRs");
		map.put("depoRevokeDocRq", "createDepoRevokeDocRs");
		map.put("messageRecvRq", "createMessageRecvRs");
		map.put("depoClientRegRq", "createDepoClientRegRs");
		map.put("billingPayPrepRq", "createBillingPayPrepRs");
		map.put("billingPayExecRq", "createBillingPayExecRs");
		map.put("billingPayCanRq", "createBillingPayCanRs");
		map.put("billingPayInqRq", "createBillingPayInqRs");
		map.put("bankAcctPermissModRq", "createBankAcctPermissModRs");
		map.put("svcAddRq", "createSvcAddRq");

		map.put("depToNewDepAddRq", "createDepToNewDep");
		map.put("newDepAddRq", "createNewDepAdd");
		map.put("cardToNewDepAddRq", "createCardToNewDep");
		map.put("pfrHasInfoInqRq", "createHasInfoInqRs");
		map.put("pfrGetInfoInqRq", "createGetInfoInqRs");
		map.put("cardAdditionalInfoRq", "createCardAdditionalInfoRs");
		map.put("OTPRestrictionModRq", "createOTPRestrictionModRs");
		//заявка на утерю сберкнижки
		map.put("setAccountStateRq","createSetAccountStateRs");
		// создание/изменение автоплатежа
		map.put("autoSubscriptionModRq","createAutoSubscriptionModRs");
		// получение списка автоплатежей
		map.put("getAutoSubscriptionListRq","createAutoSubscriptionListRs");
		// получение детальной информации по автоплатежам
		map.put("getAutoSubscriptionDetailInfoRq", "createAutoSubscriptionDetailInfoRs");
		// получение списка платежей по подписке
		map.put("getAutoPaymentListRq", "createAutoPaymentListRs");
		// получение детальной информации по платежу по подписке
		map.put("getAutoPaymentDetailInfoRq", "createGetAutoPaymentDetailInfoRs");
		//приостановки/возобновления/закрытия подписки на автоплатеж
		map.put("autoSubscriptionStatusModRq", "createAutoSubscriptionStatusModRs");
		// открытие ОМС
		map.put("depToNewIMAAddRq", "createDepToNewIMAAddRs");
		// открытие ОМС с переводом на него денежных средств с карты
		map.put("cardToNewIMAAddRq", "createCardToNewIMAAddRs");
		// покупка обезличенного металла за счет средств на карте
		map.put("cardToIMAAddRq", "createCardToIMAAddRs");
		// продажа обезличенного металла с зачислением средств на карту 
		map.put("IMAToCardAddRq", "createIMAToCardAddRs");
		//уточнение статуса операций
		map.put("xferOperStatusInfoRq","createXferOperStatusInfoRs");
		 //получение списка страховых продуктов
		map.put("getInsuranceListRq","createGetInsuranceListRs");
		//получение детальной информации по страховому продукту
		map.put("getInsuranceAppRq","createGetInsuranceAppRs");
		//Изменение условий вклада
		map.put("changeAccountInfoRq", "createChangeAccountInfoRs");
		//получение детальной информации по сберегательному сертификату
		map.put("securitiesInfoInqRq","createSecuritiesInfoInqRs");

		return map;
	}

     public com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type doIFX(com.rssl.phizic.test.webgate.esberib.generated.IFXRq_Type parameters) throws java.rmi.RemoteException
     {

          Class responseHelper = com.rssl.phizic.test.webgate.esberib.utils.ResponseHelper.class;

          try
          {
               for (Map.Entry<String, String> entry : getHelpMap().entrySet())
               {
                    if (BeanUtils.getProperty(parameters, entry.getKey()) != null)
                    {
                         Method createResponse = responseHelper.getDeclaredMethod(entry.getValue(), parameters.getClass());
                         return (IFXRs_Type) createResponse.invoke(new ResponseHelper(), parameters);
                    }
               }
          }
          catch (Exception e)
          {
	          log.error("ошибка при обработке сообщения в заглушке", e);
	          throw new RemoteException(e.getMessage(), e.getCause());
          }

	     throw new RemoteException("Некорректный запрос");
     }

}
