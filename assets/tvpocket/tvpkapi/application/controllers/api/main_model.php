<?php
class Main_model extends CI_Model
{

    function __construct()
    {
        parent::__construct();
        $this->load->database();
    }


    function get($table, $fields, $where = '', $perpage = 0, $start = 0, $one = false,
        $array = 'array')
    {

        $this->db->select($fields);
        $this->db->from($table);
        $this->db->limit($perpage, $start);
        if ($where) {
            $this->db->where($where);
        }

        $query = $this->db->get();

        $result = !$one ? $query->result($array) : $query->row();
        return $result;
    }

    function getsimple($table, $fields, $where = '')
    {
        $this->db->select($fields);
        $this->db->from($table);
        if ($where) {
            $this->db->where($where);
        }

        $query = $this->db->get();

        $result = $query->result_array();
        return $result;
    }

    function getall($table, $fields)
    {

        $this->db->select($fields);
        $this->db->from($table);

        $query = $this->db->get();

        $result = $query->result_array();
        return $result;
    }

    function add($table, $data)
    {
        $this->db->insert($table, $data);
        if ($this->db->affected_rows() == '1') {
            return $this->db->insert_id();
        }

        return -1;
    }

    function edit($table, $data, $fieldID, $ID)
    {
        $this->db->where($fieldID, $ID);
        $this->db->update($table, $data);

        if ($this->db->affected_rows() >= 0) {
            return true;
        }

        return false;
    }

    function delete($table, $fieldID, $ID)
    {
        $this->db->where($fieldID, $ID);
        $this->db->delete($table);
        if ($this->db->affected_rows() == '1') {
            return true;
        }

        return false;
    }

    function count($table, $field, $value)
    {
        if (!$field && !$value) {
            return $this->db->count_all($table);
        } else {
            $query = $this->db->getwhere($table, array($field => $value));
            $count = $query->num_rows();
        }
    }
}
