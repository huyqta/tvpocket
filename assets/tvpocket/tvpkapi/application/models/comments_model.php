<?php
class comments_model extends CI_Model{
	public function __construct(){
		$this->load->database();
	}

	public function GetAllComments(){
		$query = $this->db->get('comments');
		return $query->result_array();
	}

	public function GetCommentByAccount($datas){
		$query = $this->db->get_where('comments', array('RefAccount'=>$data['RefAccount']));
		return $query->result_array();
	}
    
    public function GetCommentByTopic($datas){
		$query = $this->db->get_where('comments', array('RefTopic'=>$data['RefTopic']));
		return $query->result_array();
	}

	public function InsertComment($data){
		return $this->db->insert('comments', $data);
	}
	
	public function UpdateComment($data){
		return $this->db->update('comments', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteComment($id){
        return $this->db->delete('comments', array('ID'=>$id));
    }
}