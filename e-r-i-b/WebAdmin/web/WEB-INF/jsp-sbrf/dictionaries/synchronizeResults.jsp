<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/dictionaries/synchronize">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="dictionariesEdit">
        <tiles:put name="submenu" type="string" value="Synchronize"/>
        <tiles:put name="data" type="string">
            <logic:iterate id="result" name="form" property="synchronizeResults">
                <div>
                    <c:if test="${result.dictionaryType == 'SPOOBK2'}">
                        <c:set var="hasError"    value="${not empty(result.errors)}"/>
                        <c:set var="isTemporary" value="${form.temporary}"/>
                        <c:set var="resultCount" value="${fn:length(result.resultRecords)}"/>

                        <c:choose>
                            <c:when test="${isTemporary}">
                                <c:set var="titleText" value="Результат загрузки справочника соответствия кодов ВСП"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="titleText" value="Результат обработки справочника соответствия кодов ВСП"/>
                            </c:otherwise>
                        </c:choose>

                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name"       type="string" value="${titleText}"/>

                            <tiles:put name="data"       type="string">

                                <c:choose>
                                    <c:when test="${not hasError}">
                                        <c:choose>
                                            <c:when test="${isTemporary}">
                                                <logic:iterate id="message" name="result" property="messages">
                                                    <div>
                                                        <c:out value="${message}"/>
                                                    </div>
                                                </logic:iterate>
                                            </c:when>
                                            <c:otherwise>
                                                <div>
                                                    Подготовлен справочник перекодировки из <span>${resultCount}</span> записей по ВСП. Провести обновление справочника?
                                                </div>

                                                <input type="hidden" name="temporary" value="true"/>
                                                <input type="hidden" name="selected"  value="DepartmentsRecodingDictionary"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>

                                    <c:otherwise>
                                        <logic:iterate id="error" name="result" property="errors">
                                            <div>
                                                <c:out value="${error}"/>
                                            </div>
                                        </logic:iterate>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>

                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"     value="button.back"/>
                                    <tiles:put name="commandHelpKey"     value="button.back.help"/>
                                    <tiles:put name="bundle"             value="dictionariesBundle"/>
                                    <tiles:put name="action"             value="/dictionaries/synchronize"/>
                                </tiles:insert>

                                <c:if test="${not hasError && not isTemporary && resultCount > 0}">
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey"         value="button.loadDictionaries"/>
                                        <tiles:put name="commandHelpKey"     value="button.loadDictionaries.help"/>
                                        <tiles:put name="bundle"             value="employeesBundle"/>
                                    </tiles:insert>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </div>
                <%-- Результаты для справочников общего вида --%>
                <div>
                    <c:if test="${result.dictionaryType == 'OTHER'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Результаты загрузки справочников"/>
                            <tiles:put name="data" type="string">
                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>
                                <logic:iterate id="message" name="result" property="messages">
                                    <div>
                                        <c:out value="${message}"/>
                                    </div>
                                </logic:iterate>
                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/dictionaries/synchronize"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <%-- Результаты для справочника депозитных продуктов --%>
                    <c:if test="${result.dictionaryType == 'DEPOSIT'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Протокол загрузки справочника вкладов"/>
                            <tiles:put name="data" type="string">
                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>
                                <c:if test="${empty result.errors}">
                                    <c:set var="successDeposits"/>
                                    <c:set var="successCount" value="0"/>
                                    <c:set var="partSuccessDeposits"/>
                                    <c:set var="partFailDeposits"/>
                                    <c:set var="partFailCount" value="0"/>
                                    <%--
                                        При разборе справочника видов вкладов ЦАС НСИ выделяется 4 типа ошибок,
                                        собираем номера видов, по типам ошибок.

                                        QVB - указанный номер не найден в справочнике, либо дата окончания приема
                                              вклада меньше текущей даты.
                                        QVKL_VAL - для данного вида вклада нет ни одной записи по валютам.
                                        DCF_TAR - для данного вида вклада не указаны процентные ставки.
                                        BCH_BUX - для данного вида вкладов нет записей в таблице с бухгалтерской
                                                  информацией
                                    --%>
                                    <c:set var="qvbError"/>
                                    <c:set var="qvklValError"/>
                                    <c:set var="dcfTarError"/>
                                    <c:set var="bchBuxError"/>
                                    <c:set var="cardDescError"/>
                                    <c:set var="failCount" value="0"/>

                                    <c:forEach items="${result.resultRecords}" var="resultRecord">
                                        <c:if test="${resultRecord.status == 'SUCCESS'}">
                                            <c:choose>
                                                <c:when test="${empty resultRecord.errorDescriptions}">
                                                    <c:set var="successDeposits" value="${successDeposits}${resultRecord.synchKey}, "/>
                                                    <c:set var="successCount" value="${successCount + 1}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="successSubTypes" value=""/>
                                                    <c:set var="failSubTypes" value=""/>
                                                    <c:set var="recordSuccessCount" value="0"/>
                                                    <c:set var="recordFailCount" value="0"/>
                                                    <c:forEach items="${resultRecord.errorDescriptions}" var="errorDescription">
                                                        <c:choose>
                                                            <c:when test="${fn:substringAfter(errorDescription, '|') eq 'OK'}">
                                                                <c:set var="recordSuccessCount" value="${recordSuccessCount + 1}"/>
                                                                <c:set var="successSubTypes" value="${successSubTypes}${fn:substringBefore(errorDescription, '|')}, "/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="recordFailCount" value="${recordFailCount + 1}"/>
                                                                <c:set var="failSubTypes" value="${failSubTypes}${fn:substringBefore(errorDescription, '|')}, "/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:if test="${recordSuccessCount ne 0}">
                                                        <c:set var="successCount" value="${successCount + 1}"/>
                                                        <c:set var="length" value="${fn:length(successSubTypes)}"/>
                                                        <c:set var="partSuccessDeposits">
                                                            ${partSuccessDeposits}
                                                            <tiles:insert definition="simpleFormRow" flush="false">
                                                                <tiles:put name="title">
                                                                    Вид:
                                                                </tiles:put>
                                                                <tiles:put name="needMargin" value="true"/>
                                                                <tiles:put name="data">
                                                                    ${resultRecord.synchKey}
                                                                </tiles:put>
                                                            </tiles:insert>
                                                            <tiles:insert definition="simpleFormRow" flush="false">
                                                               <tiles:put name="title">
                                                                   Подвид:
                                                               </tiles:put>
                                                               <tiles:put name="needMargin" value="true"/>
                                                               <tiles:put name="data">
                                                                   <c:out value="${fn:substring(successSubTypes, 0, length-2)}"/>
                                                               </tiles:put>
                                                            </tiles:insert>
                                                        </c:set>
                                                    </c:if>
                                                    <c:if test="${recordFailCount ne 0}">
                                                        <c:set var="partFailCount" value="${partFailCount + 1}"/>
                                                        <c:set var="length" value="${fn:length(failSubTypes)}"/>
                                                        <c:set var="partFailDeposits">
                                                            ${partFailDeposits}
                                                            <tiles:insert definition="simpleFormRow" flush="false">
                                                               <tiles:put name="title">
                                                                   Вид:
                                                               </tiles:put>
                                                               <tiles:put name="needMargin" value="true"/>
                                                               <tiles:put name="data">
                                                                   ${resultRecord.synchKey}
                                                               </tiles:put>
                                                            </tiles:insert>
                                                            <tiles:insert definition="simpleFormRow" flush="false">
                                                               <tiles:put name="title">
                                                                   Подвид:
                                                               </tiles:put>
                                                               <tiles:put name="needMargin" value="true"/>
                                                               <tiles:put name="data">
                                                                   <c:out value="${fn:substring(failSubTypes, 0, length-2)}:"/>
                                                                   <div>Подвид не найден, либо неверные данные в справочнике</div>
                                                               </tiles:put>
                                                            </tiles:insert>
                                                        </c:set>
                                                        <c:if test="${recordSuccessCount eq 0}">
                                                            <c:set var="failCount" value="${failCount + 1}"/>
                                                        </c:if>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <c:if test="${resultRecord.status == 'FAIL'}">
                                            <c:set var="failCount" value="${failCount + 1}"/>
                                            <c:forEach items="${resultRecord.errorDescriptions}" var="errorDescription">
                                                <c:if test="${errorDescription == 'QVB'}">
                                                    <c:set var="qvbError" value="${qvbError}${resultRecord.synchKey}, "/>
                                                </c:if>
                                                <c:if test="${errorDescription == 'QVKL_VAL'}">
                                                    <c:set var="qvklValError" value="${qvklValError}${resultRecord.synchKey}, "/>
                                                </c:if>
                                                <c:if test="${errorDescription == 'DCF_TAR'}">
                                                    <c:set var="dcfTarError" value="${dcfTarError}${resultRecord.synchKey}, "/>
                                                </c:if>
                                                <c:if test="${errorDescription == 'BCH_BUX'}">
                                                    <c:set var="bchBuxError" value="${bchBuxError}${resultRecord.synchKey}, "/>
                                                </c:if>
                                                <c:if test="${errorDescription == 'CRD_PDCT'}">
                                                    <c:set var="cardDescError" value="${cardDescError}${resultRecord.synchKey}, "/>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${successCount ne 0}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                           <tiles:put name="title">
                                               <b>Успешно загружены виды вкладов:</b>
                                           </tiles:put>
                                        </tiles:insert>
                                        <c:if test="${not empty partSuccessDeposits}">
                                            ${partSuccessDeposits}
                                        </c:if>
                                        <c:if test="${not empty successDeposits}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(successDeposits)}"/>
                                                   <c:out value="${fn:substring(successDeposits, 0, length-2)}"/>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${failCount ne 0 or partFailCount ne 0}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                           <tiles:put name="title">
                                               <b>Не загружены виды вкладов:</b>
                                           </tiles:put>
                                        </tiles:insert>
                                        <c:if test="${not empty partFailDeposits}">
                                            ${partFailDeposits}
                                        </c:if>
                                        <c:if test="${not empty qvbError}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(qvbError)}"/>
                                                   <c:out value="${fn:substring(qvbError, 0, length-2)}:"/>
                                                   <div>Указанный номер не найден, либо неверные данные в справочнике.</div>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                        <c:if test="${not empty qvklValError}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(qvklValError)}"/>
                                                   <c:out value="${fn:substring(qvklValError, 0, length-2)}:"/>
                                                   <div>Отсутствуют данные по валюте</div>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                        <c:if test="${not empty dcfTarError}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(dcfTarError)}"/>
                                                   <c:out value="${fn:substring(dcfTarError, 0, length-2)}:"/>
                                                   <div>Не определены процентные ставки</div>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                        <c:if test="${not empty bchBuxError}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(bchBuxError)}"/>
                                                   <c:out value="${fn:substring(bchBuxError, 0, length-2)}:"/>
                                                   <div>Для указанных номеров видов вкладов нет ни одной записи в таблице с информацией по бухгалтерскому учету</div>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                        <c:if test="${not empty cardDescError}">
                                            <tiles:insert definition="simpleFormRow" flush="false">
                                               <tiles:put name="title">
                                                   Вид:
                                               </tiles:put>
                                               <tiles:put name="needMargin" value="true"/>
                                               <tiles:put name="data">
                                                   <c:set var="length" value="${fn:length(cardDescError)}"/>
                                                   <c:out value="${fn:substring(cardDescError, 0, length-2)}:"/>
                                                   <div>Не является вкладным продуктом</div>
                                               </tiles:put>
                                            </tiles:insert>
                                        </c:if>
                                    </c:if>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <b>Итого:</b>
                                       </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           Загружено видов вкладов:
                                       </tiles:put>
                                       <tiles:put name="needMargin" value="true"/>
                                       <tiles:put name="data">
                                           <c:out value="${successCount}"/>
                                       </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           Не загружено видов вкладов:
                                       </tiles:put>
                                       <tiles:put name="needMargin" value="true"/>
                                       <tiles:put name="data">
                                           <c:out value="${failCount}"/>
                                       </tiles:put>
                                    </tiles:insert>
                                </c:if>
                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/dictionaries/synchronize"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false" service="DepositsManagement">
                                    <tiles:put name="commandTextKey" value="button.to.dictionary"/>
                                    <tiles:put name="commandHelpKey" value="button.to.dictionary.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/deposits/list"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <%-- Результаты для справочника карточных продуктов --%>
                    <c:if test="${result.dictionaryType == 'CARD'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Протокол загрузки справочника карт"/>
                            <tiles:put name="data" type="string">
                                <c:set var="successCards"/>
                                <c:set var="currecnyCardError"/>
                                <c:set var="dateCardError"/>
                                 <c:set var="currencyNoCardError"/>
                                <c:set var="successCount" value="0"/>
                                <c:set var="errorCount" value="0"/>
                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>

                                <c:forEach items="${result.resultRecords}" var="resultRecord">
                                    <c:if test="${resultRecord.status == 'SUCCESS'}">
                                        <c:set var="successCards" value="${successCards}${resultRecord.synchKey}, "/>
                                        <c:set var="successCount" value="${successCount + 1}"/>
                                    </c:if>
                                    <c:if test="${resultRecord.status == 'FAIL'}">
                                        <c:set var="errorCount" value="${errorCount + 1}"/>
                                        <c:forEach items="${resultRecord.errorDescriptions}" var="errorDescription">
                                            <c:if test="${errorDescription == 'CUR_CARD'}">
                                                 <c:set var="currecnyCardError" value="${currecnyCardError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                            <c:if test="${errorDescription == 'DT_CARD'}">
                                                 <c:set var="dateCardError" value="${dateCardError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                            <c:if test="${errorDescription == 'CUR_NO'}">
                                                 <c:set var="currencyNoCardError" value="${currencyNoCardError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>

                                <c:if test="${successCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Успешно загружены виды карт:
                                        </tiles:put>
                                        <tiles:put name="needMargin" value="true"/>
                                        <tiles:put name="data">
                                            <c:set var="length" value="${fn:length(successCards)}"/>
                                            <c:out value="${fn:substring(successCards, 0, length-2)}"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>

                                <c:if test="${errorCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Не загружены виды карт:
                                        </tiles:put>
                                    </tiles:insert>
                                    <c:if test="${not empty currecnyCardError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(currecnyCardError)}"/>
                                                <c:out value="${fn:substring(currecnyCardError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для карты не найдена соответствующая валюта
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty dateCardError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(dateCardError)}"/>
                                                <c:out value="${fn:substring(dateCardError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для карты не найдена дата прекращения открытия вкладов
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty currencyNoCardError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(currencyNoCardError)}"/>
                                                <c:out value="${fn:substring(currencyNoCardError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для указанного вида карты отсутствует валюта в справочнике ЦАС НСИ
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <b>Итого:</b>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Загружено видов карт:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${successCount}"/>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Не загружено видов карт:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${errorCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/dictionaries/synchronize"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <%-- Результаты для справочника ОМС --%>
                    <c:if test="${result.dictionaryType == 'IMA'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Протокол загрузки справочника ОМС"/>
                            <tiles:put name="data" type="string">
                                <c:set var="successIMAs"/>
                                <c:set var="currecnyIMAError"/>
                                <c:set var="templateIMAError"/>
                                 <c:set var="currencyNoIMAError"/>
                                <c:set var="successCount" value="0"/>
                                <c:set var="errorCount" value="0"/>
                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>

                                <c:forEach items="${result.resultRecords}" var="resultRecord">
                                    <c:if test="${resultRecord.status == 'SUCCESS'}">
                                        <c:set var="successIMAs" value="${successIMAs}${resultRecord.synchKey}, "/>
                                        <c:set var="successCount" value="${successCount + 1}"/>
                                    </c:if>
                                    <c:if test="${resultRecord.status == 'FAIL'}">
                                        <c:set var="errorCount" value="${errorCount + 1}"/>
                                        <c:forEach items="${resultRecord.errorDescriptions}" var="errorDescription">
                                            <c:if test="${errorDescription == 'CUR_IMA'}">
                                                 <c:set var="currecnyIMAError" value="${currecnyIMAError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                            <c:if test="${errorDescription == 'IMA_TEMPLATE_NO'}">
                                                 <c:set var="templateIMAError" value="${templateIMAError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                            <c:if test="${errorDescription == 'IMA_CUR_NO'}">
                                                 <c:set var="currencyNoIMAError" value="${currencyNoIMAError}${resultRecord.synchKey}, "/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>

                                <c:if test="${successCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Успешно загружены виды ОМС:
                                        </tiles:put>
                                        <tiles:put name="needMargin" value="true"/>
                                        <tiles:put name="data">
                                            <c:set var="length" value="${fn:length(successIMAs)}"/>
                                            <c:out value="${fn:substring(successIMAs, 0, length-2)}"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>

                                <c:if test="${errorCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Не загружены виды ОМС:
                                        </tiles:put>
                                    </tiles:insert>
                                    <c:if test="${not empty currecnyIMAError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(currecnyIMAError)}"/>
                                                <c:out value="${fn:substring(currecnyIMAError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для ОМС не найдена соответствующая валюта
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty templateIMAError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(templateIMAError)}"/>
                                                <c:out value="${fn:substring(templateIMAError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для ОМС не найден шаблон договора
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty currencyNoIMAError}">
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <c:set var="length" value="${fn:length(currencyNoIMAError)}"/>
                                                <c:out value="${fn:substring(currencyNoIMAError, 0, length-2)}:"/>
                                            </tiles:put>
                                            <tiles:put name="needMargin" value="true"/>
                                            <tiles:put name="data">
                                                Для указанного вида ОМС отсутствует валюта в справочнике ЦАС НСИ
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <b>Итого:</b>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Загружено видов ОМС:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${successCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Не загружено видов ОМС:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${errorCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/dictionaries/synchronize"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <%-- Результаты для справочника DEF-кодов --%>
                    <c:if test="${result.dictionaryType == 'DEF_CODES'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Протокол загрузки справочника def-кодов"/>
                            <tiles:put name="data" type="string">
                                <c:set var="successDefCodes"/>
                                <c:set var="emptyServiceIdError"/>
                                <c:set var="notFoundProviderError"/>
                                <c:set var="moreThanOneProviderError"/>
                                <c:set var="successDefCodeCount" value="0"/>
                                <c:set var="errorDefCodeCount" value="0"/>
                                <c:set var="emptyServiceIdCount" value="0"/>
                                <c:set var="notFoundProviderErrorCount" value="0"/>
                                <c:set var="moreThanOneProviderErrorCount" value="0"/>

                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>

                                <c:forEach items="${result.resultRecords}" var="resultRecord">
                                    <c:if test="${resultRecord.status == 'SUCCESS'}">
                                        <c:set var="successDefCodes" value="${successDefCodes}${resultRecord.synchKey}, "/>
                                        <c:set var="successDefCodeCount" value="${successDefCodeCount + 1}"/>
                                    </c:if>
                                    <c:if test="${resultRecord.status == 'FAIL'}">
                                        <c:set var="errorDefCodeCount" value="${errorDefCodeCount + 1}"/>
                                        <c:forEach items="${resultRecord.errorDescriptions}" var="errorDescription">
                                           <c:if test="${errorDescription == 'EMPTY_SERVICE_ID'}">
                                                <c:set var="emptyServiceIdError" value="${emptyServiceIdError}${resultRecord.synchKey}, "/>
                                                <c:set var="emptyServiceIdCount" value="${emptyServiceIdCount + 1}"/>
                                           </c:if>
                                           <c:if test="${errorDescription == 'PROVIDER_NOT_FOUND'}">
                                                <c:set var="notFoundProviderError" value="${notFoundProviderError}${resultRecord.synchKey}, "/>
                                                <c:set var="notFoundProviderErrorCount" value="${notFoundProviderErrorCount + 1}"/>
                                           </c:if>
                                            <c:if test="${errorDescription == 'PROVIDER_MORE_THAN_ONE'}">
                                                <c:set var="moreThanOneProviderError" value="${moreThanOneProviderError}${resultRecord.synchKey}, "/>
                                                <c:set var="moreThanOneProviderErrorCount" value="${moreThanOneProviderErrorCount + 1}"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>

                                <c:if test="${emptyServiceIdCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Для Def-кодов не указан SERVICE_ID:
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <c:set var="length" value="${fn:length(emptyServiceIdError)}"/>
                                            <c:out value="${fn:substring(emptyServiceIdError, 0, length-2)}:"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${notFoundProviderErrorCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Для Def-кодов не найден поставщик:
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <c:set var="length" value="${fn:length(notFoundProviderError)}"/>
                                            <c:out value="${fn:substring(notFoundProviderError, 0, length-2)}:"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${moreThanOneProviderErrorCount ne 0}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            Для Def-кодов найдено больше одного поставщика:
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <c:set var="length" value="${fn:length(moreThanOneProviderError)}"/>
                                            <c:out value="${fn:substring(moreThanOneProviderError, 0, length-2)}:"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <b>Итого:</b>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Загружено def-кодов:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${successDefCodeCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Не загружено def-кодов:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${errorDefCodeCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                            <tiles:put name="buttons">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back"/>
                                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                                    <tiles:put name="bundle" value="dictionariesBundle"/>
                                    <tiles:put name="action" value="/dictionaries/synchronize"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <%-- Результаты для справочника MNP-телефонов --%>
                    <c:if test="${result.dictionaryType == 'MNP_PHONES'}">
                        <tiles:insert definition="paymentForm" flush="false">
                            <tiles:put name="name" value="Протокол загрузки справочника mnp-телефонов"/>
                            <tiles:put name="data" type="string">
                                <logic:iterate id="error" name="result" property="errors">
                                    <div>
                                        <c:out value="${error}"/>
                                    </div>
                                </logic:iterate>

                                <c:set var="mnpPhoneCount" value="0"/>
                                <c:set var="successMNPPhoneCount" value="0"/>
                                <c:set var="errorMNPPhoneCount" value="0"/>

                                <c:forEach items="${result.resultRecords}" var="resultRecord">
                                    <c:if test="${resultRecord.status == 'SUCCESS'}">
                                        <c:set var="mnpPhoneCount" value="${mnpPhoneCount + 1}"/>
                                        <c:set var="successMNPPhoneCount" value="${successMNPPhoneCount + 1}"/>
                                    </c:if>
                                    <c:if test="${resultRecord.status == 'FAIL'}">
                                        <c:set var="mnpPhoneCount" value="${mnpPhoneCount + 1}"/>
                                        <c:set var="errorMNPPhoneCount" value="${errorMNPPhoneCount + 1}"/>
                                    </c:if>
                                </c:forEach>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <b>Итого:</b>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Всего обработано записей:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${mnpPhoneCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Загружено mnp-телефонов:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${successMNPPhoneCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        Не загружено mnp-телефонов:
                                    </tiles:put>
                                    <tiles:put name="needMargin" value="true"/>
                                    <tiles:put name="data">
                                        <c:out value="${errorMNPPhoneCount}"/>
                                    </tiles:put>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </div>
            </logic:iterate>
        </tiles:put>
    </tiles:insert>
</html:form>