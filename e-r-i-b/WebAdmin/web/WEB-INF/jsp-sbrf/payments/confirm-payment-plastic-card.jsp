<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/confirm" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:if test="${phiz:isInstance(frm.document, 'com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription') and frm.document.longOffer}">
        <jsp:include page="./subscriptions/support-payment-${frm.metadata.name}.jsp" flush="false" />
    </c:if>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu"   type="string" value="${subMenu}"/>
        <tiles:put name="pageTitle" type="string" value="${title}"/>

        <tiles:put name="data" type="string">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/activeX.js"></script>
            <script language="JavaScript">

            var Server;

            function cancel()
            {
                if (Server != undefined)
                {
                    Server.clear();   
                }
                return true;
            }

            function connectWithDll()
            {
                try
                {
                    Server = createActvieXObject("SBRFSRV.Server");
                    if (Server != undefined)
                    {
                        Server.clear();
                        if (Server.NFun(5004)==0)
                        {
                            ClientCard = Server.GParamString("ClientCard");
                            getElement("$$confirmPlasticCardNumber").value      = ClientCard;
                            getElement("$$confirmPlasticCardType").value        = Server.GParamString("CardType");
                            getElement("$$confirmPlasticTransactionDate").value = Server.GParamString("TrxDate");
                            getElement("$$confirmPlasticTransactionTime").value = Server.GParamString("TrxTime");
                            getElement("$$confirmPlasticTerminalNumber").value  = Server.GParamString("TermNum");
                            Server.clear();
                        }
                        else
                        {
                            Server.clear();
                            alert("Ошибка при считывании карты и проверки ПИН-кода");
                        }
                    }
                }
                finally
                {
                    createCommandButton('button.acceptConfirm', 'получено').click('', false);
                }
            }

            doOnLoad(function()
            {
                setTimeout("connectWithDll()", 500);
            });
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPerson"/>
                <tiles:put name="name">
                    <bean:message key="label.confirm.form.name" bundle="autopaymentsBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="label.confirm.form.description" bundle="autopaymentsBundle"/>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <div style="display: none">
                        <input type="text" name="$$confirmPlasticCardNumber"      size="40"/>
                        <input type="text" name="$$confirmPlasticCardType"        size="40"/>
                        <input type="text" name="$$confirmPlasticTransactionDate" size="40"/>
                        <input type="text" name="$$confirmPlasticTransactionTime" size="40"/>
                        <input type="text" name="$$confirmPlasticTerminalNumber"  size="40"/>
                    </div>
                </tiles:put>
                <%--кнопки--%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="autopaymentsBundle"/>
                        <tiles:put name="action"            value="private/payments/confirm.do?id=${frm.id}"/>
                         <tiles:put name="onclick">
                            cancel();
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
