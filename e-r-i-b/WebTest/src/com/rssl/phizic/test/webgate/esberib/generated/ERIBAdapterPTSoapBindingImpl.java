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

	//��� ����������� ����� ����� �������. key - �������� ���� ������ IFXRq_Type
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
		//������ �� ����� ����������
		map.put("setAccountStateRq","createSetAccountStateRs");
		// ��������/��������� �����������
		map.put("autoSubscriptionModRq","createAutoSubscriptionModRs");
		// ��������� ������ ������������
		map.put("getAutoSubscriptionListRq","createAutoSubscriptionListRs");
		// ��������� ��������� ���������� �� ������������
		map.put("getAutoSubscriptionDetailInfoRq", "createAutoSubscriptionDetailInfoRs");
		// ��������� ������ �������� �� ��������
		map.put("getAutoPaymentListRq", "createAutoPaymentListRs");
		// ��������� ��������� ���������� �� ������� �� ��������
		map.put("getAutoPaymentDetailInfoRq", "createGetAutoPaymentDetailInfoRs");
		//������������/�������������/�������� �������� �� ����������
		map.put("autoSubscriptionStatusModRq", "createAutoSubscriptionStatusModRs");
		// �������� ���
		map.put("depToNewIMAAddRq", "createDepToNewIMAAddRs");
		// �������� ��� � ��������� �� ���� �������� ������� � �����
		map.put("cardToNewIMAAddRq", "createCardToNewIMAAddRs");
		// ������� ������������� ������� �� ���� ������� �� �����
		map.put("cardToIMAAddRq", "createCardToIMAAddRs");
		// ������� ������������� ������� � ����������� ������� �� ����� 
		map.put("IMAToCardAddRq", "createIMAToCardAddRs");
		//��������� ������� ��������
		map.put("xferOperStatusInfoRq","createXferOperStatusInfoRs");
		 //��������� ������ ��������� ���������
		map.put("getInsuranceListRq","createGetInsuranceListRs");
		//��������� ��������� ���������� �� ���������� ��������
		map.put("getInsuranceAppRq","createGetInsuranceAppRs");
		//��������� ������� ������
		map.put("changeAccountInfoRq", "createChangeAccountInfoRs");
		//��������� ��������� ���������� �� ��������������� �����������
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
	          log.error("������ ��� ��������� ��������� � ��������", e);
	          throw new RemoteException(e.getMessage(), e.getCause());
          }

	     throw new RemoteException("������������ ������");
     }

}
