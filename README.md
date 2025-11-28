#Executando o projeto:#
1. Requisitos

Antes de rodar o sistema, confirme que você possui:

Java Development Kit (JDK) 17 ou superior

Para verificar sua versão:

java -version

IDE recomendada:

IntelliJ IDEA

Dependências externas:

Nenhuma dependência externa é necessária, o projeto utiliza apenas a biblioteca padrão do Java (Swing + IO).

2. Clonar o repositório
git clone https://github.com/AztecIsDead/Biblioteca-java.git

3. Abrir o projeto na IDE

Abra o IntelliJ IDEA 

Vá em File → Open

Selecione a pasta Biblioteca-java

Aguarde a IDE indexar o projeto

#Localizar a classe principal (MAIN)

O sistema deve ser iniciado exclusivamente pelo arquivo Main.java.

Não execute os Dialogs diretamente

O projeto contém várias telas (JDialogs, JFrames),
mas nenhuma delas deve ser executada isoladamente.

Se você rodar qualquer tela separada:
CSVs podem não carregar, tabelas podem aparecer vazias, handlers podem falhar, o programa abre sem estado inicial.

#Somente o Main configura o ambiente correto para o sistema funcionar.

5. Executar o sistema
Na IDE:

Abra Main.java
Clique em Run 
A tela inicial será exibida

