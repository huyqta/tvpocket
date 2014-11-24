<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

if ( ! function_exists('HasField'))
{
    /*
    HasField($this->input->post("ID"))
    */
    function HasField($data) {
        if (is_numeric($data)) return true;
        return isset($data) && !empty($data) && !is_null($data);
    }
}

if ( ! function_exists('ValidateFields'))
{
    /*
    ValidateField(array(
        $this->input->post("ID"),
        $this->input->post("RefCategory"),
    ))
    */
    function ValidateFields($data) {
        foreach ($data as $item) {
            if (!HasField($item)) {
                return false;
            }
        }
        return true;
    }
}