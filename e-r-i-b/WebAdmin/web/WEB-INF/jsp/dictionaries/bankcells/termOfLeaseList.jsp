<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/bankcells/terms">
	<tiles:insert definition="bankcellsMain">
		<tiles:put name="submenu" type="string" value="TermsOfLease"/>
		<tiles:put name="pageTitle" type="string" value="Сроки аренды"/>

		<tiles:put name="menu" type="string">
		</tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="bankcellsTerms"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle" value="bankcellsBundle"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection model="list"
                    property="data"
                    id="terms"
                    bundle="bankcellsBundle"
                    emptyKey="label.terms.empty"
                    selectName="selectedIds"
                    selectProperty="id">

                    <sl:collectionItem title="label.termOfLease" property="description" width="80%"/>
                    <sl:collectionItem title="label.sortOrder" property="sortOrder" width="20%"/>
                 </sl:collection>
                <tiles:put name="emptyMessage" value="Не найдено ни одного срока аренды!"/>
             </tiles:put>
         </tiles:insert>

		<tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="certList"/>
            <tiles:put name="text" value="Добавление нового срока аренды"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
				    <tiles:put name="commandKey" value="button.add"/>
					<tiles:put name="commandHelpKey" value="button.add.help"/>
					<tiles:put name="bundle" value="bankcellsBundle"/>
				</tiles:insert>
            </tiles:put>
            <tiles:put name="data">
				<tr>
					<td align="right">
						&nbsp;Описание&nbsp;
					</td>
					<td align="center">
						&nbsp;
						<html:text property="newTermOfLeaseDescription" size="60"
						           maxlength="120" styleClass="contactInput"/>
						&nbsp;
					</td>
                </tr>
				<tr>
					 <td align="right">
						&nbsp;Порядок сортировки&nbsp;
					</td>
					<td align="center">
						&nbsp;
						<html:text property="newTermOfLeaseSortOrder" size="60"
						           maxlength="120" styleClass="contactInput"/>
						&nbsp;
					</td>
				</tr>
			</tiles:put>            
        </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
