<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags"%>

<html:form action="/private/finances/operationDiagram">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.diagramAbstracts}">
                <diagramDataSet>
                <c:forEach var="diagramAbstract" items="${form.diagramAbstracts}">
                    <c:if test="${not empty diagramAbstract}">
                    <value>
                        <month>${phiz:formatDateToStringOnPattern(diagramAbstract.operationDate, 'MM.yyyy')}</month>
                        <income>${not empty diagramAbstract.incomeAmount ? diagramAbstract.incomeAmount : 0}</income>
                        <outcome>${not empty diagramAbstract.outcomeAmount ? diagramAbstract.outcomeAmount : 0}</outcome>
                    </value>
                    </c:if>
                </c:forEach>
                </diagramDataSet>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
