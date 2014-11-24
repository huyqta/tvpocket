<?php if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class NotificationType
{
    const Topic = 0; // Topic + comment
    const Request = 1; // Request + accepts
    const Message = 2; // Inbox + chat
}

class TopicType
{
    const Standard = 0; // Topic + comment
    const Request = 1; // Request + accepts
}

class NotificationMessage
{
    const Topic = "You have tagged on new topic."; // Topic + comment
    const Request = "You have new request."; // Request + accepts
    const Message = "You have new message."; // Inbox + chat
}

class ItemStatus
{
    const OnPrivate = 0; // Just added, not set
    const OnPublic = 1; // Request + accepts
    const OnDealing = 2; // Inbox + chat
}
