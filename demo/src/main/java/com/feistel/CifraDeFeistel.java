package com.feistel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CifraDeFeistel {

    private static final int NUM_RODADAS = 16;

    //aplica a cifra
    public static int encriptar(int blocoClaro, String chave){
        byte[] subchaves = gerarSubchaves(chave);
        int[] partes = dividirBloco(blocoClaro);
        int esquerda = partes[0]; // 16 bits mais significativos
        int direita = partes[1]; // 16 bits menos significativos

        for(int i=0; i<NUM_RODADAS; i++){
            int temp = direita;
            // Nova direita: esquerda anterior XOR função f aplicada à direita anterior
            direita = esquerda ^ funcaoF(direita, subchaves[i]);
            // Nova esquerda vira a antiga direita
            esquerda = temp;
        }

        return juntarBloco(esquerda, direita);
    }

    //recupera o bloco original
    public static int decriptar(int blocoCriptografado, String chave) {
        byte[] subchaves = gerarSubchaves(chave);
        int[] partes = dividirBloco(blocoCriptografado);
        int esquerda = partes[0];
        int direita = partes[1];

        for (int i = NUM_RODADAS - 1; i >= 0; i--) {
            int temp = esquerda;
            // Nova esquerda: direita anterior XOR função f da esquerda anterior
            esquerda = direita ^ funcaoF(esquerda, subchaves[i]);
            // Nova direita vira a antiga esquerda
            direita = temp;
        }

        return juntarBloco(esquerda, direita);
    }

    // Divide o bloco de 32 bits em duas metades de 16 bits
    private static int[] dividirBloco(int bloco) {
        int esquerda = (bloco >>> 16) & 0xFFFF; // bits mais à esquerda
        int direita = bloco & 0xFFFF; // bits mais à direita
        return new int[] { esquerda, direita };
    }

    // Junta duas metades de 16 bits em um inteiro de 32 bits
    private static int juntarBloco(int esquerda, int direita) {
       return (esquerda << 16) | (direita & 0xFFFF);
    }

    // Gera as subchaves com base na chave textual
    private static byte[] gerarSubchaves(String chave) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(chave.getBytes());
            return Arrays.copyOf(hash, NUM_RODADAS); // 16 subchaves de 1 byte
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 não disponível.");
        }
    }

    // Função de mistura (usa XOR e um shift)
    private static int funcaoF(int valor, byte subchave) {
        valor = valor & 0xFFFF;                         // isola 16 bits
        int rotacionado = (valor << 5) | (valor >>> 11); // rotação circular
        rotacionado = rotacionado & 0xFFFF;             // garante 16 bits
        return rotacionado ^ subchave;
    }

    public static void main(String[] args) {
        String chave = "minha_chave_segura";
        int textoClaro = 0xCAFEBABE;  // valor de 32 bits

        int criptografado = encriptar(textoClaro, chave);
        int decriptado = decriptar(criptografado, chave);

        System.out.println("Texto original:     " + Integer.toHexString(textoClaro));
        System.out.println("Criptografado:      " + Integer.toHexString(criptografado));
        System.out.println("Decriptado:         " + Integer.toHexString(decriptado));
    }
    
}
