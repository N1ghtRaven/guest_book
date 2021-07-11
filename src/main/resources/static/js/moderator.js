function moderateMessageCreate(json_message) {
    // Hack
    if ($('#' + json_message.id).length > 0) {
        return;
    }

    // Create message container
    jQuery('<div/>', {
        id: json_message.id,
        class: 'd-flex text-muted pt-3 border-bottom',
    }).appendTo('#message-container');
    
    // Image box
    var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    svg.setAttribute('class', 'bd-placeholder-img flex-shrink-0 mr-2 rounded');
    svg.setAttribute('width', '32');
    svg.setAttribute('height', '32');
    svg.setAttribute('focusable', 'false');
    $(svg).appendTo('#' + json_message.id);

    var title = document.createElementNS('http://www.w3.org/2000/svg', 'title');
    title.innerHTML = colorToTitle(json_message.color);
    $(title).appendTo(svg);

    var rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
    rect.setAttribute('width', '100%');
    rect.setAttribute('height', '100%');
    rect.setAttribute('fill', json_message.color.toUpperCase());
    $(rect).appendTo(svg);

    // Create message box
    jQuery('<p/>', {
        id: 'message-box_' + json_message.id,
        class: 'message-box pb-3 mb-0 small lh-sm',
    }).appendTo('#' + json_message.id);

    jQuery('<strong/>', {
        class: 'd-block text-gray-dark',
        text: json_message.username
    }).appendTo('#message-box_' + json_message.id);
    $('#message-box_' + json_message.id).html($('#message-box_' + json_message.id).html() + json_message.message);

    // Create time container
    jQuery('<span/>', {
        class: 'message-time pb-3 mb-0 small lh-sm',
        text: timeConverter(json_message.timestamp)
    }).appendTo('#' + json_message.id);

    // Create button container
    var button_container = document.createElement('div');
    button_container.setAttribute('class', 'approve-buttons')
    $(button_container).appendTo('#' + json_message.id);

    // Create Approve button
    var approve_button = document.createElement('button');
    approve_button.setAttribute('type', 'button')
    approve_button.setAttribute('class', 'btn btn-success')
    $(approve_button).click(function() { approve(json_message.id); });
    $(approve_button).appendTo(button_container);

    jQuery('<i/>', {
        class: 'fas fa-check',
    }).appendTo(approve_button);

    // Create Decline button
    var decline_button = document.createElement('button');
    decline_button.setAttribute('type', 'button')
    decline_button.setAttribute('class', 'btn btn-danger')
    $(decline_button).click(function() { decline(json_message.id); });
    $(decline_button).appendTo(button_container);

    jQuery('<i/>', {
        class: 'fas fa-times',
    }).appendTo(decline_button);
}

var stompClient = null;
function ws_moderate_connect() {
    var socket = new SockJS('/secure/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function() {        
        // Send interval
        stompClient.send("/app/new_message", {}, 0);
        stompClient.subscribe('/topic/moderate_message', function(messages) {
            var json = JSON.parse(JSON.parse(messages.body));
            for (var i = 0; i < json.length; i++)
            {
                moderateMessageCreate(json[i]);
            }
        });
    });
}

function approve(id) {
    stompClient.send("/app/approve_message", {}, id);
    $('#' + id).remove();
}

function decline(id) {
    stompClient.send("/app/decline_message", {}, id);
    $('#' + id).remove();
}

function moderate_more() {
    stompClient.send("/app/new_message", {}, $("#message-container").children().length);
}
