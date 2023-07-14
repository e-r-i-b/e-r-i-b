<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/persons/mobilewallet" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="amount" value="${form.totalAmount}"/>
    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="MobileWallet"/>
        <tiles:put name="needSave" value="false"/>
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
                <tiles:put name="name" value="Мобильный кошелек"/>
                <tiles:put name="description" value="На данной форме Вы можете обнулить сумму мобильного кошелька клиента."/>
                <tiles:put name="data">
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.totalAmount.value.description" bundle="personsBundle"/>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <c:choose>
                                    <c:when test="${empty amount}">
                                        <input readonly="true" type="text" value="0.00" class="moneyField"/> р.
                                    </c:when>
                                    <c:otherwise>
                                        <input readonly="true" type="text" value="${amount.decimal}" class="moneyField"/> р.
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.reset"/>
                        <tiles:put name="commandHelpKey" value="button.reset"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="confirmText" value="Вы действительно хотите обнулить мобильный кошелек клиента?"/>
                        <tiles:put name="enabled" value="${not empty amount && amount.decimal != '0.00'}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>