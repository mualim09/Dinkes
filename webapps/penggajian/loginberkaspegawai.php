<?php
	include_once "conf/command.php";
  	if ($_GET['act']=="login"){
            if(($_GET['usere']=="admin")&&($_GET['passwordte']=="akusayangsamakamu")) {
                session_start();
                $_SESSION['ses_admin']="admin";
                $url = "index.php?act=DetailBerkasPegawai&action=TAMBAH&nik=".$_GET['nik']."&kategori=".$_GET['kategori']."";			
            }else{
                session_start();
                session_destroy();
                if (cekSessiAdmin()){
                    session_unregister("ses_admin");
                }
                if (cekSessiPegawai()){
                    session_unregister("ses_pegawai");
                }
                $url = "index.php?act=Kontak";
            }
	    header("Location:".$url);
            
	}
    
?>
