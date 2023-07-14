<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/dictionaries/provider/replication/task/view" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="providersMain">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="pageTitle">
            <bean:message bundle="providerBundle" key="background.replication.task.view.title"/>
        </tiles:put>

        <tiles:put name="submenu" value="BackgroundProviderReplication"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="action" value="/dictionaries/provider/replication/task/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="background.replication.task.view.name" bundle="providerBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <tr>
                        <td colspan="2">
                            <fieldset>
                                <legend>
                                    Параметры задачи "Загрузка справочника поставщиков услуг"
                                </legend>
                                <table cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td class="Width160 LabelAll">Номер</td>
                                        <td>${frm.task.id}</td>
                                    </tr>
                                    <tr>
                                        <td class="Width160 LabelAll">Дата создания</td>
                                        <td><fmt:formatDate value="${frm.task.creationDate.time}"
                                                            pattern="dd.MM.yyyy HH:mm:ss"/></td>
                                    </tr>
                                    <tr>
                                        <td class="Width160 LabelAll">ФИО сотрудника</td>
                                        <td>${frm.task.ownerFIO}</td>
                                    </tr>
                                    <tr>
                                        <td class="Width160 LabelAll">Статус</td>
                                        <td><bean:message
                                                key="background.replication.label.status.${frm.task.state}"
                                                bundle="providerBundle"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                    <c:if test="${frm.task.state ne 'NEW'}">
                        <tr>
                            <td class="Width160 LabelAll">Дата начала обработки</td>
                            <td><fmt:formatDate value="${frm.task.result.startDate.time}"
                                                 pattern="dd.MM.yyyy HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td class="Width160 LabelAll">Дата окончания обработки</td>
                            <td><fmt:formatDate value="${frm.task.result.endDate.time}"
                                                 pattern="dd.MM.yyyy HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <fieldset>
                                    <legend>
                                        Статистика обработки файла репликации
                                    </legend>
                                    <table cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td class="Width160 LabelAll">Корректных поставщиков</td>
                                            <td>${frm.task.result.sourceProcessedRecordsCount}</td>
                                        </tr>
                                        <tr>
                                            <td class="Width160 LabelAll">Ошибочных поставщиков</td>
                                            <td>${frm.task.result.sourceFailedRecordsCount}</td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <fieldset>
                                    <legend>
                                        Статистика репликации
                                    </legend>
                                    <table cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td class="Width160 LabelAll">Добавлено</td>
                                            <td>${frm.task.result.destinationInseredRecordsCount}</td>
                                        </tr>
                                        <tr>
                                            <td class="Width160 LabelAll">Обновлено</td>
                                            <td>${frm.task.result.destinationUpdatedRecordsCount}</td>
                                        </tr>
                                        <tr>
                                            <td class="Width160 LabelAll">Удалено</td>
                                            <td>${frm.task.result.destinationDeletedRecordsCount}</td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <script type="text/javascript">
                                    doOnLoad(function()
                                    {
                                        if (${frm.fields.relocateToDownload != null && frm.fields.relocateToDownload == true})
                                        {
                                            <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/dictionaries/provider/replication/task/downloading')}"/>
                                            clientBeforeUnload.showTrigger=false;
                                            goTo('${downloadFileURL}');
                                            clientBeforeUnload.showTrigger=false;
                                        }    
                                    });
                                </script>
                                <tiles:put name="buttons">
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey"
                                                   value="button.download.replication.report"/>
                                        <tiles:put name="commandHelpKey"
                                                   value="button.download.replication.report"/>
                                        <tiles:put name="bundle" value="providerBundle"/>
                                    </tiles:insert>
                                </tiles:put>
                            </td>
                        </tr>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
