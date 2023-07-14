<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/erkc/audit/businessDocument" onsubmit="return setEmptyAction(event)">
    <tiles:insert definition="erkcMain">
        <tiles:put name="pageTitle" type="string" value="Список платежей и заявок"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <%-- фильтр --%>
        <tiles:put name="filter" type="string">

            <c:set var="colCount" value="3" scope="request"/>
            <%-- ------------- row 1 ----------------------------- --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.client"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="name"   value="fio"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
                <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.period"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        с&nbsp;
                        <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                   size="10"
                                   name="filter(fromDate)"
                                   class="dot-date-pick"
                                   maxsize="10"
                                   value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                            <input type="text"
                                   size="8"
                                   name="filter(fromTime)"
                                   maxsize="8"
                                   class="time-template"
                                   value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                   onkeydown="onTabClick(event,'filter(toDate)');"/>
                        </span>
                        &nbsp;по&nbsp;
                        <span style="font-weight:normal;cursor:default;">
                            <input type="text"
                                   size="10"
                                   name="filter(toDate)" class="dot-date-pick"
                                   maxsize="10"
                                   value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                            <input type="text"
                                   size="8"
                                   name="filter(toTime)"
                                   maxsize="8"
                                   class="time-template"
                                   value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                            </span>
                       </span>
                   </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.formName"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        var autoPaymentForms = new Array();

                        function changeAutoPayment()
                        {
                            var ids = ensureElement("filter(formId)").value;
                            ensureElement("filter(autoPayment)").value = autoPaymentForms[ids];
                        }

                        addClearMasks(null,
                                function(event)
                                {
                                    clearInputTemplate('filter(fromDate)','__.__.____');
                                    clearInputTemplate('filter(toDate)','__.__.____');
                                    clearInputTemplate('filter(fromTime)','__:__:__');
                                    clearInputTemplate('filter(toTime)','__:__:__');
                                });
                    </script>

                    <html:select property="filter(formId)" value="${form.filters['formId']}" onclick="changeAutoPayment()" onchange="changeAutoPayment()" styleId="filter(formId)">
                        <html:option value="">Все</html:option>
                        <c:forEach items="${form.filterPaymentForms}" var="frm">
                            <c:set var="formName">
                                <bean:message bundle="auditBundle" key="paymentform.${frm.name}" failIfNone="false"/>
                            </c:set>
                            <c:if test="${not empty formName}">
                                <html:option value="${frm.ids}">
                                    <c:out value="${formName}"/>
                                </html:option>
                            </c:if>

                            <script type="text/javascript">
                                autoPaymentForms['${frm.ids}'] = '${frm.autoPayment}';
                            </script>

                        </c:forEach>
                    </html:select>

                    <html:hidden property="filter(autoPayment)" styleId="filter(autoPayment)"/>
                </tiles:put>
            </tiles:insert>

            <%-- ------------- row 2 ----------------------------- --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.document"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name"   value="dul"/>
                <tiles:put name="size"   value="32"/>
                <tiles:put name="maxlength"  value="32"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="isDefault" value="Серия номер"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.state"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="data">
                    <c:set var="status" value="${form.status}"/>
                    <html:select property="filter(state)" styleClass="filterSelect" style="width:200px" styleId="state">
                        <html:option value="">Все</html:option>
                        <html:option value="INITIAL,SAVED">Введен</html:option>
                        <html:option value="DRAFT">Частично заполнен</html:option>
                        <html:option value="DISPATCHED,SENDED,STATEMENT_READY">Обрабатывается / Принят</html:option>
                        <html:option value="RECEIVED">Получен банком получателем</html:option>
                        <html:option value="COMPLETION">Требуется доработка</html:option>
                        <html:option value="ERROR,UNKNOW,SENT">Приостановлен</html:option>
                        <html:option value="CONSIDERATION">В рассмотрении</html:option>
                        <html:option value="ACCEPTED">Одобрен</html:option>
                        <html:option value="APPROVED">Утвержден</html:option>
                        <html:option value="SUCCESSED">Выдан</html:option>
                        <html:option value="CANCELATION">Аннулирован</html:option>
                        <html:option value="MODIFICATION">Изменён</html:option>
                        <html:option value="RETURNED">Возвращен</html:option>
                        <html:option value="RECALLED">Отозван</html:option>
                        <html:option value="EXECUTED">Исполнен</html:option>
                        <html:option value="REFUSED">Отказан</html:option>
                        <html:option value="WAIT_CONFIRM">Ожидает дополнительной обработки</html:option>
                        <html:option value="ADOPTED">Принята</html:option>
                        <html:option value="BILLING_CONFIRM_TIMEOUT">Таймаут при подтверждении в биллинге (ЕРИБ)</html:option>
                        <html:option value="BILLING_GATE_CONFIRM_TIMEOUT">Таймаут при подтверждении в биллинге (шлюз)</html:option>
                        <html:option value="ABS_RECALL_TIMEOUT">Таймаут при отзыве в АБС (ЕРИБ)</html:option>
                        <html:option value="ABS_GATE_RECALL_TIMEOUT">Таймаут при отзыве в АБС (шлюз)</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <%-- ------------- row 3 ----------------------------- --%>
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.birthDate"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name" value="birthday"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.empty"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showInitialPayments)" styleId="showInitial"/>&nbsp;<b><bean:message bundle="claimsBundle" key="label.show.initial.payments"/></b>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.empty"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showDeleted)" styleId="showDeleted"/>&nbsp;<b><bean:message bundle="claimsBundle" key="label.show.deleted.payments"/></b>
                </tiles:put>
            </tiles:insert>

            <%-- ------------- row 4 ----------------------------- --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.personId"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="name" value="loginId"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false">
            </tiles:insert>


            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.receiver"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name" value="receiverName"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="size" value="35"/>
            </tiles:insert>

            <%-- ------------- row 5 ----------------------------- --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.amount.operation"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="data">
                    от
                    <html:text property="filter(fromAmount)" size="15" maxlength="10"  styleClass="moneyField"/>
                    &nbsp;
                    до
                    <html:text property="filter(toAmount)"   size="15" maxlength="10"  styleClass="moneyField"/>

                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false">
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.paymentNumber"/>
                <tiles:put name="noWrapLabel" value="true"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="name" value="number"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="size" value="35"/>
            </tiles:insert>

            <%-- ------------- row 6 ----------------------------- --%>
            <tiles:insert definition="filterEmptytField" flush="false">
            </tiles:insert>
            <tiles:insert definition="filterEmptytField" flush="false">
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.madeOperationId"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name" value="madeOperationId"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="size" value="35"/>
                <tiles:put name="maxlength" value="100"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.nameOSB"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name" value="nameOSB"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="size" value="35"/>
                <tiles:put name="maxlength" value="100"/>
            </tiles:insert>

            <script type="text/javascript">
                function changeState()
                {
                    var disabled = true;
                    if ($("#state option:selected").val() == '') disabled = false;
                    $("#showInitial").attr("disabled", disabled);
                    $("#showDeleted").attr("disabled", disabled);
                }
                $(document).ready(function(){
                    $("#state").change(function() {
                        changeState();
                    });
                    $('input[name="filter(birthday)"]').next().remove();
                    changeState();
                });

                function initTemplates()
                {}
                initTemplates();
            </script>
        </tiles:put>
        <tiles:put name="fastSearchFilter" value="true"/>
        <tiles:put name="data" type="string">
            <jsp:include page="/WEB-INF/jsp-sbrf/persons/existsAnotherNodeDocumentMessage.jsp" flush="false"/>
            <jsp:include page="/WEB-INF/jsp/audit/auditData.jsp" flush="false"/>
        </tiles:put>
    </tiles:insert>
</html:form>
