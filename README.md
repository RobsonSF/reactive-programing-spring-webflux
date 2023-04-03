# Reactive Programing - Spring Webflux

##### Project developed for study, covering topics such as Reactive Programing with Spring Webflux.

##### Technologies used -> Java, Spring Webflux, Spring Validation, MongoDB Atlas and tests.


## Project taught by instructor [Valdir Cezar](https://www.linkedin.com/in/valdircezar/).

## Reactive Programming

#### Reactive programming is a programming paradigm that promotes an asynchronous, non-blocking, event-driven approach to data processing. Reactive programming involves modeling data and events as observable data streams and implementing data processing routines to react to the changes in those streams.


## Blocking Request Processing

#### In traditional MVC applications, a new servlet thread is created (or obtained from the thread pool) when a request comes to the server. It delegates the request to worker threads for I/O operations such as database access etc. During the time worker threads are busy, the servlet thread (request thread) remains in waiting status, and thus it is blocked. It is also called synchronous request processing.

![image](https://user-images.githubusercontent.com/82185300/229627839-922791d0-168b-4363-a4d7-65cdd31b9f4d.png)


## Processamento de solicitação sem bloqueio
#### No processamento de solicitação sem bloqueio ou assíncrono, nenhum encadeamento está no estado de espera. Geralmente, há apenas um encadeamento de solicitação recebendo a solicitação.
#### Todas as solicitações recebidas vêm com um manipulador de eventos e informações de retorno de chamada. O encadeamento de solicitação delega as solicitações recebidas a um pool de encadeamentos (geralmente um pequeno número de encadeamentos) que delega a solicitação à sua função de manipulador e imediatamente começa a processar outras solicitações recebidas do encadeamento de solicitação.
#### Quando a função do manipulador é concluída, um thread do pool coleta a resposta e a passa para a função de retorno de chamada.


![image](https://user-images.githubusercontent.com/82185300/229628237-70c7f6a9-34df-48b9-a66f-7cd134bbb17c.png)


##  What is Spring WebFlux?

#### Spring WebFlux is a parallel version of Spring MVC and supports fully non-blocking reactive streams. It supports the back pressure concept and uses Netty as the inbuilt server to run reactive applications.

## Spring WebFlux heavily uses two publishers :

### Mono: Returns 0 or 1 element.

### Flux: Returns 0…N elements. A Flux can be endless, meaning that it can keep emitting elements forever. Also it can return a sequence of elements and then send a completion notification when it has returned all of its elements.

#### In Spring WebFlux, we call reactive APIs/functions that return Monos and Fluxes, and your controllers will return monos and fluxes. When you invoke an API that returns a mono or a flux, it will return immediately. The function call results will be delivered to you through the mono or flux when they become available.

### in this course I had the opportunity to deepen my knowledge in Reactive Programing - Spring Webflux


###### Useful links

[My LinkedIn](https://www.linkedin.com/in/robson-da-silva-fernandes/)

[howtodoinjava](https://howtodoinjava.com/spring-webflux/spring-webflux-tutorial/)
