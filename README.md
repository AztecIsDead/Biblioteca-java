# Trabalho de POO (P2)
Esse repositório contém o código do trabalho da disciplina Programação
Orientada a Objetos (POO), equivalente à nota da avaliação P2,
onde o nosso grupo ficou responsável por elaborar um
sistema de gestão de uma biblioteca em Java.

O Projeto não usa bibliotecas externas e tem interface feita em **Swing**.

## Como Usar:
### Pré-Requisitos:
- **Oracle Open JDK** versão **24.0.2** ou maior

### Clonando o Repositório:
Execute o comando a seguir na linha de comando:
``` 
git clone https://github.com/AztecIsDead/Biblioteca-java.git
cd BibliotecaJava
```
Em seguida, rode o arquivo `SwingMain.java`

### Estrutura dos arquivos _.csv_:
- O programa usa arquivos da extensão _**.csv**_ como forma de manter os dados
dos **Livros**, **Clientes**, **Funcionários**, etc.


- O repositório contém arquivos de exemplo para demonstrar as funcionalidades
do projeto.

```
(Arquivo 'livros.csv' padrão do repositório)

Titulo,Autor,Disponibilidade -- Cabeçalho
1984,George Orwell,true -- Dados do Primeiro Livro registrado
O Senhor dos Anéis,J. R. R. Tolkien,false -- Dados do Segundo Livro Registrado
(...)
```

- Na GUI, os arquivos são lidos e os atributos dos livros e outras classes são
mostrados como no exemplo:

[imagem](url)

### Logando no aplicativo:
- Para usar o sistema, é necessário logar como 'Funcionário' ou 'Cliente'.


- Quando logando como funcionário, será constatado se o nome especificado
está entre os registrados no arquivo `funcionarios.csv` para permitir acesso.
A senha padrão para todos os funcionários é **'admin123'**. 


- Para logar como cliente, o cliente precisa estar registrado no arquivo
`clientes.csv` e deverá informar a sua senha, que é diferente entre cada cliente.


- Também é possível se registrar como novo cliente informando o seu nome, idade
e a sua senha.

## Funcionalidades:
- O projeto permite aos clientes pedir permissão para alugar os livros
disponíveis e permite que eles se registrem como novos clientes.


- Para funcionários, o projeto permite vizualizar os livros da biblioteca,
registrar novos livros e editar os existentes, registrar novos funcionários
e clientes, além de vizualizar e autorizar ou recusar pedidos pendentes
dos clientes.
O projeto também mapeia o tempo total de uso de cada funcionário no arquivo
`sessions.csv` e exibe esse tempo na GUI.

## INTEGRANTES:
- [Caius Vinícius (Líder do Projeto)](https://github.com/AztecIsDead)
- [José Victor Félix](https://github.com/josevictorcfelix)
- [Renan Augusto](https://github.com/onamureS)
- [Yan Magnum](https://github.com/Yan2423)
- [Igor Paz](https://github.com/igupingu)


- Disciplina: Programação Orientada A Objetos
- Professor: Samuel Oliveira
- Instituto De Ensino Superior ICEV.