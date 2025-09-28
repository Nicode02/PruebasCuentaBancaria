package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class CuentaTest {
    private Cuenta cuenta;

    //Instancia un objeto de la clase Cuenta
    @BeforeEach
    void setUp() {
        cuenta = new Cuenta("Juan", new BigDecimal("1000.00"));
    }

    /*Intenta retirar 200 en una cuenta de saldo de 1000, confirma que el saldo
    se reduzca correctamente a 800.00*/
    @Test
    void testDebitoReduceSaldo() {
        cuenta.debito(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("800.00"), cuenta.getSaldo());
    }

    /*Intenta retirar 1500 de una cuenta con un saldo de 1000 y compara que
    devuelva el mensaje de la excepción personalizada*/
    @Test
    void testDebitoDineroInsuficiente() {
        DineroInsuficienteException ex = assertThrows(
                DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1500.00"));
        });
        assertEquals("Dinero Insuficiente", ex.getMessage());
    }

    /*Verificar que el saldo de la cuenta no cambia tras un intento fallido
    de retiro de saldo */
    @Test
    void testDebitoDineroInsuficienteNoCambiaSaldo() {
        BigDecimal saldoAntes = cuenta.getSaldo();
        assertThrows(DineroInsuficienteException.class, () -> cuenta.debito(new BigDecimal("1500.00")));
        assertEquals(0, cuenta.getSaldo().compareTo(saldoAntes)); // compara valores BigDecimal
    }

    /*Deposita 500 de credito a una cuenta con saldo inicial de 1000, valida
    que el saldo final sea 1500 */
    @Test
    void testCreditoAumentaSaldo() {
        cuenta.credito(new BigDecimal("500.00"));
        assertEquals(new BigDecimal("1500.00"), cuenta.getSaldo());
    }

    /*Crea otra cuenta con el mismo titular y mismo saldo para validar
    el metodo equals*/
    @Test
    void testEqualsCuentasIguales() {
        Cuenta otra = new Cuenta("Juan", new BigDecimal("1000.00"));
        assertEquals(cuenta, otra);
    }
    /*Crea otra cuenta con un titular distinto pero el mismo saldo para validar
    que equals toma en cuenta tanto saldo como persona para comparar*/
    @Test
    void testEqualsCuentasDistintas() {
        Cuenta otra = new Cuenta("Pedro", new BigDecimal("1000.00"));
        assertNotEquals(cuenta, otra);
    }
    /*Crea otra cuenta sin persona ni saldo (null) para validar que equals responde
    a valores nulos y no causa la excepción (NullPointerException)
    */
    @Test
    void testEqualsConNulls() {
        Cuenta cuentaNula = new Cuenta(null, null);
        assertNotEquals(cuenta, cuentaNula);
    }
}