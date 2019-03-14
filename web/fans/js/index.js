function load_home1() {
    css.href = "/home/index.html"
}

function time() {
    $.ajax({
        url: '/ShowFansServlet?type=1',
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
        url: '/ShowFansServlet?type=2',
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

var allAttention;

function attention() {
    $.ajax({
        url: '/ShowAttentionServlet?type=1',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allAttention = eval(text);
        },
        async: false
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


function attentionCondition(FansUserId) {
    for (var i = 0; i < allAttention.length; ++i) {
        if (allAttention[i].userId == FansUserId) {
            return '<p class="attention" onclick="removeAttention(' + FansUserId + ')" id="' + FansUserId + '">' + "互相关注" + '</p>';
        }
    }
    return '<p class="add" onclick="addAttention(' + FansUserId + ')" id="' + FansUserId + '">添加关注 +</p>';
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
            $("#" + userId).html("添加关注 +");
        }
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
            $("#" + userId).html("互相关注");
        }
    })
}

function show(text) {
    var data = eval(text);
    var html = '';
    var noGroupNum = 0;

    var groups = [];
    var group = 0;
    console.log(data);
    for (var i = 0; i < data.length; i++) {
        html +=
            '<div class="part_1">' +
            '<img src="' + './img/portrait_1.jpg' + '" alt="img" class="portrait">' +
            '<div class="message">' +
            '<p class="name">' + data[i].nickname + '</p>' +
            '<p class="signature">' + data[i].signature + '</p>' +
            attentionCondition(data[i].userId) +
            '</div>' +
            '</div>';
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
    // document.getElementById("totalAttentionNum").innerHTML = "全部关注 " + data.length;
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
    attention();
    initial();
    // fansNum();
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