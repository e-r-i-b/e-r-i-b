<%--
  Created by IntelliJ IDEA.
  User: Kosyakov
  Date: 20.09.2006
  Time: 13:08:54
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "SynchronizeDictionaries");%>
<html:form action="/dictionaries/synchronize" enctype="multipart/form-data">
	<tiles:insert definition="dictionariesMain">
		<tiles:put name="submenu" type="string" value="Synchronize"/>
        <tiles:put name="pageTitle" type="string">
            Загрузка справочников
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.loadDictionaries"/>
                        <tiles:put name="commandHelpKey" value="button.loadDictionaries.help"/>
                        <tiles:put name="bundle"  value="employeesBundle"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="settingsBeforeInf">
                    <p>Выберите файл справочников для загрузки:</p>
                    <div><html:file property="content" size="100"/></div>
                    <p>
                        <html:radio property="fileType" value="xml" styleId="xml"/> <label for="xml">xml-файл</label>
                        <html:radio property="fileType" value="csv" styleId="csv"/><label for="csv">csv-файл</label>
                    </p>
                 </tiles:put>
                 <tiles:put name="data">
                    <logic:iterate id="descriptor" name="SynchronizeDictionariesForm" property="descriptors">
                        <c:if test="${phiz:impliesServiceRigid(descriptor.name) and (descriptor.xmlSource or descriptor.csvSource or
                                      phiz:isInstance(descriptor.source, 'com.rssl.phizic.business.dictionaries.DepositProductReplicaSource'))}">
                            <tr>
                                <td>
                                    <html:multibox property="selected" value="${descriptor.name}"/>
                                    <bean:write name="descriptor" property="description"/>
                                </td>
                            </tr>
                         </c:if>
                    </logic:iterate>
               </tiles:put>
            </tiles:insert>

            <c:if test="${phiz:impliesServiceRigid('LoadAddressDictionariesService')}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text" value="Загрузка справочников для заявки на кредит"/>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.loadLoanClaimDict"/>
                            <tiles:put name="commandHelpKey" value="button.loadLoanClaimDict.help"/>
                            <tiles:put name="bundle"  value="employeesBundle"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="settingsBeforeInf">
                        <p>Выберите файл справочников (в формате .csv) для загрузки:</p>
                        <div><html:file property="loanClaimDictFile" size="100"/></div>
                    </tiles:put>
                    <tiles:put name="data">
                        <tr>
                            <td>
                                <input type="checkbox" name="lcDictSelected" value="Area"/> Справочник "Районы"
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" name="lcDictSelected" value="City"/> Справочник "Города"
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" name="lcDictSelected" value="Settlement"/> Справочник "Населенные пункты"
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" name="lcDictSelected" value="Street"/> Справочник "Улицы"
                            </td>
                        </tr>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:impliesServiceRigid('SBNKDDictionaries')}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text" value="Загрузка справочников для заявки за дебетовую карту (СБНКД)"/>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.loadSBNKDDict"/>
                            <tiles:put name="commandHelpKey" value="button.loadSBNKDDict.help"/>
                            <tiles:put name="bundle"  value="employeesBundle"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="settingsBeforeInf">
                        <p>Выберите файл справочника (в формате .xml) для загрузки:</p>
                        <div><html:file property="sbnkdDictionaryFile" size="100"/></div>
                    </tiles:put>
                    <tiles:put name="data">
                        <td>
                            <input type="checkbox" name="sbnkdDictionarySelected" value="CellOperators"/> Справочник "Операторы сотовой связи"
                        </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="checkbox" name="sbnkdDictionarySelected" value="NumberArrays"/> Справочник "Номерная емкость"
                            </td>
                        </tr>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>
	</tiles:insert>
</html:form>
