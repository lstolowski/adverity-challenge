# Adverity challenge solution

## Getting started

To be easily started without setting up the app, I assumed that:

- simple in-memory `H2 database` is used
- CSV file is read during spring `context startup` and data loaded into memory db
- `Java 11 sdk` is required with `Maven` installed.

## Start the app

Run the app on http://localhost:8080
```bash
mvn spring-boot:run
```

## Example queries

### use defined in the e2e-restClient tests

Check example tests defined in `./e2e-restClient/client.rest` file

### in other
- Get campaigns by the datasource and the time range
http://localhost:8080/api/metrics/datasource/Google%20Ads?from=11%2F12%2F19&to=11%2F13%2F19

- Sum of clicks in the campaign by the datasource and the time range
http://localhost:8080/api/metrics/datasource/Google%20Ads/clicks?from=11%2F12%2F19&to=11%2F13%2F19

- CTR by the datasource and the campaign
http://localhost:8080/api/metrics/datasource/Google%20Ads/campaign/Adventmarkt%20Touristik/ctr
  
- all impressions groupped by day
http://localhost:8080/api/metrics/impressions/daily
  
#### notes
The rest api and the data model is very simple, so request params are based on given datasource and campaign names. 
Thats why it should be urlencoded in query 

## Tests running

- e2e-restClient -> end-to-end tests when application is started on localhost
- unit tests run by `mvn test`

## Logic implementation

Rest API is provided by `CampaignDataQueryController`.
Methods (API) are implemented in `CampaignService`.

## Import

Data import is run in `CsvImporter` class.
CSV file is downloaded to `src/main/resources` to avoid connectivity problems.