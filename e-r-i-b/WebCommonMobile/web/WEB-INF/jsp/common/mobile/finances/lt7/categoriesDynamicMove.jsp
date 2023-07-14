<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="frm" %>
<%@ taglib  uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib  uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/finances/categoriesDynamicMove">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">
            <sl:collection id="categoryItem" property="data" model="xml-list" title="items">
                <sl:collectionItem title="item">
                    <id><bean:write name="categoryItem" property="id" ignore="true"/></id>
                    <firstDay><bean:write name="categoryItem" property="firstDay.time" format="dd.MM.yyyy" ignore="true"/></firstDay>
                    <c:if test="${not empty categoryItem.lastDay}">
                        <lastDay><bean:write name="categoryItem" property="lastDay.time" format="dd.MM.yyyy" ignore="true"/></lastDay>
                    </c:if>
                    <nationalSum>
                        <amount><bean:write name="categoryItem" property="nationalSumm" ignore="true"/></amount>
                        <currency>
                            <code>RUB</code>
                            <name>${phiz:getCurrencySign("RUB")}</name>
                        </currency>
                    </nationalSum>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>