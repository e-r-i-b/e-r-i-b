<?xml version="1.0" encoding="windows-1251"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="windows-1251"/>

    <xsl:param name="departmentId" select="'departmentId'"/>
	<xsl:variable name="getDepositList_a" select="document(concat('getDepositList_a.xml?departmentId=',$departmentId))"/>
	<xsl:key name="deposits" match="deposit" use="accountTypeId"/>
	<xsl:variable name="usedDepositsList" select="."/>
	<xsl:key name="usedDeposits" match="/product/data/element" use="value[@field='accountTypeId']/text()"/>

	<xsl:template match="/">
		<table cellspacing="2" cellpadding="1" style="margin-bottom:2px" class="fieldBorder">
			<tr>
				<td class="Width120 Label">
					Имя депозитного продукта
				</td>
				<td>
					<input type="text" name="name" value="{product/@name}"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 Label">
					Описание депозитного продукта
				</td>
				<td>
					<textarea id="productDescription" name="productDescription" rows="2" cols="81">
						<xsl:value-of select="product/@productDescription"/>
					</textarea>
				</td>
			</tr>
            <tr>
				<td class="Width120 Label">
					Подразделение
				</td>
				<td>
					<input type="text" name="departmentName" disabled="true" length="100" value="{product/@departmentName}"/>
                    <input type="hidden" name="departmentId" value="{$departmentId}"/>
				</td>
			</tr>
		</table>
		<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
			<tr class="titleTable">
				<td>Идентификатор</td>
				<td>Название</td>
				<td>Валюта(ы)</td>
			</tr>
			<xsl:apply-templates select="$getDepositList_a" mode="list"/>
		</table>
	</xsl:template>

	<xsl:template match="getDepositList_a" mode="list">
		<xsl:for-each select=".//deposit[generate-id(.)=generate-id(key('deposits',string(accountTypeId)))]">
			<xsl:sort select="accountTypeId"/>
			<tr class="listLine{position() mod 2}">
				<xsl:variable name="accountTypeId" select="string(accountTypeId)"/>
				<td class="listItem">
					<input id="dep_chk_{$accountTypeId}" name="selectedIds" type="checkbox"
					       value="{$accountTypeId}">
						<xsl:apply-templates
								select="$usedDepositsList/product/data/element/value[@field='accountTypeId' and text()=$accountTypeId]"
								mode="checked_attribute">
							<xsl:with-param name="id" select="$accountTypeId"/>
						</xsl:apply-templates>
					</input>
					<label for="dep_chk_{$accountTypeId}">
						<xsl:value-of select="accountTypeId"/>
					</label>
				</td>
				<td class="listItem">
					<xsl:value-of select="accountTypeName"/>
				</td>
				<td class="listItem">
					<xsl:for-each select="key('deposits', $accountTypeId)">
						<li>
							<xsl:value-of select="currency"/>
						</li>
					</xsl:for-each>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="//value" mode="checked_attribute">
		<xsl:param name="id"/>
		<xsl:if test="key('usedDeposits', $id)">
			<xsl:attribute name="checked"/>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>