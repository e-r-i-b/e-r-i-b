<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
    <%@ include file="/WEB-INF/jsp/common/layout/print-head.jsp"%>
    <body style="height:99%;">
    <tiles:insert definition="googleTagManager"/>
        <html:form action="/private/accounts/bankDetails" show="true">
            <tiles:importAttribute scope="request"/>
            <%--Ôîðìà ïå÷àòè ðåêâèçèòîâ ñ÷åòà âêëàäà--%>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <tiles:insert definition="bankDetailsPrintTemplate" flush="false">
                <tiles:put name="title" value="ÐÅÊÂÈÇÈÒÛ ÏÅÐÅÂÎÄÀ ÍÀ Ñ×ÅÒ ÂÊËÀÄÀ"/>
                <tiles:put name="useService" value="SendBankDetailsToEmail"/>
                <tiles:put name="accountLink" beanName="form" beanProperty="accountLink"/>
                <tiles:put name="details" beanName="form" beanProperty="fields"/>
                <tiles:put name="emailSended" beanName="form" beanProperty="emailSended"/>
            </tiles:insert>
        </html:form>
    </body>
</html:html>