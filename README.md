# api-quickstart

[![Build Status](https://travis-ci.org/pallavkothari/WebhooksService.svg?branch=master)](https://travis-ci.org/pallavkothari/api-quickstart) [![Coverage Status](https://coveralls.io/repos/github/pallavkothari/api-quickstart/badge.svg?branch=master)](https://coveralls.io/github/pallavkothari/api-quickstart?branch=master)



Use this project to quickly stamp out a new rest api project.

Here's how:

```bash
mvn install

# create an archetype locally
mvn archetype:create-from-project
cd target/generated-sources/archetype
mvn install

# now stamp out a new project from this template
cd ~/git  # or wherever you want to create your project
mvn archetype:generate -DarchetypeCatalog=local
# follow the prompts and pick the local archetype just created (typically option 1)

cd <YOUR_SHINY_NEW_PROJECT>

git init
# set up a reasonable .gitignore file (`git add . -n` to preview files) 
git add .
git commit -m"Initial commit"

```

## Finish up
- Review and fixup the following generated files:
  - README.md
  - pom.xml
  - start.sh
  - pom.mustache
  - RestEndpoints.java
  - SwaggerConfig.java

## Tips and tricks

### Running your app
- Just run the main method in `Application.java` or run `start.sh` from the command line (which you'll need to modify with your artifact name)
- go to `localhost:8080` and you'll see `swagger-ui.html` by default which documents your existing rest endpoints

### Metrics & Health checks
- dropwizard metrics are prebuilt and can be used by injecting the `MetricRegistry` bean in your class
- you can add your own app health checks by extending `com.codahale.metrics.health.HealthCheck` and registering them with the `HealthCheckRegistry`. See `CpuHealthCheck` for an example.
- Check out the [dropwizard admin servlet](http://localhost:8080/dropwizard/) which exposes metrics, a ping page, thread dumps and cpu profiles :)
- All health check results are reported [here](http://localhost:8080/dropwizard/healthcheck?pretty=true)


### Swagger and codegen
- the generated client is driven off of `swagger.json`
- Running `GenerateSwaggerJsonTest` will regenerate that file if you change or add rest endpoints
- `mvn clean package` will regenerate the client sdk
- `pom.mustache` generates the client sdk's `pom.xml`
- The code in `ApplicationTests` is purely an example -- feel free to change or get rid of it
