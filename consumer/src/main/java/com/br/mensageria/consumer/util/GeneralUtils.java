package com.br.mensageria.consumer.util;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {

    private GeneralUtils() {}

    public static Boolean isNullOrEmpty(final String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isCpf(final String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") ||
                CPF.equals("33333333333") ||
                CPF.equals("44444444444") ||
                CPF.equals("55555555555") ||
                CPF.equals("66666666666") ||
                CPF.equals("77777777777") ||
                CPF.equals("88888888888") ||
                CPF.equals("99999999999") ||
                (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean isEmail(final String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNumeric(final Integer num) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (num == null) {
            return false;
        }
        return pattern.matcher(num.toString()).matches();
    }

    public static boolean isNumeric(final BigInteger num) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (num == null) {
            return false;
        }
        return pattern.matcher(num.toString()).matches();
    }

    public static boolean isBoolean(final Boolean strBool) {
        if (strBool == null) {
            return false;
        }
        return strBool || !strBool;
    }
}
