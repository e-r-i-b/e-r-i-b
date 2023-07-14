<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/archiving/exception" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="pageName"><bean:message key="label.archiving.form.title" bundle="exceptionEntryBundle"/></tiles:put>
        <tiles:put name="pageDescription"><bean:message key="label.archiving.form.description" bundle="exceptionEntryBundle"/></tiles:put>
        <tiles:put name="submenu" type="string" value="EditExceptionArchivingSettings"/>
        <tiles:put name="data" type="string">
            <fieldset>
                <legend><bean:message key="label.archiving.form.group.automatical.name" bundle="exceptionEntryBundle"/></legend>
                <c:set var="trueOptionLabel"><bean:message key="label.archiving.form.group.automatical.field.use.true" bundle="exceptionEntryBundle"/></c:set>
                <c:set var="falseOptionLabel"><bean:message key="label.archiving.form.group.automatical.field.use.false" bundle="exceptionEntryBundle"/></c:set>
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.report.exception.archiving.use"/>
                        <tiles:put name="fieldDescription"><bean:message key="label.archiving.form.group.automatical.field.use" bundle="exceptionEntryBundle"/></tiles:put>
                        <tiles:put name="fieldType" value="radio"/>
                        <tiles:put name="fieldHint"><bean:message key="label.archiving.form.group.automatical.field.use.description" bundle="exceptionEntryBundle"/></tiles:put>
                        <tiles:put name="selectItems" value="true@${trueOptionLabel}|false@${falseOptionLabel}"/>
                    </tiles:insert>

                    <tr>
                        <td class="rightAlign">
                            <bean:message key="label.archiving.form.group.automatical.field.path" bundle="exceptionEntryBundle"/>
                        </td>
                        <td><bean:write name="form" property="archivePath" filter="true"/></td>
                    </tr>
                </table>
            </fieldset>

            <c:if test="${!form.replication}">
                <fieldset>
                    <legend><bean:message key="label.archiving.form.group.manual.name" bundle="exceptionEntryBundle"/></legend>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.archiving.form.group.manual.field.date" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(unloadingDate)" styleClass="dot-month-pick"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message key="label.archiving.form.group.manual.field.date.description" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.archiving.form.group.manual.field.type" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <nobr>
                                <html:radio property="field(exceptionType)" value="internal" styleId="exceptionTypeInternal"/>
                                <label class="bold" for="exceptionTypeInternal"><bean:message key="label.archiving.form.group.manual.field.type.internal" bundle="exceptionEntryBundle"/></label>
                            </nobr>
                            <br/>
                            <nobr>
                                <html:radio property="field(exceptionType)" value="external" styleId="exceptionTypeExternal"/>
                                <label class="bold" for="exceptionTypeExternal"><bean:message key="label.archiving.form.group.manual.field.type.external" bundle="exceptionEntryBundle"/></label>
                            </nobr>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>
            </c:if>
        </tiles:put>
        <tiles:put name="formButtons">
            <script type="text/javascript">
                function unloadValidation()
                {
                    if ($('[name=field(unloadingDate)]').val() == "")
                    {
                        alert('<bean:message key="message.archiving.form.group.manual.date.error" bundle="exceptionEntryBundle"/>');
                        return false;
                    }
                    if ($('[name=field(exceptionType)]:checked').length != 1)
                    {
                        alert('<bean:message key="message.archiving.form.group.manual.type.error" bundle="exceptionEntryBundle"/>');
                        return false;
                    }
                    return true;
                }

                $(document).ready(
                    function()
                    {
                        var dateFormat = 'mm.yyyy';
                        if ($('.dot-month-pick').datePicker){
                            var dP = $('.dot-month-pick').datePicker({displayClose: true, chooseImg: skinUrl + '/images/calendar.gif', dateFormat:dateFormat});
                            dP.dpApplyMask();
                        }

                        <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                            <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=ArchivingException&clientFileName=${form.fields.clientFileName}"/>
                            clientBeforeUnload.showTrigger=false;
                            goTo('${downloadFileURL}');
                            clientBeforeUnload.showTrigger=false;
                        </c:if>
                    });
            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                <tiles:put name="action"         value="/settings/archiving/exception"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                <tiles:put name="isDefault"      value="true"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"         value="button.unload"/>
                <tiles:put name="commandHelpKey"     value="button.unload.help"/>
                <tiles:put name="bundle"             value="exceptionEntryBundle"/>
                <tiles:put name="validationFunction" value="unloadValidation();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>