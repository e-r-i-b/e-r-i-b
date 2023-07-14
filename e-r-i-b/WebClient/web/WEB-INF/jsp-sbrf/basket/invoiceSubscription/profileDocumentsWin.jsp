<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="profileDocuments"/>
    <tiles:put name="styleClass" value="basketDocuments"/>
    <tiles:put name="data" type="string">
        <tiles:insert definition="customScroll" flush="false">
            <tiles:put name="height" value="500px"/>
            <tiles:put name="data">
                <h1>Мои документы</h1>
                <div class="border-title">
                    <span>ДОКУМЕНТЫ</span>
                </div>
                <div>
                    <c:forEach var="document" items="${phiz:getBasketPassportDocuments()}">
                        <div class="rowTitle"><bean:message key="document.type.${document.documentType}" bundle="userprofileBundle"/></div>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" type="string">
                                <b>Серия и номер</b>
                            </tiles:put>
                            <tiles:put name="data" type="string">
                                <span class="value" onclick='return {type : "${document.documentType}", field : "seriesAndNumber", value : "${document.documentSeries}${phiz:getCutDocumentNumber(document.documentNumber)}"}'>
                                    <b>${document.documentSeries} ${phiz:getCutDocumentNumber(document.documentNumber)}</b>
                                </span>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Кем выдан"/>
                            <tiles:put name="data" type="string">
                                <span class="value" onclick='return {type : "${document.documentType}", field : "issueBy", value : "${phiz:escapeForJS(document.documentIssueBy, true)}"}'>
                                    <c:out value="${document.documentIssueBy}"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Дата выдачи"/>
                            <tiles:put name="data" type="string">
                                <c:if test="${not empty document.documentIssueDate}">
                                    <c:set var="documentIssueDate"><bean:write name="document" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:set>
                                    <span class="value" onclick='return {type : "${document.documentType}", field : "issueDate", value : "${documentIssueDate}", dataType: "date"}'>
                                        ${documentIssueDate}
                                    </span>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Действует до"/>
                            <tiles:put name="data" type="string">
                                <c:if test="${not empty document.documentTimeUpDate}">
                                    <c:set var="documentTimeUpDate"><bean:write name="document" property="documentTimeUpDate.time" format="dd.MM.yyyy"/></c:set>
                                    <span class="value" onclick='return {type : "${document.documentType}", field : "expireDate", value : "${documentTimeUpDate}", dataType: "date"}'>
                                        ${documentTimeUpDate}
                                    </span>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                    </c:forEach>
                </div>
                <div>
                    <c:forEach var="userDocument" items="${phiz:getBasketUserDocuments()}">
                        <div class="rowTitle"><c:out value="${userDocument.name}"/></div>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" type="string">
                                <b>Серия и номер</b>
                            </tiles:put>
                            <tiles:put name="data" type="string">
                                <span class="value" onclick='return {type : "${userDocument.documentType}", field : "seriesAndNumber", value : "${userDocument.series}${userDocument.number}"}'>
                                    <b>${userDocument.series} ${userDocument.number}</b>
                                </span>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Кем выдан"/>
                            <tiles:put name="data" type="string">
                                <span class="value" onclick='return {type : "${userDocument.documentType}", field : "issueBy", value : "${phiz:escapeForJS(userDocument.issueBy, true)}"}'>
                                    <c:out value="${userDocument.issueBy}"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Дата выдачи"/>
                            <tiles:put name="data" type="string">
                                <c:if test="${not empty userDocument.issueDate}">
                                    <c:set var="userDocumentIssueDate"><bean:write name="userDocument" property="issueDate.time" format="dd.MM.yyyy"/></c:set>
                                    <span class="value" onclick='return {type : "${userDocument.documentType}", field : "issueDate", value : "${userDocumentIssueDate}", dataType: "date"}'>
                                        ${userDocumentIssueDate}
                                    </span>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="Действует до"/>
                            <tiles:put name="data" type="string">
                                <c:if test="${not empty userDocument.expireDate}">
                                    <c:set var="userDocumentExpireDate"><bean:write name="userDocument" property="expireDate.time" format="dd.MM.yyyy"/></c:set>
                                    <span class="value" onclick='return {type : "${userDocument.documentType}", field : "expireDate", value : "${userDocumentExpireDate}", dataType: "date"}'>
                                        ${userDocumentExpireDate}
                                    </span>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                        <br/>
                    </c:forEach>
                </div>

                <script type="text/javascript">
                    function setChooseValue(formRow, params)
                    {
                        var container =  formRow.find('.paymentValue .paymentInputDiv');
                        container.find(":text").each(function(){
                            var input = $(this),fieldName = input.attr('name');
                            input.val(params['value']);

                            if(params["replace"] != null && !params["replace"])
                            {
                                input.val(input.val() + params['value']);
                                return;
                            }

                            input.val(params['value']);
                            if(isEmpty(params['type']) || isEmpty(params['field']))
                                return;

                            var externalId = fieldName.indexOf('field(') == 0 ?
                                    fieldName.substring('field('.length, fieldName.length - 1) : fieldName;

                            var dovInfoVal = container.find("input[name='" + externalId + "___document']");
                            dovInfoVal.val(params['type'] + '@' + params['field']);
                        });
                    }

                    (function(){
                        $('#profileDocumentsWin').find('.form-row').click(function()
                        {
                            var val = $(this).find('span.value'), el = val.get(0), params;
                            params = el.onclick ? el.onclick() : null;

                            if(params == null || isEmpty(params['value']))
                                return;

                            var winItem = win.getWinItem('profileDocuments');
                            if(params['dataType']!="date" && winItem.dataType ==  "date")
                            {
                                return;
                            }
                            if(winItem.referFormRow)
                            {
                                win.close('profileDocuments');
                                setChooseValue($(winItem.referFormRow), params);
                            }
                        });
                    })();
                </script>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>