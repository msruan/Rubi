# Rubi

Projeto de rede social feito com carinho por Bianca Bezerra de Oliveira e Ruan Macedo Santos


## Uso

As dependências do projeto são gerenciadas pelo Maven, então caso tenha certeza de que ele está instalado antes de tentar executar o código.


No Ubuntu e derivados, você pode obter o Maven via terminal assim:
```bash
  sudo apt install maven
```
    
## Documentação

Inicialmente os atributos privados estavam sendo nomeados com o padrão Python (__atribute_), mas após utilizar a biblioteca Lombok para gerar os getters automaticamente, essa nomenclatura se tornou inviável, pois atributos como __id,_ tinham métodos _get_id()_, enquanto esperava-se um _getId()_.

