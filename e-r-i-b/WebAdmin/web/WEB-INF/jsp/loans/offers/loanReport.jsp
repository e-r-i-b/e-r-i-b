<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/loans/offers/loanLoad">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loansMain">
        <tiles:put name="submenu" type="string" value="OffersLoad"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="offerLoad"/>
                <tiles:put name="name" value="Протокол загрузки предложений"/>
                <tiles:put name="description" value="Протокол загрузки предложений по кредитам"/>
                <tiles:put name="data">
                    <p> Общее количество обработанных предложений: <c:out value="${frm.totalCount}"/></p>
                    <p>Загружено в базу данных: <c:out value="${frm.loadCount}"/></p>
                    <c:if test="${not empty frm.personErrors}">
                            <p class="formTitle"><bean:message key="label.user" bundle="loansBundle"/></p>
                        <c:forEach var="item" items="${frm.personErrors}">
                            <p><c:out value="${item}"/></p>
                        </c:forEach>
                    </c:if>

                    <c:if test="${not empty frm.commonErrors}">
                        <p class="formTitle"><bean:message key="label.commonErrors" bundle="loansBundle"/></p>
                        <c:forEach var="item" items="${frm.commonErrors}">
                            <p><c:out value="${item}"/></p>
                        </c:forEach>
                    </c:if>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.return"/>
                        <tiles:put name="commandHelpKey" value="button.return"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="action" value="/loans/offers/loanLoad.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>


