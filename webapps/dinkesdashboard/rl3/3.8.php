<?php
 include '../conf/conf.php';
   /* header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRekapKunjuangnJenisBayar.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";*/
?>
<html>
    <body>
	<div class="panel-heading">
		<h4>Laporan RL 3.8</h4></div>
			<div class="panel-body">
    <?php
	switch($_GET[act]){
	default:
    echo"<form action='?module=3.8&act=tampil' method='post'>
	<table>
	<tr>
	<td>Periode</td><td>:</td><td><input type='text' id='calender1' name='tanggal1' >-S/D-<input type='text' id='calender' name='tanggal2' ></td>
	<td align='right'><input type='submit' value='tampilkan' ></td>
	</tr>
	</table>
	</form>";
	break;
	
	case "tampil":
    reportsqlinjection();      
		date_default_timezone_set("Asia/Jakarta");
		$datatime=date("Y-m-d H:i:s");
		$date1      = $_POST['tanggal1']; 
        $date2      = $_POST['tanggal2'];
		$tanggal1=date("Y-m-d",strtotime($date1));
		$tanggal2=date("Y-m-d",strtotime($date2));
		$datatime=date("$tanggal2 H:i:s");
		$waktu=date("H:i:s");
		echo"
		<input type='button' value='Back' onclick=self.history.go(-1)>
		<h3>Periode: $tanggal1 - $tanggal2</h3>
		<table class='table table-bordered table-hover table-striped' data-toggle='table'>
		<thead>
		<tr>
		<td>Kode</td>
		<td>Tanggal</td>
		<td>Nama Perawatan</td>
		<td>Jumlah Pasien</td>";
        $_sql = "SELECT setting.kode_ppk, jns_perawatan_lab.nm_perawatan, Count(periksa_lab.kd_jenis_prw) AS jumlah
				 FROM periksa_lab , jns_perawatan_lab , setting
				 WHERE periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw AND periksa_lab.tgl_periksa BETWEEN '$tanggal1' AND '$tanggal2'
				 GROUP BY periksa_lab.kd_jenis_prw";            
        $hasil=bukaquery($_sql);
                 while($baris = mysql_fetch_array($hasil)) { 
				 $ppk=$baris['kode_ppk'];
				 $jenis=$baris['nm_perawatan'];
				 $jml=$baris['jumlah'];
				//simpan data untuk kirim ke dinkes
				$myvars="";
				$post[] = array(
	
					'koders'=>$baris['kode_ppk'],
					'tanggal'=>$tanggal2,
					'jenispemeriksaan'=>$baris['nm_perawatan'],
					'jumlahpasien'=>$baris['jumlah'],
					);
	    echo"
		</tr>
		</thead>
		<tr>
		<td>$ppk</td>
		<td>$tanggal2</td>
		<td>$jenis</td>
		<td>$jml</td>
		</tr>";
                }  
		echo"
		<table>";
		//keep data dengan JSON
		$myvars=json_encode($post);
		?>
		<form action='?module=3.8&act=kirim' method='post'>
		<input type='hidden' value='<?php echo $myvars; ?>' name='kirim'>
		<button id="submit" name="submit" type="submit">Send</button>
		</form>
		<?php
		break ; 
 
 case "kirim":
		$myvars=$_POST['kirim'];
		// URL untuk mengirimkan data ke server dinas kesehatan
		$url = 'http://eis.dinkes.jakarta.go.id/api/curl/ws-rl38.php';
		require_once('../conf/curl-send.php');	
		echo "<script>alert('Data Insya Allah Sudah Di Kirim Juragan :)'); window.location = 'javascript:history.go(-1)'</script>";
 break;
}
?>
    </body>
</html>
