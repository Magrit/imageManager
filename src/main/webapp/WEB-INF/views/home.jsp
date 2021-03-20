<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image Manager</title>
    <meta charset="utf-8"/>
    <link href="../../css/styles.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>

<c:forEach var="image" items="${imagesList}">
    <div>
        <div class="container">
            <div class="card">
                <div class="close">
                    <a class="close-x" href="/delete/${image.id}"> x </a>
                </div>
                <img class="miniature" src="<c:url value="${image.minPath}" />">
                <h2>${image.name}</h2>
            </div>
        </div>
    </div>

    <div class="lightBox">
        <span class="close close-x">x</span>
        <div class="center">
            <img class="lightBox-content" src="<c:url value="${image.path}"/>">
        </div>
    </div>
</c:forEach>

<%@include file="footer.jsp" %>
<script src="../js/lightbox.js"></script>
</body>
</html>
