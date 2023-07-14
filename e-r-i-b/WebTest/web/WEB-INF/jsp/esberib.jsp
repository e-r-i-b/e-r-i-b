<%@ page import="com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl" %>
<%@ page import="com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl" %>
<%@ page import="com.rssl.phizic.business.persons.ActivePerson" %>
<%@ page import="com.rssl.phizic.business.persons.PersonService" %>
<%@ page import="com.rssl.phizic.gate.GateSingleton" %>
<%@ page import="com.rssl.phizic.gate.bankroll.Card" %>
<%@ page import="com.rssl.phizic.gate.clients.Client" %>
<%@ page import="com.rssl.phizic.gate.clients.ClientService" %>
<%@ page import="com.rssl.phizic.gate.cms.BlockReason" %>
<%@ page import="com.rssl.phizic.gate.dictionaries.officies.Office" %>
<%@ page import="com.rssl.phizic.gate.messaging.impl.InnerSerializer" %>
<%@ page import="com.rssl.phizic.utils.StringHelper" %>
<%@ page import="com.rssl.phizic.utils.xml.XmlHelper" %>
<%@ page import="com.rssl.phizic.common.types.bankroll.BankProductType" %>
<%@ page import="com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper" %>
<%@ page import="com.rssl.phizicgate.esberibgate.messaging.RequestTestHelper" %>
<%@ page import="com.rssl.phizicgate.esberibgate.ws.TransportProvider" %>
<%@ page import="com.rssl.phizicgate.wsgate.services.types.CardImpl" %>
<%@ page import="org.apache.axis.MessageContext" %>
<%@ page import="org.apache.axis.utils.ByteArrayOutputStream" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rssl.phizicgate.esberibgate.ws.generated.*" %>
<%@ page import="com.rssl.phizic.test.webgate.esberib.utils.CardResponseHelper" %>
<%--
  User: Pankin
  Date: 07.09.2010
  Time: 16:15:57
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String error = "";
    String statusDesc = "";

    System.out.println("!!!");

    ERIBAdapterPT stub = TransportProvider.getTransport();

    ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
    PersonService personService = new PersonService();
    ActivePerson person = (ActivePerson) personService.findById(122L);
    Client client = clientService.getClientById(person.getClientId());

    String requestType = request.getParameter("requestType");
    String clearCards = request.getParameter("clearCards");

    if (!StringHelper.isEmpty(clearCards))
    {
        CardResponseHelper cardResponseHelper = new CardResponseHelper();
        cardResponseHelper.clearStoredCards();
    }

    List<Card> cards = new ArrayList<Card>();

    Card[] cardsArray = new Card[3];
    CardImpl card1 = new CardImpl();
    card1.setNumber("2347178956382242");
    Office office = new ExtendedOfficeGateImpl();
    ExtendedCodeGateImpl code = new ExtendedCodeGateImpl();
    code.setRegion("40");
    code.setBranch("1234");
    office.setCode(code);
    card1.setOffice(office);
    cardsArray[0]=card1;
    CardImpl card2 = new CardImpl();
    card2.setNumber("2863277862549447");
    card2.setOffice(office);
    cardsArray[1]=card2;
    CardImpl card3 = new CardImpl();
    card3.setNumber("7687564536455342");
    card3.setOffice(office);
    cardsArray[2]=card3;

    String cbCode = "12345678";

    IFXRq_Type ifxRq = null;
    IFXRs_Type ifxRs = null;

    RequestTestHelper requestTestHelper = new RequestTestHelper(GateSingleton.getFactory());
    BankrollRequestHelper requestHelper = new BankrollRequestHelper(GateSingleton.getFactory());

    try
    {
        if (requestType!=null && requestType.equalsIgnoreCase("CustInqRq"))
        {
            ifxRq = requestTestHelper.createCustInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BankAcctInqRq"))
        {
            ifxRq = requestHelper.createBankAcctInqRq(client, client.getDocuments().get(0), cbCode, BankProductType.Card);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("CardAcctInqRq"))
        {
            ifxRq = requestHelper.createCardAcctInqRq(cbCode, cardsArray[0]);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("AcctInfoRq"))
        {
            ifxRq = requestTestHelper.createAcctInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepAcctStmtInqRq"))
        {
            ifxRq = requestTestHelper.createDepAcctStmtInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("ImaAcctInRq"))
        {
            ifxRq = requestTestHelper.createImaAcctInRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BankAcctStmtInqRq"))
        {
            ifxRq = requestTestHelper.createBankAcctStmtInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BankAcctFullStmtInqRq"))
        {
            ifxRq = requestTestHelper.createBankAcctFullStmtInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("CardAcctDInqRq"))
        {
            ifxRq = requestHelper.createCardAcctDInqRq("4352435243524352", null, cbCode);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BankAcctStmtImgInqRq"))
        {
            Calendar startDate = new GregorianCalendar();
            Calendar endDate = new GregorianCalendar();
            endDate.add(Calendar.DAY_OF_MONTH, 10);
            ifxRq = requestHelper.createBankAcctStmtImgInqRq(card1, "1@1.ru", startDate, endDate, cbCode);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("CCAcctExtStmtInqRq"))
        {
            ifxRq = requestHelper.createCCAcctExtStmtInqRq(10L, cbCode, cardsArray);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("LoanInqRq"))
        {
            ifxRq = requestTestHelper.createLoanInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("LoanInfoRq"))
        {
            ifxRq = requestTestHelper.createLoanInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("LoanPaymentRq"))
        {
            ifxRq = requestTestHelper.createLoanPaymentRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoClientRegRq"))
        {
            ifxRq = requestTestHelper.createDepoClientRegRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoAccInfoRq"))
        {
            ifxRq = requestTestHelper.createDepoAccInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoDeptsInfoRq"))
        {
            ifxRq = requestTestHelper.createDepoDeptsInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoDeptDetInfoRq"))
        {
            ifxRq = requestTestHelper.createDepoDeptDetInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoAccSecInfoRq"))
        {
            ifxRq = requestTestHelper.createDepoAccSecInfoRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoAccTranRq"))
        {
            ifxRq = requestTestHelper.createDepoAccTranRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("MessageRecvRq"))
        {
            ifxRq = requestTestHelper.createMessageRecvRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoAccSecRegRq"))
        {
            ifxRq = requestTestHelper.createDepoAccSecRegRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoArRq"))
        {
            ifxRq = requestTestHelper.createDepoArRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("DepoRevokeDocRq"))
        {
            ifxRq = requestTestHelper.createDepoRevokeDocRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferAddRqTCD"))
        {
            ifxRq = requestTestHelper.createXferAddRqTCD();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferAddRqTDD"))
        {
            ifxRq = requestTestHelper.createXferAddRqTDD();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferAddRqTDC"))
        {
            ifxRq = requestTestHelper.createXferAddRqTDC();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferAddRqTCP"))
        {
            ifxRq = requestTestHelper.createXferAddRqTCP();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("SvcAddRq"))
        {
            ifxRq = requestTestHelper.createSvcAddRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BillingPayInqRq"))
        {
            ifxRq = requestTestHelper.createBillingPayInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BillingPayPrepRq"))
        {
            ifxRq = requestTestHelper.createBillingPayPrepRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BillingPayExecRq"))
        {
            ifxRq = requestTestHelper.createBillingPayExecRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("ServiceStmtRq"))
        {
            ifxRq = requestTestHelper.createServiceStmtRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("SvcAcctAudRq"))
        {
            ifxRq = requestTestHelper.createSvcAcctAudRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("SvcAcctDelRq"))
        {
            ifxRq = requestTestHelper.createSvcAcctDelRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("CardBlockRq"))
        {
            ifxRq = requestHelper.createCardBlockRq(card1, BlockReason.lost, cbCode);
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferAddRqTCC"))
        {
            ifxRq = requestTestHelper.createXferAddRqTCC();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("SvcAddRqSDC"))
        {
            ifxRq = requestTestHelper.createSvcAddRqSDC();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferSyncRq"))
        {
            ifxRq = requestTestHelper.createXferSyncRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("XferCanRq"))
        {
            ifxRq = requestTestHelper.createXferCanRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("MDMClientInfoUpdateRq"))
        {
            ifxRq = requestTestHelper.createMDMClientInfoUpdateRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("MDMPaymentTemplUpdateRq"))
        {
            ifxRq = requestTestHelper.createMDMPaymentTemplUpdateRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("InfoInqRq"))
        {
            ifxRq = requestTestHelper.createInfoInqRq();
            ifxRs = stub.doIFX(ifxRq);
        }
        else if (requestType!=null && requestType.equalsIgnoreCase("BankAcctInqRqCESK"))
        {
            ifxRq = requestTestHelper.createBankAcctInqRqCESK();
            ifxRs = stub.doIFX(ifxRq);
        }
    }
    catch(Exception e)
    {
       error = e.getMessage();
    }

    StringWriter requestWriter = new StringWriter();
    StringWriter responceWriter = new StringWriter();
    if (((ERIBAdapterPTSoapBindingStub) stub)._getCall() != null)
    {
        MessageContext context = ((ERIBAdapterPTSoapBindingStub) stub)._getCall().getMessageContext();

        ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
        context.getRequestMessage().writeTo(requestOutputStream);
        new InnerSerializer(requestWriter).serialize(XmlHelper.parse(requestOutputStream.toString()));

        ByteArrayOutputStream responceOutputStream = new ByteArrayOutputStream();
        context.getResponseMessage().writeTo(responceOutputStream);
        new InnerSerializer(responceWriter).serialize(XmlHelper.parse(responceOutputStream.toString()));
    }
%>
<head>
    <title>Test Gate to ERIBadapter</title>
    <script type="text/javascript">
        function updateSelect()
        {
           var value = "${param.requestType}";
           if (value == "")
            return;
           var select = document.getElementById("requestType");
           select.value = "${param.requestType}";
        }
    </script>
</head>
<body onload="javascript:updateSelect()">
<div style="margin-bottom: 15px; margin-top:15px;">
    <form action="" method="POST">
        <input type="hidden" id="clearCards" name="clearCards" value="true"/>
        <input type="submit" value="Обновить карты клиентов" />
    </form>
</div>
    <form action="" method="POST">
        <select id="requestType" name="requestType">
            <option id="0" value="CustInqRq">3.1. CustInqRq. Проверка наличия у клиента заключенного УДБО.</option>
            <option id="1" value="BankAcctInqRq">3.2. BankAcctInqRq. Пполучение номеров счетов по вкладам, списка кредитных договоров, списка ОМС, списка МБК, списка счетов депо по ДУЛ.</option>
            <option id="2" value="CardAcctInqRq">3.3. AcctInqRq. Получение остатка по вкладу, по ОМС и по <b>карте</b>.</option>
            <option id="3" value="AcctInfoRq">3.4. AcctInfoRq. Интерфейс ACC_DI получения детальной информации по вкладу.</option>
            <option id="4" value="DepAcctStmtInqRq">3.5. DepAcctStmtInqRq. Интерфейс DAS_s получения выписки по вкладу.</option>
            <option id="5" value="ImaAcctInRq">3.6. ImaAcctInRq. Интерфейс IMA_IS_s получение детальной информации по ОМС.</option>
            <option id="6" value="BankAcctStmtInqRq">3.7. BankAcctStmtInqRq. Интерфейс IAS_s получение короткой выписки по ОМС.</option>
            <option id="7" value="BankAcctFullStmtInqRq">3.8. BankAcctFullStmtInqRq. Интерфейс IAS_F получение полной выписки по ОМС.</option>
            <option id="8" value="CardAcctDInqRq">3.9. CardAcctDInqRq. Интерфейс CRDWI получение детальной информации о карте.</option>
            <option id="9" value="BankAcctStmtImgInqRq">3.10. BankAcctStmtImgInqRq. Интерфейс SCREP отправки отчета по счету карты.</option>
            <option id="10" value="CCAcctExtStmtInqRq">3.12. CCAcctExtStmtInqRq. Интерфейс CREXT получение расширенной выписки по карте.</option>
            <option id="11" value="LoanInqRq">3.14. LoanInqRq. Интерфейс IIC получения задолженности по кредиту.</option>
            <option id="12" value="LoanInfoRq">3.15. LoanInfoRq. Интерфейс LN_CI получения детальной информации по кредиту.</option>
            <option id="13" value="LoanPaymentRq">3.16. LoanPaymentRq. Интерфейс LN_PSC получения графика платежей.</option>
            <option id="14" value="DepoClientRegRq">3.17. DepoClientRegRq Интерфейс DEPO_REG регистрации клиента на обслуживание в Депозитарии.</option>
            <option id="15" value="DepoAccInfoRq">3.18. DepoAccInfoRq. Интерфейс DEPO_IS получение информации по счетам депо.</option>
            <option id="16" value="DepoDeptsInfoRq">3.19. DepoDeptsInfoRq. Интерфейс DEPO_DS получение информации по задолженности по счетам депо.</option>
            <option id="17" value="DepoDeptDetInfoRq">3.20. DepoDeptDetInfoRq. Интерфейс  DEPO_DDS получение детальной информации по задолженности по счету депо.</option>
            <option id="18" value="DepoAccSecInfoRq">3.22. DepoAccSecInfoRq. Интерфейс DEPO_AC получение информации о содержании счета депо..</option>
            <option id="19" value="DepoAccTranRq">3.24. DepoAccTranRq Интерфейс  DEPO_SRD подачи поручения на перевод/прием перевода ценных бумаг.</option>
            <option id="20" value="MessageRecvRq">3.26. MessageRecvRq. Интерфейс  GEN_NTS оповещения о приеме сообщения.</option>
            <option id="21" value="DepoAccSecRegRq">3.27. DepoAccSecRegRq. Интерфейс  DEPO_RNS подачи заявки регистрацию новой ценной бумаги.</option>
            <option id="22" value="DepoArRq">3.28. DepoArRq. Интерфейс DEPO_AR для получения анкеты депонента.</option>
            <option id="23" value="DepoRevokeDocRq">3.29. DepoRevokeDocRq. Интерфейс  GEN_REV для отзыва документа.</option>
            <option id="24" value="XferAddRqTCD">3.30. XferAddRq. Интерфейс TCD перевод денежных средств со счета МБК на счет по вкладу принадлежащих одному клиенту.</option>
            <option id="25" value="XferAddRqTDD">3.31. XferAddRq. Интерфейс TDD перевод денежных средств со счета по вкладу на счет по вкладу принадлежащих одному клиенту.</option>
            <option id="26" value="XferAddRqTDC">3.32. XferAddRq. Интерфейс TDC перевод денежных средств счет по вкладу на счет МБК принадлежащих одному клиенту.</option>
            <option id="27" value="XferAddRqTCP">3.33. XferAddRq. Интерфейс TCP перевод денежных средств с карты юридическому лицу или физическому лицу.</option>
            <option id="28" value="SvcAddRq">3.34. SvcAddRq Интерфейс SDP создания длительного поручения на перевод денежных средств с вклада физическому лицу.</option>
            <option id="29" value="BillingPayInqRq">3.35. BillingPayInqRq. Интерфейс TBP_RI поиска информации о биллинговом получателе.</option>

            <option id="30" value="BillingPayPrepRq">3.36. BillingPayPrepRq. Интерфейс TBP_PR подготовки билингового платежа к отправке.</option>
            <option id="31" value="BillingPayExecRq">3.37. BillingPayExecRq. Интерфейс TBP_PAY проводки билингового платежа.</option>
            <option id="32" value="ServiceStmtRq">3.38. ServiceStmtRq. Интерфейс ESK_SAS получения графика исполнения длительного поручения.</option>
            <option id="33" value="SvcAcctAudRq">3.39. SvcAcctAudRq. Интерфейс COD_GSI получения информации по длительному поручению.</option>
            <option id="34" value="SvcAcctDelRq">3.40. SvcAcctDelRq. Интерфейс COD_ASD отмены длительного поручения.</option>
            <option id="35" value="CardBlockRq">3.42. CardBlockRq. Интерфейс CRBLOCK блокировка карты.</option>
            <option id="36" value="XferAddRqTCC">3.43. XferAddRq. Интерфейс ТСС перевод денежных средств со счета МБК на погашение кредита.</option>
            <option id="37" value="SvcAddRqSDC">3.44. SvcAddRq. Интерфейс SDС создания длительного поручения на погашение кредита с вкладного счета.</option>
            <option id="38" value="XferSyncRq">3.47. XferSyncRq Интерфейс COD_CLC  изменение лимита по карте. Прямая операция.</option>
            <option id="39" value="XferCanRq">3.47. XferCanRq Интерфейс COD_CLC  изменение лимита по карте. Операция отката.</option>
            <option id="40" value="MDMClientInfoUpdateRq">3.48. MDMClientInfoUpdateRq. Интерфейс MDM_CLT регистрации и обновления данных о клиенте в MDM и ЕРИБ.</option>
            <option id="41" value="MDMPaymentTemplUpdateRq">3.49. MDMPaymentTemplUpdateRq. Интерфейс MDM_PT регистрации и обновления данных о шаблонах платежа клиента в MDM .</option>
            <option id="42" value="InfoInqRq">3.50. InfoInqRq. Интерфейс CII запрос информации по клиенту из внешних источников.</option>
        </select><br/>
      <input type="submit" value="Отправить запрос"/><br/>
    </form>

<% if (!StringHelper.isEmpty(error)) {%>
    <b><font color="red">Возникла ошибка: <%=error%>.</font></b>
<%}%>
<br/>
Тип запроса: <font color="green">${param.requestType}</font>.
<br>
<%
if (ifxRs != null && ((ERIBAdapterPTSoapBindingStub) stub)._getCall() != null)
{
    %>
    <br>
    Запрос:
    <textarea readonly="true" name="requestText" type="text"
              style="width:100%;height:400px"><%= requestWriter.getBuffer().toString()%>
    </textarea>
    <br>
    Ответ:
    <textarea readonly="true" name="requestText" type="text"
              style="width:100%;height:400px"><%= responceWriter.getBuffer().toString()%>
    </textarea>
    <%
    ifxRs = null;
}
else
    {%>
    <br>
    <b><font color="red">Ответа нет.</font></b>
    <%}
%>

</body>
</html>
