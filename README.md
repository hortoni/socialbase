# SocialBase
SocialBase Programming Challenge - Mobile Developer

O aplicativo foi desenvolvido para a plataforma Android, com versão mínima 2.3, no Android Studio 1.5.1 e foi utilizado o Gradle na versão 2.8 para automatização da build do projeto.

O aplicativo contém 3 telas:
- Main
- Detail
- Web

# Aplicativo
A primeira tela do aplicativo é a tela de principal. Essa tela é responsável por carregar os itens do webservice e mostrar ao usuário em forma de lista. Ao carregar, o aplicativo guarda essa lista de objetos em um banco de dados local para ser carregado na ausência de Internet. O webservice escolhido foi o do <a href="https://www.ifixit.com/api/2.0/guides/">IFixIt</a>. Também é possível fazer uma busca por título nessa lista.

Ao clicar em um item da lista, o usuário é direcionado a tela de detalhes, na qual é mostrado detalhes como o nome do usuário responsável por aquela postagem, o tipo da postagem e a categoria, entre outros. Caso deseje ver o post completo, o usuário poderá clicar no ícone de link na parte superior direita, destacado em cinza.

Ao clicar no ícone de link, o usuário é direcionado a terceira tela, a tela da postagem completa, no qual o usuário pode ver todo o conteúdo diretamente da site da IFixIt, através de um webview.
