<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--Популярные платежи--%>

<html:form action="/private/dictionary/popularPayments">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <sl:collection id="popularPayment" property="popularPayments" model="xml-list" title="popularPaymentList">
                <sl:collectionItem title="popularPayment">
                    <id>${popularPayment.id}</id>
                    <type>${ListPopularPaymentATMForm.type}</type>
                    <title><c:out value="${popularPayment.name}"/></title>
                    <description><c:out value="${popularPayment.name}"/></description>
                    <c:if test="${not empty popularPayment.imageId}">
                        <tiles:insert definition="imageType" flush="false">
                            <tiles:put name="name" value="imgURL"/>
                            <tiles:put name="id" value="${popularPayment.imageId}"/>
                        </tiles:insert>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>