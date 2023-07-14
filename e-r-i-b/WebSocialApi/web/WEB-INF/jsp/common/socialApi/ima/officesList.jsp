<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:form action="/private/ima/office/list">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="office" property="data" model="xml-list" title="officeList">
                <sl:collectionItem title="office">
                    <name><c:out value="${office.name}"/></name>
                    <address><c:out value="${office.address}"/></address>
                    <tb><c:out value="${office.code.fields.region}"/></tb>
                    <osb><c:out value="${office.code.fields.branch}"/></osb>
                    <vsp><c:out value="${office.code.fields.office}"/></vsp>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>