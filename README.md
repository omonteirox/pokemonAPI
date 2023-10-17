# PokedexAPI
    - Desafio técnico com foco em java e conhecimento de API'S rest.
    - Utilizando java 8 e postgresql
    - Utilizando Jersey
    - Funcionalidade de importar objetos via json implementado
    - Padronizando retornos com ResponseModel
# Rotas Criadas
Segue a baixo as rotas criadas de acordo com cada método:
## POST
/pokemon
Corpo: json
Exemplo:
```json


{
    "num": "030",
    "name": "Ivysaur",
    "type" : ["Fogo","Agua","Mar"],
    "pre_evolution": [{
      "num": "001",
      "name": "Bulbasaur"
    }],
    "next_evolution": [{
      "num": "003",
      "name": "Venusaur"
    }]
  }
```
  Retorna o pokemon criado;
## GET
/pokemon/NUM
Retorna o pokemon de acordo com o "Num" que é o identificador único

/pokemons
Retorna todos os pokemons

/pokemons/pagina/quantidade
Retorna os pokemons listados em páginas

/pokemons/TIPO_POKEMON
Retorna os pokemons do tipo colocado na url

## PUT
pokemon/NUMERO_DO_POKEMON
Atualiza o pokemon com o numero passado usando os dados do pokemon informados no body da requisição.

# DELETE
pokemon/NUMERO_DO_POKEMON
Deleta o pokemon de acordo com seu número



