function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8080/greeting');
    ws = Stomp.over(socket);

    ws.connect({}, function(frame) {
        setConnected(true);
        ws.subscribe("/user/queue/errors", function(message) {
            console.log("Error " + message.body);
            showGreeting(JSON.parse(message.body).content);
        });

        ws.subscribe("/user/queue/reply", function(message) {
            console.log("Message " + message.body);
            showGreeting(JSON.parse(message.body).content);
        });
    }, function(error) {
        console.log("STOMP error " + error);
    });
}

function disconnect() {
    if (ws !== null) {
        ws.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    ws.send("/app/message", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
