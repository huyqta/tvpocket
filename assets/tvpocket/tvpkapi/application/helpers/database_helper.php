<?php if (!defined('BASEPATH'))
    exit('No direct script access allowed');

if (!function_exists('TestHelper')) {
    function TestHelper()
    {
        echo 'Helper here!';
    }
}

if (!function_exists('ValidateNullField')) {
    function ValidateNullField($fields)
    {
        foreach ($fields as $field) {
            print_r($field);
            if ($this->input->post($field) == null) {
                return false;
                break;
            }
        }
        return true;
    }
}

if (!function_exists('GenerateToken')) {
    function GenerateToken($inputStr)
    {
        return hash('SHA1', $inputStr);
    }
}

if (!function_exists('CheckTokenForWSAPI')) {
    function CheckTokenForWSAPI($account, $token)
    {
        $ci = &get_instance();
        
        $query = $ci->db->query("SELECT * FROM tokens WHERE Token = '" . $token . "'");
        $result = $query->result_array();
        
        if (is_array($result)) {
            $result = $result[0];
            return $result;
        } else
            return false;
    }
}

if (!function_exists('DBQuery')) {
    function DBQuery($query)
    {
        $ci = &get_instance();
        $query = $ci->db->query($query);
        return $query->result_array();
    }
}

if (!function_exists('WhereConditionParse')) {
    function WhereConditionParse($fields, $logical)
    {
        $where = "";

        foreach ($fields as $key => $value) {
            if (HasField($value)) {
                if ($where != '')
                    $where .= ' ' . $logical . ' ';
                $where .= $key . ' = \'' . $value . '\'';
                //$where = array_push($fields);
            }
        }
        return $where;
    }
}
