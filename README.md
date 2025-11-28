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
Em seguida, rode o arquivo `Main.java`.

(Outros arquivos como o `SwingMain` não devem ser executados diretamente).

### Estrutura dos arquivos _.csv_:
- O programa usa arquivos da extensão _**.csv**_ como forma de manter os dados
  dos **Livros**, **Clientes**, **Funcionários**, etc.


- O repositório contém arquivos de exemplo para demonstrar as funcionalidades
do projeto.

```
(Arquivo 'livros.csv' padrão do repositório)

id,titulo,autor,raro,totalCopias,copiasDisponiveis -- Cabeçalho
4,Dom Quixote,Miguel de Cervantes,false,3,3 -- Dados do Primeiro Livro registrado
5,Crime e Castigo,Fiódor Dostoiévski,true,1,1 -- Dados do Segundo Livro Registrado
(...)
```

### Logando no aplicativo:
- Para usar o sistema, é necessário logar como 'Funcionário' ou 'Cliente'.


- Quando logando como funcionário, será constatado se o nome de usuário
especificado está entre os registrados no arquivo `funcionarios.csv`
para permitir acesso. Se o nome do funcionário não estiver no arquivo,
será necessário informar um novo nome, usuário e senha para
registrar um novo funcionário.


- Para logar como cliente, o cliente precisa estar registrado no arquivo
`clientes.csv` e deverá informar a sua senha.
Caso o cliente tentando logar não esteja registrado no arquivo, será necessário
informar um novo nome, login e senha para registrar o novo cliente.

## Funcionalidades:
- O projeto permite aos clientes pedir permissão para alugar os livros
disponíveis e permite que eles se registrem como novos clientes.
Adicionalmente, clientes podem ser registrados como clientes VIP,
que terão acesso a empréstimo de livros raros
e vagas extras em eventos na biblioteca.


- Para funcionários, o projeto permite vizualizar os livros da biblioteca,
registrar novos livros e editar os existentes, registrar novos funcionários
e clientes, além de vizualizar e autorizar ou recusar pedidos pendentes
dos clientes.
Funcionários também tem permissão para editar e criar eventos na biblioteca.
O projeto também mapeia o tempo total de uso de cada funcionário no arquivo
`sessions.csv` e exibe esse tempo na GUI.

## INTEGRANTES:
- [Caius Vinícius (Líder do Projeto)](https://github.com/AztecIsDead)
- [José Victor Félix](https://github.com/josevictorcfelix)
- [Renan Augusto](https://github.com/onamureS)
- [Yan Magnum](https://github.com/Yan2423)
- [Igor Paz](https://github.com/igupingu)
---
- Disciplina: Programação Orientada A Objetos
- Professor: Samuel Oliveira
- Instituto De Ensino Superior ICEV.