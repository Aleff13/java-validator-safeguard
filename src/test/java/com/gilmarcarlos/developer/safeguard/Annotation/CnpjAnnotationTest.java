package com.gilmarcarlos.developer.safeguard.Annotation;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.constraint.annotations.Verify;
import br.com.safeguard.interfaces.Check;
import br.com.safeguard.types.ParametroTipo;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

public class CnpjAnnotationTest {
    @Test
    public void ShouldBeValidCNPJ() {
        SafeguardCheck checker = new SafeguardCheck();

        String cnpjLegacy = "00.623.904/0001-73";

        Company company = new Company();
        company.setCnpj(cnpjLegacy);

        Check results = checker.elementOf(company).validate();

        boolean isValid = !results.hasError();

        Assert.assertTrue(isValid);
    }

//    @Test
//    public void ShouldBeValidAlphaCNPJ() {
//        SafeguardCheck checker = new SafeguardCheck();
//
//        String cnpjLegacy = "ab.623.904/0001-73";
//
//        Company company = new Company();
//        company.setCnpj(cnpjLegacy);
//
//        Check results = checker.elementOf(company).validate();
//
//        boolean isValid = !results.hasError();
//
//        Assert.assertTrue(isValid);
//    }

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