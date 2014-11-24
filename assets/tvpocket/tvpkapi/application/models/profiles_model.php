<?php
class profiles_model extends CI_Model{
    function __construct(){
        $this->load->database();
    }
    
    function GetAllProfiles(){
        $query = $this->db->get('profiles');
        return $query->result_array();
    }
    
    function GetProfileByAccount($refAccount)
    {
        $query = $this->db->get_where('profiles', array('RefAccount'=>$refAccount));
        return $query->result_array();
    }
    
    public function InsertProfile($data){
		return $this->db->insert('profiles', $data);
	}
	
	public function UpdateProfile($data){
		return $this->db->update('profiles', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteProfile($id){
        return $this->db->delete('profiles', array('ID'=>$id));
    }
}