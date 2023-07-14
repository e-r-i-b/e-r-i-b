<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/departments/replication/task/view" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="departmentsMain">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="pageTitle">
            <bean:message bundle="departmentsBundle" key="background.replication.task.view.title"/>
        </tiles:put>

        <tiles:put name="submenu" value="ListBackgroundTasks"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="action" value="/departments/replication/task/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="background.replication.task.view.name" bundle="departmentsBundle"/>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <fieldset>
                        <legend>
                            <bean:message key="background.replication.param.task.legend" bundle="departmentsBundle"/>
                        </legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.number.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.task.id}
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.create.date.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <fmt:formatDate value="${frm.task.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.fio.employee.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.task.ownerFIO}
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.status.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <bean:message key="background.replication.label.status.${frm.task.state}" bundle="departmentsBundle"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                    <c:if test="${frm.task.state ne 'NEW'}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.date.begin.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <fmt:formatDate value="${frm.task.result.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="background.replication.task.report.date.end.label" bundle="departmentsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <fmt:formatDate value="${frm.task.result.endDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </tiles:put>
                        </tiles:insert>

                        <fieldset>
                            <legend>
                                <bean:message key="background.replication.statistic.process.file.legend" bundle="departmentsBundle"/>
                            </legend>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="background.replication.task.report.correct.label" bundle="departmentsBundle"/>
                                </tiles:put>
                                <tiles:put name="needMargin" value="true"/>
                                <tiles:put name="data">
                                    ${frm.task.result.sourceProcessedRecordsCount}
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="background.replication.task.report.error.label" bundle="departmentsBundle"/>
                                </tiles:put>
                                <tiles:put name="needMargin" value="true"/>
                                <tiles:put name="data">
                                    ${frm.task.result.sourceFailedRecordsCount}
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                        <c:if test="${frm.task.state ne 'PROCESSING'}">
                            <fieldset>
                                <legend>
                                    <bean:message key="background.replication.statistic.replication.legend" bundle="departmentsBundle"/>
                                </legend>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="background.replication.task.report.added.label" bundle="departmentsBundle"/>
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        ${frm.task.result.destinationInseredRecordsCount}
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="background.replication.task.report.updated.label" bundle="departmentsBundle"/>
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        ${frm.task.result.destinationUpdatedRecordsCount}
                                    </tiles:put>
                                </tiles:insert>
                            </fieldset>
                            <c:if test="${frm.fields.relocateToDownload != null && frm.fields.relocateToDownload == true}">
                                <script type="text/javascript">
                                    doOnLoad(function(){
                                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/departments/replication/task/downloading')}"/>
                                        clientBeforeUnload.showTrigger=false;
                                        goTo('${downloadFileURL}');
                                        clientBeforeUnload.showTrigger=false;
                                    });
                                </script>
                            </c:if>
                            <tiles:put name="buttons">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey"     value="button.download.replication.report"/>
                                    <tiles:put name="commandHelpKey" value="button.download.replication.report.help"/>
                                    <tiles:put name="bundle"         value="departmentsBundle"/>
                                </tiles:insert>
                            </tiles:put>
                        </c:if>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
