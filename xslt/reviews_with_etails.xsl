<?xml version="1.0" encoding="UTF-8"?>
<!-- Root element of a stylesheet: Define namespaces, such as xls. Define 
	version (2.0 is current). Optionally exclude namespaces. -->
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	exclude-result-prefixes="xs" version="2.0">
	<!-- Output format, for example xml, html, or text. -->
	<xsl:output method="html" indent="yes"></xsl:output>
	<!-- Template to apply when "match" pattern is encountered. The "match" 
		pattern is an XPath expression. A single stylesheet can contain multiple 
		templates. Within a template, you can apply the other templates to the current 
		element (and its child nodes) with: <xsl:apply-templates select="sub-template-pattern"></xsl:apply-templates>. -->
	<xsl:template match="/ReviewApplication">
		<!-- Within a template, you can use a mix of HTML and XSL. The "select" 
			patterns are XPath expressions. -->
		<html>
			<head>
				<title>HW 5.10</title>
			</head>
			<body>
				<h1>Reviews with Details</h1>
				<table border="1">
					<tr>
						<th>RestaurantName</th>
						<th>FirstName</th>
						<th>Rating</th>
					</tr>
					<xsl:for-each select="/ReviewApplication/Reviews/Review">
						<xsl:variable name="restaurantid" select="RestaurantId"></xsl:variable>
						<xsl:variable name="username" select="UserName"></xsl:variable>
						<xsl:variable name="rating" select="Rating"></xsl:variable>
						<xsl:variable name="restaurant"
							select="/ReviewApplication/Companys/Company/Restaurants/Restaurant[RestaurantId=$restaurantid]"></xsl:variable>
						<xsl:variable name="user"
							select="/ReviewApplication/Users/User[UserName=$username]"></xsl:variable>
						<tr>
							<td>
								<xsl:value-of select="$restaurant/Name/text()"></xsl:value-of>
							</td>
							<td>
								<xsl:value-of select="$user/FirstName/text()"></xsl:value-of>
							</td>
							<td>
								<xsl:value-of select="$rating/text()"></xsl:value-of>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>