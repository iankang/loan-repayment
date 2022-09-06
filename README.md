# loan-repayment
spring boot api for loan and repayment system

## setup
for end to end setup:

```sh
cd loan-repay
sudo docker-compose up -d .
```
for db setup only:
```sh
cd loan-repay
sudo docker-compose up -d dbPostgresql
```
The application is exposed on port `8091`

To access the swagger-ui documentation, navigate to `http://localhost:8091/swagger-ui/index.html`
