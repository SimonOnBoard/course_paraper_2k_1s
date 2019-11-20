<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <p><input id="query" oninput="f()"/></p>

    <form method="post" enctype="post" action="/mainSearch">
        <select id="categories" name="categories">
            <#list categories as category>
                <option value="${category}">${category}</option>
            </#list>
        </select>
        <p><input type="submit" value="Использовать фильтрацию по категориям"></p>
    </form>

    <div id="res"></div>


    <script type="application/javascript">
        function f() {
            if ($("#query").val().length >= 1) {
                var category = $("#categories").val();
                var query = $("#query").val();
                $.ajax({
                    url: "/mainSearch",
                    data: {
                        "query": query,
                        "categories": category
                    },
                    type: "post",
                    success: function (msg) {
                        var msg = JSON.parse(msg);
                        if (msg.posts.length > 0) {
                            $("#res").html("");
                            var html = "";
                            for (var i = 0; i < msg.posts.length; i++) {
                                var div_r = document.createElement("div");
                                div_r.setAttribute("id", "post_" + i);
                                $("#res").append(div_r);
                                $("#post_" + i).append("<img width=" + "\"200\"" + " src=" + "\"" + msg.posts[i].photoPath.toString() + "\"" + " />");
                                $("#post_" + i).append("<div>" + msg.posts[i].name + "</div>");
                                $("#post_" + i).append("<div>" + msg.posts[i].text + "</div>");
                                $("#post_" + i).append("<div>" + msg.posts[i].category+ "</div>");
                                var d = new Date(msg.posts[i].time);
                                $("#post_" + i).append("<div>" + d.toString() + "</div>");
                                if (msg.posts[i].showAuthor) {
                                    $("#post_" + i).append("<a href=" + "/home?id=" + msg.posts[i].auth_id + "> Нажмите, чтобы просмотреть профиль автора </a><br>");
                                }
                                $("#post_" + i).append("<a href=" + "/showPost?id=" + msg.posts[i].id + "> Нажмите, чтобы просмотреть текст записи </a>");

                            }
                        } else {
                            $("#res").html("No results..");
                        }
                    }
                });
            } else {
                $("#res").html("");
            }
        }
    </script>
</#macro>
<#macro title>
    <title>Filter</title>
</#macro>
<@display_page />