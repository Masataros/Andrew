/*
 以下のようなデータがテキストファイルで存在する。
ここではサンプルとして10行だが、実際には10K行程度
 新規のサービス提供について学生に個別にメールを送りたい。
送り先は既存の学内のメールアドレスである。
*/

package Andrew;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class SendGmail {

		public static void main(String[] argv) {

			try {
	            // ファイルのパスを指定する
	            File file = new File("");

	            // ファイルが存在しない場合に例外が発生するので確認する
	            if (!file.exists()) {
	                System.out.print("ファイルが存在しません");
	                return;
	            }

	            // BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
	            FileReader fileReader = new FileReader(file);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            String data,dataAdd,dataAdd2,dataNum;
	            while ((data = bufferedReader.readLine()) != null) {



	            	dataAdd = data.substring(0, data.indexOf(","));
	            	dataAdd = dataAdd.replace("\"","");
	            	System.out.println(dataAdd);

	            	dataAdd2 = dataAdd.replace("o3","sub");

	            	dataNum=data.substring(data.indexOf(",")+1);
	                dataNum = dataNum.replace("\"","");
	                System.out.println(dataNum);


	            }

	            // 最後にファイルを閉じてリソースを開放する
	            fileReader.close();

	        } catch (IOException e) {
	            e.printStackTrace();

	        }

		}

		public static void mailSend(String title, String text, String toAddress,
			String fromAddress, String password) {

			try {
				// プロパティの設定
				Properties props = System.getProperties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props);
				Message msg = new MimeMessage(session);

				// 送信元メールアドレスのセット
				msg.setFrom(new InternetAddress(fromAddress));

				// 送信先メールアドレスのセット
				msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddress, false));

				// メールタイトル
				msg.setSubject(title);

				// メール本文
				msg.setText(text);


				// メール送信
				SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
				try {
					t.connect("smtp.gmail.com", fromAddress, password);
					t.sendMessage(msg, msg.getAllRecipients());
				} finally {
					t.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



