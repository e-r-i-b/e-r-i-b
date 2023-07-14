<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<html:form action="/log/archive" enctype="multipart/form-data">
	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="Archive"/>

		<tiles:put name="menu" type="string"/>

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
				<tiles:put name="id" value="archiveAudit"/>
				<tiles:put name="name" value="Архивация протоколов аудита"/>
				<tiles:put name="description" value=""/>
                <tiles:put name="buttons">
                     <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.archive"/>
                        <tiles:put name="commandHelpKey" value="button.archive.help"/>
                        <tiles:put name="bundle" value="logBundle"/>
                        <tiles:put name="autoRefresh" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.unarchive"/>
                        <tiles:put name="commandHelpKey" value="button.unarchive.help"/>
                        <tiles:put name="bundle" value="logBundle"/>
                    </tiles:insert>
                </tiles:put>
				<tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <span class="bold">Архивация</span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Вид журнала
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="field(protocolType)" styleClass="select">
                                <c:if test="${phiz:impliesOperation('ArchiveSystemLogOperation','DownloadLog')}">
                                    <html:option value="system">Журнал системных действий</html:option>
                                </c:if>
                                <c:if test="${phiz:impliesOperation('ArchiveOperationsLogOperation','DownloadLog')}">
                                    <html:option value="operations">Журнал действий пользователей</html:option>
                                </c:if>
                                <c:if test="${phiz:impliesOperation('ArchiveMessagesLogOperation','ArchiveMessagesLogService')}">
                                    <html:option value="messages">Журнал сообщений</html:option>
                                </c:if>
                                <c:if test="${phiz:impliesOperation('ArchiveEntriesLogOperation','ArchiveEntriesLogService')}">
                                    <html:option value="entries">Журнал регистрации входов</html:option>
                                </c:if>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <span class="bold">Разархивация</span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Архив
                        </tiles:put>
                        <tiles:put name="data">
                            <html:file property="archive" style="width:500px;" size="45"/>
                        </tiles:put>
                    </tiles:insert>

				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>

