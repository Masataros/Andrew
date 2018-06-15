/*
メールヘッダ(ファイル)からFrom(RFC対応)と
Subject（＋Re）を抜き出して表示する
*/


package Andrew;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;


public class Withdrawn {
		  public static void main(String[] args) throws IOException {
		    try {
		      InputStream in = new FileInputStream(new File(""));
		      Session session = Session.getDefaultInstance(
		          new java.util.Properties(), null);
		      MimeMessage message = new MimeMessage(session, in);
		      print(message);
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    }
		  }

		  private static void print(MimeMessage message) throws MessagingException,
		      IOException {

		    Address[] from = message.getFrom();
		    String address = from[0].toString();

		    String[] Address = address.split(" ", 0);

		    String subject;

		    subject = message.getSubject();
		    subject = "Re:"+ subject;

		    System.out.println(subject);

		    if (Address[0].contains("@"))
		    {

		    	address = Address[0];

		    	System.out.println(Address[0].replace("<", "").
		    			replace(">", "").replace("(", "").replace(")", ""));
		    }

		    if (Address[1].contains("@"))
		    {

		    	address = Address[1];

		    	System.out.println(Address[1].replace("<", "").
		    			replace(">", "").replace("(", "").replace(")", ""));
		    }

		  }
		}
