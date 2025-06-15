package com.feistel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class CifraDeFeistelTest {

    @Test
    public void deveCriptografarEDecriptarCorretamente() {
        String chave = "teste_seguro";
        int original = 0xCAFEBABE;

        int cifrado = CifraDeFeistel.encriptar(original, chave);
        int decifrado = CifraDeFeistel.decriptar(cifrado, chave);

       assertEquals(Integer.toBinaryString(original),
             Integer.toBinaryString(decifrado),
             "Bits devem coincidir após decriptar.");
    }

    @Test
    public void cifraNaoDeveRetornarOMesmoValor() {
        String chave = "senha_simples";
        int original = 0xDEADBEEF;

        int cifrado = CifraDeFeistel.encriptar(original, chave);

        assertNotEquals(original, cifrado, "O valor cifrado não deve ser igual ao valor original.");
    }

    @Test
    public void cifrasComChavesDiferentesDevemGerarResultadosDiferentes() {
        int original = 0xBADA55;

        int cifrado1 = CifraDeFeistel.encriptar(original, "chave1");
        int cifrado2 = CifraDeFeistel.encriptar(original, "chave2");

        assertNotEquals(cifrado1, cifrado2, "Cifras com chaves diferentes devem gerar saídas diferentes.");
    }

    @Test
    public void deveFuncionarComValorZero() {
        int original = 0x00000000;
        String chave = "chave_padrao";

        int cifrado = CifraDeFeistel.encriptar(original, chave);
        int decifrado = CifraDeFeistel.decriptar(cifrado, chave);

        assertEquals(original, decifrado, "Mesmo o valor zero deve ser reversível.");
    }
}
