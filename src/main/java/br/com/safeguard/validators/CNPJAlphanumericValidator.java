package br.com.safeguard.validators;

import br.com.caelum.stella.MessageProducer;
import br.com.caelum.stella.SimpleMessageProducer;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.Validator;
import br.com.caelum.stella.validation.error.CNPJError;

import java.util.ArrayList;
import java.util.List;

public class CNPJAlphanumericValidator implements Validator<String>  {
    private static final int[] MULTIPLICADOR1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] MULTIPLICADOR2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private final MessageProducer messageProducer;

    public CNPJAlphanumericValidator(){
        this.messageProducer = new SimpleMessageProducer();
    }

    @Override
    public void assertValid(String s) {
        List<ValidationMessage> errors = this.getInvalidValues(s);
        if (!errors.isEmpty()) {
            throw new InvalidStateException(errors);
        }
    }

    private List<ValidationMessage> getInvalidValues(String cnpj) {
        List<ValidationMessage> errors = new ArrayList<>();

        if (cnpj != null) {

            String cnpjSemDigito = cnpj.substring(0, cnpj.length() - 2);
            String digitos = cnpj.substring(cnpj.length() - 2);
            String digitosCalculados = calcularDigitosVerificadores(cnpjSemDigito);

            if (!digitos.equals(digitosCalculados)) {
                errors.add(this.messageProducer.getMessage(CNPJError.INVALID_CHECK_DIGITS));
            }

        }
        return errors;
    }

    public static String calcularDigitosVerificadores(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9A-Za-z]", "");
        int[] valores = converterParaNumeros(cnpj);

        int primeiroDV = calcularDigito(valores, MULTIPLICADOR1);
        int[] valoresComDV1 = new int[valores.length + 1];
        System.arraycopy(valores, 0, valoresComDV1, 0, valores.length);
        valoresComDV1[valores.length] = primeiroDV;
        int segundoDV = calcularDigito(valoresComDV1, MULTIPLICADOR2);

        return String.valueOf(primeiroDV) + segundoDV;
    }

    private static int[] converterParaNumeros(String cnpj) {
        int[] valores = new int[cnpj.length()];
        for (int i = 0; i < cnpj.length(); i++) {
            char c = cnpj.charAt(i);
            if (Character.isDigit(c)) {
                valores[i] = c - '0';
            } else {
                valores[i] = (int) c - 48; // Conversão ASCII, subtraindo 48
            }
        }
        return valores;
    }

    private static int calcularDigito(int[] valores, int[] multiplicadores) {
        int soma = 0;
        int tamanho = multiplicadores.length;
        int inicio = valores.length - tamanho;
        for (int i = 0; i < tamanho; i++) {
            soma += valores[inicio + i] * multiplicadores[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    @Override
    public List<ValidationMessage> invalidMessagesFor(String s) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEligible(String s) {
        return true;
    }
}
