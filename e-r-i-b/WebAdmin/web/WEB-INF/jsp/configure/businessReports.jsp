<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/reports/business/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="showCheckbox" value="${form.replication}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="replicateAccessService" value="xxx"/>
        <tiles:put name="tilesDefinition" value="businessReports"/>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="personsConfigure"/>
                <tiles:put name="name" value=""/>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <div class="reportBOContainer">
                        <h2 class="reportTtl"><bean:message bundle="configureBundle" key="reports.business.label.base"/></h2>

                        <table class="BOSettings">
                            <tr>
                                <td class="alignRight"><bean:message bundle="configureBundle" key="reports.business.label.report"/>:</td>
                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <input type="checkbox" id="reports.business.all.on" onclick="selectReports(this.checked);"/> <bean:message bundle="configureBundle" key="reports.business.label.allreports"/>
                                            </td>
                                        </tr>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.transfer.on"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.transfer"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="checkbox"/>
                                        </tiles:insert>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.payment.on"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.payment"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="checkbox"/>
                                        </tiles:insert>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.openaccount.on"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.openaccount"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="checkbox"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.allreports.deviation"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.deviation"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="textSize" value="2"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.percent"/></tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.transfer.deviation"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.deviation"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="textSize" value="2"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.percent"/></tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.payment.deviation"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.deviation"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="textSize" value="2"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.percent"/></tiles:put>
                                        </tiles:insert>

                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.openaccount.deviation"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.deviation"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="text"/>
                                            <tiles:put name="textSize" value="2"/>
                                            <tiles:put name="inputDesc"><bean:message bundle="configureBundle" key="reports.business.label.percent"/></tiles:put>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <c:set var="blocks">
                                            all@Все
                                            <c:forEach var="nodeId" items="${form.nodes}">
                                                |${nodeId}@Блок${nodeId}
                                            </c:forEach>
                                        </c:set>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.block"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.block"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="select"/>
                                            <tiles:put name="selectItems" value="${blocks}"/>
                                            <tiles:put name="requiredField" value="true"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.channel"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.channel"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="select"/>
                                            <tiles:put name="selectItems" value="all@Все|mobile@МП|internet@СБОЛ|atm@УС"/>
                                            <tiles:put name="requiredField" value="true"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                            </tr>
                        </table>

                        <div class="clear"></div>

                        <h2 class="reportTtl"><bean:message bundle="configureBundle" key="reports.business.label.mail"/></h2>
                        <table class="BOSettings">
                            <tr>
                                <td style="width: 36%;">
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.mail.receivers"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.mail.receivers"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="text"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td style="width: 37%;">
                                    <table class="BOSettingsText">
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.mail.theme"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.mail.theme"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="textMaxLength" value="10"/>
                                            <tiles:put name="fieldType" value="textarea"/>
                                            <tiles:put name="requiredField" value="true"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table class="BOSettingsText">
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.mail.text"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.mail.text"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="textarea"/>
                                            <tiles:put name="textMaxLength" value="10"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                            </tr>
                        </table>

                        <br/>
                        <br/>
                        <div class="clear"></div>

                    </div>
                    <div class="reportBOContainer">
                        <h2 class="reportTtl"><bean:message bundle="configureBundle" key="reports.business.label.on"/></h2>
                        <table class="unloadTime">
                            <tiles:insert definition="propertyField" flush="false">
                                <tiles:put name="fieldName" value="reports.business.mail.time"/>
                                <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.mail.time"/></tiles:put>
                                <tiles:put name="showHint" value="none"/>
                                <tiles:put name="fieldType" value="text"/>
                                <tiles:put name="styleClass" value="short-time-template"/>
                            </tiles:insert>
                        </table>
                        <table class="autoWidthTbl">
                            <tr>
                                <td>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="com.rssl.phizic.logging.MonitoringOperation.on"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.log.on"/></tiles:put>
                                            <tiles:put name="onchange" value="monitoringOperationChanged();"/>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="select"/>
                                            <tiles:put name="selectItems" value="false@Нет|true@Да"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.aggregate.on"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.create.on"/></tiles:put>
                                            <tiles:put name="onchange" value="aggregateChanged();"/>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="select"/>
                                            <tiles:put name="selectItems" value="false@Нет|true@Да"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <tiles:insert definition="propertyField" flush="false">
                                            <tiles:put name="fieldName" value="reports.business.send.on"/>
                                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="reports.business.label.send.on"/></tiles:put>
                                            <tiles:put name="showHint" value="none"/>
                                            <tiles:put name="fieldType" value="select"/>
                                            <tiles:put name="selectItems" value="false@Нет|true@Да"/>
                                        </tiles:insert>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <script type="text/javascript">
                        function selectReports(checked)
                        {
                            getField('reports.business.transfer.on').checked = checked;
                            getField('reports.business.payment.on').checked = checked;
                            getField('reports.business.openaccount.on').checked = checked;
                        }

                        $(document).ready(function(){
                            ensureElement('reports.business.all.on').checked = getField('reports.business.transfer.on').checked &&
                                    getField('reports.business.payment.on').checked && getField('reports.business.openaccount.on').checked;
                            monitoringOperationChanged();
                        })

                        function aggregateChanged()
                        {
                            var aggregateState = getField('reports.business.aggregate.on').value;
                            if (aggregateState == "true")
                            {
                                getField('reports.business.send.on').disabled = false;
                            }
                            else
                            {
                                getField('reports.business.send.on').disabled = true;
                                getField('reports.business.send.on').value = false;
                            }
                        }

                        function monitoringOperationChanged()
                        {
                            var monitoringState = getField('com.rssl.phizic.logging.MonitoringOperation.on').value;
                            if (monitoringState == "true")
                            {
                                getField('reports.business.aggregate.on').disabled = false;
                            }
                            else
                            {
                                getField('reports.business.aggregate.on').disabled = true;
                                getField('reports.business.aggregate.on').value = false;
                            }
                            aggregateChanged();
                        }

                        function getFields()
                        {
                            var fields = new Array();
                            i = 0;
                            $('input[type!="hidden"]').each(function(){
                                if (isNotEmpty($(this).attr('name')))
                                {
                                    fields[i] = {
                                            name:$(this).attr('name'),
                                            value:$(this).attr('value')
                                    };
                                    i++;
                                }
                            });
                            $('textarea').each(function(){
                                fields[i] = {
                                    name:$(this).attr('name'),
                                    value:$(this).attr('value')
                                };
                                i++;
                            });
                            $('select').each(function(){
                                fields[i] = {
                                    name:$(this).attr('name'),
                                    value:$(this).attr('value')
                                };
                                i++;
                            });
                            return fields;
                        }
                    </script>
                </tiles:put>
            </tiles:insert>
            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.once.upload"/>
                    <tiles:put name="commandHelpKey" value="button.once.upload.help"/>
                    <tiles:put name="bundle"  value="configureBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="onclick" value="openUploadReport();"/>
                </tiles:insert>
                <script type="text/javascript">
                    function openUploadReport()
                    {
                        var url = "${phiz:calculateActionURL(pageContext,'/reports/business/configure/UploadReport')}";
                        openWindow(null, url, "uploadReport", "resizable=1,menubar=0,toolbar=0,scrollbars=1,height=300,width=1024");
                    }
                </script>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>
