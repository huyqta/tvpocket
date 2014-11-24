<?php
class cities_model extends CI_Model{
	public function __construct(){
		$this->load->database();
	}

	public function GetAllCities(){
		$query = $this->db->get('cities');
		return $query->result_array();
	}

	public function GetCityByArea($data){
		$query = $this->db->get_where('cities', array('RefArea'=>$data['RefArea']));
		return $query->result_array();
	}

	public function InsertCity($data){
		return $this->db->insert('cities', $data);
	}
	
	public function UpdateCity($data){
		return $this->db->update('cities', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteCity($id){
        return $this->db->delete('cities', array('ID'=>$id));
    }
}