<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html:form action="/reports/business/configure/UploadReport">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="configureBundle" key="button.once.upload.report"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.unload"/>
                <tiles:put name="commandHelpKey" value="button.unload.help"/>
                <tiles:put name="bundle"  value="configureBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <div class="reportBOContainer">
                <table class="autoWidthTbl centerTbl">
                    <tr>
                        <td>
                            <table>
                                <tiles:insert definition="propertyField" flush="false">
                                    <tiles:put name="fieldName" value="reports.business.period.from"/>
                                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.period.from"/></tiles:put>
                                    <tiles:put name="showHint" value="none"/>
                                    <tiles:put name="fieldType" value="date"/>
                                </tiles:insert>
                            </table>
                        </td>
                        <td>
                            <table>
                                <tiles:insert definition="propertyField" flush="false">
                                    <tiles:put name="fieldName" value="reports.business.period.to"/>
                                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.period.to"/></tiles:put>
                                    <tiles:put name="showHint" value="none"/>
                                    <tiles:put name="fieldType" value="date"/>
                                </tiles:insert>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
            <script type="text/javascript">
                var fields = window.opener.getFields();
                $.each(fields, function(i,param){
                    $('<input />').attr('type', 'hidden')
                            .attr('name', param.name)
                            .attr('value', param.value)
                            .appendTo('form');
                });
            </script>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        doOnLoad(function(){
            if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
            {
                <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/reports/business/configure/DownloadReport')}"/>
                clientBeforeUnload.showTrigger=false;
                goTo('${downloadFileURL}');
                clientBeforeUnload.showTrigger=false;
            }
        });
    </script>
</html:form>
