/*
 * 学籍番号、日付、鍵からハッシュを生成する
*/

package Andrew;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class Hash {


	public static void main(String[] argv) throws 
		NoSuchAlgorithmException  {

		try {
            File file = new File("LoginData");

            if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String Address,Number;
            SimpleDateFormat nowday = new SimpleDateFormat("yyyyMMdd");
            String day = nowday.format(new Date());
            String hashKey;


            while ((Address = bufferedReader.readLine()) != null) {

            	Number = Address.substring(0, Address.indexOf("@"));
            	Number = Address.substring(1);

            	hashKey = Hashkey(Number + day.toString());
            	mailSend(Number,hashKey);

            }

            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();

        }


	}


	public static String Hashkey(String scr) throws 
		NoSuchAlgorithmException{
		
				String key = "kagidayo";
				String scrText = key + scr;
				
				MessageDigest md = MessageDigest.getInstance("MD5");

				md.update(scrText.getBytes());

				byte[] hashBytes = md.digest();

				int[] hashInts = new int[hashBytes.length];
				StringBuilder sb = new StringBuilder();
				
				for (int i=0; i < hashBytes.length; i++) {
					hashInts[i] = (int)hashBytes[i] & 0xff;
					if (hashInts[i] <= 15) {
						sb.append("0");
					}
					sb.append(Integer.toHexString(hashInts[i]));

				}

				String hashText = sb.toString();

		return hashText;
	}



	public static void mailSend(String toAddress, String text) {

		try {
			
			// 認証やらポートなどの設定
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props);
			Message msg = new MimeMessage(session);
			String fromAddress ="";
			String password = "";
			
			// 送信元メールアドレスのセット
			msg.setFrom(new InternetAddress(fromAddress));

			// 送信先メールアドレスのセット
			msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toAddress, false));

			// メール本文
			msg.setText(text);

			// メール送信
			SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
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
