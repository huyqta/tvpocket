<?php if (!defined('BASEPATH'))
    exit('No direct script access allowed');

if (!function_exists('GetCurrentDate')) {
    function GetCurrentDate()
    {
        return date('Y-m-d H:i:s');
    }
}

if (!function_exists('DateTimeAdd')) {
    function DateTimeAddDay($date, $value)
    {
        return date('Y-m-d H:i:s', strtotime($date . ' + ' . $value . ' days'));
    }
}

if (!function_exists('DateTimeAddMonth')) {
    function DateTimeAddMonth($date, $value)
    {
        return date('Y-m-d H:i:s', strtotime($date . ' + ' . $value . ' months'));
    }
}

if (!function_exists('DateTimeAddYear')) {
    function DateTimeAddYear($date, $value)
    {
        return date('Y-m-d H:i:s', strtotime($date . ' + ' . $value . ' year'));
    }
}
?>