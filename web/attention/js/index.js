function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}


var allAttention;

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
            allAttention = eval(text);
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
            + data[i].profilePicture
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
            + attentionCondition(data[i].userId)
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
            $("#profile1").attr('src', data.profilePicture);
            $("#name").html(data.nickname);
            $("#signature").html(data.signature);
            $("#profile").attr('src', data.profilePicture);
        }
    })
}

function removeAttention(userId) {
    $.ajax({
        url: '/RemoveAttentionServlet',
        type: 'get',
        // dataType: 'json',
        data: {
            userId: userId
        },
        success: function (text) {
            var $1 = $("#" + userId);
            $1.html("添加关注 +");
            $1.attr("onclick", "addAttention(" + userId + ")");
        },
        async: false
    })
}

function addAttention(userId) {
    $.ajax({
        url: '/AddAttentionServlet',
        type: 'get',
        // dataType: 'json',
        data: {
            userId: userId
        },
        success: function () {
            var $1 = $("#" + userId);
            $1.html("已关注");
            $1.attr("onclick", "removeAttention(" + userId + ")");
        },
        async: false
    })
}


function attentionCondition(userId) {
    for (var i = 0; i < allAttention.length; ++i) {
        if (allAttention[i].userId == userId) {
            return '<p class="attention" onclick="removeAttention(' + userId + ')" id="' + userId + '">' + "已关注" + '</p>';
        }
    }
    return '<p class="add" onclick="addAttention(' + userId + ')" id="' + userId + '">添加关注 +</p>';
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
