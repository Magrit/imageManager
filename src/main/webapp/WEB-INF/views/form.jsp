<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add your photo</title>
    <meta charset="utf-8"/>
    <link href="../../css/styles.css" rel="stylesheet">
</head>
<body>
<div class="center">
    <div class="box">
        <h2>Image Manager</h2>
        <form action="/upload" method="post" enctype="multipart/form-data">
            <label>
                Nazwa:
                <input type="text" id="name" name="name">
            </label><br/>
            <label>
                <input class="btn" type="file" id="image" name="image" accept="image/*">
            </label><br/>
            <input class="btn" type="submit">
        </form>
    </div>
</div>

</body>
</html>
