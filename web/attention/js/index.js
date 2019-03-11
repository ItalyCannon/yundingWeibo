function load_home1() {
    css.href = "/home/index.html"
}

function load_home2() {
    window.location.href = "/collection/index.html";
}

function load_home3() {
    window.location.href = "/praise/index.html";
}

function load_home4() {
    window.location.href = "/commentReceive/index.html";
}


function time() {
    $.ajax({
        url: '/ShowAttentionServlet?type=1',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var element = document.getElementById("noApplicationRecord");
            if (element.innerHTML != null) {
                element.innerHTML = "";
            }
            show(text);
        }
    })
}

function initial() {
    $.ajax({
        url: '/ShowAttentionServlet?type=2',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var element = document.getElementById("noApplicationRecord");
            if (element.innerHTML != null) {
                element.innerHTML = "";
            }
            show(text);
        }
    })
}

function fansNum() {
    $.ajax({
        url: '/ShowFansNumServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var fansNum = document.getElementById("fansNum");
            fansNum.innerHTML = "粉丝 " + text;
        }
    })
}

function show(text) {
    var data = eval(text);
    var html = '';
    var noGroupNum = 0;

    var groups = [];
    var group = 0;
    for (var i = 0; i < data.length; i++) {
        html +=
            '<div class="part_1">'
            + '<img src="'
            + './img/portrait_1.jpg'
            + '" alt="img" class="portrait">'
            + '<div class="message">'
            + '<p class="name">'
            + data[i].nickname
            + '</p>'
            + '<p class="signature">'
            + data[i].signature
            + '</p>'
            + '<select name="" class="select">'
            + '<option value="">'
            + data[i].attentionGroup
            + '</option>'
            + '</select>'
            + '<p class="attention">已关注</p>'
            + '</div>'
            + '</div>';
        noApplicationRecord.innerHTML = html;
        if (data[i].attentionGroup === "未分组") {
            noGroupNum++;
        } else {
            for (var j = 0; j < group; ++j) {
                if (groups[j] != null || groups[j].oneOfGroup === data[i].attentionGroup) {
                    groups[j].count++;
                    break;
                }
            }
            var OneGroup = {oneOfGroup: data[i].attentionGroup, count: 1};
            groups.push(OneGroup);
        }
    }

    document.getElementById("attentionNum").innerHTML = "关注 " + data.length;
    document.getElementById("totalAttentionNum").innerHTML = "全部关注 " + data.length;
    document.getElementById("noGroup").innerHTML = "■ 未分组 " + noGroupNum;

    for (var a = 0; a < groups.length; ++a) {
        var element = '<li>' + '● ' + groups[a].oneOfGroup + ' ' + groups[a].count + '</li>';
        $('#groups').append(element);
    }
}

function userInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            $("#name").html(data.nickname);
            $("#signature").html(data.signature);
            $("#profile").attr('src', data.profilePicture);
        }
    })
}

window.onload = function () {
    initial();
    fansNum();
    userInfo();
};

$(document).ready(function () {
    $("#showType").change(function () {
        var selected = $(this).children('option:selected').val();
        if (selected === "initial") {
            initial();
        } else if (selected === "time") {
            time();
        }
    })
});
