# Product Buys API

Product buys API challenge proposed by iCliGo

## Challenge

[Challenge Description PT](challenge_description_pt.md)

## SwaggerUI

[`http://productbuysapi.onthewifi.com/swagger-ui.html`](http://productbuysapi.onthewifi.com/swagger-ui.html)

## Postman Collection

[`ProductBuysAPI.postman_collection.json`](ProductBuysAPI.postman_collection.json)

## JavaDoc

In order to generate the JavaDoc documentation, follow the next steps:

1. Go to the "ProductBuysAPI" directory:

   ```bash
   cd ProductBuysAPI
   ```

2. Build assets to target directory:

   ```bash
   ./mvnw install
   ```

The JavaDoc documentation should be placed in the target directory and it can be viewd by opeining the file `ProductBuysAPI/target/apidocs/index.html` with a web browser.

## Local Deployment

`docker-compose -f docker-compose.yaml up -d`

## Built with

- **Java**
- **Spring**
- **Maven**
- **MySQL**
- **Docker**
- **JetBrains IDE's**
- **Git**

## License

Distributed under the [MIT License](https://choosealicense.com/licenses/mit/). See [`LICENSE`](LICENSE) for more information.

## Author

- **Luis Marques - [`LuisMarques99`](https://github.com/LuisMarques99)**
