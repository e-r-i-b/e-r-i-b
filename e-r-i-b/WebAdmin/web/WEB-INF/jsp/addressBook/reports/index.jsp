<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="addressBookReports">
    <tiles:put name="pageTitle" type="string"><bean:message bundle="addressBookReportsBundle" key="label.form.index.title"/></tiles:put>
    <tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>
            <tiles:put name="data"><bean:message bundle="addressBookReportsBundle" key="label.form.index.text"/></tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>