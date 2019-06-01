<%@ page import="jsp.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <style>
        .url-name {
            background-color: cornflowerblue;
            border-right: solid 1px black;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

</head>
<body>
<%! User user; %>
<% user = (User) session.getAttribute("user");
    if (user != null) {
        System.out.println("Welcome " + user.getUsername());
%>
<br/>
<p>
    <button id="getURLsbtn" type="button">Get URLs</button>
    <button id="get-top-N-btn" type="button">Get top N URLs</button>
    <input type="text" id="number">
</p>
<section>
    <table id="url-table"></table>
</section>
<p style="height: 50px;"></p>
<section id="insert-section">
    <span>URL fields</span><br/>
    <table>
        <tr>
            <td>ID url:</td>
            <td><input type="text" id="url-id"></td>
        </tr>
        <tr>
            <td>URL userid:</td>
            <td><input type="text" id="url-userid"></td>
        </tr>
        <tr>
            <td>link:</td>
            <td><input type="text" id="url-link"></td>
        </tr>
        <tr>
            <td>accessed:</td>
            <td><input type="text" id="url-accessed"></td>
        </tr>

        <tr>
            <td>
                <button type="button" id="insert-url-btn">Insert URL</button>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>
                <button type="button" id="delete-url-btn">Delete URL</button>
            </td>
            <td></td>
        </tr>
    </table>
</section>
<section id="insert-result-section"></section>


<script>
    function getUserURLs(userid, callbackFunction) {
        $.getJSON(
            "URLController",
            {action: 'getAll', userid: userid},
            callbackFunction
        );
    }

    function insertURL(id, userid, link, accessed, callbackFunction) {
        $.get("URLController",
            {
                action: "insert",
                id: id,
                userid: userid,
                link: link,
                accessed: accessed
            },
            callbackFunction
        );
    }

    function deleteURL(id, callbackFunction) {
        $.get("URLController",
            {action: "delete", id: id},
            callbackFunction
        );
    }

    function getTopN(number, callbackFunction) {
        console.log(number);
        $.get("URLController",
            {action: "getTopN", number: number},
            callbackFunction
        );
    }

    $(document).ready(function () {
        $("#getURLsbtn").click(function () {
            getUserURLs(<%= user.getId() %>, function (urls) {
                console.log(urls);
                $("#url-table").html("");
                $("#url-table").append("<tr style='background-color: mediumseagreen'><td>Id</td><td>Userid</td><td>Link</td><td>Accessed</td></tr>");
                for (var name in urls) {
                    //console.log(urls[name].link);
                    $("#url-table").append("<tr><td class='url-name'>" + urls[name].id + "</td>" +
                        "<td>" + urls[name].userid + "</td>" +
                        "<td>" + urls[name].link + "</td>" +
                        "<td>" + urls[name].accessed + "</td></tr>");
                }
            })
        });

        $("#insert-url-btn").click(function () {
            insertURL($("#url-id").val(),
                $("#url-userid").val(),
                $("#url-link").val(),
                $("#url-accessed").val(),
                function (response) {
                    $("#insert-result-section").html(response);
                }
            )
        })

        $("#delete-url-btn").click(function () {
            deleteURL($("#url-id").val(),
                function (response) {
                    $("#insert-result-section").html(response);

                })
        })

        $("#get-top-N-btn").click(function () {
            getTopN($("#number").val(), function (urls) {
                console.log(urls);
                $("#url-table").html("");
                $("#url-table").append("<tr style='background-color: mediumseagreen'><td>Id</td><td>Userid</td><td>Link</td><td>Accessed</td></tr>");
                for (var name in urls) {
                    //console.log(urls[name].link);
                    $("#url-table").append("<tr><td class='url-name'>" + urls[name].id + "</td>" +
                        "<td>" + urls[name].userid + "</td>" +
                        "<td>" + urls[name].link + "</td>" +
                        "<td>" + urls[name].accessed + "</td></tr>");
                }
            })
        })
    });
</script>
<%
    }
%>

</body>
</html>