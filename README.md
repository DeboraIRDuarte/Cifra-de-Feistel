🔐 Cifra de Feistel em Java
===========================

Este projeto implementa uma versão didática da Cifra de Feistel, um dos fundamentos de muitos algoritmos criptográficos clássicos (como o DES).

A implementação trabalha com blocos de 32 bits (int), divididos em duas metades de 16 bits, e aplica 16 rodadas com uma função de mistura simples. A chave usada é uma String, da qual são derivadas subchaves com SHA-256.

Objetivos
---------
- Compreender a estrutura de uma cifra de Feistel
- Estudar reversibilidade de cifras simétricas
- Implementar uma cifra em Java de forma testável e modular

Estrutura do Projeto
--------------------
demo/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/feistel/
│   │           └── CifraDeFeistel.java
│   └── test/
│       └── java/
│           └── com/feistel/
│               └── CifraDeFeistelTest.java

Como Executar
-------------
Pré-requisitos:
- Java 17+
- Maven

Compilar:
    mvn clean compile

Executar exemplo da main:
    mvn exec:java -Dexec.mainClass="com.feistel.CifraDeFeistel"

Testes
------
O projeto inclui testes com JUnit 5:

- Verifica se E(D(M)) = M (reversibilidade)
- Testa diferentes chaves
- Verifica se o resultado da cifra não é trivial

Rodar testes:
    mvn test

Detalhes Técnicos
-----------------
- Rodadas: 16
- Bloco: 32 bits (int)
- Função f:
    - Roda bits 16-bit circularmente 5 à esquerda
    - Aplica XOR com subchave
    - Mantém reversibilidade

Exemplo de Saída
----------------
Texto original:     cafebabe
Criptografado:      82d9a38e
Decriptado:         cafebabe

Créditos
--------
Projeto da disciplina Segurança em Sistemas de Computadores (DCC075)
Com auxílio do ChatGPT para estruturação, testes e documentação.

Licença
-------
Uso livre para fins acadêmicos e didáticos.

