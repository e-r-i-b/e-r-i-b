<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${CallCentreForm}"/>
<html:form action="/ermb/migration/callCentre" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" value="CallCenterReport"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                doOnLoad(function(){
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/ermb/migration/callCentre/downloading')}"/>
                        clientBeforeUnload.showTrigger=false;
                        goTo('${downloadFileURL}');
                        clientBeforeUnload.showTrigger=false;
                    }
                });
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="callCentreReport"/>
                <tiles:put name="name">
                    <bean:message bundle="migrationBundle" key="unloading.call.centre.name"/>
                </tiles:put>
                <tiles:put name="description" value=""/>
                <tiles:put name="data" value=""/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.unload"/>
                        <tiles:put name="commandHelpKey" value="button.unload.help"/>
                        <tiles:put name="bundle" value="migrationBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>