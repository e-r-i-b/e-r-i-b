<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/ermb/migration/loading" enctype="multipart/form-data">
    <tiles:insert definition="migrationMain">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="submenu" type="string" value="Load"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="loadingClientList"/>
                <tiles:put name="name">
                    <bean:message bundle="migrationBundle" key="loading.name"/>
                </tiles:put>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="migrationBundle" key="loading.choose.file"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:file property="content" size="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="migrationBundle" key="loading.month.count"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(monthCount)"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.load"/>
                        <tiles:put name="commandHelpKey" value="button.load.help"/>
                        <tiles:put name="bundle" value="migrationBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>