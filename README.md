# Adverity challenge solution

## Getting started

To be easily started without any setup the app:

- uses simple In Memory H2 database
- reads CSV file during spring context startup and data loaded into memory db

Only Java 11 is required.



## Example queries

### in defined restClient

Check example tests defined in `./restClient/client.rest` file

### in other
- Get campaigns by datasource and the time range
http://localhost:8080/api/campaign/datasource/Google%20Ads?from=11%2F12%2F19&to=11%2F13%2F19

- Sum of clicks in the campaign by datasource and the time range
http://localhost:8080/api/campaign/datasource/Google%20Ads/clicks?from=11%2F12%2F19&to=11%2F13%2F19