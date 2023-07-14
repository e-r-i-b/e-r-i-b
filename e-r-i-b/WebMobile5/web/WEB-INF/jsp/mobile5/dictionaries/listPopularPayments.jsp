<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--Популярные платежи--%>

<html:form action="/private/dictionary/popularPayments">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="popularPayment" property="popularPayments" model="xml-list" title="popularPaymentList">
                <sl:collectionItem title="popularPayment">
                    <id>${popularPayment.id}</id>
                    <type>service</type>
                    <title>${popularPayment.name}</title>
                    <c:if test="${not empty popularPayment.description}">
                        <description><c:out value="${popularPayment.description}"/></description>
                    </c:if>
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