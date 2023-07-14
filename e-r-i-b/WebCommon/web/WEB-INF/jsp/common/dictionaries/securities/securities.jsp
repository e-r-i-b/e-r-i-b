<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:form action="/private/dictionary/securities">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <div id="reference">
                <div id="payment">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="title" value="Справочник ценных бумаг"/>
                        <tiles:put name="data">
                            <script type="text/javascript">
                                $(document).ready(function(){
                                    initReferenceSize();
                                });

                                function sendSecurityData()
                                {
                                    var ids = document.getElementsByName("selectedIds");
                                    var key = getRadioValue(ids);
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var r = ids.item(i).parentNode.parentNode;
                                            var a = new Array(5);
                                            a['inside-code']         = trim(getElementTextContent(r.cells[1]));
                                            a['name']                = trim(getElementTextContent(r.cells[2]));
                                            a['registration-number'] = trim(getElementTextContent(r.cells[5]));
                                            a['nominal-amount']      = trim(getElementTextContent(r.cells[6]));
                                            a['nominal-currency']    = trim(getElementTextContent(r.cells[7]));
                                            window.opener.setSecuritiesInfo(a);                                            
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("Выберите ценную бумагу.");
                                }

                                function clearFilter()
                                {
                                    document.getElementsByName("filter(issuer)")[0].value = '';
                                    document.getElementsByName("filter(type)")[0].value = '';
                                    document.getElementsByName("filter(registrationNumber)")[0].value = '';
                                    new CommandButton("button.filter").click();
                                }


                            </script>

                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/icon_securities.png"/>
                                <tiles:put name="alt" value="Справочник ценных бумаг"/>
                                <tiles:put name="description">
                                    Отметьте интересующую Вас ценную бумагу и нажмите на кнопку <b>Выбрать</b>.
                                </tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>

                            <tiles:insert definition="filter" flush="false">
                                <tiles:put name="data">
                                    <span>Эмитент&nbsp;</span>
                                    <html:text property="filter(issuer)" size="70" maxlength="255"/>
                                </tiles:put>
                                <tiles:put name="hiddenData">
                                    <div class="dictionary-fields">
                                        <span>Тип&nbsp;</span>
                                        <html:select property="filter(type)" style="width:200px">
                                            <html:option value="">Все</html:option>
                                            <c:forEach items="${form.securityTypes}" var="type">
                                                <c:if test="${type != ''}"><html:option value="${type}">${type}</html:option></c:if>
                                            </c:forEach>
                                        </html:select>
                                    </div>

                                    <div class="dictionary-fields">
                                        <span>Регистрационный номер&nbsp;</span>
                                        <html:text property="filter(registrationNumber)" size="12"/>
                                    </div>
                                </tiles:put>
                                <tiles:put name="buttonKey" value="button.filter"/>
                                <tiles:put name="buttonBundle" value="dictionaryBundle"/>
                            </tiles:insert>
                            <div class="clear"></div>
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" model="simple-pagination" property="data">
                                        <sl:collectionParam id="selectType" value="radio"/>
                                        <sl:collectionParam id="selectName" value="selectedIds"/>
                                        <sl:collectionParam id="selectProperty" value="synchKey"/>

                                        <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                        <sl:collectionParam id="onRowDblClick" value="sendSecurityData();"/>
                                        <sl:collectionItem title="Внутренний код" property="insideCode" hidden="true"/>
                                        <sl:collectionItem title="Наименование" property="name" hidden="true"/>
                                        <sl:collectionItem title="Эмитент" property="issuer"/>
                                        <sl:collectionItem title="Тип" property="type"/>
                                        <sl:collectionItem title="Регистрационный номер" property="registrationNumber"/>
                                        <sl:collectionItem title="Номинал" property="nominal.decimal"/>
                                        <sl:collectionItem title="Валюта" property="nominal.currency.code"/>
                                    </sl:collection>
                                </tiles:put>

                                <tiles:put name="isEmpty" value="${empty form.data}"/>
                                <tiles:put name="emptyMessage">
                                    <h3>В справочнике нет ценных бумаг, соответствующих заданным параметрам поиска. Попробуйте ввести другое значение.</h3>
                                </tiles:put>
                            </tiles:insert>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                   <tiles:put name="commandTextKey" value="button.cancel"/>
                                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                                   <tiles:put name="onclick"        value="window.close();"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                </tiles:insert>
                                <c:if test="${not empty form.data}">
                                    <tiles:insert definition="clientButton" flush="false">
                                       <tiles:put name="commandTextKey" value="button.choose"/>
                                       <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                       <tiles:put name="bundle"         value="dictionaryBundle"/>
                                       <tiles:put name="onclick"        value="sendSecurityData();"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                    </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
