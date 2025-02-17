package com.gilmarcarlos.developer.safeguard;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.constraint.annotations.Verify;
import br.com.safeguard.types.ParametroTipo;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

public class CnpjTest {

    @Test
    public void cnpjLegacy() {
        SafeguardCheck checker = new SafeguardCheck();

        String cnpjLegacy = "00.623.904/0001-73";

        boolean isValid = !checker.elementOf(cnpjLegacy, ParametroTipo.CNPJ).validate().hasError();

        System.out.println(isValid);
        Assert.assertTrue(isValid);
    }

    @Test
    public void cnpjAnnotation() {
        SafeguardCheck checker = new SafeguardCheck();

        String cnpjLegacy = "00.623.904/0001-73";

        Company company = new Company();
        company.setCnpj(cnpjLegacy);

        boolean isValid = !checker.elementOf(company.getCnpj(), ParametroTipo.CNPJ).validate().hasError();

        System.out.println(isValid);
        Assert.assertTrue(isValid);
    }

    static class Company implements Serializable {
        @Verify(value = ParametroTipo.CNPJ)
        private String cnpj;

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj){
            this.cnpj = cnpj;
        }
    }
}