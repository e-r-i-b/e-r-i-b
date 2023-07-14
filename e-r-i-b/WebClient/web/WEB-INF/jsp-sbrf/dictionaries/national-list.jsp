<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 30.05.2010
  Time: 13:53:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:form action="/private/dictionary/banks/national">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <html:hidden property="filters(ourBank)"/><%-- признак наш банк --%>
                <div id="reference">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title">
                            <div class="middleTitle">Справочник банков</div>
                        </tiles:put>
                        <tiles:put name="data">
                            <script type="text/javascript">

                                $(document).ready(function(){
                                    initReferenceSize();
                                });
                                
                                function sendBankData()
                                {
                                    var ids = document.getElementsByName("selectedIds");
                                    var synchKey = getRadioValue(ids);
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var r = ids.item(i).parentNode.parentNode;
                                            var a = new Array(3);
                                            a['BIC'] = trim(r.cells[1].innerHTML);
                                            a['name'] = trim(r.cells[2].innerHTML);
                                            if (r.cells[3].innerHTML.indexOf('&nbsp;') == -1)
                                                a['account'] = trim(r.cells[3].innerHTML);
                                            else
                                                a['account'] = '';

                                            a['our'] = '' + $('#bank_' + a['BIC']).val();
                                            window.opener.setBankInfo(a);
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("Выберите банк.");
                                }
                            </script>

                            <%-- Фильтр --%>
                            <div class="simpleFilter relative">
                                <tiles:insert definition="filter" flush="false">
                                    <tiles:put name="rightBtn" value="true"/>
                                    <tiles:put name="data">
                                        <div class="float">
                                            <span class="topItem">Наименование&nbsp;</span>
                                            <div class="clear"></div>
                                            <div class="cleaner">
                                                <html:text property="filter(title)" style="width:450px;" onkeydown="showCleaner(this);"/>
                                                <div class="cancelSearch" onclick="cleanField(this);"></div>
                                            </div>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="dictionary-fields">
                                            <span class="topItem">БИК&nbsp;</span>
                                            <div class="clear"></div>
                                            <div class="cleaner">
                                                <html:text property="filter(BIC)"  style="width:75px;" maxlength="9" onkeydown="showCleaner(this);"/>
                                                <div class="cancelSearch" onclick="cleanField(this);"></div>
                                            </div>
                                        </div>

                                        <div class="dictionary-fields">
                                            <span class="topItem">Город&nbsp;</span>
                                            <div class="clear"></div>
                                            <div class="cleaner">
                                                <html:text property="filter(city)" style="width:210px;" onkeydown="showCleaner(this);"/>
                                                <div class="cancelSearch" onclick="cleanField(this);"></div>
                                            </div>
                                        </div>
                                    </tiles:put>
                                    <tiles:put name="buttonKey" value="button.filter"/>
                                    <tiles:put name="buttonBundle" value="dictionaryBundle"/>
                                </tiles:insert>
                            </div>
                            <div class="clear"></div>
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" model="simple-pagination" property="data">
                                        <sl:collectionParam id="selectType" value="radio"/>
                                        <sl:collectionParam id="selectName" value="selectedIds"/>
                                        <sl:collectionParam id="selectProperty" value="synchKey"/>

                                        <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                        <sl:collectionParam id="onRowDblClick" value="sendBankData();"/>
                                        <input type="hidden" id="bank_${listElement.BIC}" value="${listElement.our}"/>

                                        <sl:collectionItem title="БИК" property="BIC"/>
                                        <sl:collectionItem title="Наименование" property="name"/>
                                        <sl:collectionItem title="Корр. счет" property="account"/>
                                    </sl:collection>
                                </tiles:put>

                                <tiles:put name="isEmpty" value="${empty form.data}"/>
                                <tiles:put name="emptyMessage">
                                    <h3>В справочнике нет банков, соответствующих заданным параметрам поиска. Попробуйте ввести другое значение.</h3>
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
                                       <tiles:put name="onclick"        value="sendBankData();"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            <script type="text/javascript">
                function cleanField(field)
                {
                  var fieldBox =  $(field).parents('.cleaner').find('input');
                    fieldBox.val('');
                  $(field).hide();
                }

                function showCleaner(field)
                {
                    $(field).parents('.cleaner').find('.cancelSearch').show();
                }

                doOnLoad(function () {
                    if ($("[name='filter(title)']").val())
                    {
                        showCleaner($("[name='filter(title)']")[0]);
                    }
                    if ($("[name='filter(BIC)']").val())
                    {
                        showCleaner($("[name='filter(BIC)']")[0]);
                    }
                    if ($("[name='filter(city)']").val())
                    {
                        showCleaner($("[name='filter(city)']")[0]);
                    }
                });

            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
