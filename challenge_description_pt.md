# Challenge Description PT

Desenvolve uma API para que outros serviços de backend possam ir buscar e armazenar compras de produtos. Por enquanto, a API só precisa responder a pedidos efetuados através de um servidor HTTP que está, ligado a uma base de dados que fornece as compras da empresa.

Outra equipa vai implementar a base de dados somente depois da tua equipa criar o serviço.

Tu vais implementar a classe que responde aos pedido e que define a assinatura dos métodos para acesso a base de dados DAO, que a outra equipe respeitará.

A seguir está o esquema de dados:

> Compra(id:Long, productType:String, expires:DateTime, purchaseDetails:Details)
>
> Detalhes(id:Long, description:String, quantity:Integer, value:Double)

A primeira operação devolve detalhes de compras efetuadas a empresa:

1. Devolve uma coleção de todas as compras existentes na base de dados;

2. Agrega os resultados válidos para a hora atual;

3. Subsequentemente chama a base de dados com uma coleção de IDs de compra agregados;

4. A base de dados fornece todos os detalhes de compra consultados;

5. Transforma os dados em um formato de dados textual e retorna e retorna a informação pedida. A segunda operação é responsável por armazenar - ou manipular atualizações - nas compras de produtos. A implementação da base de dados vai ser desenvolvida por outra equipa que disse que o SLA esperado será em torno de 2 segundos. O serviço deve expor métricas para serem recolhidos por serviços externos. Tens liberdade para escolher e expor as métricas que achares relevantes para analisar a utilização e performance do serviço. Está à vontade para adicionar outras operações relevantes no contexto de um micro-serviço.
