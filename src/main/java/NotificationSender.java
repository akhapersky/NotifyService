import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificationSender {

    public static void sender(String result, String username, String password, String addressList) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(addressList)
            );
            message.setSubject("Events notifier");

            /*message.setText(" Dear User,"
                    + "\n <b>Today:</b>"
                    + "\n" + result
                    + "\n<i style='color:blue;'>Мы не выбираем времена. Мы можем только решать, как жить в те времена, которые выбрали нас.</i>\n" +
                    "\n" +
                    "<i style='color:blue;'>Дж.Р.Р. Толкин</i>"
            );*/
            String messageMainText = " Dear User,"
                    + "<br> <b>Today:</b>"
                    + "<br>" + result
                    + "<br><br>"
                    + "<br><i style='color:blue;'>Мы не выбираем времена. Мы можем только решать, как жить в те времена, которые выбрали нас.</i>" +
                    "<br>" +
                    "<i style='color:blue;'>Дж.Р.Р. Толкин</i>";


            message.setContent(messageMainText,"text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("MESSAGE HAS BEEN SENT!");
    }
}
