package com.gilmarcarlos.developer.safeguard.Parameter;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.types.ParametroTipo;
import org.junit.Assert;
import org.junit.Test;

public class CnpjParameterTest {

    @Test
    public void ShouldBeValidCnpj() {
        SafeguardCheck checker = new SafeguardCheck();

        String cnpjLegacy = "00.623.904/0001-73";

        boolean isValid = !checker.elementOf(cnpjLegacy, ParametroTipo.CNPJ).validate().hasError();

        System.out.println(isValid);
        Assert.assertTrue(isValid);
    }

}