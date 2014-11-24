<?php
class accounts_model extends CI_Model{
	
	//var $username = '';
//	var $password = '';
//	var $active = '';
	
	public function __construct(){
		$this->load->database();
	}

	public function GetAllAccounts(){
		$query = $this->db->get('accounts');
		return $query->result_array();
	}

	public function GetAccountByUsername($data){
		$query = $this->db->get_where('accounts', array('Username'=>$data['Username'], 'Password'=>$data['Password']));
		return $query->result_array();
	}

	public function InsertAccount($data){
		return $this->db->insert('accounts', $data);
	}
	
	public function UpdateAccount($data){
		return $this->db->update('accounts', $data, array('ID'=>$data['ID']));
	}
    
    public function DeleteAccount($id){
        return $this->db->delete('accounts', array('ID'=>$id));
    }
}