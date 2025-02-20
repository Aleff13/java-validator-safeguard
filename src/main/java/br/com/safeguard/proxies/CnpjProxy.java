package br.com.safeguard.proxies;

import br.com.safeguard.interfaces.BaseParam;
import br.com.safeguard.types.ParametroTipo;

public class CnpjProxy {
    public static BaseParam apply(String value, BaseParam param) {
        if(!value.matches("[0-9./-]+") && param.equals(ParametroTipo.CNPJ)){
            return ParametroTipo.CNPJ_ALFANUMERICO;
        }

        return param;
    }
}
