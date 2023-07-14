<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>


<html:form action="/private/async/dictionaries/mobileOperators/list">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="mobileOperators" value="${frm.data}"/>

   <c:if test="${phiz:impliesOperation('ShowMobileOperatorsOperation', 'VirtualCardClaim')}">
        <tiles:insert definition="alphabet">
            <tiles:put name="title" value="Выбор мобильного оператора"/>
            <tiles:put name="onClickFunctionName" value="mobileOperatorClick"/>
            <tiles:put name="onClickFunctionParameters" value="name,code"/>
            <tiles:put name="data" beanName="frm" beanProperty="data" />
        </tiles:insert>
    </c:if>
</html:form>    