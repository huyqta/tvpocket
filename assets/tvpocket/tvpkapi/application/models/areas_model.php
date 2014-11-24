<?php
class areas_model extends CI_Model{
	public function __construct(){
		$this->load->database();
	}

	public function GetAllAreas(){
		$query = $this->db->get('areas');
		return $query->result_array();
	}

	public function GetAreaByID($data){
		$query = $this->db->get_where('areas', array('ID'=>$data['ID']));
		return $query->result_array();
	}

	public function InsertArea($data){
		return $this->db->insert('areas', $data);
	}
	
	public function UpdateArea($data){
		return $this->db->update('areas', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteArea($id){
        return $this->db->delete('areas', array('ID'=>$id));
    }
}