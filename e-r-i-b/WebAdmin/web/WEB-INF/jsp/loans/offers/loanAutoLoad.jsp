<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/loans/offers/loanLoad">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="loansMain">
        <tiles:put name="submenu" type="string" value="LoanOffersLoad"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="offerLoad"/>
                <tiles:put name="name" value="Загрузка предложений по кредитам"/>
                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${not empty form.fileName}">
                            <h4>Файл будет загружен из</h4>
                            <p><c:out value="${form.fileName}" /></p>
                        </c:when>
                        <c:otherwise>
                            <p>Не задана настройка загрузки предложений. Измените настройку загрузки предложений по кредитам</p>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
                <c:if test="${not empty form.fileName}">
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.replic"/>
                            <tiles:put name="commandTextKey" value="button.replic"/>
                            <tiles:put name="commandHelpKey" value="button.replic"/>
                            <tiles:put name="bundle" value="loansBundle"/>
                        </tiles:insert>
                    </tiles:put>
                </c:if>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>