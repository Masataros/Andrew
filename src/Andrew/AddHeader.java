/*
 * プログラム演習の宿題で作ったCファイルに
 * わざわざヘッダ情報を書くのめんどくさいよね
 * だったら勝手に追加してくれるプログラムを作ろう
*/



package Andrew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AddHeader {
	public static void main(String[] args) {
		try {
		      File txt = new File("myname.txt");
		      BufferedReader txt_br = new BufferedReader(new FileReader(txt));
		      String Zpath = txt.getAbsolutePath();


		     Zpath = DeleteAfterSpecifiedCharactor(Zpath,"ad"); //絶対パスを取得

		      String NamberName = txt_br.readLine();//学籍番号名前
			  File di = new File(Zpath);
			  String[] file_name = di.list(); //ファイル名を取得
			  File[] file_dir = di.listFiles();  //ファイルディレクトリを取得
			  int i=0,j=0;
			  int file_length=0;

			  while(j+1!=file_dir.length){ 	//ディレクトリかファイルを判別
				 if(file_dir[j].isFile()) {
				  file_length++;
				 }
				 j++;
			  }

			  while(i!=file_dir.length)
			    {
			     if (file_name[i].startsWith("sample"))//フォルダの中からsampleを探す
			     {
					BufferedReader c_br = new BufferedReader(new FileReader(file_dir[i]));
					String c_txt = fileToString(file_dir[i]);//Cファイルの中身を取得

					if(c_txt.indexOf(NamberName)==-1)//ファイル内に学番と名前がないとき
					{
						FileWriter file = new FileWriter(file_dir[i],false);
						PrintWriter pw = new PrintWriter(new BufferedWriter(file));
						pw.println(NamberName);//学番名前を書き込む
						pw.println();//改行
						pw.println(c_txt);//cの中身
						pw.close();
					}
					c_br.close();
			    }
			     i++;
			   }
			  txt_br.close();
		    } catch (IOException e) {
		      System.out.println(e);
		    }

		}



//----------------相対パスから絶対パスを出力するメソッド-------------------
private static String DeleteAfterSpecifiedCharactor(String text, String spec) {
    if (!text.contains(spec)) {
        return text;
    }

    return text.substring(0, text.indexOf(spec) + spec.length());
}



//-----------------ファイル内容を文字列化するメソッド----------------------
 public static String fileToString(File file) throws IOException {
	    BufferedReader br = null;
	    try {
	      // ファイルを読み込むバッファドリーダを作成
	      br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	      StringBuffer sb = new StringBuffer();// 読み込んだ文字列を保持するストリングバッファを用意
	      int c;

	      // ファイルから１文字ずつ読み込み、バッファへ追加
	      while ((c = br.read()) != -1) {
	        sb.append((char) c);
	      }
	      // バッファの内容を文字列化して返す
	      return sb.toString();
	    } finally {
	      br.close();
	    }
	  }
}

