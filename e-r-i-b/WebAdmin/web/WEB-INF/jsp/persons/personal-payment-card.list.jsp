<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/personal-payment-card/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.activePerson}"/>
    <c:set var="personId" value="${form.person}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="submenu" type="string" value="PersonalPayments"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="GetPersonsListOperation">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/persons/list.do?person=${personId}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var cardNumber = document.getElementsByName("card.cardNumber");
                var billingId = document.getElementsByName("card.billingId");
                function checkCard()
                {
                    if (cardNumber[0].value.length == 0)
                    {
                        alert("Введите номер карты персональных платежей");
                        return false;
                    }

                    if (billingId[0].value.length == 0)
                    {
                        alert("Выберите биллинговую систему");
                        return false;
                    }

                    return true;
                }

                function setBillingInfo(resource)
                {
                    setElement('card.billingId', resource['id']);
                    setElement('card.billingName', resource['name']);
                }
            </script>
            <input type="hidden" name="person" value="${personId}"/>

            <c:choose>
                <%-- Табличка с картой персональных платежей --%>
                <c:when test="${not empty form.card.id}">
                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="id" value="titlePersonalPaymentCards"/>
                        <tiles:put name="text" value="Карты персональных платежей"/>
                        <tiles:put name="buttons">
                            <tiles:insert definition="commandButton" flush="false" operation="RemovePersonalPaymentCardOperation">
                                <tiles:put name="commandKey"     value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.card"/>
                                <tiles:put name="bundle"  value="personsBundle"/>
                                <tiles:put name="confirmText" value="Вы действительно хотите удалить карту?"/>
                                <tiles:put name="validationFunction">
                                    function()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection('selectedIds', 'Выберите карту');
                                    }
                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="grid">
                            <sl:collection id="card" model="list" property="cardAsList" bundle="personsBundle">
                                <sl:collectionParam id="selectType" value="checkbox"/>
                                <sl:collectionParam id="selectName" value="selectedIds"/>
                                <sl:collectionParam id="selectProperty" value="id"/>

                                <sl:collectionItem title="Номер карты пользователя">
                                    <c:if test="${not empty card}">
                                        <bean:write name="card" property="cardNumber"/>
                                    </c:if>
                                </sl:collectionItem>

                                <sl:collectionItem title="Биллинговая система">
                                    <c:if test="${not empty card}">
                                        <bean:write name="card" property="billingName"/>
                                    </c:if>
                                </sl:collectionItem>
                            </sl:collection>
                        </tiles:put>

                        <tiles:put name="emptyMessage" value="У пользователя нет карты персональных платежей"/>
                    </tiles:insert>
                </c:when>

                <%-- Табличка для добавления карты персональных платежей --%>
                <c:otherwise>
                    <c:if test="${phiz:impliesOperation('AddPersonalPaymentCardOperation','PersonManagement')}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="id" value="editPersonalCard"/>
                            <tiles:put name="name" value="Добавление карты персональных платежей"/>
                            <tiles:put name="description" value="Используйте данную форму для создания карт ключей."/>
                            <tiles:put name="data">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Номер карты
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:text property="card.cardNumber" size="40" maxlength="20" styleClass="contactInput"/>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Биллинговая система
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:text property="card.billingName" size="60" readonly="true"/>
                                        <input type="button" class="buttWhite smButt"
                                               onclick="openBillingsDictionary(setBillingInfo);"
                                               value="..."/>
                                        <html:hidden property="card.billingId"/>
                                    </tiles:put>
                                </tiles:insert>

                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="commandButton" flush="false" operation="AddPersonalPaymentCardOperation">
                                    <tiles:put name="commandKey"        value="button.add.card"/>
                                    <tiles:put name="commandHelpKey"    value="button.add.card.help"/>
                                    <tiles:put name="bundle"            value="personsBundle"/>
                                    <tiles:put name="validationFunction" value="checkCard();"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </c:otherwise>
            </c:choose>

        </tiles:put>

    </tiles:insert>
</html:form>