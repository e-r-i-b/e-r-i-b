<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="frm" %>
<%@ taglib  uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib  uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/finances/categoriesGraph">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">
            <sl:collection id="categoryItem" property="data" model="xml-list" title="categories">
                <sl:collectionItem title="category">
                    <id><bean:write name="categoryItem" property="id" ignore="true"/></id>
                    <name><bean:write name="categoryItem" property="name" ignore="true"/></name>
                    <nationalSum>
                        <amount><bean:write name="categoryItem" property="categorySum" ignore="true"/></amount>
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