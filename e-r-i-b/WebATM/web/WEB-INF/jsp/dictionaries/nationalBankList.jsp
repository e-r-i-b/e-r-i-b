<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/dictionary/banks/national">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <sl:collection id="bank" property="data" model="xml-list" title="banksList">
                <sl:collectionItem title="bank">
                    <guid><c:out value="${bank.multiBlockRecordId}"/></guid>
                    <bic><c:out value="${bank.BIC}"/></bic>
                    <name><c:out value="${bank.name}"/></name>
                    <account><c:out value="${bank.account}"/></account>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
