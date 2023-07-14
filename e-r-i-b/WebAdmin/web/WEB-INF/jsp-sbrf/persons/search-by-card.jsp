<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/person/search/card" onsubmit="return setEmptyAction(event);">

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" type="string" value="SearchClient"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/activeX.js"></script>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPerson"/>
                <tiles:put name="name">
                    <bean:message key="person.search.by.card.form.name" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="person.search.by.card.form.description" bundle="personsBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:hidden property="field(cardNumber)"/>
                    <html:hidden property="field(cardType)"/>
                    <html:hidden property="field(transactionDate)"/>
                    <html:hidden property="field(transactionTime)"/>
                    <html:hidden property="field(terminalNumber)"/>                    
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" service="AutoSubscriptionManagment" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel"/>
                        <tiles:put name="bundle"            value="personsBundle"/>
                        <tiles:put name="action"            value="/person/search.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                doOnLoad(function()
                {
                    window.setTimeout("search()", 500);
                });

                function search()
                {
                    try
                    {
                        var activeXObject = createActvieXObject("SBRFSRV.Server");
                        if (activeXObject != undefined)
                        {
                            activeXObject.Clear();

                            if (activeXObject.NFun(5004) != "0")
                            {
                                alert("Ошибка при считывании карты и проверки ПИН-кода");
                            }

                            getElement("field(cardNumber)").value      = activeXObject.GParamString("ClientCard");
                            getElement("field(cardType)").value        = activeXObject.GParamString("CardType");
                            getElement("field(transactionDate)").value = activeXObject.GParamString("TrxDate");
                            getElement("field(transactionTime)").value = activeXObject.GParamString("TrxTime");
                            getElement("field(terminalNumber)").value  = activeXObject.GParamString("TermNum");
        
                            activeXObject.Clear();
                         }
                    }
                    finally
                    {
                        var buttom = createCommandButton('button.search', 'Найти');
                        buttom.click('', false);
                    }
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
