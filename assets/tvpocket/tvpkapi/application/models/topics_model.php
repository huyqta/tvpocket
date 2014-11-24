<?php
class topics_model extends CI_Model{
	public function __construct(){
		$this->load->database();
	}

	public function GetAllTopics(){
		$query = $this->db->get('topics');
		return $query->result_array();
	}

	public function GetTopicByAccount($datas){
		$query = $this->db->get_where('topics', array('RefAccount'=>$data['RefAccount']));
		return $query->result_array();
	}

	public function InsertTopic($data){
		return $this->db->insert('topics', $data);
	}
	
	public function UpdateTopic($data){
		return $this->db->update('topics', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteTopic($id){
        return $this->db->delete('topics', array('ID'=>$id));
    }
}