<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/replication">
    <tiles:insert definition="providersMain">
       <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="submenu" value="Providers"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="providerBundle"/>
                <tiles:put name="action"         value="/private/dictionaries/provider/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="report.name" bundle="providerBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <fieldset>
                        <legend>
                            Статистика обработки файла репликации
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Корректных поставщиков
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.result.sourceProcessedRecordsCount}
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Ошибочных поставщиков
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.result.sourceFailedRecordsCount}
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend>
                            Статистика репликации
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Добавлено
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.result.destinationInseredRecordsCount}
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Обновлено
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.result.destinationUpdatedRecordsCount}
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Удалено
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                ${frm.result.destinationDeletedRecordsCount}
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend>
                            Детализированный протокол репликации
                        </legend>
                        <pre>${frm.result.report}</pre>
                    </fieldset>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
