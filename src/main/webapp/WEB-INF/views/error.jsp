<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image Manager</title>
    <meta charset="utf-8"/>
    <link href="../../css/styles.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>

<p>${errorMsg}</p>

<%@include file="footer.jsp" %>
<script src="../js/lightbox.js"></script>
</body>
</html>
