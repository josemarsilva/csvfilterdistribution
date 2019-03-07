# csvfilterdistribution

## 1. Introdução ##

Este repositório contém o código fonte do componente **csvfilterdistribution**. Este componente distribuído como um arquivo (.jar) pode ser executado em linha de comando (tanto Windows quanto Linux). O **csvfilterdistribution** recebe como argumento um arquivo (.csv) de entrada e um arquivo de configuração com a distribuição proporcional das linhas que devem ser geradas no arquivo (.csv) de saída.


### 1.1. Help Online linha

* O componente **csvfilterdistribution** funciona com argumentos de linha de comando (tanto Windows quanto Linux). Com o argumento '-h' mostra o help da a aplicação.

```bat
C:\My Git\workspace-github\csvintoexcel\dist>java -jar csvfilterdistribution.jar
```




### 2. Documentação ###

### 2.1. Diagrama de Caso de Uso (Use Case Diagram) ###

#### Diagrama de Caso de Uso

![UseCaseDiagram](doc/UseCaseDiagram%20-%20Context%20-%20CsvFilterDistribution.png)


### 2.5. Requisitos ###

* n/a


## 3. Projeto ##

### 3.1. Pré-requisitos ###

* Linguagem de programação: Java
* IDE: Eclipse (recomendado Oxigen 2)
* JDK: 1.8
* Maven Repository dependence: Apache CLI
* Maven Repository dependence: Apache POI

### 3.2. Guia para Desenvolvimento ###

* Obtenha o código fonte através de um "git clone". Utilize a branch "master" se a branch "develop" não estiver disponível.
* Faça suas alterações, commit e push na branch "develop".


### 3.3. Guia para Configuração ###

* n/a


### 3.4. Guia para Teste ###

* n/a


### 3.5. Guia para Implantação ###

* Obtenha o último pacote (.war) estável gerado disponível na sub-pasta './dist'.



### 3.6. Guia para Demonstração ###

* **Step-1**: Suponha o arquivo `input.csv` abaixo:

```csv
col-1;col-2;col-3;col-4;col-5
1;impar;cachorro;Brasil;branco
2;par;gato;Argentina;preto
3;impar;pato;Chile;azul
4;par;leao;Uruguai;amarelo
5;impar;onca;Estados Unidos;verde
6;par;tigre;Mexico;branco
7;impar;jaguatirica;Canada;preto
8;par;lobo;Portugal;azul
9;impar;vaca;Espanha;amarelo
10;par;cavalo;Franca;verde
11;impar;galinha;Alemanha;branco
12;par;cisne;Belgica;preto
13;impar;papagaio;Marrocos;azul
14;par;arara;Argelia;amarelo
15;impar;sabia;Libia;verde
16;par;bode;Tunisia;branco
17;impar;burro;Brasil;preto
18;par;elefante;Egito;azul
19;impar;macaco;Mauritania;amarelo
20;par;aguia;Senegal;verde
```

* **Step-2**: Suponha que você deseje filtrar e redistribuir uma amostra com parte do conteúdo deste arquivo `input.csv`,  nas seguintes proporções:
    * 20% animais felinos
	* 10% animais caninos


* **Step-3**: Observando o arquivo `input.csv` identificamos que:
    * o arquivo tem uma amostra maior do que você precisa, logo você pode extrair os seus dados dele
	* a coluna `3` do arquivo tem o tipo do animal, os felinos são: `gato|leao|onca|tigre|jaguatirica` e os _caninos_ são `cachorro|lobo`
	* desta especificação e requisitos montamos o arquivo de configuração `config3.csv`

```csv
config-name;distribution;col-number-list;regular-expression
felinos;0.2;3;gato|leao|onca|tigre|jaguatirica
caninos;0.1;3;cachorro|lobo
```

* **Step-4**: Executamos a ferramenta **csvfilterdistribution** em linha de comando com os seguintes argumentos:

```cmd
java -jar csvfilterdistribution.jar -i input.csv -c config3.csv -o output.csv
Reading 'config3.csv' ...
Counting 'input.csv' ...
Counting - row: 21
Calculating distribution ...
Reading, filtering and distributing 'input.csv' ...
Reading, filtering and distributing - row: 21
Summary filter distribution ...
  ConfigName                     %          Planned    Distributed
  ------------------------------ ---------- ---------- ------------
  felinos                        20%        4          4
  caninos                        10%        2          2
```

* **Step-5**: Observamos o resultado do arquivo `output.csv` baseado no arquivo de entrada e com as configurações de filtros e distribuição:

```csv
col-1;col-2;col-3;col-4;col-5
1;impar;cachorro;Brasil;branco
2;par;gato;Argentina;preto
4;par;leao;Uruguai;amarelo
5;impar;onca;Estados Unidos;verde
6;par;tigre;Mexico;branco
8;par;lobo;Portugal;azul
```




### 3.7. Guia para Execução ###

* n/a


## Referências ##

* n/a
