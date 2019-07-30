var stompClient = null;
//连接 websocket 地址
var connect_sw_url = "/connect_sw_url";
//订阅 接收 主地址
var main_receive_url = "/main_receive_url";
//订阅 接收 子地址1
var receive_url1 = "/receive_url1";
//发送 主地址
var main_send_url = "/main_send_url";
//发送 子地址1
var send_url1 = "/send_url1";

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS(connect_sw_url);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe(main_receive_url + receive_url1, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe(main_receive_url + receive_url1, function (greeting) {
            showGreeting("test2");
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send(main_send_url + send_url1, {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});

