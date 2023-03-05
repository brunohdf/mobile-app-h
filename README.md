# mobile-app-h

Exercita conceitos relacionados do desenvolvimento na plataforma Android consumindo uma APi externa (TMDB).

Conceitos aplicados:
- Clean architecture, uma interpretação simplificada
- Single activity
- Injeção de dependência

Principais libraries utilizadas:
- Jetpack ViewModel, Navigation, LiveData
- Koin
- RxJava
- Retrofit + Moshi
- Unit + mockk


## Visão geral da arquitetura:
Fragment -> ViewModel > UseCase > Repository > DataSource

Cobertura de testes
As camadas ViewModel, UseCase, Repository e DataSource foram projetadas para serem testáveis unitáriamente.
O fluxo de listagem de filmes foi apenas parcialmente testado visando demostrar a prática de escrita de testes e o isolamento das camadas.

## Observações:
Este repositório foi criado em Jun 7, 2020 e vem sendo, eventualmente, modernizado para demostração.

### Última refatoração (Mar, 2023):
- Jetpack Navigation
- Substitui Api anterior (heroku) por TMDB
- Melhorias gerais de legibilidade e arquitetura.



