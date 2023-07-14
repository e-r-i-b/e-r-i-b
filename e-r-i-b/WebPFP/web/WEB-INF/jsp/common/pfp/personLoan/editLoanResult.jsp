<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

&nbsp;
<html:form action="/async/editPfpLoan">
    <tiles:insert definition="webModulePage">
        <tiles:put name="data">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="personLoan" value="${form.personLoan}"/>

            <c:choose>
                <c:when test="${not empty personLoan}">
                    <div>
                        <input id="loanId" type="hidden" value="${personLoan.id}"/>
                    </div>
                    <table>
                        <%@ include file="/WEB-INF/jsp/common/pfp/personLoan/personLoanLine.jsp"  %>
                    </table>
                </c:when>                
                <c:otherwise>
                    <phiz:messages  id="error" bundle="pfpBundle" field="field" message="error">
                        <c:set var="errors">${errors}${error}</c:set>
                    </phiz:messages>
                    <c:out value="${errors}"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>