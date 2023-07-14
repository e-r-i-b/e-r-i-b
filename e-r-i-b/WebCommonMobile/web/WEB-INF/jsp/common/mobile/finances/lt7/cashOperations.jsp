<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="frm" %>
<%@ taglib  uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib  uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/finances/cashOperations">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">
            <sl:collection id="categoryItem" property="data" model="xml-list" title="items">
                <sl:collectionItem title="item">
                    <firstDay><bean:write name="categoryItem" property="firstDay.time" ignore="true" format="dd.MM.yyyy"/></firstDay>
                    <lastDay><bean:write name="categoryItem" property="lastDay.time" ignore="true" format="dd.MM.yyyy"/></lastDay>
                    <cashSum>
                        <amount><bean:write name="categoryItem" property="cashSumm" ignore="true"/></amount>
                        <currency>
                            <code>RUB</code>
                            <name>${phiz:getCurrencySign("RUB")}</name>
                        </currency>
                    </cashSum>
                    <nonCashSum>
                        <amount><bean:write name="categoryItem" property="cashlessSumm" ignore="true"/></amount>
                        <currency>
                            <code>RUB</code>
                            <name>${phiz:getCurrencySign("RUB")}</name>
                        </currency>
                    </nonCashSum>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>