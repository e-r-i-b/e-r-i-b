<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<html:form action="/persons/mdm/unload/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="mdmInfo">
        <tiles:put name="submenu" type="string" value="UnloadMDMInfo"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="personsBundle" key="form.unload.mdm.information"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                $(document).ready(
                        function()
                        {
                            <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=UNLOAD_MDM_INFO_FILE_TYPE&clientFileName=${form.fields.clientFileName}"/>
                            clientBeforeUnload.showTrigger=false;
                            goTo('${downloadFileURL}');
                            clientBeforeUnload.showTrigger=false;
                        });
                </c:if>
            </script>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="personsBundle" key="form.field.login.ids"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:textarea styleId="loginIds" property="field(loginIds)" cols="58" rows="3" style="text-align:justify;"/>
                </tiles:put>
            </tiles:insert>

            <div class="pmntFormMainButton floatRight">
                <tiles:insert definition="commandButton" flush="false" operation="UnloadMDMCardInfoOperation">
                    <tiles:put name="commandKey" value="button.unload.mdm.card.info"/>
                    <tiles:put name="commandHelpKey" value="button.unload.mdm.card.info.help"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false" operation="UnloadMDMPersonInfoOperation">
                    <tiles:put name="commandKey" value="button.unload.mdm.client.info"/>
                    <tiles:put name="commandHelpKey" value="button.unload.mdm.client.info.help"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>