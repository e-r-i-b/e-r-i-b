<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/limits/mobileWallet/setAmount" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="limitsMain">
        <tiles:put name="submenu" type="string" value="MobileWallet"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="edit.mobilewallet.title" bundle="personsBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.closeResources.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/persons/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mobile.wallet.max.amount" bundle="limitsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(amount)" styleClass="moneyField"/> руб.
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="limitsBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>