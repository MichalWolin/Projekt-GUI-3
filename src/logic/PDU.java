package logic;

import java.time.LocalDateTime;

public class PDU {
    private String pdu;
    public PDU(String message, long sender, long receiver) {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder pduBuilder = new StringBuilder();
        if(receiver != 0){
            pduBuilder.append("0");
            pduBuilder.append(String.valueOf(receiver).length() / 2 + 2);
            pduBuilder.append("A1");
            pduBuilder.append(phoneBuilder(String.valueOf(receiver)));
        }else{
            pduBuilder.append("00");
        }
        pduBuilder.append("040");
        pduBuilder.append(String.valueOf(sender).length() / 2 + 5);
        pduBuilder.append("A1");
        pduBuilder.append(phoneBuilder(String.valueOf(sender)));
        pduBuilder.append("0000");
        pduBuilder.append(dateBuilder());
        pduBuilder.append(convertToHex(message.length()));
        pduBuilder.append(messageBuilder(message));
        pdu = pduBuilder.toString();
    }

    public String getPDU(){
        return pdu;
    }

    public static String dateBuilder(){
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();

        sb.append(reverseDigits(String.valueOf(now.getYear()-2000)));
        sb.append(reverseDigits(String.valueOf(now.getMonthValue())));
        sb.append(reverseDigits(String.valueOf(now.getDayOfMonth())));
        sb.append(reverseDigits(String.valueOf(now.getHour())));
        sb.append(reverseDigits(String.valueOf(now.getMinute())));
        sb.append(reverseDigits(String.valueOf(now.getSecond())));

        return sb.toString();
    }

    public static String phoneBuilder(String phoneNumber) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < phoneNumber.length() - 1; i += 2) {
            String digitPair = phoneNumber.substring(i, i + 2);
            sb.append(reverseDigits(digitPair));
        }

        sb.append("F");
        sb.append(phoneNumber.charAt(phoneNumber.length() - 1));

        return sb.toString();
    }

    public static String reverseDigits(String digits) {
        char digit1, digit2;
        if(digits.length() == 1){
            digit1 = '0';
            digit2 = digits.charAt(0);
        }else{
            digit1 = digits.charAt(0);
            digit2 = digits.charAt(1);
        }
        return String.valueOf(digit2) + String.valueOf(digit1);
    }

    public static String messageBuilder(String message) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            String hex = charToHex(letter);
            sb.append(hex);
        }

        return sb.toString();
    }

    public static String charToHex(char letter) {
        int value = (int)letter;
        String hex = "";

        while (value > 0) {
            int remainder = value % 16;
            char hexDigit = (char) (remainder < 10 ? remainder + '0' : remainder + 'A' - 10);
            hex = hexDigit + hex;
            value /= 16;
        }

        return hex;
    }

    public static String convertToHex(int number) {
        StringBuilder sb = new StringBuilder();

        if (number == 0) {
            return "00";
        }

        while (number > 0) {
            int remainder = number % 16;
            char hexDigit = getHexDigit(remainder);
            sb.insert(0, hexDigit);
            number /= 16;
        }

        return sb.toString();
    }

    public static char getHexDigit(int value) {
        if (value < 10) {
            return (char) (value + '0');
        } else {
            return (char) (value - 10 + 'A');
        }
    }
}
